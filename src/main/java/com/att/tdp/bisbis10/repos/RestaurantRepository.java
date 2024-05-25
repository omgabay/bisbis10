package com.att.tdp.bisbis10.repos;

import com.att.tdp.bisbis10.entities.Restaurant;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RestaurantRepository extends ListCrudRepository<Restaurant, Long> {
    Optional<Restaurant> findById(Long id);
}



