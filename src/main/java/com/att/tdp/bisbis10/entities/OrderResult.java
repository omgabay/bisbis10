package com.att.tdp.bisbis10.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
public class OrderResult {

    @Id
    private UUID orderId;

    @JsonIgnore
    private Long restaurantId;

    @JsonIgnore
    private String orderItems;


    public OrderResult() {
        orderId = UUID.randomUUID();
    }

    public OrderResult(Order orderRequest) {
        this.orderId = UUID.randomUUID();
        this.restaurantId = orderRequest.getRestaurantId();
        List<String> orderItemsStr = orderRequest.getOrderItems().stream().map(OrderItem::toString).toList();
        this.orderItems = String.join(",", orderItemsStr);
    }

}


