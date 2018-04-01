package com.jlbeauty.read.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;

import com.jlbeauty.read.R;
import com.jlbeauty.read.adapter.IntegralNotesAdapter;
import com.jlbeauty.read.bean.IntegralInfo;
import com.jlbeauty.read.global.Config;
import com.rongteng.base.global.Constant;
import com.rongteng.base.main.BaseFragment;
import com.rongteng.base.pullview.AbPullToRefreshView;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbRefreshUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.volley.RequestListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geek on 2016/11/1.
 * 积分记录
 */
public class IntegralNotesFragment extends BaseFragment implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {

    private View view;
    private FragmentActivity mActivity;
    private ListView listView;
    private IntegralNotesAdapter adapter;
    private AbPullToRefreshView pull;
    private int totalPage, offset, max = 10;
    private boolean isRefresh;
    private List<IntegralInfo> list;
    private ImageView network_img;
    private ImageView nodata_img;
    private RelativeLayout network;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_integral_notes, container, false);
        mActivity = getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        listView = (ListView) view.findViewById(R.id.listView);
        pull = (AbPullToRefreshView) view.findViewById(R.id.pull);

        network_img = (ImageView) view.findViewById(R.id.network_img);
        nodata_img = (ImageView) view.findViewById(R.id.nodata_img);
        network = (RelativeLayout) view.findViewById(R.id.notice_relative_network);
    }

    @Override
    protected void setData() {
        list = new ArrayList<>();
        adapter = new IntegralNotesAdapter(context, list);
        listView.setAdapter(adapter);

        AbRefreshUtil.initRefresh(pull, this, this);
        sendHttp();

    }

    private void sendHttp() {
        params.put("offset", offset + "");
        params.put("max", max + "");
        http.post(Config.GET_INTEGRAL_LIST_URL, params, progressTitle, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("json", json.toString());
                AbRefreshUtil.onComplete(true, isRefresh, pull, adapter, list);
                if (AbJsonUtil.isSuccess(json)) {
                    totalPage = AbJsonUtil.getTotalPage(json);
                    TypeToken ty = new TypeToken<List<IntegralInfo>>() {
                    };
                    List<IntegralInfo> data = (List<IntegralInfo>) AbJsonUtil.fromJson(json, ty, "data");
                    if (AbStringUtil.isListEmpty(data)) {
                        progressTitle = null;
                        list.addAll(data);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
                AbRefreshUtil.hintView(false, adapter, network, nodata_img, network_img);
            }

            @Override
            public void requestError(String message) {
                AbToastUtil.showToast(context, message);
                AbRefreshUtil.hintView(true, adapter, network, nodata_img, network_img);
                AbRefreshUtil.onComplete(false, isRefresh, pull, adapter, list);
            }
        });
    }

    @Override
    protected void setEvent() {
        network_img.setOnClickListener(this);
        nodata_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(network_img)) {
            network_img.setVisibility(View.GONE);
            pull.headerRefreshing();
            progressTitle = Constant.LOADING;
        } else if (v.equals(nodata_img)) {
            nodata_img.setVisibility(View.GONE);
            pull.headerRefreshing();
            progressTitle = Constant.LOADING;
        }
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        isRefresh = true;
        offset = 0;
        AbRefreshUtil.isAddLoad(offset, totalPage, max, pull);
        sendHttp();
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        isRefresh = false;
        offset += max;
        if (AbRefreshUtil.isLoading(offset, totalPage, pull)) {
            sendHttp();
        } else {
            AbToastUtil.showToast(getContext(), Constant.LOADED);
        }
    }
}
