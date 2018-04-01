package com.jlbeauty.read.utils;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.jlbeauty.read.bean.ArticleInfo;
import com.jlbeauty.read.global.Config;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.volley.IRequest;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.volley.RequestParams;


import java.util.List;

/**
 * Created by geek on 2016/7/20.
 * 根据类型获取文章，支持分页参数
 */
public class GetArticleList {

    private final Context activity;
    private final IRequest http;
    private final String code;
    private OnArticleClick onArticleClick;
    private int totalPage;

    public GetArticleList(Context activity, IRequest http, String code) {
        this.activity = activity;
        this.http = http;
        this.code = code;
    }

    public void getArticle(int offset, int max) {
        RequestParams params = new RequestParams();
        params.put("typeCode", code);
        params.put("offset", offset + "");
        params.put("max", max + "");
        http.post(Config.URL_GETARTICLELISTON, params, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("文章列表", json.toString());
                if (AbJsonUtil.isSuccess(json)) {
                    TypeToken ty = new TypeToken<List<ArticleInfo>>() {
                    };
                    totalPage = AbJsonUtil.getTotalPage(json);
                    List<ArticleInfo> list = (List<ArticleInfo>) AbJsonUtil.fromJson(json, ty, "data");
                    if (AbStringUtil.isListEmpty(list)) {
                        onArticleClick.onArticleClick(list, totalPage, true);
                        return;
                    }
                    onArticleClick.onArticleClick(null, totalPage, false);
                } else {
                    onArticleClick.onArticleClick(null, totalPage, false);
                }
            }

            @Override
            public void requestError(String message) {
                onArticleClick.onArticleClick(null, totalPage, false);
            }
        });
    }

    public interface OnArticleClick {
        void onArticleClick(List<ArticleInfo> data, int totalPage, boolean result);
    }

    public void setOnArticleClick(OnArticleClick onArticleClick) {
        this.onArticleClick = onArticleClick;
    }
}
