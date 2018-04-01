package com.jlbeauty.read.utils;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.jlbeauty.read.global.Config;
import com.rongteng.base.bean.Home_Banner;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.volley.IRequest;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.volley.RequestParams;


import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by geek on 2016/7/20.
 * 根据code获取banner图
 */
public class GetBanner {

    private final Context activity;
    private final IRequest http;
    private final String code;
    private OnBannerClick onBannerClick;

    public GetBanner(Context activity, IRequest http, String code) {
        this.activity = activity;
        this.http = http;
        this.code = code;
    }

    /**
     * 获取banner数据
     */
    public void getBanner() {
        RequestParams params = new RequestParams();
        params.put("code", code);
        http.post(Config.URL_GETBANNERON, params, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("getBanner", json.toString());
                if (AbJsonUtil.isSuccess(json)) {
                    Type type = new TypeToken<List<Home_Banner>>() {
                    }.getType();
                    List<Home_Banner> picList = (List<Home_Banner>) AbJsonUtil.fromJson(json, type, "data");
                    if (AbStringUtil.isListEmpty(picList)) {
                        if (onBannerClick != null) {
                            onBannerClick.onBannerClick(picList);
                        }
                    }
                }
            }

            @Override
            public void requestError(String message) {
            }
        });
    }


    /**
     * 获取banner数据
     *
     * @param progressTitle 有加载loading
     */
    public void getBanner(String progressTitle) {
        RequestParams params = new RequestParams();
        params.put("code", code);
        http.post(Config.URL_GETBANNERON, params, progressTitle, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("getBanner", json.toString());
                if (AbJsonUtil.isSuccess(json)) {
                    Type type = new TypeToken<List<Home_Banner>>() {
                    }.getType();
                    List<Home_Banner> picList = (List<Home_Banner>) AbJsonUtil.fromJson(json, type, "data");
                    if (AbStringUtil.isListEmpty(picList)) {
                        if (onBannerClick != null) {
                            onBannerClick.onBannerClick(picList);
                        }
                    }
                }
            }

            @Override
            public void requestError(String message) {
            }
        });
    }
    /**
     * 获取banner数据
     *
     * @param onBannerFailListener 加载失败回调
     */
    public void getBanner(final OnBannerFailListener onBannerFailListener) {
        RequestParams params = new RequestParams();
        params.put("code", code);
        http.post(Config.URL_GETBANNERON, params,  new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("getBanner", json.toString());
                if (AbJsonUtil.isSuccess(json)) {
                    Type type = new TypeToken<List<Home_Banner>>() {
                    }.getType();
                    List<Home_Banner> picList = (List<Home_Banner>) AbJsonUtil.fromJson(json, type, "data");
                    if (AbStringUtil.isListEmpty(picList)) {
                        if (onBannerClick != null) {
                            onBannerClick.onBannerClick(picList);
                        }
                    }
                }else {
                    onBannerFailListener.onBannerFailListener();
                }
            }

            @Override
            public void requestError(String message) {
                onBannerFailListener.onBannerFailListener();
            }
        });
    }

    public interface OnBannerClick {
        void onBannerClick(List<Home_Banner> list);
    }

    public interface OnBannerFailListener{
        void onBannerFailListener();
    }

    public void setOnBannerClick(OnBannerClick onBannerClick) {
        this.onBannerClick = onBannerClick;
    }
}
