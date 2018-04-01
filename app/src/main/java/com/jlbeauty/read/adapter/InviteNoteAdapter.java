package com.jlbeauty.read.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.bean.InviteNoteInfo;
import com.rongteng.base.global.Constant;
import com.rongteng.base.utils.AbDateUtil;
import com.rongteng.base.utils.AbMathUtil;
import com.rongteng.base.utils.AbStringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/26 0026.
 * 邀请好友记录
 */
public class InviteNoteAdapter extends BaseAdapter {
    private List<InviteNoteInfo> data = new ArrayList<>();
    private LayoutInflater mInflater;

    public InviteNoteAdapter(Context context, List<InviteNoteInfo> data) {
        mInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InviteNoteInfo note = data.get(position);
        ViewHolder mHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_list_invite_note, null);
            mHolder = new ViewHolder();
            mHolder.tv_realName = (TextView) convertView.findViewById(R.id.tv_realName);
            mHolder.userPhone = (TextView) convertView.findViewById(R.id.userPhone);
            mHolder.registerTime = (TextView) convertView.findViewById(R.id.registerTime);
            mHolder.BelohnungMoney = (TextView) convertView.findViewById(R.id.BelohnungMoney);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        if (note.getGrade() == 1) {
            if (AbStringUtil.isEmpty(note.getRealName())) {
                mHolder.tv_realName.setText("--");
            } else {
                mHolder.tv_realName.setText(note.getRealName());
            }
            mHolder.userPhone.setText(note.getMobile());
            mHolder.tv_realName.setVisibility(View.VISIBLE);
        } else {
            String mobile = AbStringUtil.hideUserPhone(note.getMobile());
            mHolder.tv_realName.setVisibility(View.GONE);
            mHolder.userPhone.setText(mobile);
        }
        String[] time = AbDateUtil.getDateAndTime(note.getRegTime());
        mHolder.registerTime.setText(time[0]);
        mHolder.BelohnungMoney.setText(AbMathUtil.roundStr(note.getCapital(), Constant.ROUND_DIGIT));
        return convertView;
    }

    class ViewHolder {
        TextView tv_realName, userPhone, registerTime, BelohnungMoney;
    }
}
