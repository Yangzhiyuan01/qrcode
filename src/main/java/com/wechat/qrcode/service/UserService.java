package com.wechat.qrcode.service;

import com.wechat.qrcode.entity.ResultResponse;
import com.wechat.qrcode.entity.WechatUsers;
import com.wechat.qrcode.entity.dto.CompanyOrganizationInfoDto;
import com.wechat.qrcode.entity.dto.CouponDetailedDto;
import com.wechat.qrcode.entity.dto.WechatUsersDto;

public interface UserService {

    /**
     * 小程序消息订阅时获取openId
     *
     * @param code
     * @return
     */
    ResultResponse getWxOpenid(String code);

    /**
     * 企微用户信息注册
     *
     * @param wechatUsers
     */
    ResultResponse userLogon(WechatUsers wechatUsers);

    /**
     * 微信用户信息录入，领取折扣卷
     *
     * @param wechatUsers
     */
    ResultResponse drawQrCode(WechatUsersDto wechatUsers);

    /**
     * 获取机构列表
     *
     * @param wechatUsers
     */
    ResultResponse selectOrganizationList(CompanyOrganizationInfoDto wechatUsers);

    /**
     * 更新消费凭证信息
     *
     * @param dto
     */
    ResultResponse updateConsumeVoucherUrl(CouponDetailedDto dto);

    /**
     * 发送短信验证码
     *
     * @param usersDto
     * @return
     */
    ResultResponse sendMsg(WechatUsersDto usersDto);
}
