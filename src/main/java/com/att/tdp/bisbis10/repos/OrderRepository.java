package com.att.tdp.bisbis10.repos;

import com.att.tdp.bisbis10.entities.Order;
import com.att.tdp.bisbis10.entities.Restaurant;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OrderRepository extends ListCrudRepository<Order, Long> { }



