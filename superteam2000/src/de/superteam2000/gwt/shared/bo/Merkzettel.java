package de.superteam2000.gwt.shared.bo;

import java.util.ArrayList;

/**
 * Die Klasse Kontaktsperre wird benötigt, um zu realisieren, dass
 * Nutzer andere Nutzer merken können.
 * Sie enthält eine ArrayList an Profil (gemerkte Profile)
 * Außerdem eine merker und gemerkter ID
 *
 * @author Funke, Volz
 */
public class Merkzettel extends BusinessObject {

  private static final long serialVersionUID = 1L;

  /**
   * Deklaration der Parameter eines Merkzettels
   *
   * @param gemerkteProfile Dieser Parameter ist eine ArrayList vom Typ Profil. In dieser ArrayList
   *        werden alle gemerkten Profile gespeichert.
   * @param gemerkterId Die Id des Profils, welches gemerkt wurde.
   * @param merkerID Die Id des Profils, dass sich andere Profile merkt.
   */
  private ArrayList<Profil> gemerkteProfile;
  private int gemerkterId = 0;
  private int merkerId = 0;


  /**
   * Aulesen der MerkerId
   *
   * @return merkerId
   */
  public int getMerkerId() {
    return merkerId;
  }

  /**
   * Setzen der MerkerId
   *
   * @param merkerId
   */
  public void setMerkerId(int merkerId) {
    this.merkerId = merkerId;
  }

  /**
   * Auslesen der gemerkten Profile
   *
   * @return gemerkteProfile
   */
  public ArrayList<Profil> getGemerkteProfile() {
    return gemerkteProfile;
  }


  /**
   * Setzen der gemerkten Profile
   *
   * @param gemerkteProfile
   */
  public void setGemerkteProfile(ArrayList<Profil> gemerkteProfile) {
    this.gemerkteProfile = gemerkteProfile;
  }

  /**
   * Ein neues gemerktes Profil hinzufügen
   *
   * @param p
   */
  public void addGemerktesProfil(Profil p) {
    gemerkteProfile.add(p);
  }

  /**
   * Auslesen der Gemerkter-Id
   *
   * @return gemerkterId
   */
  public int getGemerkterId() {
    return gemerkterId;
  }

  /**
   * Setzen der Gemerkter-Id
   *
   * @param gemerkterId
   */
  public void setGemerkterId(int gemerkterId) {
    this.gemerkterId = gemerkterId;
  }

}
