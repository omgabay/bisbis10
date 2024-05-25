package com.att.tdp.bisbis10.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "ratings")
@Getter
@Setter
@ToString
public class UserRating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id") // Foreign key column
    @JsonIgnore
    private Restaurant restaurant;

    @Column(name = "rating")
    double rating;

    public UserRating() {
        this.restaurant = new Restaurant();
    }

    public Long getRestaurantId(){
        return this.restaurant.getRestaurantId();
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurant.setRestaurantId(restaurantId);
    }

}
