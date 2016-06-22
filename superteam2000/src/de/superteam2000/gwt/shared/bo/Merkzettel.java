package de.superteam2000.gwt.shared.bo;

import java.util.ArrayList;
import java.util.Vector;

public class Merkzettel extends BusinessObject {

	private static final long serialVersionUID = 1L;

	private ArrayList<Profil> gemerkteProfile;
	private int gemerkterId = 0;
	private int merkerId = 0;


	public int getMerkerId() {
		return merkerId;
	}

	public void setMerkerId(int merkerId) {
		this.merkerId = merkerId;
	}

	public ArrayList<Profil> getGemerkteProfile() {
		return gemerkteProfile;
	}

	public void setGemerkteProfile(ArrayList<Profil> gemerkteProfile) {
		this.gemerkteProfile = gemerkteProfile;
	}
	
	public void addGemerktesProfil(Profil p){
		this.gemerkteProfile.add(p);
	}

	public int getGemerkterId() {
		return gemerkterId;
	}

	public void setGemerkterId(int gemerkterId) {
		this.gemerkterId = gemerkterId;
	}

}
