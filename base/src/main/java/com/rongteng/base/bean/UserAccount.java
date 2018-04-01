package com.rongteng.base.bean;

import java.io.Serializable;


/**
 * user--个人中心
 * 网贷，理财综合个人中心
 *
 * @author Administrator
 */
public class UserAccount implements Serializable {
    private double allMoneyTotal;//总资产
    private double allbalaceTotal;//总可用余额
    /**
     * 网贷
     */
    private String realName;//真实姓名
    private String preMobile;//银行预留手机号
    private String platcust;//开户编号
    private boolean isForceOpen;//是否强制开户  (true走托管版本的充值提现，false 走老版本充值提现)
    private boolean isOPenAccount;//是否开户
    private boolean isDepositBank;//是否绑定托管
    private boolean bindBank;//是否绑定了银行卡
    private boolean banSubName;//是否已经填写支行名称
    private boolean isBeginner;//是否是新手
    private boolean outPwdSafe;//用户的交易密码是否安全.

    private double investMoney;//投资账户余额
    private double borrowMoney;//借款账户余额
    private int voucherCount;//可使用代金券数量
    private int msgCount;//未读消息数量
    private int integral;//积分总数

    private double investAvailBalance;//投资账户可提现金额
    private double borrowAvailBalance;//借款账户可提现金额
    private double waitingReceiptCapital; //代收本金
    private double waitingReceiptInterest;//待收利息
    private double waitingReceiptAllMoney;//待收本息   不包括智能投顾的金额
    private double waitAmount;//网贷产品（散标加智投）总待收本息
    private double alreadyReceiptCapital; //已回款本金
    private double alreadyReceiptInterest;//已回款利息

    private double expAmount;//用户体验金总额
    private double repay7;//七日待还
    private double totalIncome;//累计净收益
    private double freezeBalance;//冻结金额

    private double allMoney;//网贷账户总资产
    private double availableBalance;//网贷账户总可用余额
    private double debtAmount;//借款负债
    private double advisorAllMoney;//诸葛智投的总资产
    private double wdExperienceWait;//体验标待收金额
    private double withdrawing;//提现申请中金额
    private double loanWaitAllMoney;// 散标待收金额

    /**
     * 财富
     */
    private double capitalAmoun;//财富总资产
    private double balance;//财富可用余额
    private double totalIncomeAmount;//财富累计收益
    private double t0CapitalAmount;//活期总资产
    private double tnCapitalAmount;//定期总资产

    private int riskAssessScore;//风险测评得分
    private String riskAssessResultType;//风险测评结果-风险偏好类型
    private String riskAssessResultDesc;//风险测评结果-风险偏好类型描述

    public int getRiskAssessScore() {
        return riskAssessScore;
    }

    public void setRiskAssessScore(int riskAssessScore) {
        this.riskAssessScore = riskAssessScore;
    }

    public String getRiskAssessResultType() {
        return riskAssessResultType;
    }

    public void setRiskAssessResultType(String riskAssessResultType) {
        this.riskAssessResultType = riskAssessResultType;
    }

    public String getRiskAssessResultDesc() {
        return riskAssessResultDesc;
    }

    public void setRiskAssessResultDesc(String riskAssessResultDesc) {
        this.riskAssessResultDesc = riskAssessResultDesc;
    }

    public double getWdExperienceWait() {
        return wdExperienceWait;
    }

    public void setWdExperienceWait(double wdExperienceWait) {
        this.wdExperienceWait = wdExperienceWait;
    }

    public double getWithdrawing() {
        return withdrawing;
    }

    public void setWithdrawing(double withdrawing) {
        this.withdrawing = withdrawing;
    }

    public double getLoanWaitAllMoney() {
        return loanWaitAllMoney;
    }

    public void setLoanWaitAllMoney(double loanWaitAllMoney) {
        this.loanWaitAllMoney = loanWaitAllMoney;
    }

    public double getBorrowAvailBalance() {
        return borrowAvailBalance;
    }

    public void setBorrowAvailBalance(double borrowAvailBalance) {
        this.borrowAvailBalance = borrowAvailBalance;
    }

    public double getInvestAvailBalance() {
        return investAvailBalance;
    }

    public void setInvestAvailBalance(double investAvailBalance) {
        this.investAvailBalance = investAvailBalance;
    }

    public double getAllMoneyTotal() {
        return allMoneyTotal;
    }

