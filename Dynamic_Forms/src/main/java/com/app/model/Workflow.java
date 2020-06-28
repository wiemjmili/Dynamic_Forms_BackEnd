package com.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Workflow")
public class Workflow {
	
	@Id
	private String id;
	private String name;
	private String wf_Xml;
	
	  public Workflow(String id, String name, String wf_Xml ) {
		    this.id = id;
		    this.name = name;
		    this.wf_Xml = wf_Xml;

		  }
	  public Workflow() {


	 }
	
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
		return wf_Xml;
	}
	public void setWFXML(String wFXML) {
		wf_Xml = wFXML;
	}
	
}
