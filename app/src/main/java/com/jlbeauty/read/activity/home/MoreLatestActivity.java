package com.jlbeauty.read.activity.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;


import com.jlbeauty.read.R;
import com.jlbeauty.read.fragment.MoreLatesFragment;
import com.jlbeauty.read.global.Config;
import com.rongteng.base.adapter.AllTabAdapter;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.weight.TitleView;


/**
 * Created by hjy on 2017/5/21.
 * 发现页面活动列表
 */

public class MoreLatestActivity extends BaseActivity {
    private TitleView titleView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AllTabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tab_layout);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("活动列表");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * 跳转到更多活动列表页面
     *
     * @param context
     * @param type
     */
    public static void startActivity(Context context, int type) {
        Intent intent = new Intent(context, MoreLatestActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new AllTabAdapter(this, viewPager);
        adapter.addTab("进行中", "pro", MoreLatesFragment.class);
        adapter.addTab("未开始", "pre", MoreLatesFragment.class);
        adapter.addTab("已结束", "end", MoreLatesFragment.class);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void setData() {
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        viewPager.setCurrentItem(type);
    }

    @Override
    protected void setEvent() {

    }

    @Override
    public void onClick(View v) {

    }
}
