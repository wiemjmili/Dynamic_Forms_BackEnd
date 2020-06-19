package com.app.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.app.model.Requests;
import com.app.model.Response;
import com.app.model.User;
import com.app.model.UserTask;
import com.app.model.Workflow;
import com.app.enums.State;
import com.app.model.Process;
import com.app.repository.ProcessRepository;
import com.app.repository.RequestsRepository;
import com.app.repository.ResponseRepository;
import com.app.repository.WorkflowRepository;

@Service
public class RequestsService {
	
	@Autowired
	private RequestsRepository requestsRepository;
	@Autowired
	private ResponseRepository responseRepository;
	@Autowired
	private WorkflowRepository workflowRepository;
	@Autowired
	private ProcessRepository processRepository;
	@Autowired
	private Process_Service process_Service;
    @Autowired
    private UserService userService;
    @Autowired
    private ResponseService responseService;
    @Autowired
    private UserTaskService userTaskService;
    
    static TaskService taskService;

		public String addRequest( Requests req) {
		
				User user=userService.getCurrentUser();
				req.setUser(user);
				UserTask UT=userTaskService.getUT_ById(req.getForm().getIdUT());
		 
			    ProcessEngine processEngine = process_Service.getEngine();
				Map<String, Object> variables = new HashMap<>();
			    RuntimeService runtimeService = processEngine.getRuntimeService();
	
				String idDef=process_Service.get_Id_Definition(UT.getWorkFlow().getId(), UT.getWorkFlow().getWFXML());
	
				ProcessInstance	 processInstance = runtimeService
							.startProcessInstanceById(idDef, variables);


				TaskService taskService = processEngine.getTaskService();
	    	
	    		Task task = taskService.createTaskQuery()
						.processInstanceId(processInstance.getId()).singleResult();
	    		System.out.println(task.getName());
		        Process proc=new Process();
		        proc.setIdProcess(processInstance.getId());
		        proc.setIdDefinition(idDef);
		        proc.setWorkflow(UT.getWorkFlow());
		        proc.setUser(user);
		        proc.setState(State.IN_PROGRESS);
		        Process p=processRepository.save(proc); 
		        
		        req.setIdProc(p.getId()); 
		        req.setState(State.IN_PROGRESS);
		        requestsRepository.save(req);
		        taskService.complete(task.getId());
		
		        return "Request added ";
	}
		
	
		
	public String cancelRequest( String id) {

		Requests req=getRequestByid(id);
		if(req!=null) {
			requestsRepository.delete(req);
			return "Request deleted";
			}
			else return "Request not found";
	    }


	public List<Requests> getALLRequest() {
		List <Requests> listReq =requestsRepository.findAll();
		return listReq;
	}
	
	public List<Requests> getRequestByUser() {
		
		User user=userService.getCurrentUser();
		List <Requests> listReq =requestsRepository.findAll();
		List <Requests>  listReqUser =new ArrayList<Requests>();
		
		for(int i=0;i<listReq.size();i++) {
			if(listReq.get(i).getUser().getId().equals(user.getId())) {
				listReqUser.add(listReq.get(i));
			}
		}
		return listReqUser;
	}

	public Requests getRequestByid(String id) {
		List <Requests> listReq =requestsRepository.findAll();
		Requests req=new Requests();
		boolean find=false;
		int j=0;
		while(find==false && listReq.size()>j) {
			if(listReq.get(j).getId().equals(id)) {
				find=true;
				req= listReq.get(j);
			}else {
				j++;
			}	
		}
	
		return req;
	}
	
