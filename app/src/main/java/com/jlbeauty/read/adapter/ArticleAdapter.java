package com.jlbeauty.read.adapter;

/**
 * 文章
 */

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.bean.ArticleInfo;
import com.jlbeauty.read.bean.HomeInfo;
import com.jlbeauty.read.global.Config;

import com.rongteng.base.adapter.BaseRecyclerViewAdapter;
import com.rongteng.base.global.Constant;
import com.rongteng.base.listener.OnMultiClickListener;
import com.rongteng.base.utils.AbDateUtil;
import com.rongteng.base.utils.AbImageUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.webview.WebViewActivity;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter {
    private List<ArticleInfo> list;
    private String typeCode;
    private Context context;
    private LayoutInflater inflater;

    public ArticleAdapter(Context context, List<ArticleInfo> list, String typeCode) {
        this.list = list;
        this.typeCode = typeCode;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (typeCode != null) {
            if (Constant.ARTICLE_CODE_NOTICE.equals(typeCode)) {
                view = inflater.inflate(R.layout.item_article_notice, parent, false);
                return new MyViewHolder1(view);
            } else if (Constant.ARTICLE_CODE_BROADCAST.equals(typeCode)) {
                view = inflater.inflate(R.layout.item_article_list1, parent, false);
                return new MyViewHolder2(view);
            } else if (Constant.ARTICLE_CODE_INTRODUCE.equals(typeCode)) {
                view = inflater.inflate(R.layout.item_article_list1, parent, false);
                return new MyViewHolder2(view);
            } else if (Constant.ARTICLE_CODE_UNION.equals(typeCode)) {
                view = inflater.inflate(R.layout.item_article_list1, parent, false);
                return new MyViewHolder2(view);
            } else if (Constant.ARTICLE_CODE_BUSINESSCOLLEGE.equals(typeCode)) {
                view = inflater.inflate(R.layout.item_article_list1, parent, false);
                return new MyViewHolder2(view);
            } else if (Constant.ARTICLE_CODE_FORECAST.equals(typeCode)) {
                view = inflater.inflate(R.layout.item_article_activity, parent, false);
                return new MyViewHolder3(view);
            } else {
                view = inflater.inflate(R.layout.item_article_notice, parent, false);
                return new MyViewHolder1(view);
            }
        } else {
            view = inflater.inflate(R.layout.item_article_notice, parent, false);
            return new MyViewHolder1(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder1) {
            bindHolder1((MyViewHolder1) holder, position);
        } else if (holder instanceof MyViewHolder2) {
            bindHolder2((MyViewHolder2) holder, position);
        } else if (holder instanceof MyViewHolder3) {
            bindHolder3((MyViewHolder3) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void bindHolder1(MyViewHolder1 holder, int position) {
        final ArticleInfo items = this.list.get(position);
        holder.tv_title.setText(items.title);
        holder.tv_time.setText(items.time);
        holder.rl_show.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//                ArticleDetailsActivity.startActivity(context, items.getItemId(), "公告详情");
                WebViewActivity.startActivity((Activity) context, Config.HTML_URL + items.itemId + ".html", "公告详情");
            }
        });
    }

    public void bindHolder2(MyViewHolder2 holder, int position) {
        final ArticleInfo items = this.list.get(position);
        holder.tv_title.setText(items.title);
        holder.news_time.setText(items.time);
        AbImageUtil.glideImageList(items.coverUrl, holder.news_imgs, R.drawable.et_clear);
        AbImageUtil.glideImageList(items.backupCoverUrl, holder.news_press, R.drawable.et_clear);
        holder.mediareport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ArticleDetailsActivity.startActivity(context, info.getItemId(), "报道详情");
                WebViewActivity.startActivity((Activity) context, Config.HTML_URL + items.itemId + ".html", "报道详情");
            }
        });
    }

    private void bindHolder3(MyViewHolder3 holder, int position) {
        final ArticleInfo item = list.get(position);
        holder.activity_title.setText(item.title);
        holder.tv_activity_date_time.setText(item.time);
        AbImageUtil.glideImageList(item.coverUrl, holder.iv_activity, R.drawable.default_banner);
        holder.itemView.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (!AbStringUtil.isEmpty(item.redirectUrl)) {
                    WebViewActivity.startActivity((Activity) context, item.redirectUrl, item.title);
                }
            }
        });
    }

    /**
     * 界面上的显示控件
     */
    class MyViewHolder1 extends RecyclerView.ViewHolder {
        public TextView tv_title;//
        public TextView tv_time;// 时间
        public RelativeLayout rl_show;

        public MyViewHolder1(View itemView) {
            super(itemView);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            this.rl_show = (RelativeLayout) itemView.findViewById(R.id.rl_show);
        }
    }

    /**
     * 界面上的显示控件
     */
    class MyViewHolder2 extends RecyclerView.ViewHolder {
        public TextView tv_title;//
        public ImageView news_imgs, news_press;
        public TextView news_time;// 时间
        public LinearLayout mediareport;

        public MyViewHolder2(View itemView) {
            super(itemView);
            this.mediareport = (LinearLayout) itemView.findViewById(R.id.mediareport);
            this.tv_title = (TextView) itemView.findViewById(R.id.news_title);
            this.news_imgs = (ImageView) itemView.findViewById(R.id.news_itemId);
            this.news_time = (TextView) itemView.findViewById(R.id.news_time);
            this.news_press = (ImageView) itemView.findViewById(R.id.news_press);
        }
    }

    /**
     * 最新活动
     */
    class MyViewHolder3 extends RecyclerView.ViewHolder {
        public TextView activity_title;
        public TextView tv_activity_status;
        public TextView tv_activity_date_time;
        public ImageView iv_activity;

        MyViewHolder3(View itemView) {
            super(itemView);
            activity_title = (TextView) itemView.findViewById(R.id.activity_title);
            tv_activity_status = (TextView) itemView.findViewById(R.id.tv_activity_status);
            tv_activity_date_time = (TextView) itemView.findViewById(R.id.tv_activity_date_time);
            iv_activity = (ImageView) itemView.findViewById(R.id.iv_activity);
        }
    }
}
