package com.jlbeauty.read.activity.userCenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.jlbeauty.read.R;
import com.jlbeauty.read.adapter.SuggestionAdapter;
import com.jlbeauty.read.bean.SuggestionInfo;
import com.jlbeauty.read.bean.UploadInfo;
import com.jlbeauty.read.global.Config;
import com.jlbeauty.read.main.ReadApp;
import com.rongteng.base.global.Constant;
import com.rongteng.base.main.BaseActivity;
import com.rongteng.base.utils.AbDateUtil;
import com.rongteng.base.utils.AbImageUtil;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbMd5;
import com.rongteng.base.utils.AbStringUtil;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.utils.AppUtil;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.volley.RequestParams;
import com.rongteng.base.weight.EditTextWithDel;
import com.rongteng.base.weight.LoadingFragment;
import com.rongteng.base.weight.TitleView;


import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 意见反馈
 */
public class SuggestionsActivity extends BaseActivity {
    private static final int REQUEST_CODE_GALLERY = 200;
    private static final int REQUEST_CODE_EDIT = 201;
    protected String TAG = this.getClass().getSimpleName();
    private TitleView titleView;
    private String title;
    private GridView gv;
    private SuggestionAdapter adapter;
    private List<SuggestionInfo> imgs;
    private GalleryFinal.OnHanlderResultCallback onCallback;
    private FunctionConfig config;
    private Button complete;
    private EditText tv_suContent;
    private EditTextWithDel tv_phone;
    private FinalHttp fh;
    private String content;
    private int maxSize = 3;
    private LoadingFragment loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_suggestions);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        if (title != null) {
            titleView.setTitle(title);
        } else {
            titleView.setTitle("意见反馈");
        }
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * 跳转到 SuggestionsActivity
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SuggestionsActivity.class);
        context.startActivity(intent);
    }

    //上传次数
    private int index;

    @Override
    protected void initView() {
        tv_suContent = (EditText) findViewById(R.id.tv_suContent);
        tv_phone = (EditTextWithDel) findViewById(R.id.tv_phone);
        gv = (GridView) findViewById(R.id.gv);
        complete = (Button) findViewById(R.id.complete);
        imgs = new ArrayList<>();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.crame);
        SuggestionInfo info = new SuggestionInfo(bitmap, null);
        imgs.add(info);
        adapter = new SuggestionAdapter(imgs, this);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == imgs.size() - 1) {
                    getPhoto();
                } else {
                    index = position;
                    SuggestionInfo info1 = imgs.get(index);
                    GalleryFinal.openEdit(REQUEST_CODE_EDIT, info1.getPath(), onCallback);
                }
            }
        });
    }

    //选取图片并显示在界面上
    @Override
    protected void setData() {
        onCallback = new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if (reqeustCode == REQUEST_CODE_GALLERY) {
                    for (int i = 0; i < resultList.size(); i++) {
                        PhotoInfo info = resultList.get(i);
                        String photoPath = info.getPhotoPath();
                        Bitmap bitmap = AbImageUtil.readBitMap(photoPath);
                        SuggestionInfo info1 = new SuggestionInfo(bitmap, photoPath);
                        if (adapter.getCount() <= maxSize) {
                            adapter.addList(0, info1);
                        }
                    }
                    if (adapter.getCount() > maxSize) {
                        AbToastUtil.showToast(context, "已达到图片选择上线，请提交!");
                    }
                } else if (reqeustCode == REQUEST_CODE_EDIT) {
                    PhotoInfo photoInfo = resultList.get(0);
                    Bitmap bitmap = AbImageUtil.readBitMap(photoInfo.getPhotoPath());
                    SuggestionInfo info1 = new SuggestionInfo(bitmap, photoInfo.getPhotoPath());
                    adapter.replaceInfo(index, info1);
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        };
        config = new FunctionConfig.Builder().setMutiSelectMaxSize(3).build();
    }

    @Override
    protected void setEvent() {
        complete.setOnClickListener(this);
    }

    //点击提交按钮操作  添加提交上传时进度动画
    @Override
    public void onClick(View v) {
        content = tv_suContent.getText().toString();
        if (v.equals(complete)) {
            if (!AbStringUtil.isEmpty(content)) {
                if (content.length() < 1000 && content.length() > 4) {
                    fh = new FinalHttp();
                    loading = LoadingFragment.showLoading(context, "正在提交...");
                    if (imgs.size() > 1) {
                        for (int i = 0; i < imgs.size() - 1; i++) {
                            SuggestionInfo info = imgs.get(i);
                            UploadImgHttp(info.getPath());
                        }
                    } else {
                        sendHttp();
                    }
                } else {
                    AbToastUtil.showToast(context, "请输入吐槽内容在5-1000个字");
                }
            } else {
                AbToastUtil.showToast(context, "请输入吐槽内容");
            }
        }
    }


    /**
     * 获取图片
     */
    private void getPhoto() {
        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, config, onCallback);
    }

    private List<String> upLoadList = new ArrayList<>();

    /**
     * 上传图片网络请求的操作
     *
     * @param path
     */
    private synchronized void UploadImgHttp(String path) {
        AjaxParams params = new AjaxParams();
        params.put("appKey", Constant.APPKEY);
        params.put("timestamp", AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMDHMS));
        params.put("token", ReadApp.mApp.getToken());
        params.put("version", AppUtil.getAppVersionCode(this) + "");//版本号
        File file = null;
        try {
            file = new File(path);
            params.put("file", file); // 上传文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        AbLogUtil.e("file", file.toString());
        params.put("sign", getSign());//签名
        //上传图片给服务器的请求的地址（图片上传接口-支持[jpg,png,bmp]）
        fh.post(Config.URL_UPLOADIMGON, params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                super.onSuccess(json);
                AbLogUtil.e(TAG, json);
                if (AbJsonUtil.isSuccess(json)) {
                    UploadInfo info = (UploadInfo) AbJsonUtil.fromJson(json, UploadInfo.class);
                    if (info != null) {
                        index++;
                        //将获取到的url添加到upLoadList（string）类型的集合中
                        upLoadList.add(info.getUrl());
                        //上传最后一张图片调用的 一起打包上传
                        if (index == imgs.size() - 1) {
                            sendHttp();
                        }
                    }
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                AbToastUtil.showToast(context, strMsg);
            }
        });


    }

    //给网络请求添加标签以及给个签名
    private String getSign() {
        RequestParams params1 = new RequestParams();
        params1.put("appKey", Constant.APPKEY);
        params1.put("timestamp", AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMDHMS));
        params1.put("token", ReadApp.mApp.getToken());
        params1.put("version", AppUtil.getPackageInfo(this).versionCode + "");
        params1.remove("sign");
        AbLogUtil.e("sign", AbMd5.signTopRequest(params1.getUrlParams()));
        return AbMd5.signTopRequest(params1.getUrlParams());
    }


    /**
     * 提交给后台服务器
     */
    private void sendHttp() {
        //吐糟的内容的请求参数
        params.put("content", content);
        params.put("client", Constant.CLIENT_ID_NAME);
        //留下的电话号码的请求参数
        if (!AbStringUtil.isEmpty(tv_phone.getText().toString())) {
            params.put("contactWay", tv_phone.getText().toString());
        }
        //给提交内容的一个ID号码的请求的参数
        if (!AbStringUtil.isEmpty(ReadApp.mApp.getID())) {
            params.put("memberId", ReadApp.mApp.getID());
        }
        //上传的图片的url“[url],[url],[url]”以这样的一种格式
        if (upLoadList.size() > 0) {
            String url = AbJsonUtil.toJson(upLoadList);
            params.put("imgUrl", url);
        }
        //意见反馈的请求 提交上传所有内容的接口
        http.post(Config.URL_FEEDBACKON, params, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                LoadingFragment.dismiss(loading);
                AbLogUtil.e(TAG, json);
                if (AbJsonUtil.isSuccess(json)) {
                    AbToastUtil.showToast(context, "提交成功，感谢您的反馈");
                    finish();
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
            }

            //请求失败的时候调用的方法
            @Override
            public void requestError(String message) {
                LoadingFragment.dismiss(loading);
                AbToastUtil.showToast(context, message);
            }
        });
    }
}


