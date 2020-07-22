package com.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Workflow")
public class Workflow {

	@Id
	private String id;
	private String name;
	private String xml;

	public Workflow(String id, String name, String xml) {
		this.id = id;
		this.name = name;
		this.xml = xml;

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

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

}
