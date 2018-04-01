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
import android.widget.TextView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.global.Config;
import com.jlbeauty.read.utils.GetBanner;
import com.rongteng.base.bean.Home_Banner;
import com.rongteng.base.global.Constant;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.utils.AbImageUtil;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.utils.AbViewUtil;
import com.rongteng.base.utils.AddSpaceTextWatcher;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.weight.EditTextWithDel;
import com.rongteng.base.weight.TitleView;

import java.util.List;

/**
 * 注册
 */
public class RegisterActivity extends BaseActivity implements GetBanner.OnBannerClick {
    protected String TAG = this.getClass().getSimpleName();
    private TitleView title;
    private ImageView img_set;
    private LinearLayout eyeslinear;
    private EditTextWithDel et_user_phone;
    private EditTextWithDel et_user_password;
    private TextView register_login;
    private Button next_step;
    private String user_phone;
    private String user_password;
    private GetBanner getBanner;
    private ImageView banner;
    private AddSpaceTextWatcher asEditTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(title, true, Color.parseColor(Config.colorPrimary));

    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        title = (TitleView) findViewById(R.id.title);
        title.setTitle("注册");
        title.setLeftImageButton(R.drawable.back);
        title.showLeftButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void initView() {
        img_set = (ImageView) findViewById(R.id.img_set);
        eyeslinear = (LinearLayout) findViewById(R.id.eyes_linear);
        et_user_phone = (EditTextWithDel) findViewById(R.id.et_user_phone);
        et_user_password = (EditTextWithDel) findViewById(R.id.et_user_password);
        et_user_password.setHint("请设置6~16位密码");
        register_login = (TextView) findViewById(R.id.register_login);
        next_step = (Button) findViewById(R.id.next_step);
        banner = (ImageView) findViewById(R.id.banner);
    }

    @Override
    protected void setData() {
        getBanner = new GetBanner(this, http, Constant.REGISTER_BANNER);
        getBanner.setOnBannerClick(this);
        getBanner.getBanner();
    }

    @Override
    protected void setEvent() {
        eyeslinear.setOnClickListener(this);
        next_step.setOnClickListener(this);
        register_login.setOnClickListener(this);
        et_user_password.addTextChangedListener(new MyTextWatcher());
        et_user_phone.addTextChangedListener(new MyTextWatcher());
        asEditTexts = new AddSpaceTextWatcher(et_user_phone, 13);//银行卡
        asEditTexts.setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);
    }

    private boolean isHidden = true;

    @Override
    public void onClick(View v) {
        if (v.equals(eyeslinear)) {
            if (isHidden) {
                AbViewUtil.setEditTextHidden(isHidden, et_user_password, img_set);
                isHidden = false;
            } else {
                AbViewUtil.setEditTextHidden(isHidden, et_user_password, img_set);
                isHidden = true;
            }
        } else if (v.equals(next_step)) {
            if (AbStringUtil.isEmpty(user_phone)) {
                AbToastUtil.showToast(this, "请输入手机号");
            } else {
                if (AbStringUtil.isMobileNo(user_phone)) {
                    if (AbStringUtil.isEmpty(user_password) || user_password.length() < 6) {
                        AbToastUtil.showToast(this, "请输入6~16密码");
                    } else {
                        checkMobile();
                    }
                } else {
                    AbToastUtil.showToast(this, "手机号码格式错误");
                }
            }
        } else if (v.equals(register_login)) {
            LoginActivity.startActivity(this, true);
        }
    }

    @Override
    public void onBannerClick(List<Home_Banner> list) {
        Home_Banner home_banner = list.get(0);
        AbImageUtil.glideImageList(home_banner.getImgUrl(), banner, R.drawable.default_banner);
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
            user_password = et_user_password.getText().toString();
            if (!AbStringUtil.isEmpty(user_phone) && !AbStringUtil.isEmpty(user_password)) {
                next_step.setEnabled(true);
            } else {
                next_step.setEnabled(false);
            }
        }
    }

    private void checkMobile() {
        params.put("mobile", user_phone);
        http.post(Config.URL_CHECKMOBILE, params, "验证手机号...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                if (AbJsonUtil.isSuccess(json)) {
                    boolean isReg = (boolean) AbJsonUtil.getContent(json, "isReg");
                    if (isReg) {
                        AbToastUtil.showToast(context, "该手机号已注册");
                    } else {
                        RegisterMessageCodeActivity.startRegisterMessageCodeActivity(RegisterActivity.this, user_phone, user_password);
                    }
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
