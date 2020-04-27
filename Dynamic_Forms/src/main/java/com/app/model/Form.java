package com.app.model;
import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection ="Form")
public class Form {
	
	@Id
	private String id;
	
	private Object[] data;
	
	private String idUT;

	public String getId() {
		return id;
	}

	public void setIdUT(String idUT) {
		this.idUT = idUT;
	}

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
		this.data = data;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getIdUT() {
		return idUT;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idUT == null) ? 0 : idUT.hashCode());
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
		Form other = (Form) obj;
		if (!Arrays.equals(data, other.data))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idUT == null) {
			if (other.idUT != null)
				return false;
		} else if (!idUT.equals(other.idUT))
			return false;
		return true;
	}



}
