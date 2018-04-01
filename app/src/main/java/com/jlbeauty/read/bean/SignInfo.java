package com.jlbeauty.read.bean;

import java.util.List;

/**
 * Created by geek on 2016/8/2.
 * 签到
 */
public class SignInfo {
    private boolean isSign;//是否已经签到
    private int totalSignNum;//连续签到天数
    private String integralNumber;//本次签到获得的积分
    private String remind;//连续签到奖励提示
    private List<SignDayInfo> everyDayIntegral;

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public boolean isSign() {
        return isSign;
    }

    public void setIsSign(boolean isSign) {
        this.isSign = isSign;
    }

    public int getTotalSignNum() {
        return totalSignNum;
    }

    public void setTotalSignNum(int totalSignNum) {
        this.totalSignNum = totalSignNum;
    }

    public String getIntegralNumber() {
        return integralNumber;
    }

    public void setIntegralNumber(String integralNumber) {
        this.integralNumber = integralNumber;
    }

    public List<SignDayInfo> getEveryDayIntegral() {
        return everyDayIntegral;
    }

    public void setEveryDayIntegral(List<SignDayInfo> everyDayIntegral) {
        this.everyDayIntegral = everyDayIntegral;
    }
}
