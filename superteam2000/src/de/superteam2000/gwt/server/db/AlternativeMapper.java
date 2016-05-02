package de.superteam2000.gwt.server.db;

import java.sql.*;
import java.util.ArrayList;

/**
 * Mapper-Klasse, die <code>Alternative</code>-Objekte auf eine relationale
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
// bla bal bla

public class AlternativeMapper {

	/**
	 * Die Klasse AlternativeMapper wird nur einmal instantiiert. Man spricht hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * für sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static AlternativeMapper alternativeMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen.
	 * 
	 */
	protected AlternativeMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>AlternativeMapper.AlternativeMapper()</code>. Sie stellt die
	 * Singleton-Alternative sicher, indem Sie dafür sorgt, dass nur eine
	 * einzige Instanz von <code>AlternativeMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> AlternativeMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>AlternativeMapper</code>-Objekt.
	 * @see AlternativeMapper
	 */
	public static AlternativeMapper alternativeMapper() {
		if (alternativeMapper == null) {
			alternativeMapper = new AlternativeMapper();
		}

		return alternativeMapper;
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
	public String findByKey(int id) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT id, firstName, lastName FROM Profils " + "WHERE id=" + id + " ORDER BY lastName");

			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln

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
	 * @return Ein Vektor mit Alternative-Objekten, die sämtliche Kunden
	 *         repräsentieren. Bei evtl. Exceptions wird ein partiell gef�llter
	 *         oder ggf. auch leerer Vetor zurückgeliefert.
	 */
	public ArrayList<String> findAll() {
		Connection con = DBConnection.connection();
		// Ergebnisvektor vorbereiten
		ArrayList<String> result = new ArrayList<>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, firstName, lastName " + "FROM Profils " + "ORDER BY lastName");

			// Für jeden Eintrag im Suchergebnis wird nun ein Alternative-Objekt
			// erstellt.
			while (rs.next()) {

				// a.setId(rs.getInt("id"));
				// a.setFirstName(rs.getString("firstName"));
				// a.setLastName(rs.getString("lastName"));

				// Hinzufügen des neuen Objekts zum Ergebnisvektor
				// result.addElement(a);
			}
		} catch (SQLException a) {
			a.printStackTrace();
		}

		// Ergebnisvektor zurückgeben
		return result;
	}

	/**
	 * Einfügen eines <code>Alternative</code>-Objekts in die Datenbank. Dabei
	 * wird auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	 * berichtigt.
	 * 
	 * @param a
	 *            das zu speichernde Objekt
	 * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter
	 *         <code>id</code>.
	 */
	// public Alternative insert(Alternative a) {
	// Connection con = DBConnection.connection();
	//
	// try {
	// Statement stmt = con.createStatement();
	//
	// /*
	// * Zunächst schauen wir nach, welches der momentan höchste
	// * Primärschlüsselwert ist.
	// */
	// ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
	// + "FROM Profils ");
	//
	// // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
	// if (rs.next()) {
	// /*
	// * a erhält den bisher maximalen, nun um 1 inkrementierten
	// * Primärschlüssel.
	// */
	// a.setId(rs.getInt("maxid") + 1);
	//
	// stmt = con.createStatement();
	//
	// // Jetzt erst erfolgt die tatsächliche Einfügeoperation
	// stmt.executeUpdate("INSERT INTO Profils (id, firstName, lastName) "
	// + "VALUES (" + a.getId() + ",'" + a.getFirstName() + "','"
	// + a.getLastName() + "')");
	// }
	// }
	// catch (SQLException a) {
	// a.printStackTrace();
	// }
	//
	// /*
	// * Rückgabe, des evtl. korrigierten Profils.
	// *
	// * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen
	// * Objekte übergeben werden, wäre die Anpassung des Alternative-Objekts
	// auch
	// * ohne diese explizite Rückgabe außerhalb dieser Methode sichtbar. Die
	// * explizite Rückgabe von a ist eher ein Stilmittel, um zu signalisieren,
	// * dass sich das Objekt evtl. im Laufe der Methode verändert hat.
	// */
	// return a;
	// }
	//
	// /**
	// * Wiederholtes Schreiben eines Objekts in die Datenbank.
	// *
	// * @param a das Objekt, das in die DB geschrieben werden soll
	// * @return das als Parameter übergebene Objekt
	// */
	// public Alternative update(Alternative a) {
	// Connection con = DBConnection.connection();
	//
	// try {
	// Statement stmt = con.createStatement();
	//
	// stmt.executeUpdate("UPDATE Profils " + "SET firstName=\""
	// + a.getFirstName() + "\", " + "lastName=\"" + a.getLastName() + "\" "
	// + "WHERE id=" + a.getId());
	//
	// }
	// catch (SQLException a) {
	// a.printStackTrace();
	// }
	//
	// // Um Analogie zu insert(Alternative a) zu wahren, geben wir a zurück
	// return a;
	// }
	//
	// /**
	// * Löschen der Daten eines <code>Alternative</code>-Objekts aus der
	// Datenbank.
	// *
	// * @param a das aus der DB zu löschende "Objekt"
	// */
	// public void delete(Alternative a) {
	// Connection con = DBConnection.connection();
	//
	// try {
	// Statement stmt = con.createStatement();
	//
	// stmt.executeUpdate("DELETE FROM Profils " + "WHERE id=" + a.getId());
	// }
	// catch (SQLException a) {
	// a.printStackTrace();
	// }
	// }
	//
	// public Alternative findByAuswahlId(int id) {
	// // DB-Verbindung holen
	// Connection con = DBConnection.connection();
	//
	// try {
	// // Leeres SQL-Statement (JDBC) anlegen
	// Statement stmt = con.createStatement();
	//
	// // Statement ausfüllen und als Query an die DB schicken
	// ResultSet rs = stmt
	// .executeQuery("SELECT id, firstName, lastName FROM Profils "
	// + "WHERE id=" + id + " ORDER BY lastName");
	//
	// /*
	// * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
	// * werden. Prüfe, ob ein Ergebnis vorliegt.
	// */
	// if (rs.next()) {
	// // Ergebnis-Tupel in Objekt umwandeln
	// Alternative a = new Alternative();
	// a.setId(rs.getInt("id"));
	// a.setFirstName(rs.getString("firstName"));
	// a.setLastName(rs.getString("lastName"));
	//
	// return a;
	// }
	// }
	// catch (SQLException a) {
	// a.printStackTrace();
	// return null;
	// }
	//
	// return null;
	// }


}