package com.jlbeauty.read.activity.userCenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.global.Config;
import com.jlbeauty.read.main.ReadApp;
import com.rongteng.base.bean.UserAndBankInfo;
import com.rongteng.base.global.Constant;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.pullview.AbPullToRefreshView;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbRefreshUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.weight.TitleView;

/**
 * 账户设置
 */
public class AccountSettingActivity extends BaseActivity implements AbPullToRefreshView.OnHeaderRefreshListener {
    protected String TAG = this.getClass().getSimpleName();
    private TitleView title;
    private TextView find_deal_pass;
    private TextView update_deal_pass;
    private RelativeLayout update_login_pass;
    private RelativeLayout account_user_phone_relative;
    private RelativeLayout account_user_name_relative;
    private LinearLayout singOut;//退出
    private UserAndBankInfo info;
    private TextView userName;
    private TextView userPhone;
    private TextView userRealName;
    private TextView idCard;
    private ImageView network_img;
    private ImageView nodata_img;
    private RelativeLayout network;
    private AbPullToRefreshView pull;
    private RelativeLayout userRealName_relative;//实名认证
    private RelativeLayout account_user_email_relative;
    private TextView userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_account_setting);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(title, true, Color.parseColor(Config.colorPrimary));

    }

    /**
     * @param context
     * @param title   标题
     */
    public static void startActivity(Context context, String title) {
        Intent intent = new Intent(context, AccountSettingActivity.class);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        title = (TitleView) findViewById(R.id.title);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        if (title != null) {
            this.title.setTitle(title);
        } else {
            this.title.setTitle("账户设置");
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
        network_img = (ImageView) findViewById(R.id.network_img);
        nodata_img = (ImageView) findViewById(R.id.nodata_img);
        network = (RelativeLayout) findViewById(R.id.notice_relative_network);
        pull = (AbPullToRefreshView) findViewById(R.id.pull);
        find_deal_pass = (TextView) findViewById(R.id.find_deal_pass);
        update_deal_pass = (TextView) findViewById(R.id.update_deal_pass);
        update_login_pass = (RelativeLayout) findViewById(R.id.update_login_pass);
        account_user_phone_relative = (RelativeLayout) findViewById(R.id.account_user_phone_relative);
        account_user_email_relative = (RelativeLayout) findViewById(R.id.account_user_email_relative);
        account_user_name_relative = (RelativeLayout) findViewById(R.id.account_user_name_relative);
        userRealName_relative = (RelativeLayout) findViewById(R.id.userRealName_relative);
        userName = (TextView) findViewById(R.id.userName);
        userEmail = (TextView) findViewById(R.id.userEmail);
        userPhone = (TextView) findViewById(R.id.userPhone);
        userRealName = (TextView) findViewById(R.id.userRealName);
        idCard = (TextView) findViewById(R.id.idCard);
        singOut = (LinearLayout) findViewById(R.id.singOut);
        AbRefreshUtil.initRefresh(pull, this);
    }


    @Override
    protected void setData() {
        sendHttp();
    }

    private void sendHttp() {
        params.put("memberId", ReadApp.mApp.getID());
        http.post(Config.URL_GETMEMBERCERTIINFO, params, progressTitle, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e(TAG, json.toString());
                if (AbJsonUtil.isSuccess(json)) {
                    info = (UserAndBankInfo) AbJsonUtil.fromJson(json, UserAndBankInfo.class);
                    if (info != null) {
                        progressTitle = null;
                        setValues(info);
                    }
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
                AbRefreshUtil.hintView(info, false, network, nodata_img, network_img, pull);
            }

            @Override
            public void requestError(String message) {
                AbRefreshUtil.hintView(info, true, network, nodata_img, network_img, pull);
                AbToastUtil.showToast(context, message);
            }
        });
    }

    private void setValues(UserAndBankInfo info) {
        if (info.isIdCardStatus()) {
            String realName = AbStringUtil.hideRealName(info.getRealName());
            userRealName.setText(realName);
            String idCard = AbStringUtil.hideIdCard(info.getIdCard());
            this.idCard.setText("(" + idCard + ")");
            userRealName_relative.setVisibility(View.VISIBLE);
        } else {
            userRealName_relative.setVisibility(View.GONE);
        }

        if (AbStringUtil.isEmpty(info.getUsername())) {
            userName.setText(ReadApp.mApp.getUserNmae());
        } else {
            userName.setText(info.getUsername());
            //保存用户名
            ReadApp.mApp.setUserName(info.getUsername());
        }

        if (AbStringUtil.isEmpty(info.getMemberMobile())) {
            String phone = AbStringUtil.hideUserPhone(ReadApp.mApp.getUserPhone());
            userPhone.setText(phone);
        } else {
            String phone = AbStringUtil.hideUserPhone(info.getMemberMobile());
            userPhone.setText(phone);
            //保存用户手机号
            ReadApp.mApp.setUserPhone(info.getMemberMobile());
        }
        if (info.isEmailStatus()) {
            userEmail.setText(info.getEmail() + "");
        } else {
            userEmail.setText("未绑定");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void setEvent() {
        network_img.setOnClickListener(this);
        nodata_img.setOnClickListener(this);
        singOut.setOnClickListener(this);
        find_deal_pass.setOnClickListener(this);
        update_deal_pass.setOnClickListener(this);
        update_login_pass.setOnClickListener(this);
        account_user_phone_relative.setOnClickListener(this);
        account_user_name_relative.setOnClickListener(this);
        account_user_email_relative.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(update_login_pass)) {
            UpdateLoginPassActivity.startUpdateLoginPassActivity(this);
        } else if (v.equals(account_user_phone_relative)) {
            UpdateOldPhoneActivity.startActivity(this);
        } else if (v.equals(account_user_email_relative)) {
            if (info.isEmailStatus()) {
                ReBindEmailActivity.startActivity(this, info.getEmail());
            } else {
                EmailActivity.startActivity(this, info.isEmailStatus());
            }
        } else if (v.equals(account_user_name_relative)) {
            if (info.isHaveUpdateUserName()) {
                AbToastUtil.showToast(context, "您已修改过用户名，不能再次修改!");
            } else {
                UpdateNameActivity.startUpdateNameActivity(this);
            }
        } else if (v.equals(network_img)) {
            network_img.setVisibility(View.GONE);
            pull.headerRefreshing();
            progressTitle = Constant.LOADING;
        } else if (v.equals(nodata_img)) {
            nodata_img.setVisibility(View.GONE);
            pull.headerRefreshing();
            progressTitle = Constant.LOADING;
        }
    }


    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        sendHttp();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
