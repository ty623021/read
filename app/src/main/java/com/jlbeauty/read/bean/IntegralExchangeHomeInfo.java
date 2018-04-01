package com.jlbeauty.read.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/9 0009.
 * 积分兑换红包
 */

public class IntegralExchangeHomeInfo {
    private int mallIntegral;//总积分
    private int integralVoucherCount;//红包数量
    private List<IntegralVoucherInfo> resultIntegralVoucher;//红包集合

    public int getMallIntegral() {
        return mallIntegral;
    }

    public void setMallIntegral(int mallIntegral) {
        this.mallIntegral = mallIntegral;
    }

    public int getIntegralVoucherCount() {
        return integralVoucherCount;
    }

    public void setIntegralVoucherCount(int integralVoucherCount) {
        this.integralVoucherCount = integralVoucherCount;
    }

    public List<IntegralVoucherInfo> getResultIntegralVoucher() {
        return resultIntegralVoucher;
    }

    public void setResultIntegralVoucher(List<IntegralVoucherInfo> resultIntegralVoucher) {
        this.resultIntegralVoucher = resultIntegralVoucher;
    }
}
