package com.att.tdp.bisbis10.logic;


import com.att.tdp.bisbis10.entities.Dish;
import com.att.tdp.bisbis10.entities.Order;
import com.att.tdp.bisbis10.entities.OrderItem;
import com.att.tdp.bisbis10.entities.Restaurant;
import com.att.tdp.bisbis10.repos.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantService restaurantService;

    public OrderService(OrderRepository orderRepository, RestaurantService restaurantService){
        this.orderRepository = orderRepository;
        this.restaurantService = restaurantService;
    }


    public Order submitOrder(Order order) throws IllegalArgumentException{
        Order result = null;
        Restaurant restaurant = restaurantService.getRestaurantById(order.getRestaurantId());
        if (restaurant == null){
            throw new IllegalArgumentException("Order was not created because restaurant not found!");
        }

        Set<Long> dishIds = order.getOrderItems().stream().map(OrderItem::getDishId).collect(Collectors.toSet());
        Set<Long> availableDishIds = restaurant.getDishes().stream().map(Dish::id).collect(Collectors.toSet());
        if (availableDishIds.containsAll(dishIds)){
            result = orderRepository.save(order);
        }else{
            throw new IllegalArgumentException("Order includes dishes that are not available");
        }

        return result;
    }


}
