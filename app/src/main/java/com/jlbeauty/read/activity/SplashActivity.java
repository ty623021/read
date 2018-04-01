package com.jlbeauty.read.activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;


import com.jlbeauty.read.BuildConfig;
import com.jlbeauty.read.R;
import com.jlbeauty.read.activity.home.MainActivity;
import com.jlbeauty.read.main.ReadApp;
import com.rongteng.base.bean.Home_Banner;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.webview.WebViewActivity;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;


/****
 * 启动页
 */
public class SplashActivity extends BaseActivity  {

    private static final int GOTO_NEXT = 1002;

    private static final long SPLASH_DELAY_MILLIS = 1000;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GOTO_NEXT:
                    gotoNext();
                    break;
            }
        }
    };
    private ImageView icon;
    private Home_Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        super.onCreate(savedInstanceState);
        // 用于第一次安装APP，进入到除这个启动activity的其他activity，点击home键，再点击桌面启动图标时，
        // 系统会重启此activty，而不是直接打开之前已经打开过的activity，因此需要关闭此activity
        if (!isTaskRoot()) {
            finish();
            return;
        }
        //开启友盟统计
        startMob();
    }


    /**
     * 友盟统计
     */
    private void startMob() {
        //设置为测试模式
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.updateOnlineConfig(this);
        // 对日志加密
        AnalyticsConfig.enableEncrypt(true);
    }

    private void gotoNext() {
        if (ReadApp.mApp.isFirstIn()) {
            //第一次登录的时候 和版本更新的时候
            GuideActivity.startActivity(this);
            finish();
        } else {
            goGuide();
        }
    }

    private void goGuide() {
        MainActivity.startActivity(this, 0);
        this.overridePendingTransition(R.anim.in_from_right, R.anim.scale_out_from_left);
    }


    @Override
    protected void initTitle() {
        // TODO Auto-generated method stub

    }


    @Override
    protected void initView() {
        icon = (ImageView) findViewById(R.id.splash_image_icon);
        mHandler.sendEmptyMessageDelayed(GOTO_NEXT, SPLASH_DELAY_MILLIS);
    }


    @Override
    protected void setData() {
        // TODO Auto-generated method stub

    }


    @Override
    protected void setEvent() {

    }

    @Override
    public void onClick(View v) {
        if (v.equals(icon)) {
            WebViewActivity.startActivity(this, banner.getLink(), banner.getTitle());
        }
    }
}