	public List<UserTask> getUserTaskByUser(String nameWF) {
		
        boolean find=false;
        List <UserTask> listUT= userTaskService.getTasksBynameWF(nameWF);
		User Current_User = userService.getCurrentUser();
		List<UserTask> listUserTask =new ArrayList<UserTask>();

		for(int j=0;j<listUT.size();j++) {
			
			find=false;
			for(int i=0;i<listUT.get(j).getGroup().size();i++) {
				
				for (int k=0;k<Current_User.getGroups().size();k++) {
					
					if(listUT.get(j).getGroup().get(i).getName_GP().equals(Current_User.getGroups().get(k).getName_GP())) {
						find=true;
						
			}
		}}
			if(find==true) {
				listUserTask.add(listUT.get(j));
			}
	 }
		return listUserTask;
	}
	

	
	public List<UserTask> getUserTaskToValidate(String nameWF) {
		
        boolean find=false;
        List <UserTask> listUT= userTaskService.getTasksBynameWF(nameWF);
		User Current_User = userService.getCurrentUser();
		List<UserTask> listUTvalidate =new ArrayList<UserTask>();
		
		

		for(int j=0;j<listUT.size();j++) {
			
			find=false;
			
			for(int i=0;i<listUT.get(j).getGroup().size();i++) {
				
				for (int k=0;k<Current_User.getGroups().size();k++) {
					
					if(listUT.get(j).getGroup().get(i).getName_GP().equals(Current_User.getGroups().get(k).getName_GP())) {
						find=true;
						
			}
		}}
			if(find==true) {
				listUTvalidate.add(listUT.get(j-1));
			}
	 }
	
		return listUTvalidate;
	}
	
	public List<Requests> getRequestToValidate() {
	
		List <Requests> listReq =requestsRepository.findAll();
		List <Requests>  listReqUser =new ArrayList<Requests>();
		List <Response>  listResUser =new ArrayList<Response>();
		List <Workflow> listWF = workflowRepository.findAll();

		boolean find=false;

	
		for (int i=0;i<listWF.size();i++) {

			List<UserTask> listUTvalidate=getUserTaskToValidate(listWF.get(i).getName());
			
			
			for(int j=0;j<listReq.size();j++) {
				find=false;
				for (int k=0;k<listReq.get(j).getUser().getGroups().size();k++) {	
					
					for (int l=0;l<listUTvalidate.size();l++) {
						
					if(listUTvalidate.get(l).getId().equals(listReq.get(j).getForm().getIdUT())) {
						
						for(int m=0;m<listUTvalidate.get(l).getGroup().size();m++) {

							if(listReq.get(j).getUser().getGroups().get(k).getName_GP().equals(listUTvalidate.get(l).getGroup().get(m).getName_GP()))
							{find=true;}
						}
						}
					}
				}
				
				if(find==true) {
					if(!listReq.get(j).isValide()) {
					listReqUser.add(listReq.get(j));
					}}
			}
			if(listReqUser.size()==0) {
				List <Response> listRes = responseRepository.findAll();
				for(int j=0;j<listRes.size();j++) {
					find=false;
					for (int k=0;k<listRes.get(j).getUser().getGroups().size();k++) {		
						for (int l=0;l<listUTvalidate.size();l++) {
							for(int m=0;m<listUTvalidate.get(l).getGroup().size();m++) {

								if(listRes.get(j).getUser().getGroups().get(k).getName_GP().equals(listUTvalidate.get(l).getGroup().get(m).getName_GP()))
								{find=true;}
							}
						}
					}
					
					if(find==true) {
						listResUser.add(listRes.get(j));
					}
				}
			
				if(listResUser.size()!=0) {
					for(int k=0;k<listReq.size();k++) {
						for(int j=0;j<listResUser.size();j++) {
							if(listReq.get(k).getId().equals(listResUser.get(j).getIdReq())) {
									listReqUser.add(listReq.get(k));
								}
						}
					}}
		
			}
		}		
		
		
		return listReqUser;
	}

