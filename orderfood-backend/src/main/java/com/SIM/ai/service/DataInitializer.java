package com.SIM.ai.service;

import com.SIM.domain.Dish;
import com.SIM.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private DishService dishService;

    @Override
    public void run(String... args) {
        // 只有当没有菜品数据时才初始化
        if (dishService.count() == 0) {
            Dish[] dishes = {
                    new Dish()
                            .setName("宫保鸡丁")
                            .setDescription("经典川菜，口感麻辣鲜香")
                            .setPrice(new BigDecimal("38.00"))
                            .setCategory("川菜")
                            .setImageUrl("images/jbjd.png"),

                    new Dish()
                            .setName("麻婆豆腐")
                            .setDescription("传统川菜，麻辣鲜香")
                            .setPrice(new BigDecimal("32.00"))
                            .setCategory("川菜")
                            .setImageUrl("images/ljcr.png"),

                    new Dish()
                            .setName("糖醋里脊")
                            .setDescription("酸甜可口，外酥里嫩")
                            .setPrice(new BigDecimal("42.00"))
                            .setCategory("粤菜")
                            .setImageUrl("images/mpdf.png"),

                    new Dish()
                            .setName("辣椒炒肉")
                            .setDescription("辣辣辣！")
                            .setPrice(new BigDecimal("68.00"))
                            .setCategory("湘菜")
                            .setImageUrl("images/tclj.png")
            };
            dishService.saveBatch(Arrays.asList(dishes));
        }
    }
}