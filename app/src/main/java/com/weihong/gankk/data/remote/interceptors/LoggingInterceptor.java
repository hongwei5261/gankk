package com.weihong.gankk.data.remote.interceptors;


import android.support.annotation.NonNull;
import android.util.Log;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by wei.hong on 2017/5/10.
 */

public class LoggingInterceptor implements Interceptor {

    static final String DEFAULT_TAG = "Http";
    static final Charset UTF8 = Charset.forName("UTF-8");

    static final int LEVEL_NONE = 0;
    static final int LEVEL_BASIC = 1;
    static final int LEVEL_BODY = 2;
    static final int LEVEL_HEADERS = 4;
    static final int LEVEL_ALL = LEVEL_BASIC | LEVEL_HEADERS | LEVEL_BODY;

    private int mLevel = LEVEL_BASIC;
    private String mTag = DEFAULT_TAG;

    public LoggingInterceptor() {
        this(LEVEL_BASIC, DEFAULT_TAG);
    }

    public LoggingInterceptor(int level) {
        this(level, DEFAULT_TAG);
    }

    public LoggingInterceptor(String tag) {
        this(LEVEL_BASIC, tag);
    }

    LoggingInterceptor(int level, String tag) {
        this.mLevel = level;
        this.mTag = tag;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request();
        if (mLevel == LEVEL_NONE) {
            return chain.proceed(request);
        }

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        String logRequestMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
        if (hasRequestBody) {
            logRequestMessage += " (" + requestBody.contentLength() + "-byte body)";
        }
        Log.d(mTag, logRequestMessage);

        if (isLogHeaders()) {
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody.contentType() != null) {
                    Log.d(mTag, "--> Headers: Content-Type: " + requestBody.contentType());
                }
                if (requestBody.contentLength() != -1) {
                    Log.d(mTag, "--> Headers: Content-Length: " + requestBody.contentLength());
                }
            }

            Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    Log.d(mTag, "--> Headers: " + name + ": " + headers.value(i));
                }
            }

            if (!isLogBody() || !hasRequestBody) {
                Log.d(mTag, "--> END " + request.method());
            } else if (bodyEncoded(request.headers())) {
                Log.d(mTag, "--> END " + request.method() + " (encoded body omitted)");
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                Log.d(mTag, "");
                if (isPlaintext(buffer)) {
                    assert charset != null;
                    Log.d(mTag, "--> Body: " + buffer.readString(charset));
                    Log.d(mTag, "--> END " + request.method()
                            + " (" + requestBody.contentLength() + "-byte body)");
                } else {
                    Log.d(mTag, "--> END " + request.method() + " (binary "
                            + requestBody.contentLength() + "-byte body omitted)");
                }
            }
        }
        Log.d(mTag, "");

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            Log.d(mTag, "<-- HTTP FAILED: " + e);
            Log.d(mTag, "");
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody == null ? -1 : responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        Log.d(mTag, "<-- " + response.code() + ' ' + response.message() + ' '
                + response.request().url() + " (" + tookMs + "ms, " + bodySize + " body)");

        if (isLogHeaders()) {
            Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                Log.d(mTag, "<-- Headers: " + headers.name(i) + ": " + headers.value(i));
            }

            if (!isLogBody() || !HttpHeaders.hasBody(response)) {
                Log.d(mTag, "<-- END HTTP");
            } else if (bodyEncoded(response.headers())) {
                Log.d(mTag, "<-- END HTTP (encoded body omitted)");
            } else if (responseBody == null) {
                Log.d(mTag, "<-- END HTTP (null response body)");
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (!isPlaintext(buffer)) {
                    Log.d(mTag, "<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                    Log.d(mTag, "");
                    return response;
                }

                if (contentLength != 0) {
                    Log.d(mTag, "");
                    assert charset != null;
                    Log.d(mTag, "<-- Body: " + buffer.clone().readString(charset));
                }

                Log.d(mTag, "<-- END HTTP (" + buffer.size() + "-byte body)");
            }
        }
        Log.d(mTag, "");

        return response;
    }

    private boolean isLogHeaders() {
        return (mLevel & LEVEL_HEADERS) != 0;
    }

    private boolean isLogBody() {
        return (mLevel & LEVEL_BODY) != 0;
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }
}
