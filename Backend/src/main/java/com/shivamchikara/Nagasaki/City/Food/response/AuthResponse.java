package com.shivamchikara.Nagasaki.City.Food.response;



import com.shivamchikara.Nagasaki.City.Food.domain.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {
	
	private String message;
	private String jwt;
	private USER_ROLE role;
	


}
