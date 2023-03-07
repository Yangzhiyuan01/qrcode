package com.wechat.qrcode.entity.dto;

import com.wechat.qrcode.entity.WechatUsers;

import java.util.Date;

public class WechatUsersDto extends WechatUsers {

    /**
     * 折扣卷编码
     */
    private String couponCode;

    /**
     * 生效时间
     */
    private Date startTime;

    /**
     * 失效时间
     */
    private Date endTime;

    /**
     * 折扣卷来源 1：码上游
     */
    private Integer couponType;

    /**
     * 折扣卷状态 0：待生效 1：有效  2：已失效
     */
    private Integer couponStatus;

    /**
     * 生效日期
     */
    private String stateDate;

    /**
     * 失效日期
     */
    private String endDate;

    /**
     * 验证码
     */
    private String code;

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public Integer getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(Integer couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getStateDate() {
        return stateDate;
    }

    public void setStateDate(String stateDate) {
        this.stateDate = stateDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
