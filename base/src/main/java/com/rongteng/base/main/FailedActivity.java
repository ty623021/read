package com.rongteng.base.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.rongteng.base.R;
import com.rongteng.base.global.Constant;
import com.rongteng.base.utils.AbRouterUtil;
import com.rongteng.base.weight.TitleView;


/**
 * 投资失败页面
 */
public class FailedActivity extends BaseActivity {
    protected String TAG = this.getClass().getSimpleName();
    private TitleView titleView;
    private Button back;
    private int type;
    private LinearLayout linear;
    private String score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_failed);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Constant.colorPrimary));
    }

    @Override
    protected void initTitle() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", -1);
        score = intent.getStringExtra("score");
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("投资结果");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * 跳转到 ConfirmBidFailedActivity
     *
     * @param context
     */
    public static void startActivity(Context context, String score, int type) {
        Intent intent = new Intent(context, FailedActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("type", type);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    @Override
    protected void initView() {
        back = (Button) findViewById(R.id.back);
        linear = (LinearLayout) findViewById(R.id.linear);
    }

    @Override
    protected void setData() {
        if ("NetLoan".equals(score)) {
            linear.setBackgroundColor(getResources().getColor(R.color.main_color));
        } else if ("financing".equals(score)) {
            linear.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    protected void setEvent() {
        back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.equals(back)) {
            //跳转到个人中心
            AbRouterUtil.routerData(context, "main", "startActivity", "ColligateMainActivity_center");
            finish();
        }
    }
}
