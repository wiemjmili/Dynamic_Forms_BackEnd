package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.User;
import com.app.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/check_User/{username}/{password}")
	public ResponseEntity<String> verifUser(@PathVariable(value = "username") String username,
			@PathVariable(value = "password") String password) {
		try {

			return ResponseEntity.accepted().body(userService.verifUser(username, password));

		} catch (Exception e) {
			return ResponseEntity.badRequest().header(e.getMessage()).build();
		}
	}

	@PostMapping("/addUser")
	public ResponseEntity<String> addUser(@RequestBody User user) {
		try {

			return ResponseEntity.accepted().body(userService.addUser(user));

		} catch (Exception e) {
			return ResponseEntity.badRequest().header(e.getMessage()).build();
		}
	}

	@GetMapping("/getCurrentUser")
	public ResponseEntity<User> getCurrentUser() {
		try {

			return ResponseEntity.accepted().body(userService.getCurrentUser());

		} catch (Exception e) {

			return ResponseEntity.badRequest().header(e.getMessage()).build();
		}

	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getAllUser() {
		try {

			return ResponseEntity.accepted().body(userService.getAllUser());

		} catch (Exception e) {
			return ResponseEntity.badRequest().header(e.getMessage()).build();
		}
	}

	@GetMapping("/getUser_Byid/{id}")
	public ResponseEntity<User> getUserByid(@PathVariable(value = "id") String id) {
		try {

			return ResponseEntity.accepted().body(userService.getUserByid(id));

		} catch (Exception e) {
			return ResponseEntity.badRequest().header(e.getMessage()).build();
		}
	}

	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable(value = "id") String id) {
		try {

			return ResponseEntity.accepted().body(userService.deleteUser(id));

		} catch (Exception e) {
			return ResponseEntity.badRequest().header(e.getMessage()).build();
		}
	}

}
