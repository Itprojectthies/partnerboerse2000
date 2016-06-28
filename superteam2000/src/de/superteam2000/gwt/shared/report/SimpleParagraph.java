package de.superteam2000.gwt.shared.report;

import java.io.Serializable;

/**
 * Diese Klasse stellt einzelne Absätze dar. Der Absatzinhalt wird als String gespeichert.
 *
 * @author Thies
 */
public class SimpleParagraph extends Paragraph implements Serializable {

  /**
   * TODO
   */
  private static final long serialVersionUID = 1L;

  /**
   * Inhalt des Absatzes.
   */
  private String text = "";

/**
 * NO-Argument-Konstruktor von SimpleParagraph
 */
  public SimpleParagraph() {}

  /**
   * Dieser Konstruktor ermöglicht es, bereits bei Instantiierung von 
   * SimpleParagraph-Objekten deren Inhalt angeben zu können.
   *
   * @param value der Inhalt des Absatzes
   */
  public SimpleParagraph(String value) {
    text = value;
  }

  /**
   * Auslesen des Inhalts.
   *
   * @return Inhalt als String
   */
  public String getText() {
    return text;
  }

  /**
   * Überschreiben des Inhalts.
   *
   * @param text der neue Inhalt des Absatzes.
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * Umwandeln des SimpleParagraph-Objekts in einen String.
   */
  @Override
  public String toString() {
    return text;
  }
}
