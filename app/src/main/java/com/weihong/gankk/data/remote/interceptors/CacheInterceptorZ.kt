package com.weihong.gankk.data.remote.interceptors

import android.content.Context
import android.util.Log
import com.weihong.gankk.util.NetworkUtil
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by weihong on 17-11-15.
 */
class CacheInterceptorZ(context: Context) : Interceptor {

    private var mContext = context
    private val TAG = "CacheInterceptorZ"
    private val MAX_STATLE = 60 * 60 * 24 * 2


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!NetworkUtil.isConnected(mContext)) {
            Log.d(TAG, "no network access cache response")
            val cacheControl = CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(MAX_STATLE, TimeUnit.SECONDS)
                    .build()
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()
        }
        return chain.proceed(request)
    }
}