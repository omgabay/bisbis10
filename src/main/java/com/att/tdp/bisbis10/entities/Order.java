package com.att.tdp.bisbis10.entities;

import com.att.tdp.bisbis10.auxillary.OrderItemConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import java.util.UUID;


@Entity
public class Order {



    private UUID orderId;

    private Long restaurantId;


    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

}


