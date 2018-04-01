package com.rongteng.base.utils;

import android.os.Looper;

import com.bumptech.glide.Glide;
import com.rongteng.base.main.MyApplication;

/**
 * Created by Administrator on 2017/4/27 0027.
 * 清除glide缓存
 */

public class AbGlideCatchUtil {
    private static AbGlideCatchUtil instance;

    public static AbGlideCatchUtil getInstance() {
        if (null == instance) {
            instance = new AbGlideCatchUtil();
        }
        return instance;
    }


    // 清除图片磁盘缓存，调用Glide自带方法
    public boolean clearCacheDiskSelf() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(MyApplication.getContext()).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(MyApplication.getContext()).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 清除Glide内存缓存
    public boolean clearCacheMemory() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(MyApplication.getContext()).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
