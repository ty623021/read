package com.rongteng.base.webview;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.rongteng.base.bean.InviteFriend;
import com.rongteng.base.main.MyApplication;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbRouterUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.volley.IRequest;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.volley.RequestParams;


/**
 * Created by geek on 2016/9/10.
 * 与h5页面通信
 */
public class JavaScriptObj {

    private Context context;
    private IRequest http;
    private RequestParams params;
    private InviteFriend inviteFriend;
    private String content;

    public JavaScriptObj(Context context) {
        this.context = context;
        http = new IRequest(context);
        params = new RequestParams();
    }

    @JavascriptInterface
    public void toAndroidActivity(String view, String value) {
        if (!AbStringUtil.isEmpty(view)) {
            if (view.equals("register")) {
                toRegisterActivity();
            } else if (view.equals("login")) {
                toLoginActivity();
            } else if (view.equals("inviteFriend")) {
                inviteFriend();
            }
        }
    }

    /**
     * 跳转到首页
     */
    @JavascriptInterface
    public void toMainActivity() {
        if (context != null) {
            AbRouterUtil.routerData(context, "main", "startActivity", "MainActivity");
        }
    }

    /**
     * 跳转到注册页面
     */
    @JavascriptInterface
    public void toRegisterActivity() {
        if (context != null) {
            toMainActivity();
            AbRouterUtil.routerData(context, "main", "startActivity", "RegisterActivity");
        }
    }

    /**
     * 跳转到登录页面
     */
    @JavascriptInterface
    public void toLoginActivity() {
        if (context != null) {
            toMainActivity();
            AbRouterUtil.routerData(context, "main", "startActivity", "LoginActivity");
        }
    }


    /**
     * 实现根据H5传递的参数跳转到对应的APP界面
     *
     * @param view 要跳转的界面 必须是activity的全类名
     *             com.rongteng.Activity.main.MainActivity
     */
    @JavascriptInterface
    public void toActivity(String view) {
        toMainActivity();
        try {
            Class<?> aClass = Class.forName(view);
            Intent intent = new Intent();
            intent.setClass(context, aClass);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取分享的内容
     *
     * @param content
     * @return
     */
    @JavascriptInterface
    public void setShareContent(String content) {
        AbLogUtil.e("ShareContent", content + "");
        this.content = content;
        if (onShareClick != null && !AbStringUtil.isEmpty(content)) {
            onShareClick.onShareClick(content);
        }
    }

    public String getContent() {
        return content;
    }

    @JavascriptInterface
    public void inviteFriend() {
        if (!MyApplication.mApp.hasLogin()) {
            AbToastUtil.showToast(context, "账户没有登录，请登录");
            AbRouterUtil.routerData(context, "main", "startActivity", "LoginActivity");
            return;
        }
        http.post("https://api.xzgjf.com/wd_api/inviteIndex/getInvite", params, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                if (AbJsonUtil.isSuccess(json)) {
                    inviteFriend = (InviteFriend) AbJsonUtil.fromJson(json, InviteFriend.class);
                    if (inviteFriend != null) {
                        MyShareSdk shareSdk = new MyShareSdk(context);
                        shareSdk.showShare(null, inviteFriend.getShareTitle(), inviteFriend.getRecommendUrl(), inviteFriend.getShareContent());
                    } else {
                        AbToastUtil.showToast(context, "数据加载失败");
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

    public interface OnShareClick {
        void onShareClick(String content);
    }

    private OnShareClick onShareClick;

    public void setOnShareClick(OnShareClick onShareClick) {
        this.onShareClick = onShareClick;
    }
}
