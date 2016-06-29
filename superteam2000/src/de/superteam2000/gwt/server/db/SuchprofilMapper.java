package de.superteam2000.gwt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.bo.Suchprofil;

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
 * @see InfoMapper
 * @see KontaktsperreMapper
 * @see MerkzettelMapper
 * @see ProfilMapper
 * @author
 */

public class SuchprofilMapper {

  /**
   * Von der Klasse SuchprofilMapper kann nur eine Instanz erzeugt werden. Sie erf�llt die
   * Singleton-Eigenschaft. Dies geschieht mittels eines private default-Konstruktors und genau
   * einer statischen Variablen vom Typ SuchprofilMapper, die die einzige Instanz der Klasse
   * darstellt.
   *
   */

  private static SuchprofilMapper suchprofilMapper = null;

  /**
   * Durch den Modifier "private" gesch�tzter Konstruktor, der verhindert das weiter Instanzen der
   * Klasse erzeugt werden k�nnen
   *
   */


  protected SuchprofilMapper() {}

  /**
   * Von der Klasse SuchprofilMapper kann nur eine Instanz erzeugt werden. Sie erf�llt die
   * Singleton-Eigenschaft. Dies geschieht mittels eines private default-Konstruktors und genau
   * einer statischen Variablen vom Typ SuchprofilMapper, die die einzige Instanz der Klasse
   * darstellt.
   */

  public static SuchprofilMapper suchprofilMapper() {
    if (suchprofilMapper == null) {
      suchprofilMapper = new SuchprofilMapper();
    }

    return suchprofilMapper;
  }

  /**
   * Die Methode liefert die Suchprofile, die genau einem Profil zugeordet sind
   *
   * @param Profil p
   * @param String name
   * @return Suchprofil
   *
   */
  public Suchprofil findSuchprofilForProfilByName(Profil p, String name) {
    Connection con = DBConnection.connection();
    // Ergebnisvektor vorbereiten
    Suchprofil sp = new Suchprofil();

    try {
      Statement stmt = con.createStatement();
      ResultSet rs =
          stmt.executeQuery("SELECT id, Name, Profil_id, Haarfarbe, Raucher, Religion, Geschlecht, "
              + "Koerpergroesse_min, Koerpergroesse_max, Alter_min, Alter_max, "
              + "Auswahl_text_1, Auswahl_id_1, Auswahl_text_2, Auswahl_id_2, Auswahl_text_3, "
              + "Auswahl_id_3, Auswahl_text_4, Auswahl_id_4, Auswahl_text_5, Auswahl_id_5 "
              + "FROM Suchprofil WHERE Profil_id=" + p.getId() + " AND Name='" + name + "'");

      // F�r jeden Eintrag im Suchergebnis wird nun ein Profil-Objekt
      // erstellt.

      if (rs.next()) {
        // Ergebnis-Tupel in Objekt umwandeln
        sp.setId(rs.getInt("id"));
        sp.setName(rs.getString("Name"));
        sp.setProfilId(rs.getInt("Profil_id"));
        sp.setHaarfarbe(rs.getString("Haarfarbe"));
        sp.setRaucher(rs.getString("Raucher"));
        sp.setReligion(rs.getString("Religion"));
        sp.setGeschlecht(rs.getString("Geschlecht"));
        sp.setGroesse_min(rs.getInt("Koerpergroesse_min"));
        sp.setGroesse_max(rs.getInt("Koerpergroesse_max"));
        sp.setAlter_min(rs.getInt("Alter_min"));
        sp.setAlter_max(rs.getInt("Alter_max"));

        HashMap<Integer, String> auswahlListe = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
          // if (!rs.getString("Auswahl_text_"+i).equals("Keine Angabe")) {
          auswahlListe.put(rs.getInt("Auswahl_id_" + i), rs.getString("Auswahl_text_" + i));
          // }

        }
        sp.setAuswahlListe(auswahlListe);
        // Hinzufügen des neuen Objekts zum Ergebnisvektor
      }
    } catch (SQLException e) {
      e.printStackTrace();
      ClientsideSettings.getLogger()
          .severe("Fehler beim schreiben in die DB" + e.getMessage() + " " + e.getCause() + " ");
    }

