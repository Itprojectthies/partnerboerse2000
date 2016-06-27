package de.superteam2000.gwt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.shared.bo.Auswahl;

/**
 * Klasse, die die Aufgabe erf�llt, die Objekte einer persistenten Klasse auf die Datenbank
 * abzubilden und dort zu speichern. Die zu speichernden Objekte werden dematerialisiert und zu
 * gewinnende Objekte aus der Datenbank entsprechend materialisiert. Dies wird als indirektes
 * Mapping bezeichnet. Zur Verwaltung der Objekte implementiert die Mapper-Klasse entsprechende
 * Methoden zur Suche, zum Speichern, L�schen und Modifizieren von Objekten.
 *
 * @see AehnlichkeitsMapper
 * @see BeschreibungsMapper
 * @see DBConnection
 * @see InfoMapper
 * @see KontaktsperreMapper
 * @see MerkzettelMapper
 * @see ProfilMapper
 * @see SuchprofilMapper
 *
 * @author
 */

public class AuswahlMapper {


  /**
   * Von der Klasse AuswahlMapper kann nur eine Instanz erzeugt werden. Sie erf�llt die
   * Singleton-Eigenschaft. Dies geschieht mittels eines private default-Konstruktors und genau
   * einer statischen Variablen vom Typ AuswahlMapper, die die einzige Instanz der Klasse darstellt.
   *
   *
   */
  private static AuswahlMapper AuswahlMapper = null;

  /**
   * Durch den Modifier "private" gesch�tzter Konstruktor, der verhindert das weiter Instanzen der
   * Klasse erzeugt werden k�nnen
   *
   */
  protected AuswahlMapper() {}

  /**
   * Von der Klasse AuswahlMapper kann nur eine Instanz erzeugt werden. Sie erf�llt die
   * Singleton-Eigenschaft. Dies geschieht mittels eines private default-Konstruktors und genau
   * einer statischen Variablen vom Typ AuswahlMapper, die die einzige Instanz der Klasse darstellt.
   */
  public static AuswahlMapper auswahlMapper() {
    if (AuswahlMapper == null) {
      AuswahlMapper = new AuswahlMapper();
    }

    return AuswahlMapper;

  }

  /**
   * Die Methode findByName erf�llt eine Suchfunktion und liefert Objekte des Typs Auswahl aus der
   * Datenbank zur�ck
   *
   * @param name
   * @return Auswahl - Ein Auswahl-Objekt in dem Informationen des Objekts Auswahl aus der Datenbank
   *         gespeichert werden
   */

  public Auswahl findByName(String name) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();
      Statement stmt2 = con.createStatement();

      ResultSet rs1 =
          stmt.executeQuery("SELECT id, Name, Beschreibungstext FROM Eigenschaft WHERE Name=\""
              + name + "\" AND e_typ='p_a'");


