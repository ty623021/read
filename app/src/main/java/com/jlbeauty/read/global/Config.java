package com.jlbeauty.read.global;


import com.jlbeauty.read.BuildConfig;
import com.rongteng.base.global.Constant;

/**
 * 接口地址
 */
public class Config {
    //接口地址
    public static final String SERVER_URL;
    //H5地址
    public static final String SERVER_URL_MMM;

    static {
        SERVER_URL = BuildConfig.SERVER_URL;
        SERVER_URL_MMM = BuildConfig.SERVER_URL_M;
    }

    /**
     * UI的主色调
     * colorPrimary 橘色
     */
    public static String colorPrimary = Constant.colorPrimary;

    /**
     *
     */
    public static final String HTML_URL = SERVER_URL + "";
    /**
     *
     */
    public static final String URL_INFO = SERVER_URL + "";
    /**
     * 根据(新闻/视频)分类typeCode获取(新闻/视频)页面列表，支持分页参数offset,max
     */
    public static final String URL_GETARTICLELISTON = SERVER_URL + "/wd_api/article/getArticleListOn";

    /**
     * 首页展示数据接口
     */
    public static final String SERVER_URL_HOME = SERVER_URL + "/wd_api/article/getIndexArticleOn";

    /**
     *
     */
    public static final String URL_CUNGUAN = SERVER_URL + "";

    /**
     *
     */
    public static final String URL_SECURITY = SERVER_URL + "";
    /**
     *
     */
    public static final String URL_INTRODUCE = SERVER_URL + "";
    /**
     *
     */
    public static final String URL_GETARTICLEDETAILON = SERVER_URL + "";
    /**
     *
     */
    public static final String URL_GETBANNERON = SERVER_URL + "/wd_api/banner/getBannerOn";
    /**
     *
     */
    public static final String URL_SYSCONFIG = SERVER_URL + "";
    /**
     * 版本检测
     */
    public static final String URL_VERSIONON = SERVER_URL + "/wd_api/userCenter/versionOn";

    /**
     * 获取活动列表，支持分页参数offset,max
     */
    public static final String URL_ACTIVITYLIST = SERVER_URL + "/wd_api/banner/getActivityListOn";


    /**
     * 找回登入密码(根据验证码设置新密码)
     */
    public static final String URL_SENDRETRIEVEPWDCODEON = SERVER_URL + "/uc/api/userCenter/sendRetrievePwdCodeOn";


    /**
     *发送短信验证码
     */
    public static final String URL_SENDVERIFYFORLOGINON = SERVER_URL + "/uc/api/userCenter/sendVerifyOn";
    /**
     *校验验证码是否正确
     */
    public static final String URL_CHECKVERIFYON = SERVER_URL + "/uc/api/userCenter/checkVerifyOn";

    /**
     *注册
     */
    public static final String URL_REGISTERON = SERVER_URL + "/uc/api/userCenter/registerOn";
    /**
     *检查手机号码是否已经注册
     */
    public static final String URL_CHECKMOBILE = SERVER_URL + "/uc/api/userCenter/checkMobileOn";

    /**
     *找回登入密码(根据验证码设置新密码)
     */
    public static final String URL_DORETRIEVEPWDRESETON = SERVER_URL + "/uc/api/userCenter/doRetrievePwdResetOn";
    /**
     *修改登入密码(登入状态下根据旧密码，根据memberId)
     */
    public static final String URL_EDITPASSWORD = SERVER_URL + "/uc/api/userCenter/editPassword";
    /**
     *更换手机号码
     */
    public static final String URL_REVALIDMOBILE = SERVER_URL + "/uc/api/userCenter/reValidMobile";

    /**
     *
     */
    public static final String URL_BIND_EMAIL = SERVER_URL + "";
    /**
     *登录
     */
    public static final String URL_LOGINON = SERVER_URL + "/uc/api/userCenter/loginOn";

    /**
     *获取短信验证码
     */
    public static final String URL_SENDVERIFYON = SERVER_URL + "/uc/api/userCenter/sendVerifyOn";

    /**
     *获取用户实名信息
     */
    public static final String URL_GETMEMBERCERTIINFO = SERVER_URL + "/uc/api/member/getMemberCertiInfo";

    /**
     *修改用户名
     */
    public static final String URL_UPDATEUSERNAME = SERVER_URL + "/uc/api/member/updateUserName";

    /**
     *邀请好友列表數據
     */
    public static final String URL_GETINVITEDETAIL = SERVER_URL + "/wd_api/inviteIndex/getInviteDetail";

    /**
     *邀请好友数据
     */
    public static final String URL_GETINVITE = SERVER_URL + "/wd_api/inviteIndex/getInvite";
    /**
     *图片上传接口-支持[jpg,png,bmp]
     */
    public static final String URL_UPLOADIMGON = SERVER_URL + "/wd_api/userCenter/uploadImgOn";

    /**
     *意见反馈
     */
    public static final String URL_FEEDBACKON = SERVER_URL + "/wd_api/userCenter/feedBackOn";
    /**
     *
     */
    public static final String DO_TASK_TOEARNINTEGRAL_URL = SERVER_URL + "";


    /**
     *
     */
    public static final String GET_INTEGRAL_LIST_URL = SERVER_URL + "";
    /**
     * 商城签到
     */
    public static final String URL_MALL_SIGN = SERVER_URL + "";

    /**
     * 积分兑换首页
     */
    public static final String URL_MALL_INTEGRAL_HOME = SERVER_URL + "wd_api/mallIntegral/firstPage";

    /**
     * 积分兑换列表
     */
    public static final String URL_MALL_INTEGRAL_WAITLIST = SERVER_URL + "wd_api/mallIntegral/waitList";
    /**
     * 积分兑换详情
     */
    public static final String URL_CONVERT_VOUCHER_DETAILS = SERVER_URL + "wd_api/mallIntegral/convertVoucherDetails";
    /**
     * 立即兑换
     */
    public static final String URL_CONVERSION = SERVER_URL + "wd_api/mallIntegral/conversion";
}
