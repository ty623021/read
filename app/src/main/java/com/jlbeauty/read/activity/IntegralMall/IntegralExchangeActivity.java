package com.jlbeauty.read.activity.IntegralMall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.adapter.IntegralExchangeAdapter;
import com.jlbeauty.read.bean.IntegralExchangeHomeInfo;
import com.jlbeauty.read.bean.IntegralVoucherInfo;
import com.jlbeauty.read.global.Config;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.pullview.AbPullToRefreshView;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbRefreshUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.weight.TitleView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by geek on 2016/11/2.
 * 积分兑换
 */
public class IntegralExchangeActivity extends BaseActivity implements AbPullToRefreshView.OnHeaderRefreshListener {
    private TitleView titleView;
    private RecyclerView recycleView;
    private IntegralExchangeAdapter adapter;
    private List<IntegralVoucherInfo> list = new ArrayList<>();
    private AbPullToRefreshView pull;
    private LinearLayout left_linear;
    private LinearLayout right_linear;
    private TextView tv_voucher_number;
    private TextView tv_mallIntegral;
    private IntegralExchangeHomeInfo info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_integral_exchange);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));

    }

    @Override
    public void onClick(View v) {
        if (v.equals(left_linear)) {
            if (info == null) {
                AbToastUtil.showToast(context, "数据加载失败");
                return;
            }
            if (info.getIntegralVoucherCount() == 0) {
                AbToastUtil.showToast(context, "积分不足，没有红包可兑换，您可以先去赚积分");
                return;
            }
            IntegralConvertibleActivity.startActivity(context);
        } else if (v.equals(right_linear)) {
            MyIntegralActivity.startActivity(context, 0);
        }
    }

    /**
     * 跳转到 IntegralExchangeActivity
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, IntegralExchangeActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("积分兑换");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        pull = (AbPullToRefreshView) findViewById(R.id.pull);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        left_linear = (LinearLayout) findViewById(R.id.left_linear);
        right_linear = (LinearLayout) findViewById(R.id.right_linear);
        tv_voucher_number = (TextView) findViewById(R.id.tv_voucher_number);
        tv_mallIntegral = (TextView) findViewById(R.id.tv_mallIntegral);
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

    }

    private void sendHttp() {
        http.post(Config.URL_MALL_INTEGRAL_HOME, params, progressTitle, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                pull.onHeaderRefreshFinish();
                AbLogUtil.e("积分兑换首页", json);
                if (AbJsonUtil.isSuccess(json)) {
                    progressTitle = null;
                    info = (IntegralExchangeHomeInfo) AbJsonUtil.fromJson(json, IntegralExchangeHomeInfo.class);
                    if (info != null) {
                        if (list.size() > 0) {
                            list.clear();
                        }
                        tv_voucher_number.setText(info.getIntegralVoucherCount() + "");
                        tv_mallIntegral.setText(info.getMallIntegral() + "");
                        list.addAll(info.getResultIntegralVoucher());
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                pull.onHeaderRefreshFinish();
                AbToastUtil.showToast(context, message);
            }
        });
    }

    @Override
    protected void setEvent() {
        left_linear.setOnClickListener(this);
        right_linear.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendHttp();
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        sendHttp();
    }

}
