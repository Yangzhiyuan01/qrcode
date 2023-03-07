package com.wechat.qrcode.mapper;

import com.wechat.qrcode.entity.WechatUsers;
import com.wechat.qrcode.entity.dto.WechatUsersDto;

public interface WechatUsersMapper {

    /**
     * 新增
     *
     * @param wechatUsers
     * @return
     */
    Integer insert(WechatUsers wechatUsers);

    /**
     * 根据openId获取用户信息
     *
     * @param wechatUsers
     * @return
     */
    WechatUsers selectByOpenId(WechatUsers wechatUsers);

    /**
     * 根据openId获取用户信息
     *
     * @param wechatUsers
     * @return
     */
    WechatUsersDto selectCouPonByOpenId(WechatUsers wechatUsers);

    /**
     * 根据微信openid注册用户信息
     *
     * @param wechatUsers
     * @return
     */
    int updateByOpenId(WechatUsers wechatUsers);
}
