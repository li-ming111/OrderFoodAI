package com.SIM.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.SIM.domain.Dish;
import com.SIM.domain.Order;
import com.SIM.domain.OrderItem;
import com.SIM.mapper.OrderMapper;
import com.SIM.service.DishService;
import com.SIM.service.OrderItemService;
import com.SIM.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.SIM.dto.OrderRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private DishService dishService;

    @Autowired
    private OrderItemService orderItemService;
    @Override
    public List<Order> findByCustomerPhone(String customerPhone) {
        return orderMapper.selectList(new LambdaQueryWrapper<Order>()
                .eq(Order::getCustomerPhone, customerPhone));
    }

    @Override
    public Order createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setCustomerName(orderRequest.getCustomerName());
        order.setCustomerPhone(orderRequest.getCustomerPhone());
        order.setDeliveryAddress(orderRequest.getDeliveryAddress());

        // 处理订单项
        List<OrderItem> orderItems = orderRequest.getItems().stream()
                .map(item -> {
                    // 获取菜品信息
                    Dish dish = dishService.getById(item.getDishId());
                    if (dish == null) {
                        throw new RuntimeException("菜品不存在: " + item.getDishId());
                    }
                    // 创建订单项
                    OrderItem orderItem = new OrderItem();
                    orderItem.setDishName(dish.getName());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setPrice(dish.getPrice());
                    orderItem.setSpecialInstructions(item.getSpecialInstructions());
                    return orderItem;
                })
                .collect(Collectors.toList());

        // 计算总金额
        BigDecimal totalAmount = orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // 设置订单总金额
        order.setTotalAmount(totalAmount);
        // 保存订单
        orderMapper.insert(order);
        // 设置订单项为前端显示订单的详细信息使用
        order.setOrderItems(orderItems);
        //保存订单项
        orderItems.forEach(item -> item.setOrderId(order.getId()));
        orderItemService.saveBatch(orderItems);
        return order;
    }
}