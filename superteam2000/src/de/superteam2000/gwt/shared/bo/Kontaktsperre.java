package de.superteam2000.gwt.shared.bo;

import java.util.ArrayList;

/**
 * Hier wird eine exemplarische Kontaktperre eines Users realisiert. Ein User kann sich Profile, die
 * ihm nicht gefallen, sperren lassen. Die gesperrten Profile können durch die Sperrliste angezeigt
 * werden.
 *
 * @author Christopher Funke, Daniel Volz
 * @version 1.0
 */
public class Kontaktsperre extends BusinessObject {

  private static final long serialVersionUID = 1L;
  /**
   * Deklaration der Parameter einer Kontaktsperre
   * 
   * @param gesperrteProfile Dieser Parameter ist eine ArrayList vom Typ Profil. In dieser ArrayList
   *        werden alle gesperrte Profile gespeichert.
   * @param gesperrterId Die Id des gesperrten Profils.
   * @param sperrerId Die Id des Profils, welche ein anderes Profil sperrt.
   */
  private ArrayList<Profil> gesperrteProfile;

  private int gesperrterId = 0;
  private int sperrerId = 0;

  /**
   * Auslesen der Gesperrter-Id
   * 
   * @return gesperrterId
   */
  public int getGesperrterId() {
    return this.gesperrterId;
  }

  /**
   * Setzen der Gesperrter-Id
   * 
   * @param gesperrterId
   */
  public void setGesperrterId(int gesperrterId) {
    this.gesperrterId = gesperrterId;
  }

  /**
   * Auslesen der Sperrer-Id
   * 
   * @return sperrerId
   */
  public int getSperrerId() {
    return this.sperrerId;
  }

  /**
   * Setzen der Sperrer-Id
   * 
   * @param sperrerId
   */
  public void setSperrerId(int sperrerId) {
    this.sperrerId = sperrerId;
  }

  /**
   * Auslesen aller gesperrten Profile
   * 
   * @return gesperrteProfile
   */
  public ArrayList<Profil> getGesperrteProfile() {
    return this.gesperrteProfile;
  }

  /**
   * Setzen der gesperrten Profile
   * 
   * @param gesperrteProfile
   */
  public void setGesperrteProfile(ArrayList<Profil> gesperrteProfile) {
    this.gesperrteProfile = gesperrteProfile;
  }

  /**
   * Ein neues gesperrtes Profil hinzufügen
   * 
   * @param p
   */
  public void addGesperrtesProfil(Profil p) {
    this.gesperrteProfile.add(p);
  }

}
