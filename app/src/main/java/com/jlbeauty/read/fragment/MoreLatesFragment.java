package com.jlbeauty.read.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.jlbeauty.read.R;
import com.jlbeauty.read.adapter.MoreLatesAdapter;
import com.jlbeauty.read.bean.FindLatesActivitiesInfo;
import com.jlbeauty.read.global.Config;
import com.rongteng.base.global.Constant;
import com.rongteng.base.main.BaseFragment;
import com.rongteng.base.pullview.AbPullToRefreshView;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbRefreshUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.volley.RequestListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现页面 活动列表
 * Created by hjy on 2017/5/25.
 */

public class MoreLatesFragment extends BaseFragment implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
    private View view;
    private RecyclerView recyclerView;
    private MoreLatesAdapter adapter;
    private List<FindLatesActivitiesInfo> list = new ArrayList<>();
    private ImageView network_img;
    private ImageView nodata_img;
    private AbPullToRefreshView pull;
    private int totalPage, currentPage, pageSize = 10;
    private boolean isRefresh = true;
    private String status;
    private boolean isFirstHttp = true;//是否是第一次加载


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_more_lates, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            status = bundle.getString("type");
        }
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        network_img = (ImageView) view.findViewById(R.id.network_img);
        nodata_img = (ImageView) view.findViewById(R.id.nodata_img);
        pull = (AbPullToRefreshView) view.findViewById(R.id.pull);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setFocusable(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new MoreLatesAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        AbRefreshUtil.initRefresh(pull, this, this);
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setEvent() {
        network_img.setOnClickListener(this);
        nodata_img.setOnClickListener(this);
    }

    /**
     * 发送数据请求
     */
    private void sendHttp() {
        if (isRefresh) {
            currentPage = 1;
        } else {
            currentPage++;
        }
        params.put("status", status);
        params.put("currentPage", currentPage + "");
        params.put("pageSize", pageSize + "");
        http.post(Config.URL_ACTIVITYLIST, params, progressTitle, new RequestListener() {
                    @Override
                    public void requestSuccess(String json) {
                        AbLogUtil.e("活动列表", json);
                        if (AbJsonUtil.isSuccess(json)) {
                            TypeToken tp = new TypeToken<List<FindLatesActivitiesInfo>>() {
                            };
                            totalPage = AbJsonUtil.getTotalPage(json);
                            List<FindLatesActivitiesInfo> data = (List<FindLatesActivitiesInfo>) AbJsonUtil.fromJson(json, tp, "data");
                            if (isRefresh) {
                                adapter.setList(data);
                            } else {
                                adapter.addAll(data);
                            }

                        } else {
                            AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                        }
                        AbRefreshUtil.hintView(pull, adapter, isRefresh, false, network_img, nodata_img);
                    }

                    @Override
                    public void requestError(String message) {
                        AbToastUtil.showToast(context, message);
                        if (isRefresh) {
                        } else {
                            currentPage--;
                        }
                        AbRefreshUtil.hintView(pull, adapter, isRefresh, true, network_img, nodata_img);
                    }
                }
        );
    }

    @Override
    public void onClick(View v) {
        if (v.equals(network_img)) {
            network_img.setVisibility(View.GONE);
            progressTitle = Constant.LOADING;
            sendHttp();
        } else if (v.equals(nodata_img)) {
            nodata_img.setVisibility(View.GONE);
            progressTitle = Constant.LOADING;
            sendHttp();
        }

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirstHttp) {
            if (status != null) {
                isFirstHttp = false;
                sendHttp();
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            isFirstHttp = false;
            sendHttp();
        }
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        isRefresh = false;
        if (AbRefreshUtil.isLoad(currentPage, totalPage, pull)) {
            sendHttp();
        } else {
            AbToastUtil.showToast(context, Constant.LOADED);
        }
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        isRefresh = true;
        sendHttp();
    }
}
