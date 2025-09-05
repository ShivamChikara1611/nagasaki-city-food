package com.shivamchikara.Nagasaki.City.Food.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stripe.exception.StripeException;
import com.shivamchikara.Nagasaki.City.Food.Exception.CartException;
import com.shivamchikara.Nagasaki.City.Food.Exception.OrderException;
import com.shivamchikara.Nagasaki.City.Food.Exception.RestaurantException;
import com.shivamchikara.Nagasaki.City.Food.Exception.UserException;
import com.shivamchikara.Nagasaki.City.Food.model.Address;
import com.shivamchikara.Nagasaki.City.Food.model.Cart;
import com.shivamchikara.Nagasaki.City.Food.model.CartItem;
import com.shivamchikara.Nagasaki.City.Food.model.Order;
import com.shivamchikara.Nagasaki.City.Food.model.OrderItem;
import com.shivamchikara.Nagasaki.City.Food.model.PaymentResponse;
import com.shivamchikara.Nagasaki.City.Food.model.Restaurant;
import com.shivamchikara.Nagasaki.City.Food.model.User;
import com.shivamchikara.Nagasaki.City.Food.repository.AddressRepository;
import com.shivamchikara.Nagasaki.City.Food.repository.OrderItemRepository;
import com.shivamchikara.Nagasaki.City.Food.repository.OrderRepository;
import com.shivamchikara.Nagasaki.City.Food.repository.RestaurantRepository;
import com.shivamchikara.Nagasaki.City.Food.repository.UserRepository;
import com.shivamchikara.Nagasaki.City.Food.request.CreateOrderRequest;
@Service
public class OrderServiceImplementation implements OrderService {
	
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private CartSerive cartService;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PaymentService paymentSerive;

	

	

	@Override
	public PaymentResponse createOrder(CreateOrderRequest order,User user) throws UserException, RestaurantException, CartException, StripeException {
		
	    Address shippAddress = order.getDeliveryAddress();

	    
	    Address savedAddress = addressRepository.save(shippAddress);
	    
	    if(!user.getAddresses().contains(savedAddress)) {
	    	user.getAddresses().add(savedAddress);
	    }
	    
		
		System.out.println("user addresses --------------  "+user.getAddresses());
		   
		 userRepository.save(user);
	    
	    Optional<Restaurant> restaurant = restaurantRepository.findById(order.getRestaurantId());
	    if(restaurant.isEmpty()) {
	    	throw new RestaurantException("Restaurant not found with id "+order.getRestaurantId());
	    }
	    
	    Order createdOrder = new Order();
	    
	    createdOrder.setCustomer(user);
	    createdOrder.setDeliveryAddress(savedAddress);
	    createdOrder.setCreatedAt(new Date());
	    createdOrder.setOrderStatus("PENDING");
	    createdOrder.setRestaurant(restaurant.get());

        Cart cart = cartService.findCartByUserId(user.getId());
        
	    List<OrderItem> orderItems = new ArrayList<>();
	    
	    for (CartItem cartItem : cart.getItems()) {
	        OrderItem orderItem = new OrderItem();
	       orderItem.setFood(cartItem.getFood());
	       orderItem.setQuantity(cartItem.getQuantity());
	        orderItem.setTotalPrice(cartItem.getFood().getPrice()* cartItem.getQuantity());

	        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
	        orderItems.add(savedOrderItem);
	    }
	     Double totalPrice = cartService.calculateCartTotals(cart);

	    createdOrder.setTotalAmount(totalPrice);
	    createdOrder.setRestaurant(restaurant.get());
	    createdOrder.setItems(orderItems);
	    Order savedOrder = orderRepository.save(createdOrder);

	   restaurant.get().getOrders().add(savedOrder);
	   
	   restaurantRepository.save(restaurant.get());
	   

	   
	   PaymentResponse res=paymentSerive.generatePaymentLink(savedOrder);
	   return res;

	}

	@Override
	public void cancelOrder(Long orderId) throws OrderException {
           Order order =findOrderById(orderId);
           if(order==null) {
        	   throw new OrderException("Order not found with the id "+orderId);
           }
		
		    orderRepository.deleteById(orderId);
		
	}
	
	public Order findOrderById(Long orderId) throws OrderException {
		Optional<Order> order = orderRepository.findById(orderId);
		if(order.isPresent()) return order.get();
		
		throw new OrderException("Order not found with the id "+orderId);
	}

	@Override
	public List<Order> getUserOrders(Long userId) throws OrderException {
		List<Order> orders=orderRepository.findAllUserOrders(userId);
		return orders;
	} 

	@Override
	public List<Order> getOrdersOfRestaurant(Long restaurantId,String orderStatus) throws OrderException, RestaurantException {
		
			List<Order> orders = orderRepository.findOrdersByRestaurantId(restaurantId);
			
			if(orderStatus!=null) {
				orders = orders.stream()
						.filter(order->order.getOrderStatus().equals(orderStatus))
						.collect(Collectors.toList());
			}
			
			return orders;
	}
	

	@Override
	public Order updateOrder(Long orderId, String orderStatus) throws OrderException {
		Order order=findOrderById(orderId);
		
		System.out.println("--------- "+orderStatus);
		
		if(orderStatus.equals("DELIVERED")
				|| orderStatus.equals("COMPLETED") || orderStatus.equals("PENDING")) {
			order.setOrderStatus(orderStatus);
			return orderRepository.save(order);
		}
		else throw new OrderException("Please Select A Valid Order Status");
		
		
	}
	
	

}
