package com.rongteng.base.global;

/**
 * Created by geek on 2016/6/21.
 * 全局属性
 */
public class Constant {
    public static String base_data_path = "rongteng";// 需要存储在SD卡上的文件的基路径
    public static String images_cache_path = "images_cache";// 存储图片缓存
    public static String ZHICHI_APP_KEY = "df3caf9bb0b645e2a9f875829a5f8b1d";//智齿APPkey

    //选择账户页面广播
    public static String ACCOUNT_ACTION = "com.rongteng.Activity.userCenter.SelectAccountModeActivity";
    /**
     * UI的主色调
     * colorPrimary 橘色
     */
    public static String colorPrimary = "#FF7C52";
    //客服电话
    public static String CUSTOMER_SERVICE_PHONE = "";
    /**
     * UI设计的基准宽度.
     */
    public static final int UI_WIDTH = 720;

    /**
     * UI设计的基准高度.
     */
    public static final int UI_HEIGHT = 1280;

    /**
     * UI设计的密度.
     */
    public static final int UI_DENSITY = 2;

    /**
     * 所有的金额和百分比四舍五入后的位数
     */
    public static final int ROUND_DIGIT = 2;

    /**
     * 请求网络时显示的文字信息 正在加载...
     */
    public static final String LOADING = "正在加载...";
    /**
     * 客户端设备标识 "android"
     */
    public static final String CLIENT_ID = "2";
    /**
     * 客户端设备标识 "android"
     */
    public static final String CLIENT_ID_NAME = "android";

    /**
     * 签名key
     */
    public static final String APPSECRET = "f4b9ebc5-dcae-4214-9e45-216327d2b8ca";


    /**
     * 签名key
     */
    public static final String LOANAPPSECRET = "dqA2UNYnfB6gpksWEaVGm+KunbAxOw8MFs3oAZQGJUwGZPfL6tufGeyP8nv/bVwq";
    /**
     * AppKey
     */
    public static final String APPKEY = "APP5f8e6bfa";

    /***
     * 第一次登录
     */
    public static final String APP_CUR_VERSION = "appCurVersion";
    /**
     * The  CONNECTEXCEPTION.
     */
    public static final String CONNECT_EXCEPTION = "网络连接失败，请稍后再试";

    /**
     * The  SOCKETEXCEPTION.
     */
    public static final String SOCKET_EXCEPTION = "网络异常，请稍后再试";

    /**
     * The  SOCKETTIMEOUTEXCEPTION.
     */
    public static final String SOCKET_TIMEOUT_EXCEPTION = "网络连接超时，请稍后再试";

    /**
     * 资源未找到.
     */
    public static final String NOT_FOUND_EXCEPTION = "请求资源无效404";

    /**
     * 没有权限访问资源.
     */
    public static final String FORBIDDEN_EXCEPTION = "没有权限访问资源";

    /**
     * The  REMOTESERVICEEXCEPTION.
     */
    public static final String SERVICE_UNAVAILABLE = "服务器正在维护，请稍后再试";

    /**
     * The  UNKNOWNHOSTEXCEPTION.
     */
    public static final String UNKNOWN_HOST_EXCEPTION = "连接服务器失败，请稍后再试";

    /**
     * The  ANALYSIS_DATA_EXCEPTION.
     */
    public static final String ANALYSIS_DATA_EXCEPTION = "服务器响应异常，请稍后再试";

    /**
     * The  DATA_EXCEPTION.
     */
    public static final String DATA_EXCEPTION = "服务器响应异常，请稍后再试";
    /**
     * 其他异常.
     */
    public static final String UNTREATED_EXCEPTION = "未处理的异常";

    /**
     * 登陆已过期，请重新登录
     */
    public static final String LOGIN_EXCEPTION = "登陆已过期，请重新登录";

    /**
     * 已加载全部
     */
    public static final String LOADED = "已加载全部";

    /**
     * 银行信息获取失败提示
     */
    public static final String BANK_INFO = "银行信息获取失败，请重新获取";

    /**
     * 获取banner接口，不传递code参数时，默认code为'app'
     * code的参数类型有以下：
     * activity_banner : 移动端活动专区
     * projectAD : 移动端标内广告
     * invite : 移动端邀请好友
     * Register_banner : 移动端注册页面
     * MAU-banner  我的里面用户没有登录的时候的提示框背景
     * startup 启动页图片
     */
    public static final String ALL_BANNER = "all";
    public static final String ACTIVITY_BANNER = "activity_banner";
    public static final String PROJECTAD_BANNER = "projectAD";
    public static final String INVITE_BANNER = "invite_banner";
    public static final String REGISTER_BANNER = "Register_banner";
    public static final String MAU_BANNER = "MAU-banner";
    public static final String STARTUP = "startup";

    /**
     * 文章分类code，不传则查询所有分类下的文章，
     * 具体code在服务端后台可以查阅
     * notice(公告)，
     * help(帮助中心)，
     * medianews(媒体报道)
     */
    public static final String ARTICLE_CODE_NOTICE = "notice";//公告
    public static final String ARTICLE_CODE_HELP = "help";//(帮助中心
    public static final String ARTICLE_CODE_MEDIANEWS = "medianews";//媒体报道)
    public static final String ARTICLE_CODE_DAILYREADING = "dailyreading";// 每日美读
    public static final String ARTICLE_CODE_RECOMMEND = "recommend";//特别推荐
    public static final String ARTICLE_CODE_FORECAST = "forecast";//节目预报
    public static final String ARTICLE_CODE_BUSINESSCOLLEGE = "businesscollege";//美会商院
    public static final String ARTICLE_CODE_INTRODUCE = "introduce";//美会介绍
    public static final String ARTICLE_CODE_BROADCAST = "broadcast";//美会播报
    public static final String ARTICLE_CODE_UNION = "union";//美会联盟
    public static final String ARTICLE_CODE_LATESTACTIVITY = "latestactivity";//最新活动
    public static final String ARTICLE_CODE_WOMENECONOMY = "womeneconomy";//女性经济
    public static final String ARTICLE_CODE_WOMENNEWS = "womennews";//女性新闻



    /**
     * 修改用户信息入口的类型
     * updatePhone  修改手机号
     * forgetPass 忘记登录密码
     * forgetDealPass 忘记交易密码
     * updateName 修改用户名
     * updateLogin 修改登录密码
     * updateDealPass 修改交易密码
     */
    public static final String UPDATE_TYPE_UPDATEPHONE = "updatePhone";
    public static final String UPDATE_TYPE_FORGETPASS = "forgetPass";
    public static final String UPDATE_TYPE_FORGETDEALPASS = "forgetDealPass";
    public static final String UPDATE_TYPE_UPDATENAME = "updateName";
    public static final String UPDATE_TYPE_UPDATELOGIN = "updateLogin";
    public static final String UPDATE_TYPE_UPDATEDEALPASS = "updateDealPass";
}
