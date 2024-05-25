package com.att.tdp.bisbis10.logic;


import com.att.tdp.bisbis10.entities.Dish;
import com.att.tdp.bisbis10.entities.Restaurant;
import com.att.tdp.bisbis10.repos.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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


}
