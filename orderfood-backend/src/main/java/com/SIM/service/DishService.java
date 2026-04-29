package com.SIM.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.SIM.domain.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    //分类查找
    List<Dish> findByCategory(String category);
    //是否可用
    List<Dish> findByAvailableTrue();
}