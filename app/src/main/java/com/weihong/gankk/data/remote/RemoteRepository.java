package com.weihong.gankk.data.remote;

import com.weihong.gankk.data.remote.response.GanKKResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by wei.hong on 2017/8/25.
 */

public class RemoteRepository {

    @Inject
    Retrofit mRetrofit;

    @Inject
    public RemoteRepository() {
    }

    public Observable<GanKKResponse> getData(String type, int pageSize, int page) {
        return mRetrofit.create(RemoteService.class)
                .getData(type, pageSize, page);
    }
}
