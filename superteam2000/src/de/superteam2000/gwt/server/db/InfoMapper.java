package de.superteam2000.gwt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.shared.bo.Info;

/**
 * Klasse, die die Aufgabe erf�llt, die Objekte einer persistenten Klasse auf die Datenbank
 * abzubilden und dort zu speichern. Die zu speichernden Objekte werden dematerialisiert und zu
 * gewinnende Objekte aus der Datenbank entsprechend materialisiert. Dies wird als indirektes
 * Mapping bezeichnet. Zur Verwaltung der Objekte implementiert die Mapper-Klasse entsprechende
 * Methoden zur Suche, zum Speichern, L�schen und Modifizieren von Objekten.
 *
 * @see AehnlichkeitsMapper
 * @see AuswahlMapper
 * @see BeschreibungMapper
 * @see DBConnection
 * @see KontaktsperreMapper
 * @see MerkzettelMapper
 * @see ProfilMapper
 * @see SuchprofilMapper
 *
 * @author
 */

public class InfoMapper {

  /**
   * Von der Klasse InfoMapper kann nur eine Instanz erzeugt werden. Sie erf�llt die
   * Singleton-Eigenschaft. Dies geschieht mittels eines private default-Konstruktors und genau
   * einer statischen Variablen vom Typ InfoMapper, die die einzige Instanz der Klasse darstellt.
   *
   *
   */
  private static InfoMapper infoMapper = null;

  /**
   * Durch den Modifier "private" gesch�tzter Konstruktor, der verhindert das weiter Instanzen der
   * Klasse erzeugt werden k�nnen
   *
   */
  protected InfoMapper() {}


  /**
   * Von der Klasse InfoMapper kann nur eine Instanz erzeugt werden. Sie erf�llt die
   * Singleton-Eigenschaft. Dies geschieht mittels eines private default-Konstruktors und genau
   * einer statischen Variablen vom Typ AuswahlMapper, die die einzige Instanz der Klasse darstellt.
   */
  public static InfoMapper infoMapper() {
    if (infoMapper == null) {
      infoMapper = new InfoMapper();
    }

    return infoMapper;
  }

  /**
   * Suchen eines Info-Objekts mittels der profil-id. Da diese eindeutig ist, wird genau ein Objekt
   * zur�ckgegeben.
   *
   * @param id - vom Datentyp int
   *
   * @return Info-Objekt, das dem �bergebenen Schl�ssel entspricht, bzw. null bei nicht vorhandenem
   *         DB-Tupel.
   */
  public ArrayList<Info> findAllByProfilId(int id) {
    // DB-Verbindung holen
    Connection con = DBConnection.connection();

    try {
      // Leeres SQL-Statement (JDBC) anlegen
      Statement stmt = con.createStatement();

      // Statement ausfüllen und als Query an die DB schicken
      ResultSet rs1 =
          stmt.executeQuery("SELECT id, Text, Profil_id, Eigenschaft_id FROM Info WHERE Profil_id="
              + id + " ORDER BY id");


      /*
       * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben werden. Prüfe, ob ein
       * Ergebnis vorliegt.
       */
      ArrayList<Info> result = new ArrayList<>();

      while (rs1.next()) {
        // Ergebnis-Tupel in Objekt umwandeln
        Info i = new Info();
        i.setId(rs1.getInt("id"));
        i.setText(rs1.getString("Text"));
        i.setProfilId(rs1.getInt("Profil_id"));
        i.setEigenschaftId(rs1.getInt("Eigenschaft_id"));

        result.add(i);
      }
      return result;
    } catch (SQLException e) {
      e.printStackTrace();
      ClientsideSettings.getLogger()
          .severe("Fehler beim Lesen aus der DB" + e.getMessage() + " " + e.getCause() + " ");
      return null;
    }


  }

