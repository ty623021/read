package com.jlbeauty.read.activity.userCenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jlbeauty.read.R;
import com.jlbeauty.read.global.Config;
import com.rongteng.base.adapter.AllTabAdapter;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.weight.TitleView;

/**
 * 邀请记录
 */
public class InviteNoteActivity extends BaseActivity {
    protected String TAG = this.getClass().getSimpleName();
    private TitleView titleView;
    private int index;
    private TabLayout tabLayout;
    private AllTabAdapter adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_invite_activity);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);
        titleView.setTitle("邀请记录");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 跳转到 FundsDetailedActivity
     *
     * @param context
     * @param index   默认显示那一个页面
     */
    public static void startActivity(Context context, int index) {
        Intent intent = new Intent(context, InviteNoteActivity.class);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setVisibility(View.GONE);
        adapter = new AllTabAdapter(this, viewPager);
//        adapter.addTab("已注册", "reg", investInviteNoteFragment.class);
        adapter.addTab("要求好友", "", InviteNoteFragment.class);

        //方法二：一步到位
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(index);
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

