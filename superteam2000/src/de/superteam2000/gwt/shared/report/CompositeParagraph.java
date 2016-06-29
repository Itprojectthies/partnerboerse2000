package de.superteam2000.gwt.shared.report;

import java.io.Serializable;
import java.util.Vector;

/**
 * Diese Klasse stellt eine Menge einzelner Absätze (SimpleParagraph-Objekte) dar.
 * Diese werden als Unterabschnitte in einem Vector abgelegt verwaltet.
 *
 * @author Thies
 */
public class CompositeParagraph extends Paragraph implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Speicherort der Unterabschnitte.
   */
  private Vector<SimpleParagraph> subParagraphs = new Vector<SimpleParagraph>();

  /**
   * Einen Unterabschnitt hinzufügen.
   *
   * @param p der hinzuzufügende Unterabschnitt.
   */
  public void addSubParagraph(SimpleParagraph p) {
    subParagraphs.addElement(p);
  }

  /**
   * Einen Unterabschnitt entfernen.
   *
   * @param p der zu entfernende Unterabschnitt.
   */
  public void removeSubParagraph(SimpleParagraph p) {
    subParagraphs.removeElement(p);
  }

  /**
   * Auslesen sämtlicher Unterabschnitte.
   *
   * @return Vector, der sämtliche Unterabschnitte enthält.
   */
  public Vector<SimpleParagraph> getSubParagraphs() {
    return subParagraphs;
  }

  /**
   * Auslesen der Anzahl der Unterabschnitte.
   *
   * @return Anzahl der Unterabschnitte
   */
  public int getNumParagraphs() {
    return subParagraphs.size();
  }

  /**
   * Auslesen eines einzelnen Unterabschnitts.
   *
   * @param i 
   *
   * @return der gewünschte Unterabschnitt.
   */
  public SimpleParagraph getParagraphAt(int i) {
    return subParagraphs.elementAt(i);
  }

  /**
   * Umwandeln eines CompositeParagraph in einen String.
   */
  @Override
  public String toString() {

    StringBuffer result = new StringBuffer();


    for (int i = 0; i < subParagraphs.size(); i++) {
      SimpleParagraph p = subParagraphs.elementAt(i);

      result.append(p.toString() + "<br>");
    }


    return result.toString();
  }
}
