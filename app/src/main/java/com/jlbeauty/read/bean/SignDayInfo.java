package com.jlbeauty.read.bean;

/**
 * Created by geek on 2016/8/2.
 * 签到
 */
public class SignDayInfo {
    private int day;
    private String value;
    private boolean isSign;

    public boolean isSign() {
        return isSign;
    }

    public void setIsSign(boolean isSign) {
        this.isSign = isSign;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
