package de.superteam2000.gwt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.shared.bo.Beschreibung;
	 /**
	 * Klasse, die die Aufgabe erfüllt, die Objekte einer persistenten Klasse auf die Datenbank abzubilden und dort zu speichern.
	 * Die zu speichernden Objekte werden dematerialisiert und zu gewinnende Objekte aus der Datenbank entsprechend materialisiert. Dies wird
	 * als indirektes Mapping bezeichnet. Zur Verwaltung der Objekte implementiert die Mapper-Klasse entsprechende Methoden zur Suche, zum Speichern, Löschen und 
	 * Modifizieren von Objekten.
	 * @see AehnlichkeitsMapper
	 * @see AuswahlMapper
	 * @see DBConnection
	 * @see InfoMapper
	 * @see KontaktsperreMapper
	 * @see MerkzettelMapper
	 * @see ProfilMapper
	 * @see SuchprofilMapper

	 * @author 
	 */

public class BeschreibungMapper {

	/**
	 * Von der Klasse BeschreibungMapper kann nur eine Instanz erzeugt werden. Sie erfüllt die Singleton-Eigenschaft.
	 * Dies geschieht mittels eines private default-Konstruktors und genau einer statischen Variablen vom 
	 * Typ BeschreibungMapper, die die einzige Instanz der Klasse darstellt.
	 *
	 * 
	 */
	private static BeschreibungMapper BeschreibungMapper = null;

	/**
	 * Durch den Modifier "private" geschützter Konstruktor, der verhindert das weiter Instanzen der Klasse erzeugt werden können
	 *  
	 */
	protected BeschreibungMapper() {
	}

	/**
	 * Von der Klasse AuswahlMapper kann nur eine Instanz erzeugt werden. Sie erfüllt die Singleton-Eigenschaft.
	 * Dies geschieht mittels eines private default-Konstruktors und genau einer statischen Variablen vom 
	 * Typ AuswahlMapper, die die einzige Instanz der Klasse darstellt.
	 */
	
	public static BeschreibungMapper beschreibungMapper() {
		if (BeschreibungMapper == null) {
			BeschreibungMapper = new BeschreibungMapper();
		}

		return BeschreibungMapper;
	}
	
	
	/**
	 * Die Methode findByName erfüllt eine Suchfunktion und liefert Objekte des Typs Beschreibung aus der Datenbank zurück
	 * 
	 * @param name 
	 * @return Beschreibung - Ein Beschreibungs-Objekt, in dem Informationen des Objekts Beschreibung aus der Datenbank gespeichert werden
	 */
	
	
	public Beschreibung findByName(String name) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();
			
			// Statement ausfÃ¼llen und als Query an die DB schicken
			ResultSet rs1 = stmt.executeQuery(
					"SELECT id, Name, Beschreibungstext FROM Eigenschaft WHERE Name='" 
					+ name + "' AND e_typ='p_b'");

			
			/*
			 * Da id PrimÃ¤rschlÃ¼ssel ist, kann max. nur ein Tupel zurÃ¼ckgegeben
			 * werden. PrÃ¼fe, ob ein Ergebnis vorliegt.
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
	 * Die Methode findByKey implementiert die Suche nach genau einer id aus der Datenbank, entsprechend wird 
	 * genau ein Objekt zurückgegeben.
	 * 
	 * @param id
	 * 
	 * @return Beschreibung, Beschreibungs-Objekt, das der übergegebenen id entspricht, bzw. null bei
	 *         nicht vorhandenem DB-Tupel.
	 */
	public Beschreibung findByKey(int id) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();
			
