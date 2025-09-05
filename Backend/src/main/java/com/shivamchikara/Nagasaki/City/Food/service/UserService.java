package com.shivamchikara.Nagasaki.City.Food.service;

import java.util.List;

import com.shivamchikara.Nagasaki.City.Food.Exception.UserException;
import com.shivamchikara.Nagasaki.City.Food.model.User;

public interface UserService {

	public User findUserProfileByJwt(String jwt) throws UserException;
	public User findUserByEmail(String email) throws UserException;
	public List<User> findAllUsers();

}
