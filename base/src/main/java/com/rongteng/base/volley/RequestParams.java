package com.rongteng.base.volley;

import java.util.TreeMap;

/**
 * Created by Administrator on 2015/3/11.
 */
public class RequestParams {


    protected TreeMap<String, String> urlParams;

    public RequestParams() {
        init();
    }

    public RequestParams(String key, String value) {
        init();
        put(key, value);
    }

    public TreeMap<String, String> getUrlParams() {
        return urlParams;
    }

    public void setUrlParams(TreeMap<String, String> urlParams) {
        this.urlParams = urlParams;
    }

    private void init() {
        urlParams = new TreeMap<String, String>();
    }

    /**
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        if (key != null && value != null) {
            urlParams.put(key, value);
        }
    }

    /**
     * 根据key删除对应的参数
     */
    public void remove(String key) {
        urlParams.remove(key);
    }

    public void clear() {
        urlParams.clear();
    }

    @Override
    public String toString() {
        try {
            StringBuffer bf = new StringBuffer();
            //过滤一些默认参数
            for (String key : urlParams.keySet()) {
                if ("platform".equals(key)) {
                    continue;
                } else if ("appKey".equals(key)) {
                    continue;
                } else if ("appVersion".equals(key)) {
                    continue;
                } else if ("ct_name".equals(key)) {
                    continue;
                } else if ("timestamp".equals(key)) {
                    continue;
                } else if ("sign".equals(key)) {
                    continue;
                }
                bf.append(key).append("=").append(urlParams.get(key)).append("，");
            }
            return "{ " + bf.toString().substring(0, bf.length() - 1) + " }";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
