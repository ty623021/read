package com.rongteng.base.bean;

import java.io.Serializable;

/**
 * 用户实名信息和银行信息
 *
 * @author Administrator
 */
public class UserAndBankInfo implements Serializable {

    private boolean idCardStatus;//是否已经实名
    private boolean bankStatus;//是否已经绑定银行卡
    private boolean haveUpdateUserName;//是否变更过用户名
    private boolean emailStatus;//是否已经绑定邮箱
    private String realName;//真实姓名
    private String username;//用户名
    private String memberMobile;//用户手机号
    private String preMobile;//银行预留手机号
    private String email;//邮箱
    private String idCard;//身份证
    private String bankId;//银行编号
    private String bankName;//银行名称
    private String bankAccount;//银行卡号
    private double limitOnceMoney;//银行单次限额
    private double limitDayMoney;//银行每日限额
    private String bankLogo;//银行logo
    private String platcust;//银行开户编号
    private boolean isDepositBank;//是否绑定存管
    private boolean isOPenAccount;//是否开户
    private boolean isForceOpen;//是否强制开户  (true走托管版本的充值提现，false 走老版本充值提现)
    private boolean isHoliday;//是否是节假日

    public boolean isHoliday() {
        return isHoliday;
    }

    public void setHoliday(boolean holiday) {
        isHoliday = holiday;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public boolean isDepositBank() {
        return isDepositBank;
    }

    public void setDepositBank(boolean depositBank) {
        isDepositBank = depositBank;
    }

    public boolean isOPenAccount() {
        return isOPenAccount;
    }

    public void setOPenAccount(boolean OPenAccount) {
        isOPenAccount = OPenAccount;
    }

    public boolean isForceOpen() {
        return isForceOpen;
    }

    public void setForceOpen(boolean forceOpen) {
        isForceOpen = forceOpen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(boolean emailStatus) {
        this.emailStatus = emailStatus;
    }

    public String getPreMobile() {
        return preMobile;
    }

    public void setPreMobile(String preMobile) {
        this.preMobile = preMobile;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isHaveUpdateUserName() {
        return haveUpdateUserName;
    }

    public void setHaveUpdateUserName(boolean haveUpdateUserName) {
        this.haveUpdateUserName = haveUpdateUserName;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }

    public double getLimitOnceMoney() {
        return limitOnceMoney;
    }

    public void setLimitOnceMoney(double limitOnceMoney) {
        this.limitOnceMoney = limitOnceMoney;
    }

    public double getLimitDayMoney() {
        return limitDayMoney;
    }

    public void setLimitDayMoney(double limitDayMoney) {
        this.limitDayMoney = limitDayMoney;
    }

    public boolean isIdCardStatus() {
        return idCardStatus;
    }

    public void setIdCardStatus(boolean idCardStatus) {
        this.idCardStatus = idCardStatus;
    }

    public boolean isBankStatus() {
        return bankStatus;
    }

    public void setBankStatus(boolean bankStatus) {
        this.bankStatus = bankStatus;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
}
