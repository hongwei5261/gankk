package com.weihong.gankk.main;

import com.weihong.gankk.data.GanKKRepositoryComponent;
import com.weihong.gankk.util.GanKKScope;

import dagger.Component;

/**
 * Created by wei.hong on 2017/8/25.
 */
@GanKKScope
@Component(dependencies = GanKKRepositoryComponent.class, modules = MainPresenterModule.class)
public interface MainComponent {

    void inject(MainFragment mainFragment);
}
