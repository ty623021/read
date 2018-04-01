package com.jlbeauty.read.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jlbeauty.read.R;
import com.jlbeauty.read.bean.FindLatesActivitiesInfo;
import com.rongteng.base.adapter.BaseRecyclerViewAdapter;
import com.rongteng.base.utils.AbDateUtil;
import com.rongteng.base.utils.AbImageUtil;
import com.rongteng.base.webview.WebViewActivity;


import java.util.List;

/**
 * Created by hjy on 2017/5/25.
 */

public class MoreLatesAdapter extends BaseRecyclerViewAdapter<FindLatesActivitiesInfo, MoreLatesAdapter.MyViewHolder> {


    public MoreLatesAdapter(List<FindLatesActivitiesInfo> list) {
        super(list);
    }

    @Override
    public MyViewHolder mCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.fragment_more_lates_activities_item, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new MoreLatesAdapter.MyViewHolder(view);
    }

    @Override
    public void mBindViewHolder(final MyViewHolder holder, int position) {
        final FindLatesActivitiesInfo info = list.get(position);
        AbImageUtil.glideImageList(info.getImgUrl(), holder.imgs, R.drawable.default_banner);
        holder.activity_title.setText(info.getTitle());
        String[] getStartDate = AbDateUtil.getDateAndTime(info.getStartDate());
        String[] getEndDate = AbDateUtil.getDateAndTime(info.getEndDate());
        holder.activity_time.setText(getStartDate[0] + "~" + getEndDate[0]);

        if (position == 0) {
            holder.line.setVisibility(View.GONE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        holder.imgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.startActivity((Activity) mContext, info.getLink(), info.getTitle());
            }
        });
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private ImageView imgs;
        private TextView activity_title;
        private TextView activity_time;
        private View line;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imgs = (ImageView) itemView.findViewById(R.id.iv_activity);
            activity_title = (TextView) itemView.findViewById(R.id.activity_title);
            activity_time = (TextView) itemView.findViewById(R.id.tv_activity_date_time);
            line = itemView.findViewById(R.id.line);
        }
    }


}
