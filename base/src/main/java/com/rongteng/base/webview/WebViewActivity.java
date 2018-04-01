package com.rongteng.base.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rongteng.base.R;
import com.rongteng.base.global.Constant;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.main.MyApplication;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbRouterUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AppUtil;
import com.rongteng.base.weight.MyProgressBar;
import com.rongteng.base.weight.TitleView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class WebViewActivity extends BaseActivity implements JavaScriptObj.OnShareClick {
    private static final String ACCEPT_ENCODING = "Accept-Encoding";
    private static final String RONGTENG = "read";
    private static final String ANDROID = "read-android";
    private WebView webview;
    private String titleshow;
    private String http_url;
    private TitleView titleView;
    private MyProgressBar progress_bar_horizontal;
    private String viewTitle;//当前页面的标题
    private String viewUrl;//当前页面的URL地址
    private HashMap<String, String> map;
    private JavaScriptObj javaScriptObj;
    private String shareContent;   //分享内容
    private String shareUrl = "";//分享链接默认为空，当这个页面需要分享的时候回赋值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_webview);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Constant.colorPrimary));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    /**
     * 跳转到WebViewActivity
     *
     * @param context 指定跳转的activity
     * @param url     url
     * @param title   标题
     */
    public static void startActivity(Activity context, String url, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    private boolean setTitleView() {
        Intent intent = this.getIntent();
        http_url = intent.getStringExtra("url");
        titleshow = intent.getStringExtra("title");
        if (TextUtils.isEmpty(http_url)) {
            return true;
        }
        if (AbStringUtil.isEmpty(titleshow)) {
            titleView.setTitle(getResources().getString(R.string.app_name));
        } else {
            titleView.setTitle(titleshow);
        }
        titleView.setLeftImageButton(R.drawable.back);

        titleView.showLeftButton(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });

        titleView.setLeftImageDeleteButton(R.drawable.close_img, new OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.this.finish();
            }
        });

        return false;
    }

    /**
     * 关闭
     */
    private void closeActivity() {
        if (webview.canGoBack()) {
            if (viewUrl.indexOf("http://www.d1dzb.com/") != -1) {//判断是否在洪河苑商城
                if (http_url.equals(viewUrl)) {
                    finish();
                } else {
                    webview.goBack();
                }
            } else {
                webview.goBack();
            }
        } else {
            isSource();
        }
    }

    /**
     * 判断是否显示分享
     *
     * @param isShow
     */
    private void isShowLeftOrRight(boolean isShow) {
        if (isShow) {//需要显示分享
            titleView.showRightImageButton();
            titleView.showRightImageButton(R.drawable.share_icon, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyShareSdk shareSdk = new MyShareSdk(context);
                    if (AbStringUtil.isEmpty(shareContent)) {
                        shareContent = viewTitle;
                    }
                    shareSdk.showShare(null, shareContent, viewUrl, shareContent);
                }
            });
            if (webview != null && webview.canGoBack()) {
                titleView.showLeftImageDeleteButton();
            } else {
                titleView.hiddenLeftImageDeleteButton();
            }
        } else {//不需要显示分享
            titleView.hiddenLeftImageDeleteButton();
            titleView.showRightImageButton(R.drawable.close_img, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebViewActivity.this.finish();
                }
            });
            if (webview != null && webview.canGoBack()) {
                titleView.showRightImageButton();
            } else {
                titleView.hiddenRightImageButton();
            }
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void initView() {
        titleView = (TitleView) findViewById(R.id.title);
        webview = (WebView) findViewById(R.id.webview);
        if (setTitleView()) return;
        progress_bar_horizontal = (MyProgressBar) findViewById(R.id.progress_bar_horizontal);
        setWebView();
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //在三星手机中第一次进入的时候会调用次方法，当页面goBack()的时候不会再执行此方法
                viewTitle = title;
                if (viewTitle != null) {
                    setWebTitle(http_url);
                    setPageDescribe(viewTitle);
                }
            }

            public void onProgressChanged(WebView view, int newProgress) {
                if (progress_bar_horizontal.getVisibility() != View.VISIBLE) {
                    progress_bar_horizontal.setVisibility(View.VISIBLE);
                }
                progress_bar_horizontal.setAnimProgress2(newProgress);
            }

        });

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                viewUrl = url;//记录当前加载的url
                AbLogUtil.e("onPageStarted", viewUrl);
                if (viewUrl.indexOf("http://www.d1dzb.com/") != -1) {//判断是否在洪河苑商城
                    if (http_url.equals(viewUrl)) {
                        setTitleViewHeight(titleView, true);
                    } else {
                        setTitleViewHeight(titleView, false);
                    }
                } else {
                    titleView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    syncCookie(url, map);
                    return false;
                } else if (url.startsWith("tel:")) {//拨号
                    AppUtil.call(context, url.split(":")[1]);
                    return true;
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                viewTitle = view.getTitle();
                setWebTitle(url);
                if (shareUrl.equals(url)) {//判断是否显示需要分享
                    isShowLeftOrRight(true);
                } else {
                    isShowLeftOrRight(false);
                }
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                AbLogUtil.e("onReceivedError", view.getUrl());
                super.onReceivedError(view, request, error);
            }


        });

    }

    private void setWebTitle(String url) {
        titleView.setTitle(viewTitle);
    }


    private void setWebView() {
        webview.getSettings().setJavaScriptEnabled(true);//支持JS
        webview.getSettings().setBlockNetworkImage(false);//解决图片不显
        webview.getSettings().setSupportZoom(true);// 设置可以支持缩放
        webview.getSettings().setBuiltInZoomControls(true);// 设置出现缩放工具
        webview.getSettings().setDomStorageEnabled(true);//当某些标签的不支持的时候加上
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);//扩大比例的缩放
        webview.getSettings().setDisplayZoomControls(false);//不显示webView缩放按钮
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //在5.0以下的系统即使不加这句话，图片也可以正常显示
            // 兼容https链接内容中有http的链接图片不显示的bug
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //设置缓存方式
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型：
         * 1、LayoutAlgorithm.NARROW_COLUMNS ：适应内容大小
         * 2、LayoutAlgorithm.SINGLE_COLUMN : 适应屏幕，内容将自动缩放
         */
        webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

        map = new HashMap<>();
        map.put(ACCEPT_ENCODING, "gzip");
        map.put(RONGTENG, ANDROID);
        map.put("x-auth-token", MyApplication.mApp.getSession().get("TOKEN"));
        javaScriptObj = new JavaScriptObj(this);
        javaScriptObj.setOnShareClick(this);
        webview.addJavascriptInterface(javaScriptObj, "android");
