package com.weihong.gankk.data;

import android.content.Context;

import com.weihong.gankk.data.remote.interceptors.CacheInterceptorZ;
import com.weihong.gankk.data.remote.interceptors.LoggingInterceptor;
import com.weihong.gankk.data.remote.interceptors.LoggingInterceptorZ;
import com.weihong.gankk.data.remote.interceptors.NetWorkInterceptorZ;
import com.weihong.gankk.util.FileUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wei.hong on 2017/8/25.
 */

@Module
public class GanKKRepositoryModule {

    String baseUrl = "http://gank.io/api/data/";

    @Singleton
    @Provides
    Retrofit provideCalendarRetrofit(Retrofit.Builder builder, OkHttpClient okHttpClient) {
        return builder
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient provideOKHttpClient(Context context, Cache cache) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new LoggingInterceptorZ())
                .addInterceptor(new CacheInterceptorZ(context))
                .addNetworkInterceptor(new NetWorkInterceptorZ())
                .connectTimeout(10, TimeUnit.SECONDS);
        return builder.build();
    }

    @Singleton
    @Provides
    Cache provideCache(Context context) {
        // 缓存大小20M
        int cacheSize = 20 * 1024 * 1024;
        File dir = new File(context.getExternalCacheDir(), "response_cache");
        FileUtil.createMkdirs(dir);
        Cache cache = new Cache(dir, cacheSize);
        return cache;
    }
}
