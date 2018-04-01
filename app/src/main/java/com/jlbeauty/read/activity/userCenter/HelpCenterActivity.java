package com.jlbeauty.read.activity.userCenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;


import com.jlbeauty.read.R;
import com.jlbeauty.read.global.Config;
import com.rongteng.base.adapter.AllTabAdapter;
import com.rongteng.base.global.Constant;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.utils.AppUtil;
import com.rongteng.base.weight.TitleView;

/**
 * Created by hjy on 2016/7/14.
 * 帮助中心
 */
public class HelpCenterActivity extends BaseActivity {
    private TitleView titleView;
    private String title;
    private ViewPager viewPager;
    private AllTabAdapter adapter;
    private TabLayout tabLayout;
    private Button service_phone;
    private Button btn_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_helpcenter);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        if (title != null) {
            titleView.setTitle(title);
        } else {
            titleView.setTitle("帮助中心");
        }
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * 跳转到 HelpCenterActivity
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HelpCenterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {

        service_phone = (Button) findViewById(R.id.service_phone);
        btn_feedback = (Button) findViewById(R.id.btn_feedback);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        findViewById(R.id.tabLinear).setVisibility(View.GONE);
        adapter = new AllTabAdapter(this, viewPager);
        adapter.addTab("常见问题", "cjwt", HelpCenterFragment.class);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void setData() {
    }


    @Override
    protected void setEvent() {
        service_phone.setOnClickListener(this);
        btn_feedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(service_phone)) {
            AppUtil.call(context, Constant.CUSTOMER_SERVICE_PHONE);
        } else if (v.equals(btn_feedback)) {
            SuggestionsActivity.startActivity(context);
        }

    }

}
