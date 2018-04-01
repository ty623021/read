package com.rongteng.base.bean;

/**
 * 邀请好友
 */
public class InviteFriend {
    private String recommendUrl;//	邀请好友链接
    private String recommendCode;//	邀请码
    private int count;//	已邀请好友
    private double reward;//已领取佣金
    private String appUrl;//APP下载地址
    private int voucherCount;//	红包个数
    private String shareTitle;//分享标题
    private String shareContent;//	分享内容

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public int getVoucherCount() {
        return voucherCount;
    }

    public void setVoucherCount(int voucherCount) {
        this.voucherCount = voucherCount;
    }

    public String getRecommendUrl() {
        return recommendUrl;
    }

    public void setRecommendUrl(String recommendUrl) {
        this.recommendUrl = recommendUrl;
    }

    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }
}
