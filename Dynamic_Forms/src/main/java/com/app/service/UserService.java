package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.*;
import com.app.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository Userrepository;
	
	public User Current_User;
	
	public String verif_User(String email,String login) {
		List <User> Users =Userrepository.findAll();
		boolean find=false;
		int j=0;
		
		
		while(find==false && Users.size()>j) {
			
			if(Users.get(j).getEmail().equals(email) && Users.get(j).getLogin().equals(login)) {
				if(Users.get(j).getRoles().equals("admin")) {
					return "admin";
				}else {
					find=true;
					Current_User=Users.get(j);
				}
			}else {
				j++;
			}	
		}
		
		if(find ==true) {
			return "True";
		}else return "False";
	}
	
	public String addUser( User user) {
		Userrepository.save(user);
		return "added user " +user.getName() ;
	}

	public User getCurrentUser() {
		return Current_User;
	}
	
}


/*
List<String> list =new ArrayList();
for(int i=0;i<listUserTask.size();i++) {
	listWF.add(listUserTask.get(i).getWorkFlow());	
	list.add(listUserTask.get(i).getWorkFlow().getName());
}

  Set set = new HashSet() ;
set.addAll(list) ;
List<String> distinctList = new ArrayList(set) ;

List<Workflow> all_WF =workflowrepository.findAll();
List<Workflow> listWF1 =new ArrayList();

for(int i=0;i<distinctList.size();i++) {
	for(int j=0;j<all_WF.size();j++) {
		if(distinctList.get(i).equals(all_WF.get(j).getName())){
			listWF1.add(all_WF.get(j));
		}
	}
}*/
