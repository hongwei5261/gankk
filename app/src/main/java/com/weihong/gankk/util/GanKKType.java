package com.weihong.gankk.util;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static com.weihong.gankk.util.GanKKConstant.GANK_TYPE_ALL;
import static com.weihong.gankk.util.GanKKConstant.GANK_TYPE_ANDROID;
import static com.weihong.gankk.util.GanKKConstant.GANK_TYPE_APPS;
import static com.weihong.gankk.util.GanKKConstant.GANK_TYPE_COLLECTION_VIDEO;
import static com.weihong.gankk.util.GanKKConstant.GANK_TYPE_IOS;
import static com.weihong.gankk.util.GanKKConstant.GANK_TYPE_JAVASCRIPT;
import static com.weihong.gankk.util.GanKKConstant.GANK_TYPE_LOCATION_ARROW;
import static com.weihong.gankk.util.GanKKConstant.GANK_TYPE_MOOD;
import static com.weihong.gankk.util.GanKKConstant.GANK_TYPE_MORE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by wei.hong on 2017/8/28.
 */

@StringDef({
        GANK_TYPE_ALL,
        GANK_TYPE_MOOD,
        GANK_TYPE_ANDROID,
        GANK_TYPE_IOS,
        GANK_TYPE_COLLECTION_VIDEO,
        GANK_TYPE_JAVASCRIPT,
        GANK_TYPE_LOCATION_ARROW,
        GANK_TYPE_APPS,
        GANK_TYPE_MORE})
@Retention(SOURCE)
public @interface GanKKType {
}