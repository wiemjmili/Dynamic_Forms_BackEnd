package com.app.ressource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.app.model.Group;
import com.app.repository.GroupRepository;

@RestController
@RequestMapping("/api/group")
public class GroupController {
	
	@Autowired
	private GroupRepository Grouprepository;
	
	
	@PostMapping("/addGroup")
	public String addGroup(@RequestBody Group Gp) {
		Grouprepository.save(Gp);
		return "Group added : " +Gp.getName_GP();
	}
	
	@GetMapping("/findAllGroups")
	public List <Group> getGroups(){
		return Grouprepository.findAll();
	}
	
	@GetMapping("/findAllGroup/{id}")
	public Optional<Group> getGroup(@PathVariable String id){
		return Grouprepository.findById(id);
	}
	

}
