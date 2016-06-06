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
	 * Auslesen aller Merkzetteleinträge für ein Profil.
	 *
	 * @return Merkzettel des Profils
	 */
	public Merkzettel findAllForProfil(Profil p) {
		Connection con = DBConnection.connection();
		// Ergebnisvektor vorbereiten
		Merkzettel result = new Merkzettel();
		ArrayList<Profil> profile = new ArrayList<>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT Gemerkter_id "
					+ "FROM Merkzettel WHERE Merker_id=" + p.getId());
			result.setMerkerId(p.getId());

			// Für jeden Eintrag im Suchergebnis wird nun ein Merkzettel-Objekt
			// erstellt.
			while (rs.next()) {
				Profil profil = ProfilMapper.profilMapper().findByKey(rs.getInt("Gemerkter_id"));
				profile.add(profil);
				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Profilliste in Merkzettel schreiben
		result.setGemerkteProfile(profile);
		
		// Ergebnis zurückgeben
		return result;
	}


	/**
	 * Einfügen eines <code>Merkzettel</code>-Objekts in die Datenbank. Dabei
	 wird
	 * auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	 * berichtigt.
	 *
	 * @param m das zu speichernde Objekt
	 * 
	 */
	public Merkzettel insertMerkenForProfil(Profil merker, Profil gemerkter) {

		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			// Jetzt erst erfolgt die tatsächliche Einfügeoperation

			stmt.execute("INSERT INTO Merkzettel(Gemerkter_id, Merker_id)" 
			+ "VALUES ("+gemerkter.getId()+"," + merker.getId()+  ")");
			Merkzettel m = new Merkzettel();

			m.setMerkerId(merker.getId());
			return m;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

		
	}



	/**
	 * Löschen der Daten eines Merkzettel-Eintrags aus der
	 Datenbank.
	 *
	 * @param zwei Profile, der zu löschende und der "löschende"
	 */
	public void deleteMerkenFor(Profil entferner, Profil entfernter) {
		Connection con = DBConnection.connection();
		
		

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM Merkzettel WHERE Merker_id=" +
			entferner.getId()+ " AND Gemerkter_id=" + entfernter.getId());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}



}
