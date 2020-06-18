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
	private FormRepository formRepository;
    @Autowired
    private UserTaskService usertaskService;
    @Autowired
    private RequestsService requestsService;


    public List <Form> getListFormByNameWorkflow(String nameWF){

	    List<UserTask> listUT=usertaskService.getTasksBynameWF(nameWF);
		List <Form> all_Forms =formRepository.findAll();
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
		List <Form> all_Forms =formRepository.findAll();
		return all_Forms;
    }
   
   
   public List <Form> getFormsByUser(String idUT){
 
		List <Form> all_Forms =formRepository.findAll();
		List <Form> Forms =new ArrayList<Form>();
		UserTask UT=usertaskService.getUT_ById(idUT);
		List <UserTask> listUTask=new ArrayList<UserTask>();
		List <UserTask> list=new ArrayList<UserTask>();

			listUTask=requestsService.getUserTaskByUser(UT.getWorkFlow().getName());
				for(int j=0;j<listUTask.size();j++) {
					list.add(listUTask.get(j));
			}

			for (int i=0; i<all_Forms.size();i++) {
				for(int j=0;j<list.size();j++) {
					if(all_Forms.get(i).getIdUT().equals(list.get(j).getId())) {
						Forms.add(all_Forms.get(i));
					}
				}
			}
	
		 return Forms;
    }
   
   public Form getFormbyUserTask(String idUT){
   	
		List <Form> all_Forms =formRepository.findAll();
		boolean find=false;
		int j=0;
		Form form =new Form();
		while(find==false && all_Forms.size()>j) {
			if(all_Forms.get(j).getIdUT().equals(idUT)) {
				find=true;
				form=all_Forms.get(j);
			}else {
				j++;
			}	
		}
		 return form;
    }
    
    
    public String addForm(Form form){
    	
        List<Form> list=formRepository.findAll();
		boolean find=false;
		int j=0;
		while(find==false && list.size()>j) {
			if(form.getIdUT().equals(list.get(j).getIdUT())) {
				find=true;
				
			}else {
				j++;
			}	
		}
        if(find==false) {
        	
    	if(form.getData().length !=0 ) {
    		formRepository.save(form);
    		
				return "Form added";
    	}else 
    			return "Form not added";
    	
        }else return "Form not added";
        
    }

    public List <Form> getFormbyProcess(String nameWF){
    	
    	List <Form>  listForm =new ArrayList<Form>();
	    List<UserTask> listUT=usertaskService.getTasksBynameWF(nameWF);
		List <Form> all_Forms =formRepository.findAll();
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
		if(find==true) {listForm.add(form);}

		return listForm;
    }
    
    /* public List<Form>   getForm_Validate(String idUT){
	
    List<UserTask> list=RequestsService.getOnetaskToValidate(idUT);
    List <Form> Forms =new ArrayList<Form>();
	List <Form> all_Forms =Formrepository.findAll();
	boolean find=false;
	int j=0;
	Form form =new Form();
	while(find==false && all_Forms.size()>j) {
		if(all_Forms.get(j).getIdUT().equals(list.get(0).getId())) {
			find=true;
			form=all_Forms.get(j);
		}else {
			j++;
		}	
	}
     Forms.add(form);
	 return Forms ;
}*/
}
