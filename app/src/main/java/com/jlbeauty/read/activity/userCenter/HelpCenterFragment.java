package com.jlbeauty.read.activity.userCenter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;
import com.jlbeauty.read.R;
import com.jlbeauty.read.adapter.HelpCenterAdapter;
import com.jlbeauty.read.bean.ArticleInfo;
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
 * Created by geek on 2016/6/21.
 * 帮助中心
 */
public class HelpCenterFragment extends BaseFragment implements
        AbPullToRefreshView.OnHeaderRefreshListener,
        AbPullToRefreshView.OnFooterLoadListener {
    private View view;
    private ExpandableListView lv_list;
    private boolean isRefresh = true;
    private boolean isFirstLoad=true;
    private int totalPage, offset, max = 10;
    private HelpCenterAdapter mAdapter;
    private AbPullToRefreshView pull;
    private ImageView network_img;
    private ImageView nodata_img;
    private RelativeLayout network;
    private List<ArticleInfo> groups;
    private String type;
    private List<ArticleInfo> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString("type");
        }
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initTitle() {
    }

    @Override
    protected void initView() {
        lv_list = (ExpandableListView) view.findViewById(R.id.lv);
        pull = (AbPullToRefreshView) view.findViewById(R.id.pull);

        network_img = (ImageView) view.findViewById(R.id.network_img);
        nodata_img = (ImageView) view.findViewById(R.id.nodata_img);
        network = (RelativeLayout) view.findViewById(R.id.notice_relative_network);
        groups = new ArrayList<>();
        mAdapter = new HelpCenterAdapter(context, groups);
        lv_list.setAdapter(mAdapter);

        lv_list.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i < mAdapter.getGroupCount(); i++) {
                    if (groupPosition != i) {// 关闭其他分组
                        lv_list.collapseGroup(i);
                    }
                }
            }
        });
        AbRefreshUtil.initRefresh(pull, this, this);
    }


    @Override
    protected void setData() {
        if (type.equals("cjwt")) {
            sendHttp();
        }
    }

    private void sendHttp() {
        params.put("typeCode", type);
        params.put("offset", offset + "");
        params.put("max", max + "");
        params.put("loadContent", true + "");
        params.put("publishPlatform", "2");
        http.post(Config.URL_GETARTICLELISTON, params, progressTitle, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e(TAG, json.toString());
                onComplete();
                if (AbJsonUtil.isSuccess(json)) {
                    TypeToken ty = new TypeToken<List<ArticleInfo>>() {
                    };
                    totalPage = AbJsonUtil.getTotalPage(json);
                    list = (List<ArticleInfo>) AbJsonUtil.fromJson(json, ty, "data");
                    if (AbStringUtil.isListEmpty(list)) {
                        progressTitle = null;
                        mAdapter.addList(list, isRefresh);
                        lv_list.setVisibility(View.VISIBLE);

                    }
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
                hintView(false);
            }

            @Override
            public void requestError(String message) {
                hintView(true);
                onComplete();
                AbToastUtil.showToast(context, message);
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
        if (isVisibleToUser && isFirstLoad) {
            if (getActivity() != null && http != null) {
                isFirstLoad = !isFirstLoad;
                sendHttp();
            }
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

    private void onComplete() {
        if (isRefresh == true) {
            pull.onHeaderRefreshFinish();
        } else {
            pull.onFooterLoadFinish();
        }
    }

    private void hintView(boolean isNetwork) {
        if (mAdapter.getGroupCount() == 0) {
            network.setVisibility(View.VISIBLE);
            if (isNetwork) {
                network_img.setVisibility(View.VISIBLE);
                nodata_img.setVisibility(View.GONE);
            } else {
                network_img.setVisibility(View.GONE);
                nodata_img.setVisibility(View.VISIBLE);
            }
        } else {
            network.setVisibility(View.GONE);
        }
    }


}
