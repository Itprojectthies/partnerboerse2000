package de.superteam2000.gwt.shared.bo;

import java.util.Date;

import de.superteam2000.gwt.client.DateTimeFormat;


public class Profil extends BusinessObject {

	private static final long serialVersionUID = 1L;
	
	private String nickname = "";
	private String nachname = "";
	private String vorname = "";
	private String email = "";
	private Date geburtsdatum = null;
	private Date erstelldatum = null;
	private String haarfarbe  = "";
	private String raucher = "";
	private String religion = "";
	private int groesse = 0;
	private String geschlecht = "";
	private boolean loggedIn = false;
	private String loginUrl = "";
	private String logoutUrl = "";
	private boolean isCreated = false;
	
	@SuppressWarnings("deprecation")
	public int getAlter() {
		String dateString = DateTimeFormat.getFormat("yyyy-MM-dd").format(geburtsdatum);
		String[] gebDaten = dateString.split("-");
		Date now = new Date();
	    int nowMonth = now.getMonth()+1;
	    int nowYear = now.getYear()+1900;
	    int year = Integer.valueOf(gebDaten[0]);
	    int month = Integer.valueOf(gebDaten[1]);
	    int day = Integer.valueOf(gebDaten[2]);
	    
	    int result = nowYear - year;

	    if ( month > nowMonth) {
	        result--;
	    }
	    else if (month == nowMonth) {
	        int nowDay = now.getDate();

	        if ( day > nowDay) {
	            result--;
	        }
	    }
	    return result;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

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

	public boolean isCreated() {
		return isCreated;
	}

	public void setCreated(boolean isCreated) {
		this.isCreated = isCreated;
	}

}
