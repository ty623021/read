package com.jlbeauty.read.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jlbeauty.read.R;
import com.jlbeauty.read.adapter.RecycleVideoListAdapter;
import com.jlbeauty.read.bean.HomeInfo;
import com.rongteng.base.main.BaseFragment;
import com.rongteng.base.pullview.AbPullToRefreshView;
import com.rongteng.base.utils.AbRefreshUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WIN10 on 2018/3/21.
 * 视频界面
 */
public class VideoFragment extends BaseFragment implements  AbPullToRefreshView.OnHeaderRefreshListener {
    private View view;
    RecyclerView recycleView;
    RecycleVideoListAdapter adapter;
    private AbPullToRefreshView pull;
    //首页应用列表
    private List<HomeInfo> vList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;

    }

    @Override
    protected void initTitle() {


    }

    @Override
    protected void initView() {

        recycleView = (RecyclerView) view.findViewById(R.id.recycleView_video);
        pull = (AbPullToRefreshView) view.findViewById(R.id.pull);
        recycleView.setNestedScrollingEnabled(false);
        recycleView.setFocusable(false);
        recycleView.setLayoutManager(new LinearLayoutManager(context));

        adapter = new RecycleVideoListAdapter(context, vList);
        recycleView.setAdapter(adapter);
        AbRefreshUtil.initRefresh(pull, this);

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setEvent() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {

    }
}
