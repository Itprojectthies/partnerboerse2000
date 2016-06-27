package de.superteam2000.gwt.shared.bo;

import java.util.ArrayList;

/**
 * Hier wird eine exemplarische Auswahl für einen User realisiert. Ein User hat die Möglichkeit, bei
 * Eigenschaften durch eine Auswahl von vorgegebenen Werten eine Antwort zu treffen.
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
    return this.alternativen;
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
