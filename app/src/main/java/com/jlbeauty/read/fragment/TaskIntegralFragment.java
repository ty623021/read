package com.jlbeauty.read.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.jlbeauty.read.R;
import com.jlbeauty.read.activity.userCenter.InviteFriendActivity;
import com.jlbeauty.read.activity.userCenter.RegisterActivity;
import com.jlbeauty.read.adapter.SignAdapter;
import com.jlbeauty.read.bean.SignDayInfo;
import com.jlbeauty.read.bean.SignInfo;
import com.jlbeauty.read.bean.TaskInfo;
import com.jlbeauty.read.global.Config;
import com.rongteng.base.main.BaseFragment;
import com.rongteng.base.pullview.AbPullToRefreshView;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbRefreshUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.utils.DialogUtil;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.volley.RequestParams;
import com.rongteng.base.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geek on 2016/11/1.
 * 做任务赚积分
 */
public class TaskIntegralFragment extends BaseFragment implements AbPullToRefreshView.OnHeaderRefreshListener {

    private View view;
    private FragmentActivity mActivity;
    private Button do_sign;
    private Button do_invate;
    private Button do_register;
    private View signView;
    private ImageView close;
    private TextView day_sign_integral;
    private TextView invest_integral;
    private TextView invate_integral;
    private TextView bind_bank_integral;
    private TextView register_integral;
    private AbPullToRefreshView pull;
    private TextView luck_draw;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_integral, container, false);
        mActivity = getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        pull = (AbPullToRefreshView) view.findViewById(R.id.pull);
        do_sign = (Button) view.findViewById(R.id.do_sign);
        do_invate = (Button) view.findViewById(R.id.do_invate);
        do_register = (Button) view.findViewById(R.id.do_register);

        day_sign_integral = (TextView) view.findViewById(R.id.day_sign_integral);
        invate_integral = (TextView) view.findViewById(R.id.invate_integral);
        register_integral = (TextView) view.findViewById(R.id.register_integral);
        AbRefreshUtil.initRefresh(pull, this);
    }

    @Override
    protected void setData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        sendHttp();
    }

    private void sendHttp() {
        http.post(Config.DO_TASK_TOEARNINTEGRAL_URL, params, progressTitle, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("json", json);
                pull.onHeaderRefreshFinish();
                if (AbJsonUtil.isSuccess(json)) {
                    TypeToken typeToken = new TypeToken<List<TaskInfo>>() {
                    };
                    List<TaskInfo> list = (List<TaskInfo>) AbJsonUtil.fromJsonList(json, typeToken, "taskIntegral");
                    if (list != null) {
                        progressTitle = null;
                        setValue(list);
                    } else {
                        AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                    }

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
    protected void setEvent() {
        do_sign.setOnClickListener(this);
        do_invate.setOnClickListener(this);
        do_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(close)) {
            DialogUtil.dismiss();
        } else if (v.equals(do_sign)) {
            mallSign();
        } else if (v.equals(do_invate)) {
            InviteFriendActivity.startActivity(context, "邀请好友");
        } else if (v.equals(do_register)) {
            RegisterActivity.startActivity(context);
        }
    }

    /**
     * 积分商城签到
     */
    private void mallSign() {
        RequestParams params = new RequestParams();
        http.post(Config.URL_MALL_SIGN, params, "正在签到...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("json", json);
                if (AbJsonUtil.isSuccess(json)) {
                    SignInfo signInfo = (SignInfo) AbJsonUtil.fromJson(json, SignInfo.class);
                    if (signInfo != null) {
                        do_sign.setText("已签到");
                        do_sign.setEnabled(false);
                        alertSignTip(signInfo);
                    } else {
                        AbToastUtil.showToast(context, AbJsonUtil.getError(json));
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
     * 签到成功提示
     *
     * @param signInfo
     */
    private void alertSignTip(SignInfo signInfo) {
        signView = View.inflate(mActivity, R.layout.sign_success_tip, null);
        close = (ImageView) signView.findViewById(R.id.close);
        Button go_experience = (Button) signView.findViewById(R.id.go_experience);
        go_experience.setVisibility(View.GONE);
        TextView tv_register = (TextView) signView.findViewById(R.id.tv_register);
        TextView experience_money = (TextView) signView.findViewById(R.id.experience_money);
        TextView tv_tip = (TextView) signView.findViewById(R.id.tv_tip);
        if (signInfo.isSign()) {
            tv_register.setText("已签到");
        } else {
            tv_register.setText("签到成功");
        }
        experience_money.setText("获得" + signInfo.getIntegralNumber() + "积分");
        boolean isShow = false;
        if (!AbStringUtil.isEmpty(signInfo.getRemind())) {
            tv_tip.setText(Html.fromHtml(signInfo.getRemind()));
            isShow = true;
        } else {
            tv_tip.setText("已连续签到" + signInfo.getTotalSignNum() + "天");
        }

        GridView gv1 = (GridView) signView.findViewById(R.id.gv1);
        GridView gv2 = (GridView) signView.findViewById(R.id.gv2);
        List<SignDayInfo> list1 = new ArrayList<>();
        List<SignDayInfo> list2 = new ArrayList<>();
        for (int i = 0; i < signInfo.getEveryDayIntegral().size(); i++) {
            SignDayInfo info = signInfo.getEveryDayIntegral().get(i);
            if (i < 4) {
                list1.add(info);
            } else {
                list2.add(info);
            }
        }
        SignAdapter adapter1 = new SignAdapter(context, list1, isShow);
        gv1.setAdapter(adapter1);
        SignAdapter adapter2 = new SignAdapter(context, list2, isShow);
        gv2.setAdapter(adapter2);

        close.setOnClickListener(this);
        go_experience.setOnClickListener(this);
        DialogUtil.showAlertDialog(signView);
    }

    public void setValue(List<TaskInfo> value) {
        for (int i = 0; i < value.size(); i++) {
            TaskInfo info = value.get(i);
            if (i == 0) {
                set(info, do_sign, day_sign_integral);
                if (info.isTrueOrFalse()) {
                    do_sign.setText("已签到");
                } else {
                    do_sign.setText("签到");
                }
            } else if (i == 1) {
                set(info, do_invate, invate_integral);
            }  else if (i == 2) {
                set(info, do_register, register_integral);
                do_register.setText("已完成");
            }
        }
    }

    private void set(TaskInfo info, Button bt, TextView tv) {
        tv.setText(info.getIntegral());
        bt.setEnabled(!info.isTrueOrFalse());//设置是否能点击
    }


    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        sendHttp();
    }


}
