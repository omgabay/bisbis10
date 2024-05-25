package com.att.tdp.bisbis10.entities;

public class OrderItem{

    private final Long dishId;
    private final Integer amount;

    public OrderItem(long dishId, int amount){
        this.dishId = dishId;
        this.amount = amount;
    }

    @Override
    public String toString(){
        return dishId.toString() + ":" + amount.toString();
    }

    public Long getDishId() {
        return dishId;
    }

    public Integer getAmount() {
        return amount;
    }

}
