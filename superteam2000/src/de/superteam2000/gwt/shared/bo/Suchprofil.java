package de.superteam2000.gwt.shared.bo;

import java.util.HashMap;

/**
 * 
 * Die klasse Suchprofil wird benötigt um die Suchen eines Users
 * zu realisieren. Außerdem wird sie benötigt, um die vom User
 * durchgeführten Suchen zu speichern und von der db zum client zu
 * transportieren.
 *
 * @author Funke, Volz
 */
public class Suchprofil extends BusinessObject {

  private static final long serialVersionUID = 1L;

  /**
   * Deklarationen der Parameter eines Suchprofils
   */
  private String name = "";
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

  /**
   * Deklaration des Parameters AuswahlListe
   *
   * @param auswahlListe Dieser Parameter ist eine HashMap vom Typ Integer und String. In dieser
   *        HashMap werden alle angelegten Suchprofile gespeichert.
   */
  private HashMap<Integer, String> auswahlListe = new HashMap<>();

  /**
   * Auslesen der Minimalgröße
   *
   * @return groesse_min
   */
  public int getGroesse_min() {
    return groesse_min;
  }

  /**
   * Setzen der Minimalgröße
   *
   * @param groesse_min
   */
  public void setGroesse_min(int groesse_min) {
    this.groesse_min = groesse_min;
  }

  /**
   * Auslesen der Maximalgröße
   *
   * @return groesse_max
   */
  public int getGroesse_max() {
    return groesse_max;
  }

  /**
   * Setzen der Maximalgröße
   *
   * @param groesse_max
   */
  public void setGroesse_max(int groesse_max) {
    this.groesse_max = groesse_max;
  }

  /**
   * Auslesen des Mindestalters
   *
   * @return alter_min
   */
  public int getAlter_min() {
    return alter_min;
  }

  /**
   * Setzen des Mindestalters
   *
   * @param alter_min
   */
  public void setAlter_min(int alter_min) {
    this.alter_min = alter_min;
  }

  /**
   * Auslesen des Maximalalters
   *
   * @return alter_max
   */
  public int getAlter_max() {
    return alter_max;
  }

  /**
   * Setzen des Maximalalters
   *
   * @param alter_max
   */
  public void setAlter_max(int alter_max) {
    this.alter_max = alter_max;
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
   * Auslesen des Parameters Raucher
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
   * Auslesen des Namens
   *
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * Setzen des Namens
   *
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Auslesen des Parameters Text
   *
   * @return text
   */
  public String getText() {
    return text;
  }

  /**
   * Setzen des Parameters Text
   *
   * @param text
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * Auslesen der Profil-ID
   *
   * @return profilId
   */
  public int getProfilId() {
    return profilId;
  }

  /**
   * Setzen der Profil-ID
   *
   * @param profilId
   */
  public void setProfilId(int profilId) {
    this.profilId = profilId;
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
   * Erzeugen einer ganzen Zahl, die das Suchprofil-Objekt charakterisiert.
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = (prime * result) + ((name == null) ? 0 : name.hashCode());
    result = (prime * result) + profilId;
    return result;
  }

  /**
   * Feststellen der inhaltlichen Gleichheit zweier Suchprofil-Objekte anhand der eindeutigen
   * Profil-ID eines Suchprofils.
   *
   * @see java.lang.Object#equals(Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean result = false;
    if ((object == null) || (object.getClass() != this.getClass())) {
      result = false;
    } else {
      Suchprofil sp = (Suchprofil) object;
      if ((getName() == sp.getName()) && (getProfilId() == sp.getProfilId())) {
        result = true;
      }
    }
    return result;
  }

  /**
   * Setzen des Geschlechts
   *
   * @param geschlecht
   */
  public void setGeschlecht(String geschlecht) {
    this.geschlecht = geschlecht;
  }

  /**
   * Auslesen der Auswahlliste der gesuchten Profile anhand eines bestimmten Suchprofils
   *
   * @return auswahlListe
   */
  public HashMap<Integer, String> getAuswahlListe() {
    return auswahlListe;
  }

  /**
   * Setzen der Auswhalliste der gesuchten Profile anhand eines bestimmten Suchprofils
   *
   * @param auswahlListe
   */
  public void setAuswahlListe(HashMap<Integer, String> auswahlListe) {
    this.auswahlListe = auswahlListe;
  }

}
