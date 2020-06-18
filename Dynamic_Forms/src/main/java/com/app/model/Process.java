package com.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.app.enums.State;

@Document(collection = "HistoricalProcess")
public class Process {

	
	@Id
	private String id;
	private String idProcess;
	private String idDefinition;
	private User user;
	private  Workflow workflow;
	private State state;
	
	public String getIdProcess() {
		return idProcess;
	}
	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}


	public Workflow getWorkflow() {
		return workflow;
	}
	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdDefinition() {
		return idDefinition;
	}
	public void setIdDefinition(String idDefinition) {
		this.idDefinition = idDefinition;
	}

	
	
}
