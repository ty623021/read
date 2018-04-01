package com.jlbeauty.read.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.activity.IntegralMall.ExchangeDetailActivity;
import com.jlbeauty.read.bean.IntegralVoucherInfo;
import com.rongteng.base.adapter.BaseRecyclerViewAdapter;
import com.rongteng.base.utils.AbImageUtil;


import java.util.List;

/**
 * 积分兑换
 */

public class IntegralExchangeAdapter extends BaseRecyclerViewAdapter<IntegralVoucherInfo, IntegralExchangeAdapter.IntegralExchangeHolder> {


    public IntegralExchangeAdapter(List<IntegralVoucherInfo> list) {
        super(list);
    }

    @Override
    public IntegralExchangeHolder mCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mInflater.from(mContext).inflate(R.layout.item_integral, null);
        return new IntegralExchangeHolder(item);
    }

    @Override
    public void mBindViewHolder(IntegralExchangeHolder holder, int position) {
        final IntegralVoucherInfo info = list.get(position);
        AbImageUtil.glideImageList(info.getImgUrl(), holder.mall_img, R.drawable.fail_2);
        holder.shopping_title.setText(info.getVoucherName() + "");
        holder.integral_mall_amount.setText(info.getIntegralWorth() + "");
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExchangeDetailActivity.startActivity(mContext, info.getVoucherName(), info.getId());
            }
        });
    }

    class IntegralExchangeHolder extends RecyclerView.ViewHolder {

        private ImageView mall_img;
        private TextView shopping_title;
        private TextView integral_mall_amount;
        private View view;

        public IntegralExchangeHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.mall_img = (ImageView) itemView.findViewById(R.id.mall_img);
            this.shopping_title = (TextView) itemView.findViewById(R.id.shopping_title);
            this.integral_mall_amount = (TextView) itemView.findViewById(R.id.integral_mall_amount);
        }
    }
}
