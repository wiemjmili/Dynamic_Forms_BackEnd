package com.app;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.app.model.Group;
import com.app.model.Role;
import com.app.model.User;
import com.app.model.Workflow;
import com.app.repository.UserRepository;
import com.app.repository.WorkflowRepository;


public class TestWebApp extends SpringBootHelloWorldTests {

	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WorkflowRepository workflowRepository;
	
	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void get_User() throws Exception {
		
		mockMvc.perform(get("/api/get_User")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.name").value("wiem")).andExpect(jsonPath("$.email").value("wiem@bd.com"))
				.andExpect(jsonPath("$.id").value("1234")).andExpect(jsonPath("$.password").value("123456"));
		
	}
	
	@Test
	@Rollback(false)
	public void add_User() throws Exception {
		User user =new User("wiemJmili","wiem@bd.com","123456");
		List <Group>  listGp =new ArrayList<Group>();
		List <Role>  listRole =new ArrayList<Role>();
		user.setGroups(listGp);
		user.setRoles(listRole);
		User userSaved =userRepository.save(user);
		
		assertNotNull(userSaved);
	}
	
	@Test
	public void findById() throws Exception {
		
		mockMvc.perform(get("/api/getUser_ById")).andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.name").value("Admin")).andExpect(jsonPath("$.email").value("admin@bd.com"))
			.andExpect(jsonPath("$.password").value("1234"));	
	}
	
	@Test
	public void get_WF_ById() throws Exception {
		
		mockMvc.perform(get("/api/getWF_ById")).andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.name").value("Demande congé"));
	}
	
	@Test
	public void get_WF_ByName() throws Exception {
		
		mockMvc.perform(get("/api/getWF_ByName")).andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.id").value("5ef324cebfb2181f632e48a0"))
		    .andExpect(jsonPath("$.name").value("Demande congé"));
	}
	
	@Test
	@Rollback(false)
	public void add_Workflow() throws Exception {
		Workflow WF =new Workflow("123456789","Atesstation travail","");
		Workflow workflowSaved =workflowRepository.save(WF);
		
		assertNotNull(workflowSaved);
	}
}