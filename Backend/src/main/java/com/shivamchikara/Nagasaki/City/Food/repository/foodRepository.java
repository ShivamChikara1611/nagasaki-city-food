package com.shivamchikara.Nagasaki.City.Food.repository;

import java.util.List;
import java.util.Optional;

import com.shivamchikara.Nagasaki.City.Food.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface foodRepository extends JpaRepository<Food, Long> {

	
	List<Food> findByRestaurantId(Long restaurantId);

    @Query("SELECT f FROM Food f WHERE LOWER(f.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Food> searchByName(@Param("keyword") String keyword);


	

}
