package com.jlbeauty.read.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;


import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.jlbeauty.read.R;
import com.jlbeauty.read.bean.SuggestionInfo;

import java.util.List;

/**
 * Created by hjy on 2016/7/18.
 */
public class SuggestionAdapter extends BaseAdapter {

    private List<SuggestionInfo> list;
    private Context context;

    public SuggestionAdapter(List<SuggestionInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public List<SuggestionInfo> getList() {
        return list;
    }

    public void addList(int index, SuggestionInfo info) {
        list.add(index, info);
        notifyDataSetChanged();
    }

    public void replaceInfo(int index, SuggestionInfo info) {
        list.remove(index);
        list.add(index, info);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyHolder myholder;
        SuggestionInfo info = list.get(position);
        Bitmap bitmap = info.getBitmap();
        if (convertView == null) {
            myholder = new MyHolder();
            convertView = View.inflate(context, R.layout.activity_suggestions_creame_item, null);
            myholder.suggestion_imgs = (ImageView) convertView.findViewById(R.id.iv_photo);
            myholder.delete_markView = (ImageView) convertView.findViewById(R.id.img_delete);
            myholder.tv_photo = (TextView) convertView.findViewById(R.id.tv_photo);

            convertView.setTag(myholder);
        } else {
            myholder = (MyHolder) convertView.getTag();
        }
//        默认的添加图片的那个item是不需要显示删除图片的
//        myholder.suggestion_imgs.setVisibility(View.VISIBLE);
//        myholder.suggestion_imgs.setImageBitmap(list.get(position));
        if (position == list.size() - 1) {
            myholder.tv_photo.setVisibility(View.VISIBLE);
            myholder.delete_markView.setVisibility(View.GONE);
        } else {
            myholder.tv_photo.setVisibility(View.GONE);
            myholder.delete_markView.setVisibility(View.VISIBLE);
        }
        myholder.delete_markView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        myholder.suggestion_imgs.setImageBitmap(bitmap);
        return convertView;
    }

    class MyHolder {
        private ImageView suggestion_imgs;
        private ImageView delete_markView;//小叉图标
        private TextView tv_photo;//上传照片的文字
    }


}
