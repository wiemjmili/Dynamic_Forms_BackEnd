
package com.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.app.model.User;
import com.app.model.Workflow;
import com.app.service.UserService;
import com.app.service.WorkflowService;

@RestController
@RequestMapping("/api")
public class TestController {
	
	
    @Autowired
    private UserService userService;
    @Autowired
    private WorkflowService workflowService;

  
    
    //User
	@GetMapping("/get_User")
		public User get_User() {
		
			User user = new User();
			user.setId("1234");
			user.setName("wiem");
			user.setEmail("wiem@bd.com");
			user.setPassword("123456");
			user.setGroups(null);
			user.setRoles(null);
			
       
		return user;
	}
		
		@GetMapping("/getUser_ById")
		public User get_UserById() {
		    String id="5eebae085ae309471ad978bb";
			User user = userService.getUser_Byid(id);
       
		return user;
		
	}
		
	//Workflow
		
		@GetMapping("/getWF_ById")
		public Workflow get_WF_ById() {
			
		    String id="5ef324cebfb2181f632e48a0";
			Workflow WF = workflowService.getWorkflow(id);
       
		return WF;
		
	}
		
		
		@GetMapping("/getWF_ByName")
		public Workflow get_WF_ByName() {
			
		    String name="Demande congé";
			Workflow WF = workflowService.getWF(name);
       
		return WF;
		
	}
		
	

}
