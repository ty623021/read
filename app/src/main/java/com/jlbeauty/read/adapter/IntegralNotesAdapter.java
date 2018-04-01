package com.jlbeauty.read.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.bean.IntegralInfo;
import com.jlbeauty.read.global.Config;
import com.rongteng.base.utils.AbDateUtil;

import java.util.List;

/**
 * Created by geek on 2016/11/1.
 * 商品
 */
public class IntegralNotesAdapter extends BaseAdapter {
    private List<IntegralInfo> item;
    private Context context;

    public IntegralNotesAdapter(Context context, List itemList) {
        this.item = itemList;
        this.context = context;
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
        IntegralInfo info = this.item.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.integral_notes_item, null);
            holder = new ViewHolder();
            holder.tv_integral = (TextView) convertView.findViewById(R.id.tv_integral);
            holder.tv_source = (TextView) convertView.findViewById(R.id.tv_source);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_integral.setText(info.getAvailableAffect() + "积分");
        if (info.getAvailableAffect().indexOf("-") != -1) {
            holder.tv_integral.setTextColor(Color.parseColor(Config.colorPrimary));
        } else {
            holder.tv_integral.setTextColor(Color.parseColor(Config.colorPrimary));
        }

        holder.tv_source.setText(info.getReason());
        String[] dateAndTime = AbDateUtil.getDateAndTime(info.getDateCreated());
        holder.tv_date.setText(dateAndTime[0]);
        holder.tv_time.setText(dateAndTime[1]);
        return convertView;
    }

    /**
     * 界面上的显示控件
     */
    private class ViewHolder {
        private TextView tv_integral, tv_source, tv_date, tv_time;//名称
    }
}
