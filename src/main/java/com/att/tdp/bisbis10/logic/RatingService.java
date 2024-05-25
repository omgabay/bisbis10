package com.att.tdp.bisbis10.logic;

import com.att.tdp.bisbis10.entities.UserRating;
import com.att.tdp.bisbis10.entities.Restaurant;
import com.att.tdp.bisbis10.repos.RatingRepository;
import com.att.tdp.bisbis10.repos.RestaurantRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final RestaurantRepository restaurantRepository;
    private final RatingRepository ratingRepository;

    public RatingService(RestaurantRepository restaurantRepository, RatingRepository ratingRepository) {
        this.restaurantRepository = restaurantRepository;
        this.ratingRepository = ratingRepository;
    }

    public void addRatingRecord(UserRating rating) {
        Long restaurantId = rating.getRestaurantId();
        Restaurant restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow();

        this.ratingRepository.save(rating);
        Double newAverage = this.ratingRepository.getAverageRestaurantRating(restaurantId);
        restaurant.setAverageRating(newAverage);
        this.restaurantRepository.save(restaurant);
    }
}
