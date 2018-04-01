package com.jlbeauty.read.bean;

/**
 * Created by geek on 2016/8/2.
 */
public class IntegralInfo {

    private String availableAffect;//影响积分
    private String reason;//变动原因
    private String availableBlance;//积分额度
    private String type;//	积分类型
    private String dateCreated;//	创建日期

    public String getAvailableAffect() {
        return availableAffect;
    }

    public void setAvailableAffect(String availableAffect) {
        this.availableAffect = availableAffect;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAvailableBlance() {
        return availableBlance;
    }

    public void setAvailableBlance(String availableBlance) {
        this.availableBlance = availableBlance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
