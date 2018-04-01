package com.rongteng.base.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("DefaultLocale")
public class Tool {
    private SimpleDateFormat myFmt;
    private static Toast TOAST = null;

    public Tool() {

    }

    public static boolean CheckSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

//    @SuppressLint("ShowToast")
//    public static void showTextToast(Context context, String msg) {
//        if (TOAST == null) {
//            TOAST = Toast.makeText(context, msg, 2);
//        } else {
//            TOAST.setText(msg);
//        }
//        TOAST.show();
//    }

    /**
     * File转为byte[]
     *
     * @param f
     * @return
     */
    public static byte[] file2Byte(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int n;
            while ((n = stream.read(b)) != -1) {
                out.write(b, 0, n);
            }
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }

    /**
     * Bitmap转为byte[]
     *
     * @param bitmap
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        return baos.toByteArray();
    }

    public static Bitmap getBitmap(Context con, String path) {
        if (path.toLowerCase().startsWith("http")) {
            return getHttpBitmap(path);
        } else {
            return getAssetsBitmap(con, path);
        }
    }

    public static Bitmap getFileBitmap(Context con, String name) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    public static Bitmap getAssetsBitmap(Context con, String path) {
        Bitmap image = null;
        try {
            AssetManager am = con.getResources().getAssets();
            try {
                InputStream is = am.open(path);
                image = BitmapFactory.decodeStream(is);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public static Bitmap getHttpBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmap != null) {
        }
        return bitmap;
    }

    public static String saveBitmapToFile(String savepath, Bitmap bmp,
                                          String http) {
        if (folderCreate(savepath)) {
            String imgname = System.currentTimeMillis() + ".t";
            String filename = savepath + imgname;
            if (fileCreate(savepath, imgname) != null) {
                try {
                    FileOutputStream out = new FileOutputStream(filename);
                    bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return imgname;
            }
        }
        return null;
    }

    public static boolean saveBitmapToFile(String filepath, String filename,
                                           CompressFormat format, Bitmap bmp) {
        if (folderCreate(filepath)) {
            if (fileCreate(filepath, filename) != null) {
                try {
                    FileOutputStream out = new FileOutputStream(filepath
                            + filename);
                    bmp.compress(format, 90, out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }

    public static boolean delFolder(String folderpath, String onlyFirst) {
        File folder = new File(folderpath);
        if (!folder.exists()) {
            return false;
        }
        if (!folder.isDirectory()) {
            return false;
        }
        File[] files = folder.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (onlyFirst == null
                        || files[i].getName().indexOf(onlyFirst) == 0) {
                    files[i].delete();
                }
            }
        }
        return true;
    }

    /**
     * 文件是否存在
     *
     * @param filepath 文件路径
     * @param filename 文件名
     */
    public static boolean fileExists(String filepath, String filename) {
        File file = new File(filepath + filename);
        return file.exists();
    }

