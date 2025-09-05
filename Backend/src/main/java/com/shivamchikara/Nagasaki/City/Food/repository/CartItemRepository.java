package com.shivamchikara.Nagasaki.City.Food.repository;

import com.shivamchikara.Nagasaki.City.Food.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