      if (rs1.next()) {
        Auswahl a = new Auswahl();
        a.setId(rs1.getInt("id"));
        a.setName(rs1.getString("Name"));
        a.setBeschreibungstext(rs1.getString("Beschreibungstext"));
        ResultSet rs2 =
            stmt2.executeQuery("SELECT Text FROM Alternative WHERE Auswahl_id=" + rs1.getInt("id"));
        ArrayList<String> al = new ArrayList<String>();
        while (rs2.next()) {
          al.add(rs2.getString("Text"));
        }
        a.setAlternativen(al);

        return a;
      }
    } catch (SQLException a) {
      a.printStackTrace();
      return null;
    }

    return null;
  }

  /**
   * Die Methode findByKey implementiert die Suche nach genau einer id aus der Datenbank,
   * entsprechend wird genau ein Objekt zur�ckgegeben.
   *
   * @param id
   *
   * @return Auswahl-Objekt, das der �bergegebenen id entspricht bzw. null bei nicht vorhandenem
   *         DB-Tupel.
   */
  public Auswahl findByKey(int id) {
    // DB-Verbindung holen
    Connection con = DBConnection.connection();

    try {
      // Leeres SQL-Statement (JDBC) anlegen
      Statement stmt = con.createStatement();
      Statement stmt2 = con.createStatement();

      // Statement ausfüllen und als Query an die DB schicken
      ResultSet rs1 = stmt.executeQuery(
          "SELECT id, Name, Beschreibungstext FROM Eigenschaft WHERE id=" + id + " AND e_typ='a'");

      /*
       * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben werden. Prüfe, ob ein
       * Ergebnis vorliegt.
       */

      if (rs1.next()) {
        // Ergebnis-Tupel in Objekt umwandeln
        Auswahl a = new Auswahl();
        a.setId(rs1.getInt("id"));
        a.setName(rs1.getString("Name"));
        a.setBeschreibungstext(rs1.getString("Beschreibungstext"));
        ResultSet rs2 =
            stmt2.executeQuery("SELECT Text FROM Alternative WHERE Auswahl_id=" + rs1.getInt("id"));
        ArrayList<String> al = new ArrayList<String>();
        while (rs2.next()) {
          al.add(rs2.getString("Text"));
        }
        a.setAlternativen(al);

        return a;
      }
    } catch (SQLException a) {
      a.printStackTrace();
      return null;
    }

    return null;
  }

  /**
   * Auslesen aller Auswahl-Tupel.
   *
   * @return Eine ArrayList mit Auswahl-Objekten
   */
  public ArrayList<Auswahl> findAll() {
    Connection con = DBConnection.connection();
    ArrayList<Auswahl> result = new ArrayList<Auswahl>();

    try {
      Statement stmt1 = con.createStatement();
      ResultSet rs1 = stmt1
          .executeQuery("SELECT id, Name, Beschreibungstext" + " FROM Eigenschaft WHERE e_typ='a'");

      Statement stmt2 = con.createStatement();



      while (rs1.next()) {
        Auswahl a = new Auswahl();
        a.setId(rs1.getInt("id"));
        a.setName(rs1.getString("Name"));
        a.setBeschreibungstext(rs1.getString("Beschreibungstext"));
        ResultSet rs2 = stmt2
            .executeQuery("SELECT Text FROM Alternative WHERE" + " Auswahl_id=" + rs1.getInt("id"));
        ArrayList<String> al = new ArrayList<String>();
        while (rs2.next()) {
          al.add(rs2.getString("Text"));
        }
        a.setAlternativen(al);
        // Hinzufügen des neuen Objekts zum Ergebnisvektor
        result.add(a);

      }

    } catch (SQLException e) {
      e.printStackTrace();
      ClientsideSettings.getLogger()
          .severe("Fehler beim schreiben in die DB" + e.getMessage() + " " + e.getCause() + " ");
    }

    return result;
  }

  /**
   * Auslesen aller Auswahl-Tupel.
   *
   * @return Eine ArrayList mit Auswahl-Objekten, bei einem Fehler wird eine SQL-Exception ausgel�st
   */
  public ArrayList<Auswahl> findAllProfilAtrribute() {
    Connection con = DBConnection.connection();
    // Ergebnisvektor vorbereiten
    ArrayList<Auswahl> result = new ArrayList<Auswahl>();

    try {
      Statement stmt1 = con.createStatement();
      ResultSet rs1 = stmt1.executeQuery(
          "SELECT id, Name, Beschreibungstext" + " FROM Eigenschaft WHERE e_typ='p_a'");

      Statement stmt2 = con.createStatement();

      while (rs1.next()) {
        Auswahl a = new Auswahl();
        a.setId(rs1.getInt("id"));
        a.setName(rs1.getString("Name"));
        a.setBeschreibungstext(rs1.getString("Beschreibungstext"));
        ResultSet rs2 = stmt2
            .executeQuery("SELECT Text FROM Alternative " + "WHERE Auswahl_id=" + rs1.getInt("id"));
        ArrayList<String> al = new ArrayList<String>();
        while (rs2.next()) {
          al.add(rs2.getString("Text"));
        }
        a.setAlternativen(al);
        result.add(a);

      }

    } catch (SQLException e) {
      e.printStackTrace();
      ClientsideSettings.getLogger()
          .severe("Fehler beim schreiben in die DB" + e.getMessage() + " " + e.getCause() + " ");
    }

    return result;
  }

  /**
   * Hinzuf�gen eines Auswahl-Objekts in die Datenbank.
   *
   * @param a - das zu speichernde Objekt
   *
   * @return das an die Datenbank �bergebene Objekt
   */
  public Auswahl insert(Auswahl a) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();


      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM Eigenschaft ");

      if (rs.next()) {

        a.setId(rs.getInt("maxid") + 1);

        stmt = con.createStatement();

        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
        stmt.executeUpdate(
            "INSERT INTO Eigenschaft (id, Name, " + "Beschreibungstext, e_typ) VALUES (" + a.getId()
                + ",'" + a.getName() + "','" + a.getBeschreibungstext() + "','a')");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return a;
  }

  /**
   * Die Methode update modifiziert ein auf die Datenbank abgebildetes Auswahl-Objekt.
   *
   * @param a - das Objekt, welches in der Datenbank ge�ndert wird
   * @return das als Parameter �bergebene Objekt
   */
  public Auswahl update(Auswahl a) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("UPDATE Eigenschaft SET Name='" + a.getName() + "', Beschreibungstext='"
          + a.getBeschreibungstext() + "', e_typ='a' WHERE id=" + a.getId());

    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Um Analogie zu insert(Auswahl a) zu wahren, geben wir a zurück
    return a;
  }

  /**
   * L�schen eines auf die Datenbank abgebildeteten Auswahl-Objekts
   *
   * @param a das aus der DB zu l�schende Objekt
   */
  public void delete(Auswahl a) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM Eigenschaft " + "WHERE id=" + a.getId());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
