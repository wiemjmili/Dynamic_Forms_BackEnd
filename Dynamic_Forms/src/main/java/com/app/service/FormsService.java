package com.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.app.model.Form;
import com.app.model.UserTask;
import com.app.repository.FormRepository;


@Service
public class FormsService {

	@Autowired
	private FormRepository Formrepository;
    @Autowired
    private UserTaskService UserTaskService;
	
    public List <Form> getListFormByNameWorkflow(String nameWF){
    	
	    List<UserTask> listUT=UserTaskService.getTasksforWF(nameWF);
		List <Form> all_Forms =Formrepository.findAll();
		List <Form>  listForm =new ArrayList<Form>();
		  
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
    
    
   public List <Form> getALLForms(){
    	
		List <Form> all_Forms =Formrepository.findAll();

		 return all_Forms;
    }
    
    
    public String addForm(Form form){
    	if(form.getData().length !=0 ) {
    		Formrepository.save(form);
				return "Form added";
    	}else 
    			return "Form empty";
        }

    public List <Form> getFormbyProcess(String nameWF){
    	
    	List <Form>  listForm =new ArrayList<Form>();
	    List<UserTask> listUT=UserTaskService.getTasksforWF(nameWF);
		List <Form> all_Forms =Formrepository.findAll();
		Form  form =new Form();
		UserTask UT=listUT.get(0);
		
		boolean find=false;
		int j=0;
		while(find==false && all_Forms.size()>j) {
			if(UT.getId().equals(all_Forms.get(j).getIdUT())) {
				find=true;
				form=all_Forms.get(j);
			}else {
				j++;
			}	
		}
		listForm.add(form);
		return listForm;
    }
}
