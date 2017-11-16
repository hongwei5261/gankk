package com.weihong.gankk.main

import android.content.Context
import com.weihong.gankk.data.GanKKRepositoryZ
import com.weihong.gankk.data.bean.GanKKInfoZ
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable

/**
 * Created by weihong on 17-11-16.
 */
class MainPresenterZ(context: Context, mainView: MainContractZ.View) : MainContractZ.Presenter {
    private var mMainView = mainView
    private var mGanKKRepositoryZ: GanKKRepositoryZ = GanKKRepositoryZ(context)

    override fun getData(type: String, pageSize: Int, page: Int) {
        mGanKKRepositoryZ.getData(type, pageSize, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<GanKKInfoZ>> {
                    override fun onSubscribe(@NonNull d: Disposable) {

                    }

                    override fun onNext(@NonNull ganKKInfos: List<GanKKInfoZ>) {
                        mMainView.updateData(ganKKInfos)
                    }

                    override fun onError(@NonNull e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
    }

}