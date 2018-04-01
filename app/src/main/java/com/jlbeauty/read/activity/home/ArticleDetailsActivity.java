package com.jlbeauty.read.activity.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.bean.NoticeDetailsInfo;
import com.jlbeauty.read.global.Config;
import com.jlbeauty.read.utils.ImageGet;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.webview.MyShareSdk;
import com.rongteng.base.weight.TitleView;


/**
 * 文章详情
 *
 * @author Administrator
 */
public class ArticleDetailsActivity extends BaseActivity {
    private TitleView titleView;
    private String title;
    private TextView mTime;
    private TextView mContent;
    private int id;
    private TextView mTitle;
    private NoticeDetailsInfo mInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_notice_details);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        title = intent.getStringExtra("title");
        if (title != null) {
            titleView.setTitle(title);
        } else {
            titleView.setTitle("公告详情");
        }
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.showRightImageButton(R.drawable.share_icon, new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInfo != null) {
                    MyShareSdk shareSdk = new MyShareSdk(context);
                    shareSdk.showShare(null, mInfo.getTitle(), Config.HTML_URL + id + ".html", mInfo.getTitle());
                }
            }
        });

    }

    @Override
    protected void initView() {
        mTitle = (TextView) findViewById(R.id.tv_title);
        mContent = (TextView) findViewById(R.id.tv_content);
        mTime = (TextView) findViewById(R.id.tv_time);
    }

    /**
     * 跳转到 ArticleDetailsActivity
     *
     * @param context 指定跳转的activity
     * @param id      公告ID
     */
    public static void startActivity(Context context, int id, String title) {
        Intent intent = new Intent(context, ArticleDetailsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    protected void setData() {
        params.put("itemId", id + "");
        http.post(Config.URL_GETARTICLEDETAILON, params, progressTitle, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("文章详情", json.toString());
                if (AbJsonUtil.isSuccess(json)) {
                    mInfo = (NoticeDetailsInfo) AbJsonUtil.fromJson(json, NoticeDetailsInfo.class);
                    if (mInfo != null) {
                        progressTitle = null;
                        titleView.showRightImageButton();
                        if (!AbStringUtil.isEmpty(mInfo.getTime())) {
                            mTime.setText(mInfo.getTime());
                        }
                        if (!AbStringUtil.isEmpty(mInfo.getTitle())) {
                            mTitle.setText(mInfo.getTitle());
                        }
                        if (!AbStringUtil.isEmpty(mInfo.getContent())) {
                            mContent.setText(Html.fromHtml(mInfo.getContent(), new ImageGet(ArticleDetailsActivity.this, mContent), null));
                        }
                    }
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                AbToastUtil.showToast(context, message);
            }
        });
    }

    @Override
    protected void setEvent() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
