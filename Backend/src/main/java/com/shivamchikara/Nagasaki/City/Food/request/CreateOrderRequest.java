package com.shivamchikara.Nagasaki.City.Food.request;


import com.shivamchikara.Nagasaki.City.Food.model.Address;
import lombok.Data;

@Data
public class CreateOrderRequest {

	private Long restaurantId;
	private Address deliveryAddress;
}