  /**
   * Die Methode findByKey implementiert die Suche nach genau einer id aus der Datenbank,
   * entsprechend wird genau ein Objekt zur�ckgegeben.
   *
   * @param id
   *
   * @return Info, Info-Objekt, das der �bergegebenen id entspricht, bzw. null bei nicht vorhandenem
   *         DB-Tupel.
   */
  public Info findByKey(int id) {
    // DB-Verbindung holen
    Connection con = DBConnection.connection();

    try {
      // Leeres SQL-Statement (JDBC) anlegen
      Statement stmt = con.createStatement();

      // Statement ausfüllen und als Query an die DB schicken
      ResultSet rs1 =
          stmt.executeQuery("SELECT id, Text, Profil_id, Eigenschaft_id FROM Info WHERE id =" + id);


      /*
       * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben werden. Prüfe, ob ein
       * Ergebnis vorliegt.
       */

      if (rs1.next()) {
        // Ergebnis-Tupel in Objekt umwandeln
        Info i = new Info();
        i.setId(rs1.getInt("id"));
        i.setText(rs1.getString("Text"));
        i.setProfilId(rs1.getInt("Profil_id"));
        i.setEigenschaftId(rs1.getInt("Eigenschaft_id"));
        ClientsideSettings.getLogger().severe("info " + i.getText());
        return i;

      }

    } catch (SQLException e) {
      e.printStackTrace();
      ClientsideSettings.getLogger()
          .severe("Fehler beim Lesen aus der DB" + e.getMessage() + " " + e.getCause() + " ");
      return null;
    }
    return null;


  }

  /**
   * Auslesen aller Auswahl-Tupel.
   *
   * @return Eine ArrayList mit Info-Objekten
   */

  public ArrayList<Info> findAll() {
    Connection con = DBConnection.connection();
    // Ergebnisvektor vorbereiten
    ArrayList<Info> result = new ArrayList<Info>();

    try {
      // Leeres SQL-Statement (JDBC) anlegen
      Statement stmt = con.createStatement();

      // Statement ausfüllen und als Query an die DB schicken
      ResultSet rs1 = stmt
          .executeQuery("SELECT id, Text, Profil_id, Eigenschaft_id FROM Info ORDER BY id DESC");


      /*
       * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben werden. Prüfe, ob ein
       * Ergebnis vorliegt.
       */


      while (rs1.next()) {
        // Ergebnis-Tupel in Objekt umwandeln
        Info i = new Info();
        i.setId(rs1.getInt("id"));
        i.setText(rs1.getString("Text"));
        i.setProfilId(rs1.getInt("Profil_id"));
        i.setEigenschaftId(rs1.getInt("Eigenschaft_id"));

        result.add(i);
      }


    } catch (SQLException e) {
      e.printStackTrace();
      ClientsideSettings.getLogger()
          .severe("Fehler beim schreiben in die DB" + e.getMessage() + " " + e.getCause() + " ");
    }

    // Ergebnisvektor zurückgeben
    return result;
  }


  /**
   * Hinzuf�gen eines Info-Objekts in die Datenbank.
   *
   * @param a - das zu speichernde Objekt
   *
   * @return das an die Datenbank �bergebene Objekt
   */
  public Info insert(Info i) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      /*
       * Zunächst schauen wir nach, welches der momentan höchste Primärschlüsselwert ist.
       */
      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM Info");
      // i.setId(rs.getInt("maxid") + 1);
      // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
      if (rs.next()) {
        /*
         * a erhält den bisher maximalen, nun um 1 inkrementierten Primärschlüssel.
         */
        i.setId(rs.getInt("maxid") + 1);

        stmt = con.createStatement();

        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
        stmt.executeUpdate(
            "INSERT INTO Info (Text, Profil_id, Eigenschaft_id, id) VALUES ('" + i.getText() + "',"
                + i.getProfilId() + "," + i.getEigenschaftId() + "," + i.getId() + ")");
      }
      // ResultSet rs1 = stmt.executeQuery("SELECT MAX(id) AS maxid FROM Info");
      // ClientsideSettings.getLogger().severe("maxid " +rs1.getInt("maxid"));
      // if (rs1.next()) {

      // }

    } catch (SQLException e) {
      e.printStackTrace();
      ClientsideSettings.getLogger()
          .severe("Fehler beim schreiben in die DB" + e.getMessage() + " " + e.getCause() + " ");
    }
    ClientsideSettings.getLogger()
        .severe("info im mapper erstell " + i.getId() + " name" + i.getText());
    return i;
  }


  /**
   * Die Methode update modifiziert ein auf die Datenbank abgebildetes Info-Objekt.
   *
   * @param a - das Objekt, welches in der Datenbank ge�ndert wird
   * @return das als Parameter �bergebene Objekt
   */
  public Info update(Info i) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("UPDATE Info SET Text='" + i.getText() + "' WHERE Profil_id="
          + i.getProfilId() + " AND Eigenschaft_id=" + i.getEigenschaftId());

    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Um Analogie zu insert(Auswahl a) zu wahren, geben wir a zurück
    return i;
  }

  /**
   * L�schen eines auf die Datenbank abgebildeteten Auswahl-Objekts
   *
   * @param a das aus der DB zu l�schende Objekt
   */

  public void delete(Info i) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM Info WHERE id=" + i.getId());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


}
