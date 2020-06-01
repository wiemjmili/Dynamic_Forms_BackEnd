package com.app.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.Response;
import com.app.model.User;
import com.app.model.UserTask;
import com.app.model.Requests;
import com.app.repository.ResponseRepository;
import com.app.repository.RequestsRepository;

@Service
public class ResponseService {
	
	@Autowired
	private ResponseRepository Responserepository;

	@Autowired
	private RequestsRepository Requestsrepository;
	
    @Autowired
    private UserService UserService;
    
    @Autowired
    private RequestsService RequestsService;
    
    @Autowired
    private UserTaskService UserTaskService;
    
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
		
		String id=res.getIdReq();
		Requests req=RequestsService.getRequestByid(id);
		String idUT=res.getForm().getIdUT();
		UserTask UT=UserTaskService.getUT_ById(idUT);
		
		if(req!=null) {
			
			User user=UserService.getCurrentUser();
			res.setUser(user);
			Responserepository.save(res);
			
			if(UT.getIdNextUT()==null) {
				req.setValide(true);
				}
			Requestsrepository.save(req);
		}

		return "Response added ";
	}
}
