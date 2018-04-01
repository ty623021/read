package com.rongteng.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.rongteng.base.R;

import java.util.List;

public abstract class AbstractChoickDialog2<T> extends Dialog implements
        OnClickListener {

    protected Context mContext;

    protected TextView mTVTitle;
    protected Button mButtonOK;
    protected Button mButtonCancel;
    protected ListView mListView;

    protected List<T> mList;
    protected OnClickListener mOkClickListener;
    protected OnCancelListener mCancelClickListener;

    public AbstractChoickDialog2(Context context, List<T> list) {
        super(context);
        // TODO Auto-generated constructor stub

        mContext = context;
        mList = list;

        initView(mContext);

    }

    protected void initView(Context context) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popwindow_listview_layout2);

        mTVTitle = (TextView) findViewById(R.id.tvTitle);
        mButtonOK = (Button) findViewById(R.id.btnOK);
        mButtonOK.setOnClickListener(this);
        mButtonCancel = (Button) findViewById(R.id.btnCancel);
        mButtonCancel.setOnClickListener(this);

        //设置背景透明
        Window dialogWindow = getWindow();
        ColorDrawable dw = new ColorDrawable(0x00000000);
        dialogWindow.setBackgroundDrawable(dw);

        mListView = (ListView) findViewById(R.id.listView);

    }

    public void setTitle(String title) {
        mTVTitle.setText(title);
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnOK) {
            onButtonOK();
        } else if (i == R.id.btnCancel) {
            onButtonCancel();
        }
    }

    protected void onButtonOK() {
        dismiss();
        if (mOkClickListener != null) {
            mOkClickListener.onClick(this, 0);
        }
    }

    protected void onButtonCancel() {
        dismiss();
        if (mCancelClickListener != null) {
            mCancelClickListener.onCancel(this);
        }
    }

}
