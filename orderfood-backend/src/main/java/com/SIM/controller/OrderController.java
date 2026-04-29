package com.SIM.controller;

import com.SIM.ai.service.OrderAiService;
import com.SIM.domain.Order;
import com.SIM.dto.OrderRequest;
import com.SIM.service.DishService;
import com.SIM.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderAiService orderAiService;
    @Autowired
    private DishService dishService;;

    //创建订单
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(order);
    }

    //自然语言完成订单
    @PostMapping("/natural-language")
    public ResponseEntity<Order> naturalLanguage(@RequestBody String message) {
        OrderRequest orderRequest = orderAiService.aiOrder(message);
        Order order = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(order);
    }

    //获取订单
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Integer id) {
        Order order = orderService.getById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    //所有订单
    @GetMapping("/list")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.list();
        return ResponseEntity.ok(orders);
    }
}

























