package com.rongteng.base.weight;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.rongteng.base.R;
import com.rongteng.base.utils.DialogUtil;

/**
 * Created by Administrator on 2018/2/8 0008.
 * 温馨提示框
 */
public class TipDialog {
    private Context context;
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvConfirm;
    private View view;

    public TipDialog(Context context) {
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        if (((Activity) context).isFinishing()) {
            return;
        }
        view = LayoutInflater.from(context).inflate(R.layout.dailog_cost_statement, null, false);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        tvConfirm = (TextView) view.findViewById(R.id.tv_sure);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.dismiss();
            }
        });
    }

    public void show() {
        if (view != null)
            DialogUtil.showAlertDialog(view);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTvTitle(String title) {
        if (this.tvTitle != null && title != null) {
            this.tvTitle.setText(title);
        }
    }

    /**
     * 设置内容
     *
     * @param content
     */
    public void setTvContent(String content) {
        if (this.tvContent != null && content != null) {
            this.tvContent.setText(content);
        }
    }

    /**
     * 设置确认按钮
     *
     * @param confirm
     */
    public void setTvConfirm(String confirm) {
        if (this.tvConfirm != null && confirm != null) {
            this.tvConfirm.setText(confirm);
        }
    }
}
