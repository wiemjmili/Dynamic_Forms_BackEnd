package com.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.Requests;
import com.app.model.User;
import com.app.model.UserTask;
import com.app.model.Workflow;
import com.app.repository.RequestsRepository;
import com.app.repository.WorkflowRepository;

@Service
public class RequestsService {
	
	@Autowired
	private RequestsRepository Requestsrepository;
	@Autowired
	private WorkflowRepository Workflowrepository;
    @Autowired
    private UserService UserService;
    @Autowired
    private UserTaskService UserTaskService;
	
	public String addRequest( Requests req) {
		
		User user=UserService.getCurrentUser();
		req.setUser(user);
		Requestsrepository.save(req);
		
		return "Request added ";
	}
	
	public List<Requests> getRequestByUser() {
		
		User user=UserService.getCurrentUser();
		List <Requests> listReq =Requestsrepository.findAll();
		List <Requests>  listReqUser =new ArrayList<Requests>();
		
		for(int i=0;i<listReq.size();i++) {
			if(listReq.get(i).getUser().getId().equals(user.getId())) {
				listReqUser.add(listReq.get(i));
			}
		}
		return listReqUser;
	}
	
	public List<UserTask> getUserTaskByUser(String nameWF) {
		
        boolean find=false;
        List <UserTask> listUT= UserTaskService.getTasksforWF(nameWF);
		User Current_User = UserService.getCurrentUser();
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
        List <UserTask> listUT= UserTaskService.getTasksforWF(nameWF);
		User Current_User = UserService.getCurrentUser();
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
		
		List <Requests> listReq =Requestsrepository.findAll();
		List <Requests>  listReqUser =new ArrayList<Requests>();
		List <Workflow> listWF = Workflowrepository.findAll();
		boolean find=false;
		
		for (int i=0;i<listWF.size();i++) {
			
			//List<UserTask> listUserTask= getUserTaskByUser(listWF.get(i).getName());
			List<UserTask> listUTvalidate=getUserTaskToValidate(listWF.get(i).getName());
			
			for(int j=0;j<listReq.size();j++) {
				find=false;
				for (int k=0;k<listReq.get(j).getUser().getGroups().size();k++) {		
					for (int l=0;l<listUTvalidate.size();l++) {
						for(int m=0;m<listUTvalidate.get(l).getGroup().size();m++) {

							if(listReq.get(j).getUser().getGroups().get(k).getName_GP().equals(listUTvalidate.get(l).getGroup().get(m).getName_GP()))
							{find=true;}
						}
					}
				}
				
				if(find==true) {
					listReqUser.add(listReq.get(j));
				}
			}
		}		
		return listReqUser;
	}

}
