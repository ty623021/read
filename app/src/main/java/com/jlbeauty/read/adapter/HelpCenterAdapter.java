package com.jlbeauty.read.adapter;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.jlbeauty.read.R;
import com.jlbeauty.read.bean.ArticleInfo;
import com.jlbeauty.read.utils.ImageGet;
import com.rongteng.base.utils.AbLogUtil;

import java.util.List;


/**
 * Created by hjy on 2016/7/15.
 */
public class HelpCenterAdapter extends BaseExpandableListAdapter {
    private List<ArticleInfo> groupList;
    private Context context;
    private TextView title_group;
    private ImageView right_group;

    public HelpCenterAdapter(Context context, List<ArticleInfo> groupList) {
        this.groupList = groupList;
        this.context = context;

    }

    public void addList(List<ArticleInfo> list, boolean isRefresh) {
        if (isRefresh) {
            groupList.clear();
        }
        groupList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupList.get(groupPosition).content;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.activity_helpcenter_group_item, null);
        right_group = (ImageView) view.findViewById(R.id.right_group);
        View line = view.findViewById(R.id.line);
        if (groupPosition == 0) {
            line.setVisibility(View.GONE);
        } else {
            line.setVisibility(View.VISIBLE);
        }
        if (isExpanded) {
            right_group.setImageResource(R.drawable.to_up);
        } else {
            right_group.setImageResource(R.drawable.to_down);
        }
        ArticleInfo info = groupList.get(groupPosition);
        title_group = (TextView) view.findViewById(R.id.title);
        title_group.setText(info.title);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.message_child_item, null);
        String content = groupList.get(groupPosition).content;
        AbLogUtil.e("content", content);
        TextView child = (TextView) view.findViewById(R.id.child_message);
        child.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
        child.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
        child.setText(Html.fromHtml(content, new ImageGet(context, child), null));
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }

}
