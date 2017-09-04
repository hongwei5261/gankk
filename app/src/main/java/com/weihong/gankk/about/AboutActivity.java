package com.weihong.gankk.about;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weihong.gankk.R;
import com.weihong.gankk.base.BaseActivity;
import com.weihong.gankk.widget.WeatherView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wei.hong on 2017/8/31.
 */

public class AboutActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.weatherView)
    WeatherView weatherView;

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        weatherView.start();
    }

    @Override
    public void initLogic() {
        recyclerView.setAdapter(new TestAdapter(getTestData()));
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_about;
    }

    public List<String> getTestData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("item " + (i + 1));
        }
        return list;
    }

    public class TestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public TestAdapter(@Nullable List<String> data) {
            super(android.R.layout.test_list_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ((TextView) helper.getView(android.R.id.text1)).setText(item);
        }
    }
}