    // Ergebnisvektor zurückgeben
    return sp;
  }

  /**
   * Liefert alle Suchprofile von Profilen und gibt diese als ArrayList zur�ck
   *
   * @param p
   * @return ArrayList mit Suchprofilen
   */


  public ArrayList<Suchprofil> findAllForProfil(Profil p) {
    Connection con = DBConnection.connection();
    // Ergebnisvektor vorbereiten
    ArrayList<Suchprofil> result = new ArrayList<>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs =
          stmt.executeQuery("SELECT id, Name, Profil_id, Haarfarbe, Raucher, Religion, Geschlecht, "
              + "Koerpergroesse_min, Koerpergroesse_max, Alter_min, Alter_max, "
              + "Auswahl_text_1, Auswahl_id_1, Auswahl_text_2, Auswahl_id_2, Auswahl_text_3, "
              + "Auswahl_id_3, Auswahl_text_4, Auswahl_id_4, Auswahl_text_5, Auswahl_id_5 "
              + "FROM Suchprofil WHERE Profil_id=" + p.getId());

      // Für jeden Eintrag im Suchergebnis wird nun ein Profil-Objekt
      // erstellt.

      while (rs.next()) {
        // Ergebnis-Tupel in Objekt umwandeln
        Suchprofil sp = new Suchprofil();
        sp.setId(rs.getInt("id"));
        sp.setName(rs.getString("Name"));
        sp.setProfilId(rs.getInt("Profil_id"));
        sp.setHaarfarbe(rs.getString("Haarfarbe"));
        sp.setRaucher(rs.getString("Raucher"));
        sp.setReligion(rs.getString("Religion"));
        sp.setGeschlecht(rs.getString("Geschlecht"));
        sp.setGroesse_min(rs.getInt("Koerpergroesse_min"));
        sp.setGroesse_max(rs.getInt("Koerpergroesse_max"));
        sp.setAlter_min(rs.getInt("Alter_min"));
        sp.setAlter_max(rs.getInt("Alter_max"));

        HashMap<Integer, String> auswahlListe = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
          auswahlListe.put(rs.getInt("Auswahl_id_" + i), rs.getString("Auswahl_text_" + i));
        }
        sp.setAuswahlListe(auswahlListe);
        // Hinzufügen des neuen Objekts zum Ergebnisvektor
        result.add(sp);
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
   * Abbilden eines Suchprofils auf die Datenbank
   *
   * @param sp
   * @return Suchprofil
   */
  public Suchprofil insert(Suchprofil sp) {
    Connection con = DBConnection.connection();
    try {
      Statement stmt = con.createStatement();
      Statement stmt2 = con.createStatement();
      /*
       * Zunächst schauen wir nach, welches der momentan höchste Primärschlüsselwert ist.
       */
      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM Suchprofil");
      // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
      if (rs.next()) {
        /*
         * p erhält den bisher maximalen, nun um 1 inkrementierten Primärschlüssel.
         */
        sp.setId(rs.getInt("maxid") + 1);

        stmt = con.createStatement();
        stmt2 = con.createStatement();

        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
        stmt.executeUpdate("INSERT INTO Suchprofil (id, Name, Profil_id, "
            + "Haarfarbe, Raucher, Religion, Geschlecht, Koerpergroesse_min,"
            + " Koerpergroesse_max, Alter_min, Alter_max) VALUES (" + sp.getId() + ",'"
            + sp.getName() + "'," + sp.getProfilId() + ",'" + sp.getHaarfarbe() + "','"
            + sp.getRaucher() + "','" + sp.getReligion() + "','" + sp.getGeschlecht() + "',"
            + sp.getGroesse_min() + "," + sp.getGroesse_max() + "," + sp.getAlter_min() + ","
            + sp.getAlter_max() + ")");


        HashMap<Integer, String> auswahlListe = sp.getAuswahlListe();

        int i = 1;
        for (Map.Entry<Integer, String> entry : auswahlListe.entrySet()) {
          stmt2.executeUpdate(
              "UPDATE Suchprofil SET " + "Auswahl_text_" + i + "='" + entry.getValue() + "', "
                  + "Auswahl_id_" + i + "=" + entry.getKey() + " WHERE id=" + sp.getId());
          i++;
        }


        ClientsideSettings.getLogger().info("Suchprofil " + sp.getName() + " in DB geschrieben");

      }

    } catch (SQLException e) {
      e.printStackTrace();
      ClientsideSettings.getLogger()
          .severe("Fehler beim schreiben in die DB" + e.getMessage() + " " + e.getCause() + " ");
    }


    return sp;
  }

  /**
   * L�schen eines Suchprofils aus der Datenbank
   *
   * @param Suchprofil sp
   *
   */
  public void delete(Suchprofil sp) {
    // TODO: alle FK beziehnungen löschen bevor profil löschen

    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate("DELETE FROM Suchprofil WHERE id=" + sp.getId());

    } catch (SQLException e) {
      e.printStackTrace();
      ClientsideSettings.getLogger()
          .severe("Fehler beim schreiben in die DB: " + e.getMessage() + " " + e.getCause() + " ");
    }
  }

  /**
   * Modifizieren eines bereits auf die Datenbank abgebildeteten Suchprofils
   *
   * @param sp
   * @return Suchprofil
   */
  public Suchprofil update(Suchprofil sp) {
    Connection con = DBConnection.connection();
    // Jetzt erst erfolgt die tatsächliche Einfügeoperation
    try {
      Statement stmt = con.createStatement();
      Statement stmt2 = con.createStatement();

      stmt.executeUpdate(
          "UPDATE Suchprofil SET Name='" + sp.getName() + "', Haarfarbe='" + sp.getHaarfarbe()
              + "', Raucher='" + sp.getRaucher() + "', Religion='" + sp.getReligion()
              + "', Geschlecht='" + sp.getGeschlecht() + "', " + "Koerpergroesse_min="
              + sp.getGroesse_min() + ", Koerpergroesse_max=" + sp.getGroesse_max() + ", Alter_min="
              + sp.getAlter_min() + ", Alter_max=" + sp.getAlter_max() + " WHERE id=" + sp.getId());


      HashMap<Integer, String> auswahlListe = sp.getAuswahlListe();

      int i = 1;
      for (Map.Entry<Integer, String> entry : auswahlListe.entrySet()) {
        stmt2.executeUpdate("UPDATE Suchprofil SET " + "Auswahl_text_" + i + "='" + entry.getValue()
            + "', " + "Auswahl_id_" + i + "=" + entry.getKey() + " WHERE id=" + sp.getId());
        i++;
      }

      ClientsideSettings.getLogger()
          .info("Suchprofil " + sp.getName() + " Änderungen in DB geschrieben");

    } catch (SQLException e) {
      e.printStackTrace();
      ClientsideSettings.getLogger()
          .severe("Fehler beim schreiben in die DB" + e.getMessage() + " " + e.getCause() + " ");

    }

    // Um Analogie zu insert(Profil p) zu wahren, geben wir p zurück
    return sp;
  }

}
