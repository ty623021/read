package com.rongteng.base.weight.keyboardview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.rongteng.base.R;

/**
 * Created by hjy on 2017/8/7.
 */

public class PopEnterVerificationCode extends PopupWindow {

    private OnPasswordInputFinish onFinishInput;

    private PopInputVerificationCodeView pwdView;

    private View mMenuView;

    private Activity mContext;

    public PopEnterVerificationCode(final Activity context) {
        super(context);
        if (context == null || context.isFinishing()) {
            return;
        }
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.pop_enter_verification_code, null);

        pwdView = (PopInputVerificationCodeView) mMenuView.findViewById(R.id.pwd_view);

        //添加密码输入完成的响应
        pwdView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(String password) {
                dismiss();
                if (onFinishInput != null) {
                    onFinishInput.inputFinish(password);
                }
            }
        });

        // 监听X关闭按钮
        pwdView.getImgCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // 监听键盘上方的返回
        pwdView.getVirtualKeyboardView().getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //监听点击重新获取按钮
        pwdView.getTv_refresh().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.pop_add_ainm);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x66000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }

    public void setOnFinishInput(OnPasswordInputFinish onFinishInput) {
        this.onFinishInput = onFinishInput;
    }

    public PopInputVerificationCodeView getPwdView() {
        return pwdView;
    }

}
