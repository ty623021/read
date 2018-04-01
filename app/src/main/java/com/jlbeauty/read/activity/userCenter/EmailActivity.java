package com.jlbeauty.read.activity.userCenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.global.Config;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.utils.DialogUtil;
import com.rongteng.base.utils.Tool;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.weight.EditTextWithDel;
import com.rongteng.base.weight.TitleView;

/**
 * 邮箱认证
 */
public class EmailActivity extends BaseActivity {
    protected String TAG = this.getClass().getSimpleName();
    private String active = "active";//激活
    private String rebind = "rebind";//重新绑定
    private TitleView titleView;
    private EditTextWithDel et_user_email;
    private Button complete;
    private String userEmail;
    private boolean isEmailStatus;//true 代表已绑定 false 代表未绑定
    private Button bt_confirm;
    private TextView tv_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_email);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));

    }

    @Override
    public void onClick(View v) {
        if (v.equals(complete)) {
            if (Tool.isEmail(userEmail)) {
                if (isEmailStatus) {
                    sendEmail(rebind);
                } else {
                    sendEmail(active);
                }
            } else {
                AbToastUtil.showToast(context, "请输入正确的邮箱地址");
            }
        } else if (v.equals(bt_confirm)) {
            DialogUtil.dismiss();
            finish();
        }
    }


    /**
     * 跳转到 EmailActivity
     *
     * @param context
     */
    public static void startActivity(Activity context, boolean isEmailStatus) {
        Intent intent = new Intent(context, EmailActivity.class);
        intent.putExtra("isEmailStatus", isEmailStatus);
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
        isEmailStatus = getIntent().getBooleanExtra("isEmailStatus", false);
    }

    @Override
    protected void initView() {
        et_user_email = (EditTextWithDel) findViewById(R.id.et_user_email);
        complete = (Button) findViewById(R.id.complete);
    }


    @Override
    protected void setData() {
        if (isEmailStatus) {
            titleView.setTitle("邮箱设置");
            complete.setText("提交");
        } else {
            titleView.setTitle("邮箱设置");
            complete.setText("提交");
        }
    }

    @Override
    protected void setEvent() {
        complete.setOnClickListener(this);
        et_user_email.addTextChangedListener(new MyTextWatcher());
    }

    private void sendEmail(String type) {
        params.put("type", type);
        params.put("email", userEmail);
        http.post(Config.URL_BIND_EMAIL, params, "正在提交...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e(TAG, json.toString());
                if (AbJsonUtil.isSuccess(json)) {
                    show();
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                AbToastUtil.showToast(context, message);
            }
        });
    }

    class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            userEmail = et_user_email.getText().toString().trim();
            if (AbStringUtil.isEmpty(userEmail)) {
                complete.setEnabled(false);
            } else {
                complete.setEnabled(true);
            }
        }
    }


    private void show() {
        View view = View.inflate(this, R.layout.bind_email_layout, null);
        bt_confirm = (Button) view.findViewById(R.id.dialog_bt_confirm);
        tv_email = (TextView) view.findViewById(R.id.tv_email);
        tv_email.setText(userEmail);
        bt_confirm.setOnClickListener(this);
        DialogUtil.showAlertDialog(view);
    }

}
