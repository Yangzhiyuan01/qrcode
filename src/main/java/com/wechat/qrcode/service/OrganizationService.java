package com.wechat.qrcode.service;

import com.wechat.qrcode.entity.ResultResponse;

public interface OrganizationService {

    /**
     * 获取机构提交的列表
     *
     * @param code
     * @return
     */
    ResultResponse getOrganizationList(String code);
}
