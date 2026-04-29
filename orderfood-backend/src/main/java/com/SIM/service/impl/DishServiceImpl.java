package com.SIM.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.SIM.domain.Dish;
import com.SIM.mapper.DishMapper;
import com.SIM.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Override
    public List<Dish> findByCategory(String category) {
        return dishMapper.selectList(new LambdaQueryWrapper<Dish>()
                .eq(Dish::getCategory, category));
    }

    @Override
    public List<Dish> findByAvailableTrue() {
        return dishMapper.selectList(new LambdaQueryWrapper<Dish>()
                .eq(Dish::getAvailable, true));
    }
}