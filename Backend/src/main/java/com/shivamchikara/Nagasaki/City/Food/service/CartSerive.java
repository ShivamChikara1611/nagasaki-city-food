package com.shivamchikara.Nagasaki.City.Food.service;

import com.shivamchikara.Nagasaki.City.Food.Exception.CartException;
import com.shivamchikara.Nagasaki.City.Food.Exception.CartItemException;
import com.shivamchikara.Nagasaki.City.Food.Exception.FoodException;
import com.shivamchikara.Nagasaki.City.Food.Exception.UserException;
import com.shivamchikara.Nagasaki.City.Food.model.Cart;
import com.shivamchikara.Nagasaki.City.Food.model.CartItem;
import com.shivamchikara.Nagasaki.City.Food.model.Food;
import com.shivamchikara.Nagasaki.City.Food.model.User;
import com.shivamchikara.Nagasaki.City.Food.request.AddCartItemRequest;
import com.shivamchikara.Nagasaki.City.Food.request.UpdateCartItemRequest;

public interface CartSerive {

	public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws UserException, FoodException, CartException, CartItemException;

	public CartItem updateCartItemQuantity(Long cartItemId,int quantity) throws CartItemException;

	public Cart removeItemFromCart(Long cartItemId, String jwt) throws UserException, CartException, CartItemException;

	public Double calculateCartTotals(Cart cart) throws UserException;
	
	public Cart findCartById(Long id) throws CartException;
	
	public Cart findCartByUserId(Long userId) throws CartException, UserException;
	
	public Cart clearCart(Long userId) throws CartException, UserException;
	

	

}
