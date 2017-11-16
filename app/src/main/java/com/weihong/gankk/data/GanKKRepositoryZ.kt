package com.weihong.gankk.data

import android.content.Context
import com.weihong.gankk.data.bean.GanKKInfoZ
import com.weihong.gankk.data.remote.RemoteRepositoryZ
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by weihong on 17-11-16.
 */
class GanKKRepositoryZ(context: Context) : IGanKKRepositoryZ {

    private var remoteRepositoryZ: RemoteRepositoryZ = RemoteRepositoryZ(context)

    override fun getData(type: String, pageSize: Int, page: Int): Observable<List<GanKKInfoZ>> {
        return remoteRepositoryZ.getData(type, pageSize, page)
                .subscribeOn(Schedulers.io())
                .flatMap({
                    if (it.error) Observable.empty()
                    else Observable.fromArray(it.GanKKs)
                })
    }

}