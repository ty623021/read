package com.rongteng.base.volley;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;

import com.rongteng.base.main.MyApplication;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbRouterUtil;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.weight.LoadingFragment;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2015/3/11.
 */
@SuppressLint("NewApi")
public class RequestManager {
    private static Context context;
    private static String url;
    private static ResponseListener responseListener;
    private static final int WHAT_SUCCESS = 0;
    private static final int WHAT_FAIL = 1;

    private RequestManager() {
    }

    /**
     * 返回String
     *
     * @param url      接口
     * @param tag      上下文
     * @param params   post需要传的参数
     * @param listener 回调
     */
    public static void post(String url, Object tag, RequestParams params,
                            final RequestListener listener) {
        RequestManager.url = url;
        if (tag != null) {
            context = (Context) tag;
        }
        final ResponseListener responseListener = responseListener(listener, false, null);
        OkHttpUtils
                .post()//
                .url(url)//
                .params(params.getUrlParams())//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        responseListener.onErrorResponse(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        responseListener.onResponse(response);
                    }

                });
    }


    /**
     * 返回String 带进度条
     *
     * @param url           接口
     * @param tag           上下文
     * @param params        post需要传的参数
     * @param progressTitle 进度条文字  可以不传
     * @param listener      回调
     */
    public static void post(String url, Object tag, RequestParams params,
                            String progressTitle, RequestListener listener) {
        RequestManager.url = url;
        if (tag != null) {
            context = (Context) tag;
        }
        final ResponseListener responseListener;
        if (progressTitle != null) {
            LoadingFragment dialog = LoadingFragment.showLoading(((FragmentActivity) tag), progressTitle);
            responseListener = responseListener(listener, true, dialog);
        } else {
            responseListener = responseListener(listener, false, null);
        }
        OkHttpUtils
                .post()//
                .url(url)//
                .params(params.getUrlParams())//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        responseListener.onErrorResponse(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        responseListener.onResponse(response);
                    }

                });
    }


    /**
     * 返回String 不带进度条（参数以json形式传递）
     *
     * @param url      接口
     * @param tag      上下文
     * @param params   post需要传的参数
     * @param listener 回调
     */
    public static void postJson(String url, Object tag, Map params,
                                final RequestListener listener) {
        RequestManager.url = url;
        if (tag != null) {
            context = (Context) tag;
        }
        final ResponseListener responseListener = responseListener(listener, false, null);
        String json = AbJsonUtil.toJson(params);
        OkHttpUtils
                .postString()
                .url(url)
                .content(json)
                .mediaType(MediaType.parse("application/json"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        responseListener.onErrorResponse(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        responseListener.onResponse(response);
                    }

                });

    }

    /**
     * 返回String 带进度条（参数以json形式传递）
     *
     * @param url           接口
     * @param tag           上下文
     * @param params        post需要传的参数
     * @param progressTitle 进度条文字  可以不传
     * @param listener      回调
     */
    public static void postJson(String url, Object tag, Map params,
                                String progressTitle, RequestListener listener) {
        RequestManager.url = url;
        if (tag != null) {
            context = (Context) tag;
        }
        final ResponseListener responseListener;
        if (progressTitle != null) {
            LoadingFragment dialog = LoadingFragment.showLoading(((FragmentActivity) tag), progressTitle);
            responseListener = responseListener(listener, true, dialog);
        } else {
            responseListener = responseListener(listener, false, null);
        }
        String json = AbJsonUtil.toJson(params);
        OkHttpUtils
                .postString()
                .url(url)
                .content(json)
                .mediaType(MediaType.parse("application/json"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        responseListener.onErrorResponse(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        responseListener.onResponse(response);
                    }

                });
    }

    private static Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == WHAT_SUCCESS) {
                String string = (String) msg.obj;
                if (responseListener != null) {
                    responseListener.onResponse(string);
                }
            } else if (msg.what == WHAT_FAIL) {
                Exception e = (Exception) msg.obj;
                if (responseListener != null) {
                    responseListener.onErrorResponse(e);
                }
            }
            return false;
        }
    });

    /**
     * 返回String 不带进度条（参数以Form表单形式传递）
     *
     * @param url      接口
     * @param tag      上下文
     * @param params   post需要传的参数
     * @param listener 回调
     */
    public static void postForm(String url, Object tag, Map params,
                                final RequestListener listener) {
        RequestManager.url = url;
        if (tag != null) {
            context = (Context) tag;
        }
        responseListener = responseListener(listener, false, null);
        FormBody.Builder from = new FormBody.Builder();
        Iterator iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry ent = (Map.Entry) iterator.next();
            String key = ent.getKey().toString();
            if (ent.getValue() == null) {
                continue;
            }
            String value = ent.getValue().toString();
            from.add(key, value);
        }
        RequestBody body = from.build();
        OkHttpUtils instance = OkHttpUtils.getInstance();
        OkHttpClient client = instance.getOkHttpClient();
        Request request = new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = handler.obtainMessage();
                message.what = WHAT_FAIL;
                message.obj = e;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) {
                Message message = handler.obtainMessage();
                try {
                    String string = response.body().string();
                    message.what = WHAT_SUCCESS;
                    message.obj = string;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    message.what = WHAT_FAIL;
                    message.obj = e;
                    handler.sendMessage(message);
                }
            }
        });

    }

    /**
     * 返回String 带进度条（参数以From表单形式传递）
     *
     * @param url           接口
     * @param tag           上下文
     * @param params        post需要传的参数
     * @param progressTitle 进度条文字  可以不传
     * @param listener      回调
     */
    public static void postForm(String url, Object tag, Map params,
                                String progressTitle, RequestListener listener) {
        RequestManager.url = url;
        if (tag != null) {
            context = (Context) tag;
        }
        if (progressTitle != null) {
            LoadingFragment dialog = LoadingFragment.showLoading(((FragmentActivity) tag), progressTitle);
            responseListener = responseListener(listener, true, dialog);
        } else {
            responseListener = responseListener(listener, false, null);
        }
        FormBody.Builder from = new FormBody.Builder();
        Iterator iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry ent = (Map.Entry) iterator.next();
            String key = ent.getKey().toString();
            if (ent.getValue() == null) {
                continue;
            }
            String value = ent.getValue().toString();
            from.add(key, value);
        }
        RequestBody body = from.build();
        OkHttpUtils instance = OkHttpUtils.getInstance();
        OkHttpClient client = instance.getOkHttpClient();
        Request request = new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = handler.obtainMessage();
                message.what = WHAT_FAIL;
                message.obj = e;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) {
                Message message = handler.obtainMessage();
                try {
                    String string = response.body().string();
                    message.what = WHAT_SUCCESS;
                    message.obj = string;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    message.what = WHAT_FAIL;
                    message.obj = e;
                    handler.sendMessage(message);
                }
            }
        });
    }


    /**
     * 网络请求响应监听
     *
     * @param l
     * @param flag   是否显示请求框
     * @param dialog
     * @return
     */
    protected static ResponseListener responseListener(
            final RequestListener l, final boolean flag, final LoadingFragment dialog) {

        return new ResponseListener<String>() {
            @Override
            public void onResponse(String data) {
                l.requestSuccess(data);
                if (!AbStringUtil.isEmpty(data)) {
                    isSuccess(data);
                }
                if (flag) {
                    LoadingFragment.dismiss(dialog);
                }
            }

            @Override
            public void onErrorResponse(Exception e) {
                String message = ErrorHelper.getMessage(e);
                l.requestError(message);
                if (flag) {
                    LoadingFragment.dismiss(dialog);
                }
            }
        };
    }

    public interface ResponseListener<T> {
        void onResponse(T var1);

        void onErrorResponse(Exception var1);
    }

    /**
     * 判断网络请求的状态
     *
     * @param data
     */
    private static void isSuccess(String data) {
        // (!AbJsonUtil.isSuccess(data))==true 代表小诸葛的网络请求失败了
        // (!AbJsonUtil.isWealthSuccess(data))==true 代表理财的网络请求失败了
        if (!AbJsonUtil.isSuccess(data) && !AbJsonUtil.isWealthSuccess(data)) {
            String status = AbJsonUtil.getStatus(data, "status");
            if (status != null) {
                if (status.equals("301")) {//登录过期
                    MyApplication.mApp.setPersonalStatus(false);
                    AbRouterUtil.routerData(context, "main", "startActivity", "LoginActivity");
                    AbToastUtil.showToast(context, AbJsonUtil.getError(data));
                }
            } else {
                MyApplication.mApp.setPersonalStatus(false);
                AbRouterUtil.routerData(context, "main", "startActivity", "LoginActivity");
                AbToastUtil.showToast(context, "登陆已过期，请重新登录");
                //乱码统计
                HashMap<String, String> map = new HashMap<>();
                StringBuffer buffer = new StringBuffer();
                buffer.append("user_id：")
                        .append(MyApplication.mApp.getID() + ";")
                        .append("request_url：")
                        .append(RequestManager.url + ";")
                        .append("version：")
                        .append(MyApplication.mApp.getAppVersionName() + ";")
                        .append("data：")
                        .append(data)
                ;
                map.put("data", buffer.toString());
                MobclickAgent.onEventValue(context, "user_garbled", map, 100);
            }
        }
    }


}
