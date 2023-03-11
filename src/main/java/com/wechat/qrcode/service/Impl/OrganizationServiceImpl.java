package com.wechat.qrcode.service.Impl;

import com.wechat.qrcode.entity.ResultResponse;
import com.wechat.qrcode.service.OrganizationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 机构信息
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private static final Logger LOGGER = LogManager.getLogger(OrganizationServiceImpl.class);

    /**
     * 获取机构提交的列表
     *
     * @param code
     * @return
     */
    @Override
    public ResultResponse getOrganizationList(String code) {
        return null;
    }
}
