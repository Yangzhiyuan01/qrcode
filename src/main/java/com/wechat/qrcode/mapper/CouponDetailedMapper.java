package com.wechat.qrcode.mapper;

import com.wechat.qrcode.entity.CouponDetailed;
import com.wechat.qrcode.entity.dto.CouponDetailedDto;

public interface CouponDetailedMapper {

    /**
     * 新增
     *
     * @param couponDetailed
     * @return
     */
    Integer insert(CouponDetailed couponDetailed);

    /**
     * 根据userId获取用户二维码信息
     *
     * @param userId
     * @return
     */
    CouponDetailedDto selectByUserId(Long userId);

    /**
     * 根据用户id，更新优惠劵状态
     *
     * @param userId
     * @return
     */
    int updateStatusByUserId(Long userId);

    /**
     * 根据id更新消费凭证信息
     *
     * @param dto
     * @return
     */
    int updateConsumeVoucherUrlById(CouponDetailedDto dto);
}
