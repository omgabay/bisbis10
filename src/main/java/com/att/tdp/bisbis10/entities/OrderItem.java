package com.att.tdp.bisbis10.entities;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem{

    private Long dishId;
    private Integer amount;

    public OrderItem(){}

    public OrderItem(long dishId, int amount){
        this.dishId = dishId;
        this.amount = amount;
    }

}
