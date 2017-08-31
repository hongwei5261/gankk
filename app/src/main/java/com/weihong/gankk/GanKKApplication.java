package com.weihong.gankk;

import android.app.Application;

import com.weihong.gankk.data.DaggerGanKKRepositoryComponent;
import com.weihong.gankk.data.GanKKRepositoryComponent;
import com.weihong.gankk.data.GanKKRepositoryModule;

/**
 * Created by wei.hong on 2017/8/25.
 */

public class GanKKApplication extends Application {
    private GanKKRepositoryComponent mGanKKRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mGanKKRepositoryComponent = DaggerGanKKRepositoryComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .ganKKRepositoryModule(new GanKKRepositoryModule())
                .build();
    }

    public GanKKRepositoryComponent getGankkRepositoryComponent() {
        return mGanKKRepositoryComponent;
    }
}
