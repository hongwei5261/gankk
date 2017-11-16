package com.weihong.gankk.data.remote.response

import com.google.gson.annotations.SerializedName
import com.weihong.gankk.data.bean.GanKKInfo
import com.weihong.gankk.data.bean.GanKKInfoZ

/**
 * Created by weihong on 17-11-16.
 */
class GanKKResponseZ {

    @SerializedName("error")
    var error: Boolean = false
    @SerializedName("results")
    var GanKKs: List<GanKKInfoZ>? = null
}