package com.weihong.gankk.data;

import android.content.Context;

import com.weihong.gankk.data.bean.GanKKInfo;
import com.weihong.gankk.data.remote.RemoteRepository;
import com.weihong.gankk.data.remote.response.GanKKResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wei.hong on 2017/8/25.
 */

public class GanKKRepository implements IGanKKRepository {

    @Inject
    RemoteRepository mRemoteRepository;

    @Inject
    public GanKKRepository(Context context) {

    }

    @Override
    public Observable<List<GanKKInfo>> getData(String type, int pageSize, int page) {
        return mRemoteRepository.getData(type, pageSize, page)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<GanKKResponse, Observable<List<GanKKInfo>>>() {
                    @Override
                    public Observable<List<GanKKInfo>> apply(@NonNull GanKKResponse ganKKResponse) throws Exception {
                        if (ganKKResponse.error) {
                            return null;
                        }
                        return Observable.fromArray(ganKKResponse.GanKKs);
                    }
                });
    }
}
