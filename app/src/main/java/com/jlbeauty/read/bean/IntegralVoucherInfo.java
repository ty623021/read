package com.jlbeauty.read.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/9 0009.
 * 积分兑换红包
 */

public class IntegralVoucherInfo implements Serializable {
    private String id;//红包id
    private String description;//描述
    private String ranking;//排名
    private String imgUrl;//图片地址
    private int integralWorth;//积分价值
    private int inventory;//初始库存
    private int remainingInventory;//剩余库存
    private int exchangeNum;//当日可兑换数量
    private String showNum;//
    private String status;
    private String voucherName;//红包名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getIntegralWorth() {
        return integralWorth;
    }

    public void setIntegralWorth(int integralWorth) {
        this.integralWorth = integralWorth;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getRemainingInventory() {
        return remainingInventory;
    }

    public void setRemainingInventory(int remainingInventory) {
        this.remainingInventory = remainingInventory;
    }

    public int getExchangeNum() {
        return exchangeNum;
    }

    public void setExchangeNum(int exchangeNum) {
        this.exchangeNum = exchangeNum;
    }

    public String getShowNum() {
        return showNum;
    }

    public void setShowNum(String showNum) {
        this.showNum = showNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }
}
