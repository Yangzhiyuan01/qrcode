package com.wechat.qrcode.controller;

import com.alibaba.fastjson.JSONObject;
import com.wechat.qrcode.entity.ResultResponse;
import com.wechat.qrcode.service.ex.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("voucher")
public class VoucherController {

    private static final Logger LOGGER = LogManager.getLogger(VoucherController.class);

    //文件服务器存储地址
    @Value("${uploadPathImg}")
    private String uploadPathImg;

    //文件服务器访问路径
    @Value("${virtualImgUrl}")
    private String virtualImgUrl;

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("uploadVoucher.htm")
    @ResponseBody
    public ResultResponse uploadVoucher(@RequestParam(name = "file") MultipartFile file) {
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
            fileName = fileName.substring(0, fileName.lastIndexOf(".")) + (new Date()).getTime() + "." + suffix;
            /*InputStream is = file.getInputStream();
            String fileUrl = uploadPathImg;*/
            //第一次访问时创建默认地址
            File upload_file_dir_file = new File(uploadPathImg);
            if (!upload_file_dir_file.exists()) {
                upload_file_dir_file.mkdirs();
            }
            //将文件存到文件目录在
            //5.把浏览器上传的文件复制到希望的位置
            File targetFile = new File(upload_file_dir_file, fileName);
            file.transferTo(targetFile);
            //再拼接图片链接响应给前端
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("url", virtualImgUrl + fileName);
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
            throw new ServiceException("原始图片名称不能为空");
        }
        if (file.getInputStream() == null) {
            throw new ServiceException("图片数据流不能为空");
        }
    }
}
