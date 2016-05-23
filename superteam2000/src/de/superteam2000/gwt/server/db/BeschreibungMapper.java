package de.superteam2000.gwt.server.db;


import java.sql.*;
import java.util.ArrayList;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.shared.bo.Beschreibung;

/**
 * Mapper-Klasse, die <code>Auswahl</code>-Objekte auf eine relationale
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

public class BeschreibungMapper {

	/**
	 * Die Klasse AuswahlMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * für sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static BeschreibungMapper BeschreibungMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen.
	 * 
	 */
	protected BeschreibungMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>AuswahlMapper.AuswahlMapper()</code>. Sie stellt die
	 * Singleton-Auswahl sicher, indem Sie dafür sorgt, dass nur eine einzige
	 * Instanz von <code>AuswahlMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> AuswahlMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>AuswahlMapper</code>-Objekt.
	 * @see AuswahlMapper
	 */
	public static BeschreibungMapper beschreibungMapper() {
		if (BeschreibungMapper == null) {
			BeschreibungMapper = new BeschreibungMapper();
		}

		return BeschreibungMapper;
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
	public Beschreibung findByKey(int id) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();
			
			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs1 = stmt.executeQuery(
					"SELECT id, Name, Beschreibungstext FROM Eigenschaft WHERE id=" + id + " AND e_typ='b'");

			
			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			
			if (rs1.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Beschreibung b = new Beschreibung();
				b.setId(rs1.getInt("id"));
				b.setName(rs1.getString("Name"));
				b.setBeschreibungstext(rs1.getString("Beschreibungstext"));
				
				
				return b;
			}
		} catch (SQLException a) {
			a.printStackTrace();
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
	public ArrayList<Beschreibung> findAll() {
		Connection con = DBConnection.connection();
		// Ergebnisvektor vorbereiten
		ArrayList<Beschreibung> result = new ArrayList<Beschreibung>();

		try {
			Statement stmt1 = con.createStatement();
			ResultSet rs1 = stmt1.executeQuery("SELECT id, Name, Beschreibungstext FROM Eigenschaft WHERE e_typ='b'");
			
			
			// Für jeden Eintrag im Suchergebnis wird nun ein Auswahl-Objekt
			// erstellt.
			ClientsideSettings.getLogger().severe("Statement erfolgreich");
			
			while (rs1.next()) {
				Beschreibung b = new Beschreibung();
				b.setId(rs1.getInt("id"));
				b.setName(rs1.getString("Name"));
				b.setBeschreibungstext(rs1.getString("Beschreibungstext"));
				result.add(b);
				
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
	 * @param b
	 *            das zu speichernde Objekt
	 * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter
	 *         <code>id</code>.
	 */
	public Beschreibung insert(Beschreibung b) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunächst schauen wir nach, welches der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM Eigenschaft ");

			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/*
				 * a erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
				b.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsächliche Einfügeoperation
				stmt.executeUpdate("INSERT INTO Eigenschaft (id, Name, Beschreibungstext, e_typ) VALUES (" + b.getId() + ",'"
						+ b.getName() + "','" + b.getBeschreibungstext() + "','b')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/*
		 * Rückgabe, des evtl. korrigierten Profils.
		 *
		 * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen
		 * Objekte übergeben werden, wäre die Anpassung des Auswahl-Objekts auch
		 * ohne diese explizite Rückgabe außerhalb dieser Methode sichtbar. Die
		 * explizite Rückgabe von a ist eher ein Stilmittel, um zu
		 * signalisieren, dass sich das Objekt evtl. im Laufe der Methode
		 * verändert hat.
		 */
		return b;
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 *
	 * @param b
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */
	public Beschreibung update(Beschreibung b) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE Eigenschaft SET Name=\"" + b.getName() + "\", Beschreibungstext=\""
					+ b.getBeschreibungstext() + "\", e_typ=\"a\" WHERE id=" + b.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Um Analogie zu insert(Auswahl a) zu wahren, geben wir a zurück
		return b;
	}

	/**
	 * Löschen der Daten eines <code>Auswahl</code>-Objekts aus der Datenbank.
	 *
	 * @param b
	 *            das aus der DB zu löschende "Objekt"
	 */
	public void delete(Beschreibung b) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM Eigenschaft " + "WHERE id=" + b.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}