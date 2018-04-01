package com.jlbeauty.read.bean;

/**
 * 邀请好友
 */
public class InviteNoteInfo {
    private String mobile;//	手机号码
    private String capital;//	佣金奖励
    private String realName;//	真实姓名
    private String regTime;//   注册时间
    private String username;//	用户名
    private int grade;//等级


    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "InviteNoteInfo{" +
                "mobile='" + mobile + '\'' +
                ", capital=" + capital +
                ", regTime='" + regTime + '\'' +
                ", grade=" + grade +
                ", username='" + username + '\'' +
                '}';
    }
}
