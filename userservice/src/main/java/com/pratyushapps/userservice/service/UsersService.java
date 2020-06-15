package com.pratyushapps.userservice.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.pratyushapps.userservice.model.OrderModel;
import com.pratyushapps.userservice.shared.UserDto;

public interface UsersService extends UserDetailsService{
	UserDto createUser(UserDto userDetails);
	UserDto getUserDetailsByEmail(String email);
	UserDto getUserByUserId(String userId);
	String postOrder(OrderModel orderModel);
}
