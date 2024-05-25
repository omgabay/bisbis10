package com.att.tdp.bisbis10.repos;


import com.att.tdp.bisbis10.entities.Dish;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends ListCrudRepository<Dish, Long> {
}
