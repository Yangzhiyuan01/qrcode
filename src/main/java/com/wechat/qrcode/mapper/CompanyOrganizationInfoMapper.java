package com.wechat.qrcode.mapper;

import com.wechat.qrcode.entity.CompanyOrganizationInfo;
import com.wechat.qrcode.entity.dto.CompanyOrganizationInfoDto;

import java.util.List;

public interface CompanyOrganizationInfoMapper {

    /**
     * 获取企业机构信息
     *
     * @param dto
     * @return
     */
    List<CompanyOrganizationInfoDto> selectOrganizationInfoList(CompanyOrganizationInfoDto dto);

    /**
     * 获取企业机构信息
     *
     * @param id
     * @return
     */
    CompanyOrganizationInfo selectById(Long id);
}
