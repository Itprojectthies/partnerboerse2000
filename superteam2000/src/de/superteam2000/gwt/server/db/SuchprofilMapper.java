package de.superteam2000.gwt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.bo.Suchprofil;

public class SuchprofilMapper {

	private static SuchprofilMapper suchprofilMapper = null;

	protected SuchprofilMapper() {
	}

	public static SuchprofilMapper suchprofilMapper() {
		if (suchprofilMapper == null) {
			suchprofilMapper = new SuchprofilMapper();
		}

		return suchprofilMapper;
	}

	/**
	 * Auslesen aller Kunden.
	 * 
	 * @return Ein Vektor mit Profil-Objekten, die sämtliche Kunden
	 *         repräsentieren. Bei evtl. Exceptions wird ein partiell gefüllter
	 *         oder ggf. auch leerer Vetor zurückgeliefert.
	 */
	public Suchprofil findSuchprofilForProfilByName(Profil p, String name) {
		Connection con = DBConnection.connection();
		// Ergebnisvektor vorbereiten
		Suchprofil sp = new Suchprofil();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT id, Name, Profil_id, Haarfarbe, Raucher, Religion, Geschlecht, "
					+ "Koerpergroesse_min, Koerpergroesse_max, Alter_min, Alter_max, "
					+ "Auswahl_text_1, Auswahl_id_1, Auswahl_text_2, Auswahl_id_2, Auswahl_text_3, "
					+ "Auswahl_id_3, Auswahl_text_4, Auswahl_id_4, Auswahl_text_5, Auswahl_id_5 "
					+ "FROM Suchprofil WHERE Profil_id="+ p.getId()+" AND Name='"+name+"'");

			// Für jeden Eintrag im Suchergebnis wird nun ein Profil-Objekt
			// erstellt.

			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				sp.setId(rs.getInt("id"));
				sp.setName(rs.getString("Name"));
				sp.setProfilId(rs.getInt("Profil_id"));
				sp.setHaarfarbe(rs.getString("Haarfarbe"));
				sp.setRaucher(rs.getString("Raucher"));
				sp.setReligion(rs.getString("Religion"));
				sp.setGeschlecht(rs.getString("Geschlecht"));
				sp.setGroesse_min(rs.getInt("Koerpergroesse_min"));
				sp.setGroesse_max(rs.getInt("Koerpergroesse_max"));
				sp.setAlter_min(rs.getInt("Alter_min"));
				sp.setAlter_max(rs.getInt("Alter_max"));
				
