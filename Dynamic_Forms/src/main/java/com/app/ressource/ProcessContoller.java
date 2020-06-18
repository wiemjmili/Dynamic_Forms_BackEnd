package com.app.ressource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Process;
import com.app.service.Process_Service;

@RestController
@RequestMapping("/api/process")
public class ProcessContoller {

	
    @Autowired
    private Process_Service processService;
    
    @GetMapping("/getLastProcess")
	public ResponseEntity <Process> getLastProcess() {
		try {
			
			return ResponseEntity.accepted().body(processService.getLastProcess());
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    
}
