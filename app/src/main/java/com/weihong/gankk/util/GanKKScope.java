package com.weihong.gankk.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by wei.hong on 2017/8/25.
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface GanKKScope {
}
