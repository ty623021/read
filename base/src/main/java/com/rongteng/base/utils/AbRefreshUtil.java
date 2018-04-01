package com.rongteng.base.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.rongteng.base.pullview.AbPullToRefreshView;

import java.util.List;

/**
 * Created by geek on 2016/7/22.
 * 处理刷新操作类
 */
public class AbRefreshUtil {

    /**
     * 初始化下拉刷新
     *
     * @param pull     下拉刷新控件
     * @param listener 下拉刷新监听
     */
    public static void initRefresh(AbPullToRefreshView pull, AbPullToRefreshView.OnHeaderRefreshListener listener) {
        pull.setLoadMoreEnable(false);
        pull.clearFooter();
        pull.setOnHeaderRefreshListener(listener);
        //是否直接下拉刷新
//        pull.headerRefreshing();
    }

    /**
     * 初始化下拉刷新和上拉加载
     *
     * @param pull            拉刷新控件
     * @param refreshListener 下拉刷新监听
     * @param loadListener    上拉加载监听
     */
    public static void initRefresh(AbPullToRefreshView pull, AbPullToRefreshView.OnHeaderRefreshListener refreshListener, AbPullToRefreshView.OnFooterLoadListener loadListener) {
//        pull.setLoadMoreEnable(false);
//        pull.clearFooter();
        pull.setOnHeaderRefreshListener(refreshListener);
        pull.setOnFooterLoadListener(loadListener);
        //是否直接下拉刷新
//        pull.headerRefreshing();
    }

    /**
     * 初始化上拉加载
     *
     * @param pull         拉刷新控件
     * @param loadListener 上拉加载监听
     */
    public static void initRefresh(AbPullToRefreshView pull, AbPullToRefreshView.OnFooterLoadListener loadListener) {
        pull.setOnFooterLoadListener(loadListener);
        pull.setPullRefreshEnable(false);
    }

    /**
     * 以列表形式展示数据时回调的
     *
     * @param isNotify  是否刷新列表
     * @param isRefresh 是否是下拉刷新
     * @param pull      下拉刷新的控件
     * @param adapter   列表适配器
     * @param list      列表数据集合
     */
    public static void onComplete(boolean isNotify, boolean isRefresh, AbPullToRefreshView pull, BaseAdapter adapter, List<?> list) {
        if (isRefresh == true) {
            pull.onHeaderRefreshFinish();
            if (isNotify) {
                if (adapter.getCount() > 0) {
                    list.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        } else {
            pull.onFooterLoadFinish();
        }
    }

    /**
     * 以列表形式展示数据时回调的
     *
     * @param isNetwork   是否显示网络加载失败
     * @param adapter     列表适配器
     * @param network     包裹网络加载失败和无数据的布局
     * @param nodata_img  没有请求到数据
     * @param network_img 网络加载失败的图片
     */
    public static void hintView(boolean isNetwork, BaseAdapter adapter, RelativeLayout network, ImageView nodata_img, ImageView network_img) {
        if (adapter.getCount() == 0) {
            network.setVisibility(View.VISIBLE);
            if (isNetwork) {
                network_img.setVisibility(View.VISIBLE);
                nodata_img.setVisibility(View.GONE);
            } else {
                network_img.setVisibility(View.GONE);
                nodata_img.setVisibility(View.VISIBLE);
            }
        } else {
            network.setVisibility(View.GONE);
        }
    }

    /**
     * 用recycleView列表页面的网络请求
     *
     * @param adapter   适配器
     * @param isNetwork 是否显示网络链接失败
     * @param network   包裹网络加载失败和无数据的布局
     * @param nodata    没有请求到数据
     */
    public static void hintView(AbPullToRefreshView pull, RecyclerView.Adapter adapter, boolean isNetwork, View network_disabled, View network, View nodata) {
        pull.onHeaderRefreshFinish();
        if (adapter.getItemCount() == 0) {
            network_disabled.setVisibility(View.VISIBLE);
            if (isNetwork) {
                network.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.GONE);
            } else {
                network.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
            }
        } else {
            network_disabled.setVisibility(View.GONE);
        }
    }

    /**
     * 用recycleView列表页面的网络请求
     *
     * @param adapter   适配器
     * @param isNetwork 是否显示网络链接失败
     * @param network   包裹网络加载失败和无数据的布局
     * @param nodata    没有请求到数据
     */
    public static void hintView(AbPullToRefreshView pull, RecyclerView.Adapter adapter, boolean isRefresh, boolean isNetwork, View network, View nodata) {
        if (isRefresh) {
            pull.onHeaderRefreshFinish();
        } else {
            pull.onFooterLoadFinish();
        }
        if (adapter.getItemCount() == 0) {
            pull.clearFooter();
            nodata.setVisibility(View.INVISIBLE);
            if (isNetwork) {
                network.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.GONE);
            } else {
                network.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
            }
        } else {
            nodata.setVisibility(View.GONE);
            network.setVisibility(View.GONE);
        }
    }

