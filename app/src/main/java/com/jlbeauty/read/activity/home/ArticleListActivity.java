package com.jlbeauty.read.activity.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.adapter.ArticleAdapter;
import com.jlbeauty.read.bean.ArticleInfo;
import com.jlbeauty.read.global.Config;
import com.jlbeauty.read.utils.GetArticleList;
import com.rongteng.base.global.Constant;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.pullview.AbPullToRefreshView;
import com.rongteng.base.utils.AbRefreshUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.weight.TitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WIN10 on 2018/3/22.
 * 文章列表
 */

public class ArticleListActivity extends BaseActivity implements GetArticleList.OnArticleClick, AbPullToRefreshView.OnHeaderRefreshListener,
        AbPullToRefreshView.OnFooterLoadListener {
    private TitleView titleView;
    private String title;
    private String typeCode;//文章分类标识
    private GetArticleList getArticle;
    private boolean isRefresh = true;
    private int totalPage, offset, max = 10;
    private List<ArticleInfo> list = new ArrayList<>();
    private ArticleAdapter adapter;
    private RecyclerView recycleView;
    private AbPullToRefreshView pull;
    private ImageView network_img;
    private ImageView nodata_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_list_layout);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        typeCode = intent.getStringExtra("typeCode");
        titleView = (TitleView) findViewById(R.id.title);
        if (AbStringUtil.isEmpty(title)) {
            titleView.setTitle("文章列表");
        } else {
            titleView.setTitle(title);
        }
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * @param context
     * @param title    标题
     * @param typeCode 分类
     */
    public static void startActivity(Context context, String title, String typeCode) {
        Intent intent = new Intent(context, ArticleListActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("typeCode", typeCode);
        context.startActivity(intent);
    }


    @Override
    protected void initView() {
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setItemAnimator(new DefaultItemAnimator());
        recycleView.setNestedScrollingEnabled(false);
        recycleView.setFocusable(false);
        pull = (AbPullToRefreshView) findViewById(R.id.pull);
        network_img = (ImageView) findViewById(R.id.network_img);
        nodata_img = (ImageView) findViewById(R.id.nodata_img);
        adapter = new ArticleAdapter(this,list,typeCode);
        recycleView.setAdapter(adapter);
        AbRefreshUtil.initRefresh(pull, this, this);
    }

    @Override
    protected void setData() {
        if (getArticle == null) {
            getArticle = new GetArticleList(context, http, typeCode);
            getArticle.setOnArticleClick(this);
        }
        getArticle.getArticle(offset, max);
    }

    @Override
    protected void setEvent() {
        network_img.setOnClickListener(this);
        nodata_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(network_img)) {
            network_img.setVisibility(View.GONE);
            pull.headerRefreshing();
            progressTitle = Constant.LOADING;
        } else if (v.equals(nodata_img)) {
            nodata_img.setVisibility(View.GONE);
            pull.headerRefreshing();
            progressTitle = Constant.LOADING;
        }
    }

    @Override
    public void onArticleClick(List<ArticleInfo> data, int totalPage, boolean result) {
        if (result) {
            if (isRefresh && list.size() > 0) {
                list.clear();
            }
            this.totalPage = totalPage;
            list.addAll(data);
            adapter.notifyDataSetChanged();
            AbRefreshUtil.hintView(pull, adapter, isRefresh, false, network_img, nodata_img);
        } else {
            AbRefreshUtil.hintView(pull, adapter, isRefresh, true, network_img, nodata_img);
        }
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        isRefresh = true;
        offset = 0;
        AbRefreshUtil.isAddLoad(offset, totalPage, max, pull);
        setData();
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        isRefresh = false;
        offset += max;
        if (AbRefreshUtil.isLoading(offset, totalPage, pull)) {
            setData();
        } else {
            AbToastUtil.showToast(context, Constant.LOADED);
        }
    }
}
