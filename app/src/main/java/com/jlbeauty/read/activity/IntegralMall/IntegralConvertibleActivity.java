package com.jlbeauty.read.activity.IntegralMall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;
import com.jlbeauty.read.R;
import com.jlbeauty.read.adapter.IntegralExchangeAdapter;
import com.jlbeauty.read.bean.IntegralVoucherInfo;
import com.jlbeauty.read.global.Config;

import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.pullview.AbPullToRefreshView;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbRefreshUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.weight.TitleView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by geek on 2016/11/2.
 * 可兑换
 */
public class IntegralConvertibleActivity extends BaseActivity implements AbPullToRefreshView.OnHeaderRefreshListener {
    private TitleView titleView;
    private RecyclerView recycleView;
    private IntegralExchangeAdapter adapter;
    private List<IntegralVoucherInfo> list = new ArrayList<>();
    private AbPullToRefreshView pull;
    private int mallIntegral;
    private RelativeLayout network_disabled;
    private ImageView network_img;
    private ImageView nodata_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_integral_convertible);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));

    }

    @Override
    public void onClick(View v) {
        if (v.equals(network_img)) {
            network_img.setVisibility(View.GONE);
            sendHttp();
        }
    }

    /**
     * 跳转到 IntegralConvertibleActivity
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, IntegralConvertibleActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("可兑换列表");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mallIntegral = getIntent().getIntExtra("mallIntegral", 0);

    }

    @Override
    protected void initView() {
        pull = (AbPullToRefreshView) findViewById(R.id.pull);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);

        network_disabled = (RelativeLayout) findViewById(R.id.network_disabled);
        network_img = (ImageView) findViewById(R.id.network_img);
        nodata_img = (ImageView) findViewById(R.id.nodata_img);

        recycleView.setNestedScrollingEnabled(false);
        recycleView.setFocusable(false);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        recycleView.setLayoutManager(layoutManager);
        adapter = new IntegralExchangeAdapter(list);
        recycleView.setAdapter(adapter);
        AbRefreshUtil.initRefresh(pull, this);
    }

    @Override
    protected void setData() {
        sendHttp();
    }

    @Override
    protected void setEvent() {
        network_img.setOnClickListener(this);
    }

    private void sendHttp() {
        http.post(Config.URL_MALL_INTEGRAL_WAITLIST, params, progressTitle, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("积分兑换列表", json);
                if (AbJsonUtil.isSuccess(json)) {
                    progressTitle = null;
                    TypeToken typeToken = new TypeToken<List<IntegralVoucherInfo>>() {
                    };
                    List<IntegralVoucherInfo> data = (List<IntegralVoucherInfo>) AbJsonUtil.fromJson(json, typeToken, "data");
                    if (AbStringUtil.isListEmpty(data)) {
                        if (list.size() > 0) {
                            list.clear();
                        }
                        list.addAll(data);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
                AbRefreshUtil.hintView(pull, adapter, false, network_disabled, network_img, nodata_img);
            }

            @Override
            public void requestError(String message) {
                AbRefreshUtil.hintView(pull, adapter, true, network_disabled, network_img, nodata_img);
                AbToastUtil.showToast(context, message);
            }
        });
    }


    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        sendHttp();
    }


}
