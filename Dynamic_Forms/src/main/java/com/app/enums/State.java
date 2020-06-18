package com.app.enums;

public enum State {

	TO_INITIATE("A initier"),
 
	IN_PROGRESS("En attente"),

	VALIDATED("Approuvée"),
 
	REFUSED("Rejetée"),
 
	WAITING_FINAL_VALIDATION("Attente approbation responsable paye"),

	CANCELLED("Annulée");
	
	private String labelFr;

	private State(String labelFr) {
		this.labelFr = labelFr.intern();
	}

	public String getLabelFr() {
		return labelFr;
	}

}