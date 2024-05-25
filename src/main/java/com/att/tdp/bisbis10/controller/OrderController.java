package com.att.tdp.bisbis10.controller;


import com.att.tdp.bisbis10.entities.Order;
import com.att.tdp.bisbis10.logic.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/order")
    ResponseEntity<Order> createOrder(@RequestBody Order order) {
        try{
            Order savedOrder = this.orderService.submitOrder(order);
            return ResponseEntity.ok(savedOrder);
        }catch (IllegalArgumentException iae){
            logger.error(iae.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }




}
