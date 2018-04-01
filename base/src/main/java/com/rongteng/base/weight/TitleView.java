package com.rongteng.base.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rongteng.base.R;
import com.rongteng.base.utils.AppUtil;
import com.rongteng.base.utils.ResourceUtil;


/**
 * 自定义标题栏
 */
public class TitleView extends RelativeLayout {

    private RelativeLayout titleView;
    private RelativeLayout leftTitleView;
    private RelativeLayout rightTitleView;
    private ImageView mLeftImageBtn;
    private TextView mLeftTextBtn;
    private ImageView mRightImageBtn;
    private TextView mRightTextBtn;
    private TextView mTitle;
    public TextView closeText;
    private int titleSize;
    private int textSize;
    private ImageView leftImageDeleteBtn;

    //设置标题字体颜色
    public void setTextColor(int textColor) {
        mTitle.setTextColor(textColor);
        mLeftTextBtn.setTextColor(textColor);
        mRightTextBtn.setTextColor(textColor);
    }

    //设置背景颜色
    public void setTitleViewColor(int color) {
        titleView.setBackgroundColor(color);
    }


    public void setTitle(String text) {
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(text);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize);
    }

    public void setTitle(int stringID) {
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(stringID);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize);
    }

    public String getTitle() {
        return mTitle.getText().toString();
    }

    //显示标题左侧
    public void showLeftButton(OnClickListener listener) {
        leftTitleView.setVisibility(View.VISIBLE);
        leftTitleView.setOnClickListener(listener);
    }

    //隐藏标题左侧
    public void hiddenLeftButton() {
        leftTitleView.setVisibility(View.GONE);
    }

    //设置标题左侧图片内容
    public void setLeftImageButton(int imageID) {
        mLeftImageBtn.setVisibility(View.VISIBLE);
        mLeftImageBtn.setBackgroundResource(imageID);
    }

    //设置标题左侧退出图片内容
    public void setLeftImageDeleteButton(int imageID, OnClickListener listener) {
        leftImageDeleteBtn.setBackgroundResource(imageID);
        leftImageDeleteBtn.setOnClickListener(listener);
    }

    //隐藏标题左侧图片
    public void hiddenLeftImageDeleteButton() {
        leftImageDeleteBtn.setVisibility(View.GONE);
    }

    //显示标题左侧图片
    public void showLeftImageDeleteButton() {
        leftImageDeleteBtn.setVisibility(View.VISIBLE);
    }

    //设置标题左侧文字内容
    public void setLeftTextButton(String text, OnClickListener onClickListener) {
        mLeftTextBtn.setVisibility(View.VISIBLE);
        mLeftTextBtn.setText(text);
        mLeftTextBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        mLeftTextBtn.setOnClickListener(onClickListener);
        leftTitleView.setVisibility(View.VISIBLE);
    }

    //设置标题左侧文字内容
    public void setLeftTextButton(int stringID) {
        mLeftTextBtn.setVisibility(View.VISIBLE);
        mLeftTextBtn.setText(stringID);
        mLeftTextBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }

    //隐藏标题左侧文字
    public void hiddenLeftTextButton() {
        mLeftTextBtn.setVisibility(View.GONE);
    }

    //显示标题右侧
    public void showRightButton() {
        rightTitleView.setVisibility(View.VISIBLE);
    }

    //隐藏标题右侧
    public void hiddenRightButton() {
        rightTitleView.setVisibility(View.GONE);
    }

    //设置标题右侧图片内容
    public void showRightImageButton(int imageID, OnClickListener listener) {
        rightTitleView.setVisibility(View.VISIBLE);
        mRightImageBtn.setOnClickListener(listener);
        mRightImageBtn.setBackgroundResource(imageID);
    }

    //隐藏标题右侧图片
    public void hiddenRightImageButton() {
        mRightImageBtn.setVisibility(View.GONE);
    }

    //显示标题右侧图片
    public void showRightImageButton() {
        mRightImageBtn.setVisibility(View.VISIBLE);
    }


    //设置标题右侧文字内容
    public void setRightTextButton(String text, OnClickListener onClickListener) {
        rightTitleView.setVisibility(View.VISIBLE);
//        mRightTextBtn.setVisibility(View.VISIBLE);
        mRightTextBtn.setText(text);
        mRightTextBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        mRightTextBtn.setOnClickListener(onClickListener);
    }

    //设置标题右侧文字内容
    public void setRightTextButton(int stringID, OnClickListener onClickListener) {
        rightTitleView.setVisibility(View.VISIBLE);
        mRightTextBtn.setText(stringID);
        mRightTextBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        mRightTextBtn.setOnClickListener(onClickListener);
    }

    //隐藏标题右侧文字
    public void hiddenRightTextButton() {
        mRightTextBtn.setVisibility(View.GONE);
    }

    //显示标题右侧文字
    public void showRightTextButton() {
        mRightTextBtn.setVisibility(View.VISIBLE);
    }

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.title_view, this, true);
        initViews();
        initSize();
        hidenAll();
    }

    public void initViews() {
        titleView = (RelativeLayout) findViewById(R.id.title_view);
        leftTitleView = (RelativeLayout) findViewById(R.id.title_left);
        rightTitleView = (RelativeLayout) findViewById(R.id.title_right);
        mLeftImageBtn = (ImageView) findViewById(R.id.left_image_btn);
        leftImageDeleteBtn = (ImageView) findViewById(R.id.left_image_delete_btn);
        mLeftTextBtn = (TextView) findViewById(R.id.left_text_btn);
        mRightImageBtn = (ImageView) findViewById(R.id.right_image_btn);
        mRightTextBtn = (TextView) findViewById(R.id.right_text_btn);
        mTitle = (TextView) findViewById(R.id.title_text);
        closeText = (TextView) findViewById(R.id.close_text);

    }


    public void initSize() {
        int[] screenDispaly = AppUtil.getScreenDispaly(getContext());
        int with = screenDispaly[0];
        int height = screenDispaly[1];
        int titleHeight = (int) (height * 0.07);
        LayoutParams titleViewParams = (LayoutParams) titleView.getLayoutParams();
        titleViewParams.height = titleHeight;
        titleView.setLayoutParams(titleViewParams);
        titleSize = ResourceUtil.getXmlDef(this.getContext(), R.dimen.title_text_size);
        textSize = ResourceUtil.getXmlDef(this.getContext(), R.dimen.title_other_text_size);
    }

    public void setTitlePadding(int padding) {
        leftTitleView.setPadding(padding, 0, padding, 0);
        rightTitleView.setPadding(padding, 0, padding, 0);
    }

    public void hidenAll() {
        mTitle.setVisibility(View.GONE);
        leftTitleView.setVisibility(View.GONE);
        rightTitleView.setVisibility(View.GONE);
        mLeftImageBtn.setVisibility(View.GONE);
        mLeftTextBtn.setVisibility(View.GONE);
        mRightImageBtn.setVisibility(View.GONE);
        mRightTextBtn.setVisibility(View.GONE);
        leftImageDeleteBtn.setVisibility(GONE);
    }

    public void setCloseVisibility(int visibility, OnClickListener listener) {
        closeText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        closeText.setVisibility(visibility);
        closeText.setOnClickListener(listener);
    }
}
