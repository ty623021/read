package com.jlbeauty.read.activity.userCenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jlbeauty.read.R;
import com.jlbeauty.read.activity.GuideActivity;
import com.rongteng.base.global.Constant;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.weight.EditTextWithDel;
import com.rongteng.base.weight.TitleView;

/**
 * Created by WIN10 on 2018/3/26.
 * 会员申请
 *
 */

public class VipApplyActivity extends BaseActivity {
    private TitleView titleView;
    private EditTextWithDel et_realName,et_idCard,et_applyCity,et_sex,et_address;
    private Button next_step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_vip_apply);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, false, Color.parseColor(Constant.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("会员申请");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, VipApplyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        et_realName = (EditTextWithDel) findViewById(R.id.et_realName);
        et_idCard = (EditTextWithDel) findViewById(R.id.et_idCard);
        et_applyCity = (EditTextWithDel) findViewById(R.id.et_applyCity);
        et_sex = (EditTextWithDel) findViewById(R.id.et_sex);
        et_address = (EditTextWithDel) findViewById(R.id.et_address);
        next_step = (Button) findViewById(R.id.next_step);
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setEvent() {
        next_step.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(next_step)) {
            AbToastUtil.showToast(context,"下一步");
        }
    }
}
