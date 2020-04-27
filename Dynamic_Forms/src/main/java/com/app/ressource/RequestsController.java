package com.app.ressource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    private RequestsService RequestsService;
	
    @PostMapping("/addRequest")
	public ResponseEntity <String> addRequest(@RequestBody Requests req) {
		try {
			
			return ResponseEntity.accepted().body(RequestsService.addRequest(req));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    
    @GetMapping("/getRequestByUser")
	public ResponseEntity<List<Requests>> getRequestByUser() {
		try {
			
			return ResponseEntity.accepted().body(RequestsService.getRequestByUser());
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    @GetMapping("/getRequestToValidate")
	public ResponseEntity<List<Requests>> getRequestToValidate() {
		try {
			
			return ResponseEntity.accepted().body(RequestsService.getRequestToValidate());
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
	
}
