package de.superteam2000.gwt.shared.bo;

import java.util.ArrayList;

public class Kontaktsperre extends BusinessObject {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Profil> gesperrteProfile = new ArrayList<>();

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

	public ArrayList<Profil> getGesperrteProfile() {
		return gesperrteProfile;
	}

	public void setGesperrteProfile(ArrayList<Profil> gesperrteProfile) {
		this.gesperrteProfile = gesperrteProfile;
	}
	
	public void addGesperrtesProfil(Profil p){
		this.gesperrteProfile.add(p);
	}

}
