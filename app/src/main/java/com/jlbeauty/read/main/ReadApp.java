package com.jlbeauty.read.main;

import com.rongteng.base.global.ActivityManager;
import com.rongteng.base.main.MyApplication;

/**
 * Created by WIN10 on 2018/3/21.
 */

public class ReadApp extends MyApplication {


    /**
     * 退出
     */
    public static void exit() {
        ActivityManager.getInstance().clearAllActivity();
    }
}
