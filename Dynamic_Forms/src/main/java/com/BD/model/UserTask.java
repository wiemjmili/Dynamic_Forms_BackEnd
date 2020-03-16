package com.BD.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "UserTask")
public class UserTask {
			
		@Id
		private String id;
		private String Name;
		private String groupN;
		private Workflow WorkFlow;
		private List <Group> Group;
		

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
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
		public Workflow getWorkFlow() {
			return WorkFlow;
		}
		public void setWorkFlow(Workflow workFlow) {
			WorkFlow = workFlow;
		}
		public List<Group> getGroup() {
			return Group;
		}
		public void setGroup(List<Group> group) {
			Group = group;
		}
		

}
