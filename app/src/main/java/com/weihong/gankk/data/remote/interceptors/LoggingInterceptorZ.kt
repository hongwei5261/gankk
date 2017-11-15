package com.weihong.gankk.data.remote.interceptors

import android.util.Log
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.internal.http.HttpHeaders
import okio.Buffer
import java.io.EOFException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 * Created by weihong on 17-11-15.
 */
class LoggingInterceptorZ : Interceptor {

    private val DEFAULT_TAG = "Http"
    private val UTF8 = Charset.forName("UTF-8")
    private val LEVEL_NONE = 0
    private val LEVEL_BASIC = 1
    private val LEVEL_BODY = 2
    private val LEVEL_HEADERS = 4
    private val LEVEL_ALL = LEVEL_BASIC or LEVEL_HEADERS or LEVEL_BODY
    private var mLevel = LEVEL_BASIC
    private var mTag = DEFAULT_TAG


    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        if (mLevel == LEVEL_NONE) {
            return chain.proceed(request)
        }

        var requestBody = request.body()
        val hasRequestBody = requestBody != null
        var connection = chain.connection()
        var protocol = connection?.protocol() ?: Protocol.HTTP_1_1
        Log.d(mTag, "--> ${request.method()}  ${request.url()}  $protocol  (${requestBody?.contentLength()}  -byte body)")

        if (isLogHeaders()) {
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                Log.d(mTag, "--> Headers: Content-Type: ${requestBody?.contentType()}")
                Log.d(mTag, "--> Headers: Content-Length: ${requestBody?.contentLength()}")
            }

            var headers = request.headers()
            val count = headers.size()
            for (i in 0 until count) {
                var name = headers.name(i)

                if (!"Content-Type".equals(name, ignoreCase = true) and !"Content-Length".equals(name, ignoreCase = true)) {
                    Log.d(mTag, "--> Headers: $name :  + ${headers.value(i)}")
                }
            }

            if (!isLogBody() || !hasRequestBody) {
                Log.d(mTag, "--> END ${request.method()}")
            } else if (bodyEncoded(request.headers())) {
                Log.d(mTag, "--> END ${request.method()} (encoded body omitted)")
            } else {
                var buffer = Buffer()
                requestBody.writeTo(buffer)

                var charset = UTF8
                var contentType = requestBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }

                Log.d(mTag, "");
                if (isPlaintext(buffer)) {
                    Log.d(mTag, "--> Body: " + buffer.readString(charset));
                    Log.d(mTag, "--> END " + request.method()
                            + " (" + requestBody.contentLength() + "-byte body)");
                } else {
                    Log.d(mTag, "--> END " + request.method() + " (binary "
                            + requestBody.contentLength() + "-byte body omitted)");
                }
            }
        }


        var startNs = System.nanoTime()
        var response: Response
        try {
            response = chain.proceed(request);
        } catch (e: Exception) {
            Log.d(mTag, "<-- HTTP FAILED: " + e)
            Log.d(mTag, "")
            throw e
        }
        var tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        var responseBody = response.body();
        var contentLength = responseBody?.contentLength() ?: -1L
        var bodySize = if (contentLength != -1L) {
            "$contentLength-byte"
        } else {
            "unknown-length"
        }
        Log.d(mTag, "<-- " + response.code() + ' ' + response.message() + ' '
                + response.request().url() + " (" + tookMs + "ms, " + bodySize + " body)");


        if (isLogHeaders()) {
            val headers = response.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                Log.d(mTag, "<-- Headers: " + headers.name(i) + ": " + headers.value(i))
                i++
            }

            if (!isLogBody() || !HttpHeaders.hasBody(response)) {
                Log.d(mTag, "<-- END HTTP")
            } else if (bodyEncoded(response.headers())) {
                Log.d(mTag, "<-- END HTTP (encoded body omitted)")
            } else if (responseBody == null) {
                Log.d(mTag, "<-- END HTTP (null response body)")
            } else {
                val source = responseBody.source()
                source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
                val buffer = source.buffer()

                var charset: Charset? = UTF8
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }

                if (!isPlaintext(buffer)) {
                    Log.d(mTag, "<-- END HTTP (binary ${buffer.size()}-byte body omitted)")
                    Log.d(mTag, "")
                    return response
                }

                if (contentLength != 0L) {
                    Log.d(mTag, "")
                    assert(charset != null)
                    Log.d(mTag, "<-- Body: ${buffer.clone().readString(charset!!)}")
                }

                Log.d(mTag, "<-- END HTTP (${buffer.size()}-byte body)")
            }
        }
        Log.d(mTag, "")

        return response
    }

    private fun isLogHeaders(): Boolean {
        return mLevel and LEVEL_BASIC != 0
    }

    private fun isLogBody(): Boolean {
        return mLevel and LEVEL_BODY != 0
    }

    private fun bodyEncoded(headers: Headers): Boolean {
        var contentEncoding = headers.get("Content-Encoding")
        return contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
    }


    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private fun isPlaintext(buffer: Buffer): Boolean {
        try {
            var prefix = Buffer()
            var byteCount = if (buffer.size() < 64) buffer.size() else 64
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0..15) {
                if (prefix.exhausted()) {
                    break
                }
                var codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            return true
        } catch (e: EOFException) {
            return false
        }
    }
}