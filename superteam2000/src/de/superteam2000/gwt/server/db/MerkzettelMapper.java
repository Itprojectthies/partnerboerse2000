package de.superteam2000.gwt.server.db;

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
	// public Merkzettel findByKey(int id) {
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
	// Merkzettel m = new Merkzettel();
	// m.setId(rs.getInt("id"));
	// m.setFirstName(rs.getString("firstName"));
	// m.setLastName(rs.getString("lastName"));
	//
	// return m;
	// }
	// }
	// catch (SQLException m) {
	// m.printStackTrace();
	// return null;
	// }
	//
	// return null;
	// }
	//
	// /**
	// * Auslesen aller Kunden.
	// *
	// * @return Ein Vektor mit Merkzettel-Objekten, die sämtliche Kunden
	// * repräsentieren. Bei evtl. Exceptions wird ein partiell gef�llter
	// * oder ggf. auch leerer Vetor zurückgeliefert.
	// */
	// public Vector<Merkzettel> findAll() {
	// Connection con = DBConnection.connection();
	// // Ergebnisvektor vorbereiten
	// Vector<Merkzettel> result = new Vector<Merkzettel>();
	//
	// try {
	// Statement stmt = con.createStatement();
	//
	// ResultSet rs = stmt.executeQuery("SELECT id, firstName, lastName "
	// + "FROM Profils " + "ORDER BY lastName");
	//
	// // Für jeden Eintrag im Suchergebnis wird nun ein Merkzettel-Objekt
	// // erstellt.
	// while (rs.next()) {
	// Merkzettel m = new Merkzettel();
	// m.setId(rs.getInt("id"));
	// m.setFirstName(rs.getString("firstName"));
	// m.setLastName(rs.getString("lastName"));
	//
	// // Hinzufügen des neuen Objekts zum Ergebnisvektor
	// result.addElement(m);
	// }
	// }
	// catch (SQLException m) {
	// m.printStackTrace();
	// }
	//
	// // Ergebnisvektor zurückgeben
	// return result;
	// }
	//
	//
	// /**
	// * Einfügen eines <code>Merkzettel</code>-Objekts in die Datenbank. Dabei
	// wird
	// * auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	// * berichtigt.
	// *
	// * @param m das zu speichernde Objekt
	// * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter
	// * <code>id</code>.
	// */
	// public Merkzettel insertMerkenForProfil(Profil a, Profil b) {
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
	// * m erhält den bisher maximalen, nun um 1 inkrementierten
	// * Primärschlüssel.
	// */
	// m.setId(rs.getInt("maxid") + 1);
	//
	// stmt = con.createStatement();
	//
	// // Jetzt erst erfolgt die tatsächliche Einfügeoperation
	// stmt.executeUpdate("INSERT INTO Profils (id, firstName, lastName) "
	// + "VALUES (" + m.getId() + ",'" + m.getFirstName() + "','"
	// + m.getLastName() + "')");
	// }
	// }
	// catch (SQLException m) {
	// m.printStackTrace();
	// }
	//
	// /*
	// * Rückgabe, des evtl. korrigierten Profils.
	// *
	// * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen
	// * Objekte übergeben werden, wäre die Anpassung des Merkzettel-Objekts
	// auch
	// * ohne diese explizite Rückgabe außerhalb dieser Methode sichtbar. Die
	// * explizite Rückgabe von m ist eher ein Stilmittel, um zu signalisieren,
	// * dass sich das Objekt evtl. im Laufe der Methode verändert hat.
	// */
	// return m;
	// }
	//
	// /**
	// * Wiederholtes Schreiben eines Objekts in die Datenbank.
	// *
	// * @param m das Objekt, das in die DB geschrieben werden soll
	// * @return das als Parameter übergebene Objekt
	// */
	// public Merkzettel update(Merkzettel m) {
	// Connection con = DBConnection.connection();
	//
	// try {
	// Statement stmt = con.createStatement();
	//
	// stmt.executeUpdate("UPDATE Profils " + "SET firstName=\""
	// + m.getFirstName() + "\", " + "lastName=\"" + m.getLastName() + "\" "
	// + "WHERE id=" + m.getId());
	//
	// }
	// catch (SQLException m) {
	// m.printStackTrace();
	// }
	//
	// // Um Analogie zu insert(Merkzettel m) zu wahren, geben wir m zurück
	// return m;
	// }
	//
	// /**
	// * Löschen der Daten eines <code>Merkzettel</code>-Objekts aus der
	// Datenbank.
	// *
	// * @param m das aus der DB zu löschende "Objekt"
	// */
	// public void delete(Merkzettel m) {
	// Connection con = DBConnection.connection();
	//
	// try {
	// Statement stmt = con.createStatement();
	//
	// stmt.executeUpdate("DELETE FROM Profils " + "WHERE id=" + m.getId());
	// }
	// catch (SQLException m) {
	// m.printStackTrace();
	// }
	// }
	//
	//

}
