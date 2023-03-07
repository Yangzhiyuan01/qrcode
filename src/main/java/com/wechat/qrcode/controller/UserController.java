package com.wechat.qrcode.controller;

import com.wechat.qrcode.entity.ResultResponse;
import com.wechat.qrcode.entity.WechatUsers;
import com.wechat.qrcode.entity.dto.WechatUsersDto;
import com.wechat.qrcode.service.UserService;
import com.wechat.qrcode.service.ex.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("users")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("userLogon.htm")
    @ResponseBody
    public ResultResponse userLogon(WechatUsers users){
        ResultResponse response = new ResultResponse();
        try {
            response = userService.userLogon(users);
        } catch (ServiceException e){
            response.setMessage(e.getMessage());
        } catch (Exception e){
            response.setMessage("网络异常,交易失败");
        }
        return response;
    }

    //测试提交
}
