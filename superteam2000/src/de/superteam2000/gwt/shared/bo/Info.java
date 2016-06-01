package de.superteam2000.gwt.shared.bo;

public class Info extends BusinessObject {

	private static final long serialVersionUID = 1L;

	private String text = "";
	private int eigenschaftId = 0;
	private int profilId = 0;
	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getEigenschaftId() {
		return eigenschaftId;
	}

	public void setEigenschaftId(int eigenschaft_id) {
		this.eigenschaftId = eigenschaft_id;
	}

	public int getProfilId() {
		return profilId;
	}

	public void setProfilId(int profil_id) {
		this.profilId = profil_id;
	}

}
