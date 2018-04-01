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

import com.jlbeauty.read.R;
import com.jlbeauty.read.global.Config;
import com.jlbeauty.read.utils.PassWordUtils;
import com.rongteng.base.global.Constant;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.weight.EditTextWithDel;
import com.rongteng.base.weight.TitleView;

/**
 * 重置密码
 */
public class ResetPasswordActivity extends BaseActivity {
    protected String TAG = this.getClass().getSimpleName();
    private TitleView title;
    private EditTextWithDel et_user_password;
    private EditTextWithDel et_user_confirm;
    private Button confirm;
    private String user_confirm;
    private String user_password;
    private String mobile;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_update_password);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(title, true, Color.parseColor(Config.colorPrimary));
    }


    /**
     * 跳转到 ResetPasswordActivity
     *
     * @param context
     * @param title   标题
     * @param mobile  手机号
     * @param code    验证码
     */
    public static void startActivity(Activity context, String title, String mobile, String code) {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("mobile", mobile);
        intent.putExtra("code", code);
        context.startActivity(intent);
        context.finish();
    }


    @Override
    protected void initTitle() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        mobile = intent.getStringExtra("mobile");
        code = intent.getStringExtra("code");
        this.title = (TitleView) findViewById(R.id.title);
        if (title != null) {
            this.title.setTitle(title);
        } else {
            this.title.setTitle("重置密码");
        }
        this.title.setLeftImageButton(R.drawable.back);
        this.title.showLeftButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    protected void initView() {
        MyTextWatcher watcher = new MyTextWatcher();
        et_user_password = (EditTextWithDel) findViewById(R.id.et_user_password);
        et_user_confirm = (EditTextWithDel) findViewById(R.id.et_user_confirm);
        et_user_password.addTextChangedListener(watcher);
        et_user_confirm.addTextChangedListener(watcher);
        confirm = (Button) findViewById(R.id.confirm);
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setEvent() {
        confirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.equals(confirm)) {
            if (PassWordUtils.checkPassWord(this, user_password, user_confirm)) {
                params.put("mobile", mobile);
                params.put("code", code);
                params.put("newPwd", user_password);
                http.post(Config.URL_DORETRIEVEPWDRESETON, params, "正在重置...", new RequestListener() {
                    @Override
                    public void requestSuccess(String json) {
                        if (AbJsonUtil.isSuccess(json)) {
                            UpdateSuccessActivity.startActivity(ResetPasswordActivity.this, Constant.UPDATE_TYPE_FORGETPASS);
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
        }
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
            user_password = et_user_password.getText().toString();
            user_confirm = et_user_confirm.getText().toString();
            if (!AbStringUtil.isEmpty(user_password) && !AbStringUtil.isEmpty(user_confirm)) {
                confirm.setEnabled(true);
            } else {
                confirm.setEnabled(false);
            }
        }
    }
}
