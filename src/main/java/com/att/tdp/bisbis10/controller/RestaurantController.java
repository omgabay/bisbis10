package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.entities.Restaurant;
import com.att.tdp.bisbis10.logic.RestaurantService;
import com.att.tdp.bisbis10.repos.RestaurantRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class RestaurantController {

    private final RestaurantRepository repository;

    private final RestaurantService service;

    private final ObjectMapper objectMapper;

    public RestaurantController(RestaurantRepository repository, RestaurantService service, ObjectMapper objectMapper) {
        this.repository = repository;
        this.service = service;
        this.objectMapper = objectMapper;
    }


    @GetMapping("/restaurants/{id}")
    ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        Optional<Restaurant> restaurant = repository.findById(id);
        return restaurant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/restaurants")
    ResponseEntity<List<Restaurant>> getAllRestaurants(@RequestParam(defaultValue="") String cuisine) {
        if (cuisine == null || cuisine.isEmpty()) {
            return ResponseEntity.ok(repository.findAll());
        }
        return ResponseEntity.ok(service.getRestaurantsByCuisines(cuisine));
    }


    @PostMapping("/restaurants")
    ResponseEntity<Void> createRestaurant(@RequestBody Restaurant restaurant, UriComponentsBuilder ucb) {
        System.out.println(restaurant);
        Restaurant saved = repository.save(restaurant);
        URI uri = ucb
                .path("restaurants/{id}")
                .buildAndExpand(saved.id())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(path="/restaurants/{id}", consumes = "application/json-patch+json")
    ResponseEntity<Void> updateRestaurant(@PathVariable Long id, @RequestBody JsonPatch patch) {
        try{
            Restaurant restaurant = repository.findById(id).orElseThrow();
            Restaurant patchedRestaurant = restaurant.applyPatchToRestaurant(patch, objectMapper);
            repository.save(patchedRestaurant);
            return ResponseEntity.ok().build();
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("/restaurants/{id}")
    ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            // Return 404 Not Found if the resource doesn't exist
            return ResponseEntity.notFound().build();
        }

        // Delete the resource
        repository.deleteById(id);
        return ResponseEntity.noContent().build();

    }
}
