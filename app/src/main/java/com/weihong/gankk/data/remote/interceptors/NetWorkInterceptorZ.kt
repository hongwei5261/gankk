package com.weihong.gankk.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by weihong on 17-11-15.
 */
class NetWorkInterceptorZ : Interceptor {
    private val MAX_AGE = 60

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        var request = chain.request()
        var response = chain.proceed(request)
        response = response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, max-age=" + MAX_AGE)
                .build()
        return response
    }
}