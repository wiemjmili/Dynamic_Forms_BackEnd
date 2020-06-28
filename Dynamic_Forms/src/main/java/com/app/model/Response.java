package com.app.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Response")
public class Response {

	@Id
	private String id;
	private String [][] response;
	private User user;
	private Form form;
	private String idReq;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[][] getResponse() {
		return response;
	}
	public void setResponse(String[][] response) {
		this.response = response;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Form getForm() {
		return form;
	}
	public void setForm(Form form) {
		this.form = form;
	}
	public String getIdReq() {
		return idReq;
	}
	public void setIdReq(String idReq) {
		this.idReq = idReq;
	}


	
}
