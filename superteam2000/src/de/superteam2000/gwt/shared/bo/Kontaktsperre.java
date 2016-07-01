package de.superteam2000.gwt.shared.bo;

import java.util.ArrayList;

/**
 * Die Klasse Kontaktsperre wird benötigt, um zu realisieren, dass
 * Nutzer andere Nutzer sperren können.
 * Sie enthält eine ArrayList an Profil (gesperrte Profile)
 * Außerdem eine sperrer und gesperrter ID
 *
 * @author Funke, Volz
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
    return gesperrterId;
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
    return sperrerId;
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
    return gesperrteProfile;
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
    gesperrteProfile.add(p);
  }

}
