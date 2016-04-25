package de.superteam2000.gwt.shared.bo;
import java.util.Date;

public class Profil extends BusinessObject {
	
	  private static final long serialVersionUID = 1L;
	 
	  private String nachname;
	  private String vorname;
	  private String email;
	  private Date geburtsdatum;
	  private Date erstelldatum;
	  private String haarfarbe;
	  private String raucher;
	  private String religion;
	  private int groesse;
	  private String geschlecht;
	
	  
	
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getGeburtsdatum() {
		return geburtsdatum;
	}
	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}
	public Date getErstelldatum() {
		return erstelldatum;
	}
	public void setErstelldatum(Date erstelldatum) {
		this.erstelldatum = erstelldatum;
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
	public int getGroesse() {
		return groesse;
	}
	public void setGroesse(int groesse) {
		this.groesse = groesse;
	}
	public String getGeschlecht() {
		return geschlecht;
	}
	public void setGeschlecht(String geschlecht) {
		this.geschlecht = geschlecht;
	}
	
	
	
	

}
