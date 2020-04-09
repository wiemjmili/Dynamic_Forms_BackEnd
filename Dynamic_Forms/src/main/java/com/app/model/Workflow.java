package com.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Workflow")
public class Workflow {
	
	@Id
	private String id;
	private String name;
	private String WFXML;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWFXML() {
		return WFXML;
	}
	public void setWFXML(String wFXML) {
		WFXML = wFXML;
	}
	
}