//        http_url = "file:///android_asset/index.html";//加载本地测试文件
        syncCookie(http_url, map);
        webview.loadUrl(http_url, map);
    }

    /**
     * 将cookie同步到WebView
     *
     * @param url WebView要加载的url
     * @param map 要同步的cookie
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    public boolean syncCookie(String url, Map<String, String> map) {
        String newCookie;
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                CookieSyncManager.createInstance(context);
            }
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();//移除
            for (String key : map.keySet()) {
                String value = key + "=" + map.get(key);
                cookieManager.setCookie(url, value);
            }
            newCookie = cookieManager.getCookie(url);
        } catch (Exception e) {
            e.printStackTrace();
            newCookie = "";
        }
        return TextUtils.isEmpty(newCookie) ? false : true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            closeActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void isSource() {
        finish();
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setEvent() {

    }


    @Override
    protected void initTitle() {

    }

    @Override
    public void onShareClick(String content) {
        //当页面需要被分享的时候回调
        this.shareUrl = viewUrl;
        this.shareContent = content;
    }

    /**
     * 清除WebView缓存
     */
    public void clearWebViewCache() {

        if (webview != null) {
            webview.removeAllViews();
            webview.destroy();
        }
        //清理Webview缓存数据库
        try {
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //WebView 缓存文件
        File appCacheDir = new File(getFilesDir().getAbsolutePath() + "/webcache");
        File webviewCacheDir = new File(getCacheDir().getAbsolutePath() + "/webviewCache");
        //删除webview 缓存目录
        if (webviewCacheDir.exists()) {
            this.deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if (appCacheDir.exists()) {
            deleteFile(appCacheDir);
        }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearWebViewCache();
    }
}
