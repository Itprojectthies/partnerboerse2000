package de.superteam2000.gwt.shared.report;

import java.io.Serializable;
import java.util.Date;

import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Basisklasse aller Reports. Reports sind als <code>Serializable</code> deklariert, damit sie von
 * dem Server an den Client gesendet werden können.

 * Ein Report besitzt eine Reihe von Standardelementen. Sie werden mittels Attributen modelliert und
 * dort dokumentiert.
 *
 * @see Report
 * @author Thies, Volz, Funke
 */
public abstract class Report implements Serializable {


  private static final long serialVersionUID = 1L;

  private Profil profil = null;
  private Paragraph name = null;
  
  /**
   * ein Composite Paragraph für die Profil Attribute
   */
  private CompositeParagraph attribute = null;
  
  /**
   * ein Composite Paragraph für die Bezeichnungen der Profil Attribute
   */
  private CompositeParagraph attributeBez = null;

  /**
   * ein Paragraph für die Ähnlichkeit
   */
  private Paragraph aehnlichkeit = null;

  /**
   * Titel des Reports
   */
  private String title = "Report";
  /**
   * Unterüberschrift des Reports
   */
  private String subtitle = "";
  /**
   * Datum der Erstellung des Reports
   */
  private Date created = new Date();

  
  public Paragraph getName() {
    return name;
  }

  public void setName(Paragraph name) {
    this.name = name;
  }

  /**
   * Setzen der Profilattribute
   *
   * @param attribute Text dbuteer Attri
   */
  public void setProfilAttribute(CompositeParagraph attribute) {
    this.attribute = attribute;
  }

  public CompositeParagraph getProfilAttribute() {
    return attribute;
  }

  public void setProfilAttributeBez(CompositeParagraph attributeBez) {
    this.attributeBez = attributeBez;
  }

  public CompositeParagraph getProfilAttributeBez() {
    return attributeBez;
  }

  /**
   * Auslesen der Ähnlichkeit.
   *
   * @return Ähnlichkeit.
   */
  public Paragraph getAehnlichkeit() {
    return aehnlichkeit;
  }

  /**
   * Setzen der Ähnlichkeit.
   *
   * @param aehnlichkeit 
   */
  public void setAehnlichekit(Paragraph aehnlichkeit) {
    this.aehnlichkeit = aehnlichkeit;
  }

  /**
   * Auslesen des Titels.
   *
   * @return Titeltext
   */
  public String getTitle() {
    return title;
  }
  /**
   * Setzen des Titels.
   *
   * @param title Titeltext
   */
  public void setTitle(String title) {
	  this.title = title;
  }

  /**
   * Setzen der Unterüberschrift.
   *
   * @param title Titeltext
   */
  public void setSubTitle(String subtitle) {
    this.subtitle = subtitle;
  }
  /**
   * Auslesen der Unterüberschrift
   *
   * @return Unterüberschrift
   */
  public String getSubTitle() {
    return subtitle;
  }


  /**
   * Auslesen des Erstellungsdatums.
   *
   * @return Datum der Erstellung des Reports
   */
  public Date getCreated() {
    return created;
  }

  /**
   * Setzen des Erstellungsdatums.
   *
   * @param created Zeitpunkt der Erstellung
   */
  public void setCreated(Date created) {
    this.created = created;
  }

  /**
   * gibt das gesetzte Profil zurück
   * @return Profil
   */
  public Profil getProfil() {
    return profil;
  }
/**
 * setzen des Profils
 * @param profil
 */
  public void setProfil(Profil profil) {
    this.profil = profil;
  }


}
