package com.att.tdp.bisbis10.logic;

import com.att.tdp.bisbis10.entities.Dish;
import com.att.tdp.bisbis10.entities.Restaurant;
import com.att.tdp.bisbis10.entities.UserRating;
import com.att.tdp.bisbis10.repos.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getRestaurantsByCuisines(String cuisine){
        List<Restaurant> result = new ArrayList<>();
        cuisine = cuisine.toLowerCase();

        for (Restaurant r : restaurantRepository.findAll()) {
            List<String> cuisinesLowercased = r.getCuisines().stream().map(String::toLowerCase).toList();
            if (cuisinesLowercased.contains(cuisine)) {
                result.add(r);
            }
        }

        return result;
    }

    public Restaurant  getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }


    public void addUserRating(UserRating rating) throws NoSuchElementException {
        Restaurant restaurant = restaurantRepository.findById(rating.getRestaurantId()).orElseThrow();
        List<UserRating> ratings = restaurant.getUserRatings();
        ratings.add(rating);
        rating.setRestaurant(restaurant);
        double newAverageRating = ratings.stream().mapToDouble(UserRating::getRating).average().orElse(0);
        restaurant.setAverageRating(newAverageRating);
        restaurantRepository.save(restaurant);
    }


    public void addDishToRestaurant(Long restaurantId, Dish dish) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()-> new RuntimeException("Restaurant not found"));
        dish.setRestaurant(restaurant);
        restaurant.getDishes().add(dish);
        restaurantRepository.save(restaurant);
    }

    public void removeDishFromRestaurant(Long restaurantId, Long dishId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        restaurant.getDishes().removeIf(dish -> dish.getDishId().equals(dishId));
        restaurantRepository.save(restaurant);
    }
}
