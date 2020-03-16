package com.BD.ressource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.BD.model.User;
import com.BD.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	@PostMapping("/addUser")
	public String addUser(@RequestBody User user) {
		repository.save(user);
		return "added user " +user.getName() ;
	}
	

}
