package com.smartkitchen.kitchenmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartkitchen.kitchenmanagement.model.User;
import com.smartkitchen.kitchenmanagement.responsemodel.DataResponse;
import com.smartkitchen.kitchenmanagement.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/getbyid/{id}")
	public DataResponse getUser(@PathVariable String id) {
		User user= userService.getUserById(id);
		DataResponse res = new DataResponse();
		res.setData(user);
		res.setStatus(true);
		res.setMessage("user has been fetched successfully");
		return res;
	}
	
	@GetMapping("/getall")
	public DataResponse getallUser() {
		List<User> li = userService.getallUsers();
		DataResponse res = new DataResponse();
		res.setData(li);
		res.setStatus(true);
		res.setMessage("user has been fetched successfully");
		return res;
	}


}
