package com.jlbeauty.read.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.activity.home.MainActivity;
import com.jlbeauty.read.adapter.LicenseViewPagerAdapter;
import com.jlbeauty.read.global.Config;
import com.jlbeauty.read.viewPager.GuideTransformer;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.weight.TitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 */
public class GuideActivity extends BaseActivity {
    protected String TAG = this.getClass().getSimpleName();
    private ViewPager vp;
    private List<View> views;
    private LicenseViewPagerAdapter adapter;
    private int[] view = {
            R.drawable.welcome1,
            R.drawable.welcome2,
            R.drawable.welcome3,
            R.drawable.welcome4
    };
    private TitleView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, false, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {

    }

    /**
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, GuideActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        titleView = (TitleView) findViewById(R.id.title);
        vp = (ViewPager) findViewById(R.id.vp);
        views = new ArrayList<>();
        for (int i = 0; i < view.length; i++) {
            View guide_item = View.inflate(this, R.layout.item_guide, null);
            ImageView imageView = (ImageView) guide_item.findViewById(R.id.iv_guide);
            int i1 = this.view[i];
            imageView.setBackgroundResource(i1);
            if (i == view.length - 1) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.startActivity(GuideActivity.this, 0);
                    }
                });
            }
            views.add(guide_item);
        }
        adapter = new LicenseViewPagerAdapter(views, this);
        vp.setPageTransformer(true, new GuideTransformer());
        vp.setAdapter(adapter);
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setEvent() {

    }

    @Override
    public void onClick(View v) {

    }
}
