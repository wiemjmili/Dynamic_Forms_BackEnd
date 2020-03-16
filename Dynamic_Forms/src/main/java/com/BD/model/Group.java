package com.BD.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Group")
public class Group {
	
	@Id
	private String id;
	
	private String name_GP;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName_GP() {
		return name_GP;
	}

	public void setName_GP(String name_GP) {
		this.name_GP = name_GP;
	}
}
