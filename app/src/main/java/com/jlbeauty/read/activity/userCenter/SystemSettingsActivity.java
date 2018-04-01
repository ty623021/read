package com.jlbeauty.read.activity.userCenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.jlbeauty.read.R;
import com.jlbeauty.read.global.Config;
import com.jlbeauty.read.main.ReadApp;
import com.jlbeauty.read.utils.UpdateVersionUtil;
import com.jlbeauty.read.weight.AlertDialog;
import com.rongteng.base.global.Constant;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.main.MyApplication;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.utils.AppUtil;
import com.rongteng.base.utils.DataCleanManager;
import com.rongteng.base.weight.TitleView;

import java.io.File;

/**
 * 系统设置
 */
public class SystemSettingsActivity extends BaseActivity {
    protected String TAG = this.getClass().getSimpleName();
    private TitleView titleView;
    private Button bt_logout;
    private TextView tv_version;
    private TextView tv_cache;
    private RelativeLayout version_relative;
    private RelativeLayout cache_relative;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_system_settings);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }


    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.setTitle("系统设置");
    }


    /**
     * 跳转到 SystemSettingsActivity
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SystemSettingsActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void initView() {
        bt_logout = (Button) findViewById(R.id.bt_logout);
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_cache = (TextView) findViewById(R.id.tv_cache);
        version_relative = (RelativeLayout) findViewById(R.id.version_relative);
        cache_relative = (RelativeLayout) findViewById(R.id.cache_relative);
    }

    @Override
    protected void setData() {
        tv_version.setText("当前版本：V" + AppUtil.getAppVersionName(context));
        try {
            String cacheSize = DataCleanManager.getTotalCacheSize(context);
            tv_cache.setText(cacheSize + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setEvent() {
        bt_logout.setOnClickListener(this);
        version_relative.setOnClickListener(this);
        cache_relative.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(bt_logout)) {
            final AlertDialog dialogAccount = new AlertDialog(context);
            if (dialogAccount != null) {
                dialogAccount.showDialog("安全退出", "是否退出小诸葛金服帐号？", new AlertDialog.DialogOnClickListener() {
                    @Override
                    public void onPositiveClick() {
                        dialogAccount.dismiss();
                        ReadApp.mApp.logout();
                        finish();
                    }

                    @Override
                    public void onNegativeClick() {
                        dialogAccount.dismiss();
                    }
                });
            }

//            AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
//            dialog.setTitle("安全退出");
//            dialog.setMessage("是否退出小诸葛金服帐号？");
//
//            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    RongTengApplication.mApp.logout();
//                    finish();
//                }
//            });
//            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            dialog.show();


        } else if (v.equals(version_relative)) {
            UpdateVersionUtil versionUtil = new UpdateVersionUtil(context, 1);
            versionUtil.checkVersion(version_relative);
        } else if (v.equals(cache_relative)) {
            DataCleanManager.cleanInternalCache(MyApplication.getMaApplication());
            DataCleanManager.cleanCustomCache(Environment.getExternalStorageDirectory()
                    + File.separator
                    + Constant.base_data_path
                    + File.separator
                    + Constant.images_cache_path);
            try {
                tv_cache.setText("0.00");
                AbToastUtil.showToast(context, "清理完成");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
