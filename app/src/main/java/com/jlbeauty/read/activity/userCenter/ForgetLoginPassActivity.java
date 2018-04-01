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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.global.Config;
import com.jlbeauty.read.utils.GetVerificationCode;

import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AddSpaceTextWatcher;
import com.rongteng.base.weight.CountdownProgressBar;
import com.rongteng.base.weight.EditTextWithDel;
import com.rongteng.base.weight.TitleView;

/**
 * 忘记登录密码
 */
public class ForgetLoginPassActivity extends BaseActivity implements GetVerificationCode.onCheckVerify {
    protected String TAG = this.getClass().getSimpleName();
    private TitleView title;
    private EditTextWithDel et_verification_code;
    private EditTextWithDel et_user_phone;
    private CountdownProgressBar roundProgressBar;
    private Button get_verification_code;
    private RelativeLayout relative_voice;
    private TextView get_voice_code;
    private Button next_step;
    private String user_phone;
    private String verification_code;
    private GetVerificationCode getVerCode;
    private AddSpaceTextWatcher asEditTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_obtain_message);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(title, true, Color.parseColor(Config.colorPrimary));

    }


    /**
     * 跳转到ObtainMessageActivity
     *
     * @param context
     * @param title   标题
     */
    public static void startObtainMessageActivity(Activity context, String title) {
        Intent intent = new Intent(context, ForgetLoginPassActivity.class);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }


    @Override
    protected void initTitle() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        this.title = (TitleView) findViewById(R.id.title);
        if (title != null) {
            this.title.setTitle(title);
        } else {
            this.title.setTitle("忘记密码");
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
        et_verification_code = (EditTextWithDel) findViewById(R.id.et_verification_code);
        et_user_phone = (EditTextWithDel) findViewById(R.id.et_user_phone);
        roundProgressBar = (CountdownProgressBar) findViewById(R.id.roundProgressBar);
        get_verification_code = (Button) findViewById(R.id.get_verification_code);
        //收不到验证码
        relative_voice = (RelativeLayout) findViewById(R.id.relative_voice);
        get_voice_code = (TextView) findViewById(R.id.get_voice_code);
        next_step = (Button) findViewById(R.id.next_step);

    }

    @Override
    protected void setData() {
    }

    @Override
    protected void setEvent() {
        MyTextWatcher textWatcher = new MyTextWatcher();
        asEditTexts = new AddSpaceTextWatcher(et_user_phone, 13);//手机号
        asEditTexts.setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);
        et_verification_code.addTextChangedListener(textWatcher);
        et_user_phone.addTextChangedListener(textWatcher);
        get_verification_code.setOnClickListener(this);
        get_voice_code.setOnClickListener(this);
        next_step.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (getVerCode == null) {
            getVerCode = new GetVerificationCode(this, get_verification_code, roundProgressBar, relative_voice, et_verification_code);
        }
        if (v.equals(get_verification_code)) {
            getVerCode.getPassWordCode(http, user_phone, false);
        } else if (v.equals(get_voice_code)) {
            getVerCode.getPassWordCode(http, user_phone, true);
        } else if (v.equals(next_step)) {
            getVerCode.setOnCheckVerify(this);
            getVerCode.checkVerifyOn(http, user_phone, verification_code, next_step);
        }

    }

    @Override
    public void onCheckVerify(boolean is) {
        if (is) {
            ResetPasswordActivity.startActivity(this, "重置密码", user_phone, verification_code);
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
            user_phone = asEditTexts.getTextNotSpace();
            verification_code = et_verification_code.getText().toString();
            if (!AbStringUtil.isEmpty(user_phone) && !AbStringUtil.isEmpty(verification_code)) {
                next_step.setEnabled(true);
            } else {
                next_step.setEnabled(false);
            }
        }
    }

}
