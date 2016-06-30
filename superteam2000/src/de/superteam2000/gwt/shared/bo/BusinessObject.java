package de.superteam2000.gwt.shared.bo;

import java.io.Serializable;

/**
 * Die Klasse BusinessObject stellt die Basisklasse aller in diesem Projekt für die
 * Umsetzung des Fachkonzepts relevanten Klassen dar.
 * Zentrales Merkmal ist, dass jedes BusinessObject eine Nummer besitzt, die man in
 * einer relationalen Datenbank auch als Primärschlüssel bezeichnen würde. Fernen ist jedes
 * BusinessObject als link Serializable gekennzeichnet. Durch diese Eigenschaft kann
 * jedes BusinessObject automatisch in eine textuelle Form überführt und z.B. zwischen
 * Client und Server transportiert werden.
 * @author thies
 * @version 1.0
 */
public abstract class BusinessObject implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Die eindeutige Identifikationsnummer einer Instanz dieser Klasse.
   */
  private int id = 0;

  /**
   * Auslesen der ID.
   */
  public int getId() {
    return id;
  }

  /**
   * Setzen der ID
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Erzeugen einer einfachen textuellen Darstellung der jeweiligen Instanz.
   */
  @Override
  public String toString() {
    /*
     * Wir geben den Klassennamen gefolgt von der ID des Objekts zurück.
     */
    return this.getClass().getName() + " #" + id;
  }

  /**
   * Feststellen der inhaltlichen Gleichheit zweier BusinessObject-Objekte.
   * Die Gleichheit wird in diesem Beispiel auf eine identische ID beschränkt.
   */
  @Override
  public boolean equals(Object o) {
    /*
     * Abfragen, ob ein Objekt ungleich NULL ist und ob ein Objekt gecastet werden kann, sind immer
     * wichtig!
     */
    if ((o != null) && (o instanceof BusinessObject)) {
      BusinessObject bo = (BusinessObject) o;
      try {
        if (bo.getId() == id)
          return true;
      } catch (IllegalArgumentException e) {
        /*
         * Wenn irgendetwas schief geht, dann geben wir sicherheitshalber false zurück.
         */
        return false;
      }
    }
    /*
     * Wenn bislang keine Gleichheit bestimmt werden konnte, dann müssen schließlich false
     * zurückgeben.
     */
    return false;
  }

  /**
   * Erzeugen einer ganzen Zahl, die für das <code>BusinessObject</code> charakteristisch ist. 
   */
  @Override
  public int hashCode() {
    return id;
  }

}
