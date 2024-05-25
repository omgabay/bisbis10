package com.att.tdp.bisbis10.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "ratings")
public class UserRating {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "restaurant_id", nullable = false)
    Long restaurantId;

    Double rating;

    public UserRating() {}

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }


    public Long id() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public void setRating(Double rating) {
        this.rating = rating;
    }


    public Double getRating() {
        return rating;
    }

}
