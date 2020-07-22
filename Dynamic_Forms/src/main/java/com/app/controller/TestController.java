
package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	// User
	@GetMapping("/get_User")
	public User getUser() {

		User user = new User();
		user.setId("1234");
		user.setEmail("wiem@bd.com");
		user.setPassword("123456");
		user.setGroups(null);
		user.setRoles(null);

		return user;
	}

	@GetMapping("/getUser_ById")
	public User getUserById() {
		String id = "5efdbe094add73342e742cb6";
		User user = userService.getUserByid(id);

		return user;

	}

	// Workflow

	@GetMapping("/getWF_ById")
	public Workflow getWFById() {

		String id = "5efdcc704dd58c67644e0030";
		Workflow wf = workflowService.getWorkflow(id);

		return wf;

	}

	@GetMapping("/getWF_ByName")
	public Workflow getWFByName() {

		String name = "Demande congé";
		Workflow wf = workflowService.getWF(name);

		return wf;

	}

}
