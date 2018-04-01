package com.jlbeauty.read.activity.userCenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.global.Config;
import com.jlbeauty.read.main.ReadApp;
import com.jlbeauty.read.utils.GetVerificationCode;
import com.rongteng.base.global.Constant;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AppUtil;
import com.rongteng.base.weight.CountdownProgressBar;
import com.rongteng.base.weight.EditTextWithDel;
import com.rongteng.base.weight.TitleView;

/**
 * 修改手机号码
 */
public class UpdateOldPhoneActivity extends BaseActivity implements GetVerificationCode.onCheckVerify {
    protected String TAG = this.getClass().getSimpleName();
    private TitleView title;
    private EditTextWithDel et_verification_code;
    private Button complete;
    private CountdownProgressBar roundProgressBar;
    private Button get_verification_code;

    private RelativeLayout relative_voice;
    private TextView get_voice_code;
    private String verification_code;//手机验证码
    private GetVerificationCode getVerCode;
    private EditTextWithDel et_user_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_update_phone);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(title, true, Color.parseColor(Config.colorPrimary));

    }


    @Override
    protected void initTitle() {
        title = (TitleView) findViewById(R.id.title);
        title.setTitle("更换手机号码");
        title.setLeftImageButton(R.drawable.back);
        title.showLeftButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 跳转到 UpdateOldPhoneActivity
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UpdateOldPhoneActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        et_verification_code = (EditTextWithDel) findViewById(R.id.et_verification_code);
        et_user_phone = (EditTextWithDel) findViewById(R.id.et_user_phone);
        complete = (Button) findViewById(R.id.complete);
        roundProgressBar = (CountdownProgressBar) findViewById(R.id.roundProgressBar);
        get_verification_code = (Button) findViewById(R.id.get_verification_code);
        //收不到验证码
        relative_voice = (RelativeLayout) findViewById(R.id.relative_voice);
        get_voice_code = (TextView) findViewById(R.id.get_voice_code);
    }

    @Override
    protected void setData() {
        String userPhone = AbStringUtil.hideUserPhone(ReadApp.mApp.getUserPhone());
        et_user_phone.setText(userPhone+"");
    }

    @Override
    protected void setEvent() {
        complete.setOnClickListener(this);
        get_verification_code.setOnClickListener(this);
        get_voice_code.setOnClickListener(this);
        et_verification_code.addTextChangedListener(new MyTecxtWatcher());
    }


    @Override
    public void onClick(View v) {
        if (getVerCode == null) {
            getVerCode = new GetVerificationCode(this, get_verification_code, roundProgressBar, relative_voice, et_verification_code);
        }
        if (v.equals(get_verification_code)) {
            getVerCode.getVerCode(http, ReadApp.mApp.getUserPhone(), "修改手机号码", false);
        } else if (v.equals(complete)) {
            getVerCode.setOnCheckVerify(this);
            getVerCode.checkVerifyOn(http, ReadApp.mApp.getUserPhone(), verification_code, complete);
        } else if (v.equals(get_voice_code)) {
            getVerCode.getVerCode(http, ReadApp.mApp.getUserPhone(), "修改手机号码", true);
        }
    }


    @Override
    public void onCheckVerify(boolean is) {
        if (is) {
            UpdateNewPhoneActivity.startActivity(this, verification_code);
        }
    }


    class MyTecxtWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            verification_code = et_verification_code.getText().toString();
            if (!AbStringUtil.isEmpty(verification_code)) {
                complete.setEnabled(true);
            } else {
                complete.setEnabled(false);
            }
        }
    }
}
