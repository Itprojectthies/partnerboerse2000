package de.superteam2000.gwt.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.shared.bo.*;

/**
 * Mapper-Klasse, die <code>Profil</code>-Objekte auf eine relationale Datenbank
 * abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung gestellt, mit
 * deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und gelöscht werden
 * können. Das Mapping ist bidirektional. D.h., Objekte können in DB-Strukturen
 * und DB-Strukturen in Objekte umgewandelt werden.
 * <p>
 * 
 * 
 * @see
 * @author Thies
 */

public class ProfilMapper {

	/**
	 * Die Klasse ProfilMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * für sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static ProfilMapper ProfilMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen.
	 * 
	 */
	protected ProfilMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>ProfilMapper.profilMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine
	 * einzige Instanz von <code>ProfilMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> ProfilMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>ProfilMapper</code>-Objekt.
	 * @see ProfilMapper
	 */
	public static ProfilMapper profilMapper() {
		if (ProfilMapper == null) {
			ProfilMapper = new ProfilMapper();
		}

		return ProfilMapper;
	}

	/**
	 * Suchen eines Kunden mit vorgegebener Kundennummer. Da diese eindeutig
	 * ist, wird genau ein Objekt zur�ckgegeben.
	 * 
	 * @param id
	 *            Primärschlüsselattribut (->DB)
	 * @return Kunden-Objekt, das dem übergebenen Schlüssel entspricht, null bei
	 *         nicht vorhandenem DB-Tupel.
	 */
	public Profil findByKey(int id) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT id, Vorname, Nachname, Email, Haarfarbe, Koerpergroesse, Raucher, Religion, Geschlecht, Geburtsdatum FROM Profil "
					
