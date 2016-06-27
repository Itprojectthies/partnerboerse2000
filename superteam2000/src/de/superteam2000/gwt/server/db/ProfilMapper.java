package de.superteam2000.gwt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.shared.bo.Profil;


/**
 * Klasse, die die Aufgabe erf�llt, die Objekte einer persistenten Klasse auf die Datenbank abzubilden und dort zu speichern.
 * Die zu speichernden Objekte werden dematerialisiert und zu gewinnende Objekte aus der Datenbank entsprechend materialisiert. Dies wird
 * als indirektes Mapping bezeichnet. Zur Verwaltung der Objekte implementiert die Mapper-Klasse entsprechende Methoden zur Suche, zum Speichern, L�schen und 
	 * Modifizieren von Objekten. 
 * @see AehnlichkeitsMapper
 * @see AuswahlMapper
 * @see BeschreibungMapper
 * @see DBConnection
 * @see InfoMapper
 * @see KontaktsperreMapper
 * @see MerkzettelMapper
 * @see SuchprofilMapper
 * @author 
 */

public class ProfilMapper {


	/**
	 * Von der Klasse ProfilMapper kann nur eine Instanz erzeugt werden. Sie erf�llt die Singleton-Eigenschaft.
	 * Dies geschieht mittels eines private default-Konstruktors und genau einer statischen Variablen vom 
	 * Typ ProfilMapper, die die einzige Instanz der Klasse darstellt.
	 * 
	 */
	private static ProfilMapper ProfilMapper = null;


	/**
	 * Durch den Modifier "private" gesch�tzter Konstruktor, der verhindert das weiter Instanzen der Klasse erzeugt werden k�nnen
	 *  
	 */
	protected ProfilMapper() {
	}


	/**
	 * Von der Klasse ProfilMapper kann nur eine Instanz erzeugt werden. Sie erf�llt die Singleton-Eigenschaft.
	 * Dies geschieht mittels eines private default-Konstruktors und genau einer statischen Variablen vom 
	 * Typ ProfilMapper, die die einzige Instanz der Klasse darstellt.
	 */
	
	public static ProfilMapper profilMapper() {
		if (ProfilMapper == null) {
			ProfilMapper = new ProfilMapper();
		}

		return ProfilMapper;
	}

