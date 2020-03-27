package com.BD.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;

public class Execute_Process {
	
	 public static void main(String[] args) {
		 
		    ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
		      .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
		      .setJdbcUsername("sa")
		      .setJdbcPassword("")
		      .setJdbcDriver("org.h2.Driver")
		      .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

		    ProcessEngine processEngine = cfg.buildProcessEngine();
		    
		    RepositoryService repositoryService = processEngine.getRepositoryService();
		    Deployment deployment = repositoryService.createDeployment()
		      .addClasspathResource("Process.bpmn20.xml")
		      .deploy();
		    
		    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
		    		  .deploymentId(deployment.getId())
		    		  .singleResult();
		    
		    		System.out.println("Processus: " + processDefinition.getName());
		    
		    		Scanner scanner= new Scanner(System.in);

		    		System.out.println("Nom et Prénom :");
		    		String employee = scanner.nextLine();
		    		
		    		System.out.println("Raison ");
		    		String raison = scanner.nextLine();
		    		
		    		RuntimeService runtimeService = processEngine.getRuntimeService();

		    		Map<String, Object> variables = new HashMap<String, Object>();
		    		variables.put("employee", employee);
		    		variables.put("raison", raison);

		    		ProcessInstance processInstance =
		    		  runtimeService.startProcessInstanceByKey("Process_2", variables);


		    		
		  }

}
