package com.jlbeauty.read.bean;

/**
 * Created by hjy on 2017/10/19.
 */

public class IntelligentRecordInfo {
    private String productId;//项目编号
    private String title;//标题
    private double yearRate;//预期年化利率
    private double investAmount;//投资金额
    private double expectIncome;//预期收益
    private double actualIncome;//实际收益
    private String investTime;//投资时间
    private String repaymentTime;//回款时间
    private String endDate;//到期日
    private String investId;//项目投资编号ID
    private String tabInvestStatus;//状态

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTabInvestStatus() {
        return tabInvestStatus;
    }

    public void setTabInvestStatus(String tabInvestStatus) {
        this.tabInvestStatus = tabInvestStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInvestId() {
        return investId;
    }

    public void setInvestId(String investId) {
        this.investId = investId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }




    public double getActualIncome() {
        return actualIncome;
    }

    public void setActualIncome(double actualIncome) {
        this.actualIncome = actualIncome;
    }

    public String getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(String repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public String getInvestTime() {
        return investTime;
    }

    public void setInvestTime(String investTime) {
        this.investTime = investTime;
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

    public double getYearRate() {
        return yearRate;
    }

    public void setYearRate(double yearRate) {
        this.yearRate = yearRate;
    }





}
