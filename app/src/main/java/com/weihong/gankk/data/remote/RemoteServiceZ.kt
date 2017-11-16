package com.weihong.gankk.data.remote

import com.weihong.gankk.data.remote.response.GanKKResponseZ
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * Created by weihong on 17-11-16.
 */

interface RemoteServiceZ {

    @Headers("Content-Type: application/json", "Accept:  application/json")
    @GET("{type}/{pageSize}/{page}")
    fun getData(@Path("type") type: String, @Path("pageSize") pageSize: Int, @Path("page") page: Int): Observable<GanKKResponseZ>
}