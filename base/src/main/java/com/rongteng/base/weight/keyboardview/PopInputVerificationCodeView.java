package com.rongteng.base.weight.keyboardview;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rongteng.base.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hjy on 2017/8/7.
 */

public class PopInputVerificationCodeView extends RelativeLayout {
    private TextView inputCodeView, verification_tv, tv_time, tv_refresh;
    private boolean textPassWordVisibi = false;

    Context mContext;

    private VirtualKeyboardView virtualKeyboardView;

    private TextView[] tvList;      //用数组保存6个TextView，为什么用数组？

    private GridView gridView;

    private ImageView imgCancel;

    private ArrayList<Map<String, String>> valueList;

    private int currentIndex = -1;    //用于记录当前输入密码格位置

    public PopInputVerificationCodeView(Context context) {
        this(context, null);
    }

    public PopInputVerificationCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        View view = View.inflate(context, R.layout.layout_input_verification_code, null);
        verification_tv = (TextView) view.findViewById(R.id.verification_tv);
        inputCodeView = (TextView) view.findViewById(R.id.tv_forgetPwd);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_refresh = (TextView) view.findViewById(R.id.tv_refresh);
        virtualKeyboardView = (VirtualKeyboardView) view.findViewById(R.id.virtualKeyboardView);
        imgCancel = (ImageView) view.findViewById(R.id.img_cancel);
        gridView = virtualKeyboardView.getGridView();

        initValueList();

        initView(view);

        setupView();

        addView(view);
    }

    private void initView(View view) {
        tvList = new TextView[6];
        tvList[0] = (TextView) view.findViewById(R.id.tv_pass1);
        tvList[1] = (TextView) view.findViewById(R.id.tv_pass2);
        tvList[2] = (TextView) view.findViewById(R.id.tv_pass3);
        tvList[3] = (TextView) view.findViewById(R.id.tv_pass4);
        tvList[4] = (TextView) view.findViewById(R.id.tv_pass5);
        tvList[5] = (TextView) view.findViewById(R.id.tv_pass6);

    }

    // 这里，我们没有使用默认的数字键盘，因为第10个数字不显示.而是空白
    private void initValueList() {

        valueList = new ArrayList<>();

        // 初始化按钮上应该显示的数字
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<String, String>();
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", "");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            } else if (i == 12) {
                map.put("name", "");
            }
            valueList.add(map);
        }
    }

    private void setupView() {
        // 这里、重新为数字键盘gridView设置了Adapter
        KeyBoardAdapter keyBoardAdapter = new KeyBoardAdapter(mContext, valueList);
        gridView.setAdapter(keyBoardAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 11 && position != 9) {    //点击0~9按钮
                    if (currentIndex >= -1 && currentIndex < 5) {      //判断输入位置————要小心数组越界
                        ++currentIndex;
                        tvList[currentIndex].setText(valueList.get(position).get("name"));
                        if (textPassWordVisibi) {
                            tvList[currentIndex].setVisibility(View.VISIBLE);
                        } else {
                            tvList[currentIndex].setVisibility(View.INVISIBLE);
                        }
                    }
                } else {
                    if (position == 11) {      //点击退格键
                        if (currentIndex - 1 >= -1) {      //判断是否删除完毕————要小心数组越界

                            tvList[currentIndex].setText("");

                            tvList[currentIndex].setVisibility(View.VISIBLE);

                            currentIndex--;
                        }
                    }
                }
            }
        });
    }

    //设置监听方法，在第6位输入完成后触发
    public void setOnFinishInput(final OnPasswordInputFinish pass) {
        tvList[5].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    String strPassword = "";//每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱
                    for (int i = 0; i < 6; i++) {
                        strPassword += tvList[i].getText().toString().trim();
                    }
                    pass.inputFinish(strPassword);    //接口中要实现的方法，完成密码输入完成后的响应逻辑
                }
            }
        });
    }

    public VirtualKeyboardView getVirtualKeyboardView() {

        return virtualKeyboardView;
    }

    public ImageView getImgCancel() {
        return imgCancel;
    }

    public TextView getTv_refresh() {
        return tv_refresh;
    }

    /**
     * 设置密码可见
     */
    public void setVerificationVisibility() {
        for (TextView textView : tvList) {
            textView.setInputType(InputType.TYPE_NULL);
        }
        textPassWordVisibi = true;
    }

    /**
     * 设置“请输入正确的手机验证码”描述
     *
     * @param code
     */
    public void setCode(String code) {
        inputCodeView.setText(code);
    }

    /**
     * 设置“请输入正确的手机验证码”是否显示
     *
     * @param visibility
     */
    public void setIsCodeRightVisibility(int visibility) {
        inputCodeView.setVisibility(visibility);
    }

    /**
     * 设置输入框上面的提示语
     *
     * @param tvVerification
     */
    public void setInputVerificationCodeContent(String tvVerification) {
        verification_tv.setText(tvVerification);
    }

    private int currTime = 120;//当前倒计时
    private static final int INTERVAL = 1;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case INTERVAL:
                    if (mContext == null || ((Activity) mContext).isFinishing()) {
                        mHandler.removeMessages(INTERVAL);
                        return;
                    }
                    if (currTime > 0) {
                        currTime--;
                        tv_time.setText("(" + currTime + "s)");
                        mHandler.sendEmptyMessageDelayed(INTERVAL, 1000);
                    } else {
                        mHandler.removeMessages(INTERVAL);
                        tv_time.setVisibility(GONE);
                        tv_refresh.setVisibility(VISIBLE);
                    }
                    break;
            }
        }
    };

    public void openCountdown() {
        tv_time.setVisibility(VISIBLE);
        tv_refresh.setVisibility(GONE);
        mHandler.sendEmptyMessageDelayed(INTERVAL, 1);
    }

    /**
     * 设置倒计时时长
     *
     * @param currTime
     */
    public void setCurrTime(int currTime) {
        this.currTime = currTime;
    }


}
