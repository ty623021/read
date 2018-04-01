package com.jlbeauty.read.bean;

/**
 * Created by hjy on 2017/10/20.
 */

public class IntelligentInvestListDetailInfo {

    private String productId;//项目编号
    private String investId;//项目投资编号ID
    private String title;//标题
    private double investAmount;//投资金额
    private double expectIncome;//预期收益
    private double actualIncome;//实际收益
    private String investTime;//加入时间
    private String settleTime;//结算时间
    private String costDesc;//费用说明
    private double yearRate;//预期年化利率
    private int cycle;//封闭期限
    private String cycleUnit;//封闭期限 天
    private double productAmount;//目标金额
    private String interestType;//计息说明
    private String incomeDesc;//收益说明
    private String couponsName;//优惠券名称
    private String couponsAcount;//优惠券金额
    private int invNum;//债权匹配数量

    public int getInvNum() {
        return invNum;
    }

    public void setInvNum(int invNum) {
        this.invNum = invNum;
    }

    public double getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(double productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getInvestId() {
        return investId;
    }

    public void setInvestId(String investId) {
        this.investId = investId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIncomeDesc() {
        return incomeDesc;
    }

    public void setIncomeDesc(String incomeDesc) {
        this.incomeDesc = incomeDesc;
    }

    public String getInterestType() {
        return interestType;
    }

    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }


    public String getCycleUnit() {
        return cycleUnit;
    }

    public void setCycleUnit(String cycleUnit) {
        this.cycleUnit = cycleUnit;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public double getYearRate() {
        return yearRate;
    }

    public void setYearRate(double yearRate) {
        this.yearRate = yearRate;
    }

    public String getCostDesc() {
        return costDesc;
    }

    public void setCostDesc(String costDesc) {
        this.costDesc = costDesc;
    }

    public String getInvestTime() {
        return investTime;
    }

    public void setInvestTime(String investTime) {
        this.investTime = investTime;
    }

    public String getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(String settleTime) {
        this.settleTime = settleTime;
    }

    public double getActualIncome() {
        return actualIncome;
    }

    public void setActualIncome(double actualIncome) {
        this.actualIncome = actualIncome;
    }

    public double getExpectIncome() {
        return expectIncome;
    }

    public void setExpectIncome(double expectIncome) {
        this.expectIncome = expectIncome;
    }

    public double getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(double investAmount) {
        this.investAmount = investAmount;
    }

    public String getCouponsName() {
        return couponsName;
    }

    public void setCouponsName(String couponsName) {
        this.couponsName = couponsName;
    }

    public String getCouponsAcount() {
        return couponsAcount;
    }

    public void setCouponsAcount(String couponsAcount) {
        this.couponsAcount = couponsAcount;
    }
}
