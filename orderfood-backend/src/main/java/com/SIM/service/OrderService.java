package com.SIM.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.SIM.domain.Order;
import com.SIM.dto.OrderRequest;

import java.util.List;

public interface OrderService extends IService<Order> {
    // 根据客户手机号查询订单
    List<Order> findByCustomerPhone(String customerPhone);

    //创建订单
    Order createOrder(OrderRequest orderRequest);
}