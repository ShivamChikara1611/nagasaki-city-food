package com.shivamchikara.Nagasaki.City.Food.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shivamchikara.Nagasaki.City.Food.model.Food;
import com.shivamchikara.Nagasaki.City.Food.model.OrderItem;
import com.shivamchikara.Nagasaki.City.Food.repository.OrderItemRepository;

@Service
public class OrderItemServiceImplementation implements OrderItemService {
	@Autowired
	 private OrderItemRepository orderItemRepository;

	    @Override
	    public OrderItem createOrderItem(OrderItem orderItem) {

	    	OrderItem newOrderItem=new OrderItem();
	    	newOrderItem.setQuantity(orderItem.getQuantity());
	        return orderItemRepository.save(newOrderItem);
	    }

}
