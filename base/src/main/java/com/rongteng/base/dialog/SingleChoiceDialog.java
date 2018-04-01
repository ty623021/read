package com.rongteng.base.dialog;

import android.content.Context;

import com.rongteng.base.R;
import com.rongteng.base.adapter.SingleChoicAdapter;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbViewUtil;

import java.util.List;

public class SingleChoiceDialog extends AbstractChoickDialog {

    private SingleChoicAdapter<String> mSingleChoicAdapter;

    public SingleChoiceDialog(Context context, List<String> list) {
        super(context, list);
        initData();
    }

    protected void initData() {
//        mSingleChoicAdapter = new SingleChoicAdapter<String>(mContext, mList, R.drawable.selector_checkbox2);
        mSingleChoicAdapter = new SingleChoicAdapter<String>(mContext, mOkClickListener, this, mList, R.drawable.selector_checkbox2);
        mListView.setAdapter(mSingleChoicAdapter);
        mListView.setOnItemClickListener(mSingleChoicAdapter);

        AbViewUtil.setListViewHeightBasedOnChildren(mListView);

    }

    public int getSelectItem() {
        return mSingleChoicAdapter.getSelectItem();
    }

    public void setOnOKListener(OnClickListener onClickListener) {
        mOkClickListener = onClickListener;
        mSingleChoicAdapter.setOnClickListener(onClickListener);
    }

    /**
     * 设置自动选择的条目
     *
     * @param index
     */
    public void setSelectItem(int index) {
        mSingleChoicAdapter.setSelectItem(index);
        AbLogUtil.e("position", index + "");
    }

}
