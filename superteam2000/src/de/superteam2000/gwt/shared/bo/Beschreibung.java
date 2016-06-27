package de.superteam2000.gwt.shared.bo;

/**
 * Hier wird eine exemplarische Auswahl für einen User realisiert. Ein User hat die Möglichkeit, bei
 * Eigenschaften durch eine Auswahl von vorgegebenen Werten eine Antwort zu treffen.
 *
 * @author Christopher Funke, Daniel Volz
 * @version 1.0
 */
public class Beschreibung extends Eigenschaft {

  private static final long serialVersionUID = 1L;
  /**
   * Deklaration des Parameters alternativen
   *
   * @param alternativen Dieser Parameter ist eine ArrayList vom Typ String. In dieser ArrayList
   *        werden alle vorgegebenen Werte für eine Eigenschaft gespeichert.
   */
  private String text = "";

  /**
   * Auslesen der Alternativen
   *
   * @return alternativen
   */
  public String getText() {
    return text;
  }

  /**
   * Setzen der Alternativen
   *
   * @param alternativen
   */
  public void setText(String text) {
    this.text = text;
  }

}
