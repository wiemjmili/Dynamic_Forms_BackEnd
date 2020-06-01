package com.app.ressource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Response;
import com.app.service.ResponseService;

@RestController
@RequestMapping("/api/response")

public class ResponseController {
	
    @Autowired
    private ResponseService responseService;
    
    @PostMapping("/addResponse")
	public ResponseEntity <String> addResponse(@RequestBody Response res) {
		try {
			
			return ResponseEntity.accepted().body(responseService.addResponse(res));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    
    @GetMapping("/getAllResponsebyUser")
	public ResponseEntity<List<Response>> getAllresponsebyUser() {
		try {
			
			return ResponseEntity.accepted().body(responseService.getAllresponsebyUser());
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    
    @GetMapping("/getAllResponse")
	public ResponseEntity<List<Response>> getAllresponse() {
		try {
			
			return ResponseEntity.accepted().body(responseService.getAllresponse());
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}

}