    public void setAllMoneyTotal(double allMoneyTotal) {
        this.allMoneyTotal = allMoneyTotal;
    }

    public double getAllbalaceTotal() {
        return allbalaceTotal;
    }

    public void setAllbalaceTotal(double allbalaceTotal) {
        this.allbalaceTotal = allbalaceTotal;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPreMobile() {
        return preMobile;
    }

    public void setPreMobile(String preMobile) {
        this.preMobile = preMobile;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public boolean isForceOpen() {
        return isForceOpen;
    }

    public void setForceOpen(boolean forceOpen) {
        isForceOpen = forceOpen;
    }

    public boolean isOPenAccount() {
        return isOPenAccount;
    }

    public void setOPenAccount(boolean OPenAccount) {
        isOPenAccount = OPenAccount;
    }

    public boolean isDepositBank() {
        return isDepositBank;
    }

    public void setDepositBank(boolean depositBank) {
        isDepositBank = depositBank;
    }

    public boolean isBindBank() {
        return bindBank;
    }

    public void setBindBank(boolean bindBank) {
        this.bindBank = bindBank;
    }

    public boolean isBanSubName() {
        return banSubName;
    }

    public void setBanSubName(boolean banSubName) {
        this.banSubName = banSubName;
    }

    public boolean isBeginner() {
        return isBeginner;
    }

    public void setBeginner(boolean beginner) {
        isBeginner = beginner;
    }

    public boolean isOutPwdSafe() {
        return outPwdSafe;
    }

    public void setOutPwdSafe(boolean outPwdSafe) {
        this.outPwdSafe = outPwdSafe;
    }

    public int getVoucherCount() {
        return voucherCount;
    }

    public void setVoucherCount(int voucherCount) {
        this.voucherCount = voucherCount;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(double freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    public double getDebtAmount() {
        return debtAmount;
    }

    public void setDebtAmount(double debtAmount) {
        this.debtAmount = debtAmount;
    }


    public double getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(double allMoney) {
        this.allMoney = allMoney;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public double getInvestMoney() {
        return investMoney;
    }

    public void setInvestMoney(double investMoney) {
        this.investMoney = investMoney;
    }

    public double getBorrowMoney() {
        return borrowMoney;
    }

    public void setBorrowMoney(double borrowMoney) {
        this.borrowMoney = borrowMoney;
    }

    public double getWaitingReceiptCapital() {
        return waitingReceiptCapital;
    }

    public void setWaitingReceiptCapital(double waitingReceiptCapital) {
        this.waitingReceiptCapital = waitingReceiptCapital;
    }

    public double getWaitingReceiptInterest() {
        return waitingReceiptInterest;
    }

    public void setWaitingReceiptInterest(double waitingReceiptInterest) {
        this.waitingReceiptInterest = waitingReceiptInterest;
    }

    public double getWaitingReceiptAllMoney() {
        return waitingReceiptAllMoney;
    }

    public void setWaitingReceiptAllMoney(double waitingReceiptAllMoney) {
        this.waitingReceiptAllMoney = waitingReceiptAllMoney;
    }

    public double getAlreadyReceiptCapital() {
        return alreadyReceiptCapital;
    }

    public void setAlreadyReceiptCapital(double alreadyReceiptCapital) {
        this.alreadyReceiptCapital = alreadyReceiptCapital;
    }

    public double getAlreadyReceiptInterest() {
        return alreadyReceiptInterest;
    }

    public void setAlreadyReceiptInterest(double alreadyReceiptInterest) {
        this.alreadyReceiptInterest = alreadyReceiptInterest;
    }

    public double getExpAmount() {
        return expAmount;
    }

    public void setExpAmount(double expAmount) {
        this.expAmount = expAmount;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public double getRepay7() {
        return repay7;
    }

    public void setRepay7(double repay7) {
        this.repay7 = repay7;
    }

    public double getCapitalAmoun() {
        return capitalAmoun;
    }

    public void setCapitalAmoun(double capitalAmoun) {
        this.capitalAmoun = capitalAmoun;
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

    public double getAdvisorAllMoney() {
        return advisorAllMoney;
    }

    public void setAdvisorAllMoney(double advisorAllMoney) {
        this.advisorAllMoney = advisorAllMoney;
    }

    public double getWaitAmount() {
        return waitAmount;
    }

    public void setWaitAmount(double waitAmount) {
        this.waitAmount = waitAmount;
    }
}
