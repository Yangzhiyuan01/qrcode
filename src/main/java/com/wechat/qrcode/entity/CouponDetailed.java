package com.wechat.qrcode.entity;

import java.io.Serializable;
import java.util.Date;

public class CouponDetailed implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户表id
     */
    private Long userId;

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
     * 是否删除,1=未删除，2=已删除
     */
    private Integer isDelete;

    /**
     * 是创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 门票预订人数
     */
    private Integer bookingNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(Integer bookingNumber) {
        this.bookingNumber = bookingNumber;
    }
}
