package com.rongteng.base.main;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;

import com.rongteng.base.db.UserActionDao;
import com.rongteng.base.dbentity.UserAction;
import com.rongteng.base.utils.AbDateUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AppUtil;
import com.rongteng.base.weight.TitleView;

import java.text.SimpleDateFormat;
import java.util.Date;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class BaseTranslucentActivity extends FragmentActivity {
    private String className = this.getClass().getSimpleName();
    private UserAction userAction;
    private long s, e;
    private long startTime_WebPage = 0;//h5起始时间
    public String pageDescribe = "";//页面描述
    private UserAction userAction_WebPage;//h5操作对象
    private String stop_pageDescribe = "";//在webviewActivity中的最后一个页面的标题

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        if ("WelcomeActivity".equals(className)) {
            pageDescribe = "欢迎页";
        }
        //判断版本,如果[4.4,5.0)就设置状态栏和导航栏为透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置虚拟导航栏为透明
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @SuppressLint("NewApi")
    public void setOrChangeTranslucentColor(TitleView toolbar, boolean is, int translucentPrimaryColor) {
        pageDescribe = toolbar.getTitle();
        if ("GuideActivity".equals(className)) {
            pageDescribe = "引导页";
        } else if ("MainActivity".equals(className)) {
            pageDescribe = "首页";
        } else if ("WebViewActivity".equals(className)) {
            pageDescribe = "H5页";
        }
        //判断版本,如果[4.4,5.0)就设置状态栏和导航栏为透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (toolbar != null) {
                //1.先设置toolbar的高度
                //获取自定义导航的LayoutParams
                LayoutParams params = toolbar.getLayoutParams();
//                获取系统导航栏的高度
                int statusBarHeight = getStatusBarHeight(this);
                if (is) {//如果页面有自定义导航，计算出导航的高度
                    int[] screenDispaly = AppUtil.getScreenDispaly(this);
                    int height = screenDispaly[1];
                    int titleHeight = (int) (height * 0.07);
                    params.height = titleHeight;
                } else {//没有导航，设置为0
                    params.height = 0;
                }
                // 把系统导航的高度和自定义导航栏的高度相加
                params.height += statusBarHeight;
                //设置自定义导航栏的LayoutParams
                toolbar.setLayoutParams(params);
                //2.设置paddingTop，以达到状态栏不遮挡toolbar的内容。
                toolbar.setPadding(toolbar.getPaddingLeft(),
                        toolbar.getPaddingTop() + statusBarHeight,
                        toolbar.getPaddingRight(),
                        toolbar.getPaddingBottom());
                //设置顶部的颜色
                toolbar.setBackgroundColor(translucentPrimaryColor);
            }
//            if (bottomNavigationBar != null) {
//                //解决低版本4.4+的虚拟导航栏的
//                if (hasNavigationBarShow(getWindowManager())) {
//                    LayoutParams p = bottomNavigationBar.getLayoutParams();
//                    p.height += getNavigationBarHeight(this);
//                    bottomNavigationBar.setLayoutParams(p);
//                    //设置底部导航栏的颜色
//                    bottomNavigationBar.setBackgroundColor(translucentPrimaryColor);
//                }
//            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(translucentPrimaryColor);
        } else {
            //<4.4的，不做处理
        }
    }


    /**
     * 解决4.4-5.0系统隐藏、显示顶部导航时，显示bug
     *
     * @param titleView
     * @param isShow
     */
    public void setTitleViewHeight(TitleView titleView, boolean isShow) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (titleView != null) {
                //1.先设置titleView的高度
                //获取自定义导航的LayoutParams
                LayoutParams params = titleView.getLayoutParams();
//                获取系统导航栏的高度
                int statusBarHeight = getStatusBarHeight(this);
                int[] screenDispaly = AppUtil.getScreenDispaly(this);
                int height = screenDispaly[1];
                int titleHeight = (int) (height * 0.07);
                params.height = titleHeight;
                // 把系统导航的高度和自定义导航栏的高度相加
                if (isShow) {
                    titleView.setVisibility(View.VISIBLE);
                    params.height += statusBarHeight;
                } else {
                    titleView.setVisibility(View.INVISIBLE);
                    params.height = statusBarHeight;
                }
                AbLogUtil.e("params.height", params.height + "");
                //设置自定义导航栏的LayoutParams
                titleView.setLayoutParams(params);
                //2.设置paddingTop，以达到状态栏不遮挡toolbar的内容。
                titleView.setPadding(titleView.getPaddingLeft(),
                        titleView.getPaddingTop(),
                        titleView.getPaddingRight(),
                        titleView.getPaddingBottom());
            }
        } else {
            if (titleView != null && isShow) {
                titleView.setVisibility(View.VISIBLE);
            } else {
                titleView.setVisibility(View.GONE);
            }
        }
    }


    private int getNavigationBarHeight(Context context) {
        return getSystemComponentDimen(context, "navigation_bar_height");
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    private int getStatusBarHeight(Context context) {
        // 反射手机运行的类：android.R.dimen.status_bar_height.
        return getSystemComponentDimen(context, "status_bar_height");
    }

    private static int getSystemComponentDimen(Context context, String dimenName) {
        // 反射手机运行的类：android.R.dimen.status_bar_height.
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            String heightStr = clazz.getField(dimenName).get(object).toString();
            int height = Integer.parseInt(heightStr);
            //dp--->px
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    private static boolean hasNavigationBarShow(WindowManager wm) {
        Display display = wm.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        //获取整个屏幕的高度
        display.getRealMetrics(outMetrics);
        int heightPixels = outMetrics.heightPixels;
        int widthPixels = outMetrics.widthPixels;
        //获取内容展示部分的高度
        outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int heightPixels2 = outMetrics.heightPixels;
        int widthPixels2 = outMetrics.widthPixels;
        int w = widthPixels - widthPixels2;
        int h = heightPixels - heightPixels2;
        return w > 0 || h > 0;//竖屏和横屏两种情况。
    }

    @Override
    protected void onResume() {
        super.onResume();
        Date date = new Date();
        s = date.getTime();
        userAction = new UserAction();
        userAction.setPageStartTime(getStringByFormat(date, "yyyy-MM-dd HH:mm:ss"));
        userAction.setDevice(AppUtil.getDevice());
        userAction.setPageName(className);
        userAction.setPageDescribe(pageDescribe);
        if ("WebViewActivity".equals(className)) {
            if (!TextUtils.isEmpty(stop_pageDescribe)) {
                userResume(stop_pageDescribe);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (userAction != null) {
            Date date = new Date();
            e = date.getTime();
            userAction.setPageEndTime(getStringByFormat(date, "yyyy-MM-dd HH:mm:ss"));
            userAction.setPageWaiteTime(AbDateUtil.getTimeDescription(e - s));
            UserActionDao.getInstance(this).add(userAction);
            userAction = null;
            if ("WebViewActivity".equals(className)) {
                if (!TextUtils.isEmpty(stop_pageDescribe)) {
                    userStop();
                }

            }
        }
    }

    public static String getStringByFormat(Date date, String format) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        String strDate = null;
        try {
            strDate = mSimpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    public void setPageDescribe(String pageDescribe) {
        //前页面开始
        if (startTime_WebPage == 0) {
            userResume(pageDescribe);
        } else {
            //当前页面结束,新页面开始 保存前一个页面
            userStop();
            //初始化新页面
            userResume(pageDescribe);
        }

    }

    public void userResume(String pageDescribe) {
        Date data = new Date();
        startTime_WebPage = data.getTime();
        userAction_WebPage = new UserAction();
        userAction_WebPage.setPageStartTime(getStringByFormat(data, "yyyy-MM-dd HH:mm:ss"));
        userAction_WebPage.setDevice(AppUtil.getDevice());
        userAction_WebPage.setPageName(className);
        userAction_WebPage.setPageDescribe(pageDescribe);
    }

    public void userStop() {
        if (userAction_WebPage != null) {
            userAction_WebPage.setPageEndTime(getStringByFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
            userAction_WebPage.setPageWaiteTime(AbDateUtil.getTimeDescription(new Date().getTime() - startTime_WebPage));
            UserActionDao.getInstance(this).add(userAction_WebPage);
            //记录最后一个页面的标题
            stop_pageDescribe = userAction_WebPage.getPageDescribe();
            userAction_WebPage = null;
        }
    }

}
