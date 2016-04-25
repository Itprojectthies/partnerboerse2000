package de.superteam2000.gwt.server.db;

import java.sql.*;
import java.util.Vector;

import de.superteam2000.gwt.shared.bo.*;


/**
 * Mapper-Klasse, die <code>Eigenschaft</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * <p>
 * 
 * 
 * @see 
 * @author Thies
 */

public class EigenschaftMapper {

  /**
   * Die Klasse EigenschaftMapper wird nur einmal instantiiert. Man spricht hierbei
   * von einem sogenannten <b>Singleton</b>.
   * <p>
   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
   * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
   * einzige Instanz dieser Klasse.
   * 
   */
  private static EigenschaftMapper eigenschaftMapper = null;

  /**
   * Geschützter Konstruktor - verhindert die Möglichkeit, mit new neue
   * Instanzen dieser Klasse zu erzeugen.
   * 
   */
  protected EigenschaftMapper() {
  }

  /**
   * Diese statische Methode kann aufgrufen werden durch
   * <code>EigenschaftMapper.EigenschaftMapper()</code>. Sie stellt die
   * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
   * Instanz von <code>EigenschaftMapper</code> existiert.
   * <p>
   * 
   * <b>Fazit:</b> EigenschaftMapper sollte nicht mittels <code>new</code>
   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
   * 
   * @return DAS <code>EigenschaftMapper</code>-Objekt.
   * @see EigenschaftMapper
   */
  public static EigenschaftMapper eigenschaftMapper() {
    if (eigenschaftMapper == null) {
      eigenschaftMapper = new EigenschaftMapper();
    }

    return eigenschaftMapper;
  }

  /**
   * Suchen eines Kunden mit vorgegebener Kundennummer. Da diese eindeutig ist,
   * wird genau ein Objekt zur�ckgegeben.
   * 
   * @param id Primärschlüsselattribut (->DB)
   * @return Kunden-Objekt, das dem übergebenen Schlüssel entspricht, null bei
   *         nicht vorhandenem DB-Tupel.
   */
//  public Eigenschaft findByKey(int id) {
//    // DB-Verbindung holen
//    Connection con = DBConnection.connection();
//
//    try {
//      // Leeres SQL-Statement (JDBC) anlegen
//      Statement stmt = con.createStatement();
//
//      // Statement ausfüllen und als Query an die DB schicken
//      ResultSet rs = stmt
//          .executeQuery("SELECT id, firstName, lastName FROM Profils "
//              + "WHERE id=" + id + " ORDER BY lastName");
//
//      /*
//       * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
//       * werden. Prüfe, ob ein Ergebnis vorliegt.
//       */
//      if (rs.next()) {
//        // Ergebnis-Tupel in Objekt umwandeln
//        Eigenschaft e = new Eigenschaft();
//        e.setId(rs.getInt("id"));
//        e.setFirstName(rs.getString("firstName"));
//        e.setLastName(rs.getString("lastName"));
//
//        return e;
//      }
//    }
//    catch (SQLException e) {
//      e.printStackTrace();
//      return null;
//    }
//
//    return null;
//  }
//
//  /**
//   * Auslesen aller Kunden.
//   * 
//   * @return Ein Vektor mit Eigenschaft-Objekten, die sämtliche Kunden
//   *         repräsentieren. Bei evtl. Exceptions wird ein partiell gef�llter
//   *         oder ggf. auch leerer Vetor zurückgeliefert.
//   */
//  public Vector<Eigenschaft> findAll() {
//    Connection con = DBConnection.connection();
//    // Ergebnisvektor vorbereiten
//    Vector<Eigenschaft> result = new Vector<Eigenschaft>();
//
//    try {
//      Statement stmt = con.createStatement();
//
//      ResultSet rs = stmt.executeQuery("SELECT id, firstName, lastName "
//          + "FROM Profils " + "ORDER BY lastName");
//
//      // Für jeden Eintrag im Suchergebnis wird nun ein Eigenschaft-Objekt
//      // erstellt.
//      while (rs.next()) {
//        Eigenschaft e = new Eigenschaft();
//        e.setId(rs.getInt("id"));
//        e.setFirstName(rs.getString("firstName"));
//        e.setLastName(rs.getString("lastName"));
//
//        // Hinzufügen des neuen Objekts zum Ergebnisvektor
//        result.addElement(e);
//      }
//    }
//    catch (SQLException e) {
//      e.printStackTrace();
//    }
//
//    // Ergebnisvektor zurückgeben
//    return result;
//  }
//
//
//  /**
//   * Einfügen eines <code>Eigenschaft</code>-Objekts in die Datenbank. Dabei wird
//   * auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
//   * berichtigt.
//   * 
//   * @param e das zu speichernde Objekt
//   * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter
//   *         <code>id</code>.
//   */
//  public Eigenschaft insert(Eigenschaft e) {
//    Connection con = DBConnection.connection();
//
//    try {
//      Statement stmt = con.createStatement();
//
//      /*
//       * Zunächst schauen wir nach, welches der momentan höchste
//       * Primärschlüsselwert ist.
//       */
//      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
//          + "FROM Profils ");
//
//      // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
//      if (rs.next()) {
//        /*
//         * e erhält den bisher maximalen, nun um 1 inkrementierten
//         * Primärschlüssel.
//         */
//        e.setId(rs.getInt("maxid") + 1);
//
//        stmt = con.createStatement();
//
//        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
//        stmt.executeUpdate("INSERT INTO Profils (id, firstName, lastName) "
//            + "VALUES (" + e.getId() + ",'" + e.getFirstName() + "','"
//            + e.getLastName() + "')");
//      }
//    }
//    catch (SQLException e) {
//      e.printStackTrace();
//    }
//
//    /*
//     * Rückgabe, des evtl. korrigierten Profils.
//     * 
//     * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen
//     * Objekte übergeben werden, wäre die Anpassung des Eigenschaft-Objekts auch
//     * ohne diese explizite Rückgabe außerhalb dieser Methode sichtbar. Die
//     * explizite Rückgabe von e ist eher ein Stilmittel, um zu signalisieren,
//     * dass sich das Objekt evtl. im Laufe der Methode verändert hat.
//     */
//    return e;
//  }
//
//  /**
//   * Wiederholtes Schreiben eines Objekts in die Datenbank.
//   * 
//   * @param e das Objekt, das in die DB geschrieben werden soll
//   * @return das als Parameter übergebene Objekt
//   */
//  public Eigenschaft update(Eigenschaft e) {
//    Connection con = DBConnection.connection();
//
//    try {
//      Statement stmt = con.createStatement();
//
//      stmt.executeUpdate("UPDATE Profils " + "SET firstName=\""
//          + e.getFirstName() + "\", " + "lastName=\"" + e.getLastName() + "\" "
//          + "WHERE id=" + e.getId());
//
//    }
//    catch (SQLException e) {
//      e.printStackTrace();
//    }
//
//    // Um Analogie zu insert(Eigenschaft e) zu wahren, geben wir e zurück
//    return e;
//  }
//
//  /**
//   * Löschen der Daten eines <code>Eigenschaft</code>-Objekts aus der Datenbank.
//   * 
//   * @param e das aus der DB zu löschende "Objekt"
//   */
//  public void delete(Eigenschaft e) {
//    Connection con = DBConnection.connection();
//
//    try {
//      Statement stmt = con.createStatement();
//
//      stmt.executeUpdate("DELETE FROM Profils " + "WHERE id=" + e.getId());
//    }
//    catch (SQLException e) {
//      e.printStackTrace();
//    }
//  }


}
