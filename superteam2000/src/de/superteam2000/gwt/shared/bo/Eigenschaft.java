package de.superteam2000.gwt.shared.bo;

/**
 * Hier wird eine exemplarisches Eigenschaftsobjekt eines Profils realisiert. Ein Eigenschaftsobjekt
 * stellt ein Hobby eines Users dar. Das Eigenschaftsobjekt besteht aus einem Namen und einem
 * Beschreibungstext
 *
 * @author Christopher Funke, Daniel Volz
 * @version 1.0
 */
public abstract class Eigenschaft extends BusinessObject {

  private static final long serialVersionUID = 1L;
  /**
   * Deklaration der Parameter
   * 
   * @param name Überbegriff, d. h. Begriffsbezeichnung eines Hobbies
   * @param beschreibungstext Der eigentliche Name eines Hobbies
   */
  private String name = "";
  private String beschreibungstext = "";

  /**
   * Auslesen des Parameters Name
   * 
   * @return name
   */
  public String getName() {
    return this.name;
  }


  /**
   * Setzen des Parameters Name
   * 
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Auslesen des Beschreibungstextes
   * 
   * @return beschreibungstext
   */
  public String getBeschreibungstext() {
    return this.beschreibungstext;
  }

  /**
   * Setzen des Beschreibungstextes
   * 
   * @param beschreibungstext
   */
  public void setBeschreibungstext(String beschreibungstext) {
    this.beschreibungstext = beschreibungstext;
  }

}