	public List<Requests> getRequestToValidate1() {

		List <Requests> listReq =requestsRepository.findAll();
		List <Requests>  listReqUser =new ArrayList<Requests>();
		List <Response>  listResUser =new ArrayList<Response>();
		List <Workflow> listWF = workflowRepository.findAll();

		boolean find=false;

	
		for (int i=0;i<listWF.size();i++) {

			List<UserTask> listUTvalidate=getUserTaskToValidate(listWF.get(i).getName());
			
			
			for(int j=0;j<listReq.size();j++) {
				
				find=false;
				
				for (int k=0;k<listReq.get(j).getUser().getGroups().size();k++) {	
					
					for (int l=0;l<listUTvalidate.size();l++) {
						
					if(listUTvalidate.get(l).getId().equals(listReq.get(j).getForm().getIdUT())) {
						
						for(int m=0;m<listUTvalidate.get(l).getGroup().size();m++) {

							if(listReq.get(j).getUser().getGroups().get(k).getName_GP().equals(listUTvalidate.get(l).getGroup().get(m).getName_GP()))
							{find=true;}
						}
						}
					}
				}
				
				if(find==true) {
					listReqUser.add(listReq.get(j));
					}
			}
			if(listReqUser.size()==0) {
				List <Response> listRes = responseRepository.findAll();
				for(int j=0;j<listRes.size();j++) {
					find=false;
					for (int k=0;k<listRes.get(j).getUser().getGroups().size();k++) {		
						for (int l=0;l<listUTvalidate.size();l++) {
							for(int m=0;m<listUTvalidate.get(l).getGroup().size();m++) {

								if(listRes.get(j).getUser().getGroups().get(k).getName_GP().equals(listUTvalidate.get(l).getGroup().get(m).getName_GP()))
								{find=true;}
							}
						}
					}
					
					if(find==true) {
						listResUser.add(listRes.get(j));
					}
				}
				
			
				if(listResUser.size()!=0) {
					for(int k=0;k<listReq.size();k++) {
						for(int j=0;j<listResUser.size();j++) {
							if(listReq.get(k).getId().equals(listResUser.get(j).getIdReq())) {
									listReqUser.add(listReq.get(k));
								}
						}
					}}
		
			}
		}		
		
		
		return listReqUser;
	}
	
	public List<Requests> getRequest_To_Validate() {
		
		 List <Requests>  listReqUser =new ArrayList<Requests>();
		 List <Requests> req= getRequestToValidate() ;
		 List <Response> res= responseService.getAllresponsebyUser();
		 
		 if(res.size()!=0) {
			 
		 for(int i=0;i<req.size();i++) {
			 
				boolean find=false;
				int j=0;
			
				while(find==false && res.size()>j) {
					
					if(req.get(i).getId().equals(res.get(j).getIdReq())) {
						find=true;
					}else j++;

				}			 
				if(find==false ) {
					
					listReqUser.add(req.get(i));
					
				}
		 }
		 
		 }else listReqUser=req;
		
		 return listReqUser; 
	}
	
	
	
	public List<Requests> listReq_Reject() {
		
		List <Requests> listReq =requestsRepository.findAll();
		List <Requests>  listReq_Reject =new ArrayList<Requests>();
		List <Workflow> listWF = workflowRepository.findAll();

		boolean find=false;

	
		for (int i=0;i<listWF.size();i++) {

			List<UserTask> listUTvalidate=getUserTaskToValidate(listWF.get(i).getName());
			for(int j=0;j<listReq.size();j++) {
				find=false;
				for (int k=0;k<listReq.get(j).getUser().getGroups().size();k++) {	
					
					for (int l=0;l<listUTvalidate.size();l++) {
						
					if(listUTvalidate.get(l).getId().equals(listReq.get(j).getForm().getIdUT())) {
						
						for(int m=0;m<listUTvalidate.get(l).getGroup().size();m++) {

							if(listReq.get(j).getUser().getGroups().get(k).getName_GP().equals(listUTvalidate.get(l).getGroup().get(m).getName_GP()))
							{find=true;}
						}
						}
					}
				}
				
				if(find==true) {			
					if(listReq.get(j).getState().equals(State.REFUSED)) {
					listReq_Reject.add(listReq.get(j));
					}}
			
			}
		}

		return listReq_Reject;
	}

	public List<Requests> getRequestValidated() {

		 List <Requests>  listReqUser =new ArrayList<Requests>();
		 List <Requests>  listReq_Reject =listReq_Reject();

		 List<Requests> req= getRequestToValidate1() ;
		 
		 List<Response> res= responseService.getAllresponsebyUser();
	
		 for(int i=0;i<req.size();i++) {

			 for(int j=0;j<res.size();j++) {
				 if(req.get(i).getId().equals(res.get(j).getIdReq()) ) {
					 listReqUser.add(req.get(i));
				 } 
			 } 
		 }
		 for(int i=0;i<listReq_Reject.size();i++) {
			listReqUser.add(listReq_Reject.get(i));	 
		 }
		 return listReqUser;
 
	}
	
	
	

}
