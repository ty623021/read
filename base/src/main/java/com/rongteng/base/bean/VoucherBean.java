package com.rongteng.base.bean;

/**
 * Created by Administrator on 2017/6/5.
 * 财富红包对象
 */
public class VoucherBean {
    private String start;//开始时间
    private String finish;//结束时间
    private String status;//状态
    private String couponId;//卡券ID
    private String name;//卡券名称
    private String products;//产品集
    private String rules;//规则集
    private String type;//卡券类型
    private double amount;//卡券金额或加息券利率 3=%3
    private int investAmount;//需要投资多少金额
    private String validPeriod;//体验天数
    private String investTime;//使用标的的存续期天数
    private String investTimeMax;//

    public String getInvestTime() {
        return investTime;
    }

    public void setInvestTime(String investTime) {
        this.investTime = investTime;
    }

    public String getInvestTimeMax() {
        return investTimeMax;
    }

    public void setInvestTimeMax(String investTimeMax) {
        this.investTimeMax = investTimeMax;
    }

    public String getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(String validPeriod) {
        this.validPeriod = validPeriod;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(int investAmount) {
        this.investAmount = investAmount;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
