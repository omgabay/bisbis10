package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.entities.Dish;
import com.att.tdp.bisbis10.entities.Restaurant;
import com.att.tdp.bisbis10.repos.DishRepository;
import com.att.tdp.bisbis10.repos.RestaurantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class DishController {

    private final RestaurantRepository restaurantRepo;

    private final DishRepository dishRepo;

    public DishController(RestaurantRepository restaurantRepo, DishRepository dishRepo) {
        this.restaurantRepo = restaurantRepo;
        this.dishRepo = dishRepo;
    }


    @GetMapping("/restaurants/{restaurantId}/dishes")
    public ResponseEntity<List<Dish>> getDishesByRestaurantId(@PathVariable Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepo.findById(restaurantId);
        return restaurant.map(value -> ResponseEntity.ok(value.getDishes())).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/restaurants/{restaurantId}/dishes")
    public ResponseEntity<Dish> addDishToRestaurant(@PathVariable Long restaurantId, @RequestBody Dish dish, UriComponentsBuilder uriBuilder) {
        try{
            Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow(()-> new RuntimeException("Restaurant not found"));
            dish.setRestaurant(restaurant);
            Dish savedDish = dishRepo.save(dish);
            restaurant.addDish(savedDish);
            Restaurant savedRestaurant = restaurantRepo.save(restaurant);
            URI uri = uriBuilder
                    .path("restaurants/{id}/dishes/{dishId}")
                    .buildAndExpand(savedRestaurant.id(), savedDish.id())
                    .toUri();
            return ResponseEntity.created(uri).build();
        }catch (Exception e){
            System.out.println("Omer:" + e);
            return ResponseEntity.badRequest().body(dish);
        }
    }


    @PutMapping("/restaurants/{id}/dishes/{dishId}")
    public ResponseEntity<Void> updateDish(@PathVariable Long id, @PathVariable Long dishId, @RequestBody Dish dish, UriComponentsBuilder uriBuilder) {
        return ResponseEntity.ok().build();
    }



    // FOR TESTING.
    @GetMapping("/dishes")
    public ResponseEntity<List<Dish>> getAllDishes() {
        return ResponseEntity.ok(this.dishRepo.findAll());
    }



}