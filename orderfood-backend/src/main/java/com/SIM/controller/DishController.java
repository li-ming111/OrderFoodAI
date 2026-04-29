package com.SIM.controller;

import com.SIM.domain.Dish;
import com.SIM.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {

    @Autowired
    private DishService dishService;;

    //获取所有 菜品
    @GetMapping
    public List<Dish> getAllDishes(){
        return dishService.findByAvailableTrue();
    }

    //根据菜品的id获取
    @GetMapping("/id")
    public ResponseEntity<Dish> getDishById(@PathVariable int id){
        Dish dish = dishService.getById(id);
        if (dish == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dish);
    }

    //菜品分类
    @GetMapping("/category/{category}")
    public List<Dish> getDishByCategory(@PathVariable String category){
        return dishService.findByCategory(category);
    }
}
























