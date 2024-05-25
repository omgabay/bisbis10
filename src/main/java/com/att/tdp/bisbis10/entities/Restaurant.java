package com.att.tdp.bisbis10.entities;

import com.att.tdp.bisbis10.auxillary.StringListConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="restaurant_id")
    Long restaurantId;

    @Column(name="name")
    private String name;

    @Setter
    @Column(name="is_kosher")
    private boolean isKosher;

    @Convert(converter = StringListConverter.class)
    @Column(name="cuisines")
    private List<String> cuisines  = new ArrayList<>();

    @Column(name="average_rating")
    private Double averageRating = 0.0;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="restaurant_id")
   @JsonIgnore
    private List<Dish> dishes =  new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="restaurant_id")
    @JsonIgnore
    private List<UserRating> userRatings = new ArrayList<>();

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


    public Restaurant applyPatchToRestaurant(JsonPatch patch, ObjectMapper objectMapper) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(this, JsonNode.class));
        return objectMapper.treeToValue(patched, Restaurant.class);
    }


    public boolean getIsKosher() {
        return isKosher;
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
