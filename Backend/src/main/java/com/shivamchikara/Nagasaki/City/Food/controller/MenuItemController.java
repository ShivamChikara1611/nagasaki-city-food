package com.shivamchikara.Nagasaki.City.Food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shivamchikara.Nagasaki.City.Food.Exception.FoodException;
import com.shivamchikara.Nagasaki.City.Food.Exception.RestaurantException;
import com.shivamchikara.Nagasaki.City.Food.Exception.UserException;
import com.shivamchikara.Nagasaki.City.Food.model.Food;
import com.shivamchikara.Nagasaki.City.Food.model.User;
import com.shivamchikara.Nagasaki.City.Food.request.CreateFoodRequest;
import com.shivamchikara.Nagasaki.City.Food.service.FoodService;
import com.shivamchikara.Nagasaki.City.Food.service.UserService;

@RestController
@RequestMapping("/api/food")
public class MenuItemController {
	@Autowired
	private FoodService menuItemService;
	
	@Autowired
	private UserService userService;


	@GetMapping("/search")
	public ResponseEntity<List<Food>> searchFood(
			@RequestParam String name)  {
		List<Food> menuItem = menuItemService.searchFood(name);
		return ResponseEntity.ok(menuItem);
	}

	@GetMapping("/restaurant/{restaurantId}")
	public ResponseEntity<List<Food>> getMenuItemByRestaurantId(
			@PathVariable Long restaurantId) throws FoodException {
		List<Food> menuItems= menuItemService.getRestaurantsFood(
				restaurantId);
		return ResponseEntity.ok(menuItems);
	}
}