	/**
	 * Die Methode findByKey sucht auf die Datenbank abgebildete Profile �ber die Profil-id
	 * 
	 * 
	 * @param id
	 * @return Profil p 
	 */
	public Profil findByKey(int id) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT id, Vorname, Nachname, Email, Haarfarbe, Koerpergroesse, "
					+ "Raucher, Religion, Geschlecht, Geburtsdatum, Erstelldatum FROM Profil "
					+ "WHERE id=" + id );

			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Profil p = new Profil();
				p.setId(rs.getInt("id"));
				p.setVorname(rs.getString("Vorname"));
				p.setNachname(rs.getString("Nachname"));
				p.setEmail(rs.getString("Email"));
				p.setHaarfarbe(rs.getString("Haarfarbe"));
				p.setGeburtsdatum(rs.getDate("Geburtsdatum"));
				p.setErstelldatum(rs.getTimestamp("Erstelldatum"));
				p.setGroesse(rs.getInt("Koerpergroesse"));
				p.setRaucher(rs.getString("Raucher"));
				p.setReligion(rs.getString("Religion"));
				p.setGeschlecht(rs.getString("Geschlecht"));

				return p;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Suchen eines Kunden anhand der E-Mail Adresse
	 * 
	 * @param email
	 *            
	 * @return Profil p
	 */
	public Profil findByEmail(String email) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT id, Vorname, Nachname, Email, Geburtsdatum, Erstelldatum, Haarfarbe, "
					+ "Koerpergroesse, Raucher, Religion, Geschlecht FROM Profil "
					+ "WHERE Email LIKE '" + email + "'");

			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Profil p = new Profil();
				p.setId(rs.getInt("id"));
				p.setVorname(rs.getString("Vorname"));
				p.setNachname(rs.getString("Nachname"));
				p.setEmail(rs.getString("Email"));
				p.setGeburtsdatum(rs.getDate("Geburtsdatum"));
				p.setErstelldatum(rs.getTimestamp("Erstelldatum"));
				p.setHaarfarbe(rs.getString("Haarfarbe"));
				p.setGroesse(rs.getInt("Koerpergroesse"));
				p.setRaucher(rs.getString("Raucher"));
				p.setReligion(rs.getString("Religion"));
				p.setGeschlecht(rs.getString("Geschlecht"));
				p.setCreated(true);

				return p;
			}
		} catch (SQLException e) {
			//
			ClientsideSettings.getLogger().severe("Fehler beim Zurückgbeen byEmail");
			return null;
		}

		return null;
	}

	/**
	 * Ausgabe aller Profile.
	 * 
	 * @return Eine ArrayList mit Profil-Objekten
	 */
	public ArrayList<Profil> findAll() {
		Connection con = DBConnection.connection();
		// Ergebnisvektor vorbereiten
		ArrayList<Profil> result = new ArrayList<>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT id, Vorname, Nachname, Email, Haarfarbe, Koerpergroesse, "
					+ "Raucher, Religion, Geschlecht, Geburtsdatum , Erstelldatum"
					+ " FROM Profil");

			// Für jeden Eintrag im Suchergebnis wird nun ein Profil-Objekt
			// erstellt.

			while (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Profil p = new Profil();
				p.setId(rs.getInt("id"));
				p.setVorname(rs.getString("Vorname"));
				p.setNachname(rs.getString("Nachname"));
				p.setEmail(rs.getString("Email"));
				p.setHaarfarbe(rs.getString("Haarfarbe"));
				p.setGroesse(rs.getInt("Koerpergroesse"));
				p.setRaucher(rs.getString("Raucher"));
				p.setReligion(rs.getString("Religion"));
				p.setGeschlecht(rs.getString("Geschlecht"));
				p.setGeburtsdatum(rs.getDate("Geburtsdatum"));
				p.setErstelldatum(rs.getTimestamp("Erstelldatum"));
				
				// Hinzufügen des neuen Objekts zum Ergebnisvektor
				result.add(p);
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
	 * 
	 * Abbilden eines Profil-Objektes auf die Datenbank
	 * 
	 * 
	 * @param p - vom Typ Profil
	 * @return Profil p
	 * 
	 */
	public Profil insert(Profil p) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunächst schauen wir nach, welches der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs1 = stmt.executeQuery("SELECT MAX(id) AS maxid FROM Profil");
			
			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			if (rs1.next()) {
				/*
				 * p erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
				p.setId(rs1.getInt("maxid") + 1);
				
				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsächliche Einfügeoperation
				stmt.executeUpdate("INSERT INTO Profil (id, Vorname, Nachname, Email, "
						+ "Haarfarbe, Koerpergroesse, Raucher, Religion, Geschlecht, "
						+ "Geburtsdatum) VALUES ("+ p.getId() + ",'" + p.getVorname() + 
						"','" + p.getNachname() + "','" + p.getEmail() + "','"
						+ p.getHaarfarbe() + "'," + p.getGroesse() + ",'" 
						+ p.getRaucher() + "','" + p.getReligion()
						+ "','" + p.getGeschlecht() + "','" + p.getGeburtsdatum() + "')");

				ClientsideSettings.getLogger().info("Profil " + p.getNachname() + "  in DB geschrieben");
				
				//Setzte das Erstelldatum
				ResultSet rs2 = stmt.executeQuery("SELECT Erstelldatum FROM Profil WHERE id =" +p.getId());
	            if (rs2.next()) {
                  p.setErstelldatum(rs2.getTimestamp("Erstelldatum"));
                }
	            
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			ClientsideSettings.getLogger()
					.severe("Fehler beim schreiben in die DB" + e.getMessage() + " " + e.getCause() + " ");
		}

		return p;
	}

	/**
	 * �ndern eines auf die Datenbank abgebildeten Profil-Objekts
	 * @param Profil p
	 * @return Profil 
	 */
	public Profil update(Profil p) {
		Connection con = DBConnection.connection();
		// Jetzt erst erfolgt die tatsächliche Einfügeoperation
		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE Profil SET Vorname='" + p.getVorname() + 
					"', Nachname='" + p.getNachname()+ "', Haarfarbe='" 
					+ p.getHaarfarbe() + "', Koerpergroesse=" + p.getGroesse() + 
					", Raucher='" + p.getRaucher() + "', Religion='" 
					+ p.getReligion() + "', Geburtsdatum='" + p.getGeburtsdatum()
					+ "' WHERE id=" + p.getId());

			ClientsideSettings.getLogger().info("Profiländerungen " + p.getNachname() + " in DB geschrieben");

		} catch (SQLException e) {
			e.printStackTrace();
			ClientsideSettings.getLogger()
					.severe("Fehler beim schreiben in die DB" + e.getMessage() + " " + e.getCause() + " ");

		}

		// Um Analogie zu insert(Profil p) zu wahren, geben wir p zurück
		return p;
	}

	/**
	 *  L�schen eines Profil-Objektes aus der Datenbank
	 *  
	 *  @param Profil p
	 */
	public void delete(Profil p) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Info WHERE Profil_id=" + p.getId());
			stmt.executeUpdate("DELETE FROM Merkzettel WHERE Merker_id=" + p.getId()+ " OR Gemerkter_id=" +p.getId());
			stmt.executeUpdate("DELETE FROM Kontaktsperre WHERE Sperrer_id=" + p.getId()+ " OR Gesperrter_id=" +p.getId());
			stmt.executeUpdate("DELETE FROM Profil WHERE id=" + p.getId());
			ClientsideSettings.getLogger().info("Profil " + p.getNachname() + "  aus DB gelöscht");

		} catch (SQLException e) {
			e.printStackTrace();
			ClientsideSettings.getLogger()
					.severe("Fehler beim schreiben in die DB: " 
					+ e.getMessage() + " " + e.getCause() + " ");
		}
	}
	
	
	/**
	 * Bildet einen Profilbesuch zwischen zwei Profilen auf die Datenbank ab
	 * 
	 *
	 * 
	 * @param besucher
	 * @param besuchter
	 */

	
	public void setVisited(Profil besucher, Profil besuchter) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.execute("INSERT IGNORE INTO Profilbesuch SET Besucher_id = "
					+ besucher.getId() + " , Besuchter_id = "+ besuchter.getId());

		} catch (SQLException e) {
			e.printStackTrace();
			ClientsideSettings.getLogger()
					.severe("Fehler beim schreiben in die DB " + e.getMessage() + " " + e.getCause() + " ");
		}

	}
	
	/**
	 * Holt Informationen �ber m�glicherweise erfolgten Profilbesuch aus der Datenbank
	 * 
	 * @param a
	 * @return Profil - ArrayList
	 */

	public ArrayList<Profil> getVisitedProfiles(Profil a) {

		Connection con = DBConnection.connection();
		ArrayList<Profil> result = new ArrayList<>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT Besuchter_id FROM Profilbesuch WHERE Besucher_id=" + a.getId());

			//
			while (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln

				Profil p = new Profil();
				p = ProfilMapper.findByKey(rs.getInt("Besuchter_id"));

				// Hinzuf�gen des neuen Objekts zum Ergebnisvektor
				result.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ClientsideSettings.getLogger()
					.severe("Fehler beim schreiben in die DB " + e.getMessage() + " " + e.getCause() + " ");
		}

		return result;
	}

}
