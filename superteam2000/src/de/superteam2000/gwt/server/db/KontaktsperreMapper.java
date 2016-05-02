package de.superteam2000.gwt.server.db;

/**
 * Mapper-Klasse, die <code>Kontaktsperre</code>-Objekte auf eine relationale
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

public class KontaktsperreMapper {

	/**
	 * Die Klasse KontaktsperreMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * für sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static KontaktsperreMapper kontaktsperreMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen.
	 * 
	 */
	protected KontaktsperreMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>KontaktsperreMapper.KontaktsperreMapper()</code>. Sie stellt die
	 * Singleton-Kontaktsperre sicher, indem Sie dafür sorgt, dass nur eine
	 * einzige Instanz von <code>KontaktsperreMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> KontaktsperreMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>KontaktsperreMapper</code>-Objekt.
	 * @see KontaktsperreMapper
	 */
	public static KontaktsperreMapper kontaktsperreMapper() {
		if (kontaktsperreMapper == null) {
			kontaktsperreMapper = new KontaktsperreMapper();
		}

		return kontaktsperreMapper;
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
	// public Kontaktsperre findByKey(int id) {
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
	// Kontaktsperre k = new Kontaktsperre();
	// k.setId(rs.getInt("id"));
	// k.setFirstName(rs.getString("firstName"));
	// k.setLastName(rs.getString("lastName"));
	//
	// return k;
	// }
	// }
	// catch (SQLException k) {
	// k.printStackTrace();
	// return null;
	// }
	//
	// return null;
	// }
	//
	// /**
	// * Auslesen aller Kunden.
	// *
	// * @return Ein Vektor mit Kontaktsperre-Objekten, die sämtliche Kunden
	// * repräsentieren. Bei evtl. Exceptions wird ein partiell gef�llter
	// * oder ggf. auch leerer Vetor zurückgeliefert.
	// */
	// public Vector<Kontaktsperre> findAll() {
	// Connection con = DBConnection.connection();
	// // Ergebnisvektor vorbereiten
	// Vector<Kontaktsperre> result = new Vector<Kontaktsperre>();
	//
	// try {
	// Statement stmt = con.createStatement();
	//
	// ResultSet rs = stmt.executeQuery("SELECT id, firstName, lastName "
	// + "FROM Profils " + "ORDER BY lastName");
	//
	// // Für jeden Eintrag im Suchergebnis wird nun ein Kontaktsperre-Objekt
	// // erstellt.
	// while (rs.next()) {
	// Kontaktsperre k = new Kontaktsperre();
	// k.setId(rs.getInt("id"));
	// k.setFirstName(rs.getString("firstName"));
	// k.setLastName(rs.getString("lastName"));
	//
	// // Hinzufügen des neuen Objekts zum Ergebnisvektor
	// result.addElement(k);
	// }
	// }
	// catch (SQLException k) {
	// k.printStackTrace();
	// }
	//
	// // Ergebnisvektor zurückgeben
	// return result;
	// }
	//
	//
	// /**
	// * Einfügen eines <code>Kontaktsperre</code>-Objekts in die Datenbank.
	// Dabei wird
	// * auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	// * berichtigt.
	// *
	// * @param k das zu speichernde Objekt
	// * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter
	// * <code>id</code>.
	// */
	// public Kontaktsperre insert(Kontaktsperre k) {
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
	// * k erhält den bisher maximalen, nun um 1 inkrementierten
	// * Primärschlüssel.
	// */
	// k.setId(rs.getInt("maxid") + 1);
	//
	// stmt = con.createStatement();
	//
	// // Jetzt erst erfolgt die tatsächliche Einfügeoperation
	// stmt.executeUpdate("INSERT INTO Profils (id, firstName, lastName) "
	// + "VALUES (" + k.getId() + ",'" + k.getFirstName() + "','"
	// + k.getLastName() + "')");
	// }
	// }
	// catch (SQLException k) {
	// k.printStackTrace();
	// }
	//
	// /*
	// * Rückgabe, des evtl. korrigierten Profils.
	// *
	// * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen
	// * Objekte übergeben werden, wäre die Anpassung des Kontaktsperre-Objekts
	// auch
	// * ohne diese explizite Rückgabe außerhalb dieser Methode sichtbar. Die
	// * explizite Rückgabe von k ist eher ein Stilmittel, um zu signalisieren,
	// * dass sich das Objekt evtl. im Laufe der Methode verändert hat.
	// */
	// return k;
	// }
	//
	// /**
	// * Wiederholtes Schreiben eines Objekts in die Datenbank.
	// *
	// * @param k das Objekt, das in die DB geschrieben werden soll
	// * @return das als Parameter übergebene Objekt
	// */
	// public Kontaktsperre update(Kontaktsperre k) {
	// Connection con = DBConnection.connection();
	//
	// try {
	// Statement stmt = con.createStatement();
	//
	// stmt.executeUpdate("UPDATE Profils " + "SET firstName=\""
	// + k.getFirstName() + "\", " + "lastName=\"" + k.getLastName() + "\" "
	// + "WHERE id=" + k.getId());
	//
	// }
	// catch (SQLException k) {
	// k.printStackTrace();
	// }
	//
	// // Um Analogie zu insert(Kontaktsperre k) zu wahren, geben wir k zurück
	// return k;
	// }
	//
	// /**
	// * Löschen der Daten eines <code>Kontaktsperre</code>-Objekts aus der
	// Datenbank.
	// *
	// * @param k das aus der DB zu löschende "Objekt"
	// */
	// public void delete(Kontaktsperre k) {
	// Connection con = DBConnection.connection();
	//
	// try {
	// Statement stmt = con.createStatement();
	//
	// stmt.executeUpdate("DELETE FROM Profils " + "WHERE id=" + k.getId());
	// }
	// catch (SQLException k) {
	// k.printStackTrace();
	// }
	// }
	// public ArrayList<Kontaktsperre> getAllByProfil(Profil profil){
	//
	// }

}
