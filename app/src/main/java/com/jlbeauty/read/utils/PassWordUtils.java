package com.jlbeauty.read.utils;

import android.app.Activity;

import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AbToastUtil;

/**
 * Created by geek on 2016/7/18.
 * 密码请求修改
 */
public class PassWordUtils {
    private static int MIN = 6;
    private static int MAX = 16;

    /**
     * 当只有一个密码的时候
     *
     * @param activity
     * @param pass1    密码
     * @return
     */
    public static boolean checkPassWord(Activity activity, String pass1) {
        if (AbStringUtil.isPassword(pass1)) {
            if (pass1.length() < MIN || pass1.length() > MAX) {
                AbToastUtil.showToast(activity, "密码" + MIN + "~" + MAX + "位");
            } else {
                return true;
            }
        } else {
            AbToastUtil.showToast(activity, "密码不能包含特殊字符");
        }
        return false;
    }

    /**
     * 当只有一个密码和确认密码的情况
     *
     * @param activity
     * @param pass1    密码
     * @param pass2    确认密码
     * @return
     */
    public static boolean checkPassWord(Activity activity, String pass1, String pass2) {
        if (pass1.length() < MIN || pass1.length() > MAX) {
            AbToastUtil.showToast(activity, "密码" + MIN + "~" + MAX + "位");
        } else {
            if (!pass1.equals(pass2)) {
                AbToastUtil.showToast(activity, "密码不一致");
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 当有原密码、新密码、确认密码的情况
     *
     * @param activity
     * @param pass1    新密码
     * @param pass2    确认密码
     * @param pass3    原密码
     * @return
     */
    public static boolean checkPassWord(Activity activity, String pass1, String pass2, String pass3) {
        if (pass3.length() < MIN || pass3.length() > MAX) {
            AbToastUtil.showToast(activity, "原密码" + MIN + "~" + MAX + "位");
        } else {
            if (pass1.length() < MIN || pass1.length() > MAX) {
                AbToastUtil.showToast(activity, "新密码" + MIN + "~" + MAX + "位");
            } else {
                if (!pass1.equals(pass2)) {
                    AbToastUtil.showToast(activity, "两次密码不一致");
                } else {
                    return true;
                }
            }
        }
        return false;
    }
}