							+ "WHERE id=" + id + " ORDER BY Nachname");

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
				p.setGroesse(rs.getInt("Koerpergroesse"));
				p.setRaucher(rs.getString("Raucher"));
				p.setReligion(rs.getString("Religion"));
				p.setGeschlecht(rs.getString("Geschlecht"));
				p.setGeburtsdatum(rs.getDate("Geburtsdatum"));

				return p;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Suchen eines Kunden mit vorgegebener Kundennummer. Da diese eindeutig
	 * ist, wird genau ein Objekt zur�ckgegeben.
	 * 
	 * @param email
	 *            Primärschlüsselattribut (->DB)
	 * @return Kunden-Objekt, das dem übergebenen Schlüssel entspricht, null bei
	 *         nicht vorhandenem DB-Tupel.
	 */
	public Profil findByEmail(String email) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT id, Vorname, Nachname, Email, Geburtsdatum, Haarfarbe, Koerpergroesse, Raucher, Religion, Geschlecht FROM Profil "
							+ "WHERE Email LIKE '" + email + "'");

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
				p.setGeburtsdatum(rs.getDate("Geburtsdatum"));
				p.setHaarfarbe(rs.getString("Haarfarbe"));
				p.setGroesse(rs.getInt("Koerpergroesse"));
				p.setRaucher(rs.getString("Raucher"));
				p.setReligion(rs.getString("Religion"));
				p.setGeschlecht(rs.getString("Geschlecht"));
				p.setCreated(true);

				ClientsideSettings.getLogger().severe("User byEmail zurückgegeben");
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
	 * Auslesen aller Kunden.
	 * 
	 * @return Ein Vektor mit Profil-Objekten, die sämtliche Kunden
	 *         repräsentieren. Bei evtl. Exceptions wird ein partiell gef�llter
	 *         oder ggf. auch leerer Vetor zurückgeliefert.
	 */
	public ArrayList<Profil> findAll() {
		Connection con = DBConnection.connection();
		// Ergebnisvektor vorbereiten
		ArrayList<Profil> result = new ArrayList<>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT id, Vorname, Nachname, Email, Haarfarbe, Koerpergroesse, Raucher, Religion, Geschlecht, Geburtsdatum "
							+ "FROM Profil ORDER BY Nachname");

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

				// Hinzufügen des neuen Objekts zum Ergebnisvektor
				result.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ClientsideSettings.getLogger().severe("Fehler beim schreiben in die DB" + 
					e.getMessage() + " " + e.getCause() + " ");
		}

		// Ergebnisvektor zurückgeben
		return result;
	}

	/**
	 * Auslesen aller Kunden-Objekte mit gegebenem Nachnamen
	 * 
	 * @param name
	 *            Nachname der Kunden, die ausgegeben werden sollen
	 * @return Ein Vektor mit Profil-Objekten, die sämtliche Kunden mit dem
	 *         gesuchten Nachnamen repräsentieren. Bei evtl. Exceptions wird ein
	 *         partiell gefüllter oder ggf. auch leerer Vetor zurückgeliefert.
	 */
	public ArrayList<Profil> findByLastName(String name) {
		Connection con = DBConnection.connection();
		ArrayList<Profil> result = new ArrayList<>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT id, Vorname, Nachname, Email, Haarfarbe, Koerpergroesse, Raucher, Religion, Geschlecht "
							+ "FROM Profil " + "WHERE Nachname LIKE '" + name + "' ORDER BY Nachname");

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

				// Hinzufügen des neuen Objekts zum Ergebnisvektor
				result.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Ergebnisvektor zurückgeben
		return result;
	}

	/**
	 * Einfügen eines <code>Profil</code>-Objekts in die Datenbank. Dabei wird
	 * auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	 * berichtigt.
	 * 
	 * @param p
	 *            das zu speichernde Objekt
	 * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter
	 *         <code>id</code>.
	 */
	public Profil insert(Profil p) {
		Connection con = DBConnection.connection();
		ClientsideSettings.getLogger().info("Profil " +p.getEmail() + " in DB schreiben");
		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunächst schauen wir nach, welches der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM Profil");

			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/*
				 * p erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
				p.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsächliche Einfügeoperation
				stmt.executeUpdate("INSERT INTO Profil (id, Vorname, Nachname, Email, "
						+ "Haarfarbe, Koerpergroesse, Raucher, Religion, Geschlecht, Geburtsdatum) " + "VALUES ("
						+ p.getId() + ",'" + p.getVorname() + "','" + p.getNachname() + "','" + p.getEmail() + "','"
						+ p.getHaarfarbe() + "'," + p.getGroesse() + ",'" + p.getRaucher() + "','" + p.getReligion()
						+ "','" + p.getGeschlecht() + "','" + p.getGeburtsdatum() + "')");

				ClientsideSettings.getLogger().info("Profil " +p.getEmail() + " in DB geschrieben");

			}
		} catch (SQLException e) {
			e.printStackTrace();
			ClientsideSettings.getLogger().severe("Fehler beim schreiben in die DB" + 
					e.getMessage() + " " + e.getCause() + " ");
		}

		/*
		 * Rückgabe, des evtl. korrigierten Profils.
		 * 
		 * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen
		 * Objekte übergeben werden, wäre die Anpassung des Profil-Objekts auch
		 * ohne diese explizite Rückgabe außerhalb dieser Methode sichtbar. Die
		 * explizite Rückgabe von p ist eher ein Stilmittel, um zu
		 * signalisieren, dass sich das Objekt evtl. im Laufe der Methode
		 * verändert hat.
		 */
		return p;
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param p
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */
	public Profil update(Profil p) {
		Connection con = DBConnection.connection();
		ClientsideSettings.getLogger().info("Versuche Änderungen in DB geschrieben");
		// Jetzt erst erfolgt die tatsächliche Einfügeoperation
		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE Profil SET Vorname=\"" + p.getVorname() + "\", Nachname=\""
					+ p.getNachname() + "\", Haarfarbe=\"" + p.getHaarfarbe() + "\", Koerpergroesse="
					+ p.getGroesse() + ", Raucher=\"" + p.getRaucher() + "\", Religion=\"" + p.getReligion()
					+ "\", Geburtsdatum=\"" + p.getGeburtsdatum() + "\" WHERE id=" + p.getId());

			ClientsideSettings.getLogger().info("Profil " +p.getEmail() + " Änderungen in DB geschrieben");

		} catch (SQLException e) {
			e.printStackTrace();
			ClientsideSettings.getLogger().severe("Fehler beim schreiben in die DB" + 
					e.getMessage() + " " + e.getCause() + " ");

		}

		// Um Analogie zu insert(Profil p) zu wahren, geben wir p zurück
		return p;
	}

	/**
	 * Löschen der Daten eines <code>Profil</code>-Objekts aus der Datenbank.
	 * 
	 * @param p
	 *            das aus der DB zu löschende "Objekt"
	 */
	public void delete(Profil p) {
		//TODO: alle FK beziehnungen löschen bevor profil löschen
		
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Info WHERE Profil_id=" + p.getId());
			stmt.executeUpdate("DELETE FROM Profil WHERE id=" + p.getId());
			
		} catch (SQLException e) {
			e.printStackTrace();
			ClientsideSettings.getLogger().severe("Fehler beim schreiben in die DB: " + 
					e.getMessage() + " " + e.getCause() + " ");
		}
	}

	// soll in Besuchertabelle schreiben wann wer wen besucht hat. und eine
	// Variable "visited" true setzen
	public void setVisited(Profil besucher, Profil besuchter) {
		Connection con = DBConnection.connection();
		ClientsideSettings.getLogger().info("Profil " + besucher.getEmail() + " besucht " + besuchter.getEmail());
		try {
			Statement stmt = con.createStatement();

			
			stmt.executeQuery("INSERT INTO Profilbesuch (Besucher_id, Besuchter_id) VALUES ("
					+ besucher.getId() + "," + besuchter.getId() + ")");


		} catch (SQLException e) {
			e.printStackTrace();
			ClientsideSettings.getLogger().severe("Fehler beim schreiben in die DB" + 
					e.getMessage() + " " + e.getCause() + " ");
		}

	}

	// soll eine Liste mit allen Profilen returnen wo ein timestamp drin is
	 public ArrayList<Profil> getVisitedProfiles(Profil a){
		 
		 Connection con = DBConnection.connection();
			// Ergebnisvektor vorbereiten
			ArrayList<Profil> result = new ArrayList<>();

			try {
				Statement stmt = con.createStatement();

				ResultSet rs = stmt.executeQuery(
						"SELECT Besuchter_id "
								+ "FROM Profilbesuch WHERE Besucher_id=" + a.getId());
				
				// Für jeden Eintrag im Suchergebnis wird nun ein Profil-Objekt
				// erstellt.
				ClientsideSettings.getLogger().severe("Statement ausgeführt");
				while (rs.next()) {
					// Ergebnis-Tupel in Objekt umwandeln
					
					Profil p = new Profil();
					p = ProfilMapper.findByKey(rs.getInt("Besuchter_id"));

					// Hinzufügen des neuen Objekts zum Ergebnisvektor
					result.add(p);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				ClientsideSettings.getLogger().severe("Fehler beim schreiben in die DB" + 
						e.getMessage() + " " + e.getCause() + " ");
			}
		 
	 return result;
	 }

}
