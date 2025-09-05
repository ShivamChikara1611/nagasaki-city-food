package com.shivamchikara.Nagasaki.City.Food.repository;

import java.util.Optional;

import com.shivamchikara.Nagasaki.City.Food.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CartRepository extends JpaRepository<Cart, Long> {

	 Optional<Cart> findByCustomer_Id(Long userId);
}
