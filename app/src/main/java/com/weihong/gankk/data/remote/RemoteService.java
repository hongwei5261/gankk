package com.weihong.gankk.data.remote;

import com.weihong.gankk.data.remote.response.GanKKResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by wei.hong on 2017/8/28.
 */

interface RemoteService {

    @Headers({"Content-Type: application/json", "Accept:  application/json"})
    @GET("{type}/{pageSize}/{page}")
    Observable<GanKKResponse> getData(@Path("type") String type, @Path("pageSize") int pageSize, @Path("page") int page);
}
