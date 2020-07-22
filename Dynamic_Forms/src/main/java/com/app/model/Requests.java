package com.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.app.enums.State;

@Document(collection = "Requests")
public class Requests {

	@Id
	private String id;
	private String[][] request;
	@DBRef
	private Workflow wf;
	@DBRef
	private User user;
	@DBRef
	private Form form;
	private boolean valide;
	private String idProc;
	private State state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[][] getRequest() {
		return request;
	}

	public void setRequest(String[][] request) {
		this.request = request;
	}

	public Workflow getWf() {
		return wf;
	}

	public void setWf(Workflow wf) {
		this.wf = wf;
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

	public boolean isValide() {
		return valide;
	}

	public void setValide(boolean valide) {
		this.valide = valide;
	}

	public String getIdProc() {
		return idProc;
	}

	public void setIdProc(String idProc) {
		this.idProc = idProc;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
