package com.wechat.qrcode.controller;

import com.wechat.qrcode.entity.ResultResponse;
import com.wechat.qrcode.entity.WechatUsers;
import com.wechat.qrcode.entity.dto.CompanyOrganizationInfoDto;
import com.wechat.qrcode.entity.dto.CouponDetailedDto;
import com.wechat.qrcode.entity.dto.WechatUsersDto;
import com.wechat.qrcode.service.UserService;
import com.wechat.qrcode.service.ex.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("users")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Resource
    private UserService userService;

    /**
     * 小程序消息订阅时获取openId
     *
     * @param code
     * @return
     */
    @PostMapping("getWxOpenid.htm")
    @ResponseBody
    public ResultResponse getWxOpenid(String code) {
        ResultResponse response = new ResultResponse();
        try {
            response = userService.getWxOpenid(code);
        } catch (ServiceException e) {
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setMessage("网络异常,交易失败");
        }
        return response;
    }

    /**
     * 根据微信号获取用户信息
     *
     * @param users
     * @return
     */
    @PostMapping("userLogon.htm")
    @ResponseBody
    public ResultResponse userLogon(WechatUsers users) {
        ResultResponse response = new ResultResponse();
        try {
            response = userService.userLogon(users);
        } catch (ServiceException e) {
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setMessage("网络异常,交易失败");
        }
        return response;
    }

    /**
     * 微信用户信息录入，领取折扣卷
     *
     * @param dto
     * @return
     */
    @PostMapping("drawQrCode.htm")
    @ResponseBody
    public ResultResponse drawQrCode(WechatUsersDto dto) {
        ResultResponse response = new ResultResponse();
        try {
            response = userService.drawQrCode(dto);
        } catch (ServiceException e) {
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setMessage("网络异常,交易失败");
        }
        return response;
    }

    /**
     * 获取机构列表
     *
     * @param dto
     * @return
     */
    @PostMapping("selectOrganizationList.htm")
    @ResponseBody
    public ResultResponse selectOrganizationList(CompanyOrganizationInfoDto dto) {
        ResultResponse response = new ResultResponse();
        try {
            response = userService.selectOrganizationList(dto);
        } catch (ServiceException e) {
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setMessage("网络异常,交易失败");
        }
        return response;
    }

    /**
     * 更新消费凭证信息
     *
     * @param dto
     * @return
     */
    @PostMapping("updateConsumeVoucherUrl.htm")
    @ResponseBody
    public ResultResponse updateConsumeVoucherUrl(CouponDetailedDto dto) {
        ResultResponse response = new ResultResponse();
        try {
            response = userService.updateConsumeVoucherUrl(dto);
        } catch (ServiceException e) {
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setMessage("网络异常,交易失败");
        }
        return response;
    }


    /**
     * 发送短信验证码
     *
     * @param usersDto
     * @return
     */
    @PostMapping("sendMsg.htm")
    @ResponseBody
    public ResultResponse sendMsg(WechatUsersDto usersDto) {
        ResultResponse response = new ResultResponse();
        try {
            response = userService.sendMsg(usersDto);
        } catch (ServiceException e) {
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setMessage("网络异常,交易失败");
        }
        return response;
    }

}
