/**
 *
 */
package com.jlbeauty.read.utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.jlbeauty.read.R;
import com.jlbeauty.read.bean.Version;
import com.jlbeauty.read.global.Config;

import com.jlbeauty.read.weight.AlertDialog;
import com.rongteng.base.global.Constant;
import com.rongteng.base.utils.AbJsonUtil;
import com.rongteng.base.utils.AbLogUtil;
import com.rongteng.base.utils.AbNetwork;
import com.rongteng.base.utils.AbToastUtil;
import com.rongteng.base.utils.AppUtil;
import com.rongteng.base.volley.IRequest;
import com.rongteng.base.volley.RequestListener;
import com.rongteng.base.volley.RequestParams;
import com.umeng.analytics.AnalyticsConfig;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import java.io.File;
import java.text.DecimalFormat;

/**
 * @author yinsujun
 *         <p/>
 *         2015-9-28下午6:47:23
 */
public class UpdateVersionUtil {
    private Context context;
    private int versionCode;
    private String versionName;
    private boolean isCloseDialog = true;//是否能关闭版本更新提示框 true:可以 ;false:不可以
    private int type = -1;// 0 自动检测更新 1 点击检测更新
    private NotificationManager mNotificationManager;
    Notification mNotification;
    private static final int NOTIFY_ID = 0x11;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    int count = (Integer) msg.obj;
                    RemoteViews contentview = mNotification.contentView;
                    contentview.setTextViewText(R.id.tv_progress, count + "%");
                    contentview.setProgressBar(R.id.progressbar, 100, count, false);
                    mNotificationManager.notify(NOTIFY_ID, mNotification);
                    break;
                case 1:
                    mNotificationManager.cancel(NOTIFY_ID);
                    break;
                default:
                    break;
            }
        }

        ;
    };

    public UpdateVersionUtil(Context context, int type) {
        this.context = context;
        this.type = type;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }


    public void checkVersion(final RelativeLayout version_updating) {
        IRequest http = new IRequest(context);
        versionCode = AppUtil.getAppVersionCode(context);
        versionName = AppUtil.getAppVersionName(context);
        RequestParams params = new RequestParams();
        params.put("version", versionName + "");
        params.put("client", Constant.CLIENT_ID_NAME);
        params.put("channel", AnalyticsConfig.getChannel(context));
        if (version_updating != null) {
            version_updating.setEnabled(false);
        }
        http.post(Config.URL_VERSIONON, params, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("版本更新", json.toString());
                if (version_updating != null) {
                    version_updating.setEnabled(true);
                }
                if (AbJsonUtil.isSuccess(json)) {
                    final Version version = (Version) AbJsonUtil.fromJson(json, Version.class);
                    if (version.getStatus().equals("maybe")) {
                        getDialog(version, isCloseDialog);
                    } else if (version.getStatus().equals("none")) {
                        if (type == 0) {
                            return;
                        } else if (type == 1) {
                            final AlertDialog dialog = new AlertDialog(context);
                            if (dialog != null) {
                                dialog.showDialog("版本更新", "已是最新版本V" + versionName, new AlertDialog.DialogOnClickListener() {

                                    @Override
                                    public void onPositiveClick() {
                                        dialog.removeDialog(context);
                                    }

                                    @Override
                                    public void onNegativeClick() {
                                        dialog.removeDialog(context);
                                    }
                                });
                            }
                        }
                    } else if (version.getStatus().equals("must")) {
                        if (AbNetwork.getNetworkType() == AbNetwork.NETTYPE_WIFI) {
                            isCloseDialog = false;
                            getDialog(version, isCloseDialog);
                        } else {
                            getDialog(version, isCloseDialog);
                        }
                    }
                }
            }

            @Override
            public void requestError(String message) {
                if (version_updating != null) {
                    version_updating.setEnabled(true);
                }
            }
        });

    }

    /**
     * 版本更新提示
     *
     * @param version      版本更新信息
     * @param isOpenDialog 是否能关闭版本更新提示框 true:可以 ;false:不可以
     */
    private void getDialog(final Version version, final boolean isOpenDialog) {
        final AlertDialog dialog = new AlertDialog(context, isOpenDialog);
        String content = version.getDescription();
        if (dialog != null) {
            if (!isOpenDialog) {
                dialog.hiddenCancel();
            }
            dialog.showDialog("版本更新", "最新版本：" + version.getVersion() + "\n" + content, new AlertDialog.DialogOnClickListener() {
                @Override
                public void onPositiveClick() {
                    dialog.removeDialog(context);
                    startDownLoad(version);
                }

                @Override
                public void onNegativeClick() {
                    dialog.removeDialog(context);
                }
            }, true);
        }
    }

    /**
     * 创建通知
     */
    @SuppressWarnings("deprecation")
    private void setUpNotification() {
        int icon = R.drawable.icon;
        CharSequence tickerText = "开始下载";
        long when = System.currentTimeMillis();
        mNotification = new Notification(icon, tickerText, when);
        // 放置在"正在运行"栏目中
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.download_notification_layout);
        contentView.setTextViewText(R.id.name, "正在下载 ");
        // 指定个性化视图
        mNotification.contentView = contentView;
//		Intent intent = new Intent(this, MainActivity.class);
//		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent,
//				PendingIntent.FLAG_UPDATE_CURRENT);
        // 指定内容意图
//		mNotification.contentIntent = contentIntent;
        mNotificationManager.notify(NOTIFY_ID, mNotification);
    }

    // 安装最新本版
    private void install(File file) {
        if (!file.exists()) {
            return;
        }
//		调系统的隐式意图
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String fileProvider = context.getResources().getString(R.string.fileprovider);
                Uri contentUri = FileProvider.getUriForFile(context, fileProvider, file);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            AbToastUtil.showToast(context, "自动安装失败，请到文件管理中手动安装!");
        }
    }


    private void startDownLoad(Version version) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setTitle("正在下载，请稍后...");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        setUpNotification();
        progress.show();
        String path = version.getUrl();
        FinalHttp fh = new FinalHttp();
        String newName;
        if (path.contains("/")) {
            newName = path.substring(path.lastIndexOf("/"), path.length());
        } else {
            newName = "/rongteng.apk";
        }
        // 判断有没有SD卡
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String target = Environment.getExternalStorageDirectory() + newName;
            fh.download(path, target, new AjaxCallBack<File>() {
                // 下载中
                @SuppressLint("NewApi")
                @Override
                public void onLoading(long count, long current) {
                    super.onLoading(count, current);
                    progress.setMax((int) (count));// 进度长总长
                    progress.setProgress((int) (current));// 当前进度
                    DecimalFormat df = new DecimalFormat(
                            "0.00");
                    String formatCount = df
                            .format((float) count / 1024 / 1024)
                            + "MB";
                    String formatCurrent = df
                            .format((float) current / 1024 / 1024)
                            + "MB";
                    progress.setProgressNumberFormat(formatCurrent
                            + "/"
                            + formatCount);
                    int loading = (int) (current / (float) count * 100);
                    Message.obtain(mHandler, 0, loading).sendToTarget();
                }

                @Override
                public void onSuccess(File t) {
                    super.onSuccess(t);
                    progress.dismiss();
                    Message.obtain(mHandler, 1).sendToTarget();
                    install(t);
                }
            });
        } else {
            // 没有SD卡
            Toast.makeText(context, "未检测到sd卡", Toast.LENGTH_SHORT).show();
            return;
        }
    }

}
