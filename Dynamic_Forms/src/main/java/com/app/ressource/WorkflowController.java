package com.app.ressource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.model.Form;
import com.app.model.Group;
import com.app.model.UserTask;
import com.app.model.Workflow;
import com.app.repository.UserTaskRepository;
import com.app.repository.WorkflowRepository;
import com.app.service.UserTaskService;
import com.app.service.WorkflowService;

@RestController
@RequestMapping("/api/workflow")


public class WorkflowController {
	
    @Autowired
    private WorkflowService workflowService;

	
    @GetMapping("/findAllWF")
	public ResponseEntity<List<Workflow> >getAllWorkflow() {
		try {
			
			return ResponseEntity.accepted().body(workflowService.getAllWorkflow());
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    
    @GetMapping("//findAllWF/{id}")
	public ResponseEntity <Optional<Workflow>> getForms(@PathVariable(value = "id") String id) {
		try {
			
			return ResponseEntity.accepted().body(workflowService.getWorkflow(id));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
	
    @PostMapping("/addWF")
	public ResponseEntity <String> addWF(@RequestBody Workflow WF) {
		try {
			
			return ResponseEntity.accepted().body(workflowService.addWF(WF));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
 

}
