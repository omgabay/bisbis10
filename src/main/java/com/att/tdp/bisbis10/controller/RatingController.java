package com.att.tdp.bisbis10.controller;
import com.att.tdp.bisbis10.logic.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.att.tdp.bisbis10.entities.UserRating;
import org.springframework.http.ResponseEntity;


@RestController
public class RatingController {
    @Autowired
    RatingService ratingService;

    @PostMapping("/ratings")
    ResponseEntity<Void> addRatingToRestaurant(@RequestBody UserRating rating){
        try{
            ratingService.addRatingRecord(rating);
            return ResponseEntity.ok().build();
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }


    }
}




