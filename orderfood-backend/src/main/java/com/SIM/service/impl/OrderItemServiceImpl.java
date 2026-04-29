package com.SIM.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.SIM.domain.OrderItem;
import com.SIM.mapper.OrderItemMapper;
import com.SIM.service.OrderItemService;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem>
        implements OrderItemService {}