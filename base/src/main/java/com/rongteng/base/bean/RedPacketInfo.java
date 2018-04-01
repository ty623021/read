/**
 *
 */
package com.rongteng.base.bean;

/**
 * 红包信息
 */
public class RedPacketInfo {

    private String sid;//红包ID
    private double amount;//金额
    private String useLimitMonth;//投标限制
    private double useLimitMoney;//最少投资金额
    private String name;//红包名称
    private String startTime;//开始时间
    private String dueTime;//到期时间
    private String voucherType;//红包类型
    private String useTime;//使用时间
    private String content;//(卡券中心代表使用条件)（投资时代表金额）
    private String content2;//适用于多少天(月)的标的
    private String content3;//限制什么标的使用
    private String status;//红包状态
    private double vouherDeploy;//	抵用金额配置 [抵用百分比(0为不限制)]
    private String useDate;//使用时间

    public String getContent3() {
        return content3;
    }

    public void setContent3(String content3) {
        this.content3 = content3;
    }

    public String getUseDate() {
        return useDate;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public double getVouherDeploy() {
        return vouherDeploy;
    }

    public void setVouherDeploy(double vouherDeploy) {
        this.vouherDeploy = vouherDeploy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUseLimitMonth() {
        return useLimitMonth;
    }

    public void setUseLimitMonth(String useLimitMonth) {
        this.useLimitMonth = useLimitMonth;
    }

    public double getUseLimitMoney() {
        return useLimitMoney;
    }

    public void setUseLimitMoney(double useLimitMoney) {
        this.useLimitMoney = useLimitMoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "RedPacketInfo{" +
                "sid='" + sid + '\'' +
                ", amount=" + amount +
                ", useLimitMonth='" + useLimitMonth + '\'' +
                ", useLimitMoney=" + useLimitMoney +
                ", name='" + name + '\'' +
                ", startTime='" + startTime + '\'' +
                ", dueTime='" + dueTime + '\'' +
                ", voucherType='" + voucherType + '\'' +
                ", useTime='" + useTime + '\'' +
                ", content='" + content + '\'' +
                ", content2='" + content2 + '\'' +
                ", status='" + status + '\'' +
                ", vouherDeploy=" + vouherDeploy +
                ", useDate='" + useDate + '\'' +
                '}';
    }
}
