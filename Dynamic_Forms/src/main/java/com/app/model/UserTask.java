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
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((group == null) ? 0 : group.hashCode());
			result = prime * result + ((groupN == null) ? 0 : groupN.hashCode());
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((statut == null) ? 0 : statut.hashCode());
			result = prime * result + ((workFlow == null) ? 0 : workFlow.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			UserTask other = (UserTask) obj;
			if (group == null) {
				if (other.group != null)
					return false;
			} else if (!group.equals(other.group))
				return false;
			if (groupN == null) {
				if (other.groupN != null)
					return false;
			} else if (!groupN.equals(other.groupN))
				return false;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (statut == null) {
				if (other.statut != null)
					return false;
			} else if (!statut.equals(other.statut))
				return false;
			if (workFlow == null) {
				if (other.workFlow != null)
					return false;
			} else if (!workFlow.equals(other.workFlow))
				return false;
			return true;
		}

		

}
