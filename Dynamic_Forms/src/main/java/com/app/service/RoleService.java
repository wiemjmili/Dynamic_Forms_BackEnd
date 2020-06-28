package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.Role;
import com.app.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	public String addRole( Role role) {
		if(!role.getName().equals("")) {
			roleRepository.save(role);}
		return "added user " +role.getName() ;
	}

	
	public List<Role> getAllRoles() {
		List <Role> list =roleRepository.findAll();
		return list;
	}
	
	public Role getRole_Byid(String id) {
		Role Role=new Role();
		List <Role> list =roleRepository.findAll();
		boolean find=false;
		int i=0;
		
		while(find==false && list.size()>i) {
			
			if(list.get(i).getId().equals(id)) {
				Role=list.get(i);
					find=true;
				}else i++;
			
			}
		return Role;
	}
	
	public String deleteRole( String id) {
		
		List <Role> roles =roleRepository.findAll();
		Role role=new Role();
		boolean find=false;
		int i=0;
		
		while(find==false && roles.size()>i) {
			
			if(roles.get(i).getId().equals(id)) {
				role=roles.get(i);
					find=true;
				}else i++;
			
			}
		
		if(find==true) {
			roleRepository.delete(role);
			return "Role deleted";
		}else return "Role not deleted";
	
	}
}
