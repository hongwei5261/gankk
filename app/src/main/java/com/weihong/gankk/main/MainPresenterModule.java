package com.weihong.gankk.main;

import com.weihong.gankk.util.GanKKScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wei.hong on 2017/8/25.
 */

@Module
public class MainPresenterModule {
    MainContract.View mMainView;

    public MainPresenterModule(MainContract.View mainView) {
        mMainView = mainView;
    }

    @GanKKScope
    @Provides
    MainContract.View provideMainView() {
        return mMainView;
    }
}
