package com.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection ="Form")
public class Form {
	
	@Id
	private String id;
	
	private Object[] data;
	
	private String idUT;

	public String getId() {
		return id;
	}

	public void setIdUT(String idUT) {
		this.idUT = idUT;
	}

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
		this.data = data;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getIdUT() {
		return idUT;
	}





}
