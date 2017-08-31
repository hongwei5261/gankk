package com.weihong.gankk.data;

import com.weihong.gankk.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by wei.hong on 2017/8/25.
 */
@Singleton
@Component(modules = {GanKKRepositoryModule.class, ApplicationModule.class})
public interface GanKKRepositoryComponent {

    GanKKRepository provideGankkRepository();
}
