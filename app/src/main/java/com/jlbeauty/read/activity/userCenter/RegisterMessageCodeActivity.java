package com.jlbeauty.read.activity.userCenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.activity.home.MainActivity;
import com.jlbeauty.read.global.Config;

import com.jlbeauty.read.main.ReadApp;
import com.jlbeauty.read.utils.GetBanner;
import com.jlbeauty.read.utils.GetVerificationCode;

import com.rongteng.base.bean.Home_Banner;
import com.rongteng.base.bean.UserLogin;
import com.rongteng.base.global.Constant;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.utils.AbImageUtil;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.utils.AppUtil;
import com.rongteng.base.utils.DialogUtil;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.weight.CountdownProgressBar;
import com.rongteng.base.weight.EditTextWithDel;
import com.rongteng.base.weight.TitleView;
import com.umeng.analytics.AnalyticsConfig;

import java.util.List;


/**
 * 注册验证码
 */
public class RegisterMessageCodeActivity extends BaseActivity implements GetBanner.OnBannerClick {
    private static String user_phone;//手机号
    private static String user_password;//密码
    protected String TAG = this.getClass().getSimpleName();
    private TitleView title;
    private EditTextWithDel et_invitation_code;
    private EditTextWithDel et_verification_code;
    private TextView invitation_code;
    private TextView register_protocol;
    private Button complete;
    private LinearLayout invitation_code_linear;
    private CountdownProgressBar roundProgressBar;
    private Button get_verification_code;
    private ImageView img_invitation_code;
    private View view;
    private TextView close;
    private TextView Call;
    private RelativeLayout relative_voice;
    private TextView get_voice_code;
    private String verification_code;//手机验证码
    private String invitation_code1;//邀请码
    private TextView not_sms_tip;
    private GetBanner getBanner;
    private ImageView banner;
    private GetVerificationCode getVerCode;
    private TextView register_risk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_message);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(title, true, Color.parseColor(Config.colorPrimary));

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

    /**
     * 跳转到 RegisterMessageCodeActivity
     *
     * @param context
     * @param phone   手机号
     * @param pass    密码
     */
    public static void startRegisterMessageCodeActivity(Context context, String phone, String pass) {
        user_phone = phone;
        user_password = pass;
        Intent intent = new Intent(context, RegisterMessageCodeActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void initView() {
        et_verification_code = (EditTextWithDel) findViewById(R.id.et_verification_code);
        et_invitation_code = (EditTextWithDel) findViewById(R.id.et_invitation_code);
        register_protocol = (TextView) findViewById(R.id.register_protocol);
        register_risk = (TextView) findViewById(R.id.register_risk);
        invitation_code = (TextView) findViewById(R.id.invitation_code);
        invitation_code_linear = (LinearLayout) findViewById(R.id.invitation_code_linear);
        complete = (Button) findViewById(R.id.complete);
        roundProgressBar = (CountdownProgressBar) findViewById(R.id.roundProgressBar);
        get_verification_code = (Button) findViewById(R.id.get_verification_code);
        img_invitation_code = (ImageView) findViewById(R.id.img_invitation_code);
        not_sms_tip = (TextView) findViewById(R.id.not_sms_tip);
        //收不到验证码
        relative_voice = (RelativeLayout) findViewById(R.id.relative_voice);
        get_voice_code = (TextView) findViewById(R.id.get_voice_code);
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
        complete.setOnClickListener(this);
        register_protocol.setOnClickListener(this);
        invitation_code.setOnClickListener(this);
        get_verification_code.setOnClickListener(this);
        img_invitation_code.setOnClickListener(this);
        get_voice_code.setOnClickListener(this);
        not_sms_tip.setOnClickListener(this);
        register_risk.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
       if (v.equals(invitation_code) || v.equals(img_invitation_code)) {
            if (invitation_code_linear.getVisibility() == View.VISIBLE) {
                invitation_code_linear.setVisibility(View.GONE);
                img_invitation_code.setImageResource(R.drawable.add_open);
            } else if (invitation_code_linear.getVisibility() == View.GONE) {
                invitation_code_linear.setVisibility(View.VISIBLE);
                img_invitation_code.setImageResource(R.drawable.hint);
            }
        } else if (v.equals(close)) {
            DialogUtil.dismiss();
        } else if (v.equals(Call)) {
            AppUtil.call(this, Constant.CUSTOMER_SERVICE_PHONE);
            DialogUtil.dismiss();
        } else if (v.equals(complete)) {
            verification_code = et_verification_code.getText().toString();
            if (AbStringUtil.isEmpty(verification_code)) {
                AbToastUtil.showToast(this, "请输入验证码");
            } else {
                register();
            }
        } else if (v.equals(get_verification_code)) {
            //获取短信验证码
            if (getVerCode == null) {
                getVerCode = new GetVerificationCode(this, get_verification_code, roundProgressBar, relative_voice, et_verification_code);
            }
            getVerCode.getRegisterCode(http, user_phone, false);
        } else if (v.equals(get_voice_code)) {
            //获取语音验证码
            getVerCode.getRegisterCode(http, user_phone, true);
        }
    }


    /**
     * 请求注册
     */
    private void register() {
        complete.setEnabled(false);
        params.put("mobile", user_phone);
        params.put("password", user_password);
        params.put("verifyNo", verification_code);
        invitation_code1 = et_invitation_code.getText().toString();
        if (!AbStringUtil.isEmpty(invitation_code1)) {
            params.put("referrer", invitation_code1);
        }
        params.put("marketChannel", AnalyticsConfig.getChannel(context));
        params.put("ct", Constant.CLIENT_ID);
        http.post(Config.URL_REGISTERON, params, "注册中...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e(TAG, json.toString());
                complete.setEnabled(true);
                if (AbJsonUtil.isSuccess(json)) {
                    UserLogin user = (UserLogin) AbJsonUtil.fromJson(json, UserLogin.class);
                    if (user != null) {
                        //保存用户注册信息
                        ReadApp.mApp.saveUserLogin(user);
                        MainActivity.startActivity(RegisterMessageCodeActivity.this, 0);
                    } else {
                        AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                    }
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

    @Override
    public void onBannerClick(List<Home_Banner> list) {
        Home_Banner home_banner = list.get(0);
        AbImageUtil.glideImageList(home_banner.getImgUrl(), banner, R.drawable.default_banner);
    }
}
