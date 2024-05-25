package com.att.tdp.bisbis10.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Order {

    private Long restaurantId;

    private List<OrderItem> orderItems;


}
