package com.jlbeauty.read.adapter;

/**
 * 公告列表
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.bean.SignDayInfo;


import java.util.List;

public class SignAdapter extends BaseAdapter {
    private List<SignDayInfo> item;
    private Context context;
    private boolean isShow;

    public SignAdapter(Context context, List itemList, boolean isShow) {
        this.item = itemList;
        this.context = context;
        this.isShow = isShow;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.item.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return this.item.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SignDayInfo info = this.item.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.sign_item, null);
            holder = new ViewHolder();
            holder.tv_day = (TextView) convertView.findViewById(R.id.tv_day);
            holder.tv_day_integral = (TextView) convertView.findViewById(R.id.tv_day_integral);
            holder.frame = (FrameLayout) convertView.findViewById(R.id.frame);
            holder.iv_day = (ImageView) convertView.findViewById(R.id.iv_day);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (isShow) {
            holder.iv_day.setSelected(info.isSign());
            if (item.size() == 3 && position + 1 == item.size()) {
                holder.iv_day.setVisibility(View.VISIBLE);
            } else {
                holder.iv_day.setVisibility(View.GONE);
            }
        } else {
            holder.iv_day.setVisibility(View.GONE);
        }
        holder.tv_day.setText(info.getDay() + "天");
        holder.tv_day_integral.setText("+" + info.getValue());
        holder.frame.setSelected(info.isSign());
        holder.tv_day.setSelected(info.isSign());
        return convertView;
    }

    /**
     * 界面上的显示控件
     */
    private static class ViewHolder {
        private TextView tv_day;//
        private TextView tv_day_integral;//
        private ImageView iv_day;
        private FrameLayout frame;
    }

}
