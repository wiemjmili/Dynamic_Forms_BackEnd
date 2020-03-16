package com.BD.ressource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.BD.repository.GroupRepository;
import com.BD.model.Group;

@RestController
public class GroupController {
	
	@Autowired
	private GroupRepository repository;
	
	
	@PostMapping("/addGroup")
	public String addGroup(@RequestBody Group Gp) {
		repository.save(Gp);
		return "Group added : " +Gp.getName_GP();
	}
	
	@GetMapping("/findAllGroups")
	public List <Group> getGroups(){
		return repository.findAll();
	}
	
	@GetMapping("/findAllGroup/{id}")
	public Optional<Group> getGroup(@PathVariable String id){
		return repository.findById(id);
	}
	

}
