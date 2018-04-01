package com.rongteng.base.volley;

import android.content.Context;

import com.rongteng.base.global.Constant;
import com.rongteng.base.main.MyApplication;
import com.rongteng.base.utils.AbDateUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbMd5;
import com.rongteng.base.utils.AppUtil;
import com.spinytech.macore.MaApplication;

import java.util.Map;


/**
 * Created by Administrator on 2015/3/11.
 */
public class IRequest {

    private final Context context;

    public IRequest(Context context) {
        this.context = context;
    }


//    ====================================推荐使用start================================================

    /**
     * 设置财富网络请求
     *
     * @param params
     */
    public static void setWealthParams(Map params) {
        params.put("time", System.currentTimeMillis() + "");
        params.put("appVersion", AppUtil.getAppVersionName(MaApplication.getMaApplication()));
        params.put("platform", "app");
        params.put("ct_name", Constant.CLIENT_ID_NAME);
        params.put("token", MyApplication.mApp.getToken());
        params.put("user_id", MyApplication.mApp.getID());
        AbLogUtil.e("请求参数", toString(params));
    }

    private static String toString(Map params) {
        try {
            StringBuffer bf = new StringBuffer();
            //过滤一些默认参数
            for (Object key : params.keySet()) {
                if ("platform".equals(key)) {
                    continue;
                } else if ("time".equals(key)) {
                    continue;
                } else if ("ct_name".equals(key)) {
                    continue;
                } else if ("appVersion".equals(key)) {
                    continue;
                }
                bf.append(key).append("=").append(params.get(key)).append("，");
            }
            return "{ " + bf.toString().substring(0, bf.length() - 1) + " }";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 设置小诸葛网络请求
     *
     * @param params
     */
    private static void setParams(RequestParams params) {
        String token = MyApplication.mApp.getToken();
        params.put("token", token);
        params.put("memberId", MyApplication.mApp.getID());
        params.put("platform", "app");
        params.put("appKey", Constant.APPKEY);
        params.put("appVersion", AppUtil.getAppVersionName(MaApplication.getMaApplication()));
        params.put("ct_name", Constant.CLIENT_ID_NAME);
        params.put("timestamp", AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMDHMS));
        params.remove("sign");
        String sign = AbMd5.signTopRequest(params.getUrlParams());
        params.put("sign", sign);
        AbLogUtil.e("请求参数", params.toString());
    }

    /**
     * 返回String post
     *
     * @param url
     * @param params
     * @param l
     */
    public void post(String url, RequestParams params, RequestListener l) {
        if (params != null) {
            setParams(params);
        }
        RequestManager.post(url, context, params, l);
    }

    /**
     * 返回String 带进度条 post
     *
     * @param url
     * @param params
     * @param progressTitle
     * @param l
     */
    public void post(String url, RequestParams params,
                     String progressTitle, RequestListener l) {
        if (params != null) {
            setParams(params);
        }
        RequestManager.post(url, context, params, progressTitle, l);
    }


    /**
     * 理财投资 返回String 不带进度条
     * （参数以json形式传递）
     *
     * @param url
     * @param params
     * @param l
     */
    public static void postJson(Context context, String url, Map params,
                                RequestListener l) {
        if (params != null) {
            setWealthParams(params);
        }
        RequestManager.postJson(url, context, params, l);
    }

    /**
     * 理财投资 返回String 带进度条
     * （参数以json形式传递）
     *
     * @param url
     * @param params
     * @param l
     */
    public static void postJson(Context context, String url, Map params, String progressTitle,
                                RequestListener l) {
        if (params != null) {
            setWealthParams(params);
        }
        RequestManager.postJson(url, context, params, progressTitle, l);
    }


    /**
     * 理财投资 返回String 不带进度条
     * （参数以json形式传递）
     *
     * @param url
     * @param params
     * @param l
     */
    public void postJson(String url, Map params,
                         RequestListener l) {
        if (params != null) {
            setWealthParams(params);
        }
        RequestManager.postJson(url, context, params, l);
    }

    /**
     * 理财投资 返回String 带进度条
     * （参数以json形式传递）
     *
     * @param url
     * @param params
     * @param l
     */
    public void postJson(String url, Map params, String progressTitle,
                         RequestListener l) {
        if (params != null) {
            setWealthParams(params);
        }
        RequestManager.postJson(url, context, params, progressTitle, l);
    }

    /**
     * 理财投资 返回String 不带进度条
     * （参数以Form表单形式传递）
     *
     * @param url
     * @param params
     * @param l
     */
    public void postFrom(String url, Map params,
                         RequestListener l) {
        if (params != null) {
            setWealthParams(params);
        }
        RequestManager.postForm(url, context, params, l);
    }

    /**
     * 理财投资 返回String 带进度条
     * （参数以Form表单形式传递）
     *
     * @param url
     * @param params
     * @param l
     */
    public void postFrom(String url, Map params, String progressTitle,
                         RequestListener l) {
        if (params != null) {
            setWealthParams(params);
        }
        RequestManager.postForm(url, context, params, progressTitle, l);
    }

//  =======================================推荐使用end=============================================

}
