package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.entities.Dish;
import com.att.tdp.bisbis10.entities.Restaurant;
import com.att.tdp.bisbis10.logic.RestaurantService;
import com.att.tdp.bisbis10.repos.DishRepository;
import com.att.tdp.bisbis10.repos.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@RestController
public class DishController {

    private final RestaurantRepository restaurantRepo;

    private final DishRepository dishRepo;

    private final RestaurantService restaurantService;

    private static final Logger logger = LoggerFactory.getLogger(DishController.class);


    public DishController(RestaurantRepository restaurantRepo, DishRepository dishRepo, RestaurantService restaurantService) {
        this.restaurantRepo = restaurantRepo;
        this.restaurantService = restaurantService;
        this.dishRepo = dishRepo;
    }


    @GetMapping("/restaurants/{restaurantId}/dishes")
    public ResponseEntity<List<Dish>> getDishesByRestaurantId(@PathVariable Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepo.findById(restaurantId);
        return restaurant.map(value -> ResponseEntity.ok(value.getDishes())).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/restaurants/{restaurantId}/dishes")
    public ResponseEntity<?> addDishToRestaurant(@PathVariable Long restaurantId, @RequestBody Dish dish, UriComponentsBuilder uriBuilder) {
        try{
            restaurantService.addDishToRestaurant(restaurantId, dish);
            URI uri = uriBuilder
                    .path("restaurants/{id}/dishes/{dishId}")
                    .buildAndExpand(restaurantId, dish.id())
                    .toUri();
            return ResponseEntity.created(uri).build();
        }catch (Exception e){
            logger.error("CREATE Dish error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/restaurants/{restaurantId}/dishes/{dishId}")
    public ResponseEntity<Void> updateDish(@PathVariable Long restaurantId, @PathVariable Long dishId, @RequestBody Dish newDish) {
        try{
            Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow();
            dishRepo.findById(dishId).orElseThrow();

            boolean removed = restaurant.getDishes().removeIf((d) -> Objects.equals(d.getDishId(), dishId));
            if (!removed){
                throw new NoSuchElementException();
            }else{
                newDish.setDishId(dishId);
                restaurant.getDishes().add(newDish);
                restaurantRepo.save(restaurant);
                return ResponseEntity.ok().build();
            }

        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/restaurants/{restaurantId}/dishes/{dishId}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long restaurantId, @PathVariable Long dishId) {
        try {
            restaurantService.removeDishFromRestaurant(restaurantId, dishId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("DELETE Dish Error: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // FOR DEBUG.
    @GetMapping("/dishes")
    public ResponseEntity<List<Dish>> getAllDishes() {
        return ResponseEntity.ok(this.dishRepo.findAll());
    }

}