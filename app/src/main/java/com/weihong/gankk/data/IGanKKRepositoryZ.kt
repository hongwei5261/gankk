package com.weihong.gankk.data

import com.weihong.gankk.data.bean.GanKKInfoZ
import io.reactivex.Observable

/**
 * Created by weihong on 17-11-16.
 */
interface IGanKKRepositoryZ {
    fun getData(type: String, pageSize: Int, page: Int): Observable<List<GanKKInfoZ>>
}