package com.shivamchikara.Nagasaki.City.Food.repository;

import java.util.List;
import java.util.Optional;

import com.shivamchikara.Nagasaki.City.Food.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant r WHERE " +
            "LOWER(r.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(r.cuisineType) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Restaurant> findBySearchQuery(@Param("query") String query);

    List<Restaurant> findByOwnerId(Long userId);
}
