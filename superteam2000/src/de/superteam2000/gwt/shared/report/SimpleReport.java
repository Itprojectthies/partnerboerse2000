package de.superteam2000.gwt.shared.report;

import java.util.Vector;

/**
 * Ein einfacher Report, der neben den Informationen der Superklasse
 * Report eine Tabelle mit "Attributen" aufweist. Die Tabelle greift auf zwei
 * Hilfsklassen namens Row und Column zurück.
 * Die Positionsdaten sind vergleichbar mit der Liste der Bestellpositionen eines Bestellscheins.
 * Dort werden in eine Tabelle zeilenweise Eintragung z.B. bzgl. Artikelnummer, Artikelbezeichnung,
 * Menge, Preis vorgenommen.
 *
 * @author Thies
 */
public abstract class SimpleReport extends Report {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Tabelle mit Attributen. Die Tabelle wird zeilenweise in diesem Vector
   * abgelegt.
   */
  private Vector<Row> table = new Vector<Row>();

  /**
   * Hinzufügen einer Zeile.
   *
   * @param r die hinzuzufügende Zeile
   */
  public void addRow(Row r) {
    table.addElement(r);
  }

  /**
   * Entfernen einer Zeile.
   *
   * @param r die zu entfernende Zeile.
   */
  public void removeRow(Row r) {
    table.removeElement(r);
  }

  /**
   * Auslesen sämtlicher Positionsdaten.
   *
   * @return die Tabelle der Positionsdaten
   */
  public Vector<Row> getRows() {
    return table;
  }
}
