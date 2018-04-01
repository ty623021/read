package com.rongteng.base.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Environment;
import android.text.TextUtils;

import com.mob.MobSDK;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.rongteng.base.R;
import com.rongteng.base.bean.UserAccount;
import com.rongteng.base.bean.UserLogin;
import com.rongteng.base.global.ActivityManager;
import com.rongteng.base.global.Constant;
import com.rongteng.base.volley.InitSSLSocketFactory;
import com.spinytech.macore.MaApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import okhttp3.OkHttpClient;

public class MyApplication extends MaApplication {
    private String TAG = this.getClass().getSimpleName();
    private static Context context;
    public static MyApplication mApp;
    public Session session;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        context = getApplicationContext();
        this.session = new SharedPreferencesSession(this);
        initOkHttp();//初始化okHttp
        initImageLoader();//初始化glide
        initGallery();//初始化Gallery
        MobSDK.init(this);//初始化分享
    }

    public static Context getContext() {
        return context;
    }

    private void initImageLoader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true)
                .showImageOnFail(R.drawable.default_banner).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .diskCache(new UnlimitedDiskCache(new File(Environment
                        .getExternalStorageDirectory()
                        + File.separator
                        + Constant.base_data_path
                        + File.separator
                        + Constant.images_cache_path)))
                .defaultDisplayImageOptions(options). // 上面的options对象，一些属性配置
                build();
        ImageLoader.getInstance().init(config); // 初始化
    }

    private void initOkHttp() {
        InputStream caInput = new ByteArrayInputStream(InitSSLSocketFactory.load.getBytes());
        InputStream[] inputStreams = new InputStream[]{caInput};
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(inputStreams, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(40000L, TimeUnit.MILLISECONDS)
                .readTimeout(40000L, TimeUnit.MILLISECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                //其他配置
//                .cookieJar(new CookieJar() {
//                    //理财登录接口
//                    private String wealth_url_login = Constant.WEALTH_SERVER_URL + "/mimosa/client/investor/baseaccount/login";
//
//                    @Override
//                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//                        if (wealth_url_login.equals(url.toString())) {
//                            String json = AbJsonUtil.toJson(cookies);
//                            saveWealthCookie(json);
//                        }
//                    }
//
//                    @Override
//                    public List<Cookie> loadForRequest(HttpUrl url) {
//                        List<Cookie> cookies;
//                        if (url.toString().startsWith(Constant.WEALTH_SERVER_URL)) {
//                            String cookie = getWealthCookie();
//                            TypeToken typeToken = new TypeToken<List<Cookie>>() {
//                            };
//                            cookies = (List<Cookie>) AbJsonUtil.fromJson(cookie, typeToken);
//                        } else {
//                            cookies = null;
//                        }
//                        return cookies != null ? cookies : new ArrayList<Cookie>();
//                    }
//                })
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    private void initGallery() {
        //设置主题
        // ThemeConfig.CYAN
        /**
         * 配置选择图片的样式
         setTitleBarTextColor//标题栏文本字体颜色
         setTitleBarBgColor//标题栏背景颜色
         setTitleBarIconColor//标题栏icon颜色，如果设置了标题栏icon，设置setTitleBarIconColor将无效
         setCheckNornalColor//选择框未选颜色
         setCheckSelectedColor//选择框选中颜色
         setCropControlColor//设置裁剪控制点和裁剪框颜色
         setFabNornalColor//设置Floating按钮Nornal状态颜色
         setFabPressedColor//设置Floating按钮Pressed状态颜色

         setIconBack//设置返回按钮icon
         setIconCamera//设置相机icon
         setIconCrop//设置裁剪icon
         setIconRotate//设置选择icon
         setIconClear//设置清楚选择按钮icon（标题栏清除选择按钮）
         setIconFolderArrow//设置标题栏文件夹下拉arrow图标
         setIconDelete//设置多选编辑页删除按钮icon
         setIconCheck//设置checkbox和文件夹已选icon
         setIconFab//设置Floating按钮icon
         setEditPhotoBgTexture//设置图片编辑页面图片margin外背景
         setIconPreview设置预览按钮icon
         setPreviewBg设置预览页背景
         */
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(Color.rgb(75, 174, 254))
                .setTitleBarTextColor(Color.WHITE)
                .setFabNornalColor(Color.rgb(75, 174, 254))
                .setFabPressedColor(Color.rgb(10, 123, 218))
                .setCheckSelectedColor(Color.rgb(75, 174, 254))
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();
        //配置imageloader
        GlideImageLoader imageloader = new GlideImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(context, imageloader, theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }


    @Override
    public void initializeAllProcessRouter() {

    }

    @Override
    protected void initializeLogic() {

    }

    @Override
    public boolean needMultipleProcess() {
        return false;
    }


    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * 保存用户资产信息
     *
     * @param user
     */
    public void saveUserAccount(UserAccount user) {
        session.set("wealth_balance", user.getBalance() + "");
        session.set("wealth_allMoney", user.getCapitalAmoun() + "");
        session.set("balance", user.getAvailableBalance() + "");
        session.set("allMoney", user.getAllMoney() + "");
        session.set("preMobile", user.getPreMobile() + "");
        session.set("investMoney", user.getInvestMoney() + "");
        session.set("borrowMoney", user.getBorrowMoney() + "");
        session.set("investAvailMoney", user.getInvestAvailBalance() + "");//投资账户可提现金额
        session.set("borrowAvailMoney", user.getBorrowAvailBalance() + "");//借款账户可提现金额
        session.set("expAmount", user.getExpAmount() + "");
        session.set("integral", user.getIntegral() + "");
        session.set("realName", user.getRealName() + "");
        session.putBoolean("isOPenAccount", user.isOPenAccount());
        session.putBoolean("isDepositBank", user.isDepositBank());
        session.putBoolean("isBindBank", user.isBindBank());
        session.putBoolean("isBeginner", user.isBeginner());
        session.putBoolean("isBanSubName", user.isBanSubName());
        session.putBoolean("isOutPwdSafe", user.isOutPwdSafe());
        session.putBoolean("personalStatus", true);
    }

    /**
     * 返回 true:已登录 ,false 没登录
     */
    @SuppressLint("NewApi")
    public boolean hasLogin() {
        return !(TextUtils.isEmpty(session.get("TOKEN")) || TextUtils.isEmpty(session.get("id")));
    }

    /**
     * 退出
     */
    public static void exit() {
        ActivityManager.getInstance().clearAllActivity();
    }

    /**
     * 判断是否首次启动app
     *
     * @return true 代表用户是第一次登录 false 代表不是第一次登录
     */
    public boolean isFirstIn() {
        String appCurVesion = this.session.get(Constant.APP_CUR_VERSION);
        return !(appCurVesion != null && appCurVesion.equals(getAppVersionName()));
    }

    public void setFirstIn() {
        this.setSessionVal(Constant.APP_CUR_VERSION, getAppVersionName());
    }

    // 获取当前app的版本号
    public String getAppVersionName() {
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packInfo;
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    // 依据key value 存储数据
    public void setSessionVal(String key, String value) {
        this.session.set(key, value);
    }

    /**
     * 保存用户登录注册信息
     *
     * @param user
     */
    public void saveUserLogin(UserLogin user) {
        session.set("TOKEN", user.getAccessToken());
        session.set("id", user.getId());
        session.set("username", user.getUsername());
        session.set("userPhone", user.getMobile());
        session.set("expAmount", user.getRegVoucher() + "");
        session.putBoolean("personalStatus", true);
        session.putBoolean("isLogin", true);
        setGesture(true);//开启手势密码
        setSkip(false);//选择不跳过手势密码
    }

    // 退出清空session
    public void logout() {
        this.session.set("TOKEN", "");
        this.session.set("WealthCookie", "");
        this.session.set("id", "");
        this.session.set("JSESSIONID", "");
        this.session.set("username", "");
        this.session.putBoolean("isSkip", false);
        this.session.putBoolean("isBindBank", false);
        this.session.putBoolean("isBanSubName", false);
        this.session.set("wealth_bankPhone", "");
        this.session.putBoolean("isOPenAccount", false);
        this.session.putBoolean("isDepositBank", false);
    }

    /**
     * 获取可用余额
     *
     * @return
     */
    public String getBalance() {
        return session.get("balance");
    }

    /**
     * 获取银行预留手机号码
     *
     * @return
     */
    public String getPreMobile() {
        return session.get("preMobile");
    }

    /**
     * 用户是否开通了银行存管
     *
     * @return
     */
    public boolean isOPenAccount() {
        return session.getBoolean("isOPenAccount", false);
    }

    /**
     * 用户是否绑定了托管
     *
     * @return
     */
    public boolean isDepositBank() {
        return session.getBoolean("isDepositBank", false);
    }


    /**
     * 设置网贷总可用余额
     *
     * @return
     */
    public void setBalance(double balance) {
        session.set("balance", balance + "");
    }

    /**
     * 获取网贷投资可用余额
     *
     * @return
     */
    public String getInvestBalance() {
        return session.get("investMoney");
    }

    /**
     * 获取网贷借款可用余额
     *
     * @return
     */
    public String getBorrowBalance() {
        return session.get("borrowMoney");
    }

    /**
     * 获取网贷投资账户可提现金额
     *
     * @return
     */
    public String getInvestAvailBalance() {
        return session.get("investAvailMoney");
    }

    /**
     * 获取网贷借款账户可提现金额
     *
     * @return
     */
    public String getBorrowAvailBalance() {
        return session.get("borrowAvailMoney");
    }


    public String getWealthBalance() {
        return session.get("wealth_balance");
    }

    /**
     * @param balance
     */
    public void setWealthBalance(double balance) {
        session.set("wealth_balance", balance + "");
    }

    /**
     * 获取网贷银行预留手机号
     *
     * @return
     */
    public String getWealthBankPhone() {
        return session.get("wealth_bankPhone");
    }

    /**
     * 设置网贷银行预留手机号
     *
     * @param bankPhone
     */
    public void setWealthBankPhone(String bankPhone) {
        session.set("wealth_bankPhone", bankPhone + "");
    }

    public String getWealthAllMoney() {
        return session.get("wealth_allMoney");
    }

    /**
     * 设置我的中心 网络请求状态值
     *
     * @param status
     */
    public void setPersonalStatus(boolean status) {
        session.putBoolean("personalStatus", status);
    }

    public boolean isPersonalStatus() {
        return session.getBoolean("personalStatus", true);
    }

    /**
     * 根据ID获取手势密码
     *
     * @return
     */
    public String getGesture() {
        return session.get(getID());
    }

    /**
     * 根据ID保存手势密码
     *
     * @param gesture
     */
    public void saveGesture(String gesture) {
        session.set(getID(), gesture);
    }


    /**
     * 获取积分商城ID
     *
     * @return
     */
    public String getMallUserId() {
        return session.get("mall_user_id");
    }

    /**
     * 保存积分商城ID
     *
     * @return
     */
    public void saveMallUserId(String user_id) {
        session.set("mall_user_id", user_id + "");
    }

    /**
     * 获取理财登录接口cookie
     *
     * @return
     */
    public String getWealthCookie() {
        return session.get("WealthCookie");
    }

    /**
     * 保存理财登录接口cookie
     *
     * @return
     */
    public void saveWealthCookie(String cookie) {
        session.set("WealthCookie", cookie + "");
    }


    /**
     * 获取保存的用户名
     *
     * @return
     */
    public String getUserNmae() {
        return session.get("username");
    }

    public void setUserName(String userName) {
        session.set("username", userName);
    }

    /**
     * 获取保存的用户真实姓名
     *
     * @return
     */
    public String getRealName() {
        return session.get("realName");
    }

    public void setUserPhone(String userPhone) {
        session.set("userPhone", userPhone);
    }

    /**
     * 获取保存的手机号码
     *
     * @return
     */
    public String getUserPhone() {
        return session.get("userPhone");
    }

    /**
     * 获取保存的用户ID
     * memberId
     *
     * @return
     */
    public String getID() {
        return session.get("id");
    }

    /**
     * 获取保存的用户TOKEN
     *
     * @return
     */
    public String getToken() {
        return session.get("TOKEN");
    }

    /**
     * 获取保存的用户的选择（是否设置手势密码）
     *
     * @return
     */
    public boolean getSkip() {
        return session.getBoolean("isSkip", false);
    }

    /**
     * 保存用户的选择
     *
     * @param isSkip
     */
    public void setSkip(boolean isSkip) {
        session.putBoolean("isSkip", isSkip);
    }

    /**
     * 记住帐号
     *
     * @return
     */
    public boolean getRemember() {
        return session.getBoolean("remember", true);
    }

    /**
     * 记住帐号
     *
     * @param remember
     */
    public void setRemember(boolean remember) {
        session.putBoolean("remember", remember);
    }

    /**
     * 设置是否开启手势密码
     *
     * @param is
     */
    public void setGesture(boolean is) {
        session.putBoolean("isGesture", is);
    }

    /**
     * 获取是否开启手势密码
     *
     * @return
     */
    public boolean isGesture() {
        return session.getBoolean("isGesture", true);
    }

    /**
     * 判断APP是否需要加载补丁包
     * * @return
     */
    public boolean isLoadPatch() {
        return session.getBoolean("LoadPatch", false);
    }

    public void setLoadPatch(boolean is) {
        session.putBoolean("LoadPatch", is);
    }

}
