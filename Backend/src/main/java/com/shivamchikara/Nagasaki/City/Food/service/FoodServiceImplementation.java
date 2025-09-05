package com.shivamchikara.Nagasaki.City.Food.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shivamchikara.Nagasaki.City.Food.Exception.FoodException;
import com.shivamchikara.Nagasaki.City.Food.Exception.RestaurantException;
import com.shivamchikara.Nagasaki.City.Food.model.Food;
import com.shivamchikara.Nagasaki.City.Food.model.Restaurant;
import com.shivamchikara.Nagasaki.City.Food.repository.foodRepository;
import com.shivamchikara.Nagasaki.City.Food.request.CreateFoodRequest;


@Service
public class FoodServiceImplementation implements FoodService {
	@Autowired
	private foodRepository foodRepository;


	@Override
	public Food createFood(CreateFoodRequest  req,
						   Restaurant restaurant)
			throws FoodException,
	RestaurantException {

			Food food=new Food();
			food.setCreationDate(new Date());
			food.setDescription(req.getDescription());
			food.setImages(req.getImages());
			food.setName(req.getName());
			food.setPrice((double) req.getPrice());
		food.setRestaurant(restaurant);
			food = foodRepository.save(food);

			restaurant.getFoods().add(food);
			return food;
		
	}

	@Override
	public void deleteFood(Long foodId) throws FoodException {
		Food food=findFoodById(foodId);
		food.setRestaurant(null);
		foodRepository.delete(food);

	}


	@Override
	public List<Food> getRestaurantsFood(
			Long restaurantId) throws FoodException {
		List<Food> foods = foodRepository.findByRestaurantId(restaurantId);
		return foods;
		
	}

	@Override
	public List<Food> searchFood(String keyword) {
		List<Food> items=new ArrayList<>();
		
		if(keyword != null && !keyword.trim().isEmpty()) {
			System.out.println("keyword -- "+keyword);
			items=foodRepository.searchByName(keyword);
		}
		
		return items;
	}

	@Override
	public Food updateAvailibilityStatus(Long id) throws FoodException {
		Food food = findFoodById(id);
		
		food.setAvailable(!food.isAvailable());
		foodRepository.save(food);
		return food;
	}

	@Override
	public Food findFoodById(Long foodId) throws FoodException {
		Optional<Food> food = foodRepository.findById(foodId);
		if (food.isPresent()) {
			return food.get();
		}
		throw new FoodException("food with id" + foodId + "not found");
	}

}
