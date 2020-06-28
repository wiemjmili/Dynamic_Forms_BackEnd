package com.app.service;

import java.util.ArrayList;
import java.util.List;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.Response;
import com.app.model.User;
import com.app.enums.State;
import com.app.model.Process;
import com.app.model.UserTask;
import com.app.model.Requests;
import com.app.repository.ResponseRepository;
import com.app.repository.ProcessRepository;
import com.app.repository.RequestsRepository;

@Service
public class ResponseService {
	
	@Autowired
	private ResponseRepository Responserepository;

	@Autowired
	private RequestsRepository Requestsrepository;
	@Autowired
	private ProcessRepository processRepository;
	
    @Autowired
    private UserService UserService;
    
    @Autowired
    private RequestsService RequestsService;
    
    @Autowired
    private UserTaskService UserTaskService;
    
    @Autowired
    private Process_Service process_Service;
    
	public List<Response> getAllresponsebyUser() {
		
		User user=UserService.getCurrentUser();
		List <Response> listRes =Responserepository.findAll();
		List <Response> listResponse =new ArrayList<Response>();
		
		for(int i=0;i<listRes.size();i++) {
			if(listRes.get(i).getUser().getId().equals(user.getId())) {
				listResponse.add( listRes.get(i));
			}
		}
		return listResponse;
	}
	
	public List<Response> getAllresponse() {
		
		List <Response> listRes =Responserepository.findAll();

		return listRes;
	}

	public String addResponse(Response res) {
		
		String idreq=res.getIdReq();
		Requests req=RequestsService.getRequestByid(idreq);
		String idProc=req.getIdProc();
		Process proc=process_Service.getProcess_ById(idProc);
		String idproc=proc.getIdProcess();
		
	    ProcessEngine processEngine = process_Service.getEngine();
		String idDef=proc.getIdDefinition();
		List<ProcessInstance> processInstances = processEngine.getRuntimeService().createProcessInstanceQuery()
				    .processDefinitionId(idDef).list();
		
		ProcessInstance proc_Instance=null;
		for (ProcessInstance process_Instance : processInstances) {
			   
			if(process_Instance.getId().equals(idproc)) {
				proc_Instance=process_Instance;
			}
		}

		
		
		TaskService taskService = processEngine.getTaskService();
		Task task = taskService.createTaskQuery()
				.processInstanceId(idproc).singleResult();
		Task Next_task = taskService.createTaskQuery()
				.processInstanceId(idproc).singleResult();
	
		if(task!=null) {
			taskService.complete(task.getId());
			
			if(Next_task==null && proc_Instance!=null) {
				processEngine.getRuntimeService().deleteProcessInstance(proc_Instance.getId(), "");
			}
		
			
		}
		
		String idUT=res.getForm().getIdUT();
		UserTask UT=UserTaskService.getUT_ById(idUT);			
	    User user=UserService.getCurrentUser();
			res.setUser(user);
			Responserepository.save(res);
			
			if(UT.getIdNextUT()==null) {
				req.setValide(true);
				
		}
			req.setState(State.VALIDATED);
			Requestsrepository.save(req);
			
		return "Response added ";
	}
	
	
	public String reject_Request(Requests Req) {

		Requests req=RequestsService.getRequestByid(Req.getId());
		String idProc=req.getIdProc();
		Process proc=process_Service.getProcess_ById(idProc);
		String idproc=proc.getIdProcess();
	    ProcessEngine processEngine = process_Service.getEngine();
		String idDef=proc.getIdDefinition();
		List<ProcessInstance> processInstances = processEngine.getRuntimeService().createProcessInstanceQuery()
				    .processDefinitionId(idDef).list();
		ProcessInstance proc_Instance=null;
		
		for (ProcessInstance process_Instance : processInstances) {
			   
			if(process_Instance.getId().equals(idproc)) {
				proc_Instance=process_Instance;
			}
		}
		// delete the process
		if(proc_Instance!=null) {
			processEngine.getRuntimeService().deleteProcessInstance(proc_Instance.getId(), "refused");
		}
		proc.setState(State.REFUSED);
		processRepository.save(proc);
		req.setValide(true);
		req.setState(State.REFUSED);
		Requestsrepository.save(req);
		
		return "request rejected ";
	}

}
