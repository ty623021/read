package com.rongteng.base.utils;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.rongteng.base.R;


/**
 * Created by geek on 2016/11/11.
 * SwipeRefresh 下拉刷新
 */
public class AbSwipeRefresh {

    public static void setSwipeRefresh(Context context, SwipeRefreshLayout swipe, SwipeRefreshLayout.OnRefreshListener refreshListener) {
        swipe.setColorSchemeResources(R.color.button_color);
        swipe.setSize(SwipeRefreshLayout.DEFAULT);
        swipe.setProgressBackgroundColor(R.color.white);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        swipe.setProgressViewOffset(true, -50, 100);
//        swipe.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, context.getResources()
//                .getDisplayMetrics()));
        swipe.setOnRefreshListener(refreshListener);
        swipe.setRefreshing(true);
    }
}
