package com.jlbeauty.read.weight;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jlbeauty.read.R;
import com.rongteng.base.utils.AppUtil;

/**
 * 提示框
 */
public class AlertDialog extends android.support.v7.app.AlertDialog {
    private TextView tvTitle, tvContent;
    private Button btConfirm, btCancel;
    private Context context;
    private DialogOnClickListener dialogOnClickListener;
    private LinearLayout dialog_linear;
    private float factor = 0.8f;

    public AlertDialog(Context context) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
        Activity activity = (Activity) context;
        if (activity.isFinishing()) {
            return;
        }
        this.show();
    }

    public AlertDialog(Context context, boolean is) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
        Activity activity = (Activity) context;
        if (activity.isFinishing()) {
            return;
        }
        setCancel(is);
        this.show();
    }

    /**
     * 设置dialog 不消失
     *
     * @param is 默认为true 消失，false 不消失
     */
    public void setCancel(boolean is) {
        this.setCancelable(is);// 点击HOME键不消失
        this.setCanceledOnTouchOutside(is);// 设置点击屏幕Dialog不消失
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(context);
        this.setCanceledOnTouchOutside(false);
    }

    private void initView(Context context) {
        if (((Activity) context).isFinishing()) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_layout_alert, null, false);
        dialog_linear = (LinearLayout) view.findViewById(R.id.dialog_linear);
        int windowWith = AppUtil.getWindowWith(context);
        ViewGroup.LayoutParams layoutParams = dialog_linear.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = (int) (windowWith * factor);
            dialog_linear.setLayoutParams(layoutParams);
        }
        tvTitle = (TextView) view.findViewById(R.id.dialog_tv_title);
        tvContent = (TextView) view.findViewById(R.id.dialog_tv_content);
        btConfirm = (Button) view.findViewById(R.id.dialog_bt_confirm);
        btCancel = (Button) view.findViewById(R.id.dialog_bt_cancel);
        setContentView(view);
    }

    /**
     * 设置确定按钮的名称
     *
     * @param Confirm
     */
    public void setBtConfirm(String Confirm) {
        btConfirm.setText(Confirm);
    }

    /**
     * 设置取消按钮的名称
     *
     * @param Cancel
     */
    public void setBtCancel(String Cancel) {
        btCancel.setText(Cancel);
    }

    /**
     * 隐藏取消按钮
     */
    public void hiddenCancel() {
        btCancel.setVisibility(View.GONE);
    }

    public void showCancel() {
        btCancel.setVisibility(View.VISIBLE);
    }

    /**
     * 有标题对话框 提示
     *
     * @param title    标题
     * @param message  提示内容
     * @param listener
     */
    public void showDialog(String title, String message, DialogOnClickListener listener, boolean isLeft) {
        if (tvTitle == null || tvContent == null) {
            return;
        }
        dialogOnClickListener = listener;
        tvTitle.setText(title);
        tvContent.setText(message);
        if (isLeft == true) {
            tvContent.setGravity(Gravity.LEFT);
        }
        if (dialogOnClickListener != null) {
            //确定
            btConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogOnClickListener != null) {
                        dialogOnClickListener.onPositiveClick();
                    }
                }
            });
            //取消
            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogOnClickListener != null) {
                        dialogOnClickListener.onNegativeClick();
                    }
                }
            });
        }
        show();
        double with = context.getResources().getDisplayMetrics().widthPixels * factor;
        this.getWindow().setLayout((int) with, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.getWindow().setWindowAnimations(R.style.dialogAnimationStyle);
    }

    /**
     * 有标题对话框 提示
     *
     * @param title    标题
     * @param message  提示内容
     * @param listener
     */
    public void showDialog(String title, String message, DialogOnClickListener listener) {
        if (tvTitle == null || tvContent == null) {
            return;
        }
        dialogOnClickListener = listener;
        tvTitle.setText(title);
        tvContent.setText(message);
        if (dialogOnClickListener != null) {
            //确定
            btConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogOnClickListener != null) {
                        dialogOnClickListener.onPositiveClick();
                    }
                }
            });
            //取消
            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogOnClickListener != null) {
                        dialogOnClickListener.onNegativeClick();
                    }
                }
            });
        }
        show();
        double with = context.getResources().getDisplayMetrics().widthPixels * factor;
        this.getWindow().setLayout((int) with, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.getWindow().setWindowAnimations(R.style.dialogAnimationStyle);
    }


    public void removeDialog(Context context) {
        try {
            if (context != null) {
                if (this.isShowing()) {
                    this.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Dialog事件的接口.
     */
    public interface DialogOnClickListener {
        void onPositiveClick();

        void onNegativeClick();
    }
}
