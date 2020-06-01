package com.app.ressource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.model.User;
import com.app.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	
    @Autowired
    private UserService userService;
 
    @GetMapping("/check_User/{email}/{password}")
	public ResponseEntity<String> verif_User(@PathVariable(value = "email") String email,@PathVariable(value = "password") String password) {
		try {
	
		
			return ResponseEntity.accepted().body(userService.verif_User(email,password));
			
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
	
    @PostMapping("/addUser")
	public ResponseEntity <String> addUser(@RequestBody User user) {
		try {
			
			return ResponseEntity.accepted().body(userService.addUser(user));
			
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}


   @GetMapping("/getCurrentUser")
	public ResponseEntity<User> getCurrentUser() {
		try {
			
			return ResponseEntity.accepted().body(userService.getCurrentUser());	
			
		} catch (Exception e) {
			
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
		
	}
   
   @GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getAllUser() {
		try {
			
			return ResponseEntity.accepted().body(userService.getAllUser());
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
   @GetMapping("/getUser_Byid/{id}")
	public ResponseEntity<User> getUser_Byid(@PathVariable(value = "id") String id) {
		try {
			
			return ResponseEntity.accepted().body(userService.getUser_Byid(id));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
   
   @DeleteMapping("/deleteUser/{id}")
	public ResponseEntity <String>  deleteUser(@PathVariable(value = "id") String id) {
		try {
			
			return ResponseEntity.accepted().body(userService.deleteUser(id));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}

}