    /**
     * 用recycleView列表页面的网络请求
     *
     * @param adapter   适配器
     * @param isNetwork 是否显示网络链接失败
     * @param network   包裹网络加载失败和无数据的布局
     * @param nodata    没有请求到数据
     */
    public static void hintView(AbPullToRefreshView pull, RecyclerView.Adapter adapter, boolean isRefresh, boolean isNetwork, View loading, View network, View nodata) {
        if (isRefresh) {
            pull.onHeaderRefreshFinish();
        } else {
            pull.onFooterLoadFinish();
        }
        if (adapter.getItemCount() == 0) {
            pull.clearFooter();
            loading.setVisibility(View.VISIBLE);
            if (isNetwork) {
                network.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.GONE);
            } else {
                network.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
            }
        } else {
            loading.setVisibility(View.GONE);
        }
    }

    /**
     * 用recycleView列表页面的网络请求
     *
     * @param adapter   适配器
     * @param isNetwork 是否显示网络链接失败
     * @param network   包裹网络加载失败和无数据的布局
     * @param nodata    没有请求到数据
     */
    public static void hintView(RecyclerView.Adapter adapter, boolean isNetwork, View network, View nodata) {
        if (adapter.getItemCount() == 0) {
            if (isNetwork) {
                network.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.GONE);
            } else {
                network.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
            }
        } else {
            network.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }


    /**
     * 判断是否需要添加加载更多
     *
     * @param offset    偏移量
     * @param totalPage 列表的总数
     * @param max       每页最大加载数量
     * @param pull      下拉刷新控件
     */
    public static void isAddLoad(int offset, int totalPage, int max, AbPullToRefreshView pull) {
        //当偏移量大于0 并且 请求到的总数大于每页显示的最大数量的时候，添加一个上拉加载更多的布局
        if (offset >= 0 && totalPage > max) pull.addFooter();
    }

    /**
     * 判断是否需要加载更多
     *
     * @param offset    偏移量
     * @param totalPage 列表的总数
     * @param pull      下拉刷新控件
     */
    public static boolean isLoading(int offset, int totalPage, AbPullToRefreshView pull) {
        //当偏移量大于等于请求到的总数的时候，关闭上拉加载的功能
        if (offset >= totalPage) {
            pull.onFooterLoadFinish();
            pull.clearFooter();
        } else {
            return true;
        }
        return false;
    }

    /**
     * 判断是否需要加载更多
     *
     * @param nextPage  下一页
     * @param totalPage 列表的总数
     * @param pull      下拉刷新控件
     */
    public static boolean isLoad(int nextPage, int totalPage, AbPullToRefreshView pull) {
        //当偏移量大于等于请求到的总数的时候，关闭上拉加载的功能
        if (nextPage >= totalPage) {
            pull.onFooterLoadFinish();
            pull.clearFooter();
        } else {
            return true;
        }
        return false;
    }


    /**
     * 用于一般页面的网络请求
     *
     * @param obj         网络请求道的对象
     * @param isNetwork   是否显示网络链接失败
     * @param network     包裹网络加载失败和无数据的布局
     * @param nodata_img  没有请求到数据
     * @param network_img 网络加载失败的图片
     * @param pull        下拉刷新控件
     */
    public static void hintView(Object obj, boolean isNetwork, RelativeLayout network, ImageView nodata_img, ImageView network_img, AbPullToRefreshView pull) {
        pull.onHeaderRefreshFinish();
        if (obj != null) {
            pull.setVisibility(View.VISIBLE);
            network.setVisibility(View.GONE);
        } else {
            pull.setVisibility(View.GONE);
            network.setVisibility(View.VISIBLE);
            if (isNetwork) {
                network_img.setVisibility(View.VISIBLE);
                nodata_img.setVisibility(View.GONE);
            } else {
                network_img.setVisibility(View.GONE);
                nodata_img.setVisibility(View.VISIBLE);
            }
        }
    }

    public static void hintViewWithRecyclerAdapter(RecyclerView.Adapter adapter, boolean isNetwork, View noticeView, ImageView network, ImageView nodata) {
        if (adapter.getItemCount() == 0) {
            noticeView.setVisibility(View.VISIBLE);
            if (isNetwork) {
                network.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.GONE);
            } else {
                network.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
            }
        } else {
            noticeView.setVisibility(View.GONE);
        }
    }
}
