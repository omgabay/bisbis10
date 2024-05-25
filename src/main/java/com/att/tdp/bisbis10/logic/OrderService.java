package com.att.tdp.bisbis10.logic;

import com.att.tdp.bisbis10.entities.*;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class OrderService {

    private final RestaurantService restaurantService;

    public OrderService(RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }


    public OrderResult submitOrder(Order order) throws IllegalArgumentException{
        Restaurant restaurant = restaurantService.getRestaurantById(order.getRestaurantId());
        if (restaurant == null){
            throw new IllegalArgumentException("Order was not created because restaurant is not found!");
        }

        Set<Long> dishIds = order.getOrderItems().stream().map(OrderItem::getDishId).collect(Collectors.toSet());
        Set<Long> availableDishIds = restaurant.getDishes().stream().map(Dish::id).collect(Collectors.toSet());
        if (!availableDishIds.containsAll(dishIds)){
            throw new IllegalArgumentException("Order includes dishes that cannot be found!");
        }

        return new OrderResult(order);
    }


}
