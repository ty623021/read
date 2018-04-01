package com.jlbeauty.read.activity.userCenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.jlbeauty.read.R;
import com.jlbeauty.read.global.Config;
import com.jlbeauty.read.main.ReadApp;
import com.rongteng.base.global.Constant;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.utils.Tool;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.weight.EditTextWithDel;
import com.rongteng.base.weight.TitleView;

/**
 * 修改用户名
 */
public class UpdateNameActivity extends BaseActivity {
    protected String TAG = this.getClass().getSimpleName();
    private TitleView title;
    private EditTextWithDel et_user_name;
    private Button complete;
    private MyTecxtWatcher tecxtWatcher;
    //密码最低位数
    private static final int MINLENGTH = 5;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_update_name);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(title, true, Color.parseColor(Config.colorPrimary));

    }


    @Override
    protected void initTitle() {
        title = (TitleView) findViewById(R.id.title);
        title.setTitle("修改用户名");
        title.setLeftImageButton(R.drawable.back);
        title.showLeftButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 跳转到 UpdateNameActivity
     *
     * @param context
     */
    public static void startUpdateNameActivity(Activity context) {
        Intent intent = new Intent(context, UpdateNameActivity.class);
        context.startActivityForResult(intent, 100);
    }


    @Override
    protected void initView() {
        et_user_name = (EditTextWithDel) findViewById(R.id.et_user_name);
        complete = (Button) findViewById(R.id.complete);
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setEvent() {
        complete.setOnClickListener(this);
        tecxtWatcher = new MyTecxtWatcher();
        et_user_name.addTextChangedListener(tecxtWatcher);
    }


    @Override
    public void onClick(View v) {
        if (v.equals(complete)) {
            if (Tool.isName(userName)) {
                updateName();
            } else {
                AbToastUtil.showToast(context, "用户名由4-15位字母，数字，汉字，下划线组成，不能以下划线和数字开头");
            }
        }
    }

    private void updateName() {
        params.put("userName", userName);
        http.post(Config.URL_UPDATEUSERNAME, params, "正在修改...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e(TAG, json.toString());
                if (AbJsonUtil.isSuccess(json)) {
                    ReadApp.mApp.setUserName(userName);
                    UpdateSuccessActivity.startActivity(UpdateNameActivity.this, Constant.UPDATE_TYPE_UPDATENAME);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class MyTecxtWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            userName = et_user_name.getText().toString();
            if (AbStringUtil.isEmpty(userName)) {
                complete.setEnabled(false);
            } else {
                complete.setEnabled(true);
            }
        }
    }
}
