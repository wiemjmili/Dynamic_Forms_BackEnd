package com.app.ressource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.model.User;
import com.app.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	
    @Autowired
    private UserService UserService;
 
    @GetMapping("/check_User/{email}/{login}")
	public ResponseEntity<String> verif_User(@PathVariable(value = "email") String email,@PathVariable(value = "login") String login) {
		try {
	
		
			return ResponseEntity.accepted().body(UserService.verif_User(email,login));
			
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
	
    @PostMapping("/addUser")
	public ResponseEntity <String> addUser(@RequestBody User user) {
		try {
			
			return ResponseEntity.accepted().body(UserService.addUser(user));
			
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}


   @GetMapping("/getCurrentUser")
	public ResponseEntity<User> getCurrentUser() {
		try {
			
			return ResponseEntity.accepted().body(UserService.getCurrentUser());	
			
		} catch (Exception e) {
			
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
		
	}

}
