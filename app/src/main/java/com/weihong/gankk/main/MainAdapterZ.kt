package com.weihong.gankk.main

import android.graphics.Paint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.squareup.picasso.Picasso
import com.weihong.gankk.R
import com.weihong.gankk.data.bean.GanKKInfoZ
import com.weihong.gankk.util.GanKKConstant

/**
 * Created by weihong on 17-11-16.
 */
class MainAdapterZ : BaseQuickAdapter<GanKKInfoZ, BaseViewHolder>(R.layout.item_common) {

    override fun convert(helper: BaseViewHolder, item: GanKKInfoZ) {
        var imageView = helper.getView<ImageView>(R.id.image)
        val textView = helper.getView<TextView>(R.id.title)
        if (GanKKConstant.GANK_TYPE_MOOD == item.type) {
            imageView.visibility = View.VISIBLE
            textView.visibility = View.GONE
            Picasso.with(mContext).load(item.url).placeholder(R.drawable.placeholder).into(imageView)
        } else {
            imageView.visibility = View.GONE
            textView.visibility = View.VISIBLE
            textView.paint.flags = Paint.UNDERLINE_TEXT_FLAG
            textView.paint.isAntiAlias = true
            textView.text = "${item.type}:${item.desc}"
        }
    }
}