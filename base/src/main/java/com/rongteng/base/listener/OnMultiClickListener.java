package com.rongteng.base.listener;

import android.view.View;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/12/11 0011.
 * 防止过快点击造成多次事件
 */

public abstract class OnMultiClickListener implements View.OnClickListener {
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onMultiClick(v);
        }
    }

    public abstract void onMultiClick(View v);
}
