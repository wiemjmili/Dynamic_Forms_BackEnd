package com.app.ressource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.model.Group;
import com.app.service.GroupService;

@RestController
@RequestMapping("/api/group")
public class GroupController {
	
	
    @Autowired
    private GroupService GroupService;
	
    @PostMapping("/addGroup")
	public ResponseEntity <String> addGroup(@RequestBody Group GP) {
		try {
			
			return ResponseEntity.accepted().body(GroupService.addGroup(GP));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
	
    @GetMapping("/findAllGroups")
	public ResponseEntity<List <Group>> getGroups() {
		try {
			
			return ResponseEntity.accepted().body(GroupService.getGroups());
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
	
    @GetMapping("/findAllGroup/{id}")
	public ResponseEntity <Optional<Group>>  getGroup(@PathVariable(value = "id") String id) {
		try {
			
			return ResponseEntity.accepted().body(GroupService. getGroup(id));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
	
}
