package com.smartkitchen.kitchenmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartkitchen.kitchenmanagement.model.LoginRequest;
import com.smartkitchen.kitchenmanagement.model.LoginResponse;
import com.smartkitchen.kitchenmanagement.model.User;
import com.smartkitchen.kitchenmanagement.repository.UserRepository;
import com.smartkitchen.kitchenmanagement.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	  @Autowired
	    private UserRepository userRepository;
	    private UserService userservice.;

	    @PostMapping("/register")
	    public User register(@RequestBody User user){
	        return userRepository.save(user);
	    }
	    
	    @PostMapping("/login")
	    public LoginResponse login(@RequestBody LoginRequest request) {
	    	
	    	LoginResponse response = userservice.loginUser(request);
	    	return response;
	    	
	    }
	
}
