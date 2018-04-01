package com.jlbeauty.read.bean;

/**
 * Created by Administrator on 2017/8/17 0017.
 * 兑换详情
 */

public class ExchangeDetailInfo {
    private IntegralVoucherInfo integralVoucher;
    private int jtSen;//已兑换数量
    private int mallIntegral;//当前积分数量

    public IntegralVoucherInfo getIntegralVoucher() {
        return integralVoucher;
    }

    public void setIntegralVoucher(IntegralVoucherInfo integralVoucher) {
        this.integralVoucher = integralVoucher;
    }

    public int getJtSen() {
        return jtSen;
    }

    public void setJtSen(int jtSen) {
        this.jtSen = jtSen;
    }

    public int getMallIntegral() {
        return mallIntegral;
    }

    public void setMallIntegral(int mallIntegral) {
        this.mallIntegral = mallIntegral;
    }
}
