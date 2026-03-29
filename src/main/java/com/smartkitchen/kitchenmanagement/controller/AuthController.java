package com.smartkitchen.kitchenmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartkitchen.kitchenmanagement.model.User;
import com.smartkitchen.kitchenmanagement.repository.UserRepository;
import com.smartkitchen.kitchenmanagement.requestmodel.LoginRequest;
import com.smartkitchen.kitchenmanagement.responsemodel.LoginResponse;
import com.smartkitchen.kitchenmanagement.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public User register(@RequestBody User user) {
		return userService.registerUser(user);
	}

	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest request) {
		return userService.loginUser(request);
	}
	
}
