package de.superteam2000.gwt.shared.bo;

public abstract class Eigenschaft extends BusinessObject {

	private static final long serialVersionUID = 1L;

	private String name = "";
	private String beschreibungstext = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBeschreibungstext() {
		return beschreibungstext;
	}

	public void setBeschreibungstext(String beschreibungstext) {
		this.beschreibungstext = beschreibungstext;
	}

}
