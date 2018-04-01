package com.rongteng.base.utils;

import android.content.Context;

import com.spinytech.macore.MaApplication;
import com.spinytech.macore.router.LocalRouter;
import com.spinytech.macore.router.RouterRequest;
import com.spinytech.macore.router.RouterResponse;

/**
 * Created by Administrator on 2017/5/5 0005.
 * 进行module间的跳转
 */
public class AbRouterUtil {

    /**
     * 单进程调用
     * module間通信 不含参数
     *
     * @param context
     * @param provider 要通信的module；注册的在对应module中（一般一个module中只有一个）
     * @param action   执行具体操作的类（可以有多个 如：跳转登录，注册。。。）
     * @return 执行完成后返回的参数
     */
    public static RouterResponse router(Context context, String provider, String action) {
        try {
            return LocalRouter.getInstance(MaApplication.getMaApplication())
                    .route(context, RouterRequest.obtain(context)
                            .provider(provider)
                            .action(action)
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 多进程调用
     * module间的跳转 不含参数
     *
     * @param context
     * @param domian   进程名称，不填写的时候则默认domain是当前进程名
     * @param provider 要通信的module；注册的在对应module中（一般一个module中只有一个）
     * @param action   执行具体操作的类（可以有多个 如：跳转登录，注册。。。）
     * @return 执行完成后返回的参数
     */
    public static RouterResponse router(Context context, String domian, String provider, String action) {
        try {
            return LocalRouter.getInstance(MaApplication.getMaApplication())
                    .route(context, RouterRequest.obtain(context)
                            .domain(domian)
                            .provider(provider)
                            .action(action)
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 单进程调用
     * module间的跳转 含参数
     *
     * @param context
     * @param provider 要通信的module；注册的在对应module中（一般一个module中只有一个）
     * @param action   执行具体操作的类（可以有多个 如：跳转登录，注册。。。）
     * @param data     传递参数
     * @return 执行完成后返回的参数
     */
    public static RouterResponse routerData(Context context, String provider, String action, String data) {
        try {
            return LocalRouter.getInstance(MaApplication.getMaApplication())
                    .route(context, RouterRequest.obtain(context)
                            .provider(provider)
                            .action(action)
                            .data("data", data)
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 单进程调用
     * module间的跳转 含参数
     *
     * @param context
     * @param provider 要通信的module；注册的在对应module中（一般一个module中只有一个）
     * @param action   执行具体操作的类（可以有多个 如：跳转登录，注册。。。）
     * @param data1    传递参数
     * @param data2    传递参数
     * @return 执行完成后返回的参数
     */
    public static RouterResponse routerData(Context context, String provider, String action, String data1, String data2) {
        try {
            return LocalRouter.getInstance(MaApplication.getMaApplication())
                    .route(context, RouterRequest.obtain(context)
                            .provider(provider)
                            .action(action)
                            .data("data1", data1)
                            .data("data2", data2)
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
