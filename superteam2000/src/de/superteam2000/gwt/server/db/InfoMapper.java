package de.superteam2000.gwt.server.db;

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
	// public ArrayList<Info> findByProfil(Profil profil) {
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
	// Info i = new Info();
	// i.setId(rs.getInt("id"));
	// i.setFirstName(rs.getString("firstName"));
	// i.setLastName(rs.getString("lastName"));
	//
	// return i;
	// }
	// }
	// catch (SQLException i) {
	// i.printStackTrace();
	// return null;
	// }
	//
	// return null;
	// }
	//
	// /**
	// * Auslesen aller Kunden.
	// *
	// * @return Ein Vektor mit Info-Objekten, die sämtliche Kunden
	// * repräsentieren. Bei evtl. Exceptions wird ein partiell gef�llter
	// * oder ggf. auch leerer Vetor zurückgeliefert.
	// */
	// public Vector<Info> findAll() {
	// Connection con = DBConnection.connection();
	// // Ergebnisvektor vorbereiten
	// Vector<Info> result = new Vector<Info>();
	//
	// try {
	// Statement stmt = con.createStatement();
	//
	// ResultSet rs = stmt.executeQuery("SELECT id, firstName, lastName "
	// + "FROM Profils " + "ORDER BY lastName");
	//
	// // Für jeden Eintrag im Suchergebnis wird nun ein Info-Objekt
	// // erstellt.
	// while (rs.next()) {
	// Info i = new Info();
	// i.setId(rs.getInt("id"));
	// i.setFirstName(rs.getString("firstName"));
	// i.setLastName(rs.getString("lastName"));
	//
	// // Hinzufügen des neuen Objekts zum Ergebnisvektor
	// result.addElement(i);
	// }
	// }
	// catch (SQLException i) {
	// i.printStackTrace();
	// }
	//
	// // Ergebnisvektor zurückgeben
	// return result;
	// }
	//
	//
	// /**
	// * Einfügen eines <code>Info</code>-Objekts in die Datenbank. Dabei wird
	// * auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	// * berichtigt.
	// *
	// * @param i das zu speichernde Objekt
	// * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter
	// * <code>id</code>.
	// */
	// public Info insertAuswahl(Profil p, Auswahl a) {
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
	// * i erhält den bisher maximalen, nun um 1 inkrementierten
	// * Primärschlüssel.
	// */
	// i.setId(rs.getInt("maxid") + 1);
	//
	// stmt = con.createStatement();
	//
	// // Jetzt erst erfolgt die tatsächliche Einfügeoperation
	// stmt.executeUpdate("INSERT INTO Profils (id, firstName, lastName) "
	// + "VALUES (" + i.getId() + ",'" + i.getFirstName() + "','"
	// + i.getLastName() + "')");
	// }
	// }
	// catch (SQLException i) {
	// i.printStackTrace();
	// }
	// public Info insertBeschreibung(Profil p, Beschreibung b) {
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
	// * i erhält den bisher maximalen, nun um 1 inkrementierten
	// * Primärschlüssel.
	// */
	// i.setId(rs.getInt("maxid") + 1);
	//
	// stmt = con.createStatement();
	//
	// // Jetzt erst erfolgt die tatsächliche Einfügeoperation
	// stmt.executeUpdate("INSERT INTO Profils (id, firstName, lastName) "
	// + "VALUES (" + i.getId() + ",'" + i.getFirstName() + "','"
	// + i.getLastName() + "')");
	// }
	// }
	// catch (SQLException i) {
	// i.printStackTrace();
	// }
	//
	// /*
	// * Rückgabe, des evtl. korrigierten Profils.
	// *
	// * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen
	// * Objekte übergeben werden, wäre die Anpassung des Info-Objekts auch
	// * ohne diese explizite Rückgabe außerhalb dieser Methode sichtbar. Die
	// * explizite Rückgabe von i ist eher ein Stilmittel, um zu signalisieren,
	// * dass sich das Objekt evtl. im Laufe der Methode verändert hat.
	// */
	// return i;
	// }
	//
	// /**
	// * Wiederholtes Schreiben eines Objekts in die Datenbank.
	// *
	// * @param i das Objekt, das in die DB geschrieben werden soll
	// * @return das als Parameter übergebene Objekt
	// */
	// public Info updateForProfil(Profil p, Info i) {
	// Connection con = DBConnection.connection();
	//
	// try {
	// Statement stmt = con.createStatement();
	//
	// stmt.executeUpdate("UPDATE Profils " + "SET firstName=\""
	// + i.getFirstName() + "\", " + "lastName=\"" + i.getLastName() + "\" "
	// + "WHERE id=" + i.getId());
	//
	// }
	// catch (SQLException i) {
	// i.printStackTrace();
	// }
	//
	// // Um Analogie zu insert(Info i) zu wahren, geben wir i zurück
	// return i;
	// }
	//
	// /**
	// * Löschen der Daten eines <code>Info</code>-Objekts aus der Datenbank.
	// *
	// * @param i das aus der DB zu löschende "Objekt"
	// */
	// public void deleteForProfil(Profil p, Info i) {
	// Connection con = DBConnection.connection();
	//
	// try {
	// Statement stmt = con.createStatement();
	//
	// stmt.executeUpdate("DELETE FROM Profils " + "WHERE id=" + i.getId());
	// }
	// catch (SQLException i) {
	// i.printStackTrace();
	// }
	// }

}
