package com.jlbeauty.read.activity.userCenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jlbeauty.read.R;
import com.jlbeauty.read.activity.home.MainActivity;
import com.jlbeauty.read.global.Config;
import com.jlbeauty.read.main.ReadApp;
import com.rongteng.base.bean.UserLogin;
import com.rongteng.base.global.Constant;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.utils.AbViewUtil;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.weight.EditTextWithDel;
import com.rongteng.base.weight.TitleView;

/**
 * 用户登录
 */
public class LoginActivity extends BaseActivity {
    protected String TAG = this.getClass().getSimpleName();
    private TitleView title;
    private ImageView img_set;
    private LinearLayout eyeslinear;
    private EditTextWithDel et_user_phone;
    private EditTextWithDel et_user_password;
    private TextView register_login;
    private Button login;
    private TextView forget_pass;
    private String user_phone;//用户名或手机号码
    private String user_password;//用户登录密码
    private CheckBox remember_account;
    private boolean isMain;//判断是否需要跳转到首页
    private boolean isFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(title, true, Color.parseColor(Config.colorPrimary));
    }


    @Override
    protected void initTitle() {
        Intent intent = getIntent();
        isMain = intent.getBooleanExtra("isMain", false);
        isFinish = intent.getBooleanExtra("isFinish", false);
        title = (TitleView) findViewById(R.id.title);
        title.setTitle("登录");
        title.setLeftImageButton(R.drawable.back);
        title.showLeftButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                isFinish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            isFinish();
        }
        return false;
    }

    private void isFinish() {
        if (isFinish) {
            if (isMain) {
                MainActivity.startActivity(LoginActivity.this, 0);
            } else {
                finish();
            }
        } else {
            finish();
        }
    }


    @Override
    protected void initView() {
        img_set = (ImageView) findViewById(R.id.img_set);
        eyeslinear = (LinearLayout) findViewById(R.id.eyes_linear);
        et_user_phone = (EditTextWithDel) findViewById(R.id.et_user_phone);
        et_user_password = (EditTextWithDel) findViewById(R.id.et_user_password);
        et_user_password.setHint("请输入登录密码");
        register_login = (TextView) findViewById(R.id.register_login);
        register_login.getPaint().setUnderlineText(true);
        register_login.setText("免费注册");
        login = (Button) findViewById(R.id.login);
        remember_account = (CheckBox) findViewById(R.id.remember_account);
        forget_pass = (TextView) findViewById(R.id.forget_pass);
    }

    @Override
    protected void setData() {
        if (ReadApp.mApp.getRemember()) {
            remember_account.setChecked(true);
            if (!AbStringUtil.isEmpty(ReadApp.mApp.getUserPhone())) {
                et_user_phone.setText(ReadApp.mApp.getUserPhone());
            }
            AbViewUtil.setEditTextSelection(et_user_phone);
        } else {
            remember_account.setChecked(false);
        }
    }

    @Override
    protected void setEvent() {
        eyeslinear.setOnClickListener(this);
        login.setOnClickListener(this);
        register_login.setOnClickListener(this);
        forget_pass.setOnClickListener(this);
        et_user_phone.addTextChangedListener(new MyTextWatcher());
        et_user_password.addTextChangedListener(new MyTextWatcher());
        remember_account.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ReadApp.mApp.setRemember(isChecked);
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
            user_phone = et_user_phone.getText().toString();
            user_password = et_user_password.getText().toString();
            if (!AbStringUtil.isEmpty(user_phone) && !AbStringUtil.isEmpty(user_password)) {
                login.setEnabled(true);
            } else {
                login.setEnabled(false);
            }
        }
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
        } else if (v.equals(login)) {
            //登录
            if (AbStringUtil.isEmpty(user_phone)) {
                AbToastUtil.showToast(this, "请输入手机号或用户名");
            } else {
                if (user_password.length() < 6) {
                    AbToastUtil.showToast(this, "登录密码6~14位");
                } else {
                    login();
                }
            }

        } else if (v.equals(register_login)) {
            RegisterActivity.startActivity(this);
        } else if (v.equals(forget_pass)) {
            ForgetLoginPassActivity.startObtainMessageActivity(this, "忘记密码");
        }
    }

    /**
     * 请求登录
     */
    private void login() {
        params.put("version", Build.VERSION.RELEASE);
        params.put("model", Build.MODEL);
        params.put("manufacturer", Build.BRAND);
        params.put("username", user_phone);
        params.put("password", user_password);
        params.put("ct", Constant.CLIENT_ID);
        http.post(Config.URL_LOGINON, params, "登录中...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e(TAG, json.toString());
                if (AbJsonUtil.isSuccess(json)) {
                    UserLogin user = (UserLogin) AbJsonUtil.fromJson(json, UserLogin.class);
                    if (user != null) {
                        //保存用户注册信息
                        ReadApp.mApp.saveUserLogin(user);
                        if (isMain) {
                            MainActivity.startActivity(LoginActivity.this, 0);
                        } else {
                            if (onActivityForResult != null) {
                                onActivityForResult.onActivityForResult();
                            }
                            finish();
                        }
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


    /**
     * 跳转到 LoginActivity
     *
     * @param context
     * @param isFinish 是否关闭上一个页面
     */
    public static void startActivity(Activity context, boolean isFinish) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("isFinish", isFinish);
        context.startActivity(intent);
        if (isFinish) {
            context.finish();
        }
    }

    /**
     * 跳转到 LoginActivity
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转到 LoginActivity
     *
     * @param context
     * @param activityForResult 接口回调对象
     */
    public static void startActivity(Context context, OnActivityForResult activityForResult) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        onActivityForResult = activityForResult;
    }

    /**
     * 跳转到 LoginActivity
     * 支持回调 onActivityResult
     *
     * @param activity
     */
    public static void startActivity(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * 跳转到 LoginActivity
     *
     * @param context
     * @param isFinish 是否关闭上一个页面
     * @param isMain   是否跳转到首页
     */
    public static void startActivity(Activity context, boolean isFinish, boolean isMain) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("isMain", isMain);
        intent.putExtra("isFinish", isFinish);
        context.startActivity(intent);
        if (isFinish) {
            context.finish();
        }
    }

    private static OnActivityForResult onActivityForResult;

    /**
     * 解决fragment在4.4版本OnActivityForResult回调接口bug
     */
    public interface OnActivityForResult {
        void onActivityForResult();
    }
}
