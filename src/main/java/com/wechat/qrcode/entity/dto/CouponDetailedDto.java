package com.wechat.qrcode.entity.dto;

import com.wechat.qrcode.entity.CouponDetailed;

public class CouponDetailedDto extends CouponDetailed {

    /**
     * 生效日期
     */
    private String stateDate;

    /**
     * 失效日期
     */
    private String endDate;

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
}
