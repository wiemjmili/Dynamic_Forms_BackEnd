package com.app.ressource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringBootSecurityController {
    @GetMapping("/")
	public String Home() {
    	return("<h1>welcome</h1>");
    }
    }