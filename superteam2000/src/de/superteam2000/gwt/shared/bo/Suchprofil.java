package de.superteam2000.gwt.shared.bo;

import java.util.HashMap;

public class Suchprofil extends BusinessObject {

	private static final long serialVersionUID = 1L;

	private String name  = "";
	private String text = "";
	private String haarfarbe = "";
	private String raucher = "";
	private String religion = "";
	private String geschlecht = "";

	private int profilId = 0;
	private int groesse_min = 0;
	private int groesse_max = 0;
	private int alter_min = 0;
	private int alter_max = 0;

	private HashMap<Integer, String> auswahlListe = new HashMap<>();

	public int getGroesse_min() {
		return groesse_min;
	}

	public void setGroesse_min(int groesse_min) {
		this.groesse_min = groesse_min;
	}

	public int getGroesse_max() {
		return groesse_max;
	}

	public void setGroesse_max(int groesse_max) {
		this.groesse_max = groesse_max;
	}

	public int getAlter_min() {
		return alter_min;
	}

	public void setAlter_min(int alter_min) {
		this.alter_min = alter_min;
	}

	public int getAlter_max() {
		return alter_max;
	}

	public void setAlter_max(int alter_max) {
		this.alter_max = alter_max;
	}

	public String getHaarfarbe() {
		return haarfarbe;
	}

	public void setHaarfarbe(String haarfarbe) {
		this.haarfarbe = haarfarbe;
	}

	public String getRaucher() {
		return raucher;
	}

	public void setRaucher(String raucher) {
		this.raucher = raucher;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getProfilId() {
		return profilId;
	}

	public void setProfilId(int profilId) {
		this.profilId = profilId;
	}

	public String getGeschlecht() {
		return geschlecht;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + profilId;
		return result;
	}

	@Override
	public boolean equals (Object object) {
		boolean result = false;
		if (object == null || object.getClass() != getClass()) {
			result = false;
		} else {
			Suchprofil sp = (Suchprofil) object;
			if (this.getName() == sp.getName() && this.getProfilId() == sp.getProfilId())  {
				result = true;
			}
		}
		return result;
	}

	public void setGeschlecht(String geschlecht) {
		this.geschlecht = geschlecht;
	}

	public HashMap<Integer, String> getAuswahlListe() {
		return auswahlListe;
	}

	public void setAuswahlListe(HashMap<Integer, String> auswahlListe) {
		this.auswahlListe = auswahlListe;
	}

}
