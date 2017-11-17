package com.weihong.gankk.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.weihong.gankk.R
import com.weihong.gankk.base.BaseFragmentZ
import com.weihong.gankk.data.bean.GanKKInfoZ
import com.weihong.gankk.util.CheckUtil
import com.weihong.gankk.util.GanKKConstant
import org.jetbrains.anko.find

/**
 * Created by weihong on 17-11-16.
 */
class MainFragmentZ : BaseFragmentZ(), MainContractZ.View {

    private val ARG_TYPE = "type"
    var type: String = GanKKConstant.GANK_TYPE_ALL

    private var adapter: MainAdapterZ? = null
    private var mMainPresenter: MainPresenterZ? = null
    private var currentPage = 1

    fun newInstance(type: String): MainFragmentZ {
        val args = Bundle()
        val fragment = MainFragmentZ()
        args.putString(ARG_TYPE, type)
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments.getString(ARG_TYPE)

        adapter = MainAdapterZ()
        mMainPresenter = MainPresenterZ(context, this)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshLayout: SmartRefreshLayout
    override fun initViews(rootView: View) {
        recyclerView = rootView.find(R.id.recyclerView)
        refreshLayout = rootView.find(R.id.refreshLayout)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter?.setOnItemChildClickListener { adapter, view, position ->
            val info = adapter.data[position] as GanKKInfoZ
            if (GanKKConstant.GANK_TYPE_MOOD == info.type) {

            } else {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(info.url)
                startActivity(intent)
            }
        }
    }


    override fun initLogic() {
        recyclerView.adapter = adapter

        refreshLayout.setOnRefreshListener { layout -> layout.finishRefresh(2000) }
        refreshLayout.setOnLoadmoreListener { mMainPresenter?.getData(type, GanKKConstant.PAGE_SIZE, currentPage) }
        refreshLayout.isEnableRefresh = false
        adapter?.setEmptyView(R.layout.load_view, recyclerView.parent as ViewGroup?)
        mMainPresenter?.getData(type, GanKKConstant.PAGE_SIZE, currentPage)
    }

    override fun getContentViewId(): Int = R.layout.fragment_main

    override fun updateData(ganKKInfos: List<GanKKInfoZ>) {
        if (CheckUtil.isEmpty(ganKKInfos)) {
            adapter?.setEmptyView(R.layout.empty_view, recyclerView.parent as ViewGroup)
        } else {
            currentPage++
            adapter?.addData(ganKKInfos)
            refreshLayout.finishLoadmore(200)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && CheckUtil.isEmpty(adapter?.getData())) {
            mMainPresenter?.getData(type, GanKKConstant.PAGE_SIZE, currentPage)
        }
    }
}