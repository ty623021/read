/**
 *
 */
package com.jlbeauty.read.bean;

import java.io.Serializable;

/**
 * 诸葛智投标的详情信息
 */
public class IntelligentDetailsInfo implements Serializable {

    private String classify;
    private String finaId;//项目ID
    private String title;//理财产品名称
    private double yearRate;//年化利率(%)
    private double coefficient;//递增金额
    private int investMin;//最低投资额
    private int investMax;//最高投资额
    private String cycle;//期限
    private int cycleNumber;//数字 几天/几个月
    private String cycleType;//day" : "month"
    private String cycleTypeShow;//天" : "月"
    private String type;//月计划", "month" "季计划", "season" "半年计划", "half"
    private String typeName;//类型名称
    private String finaStatus;//状态
    private String finaStatusName;//状态 名称 none ： 未上线 online：已上线 process ：待加入 success：已满额 run：收益中 done：已结束
    private double amount;//计划募集金额
    private double alreadyAmount;//已募集金额(本金)
    private double surplusAmount;//剩余可投金额
    private double minYearRate;//项目最低年化
    private double maxYearRate;//项目最高年化
    private double canRaiseDay;//可募集天数
    private double subsidyRate;//加息
    private int investProcess;//投资进度
    private boolean isUseBonus;//是否使用红包 true 可用 false 不可用
    private String introduce;//计划简介
    private String commonProblem;//常见问题
    private int joinFee;//加入费用
    private int outFee;//退出费用
    private boolean isLimitBeginnerRoa;//是否是新手标

    public boolean isLimitBeginnerRoa() {
        return isLimitBeginnerRoa;
    }

    public void seLimitBeginnerRoa(boolean isLimitBeginnerRoa) {
        isLimitBeginnerRoa = isLimitBeginnerRoa;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getFinaId() {
        return finaId;
    }

    public void setFinaId(String finaId) {
        this.finaId = finaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getSubsidyRate() {
        return subsidyRate;
    }

    public void setSubsidyRate(double subsidyRate) {
        this.subsidyRate = subsidyRate;
    }

    public double getYearRate() {
        return yearRate;
    }

    public void setYearRate(double yearRate) {
        this.yearRate = yearRate;
    }

    public int getInvestMin() {
        return investMin;
    }

    public void setInvestMin(int investMin) {
        this.investMin = investMin;
    }

    public int getInvestMax() {
        return investMax;
    }

    public void setInvestMax(int investMax) {
        this.investMax = investMax;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public int getCycleNumber() {
        return cycleNumber;
    }

    public void setCycleNumber(int cycleNumber) {
        this.cycleNumber = cycleNumber;
    }

    public String getCycleType() {
        return cycleType;
    }

    public String getCycleTypeShow() {
        return cycleTypeShow;
    }

    public void setCycleTypeShow(String cycleTypeShow) {
        this.cycleTypeShow = cycleTypeShow;
    }

    public void setCycleType(String cycleType) {
        this.cycleType = cycleType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getFinaStatus() {
        return finaStatus;
    }

    public void setFinaStatus(String finaStatus) {
        this.finaStatus = finaStatus;
    }

    public String getFinaStatusName() {
        return finaStatusName;
    }

    public void setFinaStatusName(String finaStatusName) {
        this.finaStatusName = finaStatusName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAlreadyAmount() {
        return alreadyAmount;
    }

    public void setAlreadyAmount(double alreadyAmount) {
        this.alreadyAmount = alreadyAmount;
    }

    public double getSurplusAmount() {
        return surplusAmount;
    }

    public void setSurplusAmount(double surplusAmount) {
        this.surplusAmount = surplusAmount;
    }

    public double getMinYearRate() {
        return minYearRate;
    }

    public void setMinYearRate(double minYearRate) {
        this.minYearRate = minYearRate;
    }

    public double getMaxYearRate() {
        return maxYearRate;
    }

    public void setMaxYearRate(double maxYearRate) {
        this.maxYearRate = maxYearRate;
    }

    public double getCanRaiseDay() {
        return canRaiseDay;
    }

    public void setCanRaiseDay(double canRaiseDay) {
        this.canRaiseDay = canRaiseDay;
    }

    public int getInvestProcess() {
        return investProcess;
    }

    public void setInvestProcess(int investProcess) {
        this.investProcess = investProcess;
    }

    public boolean isUseBonus() {
        return isUseBonus;
    }

    public void setUseBonus(boolean useBonus) {
        isUseBonus = useBonus;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getCommonProblem() {
        return commonProblem;
    }

    public void setCommonProblem(String commonProblem) {
        this.commonProblem = commonProblem;
    }

    public int getJoinFee() {
        return joinFee;
    }

    public void setJoinFee(int joinFee) {
        this.joinFee = joinFee;
    }

    public int getOutFee() {
        return outFee;
    }

    public void setOutFee(int outFee) {
        this.outFee = outFee;
    }
}
