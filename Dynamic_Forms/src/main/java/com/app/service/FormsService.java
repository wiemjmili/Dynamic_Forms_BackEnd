package com.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.Form;
import com.app.model.UserTask;
import com.app.repository.FormRepository;
import com.app.repository.UserTaskRepository;

@Service
public class FormsService {

	@Autowired
	private FormRepository Formrepository;
	@Autowired
	private UserTaskRepository UserTaskrepository;
    @Autowired
    private UserTaskService UserTaskService;
	
    public List <Form> getListFormByNameWorkflow(String nameWF){
    	
	    List<UserTask> listUT=UserTaskService.getTasksforWF(nameWF);
		List <Form> all_Forms =Formrepository.findAll();
		List <Form>  listForm =new ArrayList();
		  
		  for (int i = 0; i < listUT.size(); i++)   
		  {   
			 for(int j=0;j<all_Forms.size();j++) {
				 if(listUT.get(i).getId().equals(all_Forms.get(j).getIdUT())) {
					 listForm.add(all_Forms.get(j));
				 }
			 }
		  }
		 
		 return listForm;
    }
    
    public String addForm(Form form){
    	
    	if(form.getData().length !=0 ) {
		Formrepository.save(form);
		
		return "Form added";
	
    	}else 
    		
          return "Form empty";
        }
    
}
