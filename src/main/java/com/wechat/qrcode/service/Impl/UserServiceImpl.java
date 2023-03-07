package com.wechat.qrcode.service.Impl;

import com.wechat.qrcode.entity.CouponDetailed;
import com.wechat.qrcode.entity.ResultResponse;
import com.wechat.qrcode.entity.WechatUsers;
import com.wechat.qrcode.entity.dto.WechatUsersDto;
import com.wechat.qrcode.mapper.CouponDetailedMapper;
import com.wechat.qrcode.mapper.WechatUsersMapper;
import com.wechat.qrcode.service.UserService;
import com.wechat.qrcode.service.ex.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private WechatUsersMapper wechatUsersMapper;

    @Resource
    private CouponDetailedMapper couponDetailedMapper;

    /**
     * 企微微信号录入
     *
     * @param wechatUsers
     */
    @Override
    public ResultResponse userLogon(WechatUsers wechatUsers) {
        if (StringUtils.isEmpty(wechatUsers.getOpenId())){
            throw new ServiceException("微信号不能为空");
        }
        ResultResponse response = new ResultResponse();
        WechatUsersDto users = wechatUsersMapper.selectCouPonByOpenId(wechatUsers);
        //如果用户信息不存在，则录入openId 账户类型为临时账户
        if (users == null){
            wechatUsers.setStatus(1);
            wechatUsers.setIsDelete(1);
            wechatUsers.setCreateTime(new Date());
            wechatUsersMapper.insert(wechatUsers);
            response.setData(wechatUsers);
        } else {
            //如果账户已存在，则展示用户注册信息及二维码信息
            response.setData(users);
        }
        response.setSuccess(true);
        return response;
    }


    /**
     * 企微用户信息录入，领取折扣卷
     *
     * @param wechatUsers
     */
    @Override
    public ResultResponse userEntry(WechatUsersDto wechatUsers) {
        checkData(wechatUsers);
        ResultResponse response = new ResultResponse();
        WechatUsers users = wechatUsersMapper.selectByOpenId(wechatUsers);
        if (users == null){
            throw new ServiceException("请先录入微信基础信息");
        } else if (users.getStatus() != 1){
            throw new ServiceException("当前微信已领取消费折扣卷，请勿重复领取");
        } else {
            //个人/团体信息完善
            wechatUsersMapper.updateByOpenId(wechatUsers);
            //领取折扣卷，生产二维码
            CouponDetailed detailed = new CouponDetailed();
            detailed.setUserId(wechatUsers.getId());
            detailed.setCouponCode(UUID.randomUUID().toString().replaceAll("-", ""));
            detailed.setStartTime(new Date());
            detailed.setEndTime(new Date());
            //
            couponDetailedMapper.insert(detailed);
        }
        response.setSuccess(true);
        return response;
    }

    /**
     * 参数验证
     *
     * @param wechatUsers
     */
    private void checkData(WechatUsersDto wechatUsers){
        if (StringUtils.isEmpty(wechatUsers.getOpenId())){
            throw new ServiceException("微信号不能为空");
        } else if (StringUtils.isEmpty(wechatUsers.getName())){
            throw new ServiceException("姓名不能为空");
        } else if (StringUtils.isEmpty(wechatUsers.getIdCardType())){
            throw new ServiceException("证件类型不能为空");
        } else if (StringUtils.isEmpty(wechatUsers.getIdCard())){
            throw new ServiceException("证件号码不能为空");
        } else if (StringUtils.isEmpty(wechatUsers.getPhoneNumber())){
            throw new ServiceException("手机号不能为空");
        } else if (StringUtils.isEmpty(wechatUsers.getCode())){
            throw new ServiceException("验证码不能为空");
        } else if (StringUtils.isEmpty(wechatUsers.getStatus())){
            //账户状态 1：临时账户 2：个人账户 3：团体账户
            throw new ServiceException("账户状态不能为空");
        } else if (wechatUsers.getStatus() == 2){
            if (StringUtils.isEmpty(wechatUsers.getOrganizationCode())){
                throw new ServiceException("机构代码不能为空");
            }
            //验证码校验
        }
    }

}
