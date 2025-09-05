package com.shivamchikara.Nagasaki.City.Food.service;

import java.util.List;

import com.stripe.exception.StripeException;
import com.shivamchikara.Nagasaki.City.Food.Exception.CartException;
import com.shivamchikara.Nagasaki.City.Food.Exception.OrderException;
import com.shivamchikara.Nagasaki.City.Food.Exception.RestaurantException;
import com.shivamchikara.Nagasaki.City.Food.Exception.UserException;
import com.shivamchikara.Nagasaki.City.Food.model.Order;
import com.shivamchikara.Nagasaki.City.Food.model.PaymentResponse;
import com.shivamchikara.Nagasaki.City.Food.model.User;
import com.shivamchikara.Nagasaki.City.Food.request.CreateOrderRequest;

public interface OrderService {
	
	 public PaymentResponse createOrder(CreateOrderRequest order, User user) throws UserException, RestaurantException, CartException, StripeException;
	 
	 public Order updateOrder(Long orderId, String orderStatus) throws OrderException;
	 
	 public void cancelOrder(Long orderId) throws OrderException;
	 
	 public List<Order> getUserOrders(Long userId) throws OrderException;
	 
	 public List<Order> getOrdersOfRestaurant(Long restaurantId,String orderStatus) throws OrderException, RestaurantException;

}
