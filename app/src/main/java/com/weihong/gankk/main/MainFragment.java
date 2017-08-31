package com.weihong.gankk.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.weihong.gankk.GanKKApplication;
import com.weihong.gankk.R;
import com.weihong.gankk.base.BaseFragment;
import com.weihong.gankk.data.bean.GanKKInfo;
import com.weihong.gankk.util.CheckUtil;
import com.weihong.gankk.util.GanKKConstant;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by wei.hong on 2017/8/24.
 */

public class MainFragment extends BaseFragment implements MainContract.View {

    public static final String ARG_TYPE = "type";

    private String type = GanKKConstant.GANK_TYPE_ALL;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @Inject
    MainAdapter adapter;
    @Inject
    MainPresenter mMainPresenter;
    int currentPage = 1;

    public static MainFragment newInstance(String type) {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(ARG_TYPE);
        }

        DaggerMainComponent.builder()
                .mainPresenterModule(new MainPresenterModule(this))
                .ganKKRepositoryComponent(((GanKKApplication) (getActivity().getApplication())).getGankkRepositoryComponent())
                .build()
                .inject(this);
    }

    @Override
    public void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initLogic() {
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                mMainPresenter.getData(type, GanKKConstant.PAGE_SIZE, currentPage);
            }
        });
        adapter.setEmptyView(R.layout.load_view, (ViewGroup) recyclerView.getParent());
        mMainPresenter.getData(type, GanKKConstant.PAGE_SIZE, currentPage);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_main;
    }

    @Override
    public void updateData(List<GanKKInfo> ganKKInfos) {
        if (CheckUtil.isEmpty(ganKKInfos)) {
            adapter.setEmptyView(R.layout.empty_view, (ViewGroup) recyclerView.getParent());
            return;
        } else {
            currentPage++;
        }
        adapter.addData(ganKKInfos);
        refreshLayout.finishLoadmore(200);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && CheckUtil.isEmpty(adapter.getData())) {
            mMainPresenter.getData(type, GanKKConstant.PAGE_SIZE, currentPage);
        }
    }
}
