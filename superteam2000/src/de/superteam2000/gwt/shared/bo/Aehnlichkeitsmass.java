package de.superteam2000.gwt.shared.bo;

import java.util.ArrayList;

/**
 * Hier wird ein exemplarisches Ähnlichkeitsmass eines Users realisiert. Anhand diesem kann
 * berechnet werden, wie ähnlich sich das Profil eines User mit den anderen Profilen der
 * Partnerbörse sind.
 *
 * @author Christopher Funke, Daniel Volz
 * @version 1.0
 */

public class Aehnlichkeitsmass extends BusinessObject {

  private static final long serialVersionUID = 1L;

  /**
   * Deklaration des Parameters aehnlichkeitsmass
   *
   * @param aehnlichkeitsmass Dieser Parameter gibt in % an, wie ähnlich sich zwei Profile sind
   */
  private float aehnlichkeitsmass = 0;

  /**
   * Auslesen des Ähnlichkeitsmasses
   *
   * @return aehnlichkeitsmass
   */
  public float getAehnlichkeitsmass() {
    return aehnlichkeitsmass;
  }

  /**
   * Setzen des Ähnlichkeitsmasses
   *
   * @param aehnlichkeitsmass
   */
  public void setAehnlichkeitsmass(int aehnlichkeitsmass) {
    this.aehnlichkeitsmass = aehnlichkeitsmass;
  }

  /**
   * Berechnung des Ähnlichkeitsmaßes Das berechnete Ähnlichkeitsmaß zweier Profil wird in einer
   * ArrayList vom Typ Profil gespeichert.
   *
   * @param p
   * @param referenz
   * @return null
   */
  public ArrayList<Profil> aehnlichkeitsmassBerechnen(Profil p, Profil referenz) {
    return null;
  }

}
