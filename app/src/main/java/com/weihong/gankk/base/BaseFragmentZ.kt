package com.weihong.gankk.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by weihong on 17-11-14.
 */
abstract class BaseFragmentZ : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater?.inflate(getContentViewId(), container, false)
        initViews(rootView!!)
        initLogic()
        return rootView
    }

    /**
     * 初始化view
     */
    abstract fun initViews(rootView: View)

    /**
     * 初始化逻辑
     */
    abstract fun initLogic()

    /**
     * 初始化ContentViewId
     */
    abstract fun getContentViewId(): Int
}