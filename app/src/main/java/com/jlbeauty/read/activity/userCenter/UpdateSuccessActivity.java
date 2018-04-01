package com.jlbeauty.read.activity.userCenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.activity.home.MainActivity;
import com.jlbeauty.read.global.Config;
import com.jlbeauty.read.main.ReadApp;
import com.rongteng.base.global.Constant;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.weight.TitleView;


/**
 * 修改密码成功
 */
public class UpdateSuccessActivity extends BaseActivity {
    protected String TAG = this.getClass().getSimpleName();
    private TitleView title;
    private Button re_login;
    private String type;
    private TextView success_tv1;
    private TextView success_tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_update_success);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(title, true, Color.parseColor(Config.colorPrimary));

    }

    /**
     * 跳转到 UpdateSuccessActivity
     *
     * @param context
     * @param type    类型
     */
    public static void startActivity(Activity context, String type) {
        Intent intent = new Intent(context, UpdateSuccessActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
        context.finish();
    }

    @Override
    protected void initTitle() {
        title = (TitleView) findViewById(R.id.title);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if (type != null) {
            this.title.setTitle("修改成功");
        } else {
            this.title.setTitle("修改成功");
        }
        this.title.setLeftImageButton(R.drawable.back);
        this.title.showLeftButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });

    }


    @Override
    protected void initView() {
        re_login = (Button) findViewById(R.id.Re_login);
        success_tv1 = (TextView) findViewById(R.id.success_tv1);
        success_tv2 = (TextView) findViewById(R.id.success_tv2);
    }

    @Override
    protected void setData() {
        if (type.equals(Constant.UPDATE_TYPE_UPDATEPHONE)) {
            success_tv1.setText(ReadApp.mApp.getUserPhone());
            success_tv2.setText("恭喜您，手机号更换成功!");
            re_login.setText("进入账户中心");
        } else if (type.equals(Constant.UPDATE_TYPE_FORGETPASS)) {
            success_tv1.setText("恭喜您，新密码设置成功!");
            success_tv2.setText("请牢记您的新密码");
            re_login.setText("重新登录");
        } else if (type.equals(Constant.UPDATE_TYPE_FORGETDEALPASS)) {
            success_tv1.setText("恭喜您，交易密码重置成功!");
            success_tv2.setText("请牢记您的新交易密码");
            re_login.setText("完成");
        } else if (type.equals(Constant.UPDATE_TYPE_UPDATENAME)) {
            success_tv1.setText("恭喜您，用户名修改成功!");
            success_tv2.setText("请牢记您的新用户名");
            re_login.setText("完成");
        } else if (type.equals(Constant.UPDATE_TYPE_UPDATELOGIN)) {
            success_tv1.setText("恭喜您，登录密码修改成功!");
            success_tv2.setText("请牢记您的新密码");
            re_login.setText("重新登录");
        } else if (type.equals(Constant.UPDATE_TYPE_UPDATEDEALPASS)) {
            success_tv1.setText("恭喜您，交易密码修改成功!");
            success_tv2.setText("请牢记您的新密码");
            re_login.setText("完成");
        }

    }

    @Override
    protected void setEvent() {
        re_login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.equals(re_login)) {
            finishActivity();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void finishActivity() {
        if (type.equals(Constant.UPDATE_TYPE_UPDATEPHONE)) {
            MainActivity.startActivity(this,4);
        } else if (type.equals(Constant.UPDATE_TYPE_FORGETPASS)) {
            LoginActivity.startActivity(this, true);
        } else if (type.equals(Constant.UPDATE_TYPE_FORGETDEALPASS)) {
            finish();
        } else if (type.equals(Constant.UPDATE_TYPE_UPDATENAME)) {
            finish();
        } else if (type.equals(Constant.UPDATE_TYPE_UPDATELOGIN)) {
            LoginActivity.startActivity(this, true);
        } else if (type.equals(Constant.UPDATE_TYPE_UPDATEDEALPASS)) {
            finish();
        }
    }
}
