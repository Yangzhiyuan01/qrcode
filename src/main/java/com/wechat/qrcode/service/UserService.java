package com.wechat.qrcode.service;

import com.wechat.qrcode.entity.ResultResponse;
import com.wechat.qrcode.entity.WechatUsers;
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
}
