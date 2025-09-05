package com.shivamchikara.Nagasaki.City.Food.service;

import com.shivamchikara.Nagasaki.City.Food.model.Cart;
import com.shivamchikara.Nagasaki.City.Food.model.CartItem;
import com.shivamchikara.Nagasaki.City.Food.model.Order;
import com.shivamchikara.Nagasaki.City.Food.model.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
public class PaymentServiceImplementation implements PaymentService{
	
	
	@Value("${stripe.api.key}")
	 private String stripeSecretKey;

	@Override
	public PaymentResponse generatePaymentLink(Order order) throws StripeException {

	  Stripe.apiKey = stripeSecretKey;

	        SessionCreateParams params = SessionCreateParams.builder()
	                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
	                .setMode(SessionCreateParams.Mode.PAYMENT)
	                .setSuccessUrl("http://localhost:8080/payment/success")
	                .setCancelUrl("http://localhost:8080/cancel")
	                .addLineItem(SessionCreateParams.LineItem.builder()
	                        .setQuantity(1L)
	                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
	                                .setCurrency("jpy")
                                    .setUnitAmount(Math.round(order.getTotalAmount()))
	                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
	                                        .setName("Total bill")
	                                        .build())
	                                .build())
	                        .build())
	                .build();
	        
	        Session session = Session.create(params);
	        
	        System.out.println("session _____ " + session);
	        
	        PaymentResponse res = new PaymentResponse();
	        res.setPayment_url(session.getUrl());
	        
	        return res;
	    
	}

}
