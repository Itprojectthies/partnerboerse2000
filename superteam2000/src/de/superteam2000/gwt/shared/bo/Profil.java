package de.superteam2000.gwt.shared.bo;

import java.sql.Timestamp;
import java.util.Date;

import de.superteam2000.gwt.client.gui.DateTimeFormat;

/**
 * Hier wird ein exemplarisches Profil realisiert. Ein Profil hat einige vorgegebene Eigenschaften.
 * Anhand dem Geburtstdatum kann das Alter errechnet werden. Die Eigenschaft aehnlichkeit gibt an,
 * wie viel Prozent das Profil mit einem anderen Profil übereinstimmt.
 *
 * @author Christopher Funke, Daniel Volz
 * @version 1.0
 */
public class Profil extends BusinessObject {

  private static final long serialVersionUID = 1L;

  /**
   * Deklaration der Parameter eines Profils
   *
   * @param nickname
   * @param nachname
   * @mparam vorname
   * @param email E-Mail-Adresse des Users
   * @param geburtsdatum
   * @param erstelldatum Erstelldatum des Profils
   * @param haarfarbe
   * @param raucher Parameter gibt an, ob User Raucher oder Nichtraucher ist
   * @param religion
   * @param groesse
   * @param geschlecht
   * @param loggedIn Parameter gibt an, ob der User eingeloggt ist
   * @param loginUrl Parameter gibt die Login-Informationen des User an
   * @param logoutUrl Parameter gibt die Logout-Informationen des User an
   * @param isCreated Parameter gibt an, ob Profil angelegt ist
   * @param aehnlichkeit
   */
  private String nickname = "";
  private String nachname = "";
  private String vorname = "";
  private String email = "";
  private Date geburtsdatum = null;
  private Timestamp erstelldatum = null;
  private String haarfarbe = "";
  private String raucher = "";
  private String religion = "";
  private int groesse = 0;
  private String geschlecht = "";
  private boolean loggedIn = false;
  private String loginUrl = "";
  private String logoutUrl = "";
  private boolean isCreated = false;
  private int aehnlichkeit = 0;

  /**
   * Berechnung des Alters anhand des Geburtsdatums
   *
   * @return result Rückgabe des Alters
   */
  @SuppressWarnings("deprecation")
  public int getAlter() {
    String dateString = DateTimeFormat.getFormat("yyyy-MM-dd").format(geburtsdatum);
    String[] gebDaten = dateString.split("-");
    Date now = new Date();
    int nowMonth = now.getMonth() + 1;
    int nowYear = now.getYear() + 1900;
    int year = Integer.valueOf(gebDaten[0]);
    int month = Integer.valueOf(gebDaten[1]);
    int day = Integer.valueOf(gebDaten[2]);

    int result = nowYear - year;

    if (month > nowMonth) {
      result--;
    } else if (month == nowMonth) {
      int nowDay = now.getDate();

      if (day > nowDay) {
        result--;
      }
    }
    return result;
  }

  /**
   * Überprüfen ob User eingeloggt ist
   *
   * @return loggedIn Wert ob User eingeloggt ist
   */
  public boolean isLoggedIn() {
    return loggedIn;
  }

  /**
   * Setzen des Parameters loggedIn
   *
   * @param loggedIn
   */
  public void setLoggedIn(boolean loggedIn) {
    this.loggedIn = loggedIn;
  }

  /**
   * Auslesen des Logins
   *
   * @return loginUrl Wert mit welcher ID User eingeloggt ist
   */
  public String getLoginUrl() {
    return loginUrl;
  }

  /**
   * Setzen des Logins
   *
   * @param loginUrl
   */
  public void setLoginUrl(String loginUrl) {
    this.loginUrl = loginUrl;
  }

  /**
   * Auslesen des Logouts
   *
   * @return logoutUrl
   */
  public String getLogoutUrl() {
    return logoutUrl;
  }

  /**
   * Setzen des Logouts
   *
   * @param logoutUrl
   */
  public void setLogoutUrl(String logoutUrl) {
    this.logoutUrl = logoutUrl;
  }

