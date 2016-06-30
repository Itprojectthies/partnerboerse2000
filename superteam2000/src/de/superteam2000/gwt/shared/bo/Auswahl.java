package de.superteam2000.gwt.shared.bo;

import java.util.ArrayList;

/**
 * Die Klasse Auswahl erweitert die Klasse Eigenschaft.
 * sie enthält eine Reihe von Auswahlalternativen
 * vom Typ String.
 *
 * @author Christopher Funke, Daniel Volz
 * @version 1.0
 */
public class Auswahl extends Eigenschaft {

  private static final long serialVersionUID = 1L;

  /**
   * Deklaration des Parameters alternativen
   *
   * @param alternativen Dieser Parameter ist eine ArrayList vom Typ String. In dieser ArrayList
   *        werden alle vorgegebenen Werte für eine Eigenschaft gespeichert.
   */
  private ArrayList<String> alternativen;

  /**
   * Auslesen der Alternativen
   *
   * @return alternativen
   */
  public ArrayList<String> getAlternativen() {
    return alternativen;
  }

  /**
   * Setzen der Alternativen
   *
   * @param alternativen
   */
  public void setAlternativen(ArrayList<String> alternativen) {
    this.alternativen = alternativen;
  }

}
