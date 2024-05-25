package com.att.tdp.bisbis10.controller;
import com.att.tdp.bisbis10.logic.RestaurantService;
import com.att.tdp.bisbis10.repos.RatingRepository;
import org.springframework.web.bind.annotation.*;

import com.att.tdp.bisbis10.entities.UserRating;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
public class RatingController {

    RatingRepository ratingRepo;
    RestaurantService restaurantService;

    public RatingController(RatingRepository ratingRepo, RestaurantService restaurantService) {
        this.ratingRepo = ratingRepo;
        this.restaurantService = restaurantService;
    }

    @PostMapping("/ratings")
    ResponseEntity<Void> addRatingToRestaurant(@RequestBody UserRating rating){
        try{
            restaurantService.addUserRating(rating);
            return ResponseEntity.ok().build();
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }


    // FOR DEBUGGING
    @GetMapping("/ratings")
    ResponseEntity<List<UserRating>> getAllRatings(){
        return ResponseEntity.ok(ratingRepo.findAll());
    }

}