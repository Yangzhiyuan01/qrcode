package com.wechat.qrcode.entity;

import java.io.Serializable;

public class ResultResponse<T> implements Serializable {
    private boolean isSuccess = false;
    private String comment;
    private String msgCode;
    private String message;
    private T data;

    public ResultResponse() {
    }

    public ResultResponse(String msgCode, String message) {
        this.msgCode = msgCode;
        this.message = message;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public void setSuccess(boolean success) {
        this.isSuccess = success;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMsgCode() {
        return this.msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
