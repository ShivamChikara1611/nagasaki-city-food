package com.shivamchikara.Nagasaki.City.Food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.shivamchikara.Nagasaki.City.Food.model.PaymentResponse;
import com.shivamchikara.Nagasaki.City.Food.service.PaymentService;

@RestController
@RequestMapping("/api/customer")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
}
