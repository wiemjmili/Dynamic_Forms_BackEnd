package com.app.model;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name_GP == null) ? 0 : name_GP.hashCode());
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
		Group other = (Group) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name_GP == null) {
			if (other.name_GP != null)
				return false;
		} else if (!name_GP.equals(other.name_GP))
			return false;
		return true;
	}
}
