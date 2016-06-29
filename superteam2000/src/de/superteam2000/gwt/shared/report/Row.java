package de.superteam2000.gwt.shared.report;

import java.io.Serializable;
import java.util.Vector;

/**
 * Zeile einer Tabelle eines SimpleReport-Objekts. 
 *
 * @author Thies, Volz, Funke
 */
public class Row implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  /**
   * Speicherplatz für die Spalten der Zeile.
   */
  private Vector<Column> columns = new Vector<Column>();

  /**
   * Hinzufügen einer Spalte.
   *
   * @param c das Spaltenobjekt
   */
  public void addColumn(Column c) {
    columns.addElement(c);
  }

  /**
   * Entfernen einer benannten Spalte
   *
   * @param c das zu entfernende Spaltenobjekt
   */
  public void removeColumn(Column c) {
    columns.removeElement(c);
  }

  /**
   * Auslesen sämtlicher Spalten.
   *
   * @return Vector-Objekts mit sämtlichen Spalten
   */
  public Vector<Column> getColumns() {
    return columns;
  }

  /**
   * Auslesen der Anzahl sämtlicher Spalten.
   *
   * @return int Anzahl der Spalten
   */
  public int getNumColumns() {
    return columns.size();
  }

  /**
   * Auslesen eines einzelnen Spalten-Objekts.
   *
   * @param i 
   * @return das gewünschte Spaltenobjekt.
   */
  public Column getColumnAt(int i) {
    return columns.elementAt(i);
  }
}
