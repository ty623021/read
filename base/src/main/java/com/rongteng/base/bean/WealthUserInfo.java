package com.rongteng.base.bean;

import com.rongteng.base.utils.BaseRespondWithWealth;

/**
 * Created by Administrator on 2017/5/16 0016.
 * 理财个人中心数据
 */

public class WealthUserInfo extends BaseRespondWithWealth {

    private double capitalAmount;//资产总额
    private double balance;//总余额
    private double applyAvailableBalance;//可用余额
    private double totalIncomeAmount;//累计收益总额
    private double t0YesterdayIncome;//活期昨日收益额
    private double t0CapitalAmount;//活期总资产
    private double tnCapitalAmount;//定期总资产
    private double tnTotalIncome;//定期总收益额
    private double t0TotalIncome;//活期总收益额

    public double getApplyAvailableBalance() {
        return applyAvailableBalance;
    }

    public void setApplyAvailableBalance(double applyAvailableBalance) {
        this.applyAvailableBalance = applyAvailableBalance;
    }

    public double getCapitalAmount() {
        return capitalAmount;
    }

    public void setCapitalAmount(double capitalAmount) {
        this.capitalAmount = capitalAmount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getTotalIncomeAmount() {
        return totalIncomeAmount;
    }

    public void setTotalIncomeAmount(double totalIncomeAmount) {
        this.totalIncomeAmount = totalIncomeAmount;
    }

    public double getT0YesterdayIncome() {
        return t0YesterdayIncome;
    }

    public void setT0YesterdayIncome(double t0YesterdayIncome) {
        this.t0YesterdayIncome = t0YesterdayIncome;
    }

    public double getTnTotalIncome() {
        return tnTotalIncome;
    }

    public void setTnTotalIncome(double tnTotalIncome) {
        this.tnTotalIncome = tnTotalIncome;
    }

    public double getT0TotalIncome() {
        return t0TotalIncome;
    }

    public void setT0TotalIncome(double t0TotalIncome) {
        this.t0TotalIncome = t0TotalIncome;
    }

    public double getT0CapitalAmount() {
        return t0CapitalAmount;
    }

    public void setT0CapitalAmount(double t0CapitalAmount) {
        this.t0CapitalAmount = t0CapitalAmount;
    }

    public double getTnCapitalAmount() {
        return tnCapitalAmount;
    }

    public void setTnCapitalAmount(double tnCapitalAmount) {
        this.tnCapitalAmount = tnCapitalAmount;
    }
}
