package com.weihong.gankk.main;


import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weihong.gankk.data.bean.GanKKInfo;

import javax.inject.Inject;

/**
 * Created by wei.hong on 2017/8/24.
 */

public class MainAdapter extends BaseQuickAdapter<GanKKInfo, BaseViewHolder> {

    @Inject
    public MainAdapter() {
        super(android.R.layout.test_list_item, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, GanKKInfo item) {
        ((TextView) helper.getView(android.R.id.text1)).setText(item.type + ":" + item.desc);
    }
}
