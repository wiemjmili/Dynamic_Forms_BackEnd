package com.app.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.model.Group;
import com.app.service.GroupService;

@RestController
@RequestMapping("/api/group")
public class GroupController {
	
	
    @Autowired
    private GroupService groupService;
	
    @PostMapping("/addGroup")
	public ResponseEntity <String> addGroup(@RequestBody Group GP) {
		try {
			
			return ResponseEntity.accepted().body(groupService.addGroup(GP));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
	
    @GetMapping("/findAllGroups")
	public ResponseEntity<List <Group>> getGroups() {
		try {
			
			return ResponseEntity.accepted().body(groupService.getGroups());
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
	
    @GetMapping("/getGroup_Byid/{id}")
	public ResponseEntity <Group>  getGroup(@PathVariable(value = "id") String id) {
		try {
			
			return ResponseEntity.accepted().body(groupService. getGroup_Byid(id));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    
    @DeleteMapping("/deleteGroup/{id}")
	public ResponseEntity <String>  deleteGroup(@PathVariable(value = "id") String id) {
		try {
			
			return ResponseEntity.accepted().body(groupService.deleteGroup(id));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
}
