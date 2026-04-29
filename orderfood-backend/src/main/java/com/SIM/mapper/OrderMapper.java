package com.SIM.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.SIM.domain.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {}