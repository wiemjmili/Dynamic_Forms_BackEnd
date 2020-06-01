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
    private UserTaskService usertaskService;

    @GetMapping("/findAlltasks")
	public ResponseEntity<List<UserTask>> getTasks() {
		try {
			
			return ResponseEntity.accepted().body(usertaskService.getTasks());
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
	
    @GetMapping("/findAlltasks/{idWF}")
	public ResponseEntity<List<UserTask>> getTasksforWF(@PathVariable(value = "idWF") String idWF) {
		try {
			
			return ResponseEntity.accepted().body(usertaskService.getTasksforWF(idWF));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    
	@GetMapping("/findUsertask/{nameUT}/{nameWF}")
	public ResponseEntity<String> getUserTask(@PathVariable(value = "nameUT") String nameUT , @PathVariable(value ="nameWF") String nameWF) 
	{
		try {
			return ResponseEntity.accepted().body(usertaskService.getUserTask(nameUT,nameWF));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
	
    @PostMapping("/updateUT")
	public ResponseEntity <String> addGrouptoUserTask(@RequestBody UserTask UT) {
		try {
			
			return ResponseEntity.accepted().body(usertaskService.addGroup(UT));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    
	@GetMapping("/getUT_ById/{idUT}")
	public ResponseEntity<UserTask> getUT_ById(@PathVariable(value = "idUT") String idUT) 
	{
		try {
			return ResponseEntity.accepted().body(usertaskService.getUT_ById(idUT));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}

}
