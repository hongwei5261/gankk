package com.weihong.gankk.data;

import com.weihong.gankk.data.bean.GanKKInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wei.hong on 2017/8/25.
 */

public interface IGanKKRepository {

    Observable<List<GanKKInfo>> getData(String type, int pageSize, int page);
}
