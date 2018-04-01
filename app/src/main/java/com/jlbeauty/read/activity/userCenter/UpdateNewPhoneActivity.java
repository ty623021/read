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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.global.Config;
import com.jlbeauty.read.main.ReadApp;
import com.jlbeauty.read.utils.GetVerificationCode;
import com.rongteng.base.global.Constant;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.utils.AddSpaceTextWatcher;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.weight.CountdownProgressBar;
import com.rongteng.base.weight.EditTextWithDel;
import com.rongteng.base.weight.TitleView;

/**
 * 修改手机号码
 */
public class UpdateNewPhoneActivity extends BaseActivity {
    protected String TAG = this.getClass().getSimpleName();
    private TitleView title;
    private EditTextWithDel et_verification_code;
    private Button complete;
    private CountdownProgressBar roundProgressBar;
    private Button get_verification_code;
    private RelativeLayout relative_voice;
    private TextView get_voice_code;
    private static final int MINLENGTH = 5;
    private static final int MAXLENGTH = 10;
    private EditTextWithDel et_user_phone;
    private MyTextWatcher textWatcher;
    private GetVerificationCode getCode;
    private String userPhone;
    private String verificationCode;
    private String oldCode;
    private AddSpaceTextWatcher asEditTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_update_new_phone);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(title, true, Color.parseColor(Config.colorPrimary));

    }


    @Override
    protected void initTitle() {
        title = (TitleView) findViewById(R.id.title);
        title.setTitle("修改手机号码");
        Intent intent = getIntent();
        oldCode = intent.getStringExtra("oldCode");
        title.setLeftImageButton(R.drawable.back);
        title.showLeftButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 跳转到 UpdateNewPhoneActivity
     *
     * @param context
     */
    public static void startActivity(Context context, String oldCode) {
        Intent intent = new Intent(context, UpdateNewPhoneActivity.class);
        intent.putExtra("oldCode", oldCode);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {

        et_user_phone = (EditTextWithDel) findViewById(R.id.et_user_phone);
        et_user_phone.setHint("请输入新手机号码");
        complete = (Button) findViewById(R.id.complete);

        et_verification_code = (EditTextWithDel) findViewById(R.id.et_verification_code);
        roundProgressBar = (CountdownProgressBar) findViewById(R.id.roundProgressBar);
        get_verification_code = (Button) findViewById(R.id.get_verification_code);
        //收不到验证码
        relative_voice = (RelativeLayout) findViewById(R.id.relative_voice);
        get_voice_code = (TextView) findViewById(R.id.get_voice_code);
    }

    @Override
    protected void setData() {
    }

    @Override
    protected void setEvent() {
        complete.setOnClickListener(this);
        get_verification_code.setOnClickListener(this);
        get_voice_code.setOnClickListener(this);
        textWatcher = new MyTextWatcher();
        et_verification_code.addTextChangedListener(textWatcher);
        et_user_phone.addTextChangedListener(textWatcher);
        asEditTexts = new AddSpaceTextWatcher(et_user_phone, 13);//手机号
        asEditTexts.setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);
    }


    @Override
    public void onClick(View v) {
        if (v.equals(get_verification_code)) {
            if (getCode == null) {
                getCode = new GetVerificationCode(this, get_verification_code, roundProgressBar, relative_voice, et_verification_code);
            }
            getCode.getVerCode(http, userPhone, "修改手机号码", false);
        } else if (v.equals(complete)) {
            sendHttp();
        } else if (v.equals(get_voice_code)) {
            getCode.getVerCode(http, userPhone, "修改手机号码", true);
        }
    }

    private void sendHttp() {
        params.put("authNo", oldCode);
        params.put("mobile", userPhone);
        params.put("verifyNo", verificationCode);
        complete.setEnabled(false);
        http.post(Config.URL_REVALIDMOBILE, params, "正在修改...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                complete.setEnabled(true);
                if (AbJsonUtil.isSuccess(json)) {
                    ReadApp.mApp.setUserPhone(userPhone);
                    UpdateSuccessActivity.startActivity(UpdateNewPhoneActivity.this, Constant.UPDATE_TYPE_UPDATEPHONE);
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                complete.setEnabled(true);
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
//            userPhone = et_user_phone.getText().toString();
            userPhone = asEditTexts.getTextNotSpace();
            verificationCode = et_verification_code.getText().toString();
            if (verificationCode.length() > MINLENGTH && userPhone.length() > MAXLENGTH) {
                complete.setEnabled(true);
            } else {
                complete.setEnabled(false);
            }
        }
    }
}