    /**
     * 创建文件夹
     *
     * @param filepath
     * @return
     */
    public static boolean folderCreate_(String filepath) {
        File folder = new File(filepath);
        if (!folder.exists()) {
            try {
                boolean b = folder.mkdirs();
                return b;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public static boolean folderCreate(String filePath) {
        try {
            File file = null;
            String newPath = null;
            String[] path = filePath.split("/");
            for (int i = 0; i < path.length; i++) {
                if (newPath == null) {
                    newPath = path[i];
                } else {
                    newPath = newPath + "/" + path[i];
                }
                file = new File(newPath);
                if (!file.exists()) {
                    file.mkdir();
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 创建文件
     *
     * @param filepath 文件路径
     * @param filename 文件名
     * @return
     */
    public static File fileCreate(String filepath, String filename) {
        try {
            File file = new File(filepath + filename);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            return file;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean fileRename(String filepath, String filename,
                                     String newname) {
        try {
            File file = new File(filepath + filename);
            String path = file.getParent();
            File newfile = new File(path + newname);
            if (file.exists()) {
                return file.renameTo(newfile);
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean writeFile(String filepath, String filename,
                                    byte[] info) {
        File file = fileCreate(filepath, filename);

        try {
            FileOutputStream op = new FileOutputStream(file);
            op.write(info);
            op.flush();
            op.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 版本号
     *
     * @param con
     * @return
     */
    public static int getAppVersionCode(Context con) {
        try {
            PackageInfo pkgInfo = con.getPackageManager().getPackageInfo(
                    con.getPackageName(), 0);
            if (pkgInfo != null) {
                return pkgInfo.versionCode;
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 版本名称
     *
     * @param con
     * @return
     */
    public static String getAppVersionName(Context con) {
        try {
            PackageInfo pkgInfo = con.getPackageManager().getPackageInfo(
                    con.getPackageName(), 0);
            if (pkgInfo != null) {
                return pkgInfo.versionName;
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 固定电话判断
     *
     * @param telephone
     * @return
     */
    public static boolean isTelephoneNum(String telephone) {
        Pattern p = Pattern.compile("^[\\d|\\D]{0,}\\d{7,11}[\\d|\\D]{0,}$");
        Matcher m = p.matcher(telephone);
        return m.matches();
    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public static void hideInputMethod(Activity activity) {
        if (null == activity) {
            return;
        }
        if (null != activity.getCurrentFocus() && null != activity.getCurrentFocus().getWindowToken()) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //OOM出现 解决办法
    public static Bitmap createBitmap(int width, int height, Bitmap.Config config) {
        Bitmap bitmap = null;
        try {
            bitmap = Bitmap.createBitmap(width, height, config);
        } catch (OutOfMemoryError e) {
            while (bitmap == null) {
                System.gc();
                System.runFinalization();
                bitmap = createBitmap(width, height, config);
            }
        }
        return bitmap;
    }

    /**
     * 获取屏幕分辨率
     *
     * @param context
     * @return
     */
    public static int[] getScreenDispaly(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        int width = windowManager.getDefaultDisplay().getWidth();
        @SuppressWarnings("deprecation")
        int height = windowManager.getDefaultDisplay().getHeight();
        int result[] = {width, height};
        return result;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return yinsujun 2015-8-20 下午5:34:18
     */
    public static int getWondowWith(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        int width = windowManager.getDefaultDisplay().getWidth();
        return width;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return yinsujun 2015-8-20 下午5:34:18
     */
    public static int getWondowHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        int height = windowManager.getDefaultDisplay().getWidth();
        return height;
    }

    // 判断字符串对象为null或者""
    public static boolean isBlank(String str) {
        return (str == null || TextUtils.isEmpty(str) || "null".equals(str));
    }

    // 四舍五入(保留两位小数)
    public static double changedoublehalf(double number) {
        BigDecimal b = new BigDecimal(number);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    // 四舍五入(无小数位)
    public static double changedoublenopoint(String string) {
        BigDecimal b = new BigDecimal(string);
        return b.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * @param version1
     * @param version2
     * @return if version1 > version2, return 1, if equal, return 0, else return -1
     */
    public static int compare(String version1, String version2) {
        if (version1 == null || version1.length() == 0 || version2 == null || version2.length() == 0)
            throw new IllegalArgumentException("Invalid parameter!");

        int index1 = 0;
        int index2 = 0;
        while (index1 < version1.length() && index2 < version2.length()) {
            int[] number1 = getValue(version1, index1);
            int[] number2 = getValue(version2, index2);

            if (number1[0] < number2[0]) return -1;
            else if (number1[0] > number2[0]) return 1;
            else {
                index1 = number1[1] + 1;
                index2 = number2[1] + 1;
            }
        }
        if (index1 == version1.length() && index2 == version2.length()) return 0;
        if (index1 < version1.length())
            return 1;
        else
            return -1;
    }

    /**
     * @param version
     * @param index   the starting point
     * @return the number between two dots, and the index of the dot
     */
    public static int[] getValue(String version, int index) {
        int[] value_index = new int[2];
        StringBuilder sb = new StringBuilder();
        while (index < version.length() && version.charAt(index) != '.') {
            sb.append(version.charAt(index));
            index++;
        }
        value_index[0] = Integer.parseInt(sb.toString());
        value_index[1] = index;

        return value_index;
    }

    /**
     * utf-8 转unicode
     *
     * @param str
     * @return String
     */
    public static String toUnicode(String str) {
        char[] arChar = str.toCharArray();
        int iValue = 0;
        String uStr = "";
        for (int i = 0; i < arChar.length; i++) {
            iValue = (int) str.charAt(i);
            char sValue = str.charAt(i);
            if (iValue <= 256) {
                // uStr+="& "+Integer.toHexString(iValue)+";";
                // uStr+="\\"+Integer.toHexString(iValue);
                uStr += sValue;
            } else {
                // uStr+="&#x"+Integer.toHexString(iValue)+";";
                uStr += "\\u" + Integer.toHexString(iValue);
            }
        }
        return uStr;
    }

    // 转32位大写MD5
    public final static String get32MD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            Log.e("Tool", "NoSuchAlgorithmException:" + e.toString());
        } catch (UnsupportedEncodingException e) {
            Log.e("Tool", "UnsupportedEncodingException:" + e.toString());
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }

    // 判断是否是手机号码
    public static boolean isMobileNO(String mobiles) {
        if (isBlank(mobiles))
            return false;
        Pattern p = Pattern.compile("^1[0-9]{10}$");
        // ^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    // 判断是否是邮箱地址
    public static boolean isEmail(String email) {
        if (isBlank(email))
            return false;
//        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        String str = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
//        String str = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    // 电话号码部分加*号
    public static String changeMobile(String telephone) {
        if (isBlank(telephone)) {
            return "";
        }
        if (!isMobileNO(telephone))
            return telephone;
        return telephone.substring(0, 3) + "****"
                + telephone.substring(7, telephone.length());
    }

    // 姓名加*号
    public static String changeName(String name) {
        if (isBlank(name)) {
            return "";
        }
        return "*" + name.substring(1, name.length());
    }

    // 身份证号加*号
    public static String changeIdentity(String identity) {
        if (isBlank(identity)) {
            return "";
        }
        if (identity.length() != 15 && identity.length() != 18) {
            return "身份证号码异常";
        }
        return identity.substring(0, 3) + "************"
                + identity.substring(identity.length() - 3, identity.length());
    }

    // 银行卡加*号
    public static String changeBankAccount(String bankAccount) {
        if (isBlank(bankAccount)) {
            return "";
        }
        return "**** **** **** *** "
                + bankAccount.substring(bankAccount.length() - 4, bankAccount.length());
    }

    // 小数进位
    public static double carryAigit(float number) {
        return Math.ceil(number);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    // 判断是否为数字
    public static boolean isNumeric(String str) {
        if (Tool.isBlank(str))
            return false;
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // 判断身份证是否合法
    public static String checkPersonalId(String personalId) {
        return IdCardUtils.IDCardValidate(personalId);
    }

    // 名字为汉字
    public static boolean isWord(String str) {
        if (Tool.isBlank(str) || str.trim().length() < 2) {
            return false;
        }
        return true;
    }

    // 检查银行卡号
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length()-1) == bit;
    }

    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    // 金额由10000分转为100.00元
    public static String toDeciDouble(String num) {
        if (isBlank(num)) {
            return "0.00";
        }
        if (num.contains(".")) {
            num = num.substring(0, num.indexOf("."));
        }
        int len = num.length();
        if (len == 1) {
            return "0.0" + num;
        } else if (len == 2) {
            return "0." + num;
        } else {
            return num.substring(0, len - 2) + "." + num.substring(len - 2);
        }
    }

    /**
     * 转两位小数 单位：元
     *
     * @param moneyInY
     * @return
     */
    public static String toDeciDouble2(double moneyInY) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(moneyInY);
    }

    // 金额由10000分转为100元
    public static String toIntAccount(String num) {
        if (isBlank(num)) {
            return "0";
        }
        return "" + Long.parseLong(num) / 100;
    }

    // 金额由1000000分转为10,000元
    public static String toDivAccount(String num) {
        if (isBlank(num)) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(Long.parseLong(num) / 100);
    }

    // 金额由10000转为10,000元
    public static String toDivAccount1(String num) {
        if (isBlank(num)) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(Long.parseLong(num));
    }

    // 金额由10000.00转为10,000.00元
    public static String toDivAccount2(String num) {
        if (isBlank(num) || Double.parseDouble(num) == 0d) {
            return "0.00";
        }
        if (Double.parseDouble(num) < 1d && Double.parseDouble(num) > 0) {
            return num;
        }
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(Double.parseDouble(num));
    }

    //时间比较
    public long getDateShort(String time1, String time2) {
        long dateTime = 0;
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(myFmt.parse(time1));
            long date1 = c.getTimeInMillis();

            Calendar c2 = Calendar.getInstance();
            c2.setTime(myFmt.parse(time2));
            long date2 = c2.getTimeInMillis();
            dateTime = date2 - date1;
            return dateTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    // 利率保留两位小数，要四舍五入
    public static String toFFDoubleForApr3(double num) {
        if (Double.isNaN(num)) {
            return "0.00";
        }
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        BigDecimal bd = new BigDecimal(num);
        num = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return toDeciDouble(nf.format(num * 1000));
    }

    //利率保留两位小数，要四舍五入
    public static String toFFDoubleForApr(double num) {
        if (Double.isNaN(num)) {
            return "0.00";
        }
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        BigDecimal bd = new BigDecimal(num);
        num = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return toDeciDouble(nf.format(num * 100));
    }

    public static String toFFDouble(double num) {
        if (Double.isNaN(num)) {
            return "0.00";
        }
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        BigDecimal bd = new BigDecimal(num);
        num = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return toDeciDouble(nf.format(num * 100));
    }

    // 将输入的数字转为0.00格式的double
    public static Double toForDouble2(String num) {
        if (Tool.isBlank(num)) {
            return 0.00;
        }
        return Double.parseDouble((new DecimalFormat("0.00")).format(Double
                .parseDouble(num)));
    }

    //把一个字符串中的大写转为小写，小写转换为大写：思路1
    public static String exChange(String str) {
        StringBuffer sb = new StringBuffer();
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (Character.isUpperCase(c)) {
                    sb.append(Character.toLowerCase(c));
                } else if (Character.isLowerCase(c)) {
                    sb.append(Character.toUpperCase(c));
                }
            }
        }

        return sb.toString();
    }

    //把一个字符串中的大写转为小写，小写转换为大写：思路2
    public static String exChange2(String str) {
        for (int i = 0; i < str.length(); i++) {
            //如果是小写
            if (str.substring(i, i + 1).equals(str.substring(i, i + 1).toLowerCase())) {
                str.substring(i, i + 1).toUpperCase();
            } else {
                str.substring(i, i + 1).toLowerCase();
            }
        }
        return str;
    }


    /**
     * 是否是数字
     *
     * @param number
     * @return
     */
    public static boolean isNumber(String number) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(number);
        return m.matches();
    }


    public static boolean isName(String name) {
        Pattern p = Pattern.compile("^[A-Za-z\\u4e00-\\u9fa5][\\u4e00-\\u9fa5\\da-zA-Z_]{3,15}$");
        Matcher m = p.matcher(name);
        return m.matches();
    }

    /**
     * 非法字符illegal character 判断是否有特殊符号
     *
     * @param number
     * @return
     */
    public static boolean isIllegalCharacter(String number) {
        Pattern p = Pattern.compile("^[_A-Za-z0-9]+$");
        Matcher m = p.matcher(number);
        return m.matches();
    }


    // 校验Tag Alias 只能是数字,英文字母和中文
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 网络是否连接
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

}
