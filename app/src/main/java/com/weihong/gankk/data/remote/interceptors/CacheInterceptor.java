package com.weihong.gankk.data.remote.interceptors;

import android.content.Context;
import android.util.Log;

import com.weihong.gankk.util.NetworkUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wei.hong on 2017/5/10.
 */

public class CacheInterceptor implements Interceptor {
    private static final String TAG = "CacheIntercept";
    /**
     * 缓存2天
     */
    private static final int MAX_STATLE = 60 * 60 * 24 * 2;
    private Context mContext;

    public CacheInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!NetworkUtil.isConnected(mContext)) {
            Log.d(TAG, "no network access cache response");
            CacheControl cacheControl = new CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(MAX_STATLE, TimeUnit.SECONDS)
                    .build();
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();
        }
        return chain.proceed(request);
    }
}