				HashMap<Integer, String> auswahlListe = new HashMap<>();
				for (int i = 1; i <= 5; i++) {
					auswahlListe.put(rs.getInt("Auswahl_id_"+i), rs.getString("Auswahl_text_"+i));
				}
				sp.setAuswahlListe(auswahlListe);
				// Hinzufügen des neuen Objekts zum Ergebnisvektor
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ClientsideSettings.getLogger().severe("Fehler beim schreiben in die DB" + 
					e.getMessage() + " " + e.getCause() + " ");
		}

		// Ergebnisvektor zurückgeben
		return sp;
	}
	
	/**
	 * Auslesen aller Kunden.
	 * 
	 * @return Ein Vektor mit Profil-Objekten, die sämtliche Kunden
	 *         repräsentieren. Bei evtl. Exceptions wird ein partiell gefüllter
	 *         oder ggf. auch leerer Vetor zurückgeliefert.
	 */
	public ArrayList<Suchprofil> findAllForProfil(Profil p) {
		Connection con = DBConnection.connection();
		// Ergebnisvektor vorbereiten
		ArrayList<Suchprofil> result = new ArrayList<>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT id, Name, Profil_id, Haarfarbe, Raucher, Religion, Geschlecht, "
					+ "Koerpergroesse_min, Koerpergroesse_max, Alter_min, Alter_max, "
					+ "Auswahl_text_1, Auswahl_id_1, Auswahl_text_2, Auswahl_id_2, Auswahl_text_3, "
					+ "Auswahl_id_3, Auswahl_text_4, Auswahl_id_4, Auswahl_text_5, Auswahl_id_5 "
					+ "FROM Suchprofil WHERE Profil_id="+ p.getId());

			// Für jeden Eintrag im Suchergebnis wird nun ein Profil-Objekt
			// erstellt.

			while (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Suchprofil sp = new Suchprofil();
				sp.setId(rs.getInt("id"));
				sp.setName(rs.getString("Name"));
				sp.setProfilId(rs.getInt("Profil_id"));
				sp.setHaarfarbe(rs.getString("Haarfarbe"));
				sp.setRaucher(rs.getString("Raucher"));
				sp.setReligion(rs.getString("Religion"));
				sp.setGeschlecht(rs.getString("Geschlecht"));
				sp.setGroesse_min(rs.getInt("Koerpergroesse_min"));
				sp.setGroesse_max(rs.getInt("Koerpergroesse_max"));
				sp.setAlter_min(rs.getInt("Alter_min"));
				sp.setAlter_max(rs.getInt("Alter_max"));
				
				HashMap<Integer, String> auswahlListe = new HashMap<>();
				for (int i = 1; i <= 5; i++) {
					auswahlListe.put(rs.getInt("Auswahl_id_"+i), rs.getString("Auswahl_text_"+i));
				}
				sp.setAuswahlListe(auswahlListe);
				// Hinzufügen des neuen Objekts zum Ergebnisvektor
				result.add(sp);
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
	 * Einfügen eines <code>Profil</code>-Objekts in die Datenbank. Dabei wird
	 * auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	 * berichtigt.
	 * 
	 * @param currentProfil das zu speichernde Objekt
	 * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter
	 *         <code>id</code>.
	 */
	public Suchprofil insert(Suchprofil sp) {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			Statement stmt2 = con.createStatement();
			/*
			 * Zunächst schauen wir nach, welches der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM Suchprofil");
			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/*
				 * p erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
				sp.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();
				stmt2 = con.createStatement();

				// Jetzt erst erfolgt die tatsächliche Einfügeoperation
				stmt.executeUpdate("INSERT INTO Suchprofil (id, Name, Profil_id, "
						+ "Haarfarbe, Raucher, Religion, Geschlecht, Koerpergroesse_min,"
						+ " Koerpergroesse_max, Alter_min, Alter_max) VALUES ("
						+ sp.getId() + ",'" + sp.getName() + "'," + sp.getProfilId() + ",'"
						+ sp.getHaarfarbe() + "','" + sp.getRaucher() + "','" + sp.getReligion()
						+ "','" + sp.getGeschlecht() + "'," + sp.getGroesse_min() + ","
						+ sp.getGroesse_max() + "," + sp.getAlter_min() + "," + sp.getAlter_max()+ ")");
				
				
				HashMap<Integer, String> auswahlListe = sp.getAuswahlListe();
				
				int i = 1;
				for (Map.Entry<Integer, String> entry : auswahlListe.entrySet()) {
				    stmt2.executeUpdate("UPDATE Suchprofil SET "
				    		+ "Auswahl_text_"+i+"='"+entry.getValue()+"', "
				    				+ "Auswahl_id_"+i+"="+entry.getKey()+" WHERE id=" + sp.getId() );
				    i++;
				}
				
				
				ClientsideSettings.getLogger().info("Suchprofil " + sp.getName() + " in DB geschrieben");

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			ClientsideSettings.getLogger()
					.severe("Fehler beim schreiben in die DB" + e.getMessage() + " " + e.getCause() + " ");
		}

		
		return sp;
	}

	/**
	 * Löschen der Daten eines <code>Profil</code>-Objekts aus der Datenbank.
	 * 
	 * @param currentProfil das aus der DB zu löschende "Objekt"
	 */
	public void delete(Suchprofil sp) {
		//TODO: alle FK beziehnungen löschen bevor profil löschen
		
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Suchprofil WHERE id=" + sp.getId());
			
		} catch (SQLException e) {
			e.printStackTrace();
			ClientsideSettings.getLogger().severe("Fehler beim schreiben in die DB: " + 
					e.getMessage() + " " + e.getCause() + " ");
		}
	}
	
	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param sp das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */
	public Suchprofil update(Suchprofil sp) {
		Connection con = DBConnection.connection();
		// Jetzt erst erfolgt die tatsächliche Einfügeoperation
		try {
			Statement stmt = con.createStatement();
			Statement stmt2 = con.createStatement();

			stmt.executeUpdate("UPDATE Suchprofil SET Name='" + sp.getName() + "', Haarfarbe='"
					+ sp.getHaarfarbe() + "', Raucher='" + sp.getRaucher() + "', Religion='"
					+ sp.getReligion() + "', Geschlecht='" + sp.getGeschlecht() + "', "
					+ "Koerpergroesse_min=" + sp.getGroesse_min() + ", Koerpergroesse_max="
					+ sp.getGroesse_max() + ", Alter_min=" + sp.getAlter_min() + ", Alter_max="
					+ sp.getAlter_max() + " WHERE id=" + sp.getId());
			
			
			HashMap<Integer, String> auswahlListe = sp.getAuswahlListe();
			
			int i = 1;
			for (Map.Entry<Integer, String> entry : auswahlListe.entrySet()) {
			    stmt2.executeUpdate("UPDATE Suchprofil SET "
			    		+ "Auswahl_text_"+i+"='"+entry.getValue()+"', "
			    				+ "Auswahl_id_"+i+"="+entry.getKey()+" WHERE id=" + sp.getId() );
			    i++;
			}
			
			ClientsideSettings.getLogger().info("Suchprofil " +sp.getName() + " Änderungen in DB geschrieben");

		} catch (SQLException e) {
			e.printStackTrace();
			ClientsideSettings.getLogger().severe("Fehler beim schreiben in die DB" + 
					e.getMessage() + " " + e.getCause() + " ");

		}

		// Um Analogie zu insert(Profil p) zu wahren, geben wir p zurück
		return sp;
	}
	
}
