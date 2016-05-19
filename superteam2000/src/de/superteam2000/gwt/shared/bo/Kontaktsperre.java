package de.superteam2000.gwt.shared.bo;

public class Kontaktsperre extends BusinessObject {

	private static final long serialVersionUID = 1L;

	private int gesperrterId = 0;
	private int sperrerId = 0;

	public int getGesperrterId() {
		return gesperrterId;
	}

	public void setGesperrterId(int gesperrterId) {
		this.gesperrterId = gesperrterId;
	}

	public int getSperrerId() {
		return sperrerId;
	}

	public void setSperrerId(int sperrerId) {
		this.sperrerId = sperrerId;
	}

}
