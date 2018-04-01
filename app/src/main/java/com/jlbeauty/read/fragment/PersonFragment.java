package com.jlbeauty.read.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jlbeauty.read.R;
import com.jlbeauty.read.activity.IntegralMall.IntegralExchangeActivity;
import com.jlbeauty.read.activity.userCenter.AccountSettingActivity;
import com.jlbeauty.read.activity.userCenter.HelpCenterActivity;
import com.jlbeauty.read.activity.userCenter.InviteFriendActivity;
import com.jlbeauty.read.activity.userCenter.LoginActivity;
import com.jlbeauty.read.activity.userCenter.RegisterActivity;
import com.jlbeauty.read.activity.userCenter.SystemSettingsActivity;
import com.jlbeauty.read.activity.userCenter.VipApplyActivity;
import com.jlbeauty.read.global.Config;
import com.jlbeauty.read.main.ReadApp;
import com.rongteng.base.main.BaseFragment;
import com.rongteng.base.pullview.AbPullToRefreshView;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbRefreshUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.volley.RequestListener;

/**
 * Created by WIN10 on 2018/3/21.
 * 个人中心
 */

public class PersonFragment extends BaseFragment implements AbPullToRefreshView.OnHeaderRefreshListener {
    private RelativeLayout asset_detail_relative, mall_relative, help_relative, invite_friends_relative, setting_relative, rl_account;
    private View view;
    private FragmentActivity activity;
    private AbPullToRefreshView pull;
    private LinearLayout not_login_background;
    private Button bt_login;
    private Button bt_register;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_person, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        activity = getActivity();
        return view;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        pull = (AbPullToRefreshView) view.findViewById(R.id.pull);
        asset_detail_relative = (RelativeLayout) view.findViewById(R.id.asset_detail_relative);
        mall_relative = (RelativeLayout) view.findViewById(R.id.mall_relative);
        help_relative = (RelativeLayout) view.findViewById(R.id.help_relative);
        invite_friends_relative = (RelativeLayout) view.findViewById(R.id.invite_friends_relative);
        setting_relative = (RelativeLayout) view.findViewById(R.id.setting_relative);
        rl_account = (RelativeLayout) view.findViewById(R.id.rl_account);
        not_login_background = (LinearLayout) view.findViewById(R.id.not_login_background);

        bt_login = (Button) view.findViewById(R.id.bt_login);
        bt_register = (Button) view.findViewById(R.id.bt_register);

        not_login_background.getBackground().setAlpha(150);
        AbRefreshUtil.initRefresh(pull, this);
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setEvent() {
        asset_detail_relative.setOnClickListener(this);
        mall_relative.setOnClickListener(this);
        help_relative.setOnClickListener(this);
        invite_friends_relative.setOnClickListener(this);
        setting_relative.setOnClickListener(this);
        rl_account.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.equals(bt_login)) {
            LoginActivity.startActivity(activity, false);
            return;
        } else if (v.equals(bt_register)) {
            RegisterActivity.startActivity(context);
            return;
        }
        if (ReadApp.mApp.hasLogin() ) {
            LoginActivity.startActivity(getActivity(), false);
            return;
        }
        if (v.equals(rl_account)) {
            AccountSettingActivity.startActivity(context, "账户设置");
        } else if (v.equals(asset_detail_relative)) {//vip申请
            VipApplyActivity.startActivity(context);
        } else if (v.equals(mall_relative)) {//我的积分
            IntegralExchangeActivity.startActivity(context);
        } else if (v.equals(help_relative)) {//帮助中心
            HelpCenterActivity.startActivity(context);
        } else if (v.equals(invite_friends_relative)) {//邀请好友
            InviteFriendActivity.startActivity(context,"邀请好友");
        } else if (v.equals(setting_relative)) {//系统设置
            SystemSettingsActivity.startActivity(context);
        }
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        sendHttp();
    }

    private void sendHttp() {
        http.post(Config.URL_INFO, params, progressTitle, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("个人中心", json.toString());
                pull.onHeaderRefreshFinish();
                if (AbJsonUtil.isSuccess(json)) {

                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                pull.onHeaderRefreshFinish();
                AbToastUtil.showToast(context, message);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
    }

    private void getUserInfo() {
        if (ReadApp.mApp.isPersonalStatus()) {
            if (ReadApp.mApp.hasLogin()) {
                sendHttp();
                not_login_background.setVisibility(View.GONE);
                setClick(true);
            } else {
                not_login_background.setVisibility(View.GONE);
                setClick(true);
            }
        }
    }

    /**
     * 设置是否能点击
     * @param enable
     */
    private void setClick(boolean enable) {
        pull.setPullRefreshEnable(enable);
        setting_relative.setEnabled(enable);
        asset_detail_relative.setEnabled(enable);
        invite_friends_relative.setEnabled(enable);
        mall_relative.setEnabled(enable);
        help_relative.setEnabled(enable);
    }

}
