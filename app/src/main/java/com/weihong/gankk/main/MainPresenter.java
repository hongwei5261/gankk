package com.weihong.gankk.main;

import com.weihong.gankk.data.GanKKRepository;
import com.weihong.gankk.data.bean.GanKKInfo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by wei.hong on 2017/8/25.
 */

final class MainPresenter implements MainContract.Presenter {

    MainContract.View mMainView;
    @Inject
    GanKKRepository mGanKKRepository;

    @Inject
    public MainPresenter(MainContract.View mainView) {
        mMainView = mainView;
    }

    @Override
    public void getData(String type, int pageSize, int page) {
        mGanKKRepository.getData(type, pageSize, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GanKKInfo>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<GanKKInfo> ganKKInfos) {
                        mMainView.updateData(ganKKInfos);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
