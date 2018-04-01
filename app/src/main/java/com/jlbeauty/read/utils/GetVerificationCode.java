package com.jlbeauty.read.utils;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.jlbeauty.read.global.Config;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.volley.IRequest;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.volley.RequestParams;
import com.rongteng.base.weight.CountdownProgressBar;
import com.rongteng.base.weight.EditTextWithDel;

/**
 * Created by geek on 2016/7/17.
 * 获取验证码
 */
public class GetVerificationCode {

    private final Activity activity;
    private Button get_verification_code;//获取验证码按钮
    private CountdownProgressBar countdownProgressBar;//倒计时进度条
    private RelativeLayout relativeVoice;//语音验证码的布局
    private GetVerificationCode.onCheckVerify onCheckVerify;//验证验证码的回调接口
    private EditTextWithDel et_verification_code;

    public GetVerificationCode(Activity activity, Button verification_code, CountdownProgressBar countdown, RelativeLayout relative_voice, EditTextWithDel et_verification_code) {
        this.get_verification_code = verification_code;
        this.countdownProgressBar = countdown;
        this.activity = activity;
        this.relativeVoice = relative_voice;
        this.et_verification_code = et_verification_code;
        countdownProgressBar.setShowVoiceTime(20);
        countdownProgressBar.setCountdownListener(new CountdownProgressBar.CountdownListener() {
            @Override
            public void onTheEnd() {
                get_verification_code.setVisibility(View.VISIBLE);
                get_verification_code.setText("重新获取");
                countdownProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onOther() {
                relativeVoice.setVisibility(View.VISIBLE);
            }
        });
    }


    /**
     * 获取验证码
     * 找回登陆密码验证码发送
     *
     * @param userPhone 用户手机号
     * @param isVoice   是否获取语音验证码
     */
    public void getPassWordCode(IRequest http, String userPhone, final boolean isVoice) {
        if (AbStringUtil.isEmpty(userPhone)) {
            AbToastUtil.showToast(activity, "请输入手机号");
            return;
        }
        if (!AbStringUtil.isMobileNo(userPhone)) {
            AbToastUtil.showToast(activity, "手机号码格式错误");
            return;
        }
        RequestParams params = new RequestParams();
        params.put("mobile", userPhone);
        params.put("voice", isVoice + "");
        String title = "短信发送中...";
        if (isVoice) {
            title = "语音发送中...";
        }
        http.post(Config.URL_SENDRETRIEVEPWDCODEON, params, title, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                boolean success = AbJsonUtil.isSuccess(json);
                if (success) {
                    //是否是新用户
                    checkIsVoice(isVoice);
                } else {
                    AbToastUtil.showToast(activity, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                AbToastUtil.showToast(activity, message);
            }
        });
    }


    private void checkIsVoice(boolean isVoice) {
        if (isVoice) {
            //语音验证码
            AbToastUtil.showToast(activity, "请接听021-31590966来电获得验证码");
        } else {
            AbToastUtil.showToast(activity, "短信验证码发送成功");
            get_verification_code.setVisibility(View.GONE);
            countdownProgressBar.setVisibility(View.VISIBLE);
            countdownProgressBar.setAnimProgress(60);
        }
    }

    /**
     * 获取验证码接口
     *
     * @param userPhone 用户手机号
     * @param isVoice   是否获取语音验证码
     * @param operate   中文信息，用于显示在短信上 例如：您正在${operate}，本次的动态验证码为XXXX
     */
    public void getVerCode(IRequest http, String userPhone, String operate, final boolean isVoice) {
        if (AbStringUtil.isEmpty(userPhone)) {
            AbToastUtil.showToast(activity, "请输入手机号");
            return;
        }
        if (!AbStringUtil.isMobileNo(userPhone)) {
            AbToastUtil.showToast(activity, "手机号码格式错误");
            return;
        }
        RequestParams params = new RequestParams();
        params.put("mobile", userPhone);
        params.put("operate", operate);
        params.put("voice", isVoice + "");
        String title = "短信发送中...";
        if (isVoice) {
            title = "语音发送中...";
        }
        http.post(Config.URL_SENDVERIFYON, params, title, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                boolean success = AbJsonUtil.isSuccess(json);
                if (success) {
                    checkIsVoice(isVoice);
                } else {
                    AbToastUtil.showToast(activity, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                AbToastUtil.showToast(activity, message);
            }
        });
    }


    /**
     * 获取验证码
     * 获取注册验证码
     *
     * @param userPhone 用户手机号
     * @param isVoice   是否获取语音验证码
     */
    public void getRegisterCode(IRequest http, String userPhone, final boolean isVoice) {
        if (AbStringUtil.isEmpty(userPhone)) {
            AbToastUtil.showToast(activity, "请输入手机号");
            return;
        }
        if (!AbStringUtil.isMobileNo(userPhone)) {
            AbToastUtil.showToast(activity, "手机号码格式错误");
            return;
        }
        RequestParams params = new RequestParams();
        params.put("mobile", userPhone);
        params.put("voice", isVoice + "");
        String title = "短信发送中...";
        if (isVoice) {
            title = "语音发送中...";
        }
        http.post(Config.URL_SENDVERIFYFORLOGINON, params, title, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                boolean success = AbJsonUtil.isSuccess(json);
                if (success) {
                    checkIsVoice(isVoice);
                } else {
                    AbToastUtil.showToast(activity, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                AbToastUtil.showToast(activity, message);
            }
        });
    }


    /**
     * 验证验证码
     *
     * @param http
     * @param mobileM  手机号
     * @param verifyNo 验证码
     */
    public void checkVerifyOn(IRequest http, String mobileM, String verifyNo, final Button complete) {
        if (!AbStringUtil.isMobileNo(mobileM)) {
            AbToastUtil.showToast(activity, "手机号码格式错误");
            return;
        }
        if (AbStringUtil.isEmpty(verifyNo)) {
            AbToastUtil.showToast(activity, "请输入验证码");
            return;
        }
        RequestParams params = new RequestParams();
        params.put("mobile", mobileM);
        params.put("verifyNo", verifyNo);
        complete.setEnabled(false);
        http.post(Config.URL_CHECKVERIFYON, params, "正在验证短信...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                complete.setEnabled(true);
                if (AbJsonUtil.isSuccess(json)) {
                    if (onCheckVerify != null) {
                        onCheckVerify.onCheckVerify(true);
                    }
                } else {
                    AbToastUtil.showToast(activity, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                complete.setEnabled(true);
                AbToastUtil.showToast(activity, message);
            }
        });
    }

    /**
     * 回调接口
     */
    public interface onCheckVerify {
        void onCheckVerify(boolean is);
    }

    public void setOnCheckVerify(onCheckVerify onCheckVerify) {
        this.onCheckVerify = onCheckVerify;
    }

}
