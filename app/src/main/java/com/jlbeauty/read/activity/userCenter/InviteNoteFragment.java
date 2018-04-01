package com.jlbeauty.read.activity.userCenter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;
import com.jlbeauty.read.R;
import com.jlbeauty.read.adapter.InviteNoteAdapter;
import com.jlbeauty.read.bean.InviteNoteInfo;
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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 邀请记录
 */
public class InviteNoteFragment extends BaseFragment implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {

    private View view;
    private AbPullToRefreshView pull;
    private ListView listView;
    private List<InviteNoteInfo> list = new ArrayList<>();
    private InviteNoteAdapter adapter;
    private ImageView network_img;
    private ImageView nodata_img;
    private RelativeLayout network;
    private int totalPage, offset, max = 10;
    private boolean isRefresh;
    private String status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_invite_note, container, false);
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
        network_img = (ImageView) view.findViewById(R.id.network_img);
        nodata_img = (ImageView) view.findViewById(R.id.nodata_img);
        network = (RelativeLayout) view.findViewById(R.id.notice_relative_network);

        pull = (AbPullToRefreshView) view.findViewById(R.id.pull);
        listView = (ListView) view.findViewById(R.id.lv);
        adapter = new InviteNoteAdapter(context, list);
        listView.setAdapter(adapter);

        AbRefreshUtil.initRefresh(pull, this, this);
    }

    @Override
    protected void setData() {
        sendHttp();
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
            progressTitle = Constant.LOADING;
            pull.headerRefreshing();
        }
    }

    private void sendHttp() {
        params.put("type", status);
        params.put("offset", offset + "");
        params.put("max", max + "");
        http.post(Config.URL_GETINVITEDETAIL, params, progressTitle, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbRefreshUtil.onComplete(true, isRefresh, pull, adapter, list);
                AbLogUtil.e(TAG, json.toString());
                if (AbJsonUtil.isSuccess(json)) {
                    Type typeToken = new TypeToken<List<InviteNoteInfo>>() {
                    }.getType();
                    totalPage = AbJsonUtil.getTotalPage(json);
                    List<InviteNoteInfo> data = (List<InviteNoteInfo>) AbJsonUtil.fromJson(json, typeToken, "data");
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
                AbRefreshUtil.hintView(true, adapter, network, nodata_img, network_img);
                AbRefreshUtil.onComplete(false, isRefresh, pull, adapter, list);
                AbToastUtil.showToast(context, message);
            }
        });
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
            AbToastUtil.showToast(getContext(), "已加载全部");
        }
    }
}

