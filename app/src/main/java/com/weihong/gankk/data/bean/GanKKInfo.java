package com.weihong.gankk.data.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wei.hong on 2017/8/28.
 */

public class GanKKInfo {

    @SerializedName("_id")
    public String id;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("desc")
    public String desc;
    @SerializedName("images")
    public String[] images;
    @SerializedName("publishedAt")
    public String publishedAt;
    @SerializedName("source")
    public String source;
    @SerializedName("type")
    public String type;
    @SerializedName("url")
    public String used;
    @SerializedName("used")
    public boolean url;
    @SerializedName("who")
    public String who;
}
