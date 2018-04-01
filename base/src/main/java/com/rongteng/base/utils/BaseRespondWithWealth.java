package com.rongteng.base.utils;

import com.rongteng.base.global.Constant;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/24.
 */

public class BaseRespondWithWealth implements Serializable{
    public static String PAGE_NUMBER="10";
    private int  errorCode=-1;
    private String errorMessage= Constant.DATA_EXCEPTION;


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess(){
        return this.getErrorCode()==0;
    }
}
