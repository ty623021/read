package com.jlbeauty.read.bean;

/**
 * Created by geek on 2016/8/2.
 */
public class TaskInfo {

    private String integral;//描述 积分任务奖励
    private boolean trueOrFalse;//是否完成 true 代表已完成 false 代表未完成

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public boolean isTrueOrFalse() {
        return trueOrFalse;
    }

    public void setTrueOrFalse(boolean trueOrFalse) {
        this.trueOrFalse = trueOrFalse;
    }
}
