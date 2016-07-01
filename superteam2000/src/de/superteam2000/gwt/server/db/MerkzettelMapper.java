package de.superteam2000.gwt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.superteam2000.gwt.shared.bo.Merkzettel;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Klasse, die die Aufgabe erfüllt, die Objekte einer persistenten Klasse auf die Datenbank
 * abzubilden und dort zu speichern. Die zu speichernden Objekte werden dematerialisiert und zu
 * gewinnende Objekte aus der Datenbank entsprechend materialisiert. Dies wird als indirektes
 * Mapping bezeichnet. Zur Verwaltung der Objekte implementiert die Mapper-Klasse entsprechende
 * Methoden zur Suche, zum Speichern, Löschen und Modifizieren von Objekten.
 *
 * @see AehnlichkeitsMapper
 * @see AuswahlMapper
 * @see BeschreibungMapper
 * @see DBConnection
 * @see InfoMapper
 * @see KontaktsperreMapper
 * @see ProfilMapper
 * @see SuchprofilMapper
 * @author Thies, Volz, Funke
 */


public class MerkzettelMapper {

  /**
   * Von der Klasse MerkzettelMapper kann nur eine Instanz erzeugt werden. Sie erfüllt die
   * Singleton-Eigenschaft. Dies geschieht mittels eines private default-Konstruktors und genau
   * einer statischen Variablen vom Typ MerkzettelMapper, die die einzige Instanz der Klasse
   * darstellt.
   *
   */
  private static MerkzettelMapper merkzettelMapper = null;


  /**
   * Durch den Modifier "private" geschützter Konstruktor, der verhindert das weiter Instanzen der
   * Klasse erzeugt werden können
   *
   */
  protected MerkzettelMapper() {}

  /**
   * Von der Klasse MerkzettelMapper kann nur eine Instanz erzeugt werden. Sie erfüllt die
   * Singleton-Eigenschaft. Dies geschieht mittels eines private default-Konstruktors und genau
   * einer statischen Variablen vom Typ MerkzettelMapper, die die einzige Instanz der Klasse
   * darstellt.
   */

  public static MerkzettelMapper merkzettelMapper() {
    if (merkzettelMapper == null) {
      merkzettelMapper = new MerkzettelMapper();
    }

    return merkzettelMapper;
  }



  /**
   * Auslesen aller Merkzetteleinträge aus der Datenbank für ein Profil.
   *
   * @param p - Profil p
   * @return Merkzettel des Profils
   *
   */


  public Merkzettel findAllForProfil(Profil p) {
    Connection con = DBConnection.connection();
    // Ergebnisvektor vorbereiten
    Merkzettel result = new Merkzettel();
    ArrayList<Profil> profile = new ArrayList<Profil>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt
          .executeQuery("SELECT Gemerkter_id " + "FROM Merkzettel WHERE Merker_id=" + p.getId());
      result.setMerkerId(p.getId());

      // Für jeden Eintrag im Suchergebnis wird nun ein Merkzettel-Objekt
      // erstellt.
      while (rs.next()) {
        Profil profil = ProfilMapper.profilMapper().findByKey(rs.getInt("Gemerkter_id"));
        profile.add(profil);

      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Profilliste in Merkzettel schreiben
    result.setGemerkteProfile(profile);

    // Ergebnis zurückgeben
    return result;
  }


  /**
   * Einfügen eines Merkzettel-Objekts in die Datenbank.
   *
   * @param m das zu speichernde Objekt
   *
   */
  public void insertMerkenForProfil(Profil merker, Profil gemerkter) {

    Connection con = DBConnection.connection();
    try {
      Statement stmt = con.createStatement();
      // Jetzt erst erfolgt die tatsächliche Einfügeoperation

      stmt.execute("INSERT INTO Merkzettel(Gemerkter_id, Merker_id)" + "VALUES ("
          + gemerkter.getId() + "," + merker.getId() + ")");
      Merkzettel m = new Merkzettel();

      m.setMerkerId(merker.getId());
    } catch (SQLException e) {
      e.printStackTrace();
    }


  }



  /**
   * Löschen der Daten eines Merkzettel-Eintrags aus der Datenbank.
   *
   * @param zwei Profile, der zu Löschende und der "Löschende"
   */
  public void deleteMerkenFor(Profil entferner, Profil entfernter) {
    Connection con = DBConnection.connection();



    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM Merkzettel WHERE Merker_id=" + entferner.getId()
          + " AND Gemerkter_id=" + entfernter.getId());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }



}
