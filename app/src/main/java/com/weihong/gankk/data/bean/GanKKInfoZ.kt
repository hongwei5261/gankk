package com.weihong.gankk.data.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by weihong on 17-11-16.
 */
class GanKKInfoZ {

    @SerializedName("_id")
    var id: String? = null
    @SerializedName("createdAt")
    var createdAt: String? = null
    @SerializedName("desc")
    var desc: String? = null
    @SerializedName("images")
    var images: Array<String>? = null
    @SerializedName("publishedAt")
    var publishedAt: String? = null
    @SerializedName("source")
    var source: String? = null
    @SerializedName("type")
    var type: String? = null
    @SerializedName("used")
    var used: Boolean = false
    @SerializedName("url")
    var url: String? = null
    @SerializedName("who")
    var who: String? = null
}