package com.app.service;

import org.flowable.engine.TaskService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.flowable.task.api.Task;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import com.app.model.UserTask;
import com.app.enums.State;


public class Execute_Process {
	
	static TaskService taskService;
	

	
	 public static void main(String[] args) {
		 
		 	//ProcessEngineConfiguration cfg=h2DatabaseConnection.getConfiguration();
		    ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
		      .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
		      .setJdbcUsername("sa")
		      .setJdbcPassword("")
		      .setJdbcDriver("org.h2.Driver")
		      .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		    
		    ProcessEngine processEngine = cfg.buildProcessEngine();
		    
			Map<String, Object> variables = new HashMap<>();
		    RuntimeService runtimeService = processEngine.getRuntimeService();
			RepositoryService repositoryService = processEngine.getRepositoryService();
			Deployment deployment = repositoryService.createDeployment().addClasspathResource("Process.bpmn20.xml").deploy();
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
					.deploymentId(deployment.getId()).singleResult();
			

			ProcessInstance processInstance = runtimeService
					.startProcessInstanceById(processDefinition.getId(), variables);
    		
			TaskService taskService = processEngine.getTaskService();
    	
    		Task task = taskService.createTaskQuery()
					.processInstanceId(processInstance.getId()).singleResult();

    		Scanner scanner= new Scanner(System.in); 
    		// First task 
    		System.out.println("You have task:");
    		System.out.println((1) + ") " + task.getName());
    		System.out.println(" Raison de congé ?");
    		String raison = scanner.nextLine();
    		variables = new HashMap<String, Object>();
    		variables.put("raison", raison);
 
    		taskService.complete(task.getId(), variables);
    		System.out.println(" Task Completed "+task.getName());
    		// Task finished
    		
            // Validation task 
    		Task task1 = taskService.createTaskQuery().singleResult();
    		System.out.println("Validation task :" + task1.getName());
    		System.out.println("L'employeur  veut un congé , Raison : "+variables.get("raison")+", Approuver ou Refuser ?(y/n)");
    		boolean Response = scanner.nextLine().toLowerCase().equals("y");
    		UserTask UT1=new UserTask();
    		
    		if(Response==true) {
    			UT1.setName(task1.getName());
    			UT1.setState(State.VALIDATED);
    			System.out.println(task1.getName() +" "+State.VALIDATED);
    			taskService.complete(task1.getId());
    			
    		}else {
    			UT1.setName(task1.getName());
    			UT1.setState(State.REFUSED);
    			System.out.println(processInstance.getId());
    			System.out.println(task1.getId());
    			System.out.println(task1.getName() +" "+State.REFUSED);
    			taskService.complete(task1.getId());
    			processEngine.getRuntimeService().deleteProcessInstance(processInstance.getId(), "refused");
    		}

    		System.out.println(" Task Completed "+task1.getName());
    		
    		// Task finished
    		
    		Task task2 = taskService.createTaskQuery().singleResult();
    		UserTask UT2=new UserTask();
    		if(task2!=null) {
    		// Validation task 
    		System.out.println("Validation task :" + task2.getName());
    		System.out.println("L'employeur veut un congé , Raison : "+variables.get("raison")+", Approuver ou Refuser ? (y/n)");
    		Response = scanner.nextLine().toLowerCase().equals("y");

    		if(Response==true) {
    			UT2.setName(task2.getName());
    			UT2.setState(State.VALIDATED);
    			System.out.println(State.VALIDATED);
    			System.out.println(task2.getName() +" "+State.VALIDATED);
    			taskService.complete(task2.getId());
    			
    		}else {
    			UT2.setName(task2.getName());
    			UT2.setState(State.REFUSED);
    			
    			System.out.println(task2.getName() +" "+State.REFUSED);
    			taskService.complete(task2.getId());
    		}

    		System.out.println(" Task Completed "+task2.getName());
    		// Task finished
    		}
    	
    		System.out.println(" Process :"+task.getName()+" faite par "+task.getAssignee());
    		System.out.println(" Validation Task :"+task1.getName()+ " | "+UT1.getState()+" par "+task1.getAssignee());
    		if(task2!=null) {
    		System.out.println(" Validation Task :"+task2.getName()+" | "+UT2.getState()+" par "+task2.getAssignee()); 	
    		}

}}
