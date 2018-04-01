
package com.jlbeauty.read.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.jlbeauty.read.R;
import com.jlbeauty.read.activity.home.ArticleListActivity;
import com.jlbeauty.read.adapter.RecycleListAdapter;
import com.jlbeauty.read.bean.ArticleInfo;
import com.jlbeauty.read.bean.HomeInfo;
import com.jlbeauty.read.global.Config;
import com.jlbeauty.read.main.ReadApp;
import com.jlbeauty.read.utils.GetArticleList;
import com.jlbeauty.read.utils.GetBanner;
import com.jlbeauty.read.weight.AutoTextView;
import com.rongteng.base.bean.Home_Banner;
import com.rongteng.base.global.Constant;
import com.rongteng.base.mViewPager.CycleViewPager;
import com.rongteng.base.mViewPager.ViewFactory;
import com.rongteng.base.main.BaseFragment;
import com.rongteng.base.pullview.AbPullToRefreshView;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbRefreshUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.utils.AppUtil;
import com.rongteng.base.volley.IRequest;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.webview.WebViewActivity;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 首页
 */
public class HomeFragment extends BaseFragment implements
        GetBanner.OnBannerClick, GetArticleList.OnArticleClick, AbPullToRefreshView.OnHeaderRefreshListener {
    private View view;
    private FragmentActivity mActivity;
    private AutoTextView home_notice_content;
    private AbPullToRefreshView pull;
    // 轮播圆点
    private List<ImageView> views = new ArrayList<>();
    // viewpager的页数
    private int all = 0;

    private RelativeLayout linearNotice;//公告

    //首页应用列表
    private List<HomeInfo> colligateList = new ArrayList<>();
    //公告列表
    private List<ArticleInfo> list = new ArrayList<>();
    //首页banner图列表
    private List<Home_Banner> bannerList = new ArrayList<>();
    //公告轮播间隔时间
    private static final int LONGTIME = 5000;
    //公告轮播条数
    private static final int NOTICE_NUM = 3;
    //公告当前位置
    private int position = 1;
    private Timer timer;
    private boolean isTimer = true;
    private MyTask task;
    private TextView notice_more;

    private GetBanner getBanner;
    private GetArticleList getArticle;

    private HomeFragment homeFragment;
    private CycleViewPager cycleViewPager;
    private android.support.v4.app.FragmentTransaction transaction;
    private RecyclerView recycleView;
    private RecycleListAdapter adapter;
    private LinearLayout brandIntroductionLinear;//美会介绍
    private LinearLayout securityGuaranteeLinear;//美会联盟
    private LinearLayout mediaCoverageLinear;//美会播报
    private LinearLayout platformLinear;//美会商院
    private NestedScrollView scroll;
    private LinearLayout main_title_linear;
    private TextView main_title_text;
    private int height;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        if (ReadApp.mApp.isFirstIn()) {
            ReadApp.mApp.setFirstIn();
        }
        mActivity = getActivity();
        homeFragment = this;
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initTitle() {
    }

    private int totalPage, offset, max = 3;

    @Override
    protected void setData() {
        sendBanner();
        getArticle = new GetArticleList(context, http, Constant.ARTICLE_CODE_NOTICE);
        getArticle.getArticle(offset, max);
        getArticle.setOnArticleClick(this);
        getPortalsOn();
    }


    /**
     * 获取首页应用列表
     */
    private void getPortalsOn() {
        http.post(Config.SERVER_URL_HOME, params, progressTitle, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("首页推荐数据列表", json);
                progressTitle = null;
                pull.onHeaderRefreshFinish();
                if (AbJsonUtil.isSuccess(json)) {
                    TypeToken typeToken = new TypeToken<List<HomeInfo>>() {
                    };//推荐标
                    List<HomeInfo> data = (List<HomeInfo>) AbJsonUtil.fromJson(json, typeToken, "data");
                    if (AbStringUtil.isListEmpty(data)) {
                        setHomeAdapter(data);
                    }

                } else {
                    AbToastUtil.showToast(mActivity, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                pull.onHeaderRefreshFinish();
                progressTitle = null;
                AbToastUtil.showToast(mActivity, message);
            }
        });
    }

    /**
     * 设置首页应用列表
     *
     * @param data
     */
    private void setHomeAdapter(List data) {
        if (colligateList.size() > 0) {
            colligateList.clear();
        }
        colligateList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    /**
     * 加载导航banner
     */
    private void sendBanner() {
        if (getBanner == null) {
            IRequest http1 = new IRequest(context);
            getBanner = new GetBanner(context, http1, Constant.ACTIVITY_BANNER);
            getBanner.setOnBannerClick(this);
        }
        getBanner.getBanner();
    }

    @Override
    protected void setEvent() {
        setOnScrollListener();
        notice_more.setOnClickListener(this);

        platformLinear.setOnClickListener(this);
        mediaCoverageLinear.setOnClickListener(this);
        securityGuaranteeLinear.setOnClickListener(this);
        brandIntroductionLinear.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.equals(notice_more)) {
            ArticleListActivity.startActivity(context, "公告列表", Constant.ARTICLE_CODE_NOTICE);
        } else if (v.equals(platformLinear)) {
            ArticleListActivity.startActivity(context, "美会商院", Constant.ARTICLE_CODE_BUSINESSCOLLEGE);
        } else if (v.equals(securityGuaranteeLinear)) {
            ArticleListActivity.startActivity(context, "美会联盟", Constant.ARTICLE_CODE_UNION);
        } else if (v.equals(brandIntroductionLinear)) {
            ArticleListActivity.startActivity(context, "美会介绍", Constant.ARTICLE_CODE_INTRODUCE);
        } else if (v.equals(mediaCoverageLinear)) {
            ArticleListActivity.startActivity(context, "美会播报", Constant.ARTICLE_CODE_BROADCAST);
        }

    }


    @Override
    protected void initView() {

        addCycleViewPager();

        home_notice_content = (AutoTextView) view.findViewById(R.id.home_notice_content);
        pull = (AbPullToRefreshView) view.findViewById(R.id.pull);
        notice_more = (TextView) view.findViewById(R.id.notice_more);
        scroll = (NestedScrollView) view.findViewById(R.id.scroll);
        linearNotice = (RelativeLayout) view.findViewById(R.id.notice_linear);
        platformLinear = (LinearLayout) view.findViewById(R.id.platformLinear);
        mediaCoverageLinear = (LinearLayout) view.findViewById(R.id.mediaCoverageLinear);
        brandIntroductionLinear = (LinearLayout) view.findViewById(R.id.brandIntroductionLinear);
        securityGuaranteeLinear = (LinearLayout) view.findViewById(R.id.securityGuaranteeLinear);
        main_title_linear = (LinearLayout) view.findViewById(R.id.main_title_linear);
        main_title_text = (TextView) view.findViewById(R.id.main_title_text);

        recycleView = (RecyclerView) view.findViewById(R.id.recycleView);
        recycleView.setNestedScrollingEnabled(false);
        recycleView.setFocusable(false);
        recycleView.setLayoutManager(new LinearLayoutManager(context));

        adapter = new RecycleListAdapter(context, colligateList);
        recycleView.setAdapter(adapter);
        AbRefreshUtil.initRefresh(pull, this);

    }

    private void addCycleViewPager() {
        if (cycleViewPager == null) {
            FragmentManager fragmentManager = getChildFragmentManager();
            transaction = fragmentManager.beginTransaction();
            cycleViewPager = new CycleViewPager();
            transaction.add(R.id.viewPager_content, cycleViewPager, "view_pager");
            transaction.commitAllowingStateLoss();
        }
    }

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;

    @Override
    public void onBannerClick(List<Home_Banner> list) {
        if (bannerList.size() > 0) {
            bannerList.clear();
        }
        bannerList.addAll(list);
        //homeFragment 不能为空或者 homeFragment.getActivity()不能为空，否则会报NullPointerException
        if (homeFragment == null || homeFragment.getActivity() == null || homeFragment.getActivity().isFinishing()) {
            setHomeBanner(bannerList);
        } else {
            MPermissions.requestPermissions(homeFragment, WRITE_EXTERNAL_STORAGE_REQUEST_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(Home_Banner info, int position, View imageView) {
            if (cycleViewPager != null && cycleViewPager.isCycle()) {
                if (!AbStringUtil.isEmpty(info.getLink())) {
                    WebViewActivity.startActivity(mActivity, info.getLink(), info.getTitle());
                }
            }
        }

    };


    @PermissionGrant(WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
    public void requestContactSuccess() {
        setHomeBanner(bannerList);
    }

    @PermissionDenied(WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
    public void requestContactFailed() {
        setHomeBanner(bannerList);
        AbToastUtil.showToast(mActivity, "您禁止了读取存储权限，有些图片将不能被获取");
        AppUtil.getAppDetailSettingIntent();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(homeFragment, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setHomeBanner(List<Home_Banner> list) {
        if (mActivity == null && mActivity.isFinishing()) {
            return;
        }
        if (cycleViewPager == null) {
            addCycleViewPager();
        }
        if (views.size() > 0) {
            views.clear();
        }
        if (list.size() > 1) {
            //当图片大于1的时候需要循环滑动
            //将最后一个ImageView添加进来
            views.add(ViewFactory.getImageView(mActivity, list.get(list.size() - 1).getImgUrl()));
            for (int i = 0; i < list.size(); i++) {
                views.add(ViewFactory.getImageView(mActivity, list.get(i).getImgUrl()));
            }
            // 将第一个ImageView添加进来
            views.add(ViewFactory.getImageView(mActivity, list.get(0).getImgUrl()));
        } else {
            //只有一张图片的时候禁止滑动
            for (int i = 0; i < list.size(); i++) {
                views.add(ViewFactory.getImageView(mActivity, list.get(i).getImgUrl()));
            }
        }

        //设置轮播
        cycleViewPager.setWheel(true);
        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);
        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, list, mAdCycleViewListener);
    }

    //首页公告
    private void setHomeNotice() {
        linearNotice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticleInfo items = list.get(position - 1);
//                ArticleDetailsActivity.startActivity(getActivity(), items.getItemId(), "公告详情");
                WebViewActivity.startActivity((Activity) context, Config.HTML_URL + items.itemId + ".html", "公告详情");
            }
        });
        task = new MyTask();
        if (isTimer) {
            timer = new Timer();
            timer.schedule(task, 1000, LONGTIME);
            isTimer = false;
        } else {
            if (timer != null) {
                timer.cancel();
            }
            timer = new Timer();
            timer.schedule(task, 1000, LONGTIME);
        }

    }

    @Override
    public void onArticleClick(List<ArticleInfo> lists, int totalPage, boolean result) {
        if (result) {
            if (list.size() > 0) {
                list.clear();
            }
            list.addAll(lists);
            setHomeNotice();
        }
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        getPortalsOn();
        sendBanner();
        getArticle.getArticle(offset, max);
    }

    @Override
    public void onResume() {
        if (!isTimer) {
            timer = new Timer();
            task = new MyTask();
            timer.schedule(task, 1000, LONGTIME);

        }
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
            timer = null;
            task = null;
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 10:
                    //首页公告轮播
                    if (list.size() > 0) {
                        if (position < list.size() && position < NOTICE_NUM) {
                            ArticleInfo info = list.get(position);
                            home_notice_content.setText(info.title);
                            position++;
                        } else {
                            ArticleInfo info = list.get(0);
                            home_notice_content.setText(info.title);
                            position = 1;
                        }
                    }
                    break;
                default:
                    break;
            }
        }

    };


    private class MyTask extends TimerTask {

        @Override
        public void run() {
            myHandler.sendEmptyMessage(10);
        }
    }


    //透明度
    private static final int START_ALPHA = 0;
    private static final int START_ALPHA1 = 200;
    private static final int END_ALPHA = 255;

    /**
     * 设置scrollView滑动监听 改变搜索栏的透明度变化
     */
    private void setOnScrollListener() {
        scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                height = main_title_linear.getHeight();
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    AbLogUtil.e(TAG, "滑动到底部");
                }
                if (scrollY > height) {
                    scrollY = height;   //当滑动到指定位置之后设置颜色为纯色，之前的话要渐变---实现下面的公式即可
                }
                setAlpha(scrollY);
            }
        });
    }

    private void setAlpha(int scrollY) {
        if (scrollY > 0) {
            main_title_linear.setVisibility(View.VISIBLE);
        }
        if (height > 0) {
            int alpha = scrollY * (END_ALPHA - START_ALPHA) / height + START_ALPHA;
            main_title_text.setTextColor(Color.argb(alpha, 255, 255, 255));
            main_title_linear.getBackground().setAlpha(alpha);
        }

    }
}
