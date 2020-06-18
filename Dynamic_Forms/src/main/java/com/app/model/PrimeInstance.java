package com.app.model;
import com.app.enums.State;

public class PrimeInstance {
	
	private User user;
	private String validationStep;
	private State state;
	private String libelle;
	private String processInstanceId;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getValidationStep() {
		return validationStep;
	}
	public void setValidationStep(String validationStep) {
		this.validationStep = validationStep;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

}
