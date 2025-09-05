package com.shivamchikara.Nagasaki.City.Food.repository;

import com.shivamchikara.Nagasaki.City.Food.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
