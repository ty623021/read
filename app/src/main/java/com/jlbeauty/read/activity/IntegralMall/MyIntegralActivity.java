package com.jlbeauty.read.activity.IntegralMall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;


import com.jlbeauty.read.R;
import com.jlbeauty.read.fragment.IntegralNotesFragment;
import com.jlbeauty.read.fragment.TaskIntegralFragment;
import com.jlbeauty.read.global.Config;
import com.rongteng.base.adapter.AllTabAdapter;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.weight.TitleView;



/**
 * Created by geek on 2016/11/2.
 * 积分任务
 */
public class MyIntegralActivity extends BaseActivity {
    private TitleView titleView;
    private ViewPager viewPager;
    private AllTabAdapter adapter;
    private TabLayout tabLayout;
    private int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tab_layout);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 跳转到 MyInvestmentListActivity
     *
     * @param context
     */
    public static void startActivity(Context context, int currPager) {
        Intent intent = new Intent(context, MyIntegralActivity.class);
        intent.putExtra("index", currPager);
        context.startActivity(intent);
    }


    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("我的积分");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        index = getIntent().getIntExtra("index", 0);

    }

    @Override
    protected void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new AllTabAdapter(this, viewPager);
        adapter.addTab("做任务赚积分", TaskIntegralFragment.class);
        adapter.addTab("积分记录", IntegralNotesFragment.class);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(index);
    }

    @Override
    protected void setData() {
    }

    @Override
    protected void setEvent() {

    }


}
