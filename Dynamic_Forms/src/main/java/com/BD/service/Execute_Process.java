package com.BD.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

public class Execute_Process {
    ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
    	      .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
    	      .setJdbcUsername("sa")
    	      .setJdbcPassword("")
    	      .setJdbcDriver("org.h2.Driver")
    	      .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

    	    ProcessEngine processEngine = cfg.buildProcessEngine();
    	    
    	    RepositoryService repositoryService = processEngine.getRepositoryService();
    	    Deployment deployment = repositoryService.createDeployment()
    	      .addClasspathResource("DmdConge.bpmn20.xml")
    	      .deploy();
    	    
    	    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
    	    		  .deploymentId(deployment.getId())
    	    		  .singleResult();
    	    
    	    	/*	System.out.println("Processus: " + processDefinition.getName());
    	    
    	    		Scanner scanner= new Scanner(System.in);

    	    		System.out.println("Nom et Prénom :");
    	    		String employee = scanner.nextLine();
    	    		
    	    		
    	    		System.out.println("Nombre de jours ?");
    	    		Integer nbrJours = Integer.valueOf(scanner.nextLine());
    	    		
    	    		System.out.println("Le congé du date : jj/mm/aaaa");
    	    		String date1 = scanner.nextLine();
    	    		
    	    		System.out.println("Au date : jj/mm/aaaa");
    	    		String date2 = scanner.nextLine();

    	    		System.out.println("Raison du congé :");
    	    		String raison = scanner.nextLine();
    	    		
    	    		
    	    		RuntimeService runtimeService = processEngine.getRuntimeService();

    	    		Map<String, Object> variables = new HashMap<String, Object>();
    	    		variables.put("employee", employee);
    	    		variables.put("nbrJours", nbrJours);
    	    		variables.put("raison", raison);
    	    		variables.put("date1", date1);
    	    		variables.put("date2", date2);
    	    		
    	    		ProcessInstance processInstance =
    	    		  runtimeService.startProcessInstanceByKey("DmdConge", variables);

    	    		TaskService taskService = processEngine.getTaskService();
    	    		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("employee").list();
    	    		System.out.println("You have " + tasks.size() + " tasks:");
    	    		
    	    		for (int i=0; i<tasks.size(); i++) {
    	    		  System.out.println((i+1) + ") " + tasks.get(i).getName());
    	    		}
    	    		    		
    	    	    //System.out.println("Which task would you like to complete?");
    	    	    
    	    		int taskIndex = 1; //Integer.valueOf(scanner.nextLine());
    	    		Task task = tasks.get(taskIndex - 1);
    	    		Map<String, Object> processVariables = taskService.getVariables(task.getId());
    	    		
    	    		System.out.println("L'employeur "+processVariables.get("employee") + " veut " +
    	    		    processVariables.get("nbrJours") + " jours de congé de " +
    	    		    processVariables.get("date1") + " Au " +
    	    		    processVariables.get("date2")+" Approuver ou Refuser ?");
    	    		
    	    		boolean approved = scanner.nextLine().toLowerCase().equals("y");
    	    		variables = new HashMap<String, Object>();
    	    		variables.put("approved", approved);
    	    		taskService.complete(task.getId(), variables);
    	    		
    	    		
    	  }*/

}
