package de.superteam2000.gwt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.shared.bo.Kontaktsperre;
import de.superteam2000.gwt.shared.bo.Merkzettel;
import de.superteam2000.gwt.shared.bo.Profil;

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
	public Kontaktsperre insertKontaktsperreForProfil(Profil sperrer, Profil gesperrter) {
		ClientsideSettings.getLogger().info("insertMerkenforProfil Methode aufgerufen");

		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			// Jetzt erst erfolgt die tatsächliche Einfügeoperation

			stmt.execute("INSERT INTO `kontaktsperre`( `Sperrer_id`, `Gesperrter_id`)" 
			+ "VALUES ("+sperrer.getId()+"," + gesperrter.getId()+  ")");
			Kontaktsperre k = new Kontaktsperre();

			k.setSperrerId(sperrer.getId());
			return k;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

		
	}
	
	/**
	 * Auslesen aller Kontaktsperren eines Profils.
	 *
	 * @return Kontaktsperr(liste) des Profils
	 */
	public Kontaktsperre findAllForProfil(Profil p) {
		Connection con = DBConnection.connection();
		
		// Ergebnis vorbereiten
		Kontaktsperre result = new Kontaktsperre();
		ArrayList<Profil> profile = new ArrayList<>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT `Gesperrter_id` "
					+ "FROM `kontaktsperre`" +" WHERE `Sperrer_id`=" + p.getId());
			result.setSperrerId(p.getId());

			// Für jeden Eintrag im Suchergebnis wird nun ein Merkzettel-Objekt
			// erstellt.
			while (rs.next()) {
				Profil profil = ProfilMapper.profilMapper().findByKey(rs.getInt("Gesperrter_id"));
				profile.add(profil);
				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Profilliste in Merkzettel schreiben
		result.setGesperrteProfile(profile);
		
		// Ergebnis zurückgeben
		return result;
	}

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
