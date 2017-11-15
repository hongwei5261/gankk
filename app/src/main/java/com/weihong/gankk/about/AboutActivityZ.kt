package com.weihong.gankk.about

import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.weihong.gankk.R
import com.weihong.gankk.base.BaseActivityZ
import kotlinx.android.synthetic.main.activity_about.*
import java.util.*

/**
 * Created by weihong on 17-11-15.
 */
class AboutActivityZ : BaseActivityZ() {

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        weatherView.start()
    }

    override fun initLogic() {
        recyclerView.adapter = TestAdapter(getTestData());
    }

    override fun getContentViewId(): Int = R.layout.activity_about

    private fun getTestData(): List<String> {
        var data = ArrayList<String>()
        (0..49).forEach { i -> data.add("itme kk" + (i + 1)) }
        return data
    }

    /**
     * 此适配器存在两个构造函数，如果只有一个构造函数可以采用如下注释部分实现
     */
    class TestAdapter : BaseQuickAdapter<String, BaseViewHolder> {

        /**
         * 主构造函数
         */
        constructor(id: Int, data: List<String>) : super(id, data)

        /**
         * 次构造函数，次构造函数必须调用主构造函数
         */
        constructor(data: List<String>) : this(android.R.layout.test_list_item, data)

        override fun convert(helper: BaseViewHolder?, item: String?) {
            helper!!.getView<TextView>(android.R.id.text1).text = item
        }
    }

    /*class TestAdapter constructor(id: Int = android.R.layout.test_list_item, data: List<String>) :
            BaseQuickAdapter<String, BaseViewHolder>(id, data) {

        override fun convert(helper: BaseViewHolder?, item: String?) {
            helper!!.getView<TextView>(android.R.id.text1).text = item
        }
    }*/
}