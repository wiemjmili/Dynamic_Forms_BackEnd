package com.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.flowable.engine.TaskService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;


public class Execute_Process {
	
	static TaskService taskService;
	
	 public static void main(String[] args) {
		  
		    ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
		      .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
		      .setJdbcUsername("sa")
		      .setJdbcPassword("")
		      .setJdbcDriver("org.h2.Driver")
		      .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

		    ProcessEngine processEngine = cfg.buildProcessEngine();
		    RuntimeService runtimeService = processEngine.getRuntimeService();
		    RepositoryService repositoryService = processEngine.getRepositoryService();
		    Deployment deployment = repositoryService.createDeployment().addClasspathResource("Process.bpmn20.xml").deploy();
		    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
		    System.out.println("Processus: " + processDefinition.getName());
		    
		    Map<String, Object> variables = new HashMap<String, Object>();
		    ProcessInstance processInstance =runtimeService.startProcessInstanceByKey("Process_1", variables);	
		    TaskService taskService = processEngine.getTaskService();
		    List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("Group data").list();
		    System.out.println("You have " + tasks.size() + " tasks:");
	        for (int i=0; i<tasks.size(); i++) {
		    	System.out.println((i+1) + ") " + tasks.get(i).getName());
		    	    		
		    }
	        }}
