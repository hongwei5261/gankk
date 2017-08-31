package com.weihong.gankk.data.remote.response;

import com.google.gson.annotations.SerializedName;
import com.weihong.gankk.data.bean.GanKKInfo;

import java.util.List;

/**
 * Created by wei.hong on 2017/8/28.
 */

public class GanKKResponse {
    @SerializedName("error")
    public boolean error;
    @SerializedName("results")
    public List<GanKKInfo> GanKKs;
}
