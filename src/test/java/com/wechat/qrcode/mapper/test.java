package com.wechat.qrcode.mapper;

import com.mysql.cj.util.DataTypeUtil;
import com.wechat.qrcode.entity.CouponDetailed;
import com.wechat.qrcode.entity.WechatUsers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.unit.DataUnit;

import javax.annotation.Resource;
import java.util.Date;

//@SpringBootTest:表示标注当前的类是个测试类，不会随同项目一块打包发送
@SpringBootTest
//@RunWith:表示这个是单元测试类（单元测试类是不能运行的）需要传递一个参数，必须是SpringRunner的实体类
@RunWith(SpringRunner.class)
public class test {

    @Resource
    private WechatUsersMapper wechatUsersMapper;

    @Resource
    private CouponDetailedMapper couponDetailedMapper;

    @Test
    public void insertWechatUsersTest(){
        WechatUsers wechatUsers = new WechatUsers();
        wechatUsers.setOpenId("WSD21515151515115");
        wechatUsers.setNickname("测试123456");
        wechatUsers.setHeadUrl(null);
        wechatUsers.setName("张三");
        wechatUsers.setIdCardType(1);
        wechatUsers.setIdCard("430322115877845521");
        wechatUsers.setPhoneNumber("18621198878");
        wechatUsers.setOrganizationId(2L);
        wechatUsers.setStatus(1);
        wechatUsers.setIsDelete(1);
        wechatUsers.setCreateTime(new Date());
        int i = wechatUsersMapper.insert(wechatUsers);
        System.out.println(i);
    }

    @Test
    public void insertCouponDetailedTest(){
        CouponDetailed wechatUsers = new CouponDetailed();
        wechatUsers.setUserId(2L);
        wechatUsers.setCouponCode("544551545415411");
        wechatUsers.setStartTime(new Date());
        wechatUsers.setEndTime(new Date());
        wechatUsers.setCouponType(1);
        wechatUsers.setCouponStatus(1);
        wechatUsers.setIsDelete(1);
        wechatUsers.setCreateTime(new Date());
        int i = couponDetailedMapper.insert(wechatUsers);
        System.out.println(i);
    }
}
