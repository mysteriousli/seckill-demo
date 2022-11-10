package com.example.seckilldemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckilldemo.pojo.Order;
import com.example.seckilldemo.pojo.User;
import com.example.seckilldemo.vo.GoodsVo;
import com.example.seckilldemo.vo.OrderDetailVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liguangyuan
 * @since 2022-04-06
 */
public interface IOrderService extends IService<Order> {
    Order seckill(User user, GoodsVo goodsVo);

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    OrderDetailVo detail(Long orderId);
}
