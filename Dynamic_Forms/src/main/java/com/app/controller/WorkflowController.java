package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.model.Workflow;
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
    
    @GetMapping("/findAllWF/{id}")
	public ResponseEntity <Workflow> getForms(@PathVariable(value = "id") String id) {
		try {
			
			return ResponseEntity.accepted().body(workflowService.getWorkflow(id));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
	
    @PostMapping("/addWF")
	public ResponseEntity <String> addWF(@RequestBody Workflow wf) {
		try {
			
			return ResponseEntity.accepted().body(workflowService.addWF(wf));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    
    @PostMapping("/updateWF")
	public ResponseEntity <String> updateWF(@RequestBody Workflow wf) {
		try {
			
			return ResponseEntity.accepted().body(workflowService.updateWF(wf));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    
    @GetMapping("/getlistProcessbyUser")
 	public ResponseEntity<List<Workflow> >getListProcessbyUser() {
 		try {
 			
 			return ResponseEntity.accepted().body(workflowService.getListProcessbyUser());	
 			
 		} catch (Exception e) {
 			
 			return ResponseEntity.badRequest().header( e.getMessage()).build();
 		}
 		
 	}
    
    @DeleteMapping("/deleteWF/{id}")
 	public ResponseEntity <String>  deleteWF(@PathVariable(value = "id") String id){
 		try {
 			
 			return ResponseEntity.accepted().body(workflowService.deleteWF(id));
 			
 		} catch (Exception e) {
 			return ResponseEntity.badRequest().header( e.getMessage()).build();
 		}
 	}
 
}
