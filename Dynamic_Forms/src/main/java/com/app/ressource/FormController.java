package com.app.ressource;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Form;
import com.app.service.FormsService;


@RestController
@RequestMapping("/api/form")
public class FormController {
	
    @Autowired
    private FormsService FormService;
 
    @GetMapping("/findAllForms/{nameWF}")
	public ResponseEntity<List<Form> > getForms(@PathVariable(value = "nameWF") String nameWF) {
		try {
			
			return ResponseEntity.accepted().body(FormService.getListFormByNameWorkflow(nameWF));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    
    @GetMapping("/findAllForm")
	public ResponseEntity<List<Form> > getAllForms() {
		try {
			
			return ResponseEntity.accepted().body(FormService.getALLForms());
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    
    @PostMapping("/addForm")
	public ResponseEntity <String> addForm(@RequestBody Form form) {
		try {
			
			return ResponseEntity.accepted().body(FormService.addForm(form));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}

    @GetMapping("/findFormbyProcess/{nameWF}")
	public ResponseEntity<List<Form> > getFormbyProcess(@PathVariable(value = "nameWF") String nameWF) {
		try {
			
			return ResponseEntity.accepted().body(FormService.getFormbyProcess(nameWF));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().header( e.getMessage()).build();
		}
	}
    

}

