package com.weihong.gankk.main

import com.weihong.gankk.base.BasePresenter
import com.weihong.gankk.base.BaseViewZ
import com.weihong.gankk.data.bean.GanKKInfoZ

/**
 * Created by weihong on 17-11-16.
 */
interface MainContractZ {
    interface View : BaseViewZ<Presenter> {
        fun updateData(ganKKInfos: List<GanKKInfoZ>)
    }

    interface Presenter : BasePresenter {
        fun getData(type: String, pageSize: Int, page: Int)
    }
}