			// Statement ausfÃ¼llen und als Query an die DB schicken
			ResultSet rs1 = stmt.executeQuery(
					"SELECT id, Name, Beschreibungstext FROM Eigenschaft WHERE id=" + id + " AND e_typ='b'");

			
			/*
			 * Da id PrimÃ¤rschlÃ¼ssel ist, kann max. nur ein Tupel zurÃ¼ckgegeben
			 * werden. PrÃ¼fe, ob ein Ergebnis vorliegt.
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
	 * Auslesen aller Auswahl-Tupel.
	 *
	 * @return Eine ArrayList mit Auswahl-Objekten
	 */
	public ArrayList<Beschreibung> findAll() {
		Connection con = DBConnection.connection();
		// Ergebnisvektor vorbereiten
		ArrayList<Beschreibung> result = new ArrayList<Beschreibung>();

		try {
			Statement stmt1 = con.createStatement();
			ResultSet rs1 = stmt1.executeQuery("SELECT id, Name, Beschreibungstext FROM Eigenschaft WHERE e_typ='b'");
			
			
			// FÃ¼r jeden Eintrag im Suchergebnis wird nun ein Auswahl-Objekt
			// erstellt.
			
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

		// Ergebnisvektor zurÃ¼ckgeben
		return result;
	}
	
	
	/**
	 * Auslesen aller auf die Datenbank abgebildeten Datenbank-Objekte.
	 *
	 * @return ArrayList <Beschreibung> - ArrayList mit Beschreibungs-Objekten, bei einem Fehler wird eine SQL-Exception ausgelöst
	 */
	public ArrayList<Beschreibung> findAllProfilAttribute() {
		Connection con = DBConnection.connection();
		// Ergebnisvektor vorbereiten
		ArrayList<Beschreibung> result = new ArrayList<Beschreibung>();
		
		try {
			Statement stmt1 = con.createStatement();
			ResultSet rs1 = stmt1.executeQuery("SELECT id, Name, Beschreibungstext FROM Eigenschaft WHERE e_typ='p_b'");
			
			
			// FÃ¼r jeden Eintrag im Suchergebnis wird nun ein Auswahl-Objekt
			// erstellt.
			
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
		
		// Ergebnisvektor zurÃ¼ckgeben
		return result;
	}

	/**
	 * Hinzufügen eines Beschreibungs-Objekts in die Datenbank. 
	 *
	 * @param a - das zu speichernde Objekt
	 *            
	 * @return das an die Datenbank übergebene Objekt
	 */
	public Beschreibung insert(Beschreibung b) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/*
			 * ZunÃ¤chst schauen wir nach, welches der momentan hÃ¶chste
			 * PrimÃ¤rschlÃ¼sselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM Eigenschaft ");

			// Wenn wir etwas zurÃ¼ckerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/*
				 * a erhÃ¤lt den bisher maximalen, nun um 1 inkrementierten
				 * PrimÃ¤rschlÃ¼ssel.
				 */
				b.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsÃ¤chliche EinfÃ¼geoperation
				stmt.executeUpdate("INSERT INTO Eigenschaft (id, Name, Beschreibungstext, e_typ) VALUES (" + b.getId() + ",'"
						+ b.getName() + "','" + b.getBeschreibungstext() + "','b')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/*
		 * RÃ¼ckgabe, des evtl. korrigierten Profils.
		 *
		 * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen
		 * Objekte Ã¼bergeben werden, wÃ¤re die Anpassung des Auswahl-Objekts auch
		 * ohne diese explizite RÃ¼ckgabe auÃŸerhalb dieser Methode sichtbar. Die
		 * explizite RÃ¼ckgabe von a ist eher ein Stilmittel, um zu
		 * signalisieren, dass sich das Objekt evtl. im Laufe der Methode
		 * verÃ¤ndert hat.
		 */
		return b;
	}


	/**
	 * Die Methode update modifiziert ein auf die Datenbank abgebildetes Beschreibungs-Objekt.
	 * @param a - das Objekt, welches in der Datenbank geändert wird
	 * @return das als Parameter übergebene Objekt
	 */
	public Beschreibung update(Beschreibung b) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE Eigenschaft SET Name='" + b.getName() + "', Beschreibungstext='"
					+ b.getBeschreibungstext() + "', e_typ='a' WHERE id=" + b.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Um Analogie zu insert(Auswahl a) zu wahren, geben wir a zurÃ¼ck
		return b;
	}

	/**
	 * Löschen eines auf die Datenbank abgebildeteten Auswahl-Objekts
	 *
	 * @param a das aus der DB zu löschende Objekt
	 */
	public void delete(Beschreibung b) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM Eigenschaft WHERE id=" + b.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}