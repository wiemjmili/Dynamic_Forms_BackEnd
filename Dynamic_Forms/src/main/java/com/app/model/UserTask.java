package com.app.model;

import java.util.List;
import com.app.enums.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "UserTask")
public class UserTask {
			
		@Id
		private String id;
		private String name;
		private Workflow workFlow;
		private List <Group> group;
		private String statut;
		private String idNextUT;
		private State state;

		public String getIdNextUT() {
			return idNextUT;
		}
		public void setIdNextUT(String idNextUT) {
			this.idNextUT = idNextUT;
		}
		public String getStatut() {
			return statut;
		}
		public void setStatut(String statut) {
			this.statut = statut;
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
		public State getState() {
			return state;
		}
		public void setState(State state) {
			this.state = state;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((idNextUT == null) ? 0 : idNextUT.hashCode());
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
			if (idNextUT == null) {
				if (other.idNextUT != null)
					return false;
			} else if (!idNextUT.equals(other.idNextUT))
				return false;
			return true;
		}

		

}
