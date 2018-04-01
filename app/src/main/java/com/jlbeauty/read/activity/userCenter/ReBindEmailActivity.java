package com.jlbeauty.read.activity.userCenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.global.Config;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.weight.TitleView;

/**
 * 重新绑定提示页面
 */
public class ReBindEmailActivity extends BaseActivity {
    protected String TAG = this.getClass().getSimpleName();
    private TitleView titleView;
    private Button complete;
    private TextView tv_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_rebind_email);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));

    }

    @Override
    public void onClick(View v) {
        if (v.equals(complete)) {
            EmailActivity.startActivity(this, true);
            finish();
        }
    }


    /**
     * 跳转到 EmailActivity
     *
     * @param context
     */
    public static void startActivity(Activity context, String email) {
        Intent intent = new Intent(context, ReBindEmailActivity.class);
        intent.putExtra("email", email);
        context.startActivity(intent);
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
        titleView.setTitle("邮箱设置");
    }

    @Override
    protected void initView() {
        complete = (Button) findViewById(R.id.complete);
        tv_email = (TextView) findViewById(R.id.tv_email);

    }


    @Override
    protected void setData() {
        String email = getIntent().getStringExtra("email");
        tv_email.setText(email + "");
    }

    @Override
    protected void setEvent() {
        complete.setOnClickListener(this);
    }

}
