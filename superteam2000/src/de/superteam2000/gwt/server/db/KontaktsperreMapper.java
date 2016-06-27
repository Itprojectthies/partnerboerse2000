package de.superteam2000.gwt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.shared.bo.Kontaktsperre;
import de.superteam2000.gwt.shared.bo.Profil;

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
 * @see MerkzettelMapper
 * @see ProfilMapper
 * @see SuchprofilMapper
 * @author
 */

public class KontaktsperreMapper {

  /**
   * Von der Klasse KontaktsperreMapper kann nur eine Instanz erzeugt werden. Sie erf�llt die
   * Singleton-Eigenschaft. Dies geschieht mittels eines private default-Konstruktors und genau
   * einer statischen Variablen vom Typ KontaktsperreMapper, die die einzige Instanz der Klasse
   * darstellt.
   *
   *
   */
  private static KontaktsperreMapper kontaktsperreMapper = null;

  /**
   * Durch den Modifier "private" gesch�tzter Konstruktor, der verhindert das weiter Instanzen der
   * Klasse erzeugt werden k�nnen
   *
   */
  protected KontaktsperreMapper() {}

  /**
   * Von der Klasse KontaktsperreMapper kann nur eine Instanz erzeugt werden. Sie erf�llt die
   * Singleton-Eigenschaft. Dies geschieht mittels eines private default-Konstruktors und genau
   * einer statischen Variablen vom Typ KontaktsperreMapper, die die einzige Instanz der Klasse
   * darstellt.
   */

  public static KontaktsperreMapper kontaktsperreMapper() {
    if (kontaktsperreMapper == null) {
      kontaktsperreMapper = new KontaktsperreMapper();
    }

    return kontaktsperreMapper;
  }

  /**
   * Die Methode insertForProfil bietet die M�glichkeit die Kontaktsperre auf die Datenbank
   * abzubilden
   *
   * @param sperrer - sperrendes Profil
   * @param gesperrter - gesperrtes Profil
   * @return
   */



  public Kontaktsperre insertForProfil(Profil sperrer, Profil gesperrter) {
    ClientsideSettings.getLogger().info("insertMerkenforProfil Methode aufgerufen");

    Connection con = DBConnection.connection();
    try {
      Statement stmt = con.createStatement();
      // Jetzt erst erfolgt die tatsächliche Einfügeoperation

      stmt.execute("INSERT INTO Kontaktsperre (Sperrer_id, Gesperrter_id)" + "VALUES ("
          + sperrer.getId() + "," + gesperrter.getId() + ")");
      Kontaktsperre k = new Kontaktsperre();

      k.setSperrerId(sperrer.getId());
      return k;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;


  }

  /**
   * Die Methode deleteSperreFor l�scht die Kontaktsperre zwischen zwei Profil-Objekten.
   *
   *
   * @param entferner
   * @param entfernter
   */



  public void deleteSperreFor(Profil entferner, Profil entfernter) {
    Connection con = DBConnection.connection();



    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM Kontaktsperre WHERE Sperrer_id=" + entferner.getId()
          + " AND Gesperrter_id=" + entfernter.getId());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Auslesen aller Kontaktsperren eines Profils.
   *
   * @return Kontaktsperre (liste) des Profils
   */
  public Kontaktsperre findAllForProfil(Profil p) {
    Connection con = DBConnection.connection();

    // Ergebnis vorbereiten
    Kontaktsperre result = new Kontaktsperre();
    ArrayList<Profil> profile = new ArrayList<>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery(
          "SELECT Gesperrter_id " + "FROM Kontaktsperre WHERE Sperrer_id=" + p.getId());
      result.setSperrerId(p.getId());

      // Für jeden Eintrag im Suchergebnis wird nun ein Merkzettel-Objekt
      // erstellt.
      while (rs.next()) {
        Profil profil = ProfilMapper.profilMapper().findByKey(rs.getInt("Gesperrter_id"));
        profile.add(profil);

      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Profilliste in Merkzettel schreiben
    result.setGesperrteProfile(profile);

    // Ergebnis zurückgeben
    return result;
  }
}
