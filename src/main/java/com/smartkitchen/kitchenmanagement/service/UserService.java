package com.smartkitchen.kitchenmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartkitchen.kitchenmanagement.config.JwtUtil;
import com.smartkitchen.kitchenmanagement.model.User;
import com.smartkitchen.kitchenmanagement.repository.UserRepository;
import com.smartkitchen.kitchenmanagement.requestmodel.LoginRequest;
import com.smartkitchen.kitchenmanagement.responsemodel.LoginResponse;

import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	public User registerUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public LoginResponse loginUser(LoginRequest request) {
		Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
		
		LoginResponse response = new LoginResponse();
		
		if (userOpt.isPresent() && passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
			String token = jwtUtil.generateToken(userOpt.get().getEmail());
			response.setMessage("Login Successful");
			response.setToken(token);
		} else {
			response.setMessage("Invalid email or password");
		}
		
		return response;
	}
}
