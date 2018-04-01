package com.rongteng.base.utils;

import android.os.Build;

/**
 * Created by geek on 2016/9/23.
 * 权限管理
 */
public class AbPermissions {


    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
