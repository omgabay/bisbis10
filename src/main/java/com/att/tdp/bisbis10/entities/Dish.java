package com.att.tdp.bisbis10.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


@Entity
public class Dish{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_id")
    private Long dishId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id") // Foreign key column
    @JsonIgnore
    private Restaurant restaurant;


    private String name;
    private String description;
    private Integer price;


    public Dish() {}

    public Long id(){
        return dishId;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String toString() {
        return name + ": " + price + "$";
    }
}
