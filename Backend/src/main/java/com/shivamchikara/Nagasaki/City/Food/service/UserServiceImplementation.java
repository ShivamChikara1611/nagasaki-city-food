package com.shivamchikara.Nagasaki.City.Food.service;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shivamchikara.Nagasaki.City.Food.Exception.UserException;
import com.shivamchikara.Nagasaki.City.Food.config.JwtProvider;
import com.shivamchikara.Nagasaki.City.Food.model.User;
import com.shivamchikara.Nagasaki.City.Food.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {


	private UserRepository userRepository;
	private JwtProvider jwtProvider;

	
	public UserServiceImplementation(
			UserRepository userRepository,
			JwtProvider jwtProvider
    ) {
		
		this.userRepository=userRepository;
		this.jwtProvider=jwtProvider;
		
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email=jwtProvider.getEmailFromJwtToken(jwt);
		
		
		User user = userRepository.findByEmail(email);
		
		if(user==null) {
			throw new UserException("user not exist with email "+email);
		}
		return user;
	}

	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}


	@Override
	public User findUserByEmail(String username) throws UserException {
		
		User user=userRepository.findByEmail(username);
		
		if(user!=null) {
			
			return user;
		}
		
		throw new UserException("user not exist with username "+username);
	}



}
