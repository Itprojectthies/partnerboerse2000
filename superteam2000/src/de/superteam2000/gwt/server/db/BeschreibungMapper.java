package de.superteam2000.gwt.server.db;

/**
 * Mapper-Klasse, die <code>Beschreibung</code>-Objekte auf eine relationale
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
	 * Die Klasse BeschreibungMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * für sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static BeschreibungMapper beschreibungMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen.
	 * 
	 */
	protected BeschreibungMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>BeschreibungMapper.BeschreibungMapper()</code>. Sie stellt die
	 * Singleton-Beschreibung sicher, indem Sie dafür sorgt, dass nur eine
	 * einzige Instanz von <code>BeschreibungMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> BeschreibungMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>BeschreibungMapper</code>-Objekt.
	 * @see BeschreibungMapper
	 */
	public static BeschreibungMapper beschreibungMapper() {
		if (beschreibungMapper == null) {
			beschreibungMapper = new BeschreibungMapper();
		}

		return beschreibungMapper;
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
	// public Beschreibung findByKey(int id) {
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
	// Beschreibung b = new Beschreibung();
	// b.setId(rs.getInt("id"));
	// b.setFirstName(rs.getString("firstName"));
	// b.setLastName(rs.getString("lastName"));
	//
	// return b;
	// }
	// }
	// catch (SQLException b) {
	// b.printStackTrace();
	// return null;
	// }
	//
	// return null;
	// }
	//
	// /**
	// * Auslesen aller Kunden.
	// *
	// * @return Ein Vektor mit Beschreibung-Objekten, die sämtliche Kunden
	// * repräsentieren. Bei evtl. Exceptions wird ein partiell gef�llter
	// * oder ggf. auch leerer Vetor zurückgeliefert.
	// */
	// public Vector<Beschreibung> findAll() {
	// Connection con = DBConnection.connection();
	// // Ergebnisvektor vorbereiten
	// Vector<Beschreibung> result = new Vector<Beschreibung>();
	//
	// try {
	// Statement stmt = con.createStatement();
	//
	// ResultSet rs = stmt.executeQuery("SELECT id, firstName, lastName "
	// + "FROM Profils " + "ORDER BY lastName");
	//
	// // Für jeden Eintrag im Suchergebnis wird nun ein Beschreibung-Objekt
	// // erstellt.
	// while (rs.next()) {
	// Beschreibung b = new Beschreibung();
	// b.setId(rs.getInt("id"));
	// b.setFirstName(rs.getString("firstName"));
	// b.setLastName(rs.getString("lastName"));
	//
	// // Hinzufügen des neuen Objekts zum Ergebnisvektor
	// result.addElement(b);
	// }
	// }
	// catch (SQLException b) {
	// b.printStackTrace();
	// }
	//
	// // Ergebnisvektor zurückgeben
	// return result;
	// }
	//
	//
	// /**
	// * Einfügen eines <code>Beschreibung</code>-Objekts in die Datenbank.
	// Dabei wird
	// * auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	// * berichtigt.
	// *
	// * @param b das zu speichernde Objekt
	// * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter
	// * <code>id</code>.
	// */
	// public Beschreibung insert(Beschreibung b) {
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
	// * b erhält den bisher maximalen, nun um 1 inkrementierten
	// * Primärschlüssel.
	// */
	// b.setId(rs.getInt("maxid") + 1);
	//
	// stmt = con.createStatement();
	//
	// // Jetzt erst erfolgt die tatsächliche Einfügeoperation
	// stmt.executeUpdate("INSERT INTO Profils (id, firstName, lastName) "
	// + "VALUES (" + b.getId() + ",'" + b.getFirstName() + "','"
	// + b.getLastName() + "')");
	// }
	// }
	// catch (SQLException b) {
	// b.printStackTrace();
	// }
	//
	// /*
	// * Rückgabe, des evtl. korrigierten Profils.
	// *
	// * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen
	// * Objekte übergeben werden, wäre die Anpassung des Beschreibung-Objekts
	// auch
	// * ohne diese explizite Rückgabe außerhalb dieser Methode sichtbar. Die
	// * explizite Rückgabe von b ist eher ein Stilmittel, um zu signalisieren,
	// * dass sich das Objekt evtl. im Laufe der Methode verändert hat.
	// */
	// return b;
	// }
	//
	// /**
	// * Wiederholtes Schreiben eines Objekts in die Datenbank.
	// *
	// * @param b das Objekt, das in die DB geschrieben werden soll
	// * @return das als Parameter übergebene Objekt
	// */
	// public Beschreibung update(Beschreibung b) {
	// Connection con = DBConnection.connection();
	//
	// try {
	// Statement stmt = con.createStatement();
	//
	// stmt.executeUpdate("UPDATE Profils " + "SET firstName=\""
	// + b.getFirstName() + "\", " + "lastName=\"" + b.getLastName() + "\" "
	// + "WHERE id=" + b.getId());
	//
	// }
	// catch (SQLException b) {
	// b.printStackTrace();
	// }
	//
	// // Um Analogie zu insert(Beschreibung b) zu wahren, geben wir b zurück
	// return b;
	// }

	/**
	 * Löschen der Daten eines <code>Beschreibung</code>-Objekts aus der
	 * Datenbank.
	 * 
	 * @param b
	 *            das aus der DB zu löschende "Objekt"
	 */
	// public void delete(Beschreibung b) {
	// Connection con = DBConnection.connection();
	//
	// try {
	// Statement stmt = con.createStatement();
	//
	// stmt.executeUpdate("DELETE FROM Profils " + "WHERE id=" + b.getId());
	// }
	// catch (SQLException b) {
	// b.printStackTrace();
	// }
	// }
	//
	//

}