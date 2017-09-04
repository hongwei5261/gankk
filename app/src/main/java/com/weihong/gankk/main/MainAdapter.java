package com.weihong.gankk.main;


import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;
import com.weihong.gankk.R;
import com.weihong.gankk.data.bean.GanKKInfo;
import com.weihong.gankk.util.GanKKConstant;

import javax.inject.Inject;

/**
 * Created by wei.hong on 2017/8/24.
 */

public class MainAdapter extends BaseQuickAdapter<GanKKInfo, BaseViewHolder> {

    @Inject
    public MainAdapter() {
        super(R.layout.item_common, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, GanKKInfo item) {
        ImageView imageView = helper.getView(R.id.image);
        TextView textView = helper.getView(R.id.title);
        if (GanKKConstant.GANK_TYPE_MOOD.equals(item.type)) {
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            Picasso.with(mContext).load(item.url).placeholder(R.drawable.placeholder).into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            textView.getPaint().setAntiAlias(true);
            textView.setText(item.type + ":" + item.desc);
        }
    }
}
