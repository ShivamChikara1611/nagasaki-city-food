package com.shivamchikara.Nagasaki.City.Food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shivamchikara.Nagasaki.City.Food.Exception.RestaurantException;
import com.shivamchikara.Nagasaki.City.Food.Exception.UserException;
import com.shivamchikara.Nagasaki.City.Food.dto.RestaurantDto;
import com.shivamchikara.Nagasaki.City.Food.model.Restaurant;
import com.shivamchikara.Nagasaki.City.Food.model.User;
import com.shivamchikara.Nagasaki.City.Food.service.RestaurantService;
import com.shivamchikara.Nagasaki.City.Food.service.UserService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private UserService userService;


    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> findRestaurantByName(@RequestParam String keyword) {
        List<Restaurant> restaurant = restaurantService.searchRestaurant(keyword);
        return ResponseEntity.ok(restaurant);
    }


	@GetMapping()
	public ResponseEntity<List<Restaurant>> getAllRestaurants() {

		List<Restaurant> restaurants = restaurantService.getAllRestaurant();

		return ResponseEntity.ok(restaurants);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> findRestaurantById(
			@PathVariable Long id) throws RestaurantException {
			Restaurant restaurant = restaurantService.findRestaurantById(id);
			return ResponseEntity.ok(restaurant);

	}
	
	@PutMapping("/{id}/add-favorites")
	public ResponseEntity<RestaurantDto> addToFavorite(
			@RequestHeader("Authorization") String jwt,
			@PathVariable Long id) throws RestaurantException, UserException {
		
			User user = userService.findUserProfileByJwt(jwt);
			RestaurantDto restaurant = restaurantService.addToFavorites(id, user);
			return ResponseEntity.ok(restaurant);

	}
}
