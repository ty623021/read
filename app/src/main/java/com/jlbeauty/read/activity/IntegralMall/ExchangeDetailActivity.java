package com.jlbeauty.read.activity.IntegralMall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.activity.home.MainActivity;
import com.jlbeauty.read.bean.ExchangeDetailInfo;
import com.jlbeauty.read.bean.IntegralVoucherInfo;
import com.jlbeauty.read.global.Config;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.pullview.AbPullToRefreshView;
import com.rongteng.base.utils.AbImageUtil;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbRefreshUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.utils.AppUtil;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.weight.TitleView;



/**
 * Created by geek on 2016/11/2.
 * 兑换详情页面
 */
public class ExchangeDetailActivity extends BaseActivity implements AbPullToRefreshView.OnHeaderRefreshListener {
    private TitleView titleView;
    private IntegralVoucherInfo info;
    private Button complete;
    private View main;
    private PopupWindow window1;
    private PopupWindow window2;
    private TextView tv_content;
    private TextView tv_remaining_num;
    private TextView tv_title;
    private TextView tv_price;
    private TextView tv_tip;
    private ImageView iv_voucher_icon;
    private int mallIntegral;
    private int number = 1;//默认购买数量为1
    private ExchangeDetailInfo obj;
    private String id;
    private String title;
    private AbPullToRefreshView pull;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_exchange_detail);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));

    }

    public void isMallIntegral() {
        if (number * info.getIntegralWorth() > mallIntegral) {
            tv_tip.setVisibility(View.VISIBLE);
            complete.setEnabled(false);
        } else {
            tv_tip.setVisibility(View.GONE);
            complete.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        if (obj == null) {
            AbToastUtil.showToast(context, "数据加载失败，请重新获取");
            return;
        }
        if (v.equals(complete)) {
            if (number * info.getIntegralWorth() > mallIntegral) {
                AbToastUtil.showToast(context, "积分不足");
                return;
            }
            showPopWindow();
        }
    }


    /**
     * 跳转到 ExchangeDetailActivity
     *
     * @param context
     */
    public static void startActivity(Context context, String title, String id) {
        Intent intent = new Intent(context, ExchangeDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }


    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        if (title != null) {
            titleView.setTitle(title);
        } else {
            titleView.setTitle("兑换详情");
        }
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        pull = (AbPullToRefreshView) findViewById(R.id.pull);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_remaining_num = (TextView) findViewById(R.id.tv_remaining_num);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_voucher_icon = (ImageView) findViewById(R.id.iv_voucher_icon);
        complete = (Button) findViewById(R.id.complete);
        main = findViewById(R.id.main);

        AbRefreshUtil.initRefresh(pull, this);
    }

    @Override
    protected void setData() {
        sendHttp();
    }

    private void sendHttp() {
        params.put("id", id);
        http.post(Config.URL_CONVERT_VOUCHER_DETAILS, params, progressTitle, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("积分兑换详情", json);
                pull.onHeaderRefreshFinish();
                if (AbJsonUtil.isSuccess(json)) {
                    obj = (ExchangeDetailInfo) AbJsonUtil.fromJson(json, ExchangeDetailInfo.class);
                    if (obj != null) {
                        setValues();
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

    private void setValues() {
        info = obj.getIntegralVoucher();
        mallIntegral = obj.getMallIntegral();
        tv_title.setText(info.getVoucherName() + "");
        tv_remaining_num.setText(info.getRemainingInventory() + "");
        tv_price.setText(info.getIntegralWorth() + "");
        if (!AbStringUtil.isEmpty(info.getDescription())) {
            tv_content.setText(Html.fromHtml(info.getDescription()) + "");
        }
        AbImageUtil.glideImageList(info.getImgUrl(), iv_voucher_icon, R.drawable.fail_2);
        isMallIntegral();
    }

    @Override
    protected void setEvent() {
        complete.setOnClickListener(this);
    }


    /**
     * 第一个弹框
     */
    private void showPopWindow() {
        // TODO Auto-generated method stub
        View view = View.inflate(context, R.layout.pay_fragment1, null);

        TitleView titleView = (TitleView) view.findViewById(R.id.title);
        Button bt_pay = (Button) view.findViewById(R.id.bt_pay);
        TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
        tv_price.setText(number * info.getIntegralWorth() + "");
        titleView.setTitle("");
        titleView.setTitleViewColor(Color.WHITE);
        titleView.setTextColor(Color.parseColor("#BCBCBC"));

        titleView.showRightTextButton();

        window1 = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //让pop覆盖在输入法上面
        window1.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //让pop自适应输入状态
        window1.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window1.setFocusable(true);

        // 实例化一个ColorDrawable颜色为白色
        ColorDrawable dw = new ColorDrawable(0xFFFFFFFF);
        window1.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window1.setAnimationStyle(R.style.my_popshow_anim_style);
        //设置在底部显示
        window1.showAtLocation(main, Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);

        //popWindow消失监听方法
        window1.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                AppUtil.backgroundAlpha(ExchangeDetailActivity.this, 1f);
            }
        });
        titleView.setRightTextButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window1.dismiss();
            }
        });

        bt_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exchange();
            }
        });
        AppUtil.backgroundAlpha(ExchangeDetailActivity.this, 0.5f);
    }


    public void exchange() {
        params.put("id", info.getId());
        params.put("amount", number + "");
        http.post(Config.URL_CONVERSION, params, "正在兑换...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("立即兑换", json);
                if (AbJsonUtil.isSuccess(json)) {
                    showPopWindow2();
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
     * 第二个弹框
     */
    private void showPopWindow2() {
        // TODO Auto-generated method stub
        View view = View.inflate(context, R.layout.pay_fragment3, null);
        Button bt_pay = (Button) view.findViewById(R.id.bt_pay);
        TitleView titleView = (TitleView) view.findViewById(R.id.title);
        titleView.setTitle("");
        titleView.setTitleViewColor(Color.WHITE);
        titleView.setTextColor(Color.parseColor("#BCBCBC"));
        titleView.showRightTextButton();

        window2 = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //让pop覆盖在输入法上面
        window2.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //让pop自适应输入状态
        window2.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window2.setFocusable(true);

        // 实例化一个ColorDrawable颜色为白色
        ColorDrawable dw = new ColorDrawable(0xFFFFFFFF);
        window2.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window2.setAnimationStyle(R.style.left_in_bottom_out);
        //设置在底部显示
        window2.showAtLocation(main, Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);
        //popWindow消失监听方法
        window2.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                closeDialog();
                finish();
                AppUtil.backgroundAlpha(ExchangeDetailActivity.this, 1f);
            }
        });
        bt_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
                MainActivity.startActivity(context, 0);
            }
        });
        titleView.setRightTextButton("完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
                finish();
            }
        });
        AppUtil.backgroundAlpha(ExchangeDetailActivity.this, 0.5f);
    }

    private void closeDialog() {
        if (window1 != null) {
            window1.dismiss();
            window1 = null;
        }
        if (window2 != null) {
            window2.dismiss();
            window2 = null;
        }
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        sendHttp();
    }
}
