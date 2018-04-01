package com.jlbeauty.read.bean;

import android.graphics.Bitmap;

/**
 * Created by hjy on 2016/7/18.
 * 意见反馈的
 */
public class SuggestionInfo {

    public SuggestionInfo(Bitmap bitmap, String path) {
        this.bitmap = bitmap;
        this.path = path;
    }

    private Bitmap bitmap;
    private String path;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
