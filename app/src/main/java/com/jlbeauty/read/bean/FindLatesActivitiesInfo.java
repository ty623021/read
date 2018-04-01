package com.jlbeauty.read.bean;

/**
 * Created by hjy on 2017/5/19.
 */

public class FindLatesActivitiesInfo {


    private String imgUrl;//图片地址
    private String endDate;//结束日期
    private String startDate;//开始日期
    private String link;//图片链接地址
    private String title;//标题
    private String status;//活动状态


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
