package com.app.ressource;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.UserTask;

import com.app.service.UserTaskService;

@RestController
@RequestMapping("/api/userTask")
public class UserTaskController {
 
    @Autowired
    private UserTaskService UserTaskService;

    @GetMapping("/findAlltasks")
	public ResponseEntity<List<UserTask>> getTasks() {
		try {
			
			return ResponseEntity.accepted().body(UserTaskService.getTasks());
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
	
    @GetMapping("/findAlltasks/{nameWF}")
	public ResponseEntity<List<UserTask>> getTasksWF(@PathVariable(value = "nameWF") String nameWF) {
		try {
			
			return ResponseEntity.accepted().body(UserTaskService.getTasksforWF(nameWF));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    
	@GetMapping("/findUsertask/{nameUT}/{nameWF}")
	public ResponseEntity<String> getUserTask(@PathVariable(value = "nameUT") String nameUT , @PathVariable(value ="nameWF") String nameWF) 
	{
		try {
			return ResponseEntity.accepted().body(UserTaskService.getUserTask(nameUT,nameWF));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
	
    @PostMapping("/updateUT")
	public ResponseEntity <String> addGrouptoUserTask(@RequestBody UserTask UT) {
		try {
			
			return ResponseEntity.accepted().body(UserTaskService.addGroup(UT));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}

}
