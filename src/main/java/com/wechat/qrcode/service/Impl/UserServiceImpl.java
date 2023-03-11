package com.wechat.qrcode.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.wechat.qrcode.entity.CompanyOrganizationInfo;
import com.wechat.qrcode.entity.CouponDetailed;
import com.wechat.qrcode.entity.ResultResponse;
import com.wechat.qrcode.entity.WechatUsers;
import com.wechat.qrcode.entity.dto.CompanyOrganizationInfoDto;
import com.wechat.qrcode.entity.dto.WechatUsersDto;
import com.wechat.qrcode.mapper.CompanyOrganizationInfoMapper;
import com.wechat.qrcode.mapper.CouponDetailedMapper;
import com.wechat.qrcode.mapper.WechatUsersMapper;
import com.wechat.qrcode.service.UserService;
import com.wechat.qrcode.service.ex.ServiceException;
import com.wechat.qrcode.util.DateUtil;
import com.wechat.qrcode.util.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    @Resource
    private WechatUsersMapper wechatUsersMapper;

    @Resource
    private CouponDetailedMapper couponDetailedMapper;

    @Resource
    private CompanyOrganizationInfoMapper companyOrganizationInfoMapper;


    /**
     * 小程序消息订阅时获取openId
     *
     * @param code
     * @return
     */
    public ResultResponse getWxOpenid(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new ServiceException("微信code不能为空");
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("appid", "");//小程序appid
        params.put("secret", "");//小程序秘钥
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        String host = "https://api.weixin.qq.com/sns/oauth2/access_token";//小程序接口地址
        String result = new RestTemplate().getForEntity(host, String.class, params).getBody();
        ResultResponse resultResponse = new ResultResponse();
        if (StringUtils.isEmpty(result)) {
            throw new ServiceException("微信接口调用失败");
        } else {
            JSONObject jsonObject = JSONObject.parseObject(result);
            LOGGER.info("调用微信接口：" + host + "；传递参数：" + JSONObject.toJSONString(params) + "；获取的返回结果：" + result);
            if (StringUtils.isEmpty(jsonObject.getString("openid"))) {
                throw new ServiceException("微信接口返回结果无openid");
            } else {
                resultResponse.setData(jsonObject.getString("openid"));
            }
        }
        resultResponse.setSuccess(true);
        return resultResponse;
    }

    /**
     * 企微微信号录入
     *
     * @param wechatUsers
     */
    @Override
    public ResultResponse userLogon(WechatUsers wechatUsers) {
        if (StringUtils.isEmpty(wechatUsers.getOpenId())) {
            LOGGER.error("企微微信号录入,异常信息: 微信号不能为空");
            throw new ServiceException("微信号不能为空");
        }
        ResultResponse response = new ResultResponse();
        WechatUsersDto users = wechatUsersMapper.selectCouPonByOpenId(wechatUsers);
        //如果用户信息不存在，则录入openId 账户类型为临时账户
        if (users == null) {
            wechatUsers.setStatus(1);
            wechatUsers.setIsDelete(1);
            wechatUsers.setCreateTime(new Date());
            wechatUsersMapper.insert(wechatUsers);
            response.setData(wechatUsers);
        } else {
            updateCoupon(users);
            //展示用户注册信息及二维码信息
            response.setData(users);
        }
        response.setSuccess(true);
        return response;
    }

    /**
     * 在用户点击展示优惠劵时，更新优惠劵最新状态
     *
     * @param users
     */
    private void updateCoupon(WechatUsersDto users) {
        if (users.getCouponStatus() != null && users.getCouponStatus() == 1) {
            //如果账户已存在, 判断当前优惠劵是否已经过期
            if (users.getEndTime().getTime() - new Date().getTime() <= 0) {
                //更新优惠劵状态
                users.setCouponStatus(2);
                couponDetailedMapper.updateStatusByUserId(users.getId());
            }
        }
    }

    /**
     * 微信用户信息录入，领取折扣卷
     *
     * @param wechatUsers
     */
    @Override
    @Transient
    public ResultResponse drawQrCode(WechatUsersDto wechatUsers) {
        checkData(wechatUsers);
        ResultResponse response = new ResultResponse();
        WechatUsersDto users = wechatUsersMapper.selectCouPonByOpenId(wechatUsers);
        if (users == null) {
            throw new ServiceException("请先录入微信基础信息");
        } else if (users.getCouponStatus() != null && users.getCouponStatus() == 1) {
            throw new ServiceException("当前微信已领取消费折扣卷，请勿重复领取");
        } else {
            //个人/团体信息完善
            wechatUsersMapper.updateByOpenId(wechatUsers);
            //领取折扣卷，生产二维码
            CouponDetailed detailed = new CouponDetailed();
            detailed.setUserId(users.getId());
            detailed.setCouponCode(UUID.randomUUID().toString().replaceAll("-", ""));
            detailed.setStartTime(new Date());//生效时间
            detailed.setEndTime(DateUtil.stringToTime(DateUtils.getDays(7, true), DateUtil.YYYY_MM_DD_HH_MM_SS));//失效时间 + 7天
            detailed.setCouponType(1);//折扣卷来源 1：码上游
            detailed.setCouponStatus(1);//折扣卷状态 0：待生效 1：有效  2：已失效
            detailed.setBookingNumber(wechatUsers.getBookingNumber());//预订人数
            detailed.setIsDelete(1);//是否删除,1=未删除，2=已删除
            detailed.setCreateTime(new Date());
            int i = couponDetailedMapper.insert(detailed);
            if (i > 0) {
                response.setSuccess(true);
                response.setMessage("领取成功");
                return response;
            } else {
                LOGGER.error("优惠卷生成失败, 插入数据库失败；");
                throw new ServiceException("优惠卷生成失败，请稍后在领取");
            }
        }

    }

    /**
     * 参数验证
     *
     * @param wechatUsers
     */
    private void checkData(WechatUsersDto wechatUsers) {
        if (StringUtils.isEmpty(wechatUsers.getOpenId())) {
            throw new ServiceException("微信号不能为空");
        } else if (StringUtils.isEmpty(wechatUsers.getPhoneNumber())) {
            throw new ServiceException("手机号不能为空");
        } else if (StringUtils.isEmpty(wechatUsers.getCode())) {
            throw new ServiceException("验证码不能为空");
        } else if (StringUtils.isEmpty(wechatUsers.getStatus())) {
            //账户状态 1：临时账户 2：个人账户 3：团体账户
            throw new ServiceException("账户状态不能为空");
        } else if (wechatUsers.getStatus() == 2) {
            if (StringUtils.isEmpty(wechatUsers.getName())) {
                throw new ServiceException("姓名不能为空");
            } else if (StringUtils.isEmpty(wechatUsers.getIdCardType())) {
                throw new ServiceException("证件类型不能为空");
            } else if (StringUtils.isEmpty(wechatUsers.getIdCard())) {
                throw new ServiceException("证件号码不能为空");
            }
        } else if (wechatUsers.getStatus() == 3) {
            if (wechatUsers.getOrganizationId() == null) {
                throw new ServiceException("机构代码编号不能为空");
            } else if (StringUtils.isEmpty(wechatUsers.getBookingNumber())){
                throw new ServiceException("预订人数不能为空");
            } else if (wechatUsers.getBookingNumber() < 15){
                throw new ServiceException("预订人数低于15人");
            }
            CompanyOrganizationInfo organizationInfo = companyOrganizationInfoMapper.selectById(wechatUsers.getOrganizationId());
            if (organizationInfo == null) {
                throw new ServiceException("未获取到机构信息，请重新操作");
            }
        } else {
            throw new ServiceException("账户状态异常，请联系管理员");
        }
    }

    /**
     * 获取机构列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResultResponse selectOrganizationList(CompanyOrganizationInfoDto dto) {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setData(companyOrganizationInfoMapper.selectOrganizationInfoList(dto));
        resultResponse.setSuccess(true);
        return resultResponse;
    }
}
