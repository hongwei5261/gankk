package com.weihong.gankk.base

import android.app.Activity
import android.os.Bundle
import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * Created by weihong on 17-11-14.
 */
open abstract class BaseActivityZ : Activity() {

    private var mUnbinder: Unbinder? = null
    private var mExtrasBundle: Bundle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView()
        setContentView(getContentViewId())
        mUnbinder = ButterKnife.bind(this)
        mExtrasBundle = getIntent().getExtras()
        initView()
        initLogic()
    }

    /**
     * 初始化ContentView的使用
     */
    open fun initContentView() {

    }

    /**
     * 初始化view
     */
    open abstract fun initView()

    /**
     * 初始化逻辑
     */
    open abstract fun initLogic()

    /**
     * 初始化ContentViewId
     */
    open abstract fun getContentViewId(): Int

    override fun onDestroy() {
        super.onDestroy()

        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder?.unbind()
        }
        mUnbinder = null
    }
}