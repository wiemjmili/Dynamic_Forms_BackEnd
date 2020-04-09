package com.app.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "UserTask")
public class UserTask {
			
		@Id
		private String id;
		private String name;
		private String groupN;
		private Workflow workFlow;
		private List <Group> group;
		private String statut;

		public String getStatut() {
			return statut;
		}
		public void setStatut(String statut) {
			this.statut = statut;
		}
		public String getGroupN() {
			return groupN;
		}
		public void setGroupN(String groupN) {
			this.groupN = groupN;
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
		public Workflow getWorkFlow() {
			return workFlow;
		}
		public void setWorkFlow(Workflow workFlow) {
			this.workFlow = workFlow;
		}
		public List<Group> getGroup() {
			return group;
		}
		public void setGroup(List<Group> group) {
			this.group = group;
		}

		

}
