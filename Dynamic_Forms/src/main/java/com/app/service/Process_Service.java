package com.app.service;

import java.util.List;


import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.model.Process;
import com.app.repository.ProcessRepository;

@Service
public class Process_Service {

	@Autowired
	private ProcessRepository processRepository;
	private ProcessEngine processEngine;

	public ProcessEngine getEngine() {
		
		if (processEngine == null) {
			ProcessEngineConfiguration procEngConfig = new StandaloneProcessEngineConfiguration()
				      .setJdbcUrl("jdbc:postgresql://localhost:5432/FLOWABLE_DB")
				      .setJdbcUsername("postgres")
				      .setJdbcPassword("1995")
				      .setJdbcDriver("org.postgresql.Driver")
				      .setAsyncExecutorActivate(true)
				      .setDatabaseType(ProcessEngineConfiguration.DATABASE_TYPE_POSTGRES)
				      .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
			
			processEngine = procEngConfig.buildProcessEngine();

		}
		return processEngine;
	}
	
	public Process getProcess_ById(String idProc) {
		
		List<Process> list=processRepository.findAll();
		boolean find=false;
		int i=0;
        Process proc=new Process();
		
		while(find==false && list.size()>i) {
			if(list.get(i).getId().equals(idProc)) {
				find=true;
				proc= list.get(i);
			}else {
				i++;
			}	
		}

		return proc;
	}
	
	public String get_Id_Definition(String idWF,String xmlWF) {
		
		List<Process> list=processRepository.findAll();
		boolean find=false;
		int i=0;
		String id_Def="";
		
		while(find==false && list.size()>i) {
			if(list.get(i).getWorkflow().getId().equals(idWF)) {
				find=true;
				id_Def= list.get(i).getIdDefinition();
			}else {
				i++;
			}	
		}
		
		if(id_Def.equals("")){
			
			processEngine = getEngine();
			RepositoryService repositoryService = processEngine.getRepositoryService();
			Deployment deployment = repositoryService.createDeployment().addString("Process" + ".bpmn", xmlWF).deploy();
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
						.deploymentId(deployment.getId()).singleResult();
			
			id_Def=processDefinition.getId();
		}
		return id_Def;
	}
	
}