  /**
   * Auslesen des Nicknames
   *
   * @return nickname
   */
  public String getNickname() {
    return nickname;
  }

  /**
   * Setzen des Nicknames
   *
   * @param nickname
   */
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  /**
   * Auslesen des Nachnamens
   *
   * @return nachname
   */
  public String getNachname() {
    return nachname;
  }

  /**
   * Setzen des Nachnamens
   *
   * @param nachname
   */
  public void setNachname(String nachname) {
    this.nachname = nachname;
  }

  /**
   * Auslesen des Vornamens
   *
   * @return vorname
   */
  public String getVorname() {
    return vorname;
  }

  /**
   * Setzen des Vornames
   *
   * @param vorname
   */
  public void setVorname(String vorname) {
    this.vorname = vorname;
  }

  /**
   * Auslesen der E-Mail-Adresse
   *
   * @return email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Setzen der E-Mail-Adresse
   *
   * @param email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Aulesen des Geburtsdatums
   *
   * @return geburtsdatum
   */
  public Date getGeburtsdatum() {
    return geburtsdatum;
  }

  /**
   * Setzen des Geburtsdatum
   *
   * @param geburtsdatum
   */
  public void setGeburtsdatum(Date geburtsdatum) {
    this.geburtsdatum = geburtsdatum;
  }

  /**
   * Auslesen des Erstelldatums
   *
   * @return erstelldatum
   */
  public Timestamp getErstelldatum() {
    // Date date = new Date(stamp.getTime());
    return erstelldatum;
  }

  /**
   * Setzen des Erstelldatums
   *
   * @param erstelldatum
   */
  public void setErstelldatum(Timestamp erstelldatum) {
    this.erstelldatum = erstelldatum;
  }

  /**
   * Auslesen der Haarfarbe
   *
   * @return haarfarbe
   */
  public String getHaarfarbe() {
    return haarfarbe;
  }

  /**
   * Setzen der Haarfarbe
   *
   * @param haarfarbe
   */
  public void setHaarfarbe(String haarfarbe) {
    this.haarfarbe = haarfarbe;
  }

  /**
   * Auslesen des Parameter Raucher
   *
   * @return raucher
   */
  public String getRaucher() {
    return raucher;
  }

  /**
   * Setzen des Parameters Raucher
   *
   * @param raucher
   */
  public void setRaucher(String raucher) {
    this.raucher = raucher;
  }

  /**
   * Auslesen der Religion
   *
   * @return religion
   */
  public String getReligion() {
    return religion;
  }

  /**
   * Setzen der Religion
   *
   * @param religion
   */
  public void setReligion(String religion) {
    this.religion = religion;
  }

  /**
   * Auslesen der Größe
   *
   * @return groesse
   */
  public int getGroesse() {
    return groesse;
  }

  /**
   * Setzen der Größe
   *
   * @param groesse
   */
  public void setGroesse(int groesse) {
    this.groesse = groesse;
  }

  /**
   * Auslesen des Geschlechts
   *
   * @return geschlecht
   */
  public String getGeschlecht() {
    return geschlecht;
  }

  /**
   * Setzen des Parameters Geschlecht
   *
   * @param geschlecht
   */
  public void setGeschlecht(String geschlecht) {
    this.geschlecht = geschlecht;
  }

  /**
   * Überprüfung, ob Profil angelegt wurde
   *
   * @return isCreated
   */
  public boolean isCreated() {
    return isCreated;
  }

  /**
   * Setzen des Parameters isCreated
   *
   * @param isCreated
   */
  public void setCreated(boolean isCreated) {
    this.isCreated = isCreated;
  }

  /**
   * Auslesen der Ähnlichkeit zu einem anderen Profil
   *
   * @return aehnlichkeit
   */
  public int getAehnlichkeit() {
    return aehnlichkeit;
  }

  /**
   * Setzen des Parameter Ähnlichkeit
   *
   * @param aehnlichkeit
   */
  public void setAehnlichkeit(int aehnlichkeit) {
    this.aehnlichkeit = aehnlichkeit;
  }

}
