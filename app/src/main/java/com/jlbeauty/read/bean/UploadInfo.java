package com.jlbeauty.read.bean;

/**
 * Created by geek on 2016/8/2.
 */
public class UploadInfo {
    private String url;//上传后文件的服务器地址

    private String original;//原文件名

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
