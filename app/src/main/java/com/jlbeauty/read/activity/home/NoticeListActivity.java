package com.jlbeauty.read.activity.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;


import com.jlbeauty.read.R;
import com.jlbeauty.read.global.Config;
import com.rongteng.base.adapter.AllTabAdapter;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.weight.TitleView;


/**
 * 公告列表
 */
public class NoticeListActivity extends BaseActivity {
    private TitleView title;
    private ViewPager viewPager;
    private AllTabAdapter adapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tab_layout);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(title, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    public void onClick(View v) {

    }

    /**
     *
     * @param context
     * @param type        类型
     * @param startSource 来源
     */
    public static void startActivity(Context context, int type, String startSource) {
        Intent intent = new Intent(context, NoticeListActivity.class);
        intent.putExtra("startSource", startSource);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }


    @Override
    protected void initTitle() {
        title = (TitleView) findViewById(R.id.title);
        title.setTitle("公告中心");
        title.setLeftImageButton(R.drawable.back);
        title.showLeftButton(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    @Override
    protected void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        LinearLayout tabLinear = (LinearLayout) findViewById(R.id.tabLinear);
        tabLinear.setVisibility(View.GONE);
        adapter = new AllTabAdapter(this, viewPager);
//        adapter.addTab("公告", NoticeFragment.class);
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

}
