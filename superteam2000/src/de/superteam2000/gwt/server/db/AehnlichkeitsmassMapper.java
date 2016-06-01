package de.superteam2000.gwt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Mapper-Klasse, die <code>Account</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 * @see CustomerMapper, TransactionMapper
 * @author Thies
 */
public class AehnlichkeitsmassMapper {

	/**
	 * Die Klasse AehnlichkeitsmassMapper wird nur einmal instantiiert. Man
	 * spricht hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * für sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 * @see AehnlichkeitsmassMapper()
	 */
	private static AehnlichkeitsmassMapper aehnlichkeitsmassMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected AehnlichkeitsmassMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>AehnlichkeitsmassMapper.AehnlichkeitsmassMapper()</code>. Sie
	 * stellt die Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur
	 * eine einzige Instanz von <code>AehnlichkeitsmassMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> AehnlichkeitsmassMapper sollte nicht mittels
	 * <code>new</code> instantiiert werden, sondern stets durch Aufruf dieser
	 * statischen Methode.
	 * 
	 * @return DAS <code>AehnlichkeitsmassMapper</code>-Objekt.
	 * @see AehnlichkeitsmassMapper
	 */
	public static AehnlichkeitsmassMapper aehnlichkeitsmassMapper() {
		if (aehnlichkeitsmassMapper == null) {
			aehnlichkeitsmassMapper = new AehnlichkeitsmassMapper();
		}

		return aehnlichkeitsmassMapper;
	}
	
	/*holt Profilinformationen aus der Datenbank und materialisiert Profil-Objekte, die in ArrayList gespeichert werden */
	
	 public ArrayList<Profil> getProfilesForAehnlichkeitsmass(){
		 Connection con = DBConnection.connection();
			ArrayList<Profil> result = new ArrayList<>();


			try {
				//Statement anlegen
				Statement stmt = con.createStatement();
				
				// Statement ausfullen und als Query an die DB schicken
				ResultSet rs = stmt.executeQuery("SELECT Geburtsdatum, id, Geschlecht, Haarfarbe, Koerpergroesse,Raucher, Religion, Vorname, Nachname FROM Profil ");

						while (rs.next()){
								Profil ap = new Profil();
								ap.setId(rs.getInt("id"));
								ap.setVorname(rs.getString("Vorname"));
								ap.setNachname(rs.getString("Nachname"));
								ap.setHaarfarbe(rs.getString("Haarfarbe"));
								ap.setGeburtsdatum(rs.getDate("Geburtsdatum"));
								ap.setGroesse(rs.getInt("Koerpergroesse"));
								ap.setRaucher(rs.getString("Raucher"));
								ap.setReligion(rs.getString("Religion"));
								ap.setGeschlecht(rs.getString("Geschlecht"));
								ap.setGeburtsdatum(rs.getDate("Geburtsdatum"));
						result.add(ap);
								return result;
						}}
						catch (SQLException e) {
							e.printStackTrace();
							ClientsideSettings.getLogger().severe("Fehler" + 
									e.getMessage() + " " + e.getCause() + " ");
						}
			return result;
	 }
			
				
public ArrayList <Info> getInfoForAehnlichkeitsmass (){
	Connection con = DBConnection.connection();
	ArrayList <Info> result = new ArrayList<>();

	
	try{
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(("SELECT * FROM Info"));
	
			while (rs.next()){
			Info ip= new Info();
			ip.setId(rs.getInt("id"));
			ip.setEigenschaftId(rs.getInt("Eigenschaft_id"));
			ip.setProfilId(rs.getInt("Profil_id"));
			ip.setText(rs.getString("Text"));
			result.add(ip);

		}}
	
	
		catch (SQLException e) {
			e.printStackTrace();
			ClientsideSettings.getLogger().severe("Fehler" + 
			e.getMessage() + " " + e.getCause() + " ");
}
	return result;}


	
}
