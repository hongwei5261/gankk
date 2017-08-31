package com.weihong.gankk.base;

import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends Activity {

    Unbinder mUnbinder = null;
    public Bundle mExtrasBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        setContentView(getContentViewId());
        mUnbinder = ButterKnife.bind(this);
        mExtrasBundle = getIntent().getExtras();
        initView();
        initLogic();
    }

    /**
     * 初始化ContentView的使用
     */
    public void initContentView() {

    }

    /**
     * 初始化view
     */
    public abstract void initView();

    /**
     * 初始化逻辑
     */
    public abstract void initLogic();

    /**
     * 初始化ContentViewId
     */
    public abstract int getContentViewId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        mUnbinder = null;
    }
}