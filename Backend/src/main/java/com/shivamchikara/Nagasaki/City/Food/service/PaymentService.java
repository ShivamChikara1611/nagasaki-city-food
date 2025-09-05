package com.shivamchikara.Nagasaki.City.Food.service;

import com.stripe.exception.StripeException;
import com.shivamchikara.Nagasaki.City.Food.model.Order;
import com.shivamchikara.Nagasaki.City.Food.model.PaymentResponse;

public interface PaymentService {
	
	public PaymentResponse generatePaymentLink(Order order) throws StripeException;

}
