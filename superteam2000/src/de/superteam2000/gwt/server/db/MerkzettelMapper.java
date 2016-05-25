package de.superteam2000.gwt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.superteam2000.gwt.shared.bo.Merkzettel;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Mapper-Klasse, die <code>Merkzettel</code>-Objekte auf eine relationale
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

public class MerkzettelMapper {

	/**
	 * Die Klasse MerkzettelMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * für sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static MerkzettelMapper merkzettelMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen.
	 * 
	 */
	protected MerkzettelMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>MerkzettelMapper.MerkzettelMapper()</code>. Sie stellt die
	 * Singleton-Merkzettel sicher, indem Sie dafür sorgt, dass nur eine einzige
	 * Instanz von <code>MerkzettelMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> MerkzettelMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>MerkzettelMapper</code>-Objekt.
	 * @see MerkzettelMapper
	 */
	public static MerkzettelMapper merkzettelMapper() {
		if (merkzettelMapper == null) {
			merkzettelMapper = new MerkzettelMapper();
		}

		return merkzettelMapper;
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
//	public Merkzettel findByKey(int id) {
//		// DB-Verbindung holen
//		Connection con = DBConnection.connection();
//
//		try {
//			// Leeres SQL-Statement (JDBC) anlegen
//			Statement stmt = con.createStatement();
//
//			// Statement ausfüllen und als Query an die DB schicken
//			ResultSet rs = stmt
//					.executeQuery("SELECT id, firstName, lastName FROM Profils "
//							+ "WHERE id=" + id + " ORDER BY lastName");
//
//			/*
//			 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
//			 * werden. Prüfe, ob ein Ergebnis vorliegt.
//			 */
//			if (rs.next()) {
//				// Ergebnis-Tupel in Objekt umwandeln
//				Merkzettel m = new Merkzettel();
//				m.setId(rs.getInt("id"));
//				m.setFirstName(rs.getString("firstName"));
//				m.setLastName(rs.getString("lastName"));
//
//				return m;
//			}
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//
//		return null;
//	}

	/**
	 * Auslesen aller Kunden.
	 *
	 * @return Ein Vektor mit Merkzettel-Objekten, die sämtliche Kunden
	 * repräsentieren. Bei evtl. Exceptions wird ein partiell gef�llter
	 * oder ggf. auch leerer Vetor zurückgeliefert.
	 */
	public ArrayList<Merkzettel> findAll() {
		Connection con = DBConnection.connection();
		// Ergebnisvektor vorbereiten
		ArrayList<Merkzettel> result = new ArrayList<Merkzettel>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, Gemerkter_id, Merker_id "
					+ "FROM Merkzettel");

			// Für jeden Eintrag im Suchergebnis wird nun ein Merkzettel-Objekt
			// erstellt.
			while (rs.next()) {
				Merkzettel m = new Merkzettel();
				m.setId(rs.getInt("id"));
				m.setMerkerId(rs.getInt("Merker_id"));
				m.setGemerkterId(rs.getInt("Gemerkter_id"));

				// Hinzufügen des neuen Objekts zum Ergebnisvektor
				result.add(m);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		// Ergebnisvektor zurückgeben
		return result;
	}


	/**
	 * Einfügen eines <code>Merkzettel</code>-Objekts in die Datenbank. Dabei
	 wird
	 * auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	 * berichtigt.
	 *
	 * @param m das zu speichernde Objekt
	 * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter
	 * <code>id</code>.
	 */
	public Merkzettel insertMerkenForProfil(Profil merker, Profil gemerkter) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			// Jetzt erst erfolgt die tatsächliche Einfügeoperation
			stmt.executeUpdate("INSERT INTO Merkzettel ( Gemerkter_id, Merker_id) "
					+ "VALUES (" + gemerkter.getId() + "," + merker.getId() + ")");
			Merkzettel m = new Merkzettel();
			m.setGemerkterId(gemerkter.getId());
			m.setMerkerId(merker.getId());
			return m;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		/*
		 * Rückgabe, des evtl. korrigierten Profils.
		 *
		 * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen
		 * Objekte übergeben werden, wäre die Anpassung des Merkzettel-Objekts
	 	 * auch
		 * ohne diese explizite Rückgabe außerhalb dieser Methode sichtbar. Die
		 * explizite Rückgabe von m ist eher ein Stilmittel, um zu signalisieren,
		 * dass sich das Objekt evtl. im Laufe der Methode verändert hat.
		 */
		
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 *
	 * @param m das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */
	public Merkzettel update(Merkzettel m) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE Merkzettel " + "SET Gemerkter_id=\""
					+ m.getGemerkterId() + "\", Merker_id=\"" + m.getMerkerId() + "\" "
					+ "WHERE id=" + m.getId());

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		// Um Analogie zu insert(Merkzettel m) zu wahren, geben wir m zurück
		return m;
	}

	/**
	 * Löschen der Daten eines <code>Merkzettel</code>-Objekts aus der
	 Datenbank.
	 *
	 * @param m das aus der DB zu löschende "Objekt"
	 */
	public void delete(Merkzettel m) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM Merkzettel WHERE id=" + m.getId());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}



}
