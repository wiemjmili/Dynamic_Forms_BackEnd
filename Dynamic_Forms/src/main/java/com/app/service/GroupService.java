package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.app.model.Group;
import com.app.repository.GroupRepository;

@Service
public class GroupService {
	@Autowired
	private GroupRepository Grouprepository;
	
	
	public List <Group> getGroups(){
		return Grouprepository.findAll();
	}
	
	public String addGroup( Group Gp) {
		Grouprepository.save(Gp);
		return "Group added : " +Gp.getName_GP();
	}
	
	public Optional<Group> getGroup( String id){
		return Grouprepository.findById(id);
	}
}
