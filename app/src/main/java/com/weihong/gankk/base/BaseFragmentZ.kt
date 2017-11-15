package com.weihong.gankk.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * Created by weihong on 17-11-14.
 */
abstract class BaseFragmentZ : Fragment() {

    private var mUnbinder: Unbinder? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater?.inflate(getContentViewId(), container, false)
        mUnbinder = ButterKnife.bind(this, rootView as View)
        initViews()
        initLogic()
        return rootView
    }
    /**
     * 初始化view
     */
    abstract fun initViews()

    /**
     * 初始化逻辑
     */
    abstract fun initLogic()

    /**
     * 初始化ContentViewId
     */
    abstract fun getContentViewId(): Int

    override fun onDestroy() {
        super.onDestroy()

        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder?.unbind()
        }
        mUnbinder = null
    }
}