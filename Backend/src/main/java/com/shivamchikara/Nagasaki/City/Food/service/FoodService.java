package com.shivamchikara.Nagasaki.City.Food.service;

import java.util.List;

import com.shivamchikara.Nagasaki.City.Food.Exception.FoodException;
import com.shivamchikara.Nagasaki.City.Food.Exception.RestaurantException;
import com.shivamchikara.Nagasaki.City.Food.model.Food;
import com.shivamchikara.Nagasaki.City.Food.model.Restaurant;
import com.shivamchikara.Nagasaki.City.Food.request.CreateFoodRequest;

public interface FoodService {

	public Food createFood(CreateFoodRequest req,
						   Restaurant restaurant) throws FoodException, RestaurantException;

	void deleteFood(Long foodId) throws FoodException;
	
	public List<Food> getRestaurantsFood(Long restaurantId) throws FoodException;
	
	public List<Food> searchFood(String keyword);
	
	public Food findFoodById(Long foodId) throws FoodException;

	public Food updateAvailibilityStatus(Long foodId) throws FoodException;
}
