package de.superteam2000.gwt.shared.report;

import java.io.Serializable;

/**
 * Spalte eines Row-Objekts. 
 *
 * @author Thies
 */
public class Column implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
/**
 *hierdurch wird der no argument konstruktor überschrieben 
 */

  private String value = "";


  public Column() {}

  /**
   * Konstruktor, der die Angabe eines Werts (Spalteneintrag) erzwingt.
   *
   * @param s 
   */
  public Column(String s) {
    value = s;
  }

  /**
   * Auslesen des Spaltenwerts.
   *
   * @return der Eintrag als String
   */
  public String getValue() {
    return value;
  }

  /**
   * Überschreiben des aktuellen Spaltenwerts.
   *
   * @param value neuer Spaltenwert
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * Umwandeln des Column-Objekts in einen String.
   *
   */
  @Override
  public String toString() {
    return value;
  }
}
