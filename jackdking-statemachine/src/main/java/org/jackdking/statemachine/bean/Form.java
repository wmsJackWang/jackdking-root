package org.jackdking.statemachine.bean;

public class Form {
	String id;
	public String formName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	@Override
	public String toString() {
		return "Form [id=" + id + ", formName=" + formName + "]";
	}

}
