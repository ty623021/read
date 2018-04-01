package com.jlbeauty.read.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import android.widget.TextView;


import com.jlbeauty.read.R;
import com.jlbeauty.read.activity.home.ArticleListActivity;
import com.jlbeauty.read.activity.home.MoreLatestActivity;
import com.jlbeauty.read.bean.HomeInfo;

import com.rongteng.base.global.Constant;
import com.rongteng.base.listener.OnMultiClickListener;
import com.rongteng.base.utils.AbImageUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.webview.WebViewActivity;


import java.util.List;

/**
 * recycleView嵌套recycleView
 */
public class RecycleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_TYPE1 = 0xff01;
    public static final int TYPE_TYPE2 = 0xff02;
    public static final int TYPE_TYPE3 = 0xff03;

    private Context context;
    private List<HomeInfo> list;

    public RecycleListAdapter(Context context, List<HomeInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderType(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HolderType) {
            bindType((HolderType) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void bindType(HolderType holder, final int position) {
        final HomeInfo homeInfo = list.get(position);
        holder.item_recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.tv_item_title.setText(homeInfo.catalogName);
        holder.tv_item_more.setVisibility(View.VISIBLE);
        holder.item_recyclerView.setAdapter(new ItemRecycleAdapter(TYPE_TYPE2, homeInfo.catalogData));
        holder.tv_item_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homeInfo.catalogName.equals("特别推荐")) {
                    MoreLatestActivity.startActivity(context, 0);
                }else if (homeInfo.catalogName.equals("节目预报")){
                    ArticleListActivity.startActivity(context, "节目预报", Constant.ARTICLE_CODE_FORECAST);
                }
            }
        });
    }

    public class HolderType extends RecyclerView.ViewHolder {
        private TextView tv_item_title, tv_item_more;
        private RecyclerView item_recyclerView;

        public HolderType(View itemView) {
            super(itemView);
            item_recyclerView = (RecyclerView) itemView.findViewById(R.id.item_recyclerView);
            item_recyclerView.setNestedScrollingEnabled(false);
            item_recyclerView.setFocusable(false);
            tv_item_title = (TextView) itemView.findViewById(R.id.tv_item_title);
            tv_item_more = (TextView) itemView.findViewById(R.id.tv_item_more);
        }
    }

    private class ItemRecycleAdapter extends RecyclerView.Adapter {
        private int type;
        private List data;

        public ItemRecycleAdapter(int type, List data) {
            this.type = type;
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (type) {
                case TYPE_TYPE1:
                    return new ItemRecycleAdapter.WealthHolderType(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_wealth_list, parent, false));
                case TYPE_TYPE2:
                    return new ItemRecycleAdapter.ActivityHolderType(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_new_activity, parent, false));
                case TYPE_TYPE3:
                    return new ItemRecycleAdapter.BannerHolderType(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_banner, parent, false));
                default:
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof WealthHolderType) {
                bindType1((WealthHolderType) holder, position);
            } else if (holder instanceof ActivityHolderType) {
                bindType2((ActivityHolderType) holder, position);
            } else if (holder instanceof BannerHolderType) {
                bindType3((BannerHolderType) holder, position);
            }

        }

        private void bindType1(WealthHolderType holder, int position) {

        }

        private void bindType2(ActivityHolderType holder, int position) {
            final HomeInfo.HomeItem item = (HomeInfo.HomeItem) data.get(position);
            holder.activity_title.setText(item.title);
            holder.tv_activity_date_time.setText(item.time);
            AbImageUtil.glideImageList(item.coverUrl, holder.iv_activity, R.drawable.default_banner);
            holder.itemView.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View v) {
                    if (!AbStringUtil.isEmpty(item.redirectUrl)) {
                        WebViewActivity.startActivity((Activity) context,item.redirectUrl,item.title);
                    }
                }
            });
        }

        private void bindType3(BannerHolderType holder, int position) {

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        /**
         * 财富推荐
         */
        class WealthHolderType extends RecyclerView.ViewHolder {


            WealthHolderType(View itemView) {
                super(itemView);

            }
        }

        /**
         * 最新活动
         */
        class ActivityHolderType extends RecyclerView.ViewHolder {
            public TextView activity_title;
            public TextView tv_activity_status;
            public TextView tv_activity_date_time;
            public ImageView iv_activity;

            ActivityHolderType(View itemView) {
                super(itemView);
                activity_title = (TextView) itemView.findViewById(R.id.activity_title);
                tv_activity_status = (TextView) itemView.findViewById(R.id.tv_activity_status);
                tv_activity_date_time = (TextView) itemView.findViewById(R.id.tv_activity_date_time);
                iv_activity = (ImageView) itemView.findViewById(R.id.iv_activity);
            }
        }

        /**
         * 猜你喜欢
         */
        class BannerHolderType extends RecyclerView.ViewHolder {
            private ImageView iv_banner;

            BannerHolderType(View itemView) {
                super(itemView);
                iv_banner = (ImageView) itemView.findViewById(R.id.iv_banner);
            }
        }
    }

}
