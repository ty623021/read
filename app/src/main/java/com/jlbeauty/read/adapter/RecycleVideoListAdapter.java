package com.jlbeauty.read.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jlbeauty.read.R;

import java.util.List;

/**
 * Created by 49479 on 2018/3/29.
 */

public class RecycleVideoListAdapter extends RecyclerView.Adapter {
    Context context;
    List datas;

    public RecycleVideoListAdapter(Context context, List datas){
        super();
        this.context=context;
        this.datas=datas;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     View view=  LayoutInflater.from(context).inflate(R.layout.fragment_video,null);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    // 定义内部类继承ViewHolder
    class MyHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public MyHolder(View view) {
            super(view);
//            imageView = (ImageView) view.findViewById(R.id.iv_item);
        }

    }


}
