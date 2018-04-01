package com.jlbeauty.read.activity.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.activity.userCenter.LoginActivity;
import com.jlbeauty.read.fragment.ChatFragment;
import com.jlbeauty.read.fragment.HomeFragment;
import com.jlbeauty.read.fragment.PersonFragment;
import com.jlbeauty.read.fragment.ShopMallFragment;
import com.jlbeauty.read.fragment.VideoFragment;
import com.jlbeauty.read.global.Config;
import com.jlbeauty.read.main.ReadApp;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.weight.TitleView;

import yicheng.android.ui.materialdesignlibrary.views.LayoutRipple;

public class MainActivity extends BaseActivity {
    private TitleView titleView;
    private ImageView ivOne, ivTow, ivThree, ivFour,ivFive;
    private TextView tvOne, tvTow, tvThree, tvFour, tvFive;
    //                   pipleOne:首页, pipleTow:聊天, pipleThree:视频, pipleFour:商城 ,pipleFive:个人中心
    private LayoutRipple pipleOne, pipleTow, pipleThree, pipleFour,pipleFive;

    // 再次点击返回键退出
    private long exitTime = 0;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private HomeFragment homeFragment;
    private ChatFragment chatFragment;
    private VideoFragment videoFragment;
    private ShopMallFragment shopMallFragment;
    private PersonFragment personFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, false, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        titleView = (TitleView) findViewById(R.id.title);
        ivOne = (ImageView) findViewById(R.id.tab_one_icon);
        tvOne = (TextView) findViewById(R.id.tab_one_text);

        ivTow = (ImageView) findViewById(R.id.tab_tow_icon);
        tvTow = (TextView) findViewById(R.id.tab_tow_text);

        ivThree = (ImageView) findViewById(R.id.tab_three_icon);
        tvThree = (TextView) findViewById(R.id.tab_three_text);

        ivFour = (ImageView) findViewById(R.id.tab_four_icon);
        tvFour = (TextView) findViewById(R.id.tab_four_text);

        ivFive = (ImageView) findViewById(R.id.tab_five_icon);
        tvFive = (TextView) findViewById(R.id.tab_five_text);

        pipleOne = (LayoutRipple) findViewById(R.id.piple_one);
        pipleTow = (LayoutRipple) findViewById(R.id.piple_tow);
        pipleThree = (LayoutRipple) findViewById(R.id.piple_three);
        pipleFour = (LayoutRipple) findViewById(R.id.piple_four);
        pipleFive = (LayoutRipple) findViewById(R.id.piple_five);
    }

    @Override
    protected void setData() {
        fragmentManager = getSupportFragmentManager();
        ReadApp.mApp.setPersonalStatus(true);
        switchTab(0);
    }

    @Override
    protected void setEvent() {
        pipleOne.setOnClickListener(this);
        pipleTow.setOnClickListener(this);
        pipleThree.setOnClickListener(this);
        pipleFour.setOnClickListener(this);
        pipleFive.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(pipleOne)) {
            switchTab(0);
        } else if (v.equals(pipleTow)) {
            switchTab(1);
        }else if (v.equals(pipleThree)) {
            switchTab(2);
        }  else if (v.equals(pipleFour)) {
            switchTab(3);
        } else if (v.equals(pipleFive)) {
            switchTab(4);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                AbToastUtil.showToast(MainActivity.this, "再次点击返回键退出");
                exitTime = System.currentTimeMillis();
            } else {
                ReadApp.exit();
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            int currPager = intent.getIntExtra("currPager", 0);
            switchTab(currPager);
        }
    }


    public void switchTab(int index) {
        initTab();
        transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (index) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.container, homeFragment, "home");
                } else {
                    transaction.show(homeFragment);
                }
                ivOne.setSelected(true);
                tvOne.setSelected(true);
                break;
            case 1:
                if (chatFragment == null) {
                    chatFragment = new ChatFragment();
                    transaction.add(R.id.container, chatFragment, "chat");
                } else {
                    transaction.show(chatFragment);
                }
                ivTow.setSelected(true);
                tvTow.setSelected(true);
                break;
            case 2:
                if (videoFragment == null) {
                    videoFragment = new VideoFragment();
                    transaction.add(R.id.container, videoFragment, "video");
                } else {
                    transaction.show(videoFragment);
                }
                ivThree.setSelected(true);
                tvThree.setSelected(true);
                break;
            case 3:
                if (shopMallFragment == null) {
                    shopMallFragment = new ShopMallFragment();
                    transaction.add(R.id.container, shopMallFragment, "shop");
                } else {
                    // 如果indexFragment不为空，则直接将它显示出来
                    transaction.show(shopMallFragment);
                }
                ivFour.setSelected(true);
                tvFour.setSelected(true);
                break;
            case 4:
                if (personFragment == null) {
                    personFragment = new PersonFragment();
                    transaction.add(R.id.container, personFragment,"person");
                } else {
                    // 如果indexFragment不为空，则直接将它显示出来
                    transaction.show(personFragment);
                }
                ivFive.setSelected(true);
                tvFive.setSelected(true);
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    private void initTab() {
        ivOne.setSelected(false);
        ivTow.setSelected(false);
        ivThree.setSelected(false);
        ivFour.setSelected(false);
        ivFive.setSelected(false);
        tvOne.setSelected(false);
        tvTow.setSelected(false);
        tvThree.setSelected(false);
        tvFour.setSelected(false);
        tvFive.setSelected(false);
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (chatFragment != null) {
            transaction.hide(chatFragment);
        }
        if (videoFragment != null) {
            transaction.hide(videoFragment);
        }
        if (shopMallFragment != null) {
            transaction.hide(shopMallFragment);
        }
        if (personFragment != null) {
            transaction.hide(personFragment);
        }
    }

    /**
     * 跳转到 MainActivity
     * 不关闭上一个页面
     *
     * @param context
     * @param currPager 选中的页面
     */
    public static void startActivity(Context context, int currPager) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("currPager", currPager);
        context.startActivity(intent);
    }

    /**
     * 跳转到 MainActivity
     *
     * @param context
     * @param currPager 选中的页面
     */
    public static void startActivity(Activity context, int currPager) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("currPager", currPager);
        context.startActivity(intent);
        context.finish();
    }
}
