package com.BD.ressource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.BD.model.Group;
import com.BD.model.UserTask;
import com.BD.model.Workflow;
import com.BD.repository.GroupRepository;
import com.BD.repository.UserTaskRepository;
import com.BD.service.UserTaskService;

@RestController
public class UserTaskController {
	
	@Autowired
	private UserTaskRepository UserTaskrepository;
    @Autowired
    private UserTaskService UserTaskService;
	@Autowired
	private GroupRepository Grouprepository;
	
	@PostMapping("/updateUT")
	public String addGroup(@RequestBody UserTask UT) {
		
		System.out.println(UT.getGroupN());
		
		UserTask UserT=new UserTask();
	    List <Group>  listGPUT =new ArrayList();
		List <UserTask> listUT=UserTaskrepository.findAll();
		List <Group> listGp=Grouprepository.findAll();
		
		  for (int i = 0; i < listUT.size(); i++)   
		  {
			  if(listUT.get(i).getName().equals(UT.getName())) {
				        UserT=listUT.get(i);
			  }
		  }
		  for (int i = 0; i < listGp.size(); i++)   
		  {
			  if(listGp.get(i).getName_GP().equals(UT.getGroupN())) {
				  listGPUT.add(listGp.get(i));
			  }
		  }
		  
		  UserT.setGroup(listGPUT);
	
		  UserTaskrepository.save(UserT);
		  UserTaskService.addGptoUT();

		return UT.getName();
	}
    
	@GetMapping("/findAlltasks")
	public List <UserTask> getTasks(){
		return UserTaskrepository.findAll();
	}
	
	@GetMapping("/findTasksWF")
	public List <UserTask> getTasksWF(){
		return UserTaskService.getTasksforLastWF();
	}
	

}
