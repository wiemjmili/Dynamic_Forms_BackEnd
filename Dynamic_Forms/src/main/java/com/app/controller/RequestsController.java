package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Requests;
import com.app.service.RequestsService;;

@RestController
@RequestMapping("/api/requests")
public class RequestsController {
	
    @Autowired
    private RequestsService requestsService;
	
    @PostMapping("/addRequest")
	public ResponseEntity <String> addRequest(@RequestBody Requests req) {
		try {
			
			return ResponseEntity.accepted().body(requestsService.addRequest(req));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    
    
    
    @GetMapping("/getALLRequest")
	public ResponseEntity<List<Requests>>  getALLRequest() {
		try {
			
			return ResponseEntity.accepted().body(requestsService. getALLRequest());
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    
    
    @GetMapping("/getRequestByUser")
	public ResponseEntity<List<Requests>> getRequestByUser() {
		try {
			
			return ResponseEntity.accepted().body(requestsService.getRequestByUser());
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    
    
    
    @GetMapping("/getRequestByid/{id}")
	public ResponseEntity <Requests> getRequestByid(@PathVariable(value = "id") String id) {
		try {
			
			return ResponseEntity.accepted().body(requestsService.getRequestByid(id));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    
    
    @GetMapping("/getRequestToValidate")
	public ResponseEntity<List<Requests>> getRequestToValidate() {
		try {
			
			return ResponseEntity.accepted().body(requestsService.getRequest_To_Validate());
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    
    
    @GetMapping("/getRequestValidated")
	public ResponseEntity<List<Requests>> getRequestValidated() {
		try {
			
			return ResponseEntity.accepted().body(requestsService.getRequestValidated());
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    
    @DeleteMapping("/cancelRequest/{id}")
 	public ResponseEntity <String>  cancelRequest(@PathVariable(value = "id") String id) {
 		try {
 			
 			return ResponseEntity.accepted().body(requestsService.cancelRequest(id));
 			
 		} catch (Exception e) {
 			return ResponseEntity.badRequest().header( e.getMessage()).build();
 		}
 	}
	
}
