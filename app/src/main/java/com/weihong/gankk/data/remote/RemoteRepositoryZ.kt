package com.weihong.gankk.data.remote

import android.content.Context
import com.weihong.gankk.data.remote.interceptors.CacheInterceptorZ
import com.weihong.gankk.data.remote.interceptors.LoggingInterceptorZ
import com.weihong.gankk.data.remote.interceptors.NetWorkInterceptorZ
import com.weihong.gankk.data.remote.response.GanKKResponse
import com.weihong.gankk.data.remote.response.GanKKResponseZ
import com.weihong.gankk.util.FileUtil
import io.reactivex.Observable
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by weihong on 17-11-16.
 */
class RemoteRepositoryZ constructor(context: Context) {
    val baseUrl = ""

    private var mRetrofit: Retrofit

    init {
        val cacheSize = 20 * 1024 * 1024
        val dir = File(context.externalCacheDir, "response_cache")
        FileUtil.createMkdirs(dir)
        val cache = Cache(dir, cacheSize.toLong())

        var okHttpClient = OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(LoggingInterceptorZ())
                .addInterceptor(CacheInterceptorZ(context))
                .addNetworkInterceptor(NetWorkInterceptorZ())
                .connectTimeout(10, TimeUnit.SECONDS).build()

        mRetrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    }


    fun getData(type: String, pageSize: Int, page: Int): Observable<GanKKResponseZ> {
        return mRetrofit!!.create(RemoteServiceZ::class.java).getData(type, pageSize, page)
    }
}