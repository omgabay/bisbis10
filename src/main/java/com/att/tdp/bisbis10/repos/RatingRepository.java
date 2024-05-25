package com.att.tdp.bisbis10.repos;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import com.att.tdp.bisbis10.entities.UserRating;
import java.util.List; 

public interface RatingRepository extends ListCrudRepository<UserRating, Long>{

    List<UserRating> findByRestaurantId(Long restaurantId);

    boolean existsByRestaurantId(Long restaurantId);

    @Query("SELECT AVG(rr.rating) FROM UserRating rr WHERE rr.restaurantId = ?1")
    Double getAverageRestaurantRating(Long restaurantId);

}


  

