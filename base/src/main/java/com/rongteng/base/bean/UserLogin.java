package com.rongteng.base.bean;

/**
 * 用户登录返回的对象
 */
public class UserLogin {
    private String accessToken;//登陆成功和注册成功后系统返回的accessToken数据
    private String username;//用户名
    private String mobile;//手机号
    private String id;//用户ID
    private double regVoucher;//红包金额

    public double getRegVoucher() {
        return regVoucher;
    }

    public void setRegVoucher(double regVoucher) {
        this.regVoucher = regVoucher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "accessToken='" + accessToken + '\'' +
                ", username='" + username + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
