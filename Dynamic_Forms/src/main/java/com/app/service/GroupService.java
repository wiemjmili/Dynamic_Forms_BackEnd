package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.app.model.Group;
import com.app.repository.GroupRepository;

@Service
public class GroupService {
	@Autowired
	private GroupRepository groupRepository;
	
	
	public List <Group> getGroups(){
		return groupRepository.findAll();
	}
	
	public String addGroup( Group Gp) {
		if(Gp.getName_GP()!="") {
			groupRepository.save(Gp);}
		return "Group added : " +Gp.getName_GP();
	}
	
	
	public Group getGroup_Byid(String id) {
		Group Group=new Group();
		List <Group> list =groupRepository.findAll();
		boolean find=false;
		int i=0;
		
		while(find==false && list.size()>i) {
			
			if(list.get(i).getId().equals(id)) {
				Group=list.get(i);
					find=true;
				}else i++;
			
			}
		return Group;
	}
	
	public String deleteGroup( String id) {
	
		List <Group> Group =groupRepository.findAll();
		Group Gp=new Group();
		boolean find=false;
		int i=0;
		
		while(find==false && Group.size()>i) {
			
			if(Group.get(i).getId().equals(id)) {
					Gp=Group.get(i);
					find=true;
				}else i++;
			
			}
		
		if(find==true) {
			groupRepository.delete(Gp);
			return "Group deleted";
		}else return "Group not deleted";
	
	}
}
