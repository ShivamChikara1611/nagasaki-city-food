package com.shivamchikara.Nagasaki.City.Food.controller;

import com.shivamchikara.Nagasaki.City.Food.Exception.FoodException;
import com.shivamchikara.Nagasaki.City.Food.Exception.RestaurantException;
import com.shivamchikara.Nagasaki.City.Food.Exception.UserException;
import com.shivamchikara.Nagasaki.City.Food.model.Food;
import com.shivamchikara.Nagasaki.City.Food.model.Restaurant;
import com.shivamchikara.Nagasaki.City.Food.model.User;
import com.shivamchikara.Nagasaki.City.Food.request.CreateFoodRequest;
import com.shivamchikara.Nagasaki.City.Food.service.FoodService;
import com.shivamchikara.Nagasaki.City.Food.service.RestaurantService;
import com.shivamchikara.Nagasaki.City.Food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/food")
public class AdminMenuItemController {
	
	@Autowired
	private FoodService menuItemService;
	@Autowired
	private UserService userService;
	@Autowired
	private RestaurantService restaurantService;

	@PostMapping()
	public ResponseEntity<Food> createItem(
			@RequestBody CreateFoodRequest item,
			@RequestHeader("Authorization") String jwt)
			throws FoodException, UserException, RestaurantException {
		System.out.println("req-controller ----"+item);
		User user = userService.findUserProfileByJwt(jwt);
		Restaurant restaurant=restaurantService.findRestaurantById(item.getRestaurantId());
			Food menuItem = menuItemService.createFood(item,restaurant);
			return ResponseEntity.ok(menuItem);

	}


	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteItem(@PathVariable Long id, @RequestHeader("Authorization") String jwt)
			throws UserException, FoodException {
		User user = userService.findUserProfileByJwt(jwt);
		
			menuItemService.deleteFood(id);
			return ResponseEntity.ok("Menu item deleted");
		
	
	}

	

	@GetMapping("/search")
	public ResponseEntity<List<Food>> getMenuItemByName(@RequestParam String name)  {
		List<Food> menuItem = menuItemService.searchFood(name);
		return ResponseEntity.ok(menuItem);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Food> updateAvilibilityStatus(
			@PathVariable Long id) throws FoodException {
		Food menuItems= menuItemService.updateAvailibilityStatus(id);
		return ResponseEntity.ok(menuItems);
	}
	
	

}
