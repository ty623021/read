/**
 *
 */
package com.jlbeauty.read.bean;

import java.io.Serializable;

/**
 * 银行卡信息
 */
public class IntelligentItemDesignInfo implements Serializable {

    private String realName;//用户真实姓名
    private String idCard;//身份证号码
    private String borrowUse;//借款用途
    private String amount;//借款金额
    private String productId;//智投产品ID
    private String borrowId;//智投产品ID 修改过
    private String address;

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

    public String getBorrowUse() {
        return borrowUse;
    }

    public void setBorrowUse(String borrowUse) {
        this.borrowUse = borrowUse;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
