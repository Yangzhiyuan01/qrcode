package com.wechat.qrcode.controller;

import com.alibaba.fastjson.JSONObject;
import com.wechat.qrcode.entity.ResultResponse;
import com.wechat.qrcode.entity.WechatUsers;
import com.wechat.qrcode.entity.dto.CompanyOrganizationInfoDto;
import com.wechat.qrcode.entity.dto.WechatUsersDto;
import com.wechat.qrcode.service.UserService;
import com.wechat.qrcode.service.ex.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

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
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("uploadInvoice.htm")
    @ResponseBody
    public ResultResponse uploadInvoice(@RequestParam(name = "file") MultipartFile file) {
        ResultResponse resultResponse = new ResultResponse();
        try {
            //判断文件格式
            checkUploadData(file);
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String[] filenames = {"jpg", "png", "jpeg"};
            boolean checkFlag = false;//false代表不符合文件格式
            for (String filenameCheck : filenames) {
                if (filenameCheck.equalsIgnoreCase(suffix)) {
                    checkFlag = true;
                    break;
                }
            }
            if (!checkFlag) {
                throw new ServiceException("上传文件格式错误");
            }
            //判断文件是否超过10M
            Long file1 = file.getSize();
            System.out.println(file1);
            if (file.getSize() > 10 * 1024 * 1024) {
                throw new ServiceException("上传文件大小不能超过10M");
            }
            //获取文件后缀名
            fileName =  fileName.substring(0, fileName.lastIndexOf("."))  + (new Date()).getTime() + "." + suffix;
            InputStream is = file.getInputStream();
            //将文件存到文件目录在

            //再拼接图片链接响应给前端

            //aliOss.uploadFile(is, fileName, environment.getProperty("oss.bucketName.img"), file.getContentType());
            //String url = String.format(environment.getProperty("oss.download.url"), environment.getProperty("oss.bucketName.img"));


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("url", "");
            jsonObject.put("invoiceUrl", fileName);
            resultResponse.setData(jsonObject);
            resultResponse.setSuccess(true);
        } catch (IOException e) {
            LOGGER.error(file.getOriginalFilename() + "上传失败");
        }
        return resultResponse;
    }


    /**
     * 判断文件是否存在
     *
     * @param file
     * @throws IOException
     */
    private void checkUploadData(MultipartFile file) throws IOException {
        if (StringUtils.isBlank(file.getOriginalFilename())) {
            throw new ServiceException( "原始图片名称不能为空");
        }
        if (file.getInputStream() == null) {
            throw new ServiceException( "图片数据流不能为空");
        }
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


}
