package com.jlbeauty.read.activity.userCenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.global.Config;
import com.rongteng.base.bean.InviteFriend;
import com.rongteng.base.global.Constant;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.pullview.AbPullToRefreshView;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbMathUtil;
import com.rongteng.base.utils.AbRefreshUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.utils.AppUtil;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.webview.MyShareSdk;
import com.rongteng.base.weight.TitleView;

/**
 * 新的邀请好友的页面
 * Created by hjy on 2017/2/15.
 */
public class InviteFriendActivity extends BaseActivity implements AbPullToRefreshView.OnHeaderRefreshListener {
    private TitleView titleView;
    private TextView tv_invite_code;
    private InviteFriend inviteFriend;

    private TextView tv_copy;
    private TextView tv_people_amount;
    private TextView tv_money_amount;
    private Button btn_invite;
    private View view, main;
    private LinearLayout qrcode, wechat_friends, wechat_circle, qq_friends, sinaweibo_friends, qq_zone;
    private MyShareSdk shareSdk;
    private TextView tv_reward_rules;
    private TextView my_invite;
    private RelativeLayout notice_relative_network;
    private ImageView network_img;
    private ImageView nodata_img;
    private AbPullToRefreshView pull;
    private LinearLayout amount_linear;
    private LinearLayout money_linear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_invite_friends);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));

    }


    /**
     * 跳转到 InviteFriendActivity
     *
     * @param context
     * @param title   标题
     */
    public static void startActivity(Context context, String title) {
        Intent intent = new Intent(context, InviteFriendActivity.class);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("邀请好友");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void initView() {
        main = findViewById(R.id.main);
        tv_invite_code = (TextView) findViewById(R.id.invite_code);

        notice_relative_network = (RelativeLayout) findViewById(R.id.notice_relative_network);
        network_img = (ImageView) findViewById(R.id.network_img);
        nodata_img = (ImageView) findViewById(R.id.nodata_img);
        pull = (AbPullToRefreshView) findViewById(R.id.pull);

        tv_copy = (TextView) findViewById(R.id.copys);
        tv_people_amount = (TextView) findViewById(R.id.people_amount);
        tv_money_amount = (TextView) findViewById(R.id.money_amount);
        btn_invite = (Button) findViewById(R.id.invite);

        tv_reward_rules = (TextView) findViewById(R.id.reward_rules);
        my_invite = (TextView) findViewById(R.id.my_invite);

        amount_linear = (LinearLayout) findViewById(R.id.amount_linear);
        money_linear = (LinearLayout) findViewById(R.id.money_linear);

        //初始化下拉刷新
        AbRefreshUtil.initRefresh(pull, this);
    }

    @Override
    protected void setData() {
        sendHttp();
    }

    private void sendHttp() {
        http.post(Config.URL_GETINVITE, params, progressTitle, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e(TAG, json.toString());
                if (AbJsonUtil.isSuccess(json)) {
                    inviteFriend = (InviteFriend) AbJsonUtil.fromJson(json, InviteFriend.class);
                    setValue();
                    progressTitle = null;
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
                AbRefreshUtil.hintView(inviteFriend, false, notice_relative_network, nodata_img, network_img, pull);
            }

            @Override
            public void requestError(String message) {
                AbToastUtil.showToast(context, message);
                AbRefreshUtil.hintView(inviteFriend, true, notice_relative_network, nodata_img, network_img, pull);
            }
        });
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        sendHttp();
    }

    private void setValue() {
        tv_invite_code.setText(inviteFriend.getRecommendCode());
        tv_people_amount.setText(inviteFriend.getCount() + "");
        String reward = AbMathUtil.roundStr(inviteFriend.getReward(), Constant.ROUND_DIGIT);
        tv_money_amount.setText(reward);
    }


    @Override
    protected void setEvent() {
        tv_copy.setOnClickListener(this);
        btn_invite.setOnClickListener(this);
        tv_reward_rules.setOnClickListener(this);
        my_invite.setOnClickListener(this);
        notice_relative_network.setOnClickListener(this);
        amount_linear.setOnClickListener(this);
        money_linear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(tv_copy)) {
            AbStringUtil.copy(tv_invite_code.getText().toString(), this);
            AbToastUtil.showToast(this, "复制成功！");
        } else if (view.equals(btn_invite)) {
            showPopWindow();
        } else if (view.equals(my_invite)) {
            InviteNoteActivity.startActivity(this, 0);
        } else if (view.equals(notice_relative_network)) {
            notice_relative_network.setVisibility(View.GONE);
            progressTitle = Constant.LOADING;
            pull.headerRefreshing();
        }

        if (inviteFriend == null) {
            return;
        } else {
            if (shareSdk == null) {
                shareSdk = new MyShareSdk(context);
            }
            if (view.equals(wechat_friends)) {
                shareSdk.showShare("Wechat", inviteFriend.getShareTitle(), inviteFriend.getRecommendUrl(), inviteFriend.getShareContent());
            } else if (view.equals(wechat_circle)) {
                shareSdk.showShare("WechatMoments", inviteFriend.getShareTitle(), inviteFriend.getRecommendUrl(), inviteFriend.getShareContent());
            } else if (view.equals(qq_friends)) {
                shareSdk.showShare("QQ", inviteFriend.getShareTitle(), inviteFriend.getRecommendUrl(), inviteFriend.getShareContent());
            } else if (view.equals(sinaweibo_friends)) {
                shareSdk.showShare("SinaWeibo", inviteFriend.getShareTitle(), inviteFriend.getRecommendUrl(), inviteFriend.getShareContent());
            } else if (view.equals(qq_zone)) {
                shareSdk.showShare("QZone", inviteFriend.getShareTitle(), inviteFriend.getRecommendUrl(), inviteFriend.getShareContent());
            }
        }
    }

    private void showPopWindow() {
        // TODO Auto-generated method stub
        view = LayoutInflater.from(this).inflate(R.layout.share_dailog, null);
        wechat_friends = (LinearLayout) view.findViewById(R.id.wechat_friends);
        wechat_circle = (LinearLayout) view.findViewById(R.id.wechat_circle);
        qq_friends = (LinearLayout) view.findViewById(R.id.qq_friends);
        sinaweibo_friends = (LinearLayout) view.findViewById(R.id.sinaweibo_friends);
        qq_zone = (LinearLayout) view.findViewById(R.id.qq_zone);
        qrcode = (LinearLayout) view.findViewById(R.id.qrcode);
        wechat_friends.setOnClickListener(this);
        wechat_circle.setOnClickListener(this);
        qq_friends.setOnClickListener(this);
        sinaweibo_friends.setOnClickListener(this);
        qq_zone.setOnClickListener(this);
        qrcode.setOnClickListener(this);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00808080);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.my_popshow_anim_style);
        //设置在底部显示
        window.showAtLocation(main, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                AppUtil.backgroundAlpha(InviteFriendActivity.this, 1f);
            }
        });
        AppUtil.backgroundAlpha(InviteFriendActivity.this, 0.5f);

    }

}
