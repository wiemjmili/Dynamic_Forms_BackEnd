package com.app.ressource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.app.model.User;
import com.app.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	@PostMapping("/addUser")
	public String addUser(@RequestBody User user) {
		repository.save(user);
		return "added user " +user.getName() ;
	}
	

}
