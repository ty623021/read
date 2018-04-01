package com.rongteng.base.dialog;

import android.content.Context;

import com.rongteng.base.R;
import com.rongteng.base.adapter.MultiChoicAdapter;
import com.rongteng.base.utils.AbViewUtil;

import java.util.List;

public class MultiChoicDialog extends AbstractChoickDialog {

    private MultiChoicAdapter<String> mMultiChoicAdapter;

    public MultiChoicDialog(Context context, List<String> list, boolean[] flag) {
        super(context, list);

        initData(flag);
    }


    protected void initData(boolean flag[]) {
        // TODO Auto-generated method stub
        mMultiChoicAdapter = new MultiChoicAdapter<String>(mContext, mList, flag, R.drawable.selector_checkbox1);

        mListView.setAdapter(mMultiChoicAdapter);
        mListView.setOnItemClickListener(mMultiChoicAdapter);

        AbViewUtil.setListViewHeightBasedOnChildren(mListView);

    }


    public boolean[] getSelectItem() {
        return mMultiChoicAdapter.getSelectItem();
    }

}
