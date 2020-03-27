package com.BD.ressource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.BD.model.Group;
import com.BD.model.UserTask;
import com.BD.model.Workflow;
import org.springframework.web.bind.annotation.*;

import com.BD.repository.UserTaskRepository;
import com.BD.repository.WorkflowRepository;
import com.BD.service.WorkflowService;
import com.BD.service.UserTaskService;

@RestController
public class WorkflowController {
	
	@Autowired
	private WorkflowRepository workflowrepository;
	
    @Autowired
    private WorkflowService workflowService;
    
	@Autowired
	private UserTaskRepository userTaskrepository;
	
    @Autowired
    private UserTaskService UserTaskService;
	
	@PostMapping("/addWF")
	public String addWF(@RequestBody Workflow WF) {
		  //save in mongo db 
		  workflowrepository.save(WF);
	      //save in file Process.bpmn20.xml
	      String name=workflowService.saveProcess(WF);
	      String xml=workflowService.addFlowable();
	      List <Group>  listGP =new ArrayList();
	      WF.setName(name);
	      WF.setWFXML(xml);
		  workflowrepository.save(WF);
	      // add UserTasks  
			List <UserTask> list=UserTaskService.getAllTasks();
			int i=0;
			  while(i<list.size()) 
			  {
				  list.get(i).setWorkFlow(WF);
				  list.get(i).setGroup(listGP);
				  userTaskrepository.save(list.get(i));
				  i++;
			  }  
		return "Workflow added"+WF.getName();
	}
	
	@GetMapping("/findAllWF")
	public List <Workflow> getWorkflow(){
		return workflowrepository.findAll();
	}
	
	
	@GetMapping("/findAllWF/{id}")
	public Optional<Workflow> getWorkflow(@PathVariable String id){
		return workflowrepository.findById(id);
	}
	

}
