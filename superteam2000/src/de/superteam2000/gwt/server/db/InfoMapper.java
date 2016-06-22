package de.superteam2000.gwt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.shared.bo.Info;

/**
 * Mapper-Klasse, die <code>Info</code>-Objekte auf eine relationale Datenbank
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

public class InfoMapper {

	/**
	 * Die Klasse InfoMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * für sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static InfoMapper infoMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen.
	 * 
	 */
	protected InfoMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>InfoMapper.InfoMapper()</code>. Sie stellt die Singleton-Info
	 * sicher, indem Sie dafür sorgt, dass nur eine einzige Instanz von
	 * <code>InfoMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> InfoMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>InfoMapper</code>-Objekt.
	 * @see InfoMapper
	 */
	public static InfoMapper infoMapper() {
		if (infoMapper == null) {
			infoMapper = new InfoMapper();
		}

		return infoMapper;
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
	public ArrayList<Info> findAllByProfilId(int id) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();
			
			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs1 = stmt.executeQuery(
					"SELECT id, Text, Profil_id, Eigenschaft_id FROM Info WHERE Profil_id=" + id +" ORDER BY id");

			
			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
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
			ClientsideSettings.getLogger().severe("Fehler beim Lesen aus der DB" + 
					e.getMessage() + " " + e.getCause() + " ");
			return null;
		}

		
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
	public Info findByKey(int id) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();
			
			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs1 = stmt.executeQuery(
					"SELECT id, Text, Profil_id, Eigenschaft_id FROM Info WHERE id =" + id);

			
			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			
			if (rs1.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Info i = new Info();
				i.setId(rs1.getInt("id"));
				i.setText(rs1.getString("Text"));
				i.setProfilId(rs1.getInt("Profil_id"));
				i.setEigenschaftId(rs1.getInt("Eigenschaft_id"));
				return i;
					
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			ClientsideSettings.getLogger().severe("Fehler beim Lesen aus der DB" + 
					e.getMessage() + " " + e.getCause() + " ");
			return null;
		}
		return null;

		
	}

	/**
	 * Auslesen aller Kunden.
	 *
	 * @return Ein Vektor mit Auswahl-Objekten, die sämtliche Kunden
	 *         repräsentieren. Bei evtl. Exceptions wird ein partiell gef�llter
	 *         oder ggf. auch leerer Vetor zurückgeliefert.
	 */
	public ArrayList<Info> findAll() {
		Connection con = DBConnection.connection();
		// Ergebnisvektor vorbereiten
		ArrayList<Info> result = new ArrayList<Info>();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
						Statement stmt = con.createStatement();
						
						// Statement ausfüllen und als Query an die DB schicken
						ResultSet rs1 = stmt.executeQuery(
								"SELECT id, Text, Profil_id, Eigenschaft_id FROM Info ORDER BY id DESC");

						
						/*
						 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
						 * werden. Prüfe, ob ein Ergebnis vorliegt.
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
			ClientsideSettings.getLogger().severe("Fehler beim schreiben in die DB" + 
					e.getMessage() + " " + e.getCause() + " ");
		}

		// Ergebnisvektor zurückgeben
		return result;
	}

	/**
	 * Einfügen eines <code>Auswahl</code>-Objekts in die Datenbank. Dabei wird
	 * auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	 * berichtigt.
	 *
	 * @param a
	 *            das zu speichernde Objekt
	 * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter
	 *         <code>id</code>.
	 */
	public Info insert(Info i) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunächst schauen wir nach, welches der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM Info ");

			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/*
				 * a erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
				i.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsächliche Einfügeoperation
				stmt.executeUpdate("INSERT INTO Info (Text, Profil_id, Eigenschaft_id) VALUES ('"
						+ i.getText() + "'," + i.getProfilId() + "," + i.getEigenschaftId() + ")");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ClientsideSettings.getLogger().severe("Fehler beim schreiben in die DB" + 
					e.getMessage() + " " + e.getCause() + " ");
		}

		return i;
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 *
	 * @param a
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */
	public Info update(Info i) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE Info SET Text='"+i.getText()+
					 "' WHERE Profil_id=" + i.getProfilId() + 
					 " AND Eigenschaft_id=" + i.getEigenschaftId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Um Analogie zu insert(Auswahl a) zu wahren, geben wir a zurück
		return i;
	}

	/**
	 * Löschen der Daten eines <code>Auswahl</code>-Objekts aus der Datenbank.
	 *
	 * @param a
	 *            das aus der DB zu löschende "Objekt"
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
