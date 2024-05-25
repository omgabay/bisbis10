package com.att.tdp.bisbis10.entities;


import com.att.tdp.bisbis10.auxillary.StringListConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="restaurant_id")
    Long restaurantId;

    @Column(name="name")
    private String name;

    @Column(name="is_kosher")
    private boolean isKosher;

    @Convert(converter = StringListConverter.class)
    @Column(name="cuisines")
    private List<String> cuisines  = new ArrayList<>();

    @Column(name="average_rating")
    private Double averageRating = 0.0;

    @OneToMany(mappedBy = "restaurant", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Dish> dishes =  new ArrayList<>();


    @OneToMany(mappedBy = "restaurant", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Order> orders = new ArrayList<>();

    public Restaurant() {}

    public Restaurant(String name, boolean isKosher, List<String> cuisines){
        this.name = name;
        this.isKosher = isKosher;
        this.cuisines = new ArrayList<>(cuisines);
        this.dishes = new ArrayList<>();
        averageRating = 0.0;
    }


    public Long id() {
        return restaurantId;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsKosher() {
        return isKosher;
    }

    public void setKosher(boolean kosher) {
        isKosher = kosher;
    }

    public List<String> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<String> cuisines) {
        this.cuisines = cuisines;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public void addDish(Dish dish) {
        this.dishes.add(dish);
    }


    public Restaurant applyPatchToRestaurant(JsonPatch patch, ObjectMapper objectMapper) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(this, JsonNode.class));
        return objectMapper.treeToValue(patched, Restaurant.class);
    }


    @Override
    public String toString(){
        return "Restaurant ID: " + restaurantId + "\n" +
                "Restaurant Name: " + name + "\n" +
                "Is Kosher: " + isKosher + "\n" +
                "Cuisines: " + String.join(", ", cuisines) + "\n" +
                "Dishes: " + dishes;
    }
}
