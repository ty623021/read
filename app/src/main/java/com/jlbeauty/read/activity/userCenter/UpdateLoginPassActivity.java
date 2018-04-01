package com.jlbeauty.read.activity.userCenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.jlbeauty.read.R;
import com.jlbeauty.read.global.Config;
import com.jlbeauty.read.main.ReadApp;
import com.jlbeauty.read.utils.PassWordUtils;
import com.rongteng.base.global.Constant;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.utils.AbViewUtil;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.weight.EditTextWithDel;
import com.rongteng.base.weight.TitleView;


/**
 * 修改登录密码
 */
public class UpdateLoginPassActivity extends BaseActivity {
    protected String TAG = this.getClass().getSimpleName();
    private TitleView title;
    private EditTextWithDel et_confirm_new_loginPass;
    private EditTextWithDel et_new_loginPass;
    private EditTextWithDel et_old_loginPass;
    private Button complete;
    private MyTextWatcher textWatcher;
    //密码最低位数
    private static final int MINLENGTH = 5;
    private String newLoginPass;
    private String confirmNewLoginPass;
    private String oldLoginPass;
    private ImageView img_set, img_set1, img_set2;
    private LinearLayout eyeslinear, eyeslinear1, eyeslinear2;
    private boolean isHidden = true;
    private boolean isHidden1 = true;
    private boolean isHidden2 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_update_login_pass);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(title, true, Color.parseColor(Config.colorPrimary));

    }

    @Override
    protected void initTitle() {
        title = (TitleView) findViewById(R.id.title);
        title.setTitle("修改登录密码");
        title.setLeftImageButton(R.drawable.back);
        title.showLeftButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 跳转到 UpdateLoginPassActivity
     *
     * @param context
     */
    public static void startUpdateLoginPassActivity(Context context) {
        Intent intent = new Intent(context, UpdateLoginPassActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        img_set = (ImageView) findViewById(R.id.img_set);
        img_set1 = (ImageView) findViewById(R.id.img_set1);
        img_set2 = (ImageView) findViewById(R.id.img_set2);

        eyeslinear = (LinearLayout) findViewById(R.id.eyes_linear);
        eyeslinear1 = (LinearLayout) findViewById(R.id.eyes_linear1);
        eyeslinear2 = (LinearLayout) findViewById(R.id.eyes_linear2);

        et_new_loginPass = (EditTextWithDel) findViewById(R.id.new_loginPass);
        et_confirm_new_loginPass = (EditTextWithDel) findViewById(R.id.confirm_new_loginPass);
        et_old_loginPass = (EditTextWithDel) findViewById(R.id.old_loginPass);
        complete = (Button) findViewById(R.id.complete);
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setEvent() {
        complete.setOnClickListener(this);
        textWatcher = new MyTextWatcher();
        et_new_loginPass.addTextChangedListener(textWatcher);
        et_confirm_new_loginPass.addTextChangedListener(textWatcher);
        et_old_loginPass.addTextChangedListener(textWatcher);
        eyeslinear.setOnClickListener(this);
        eyeslinear1.setOnClickListener(this);
        eyeslinear2.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.equals(complete)) {
            boolean is = PassWordUtils.checkPassWord(this, newLoginPass, confirmNewLoginPass, oldLoginPass);
            if (is) {
                updateDealPass();
            }
        } else if (v.equals(eyeslinear)) {
            if (isHidden) {
                AbViewUtil.setEditTextHidden(isHidden, et_old_loginPass, img_set);
                isHidden = false;
            } else {
                AbViewUtil.setEditTextHidden(isHidden, et_old_loginPass, img_set);
                isHidden = true;
            }
        } else if (v.equals(eyeslinear1)) {
            if (isHidden1) {
                AbViewUtil.setEditTextHidden(isHidden1, et_new_loginPass, img_set1);
                isHidden1 = false;
            } else {
                AbViewUtil.setEditTextHidden(isHidden1, et_new_loginPass, img_set1);
                isHidden1 = true;
            }
        } else if (v.equals(eyeslinear2)) {
            if (isHidden2) {
                AbViewUtil.setEditTextHidden(isHidden2, et_confirm_new_loginPass, img_set2);
                isHidden2 = false;
            } else {
                AbViewUtil.setEditTextHidden(isHidden2, et_confirm_new_loginPass, img_set2);
                isHidden2 = true;
            }
        }
    }


    /**
     * 修改登录
     */
    private void updateDealPass() {
        params.put("memberId", ReadApp.mApp.getID());
        params.put("oldPassword", oldLoginPass);
        params.put("newPassword", newLoginPass);
        http.post(Config.URL_EDITPASSWORD, params, "正在修改...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e(TAG, json.toString());
                if (AbJsonUtil.isSuccess(json)) {
                    UpdateSuccessActivity.startActivity(UpdateLoginPassActivity.this, Constant.UPDATE_TYPE_UPDATELOGIN);
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
            newLoginPass = et_new_loginPass.getText().toString();
            confirmNewLoginPass = et_confirm_new_loginPass.getText().toString();
            oldLoginPass = et_old_loginPass.getText().toString();
            if (newLoginPass.length() > MINLENGTH && confirmNewLoginPass.length() > MINLENGTH && oldLoginPass.length() > MINLENGTH) {
                complete.setEnabled(true);
            } else {
                complete.setEnabled(false);
            }
        }
    }
}
