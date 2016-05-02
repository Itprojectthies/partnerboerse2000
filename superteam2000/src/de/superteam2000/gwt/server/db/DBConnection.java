package de.superteam2000.gwt.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;


import com.google.appengine.api.utils.SystemProperty;

import de.superteam2000.gwt.client.ClientsideSettings;

/**
 * Verwalten einer Verbindung zur Datenbank.
 * <p>
 * <b>Vorteil:</b> Sehr einfacher Verbindungsaufbau zur Datenbank.
 * <p>
 * <b>Nachteil:</b> Durch die Singleton-Eigenschaft der Klasse kann nur auf eine
 * fest vorgegebene Datenbank zugegriffen werden.
 * <p>
 * In der Praxis kommen die meisten Anwendungen mit einer einzigen Datenbank
 * aus. Eine flexiblere Variante für mehrere gleichzeitige
 * Datenbank-Verbindungen wäre sicherlich leistungsfähiger. Dies würde
 * allerdings den Rahmen dieses Projekts sprengen bzw. die Software unnötig
 * verkomplizieren, da dies für diesen Anwendungsfall nicht erforderlich ist.
 * 
 * @author Thies
 */
public class DBConnection {

	/**
	 * Die Klasse DBConnection wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * für sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 * @see AccountMapper.accountMapper()
	 * @see CustomerMapper.customerMapper()
	 */
	private static Connection con = null;

	
	/**
	 * Die URL, mit deren Hilfe die Datenbank angesprochen wird. In einer
	 * professionellen Applikation würde diese Zeichenkette aus einer
	 * Konfigurationsdatei eingelesen oder über einen Parameter von außen
	 * mitgegeben, um bei einer Veränderung dieser URL nicht die gesamte
	 * Software neu komilieren zu müssen.
	 */
	

	private static String googleUrl = "jdbc:google:mysql://partnerboerse2000:partnerboerse2000-db/partnerboerse2000?user=root&password=test";
	private static String localUrl = "jdbc:mysql://127.0.0.1:3306/partnerboerse2000?user=root&password=";


	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>DBConnection.connection()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine
	 * einzige Instanz von <code>DBConnection</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> DBConnection sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * <p>
	 * 
	 * <b>Nachteil:</b> Bei Zusammenbruch der Verbindung zur Datenbank - dies
	 * kann z.B. durch ein unbeabsichtigtes Herunterfahren der Datenbank
	 * ausgelöst werden - wird keine neue Verbindung aufgebaut, so dass die in
	 * einem solchen Fall die gesamte Software neu zu starten ist. In einer
	 * robusten Lösung würde man hier die Klasse dahingehend modifizieren, dass
	 * bei einer nicht mehr funktionsfähigen Verbindung stets versucht würde,
	 * eine neue Verbindung aufzubauen. Dies würde allerdings ebenfalls den
	 * Rahmen dieses Projekts sprengen.
	 * 
	 * @return DAS <code>DBConncetion</code>-Objekt.
	 * @see con
	 */
	public static Connection connection() {
		Logger logger = ClientsideSettings.getLogger();
		// Wenn es bisher keine Conncetion zur DB gab, ...
		if (con == null) {
			String url = null;
			try {
				if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
					
					logger.info("produktions modus");
					// Load the class that provides the new
					// "jdbc:google:mysql://" prefix.
					Class.forName("com.mysql.jdbc.GoogleDriver");
					url = googleUrl;
				} else {
					// Local MySQL instance to use during development.
					Class.forName("com.mysql.jdbc.Driver");
					url = localUrl;
					logger.info("lokaler modus");
				}
				/*
				 * Dann erst kann uns der DriverManager eine Verbindung mit den
				 * oben in der Variable url angegebenen Verbindungsinformationen
				 * aufbauen.
				 * 
				 * Diese Verbindung wird dann in der statischen Variable con
				 * abgespeichert und fortan verwendet.
				 */
				con = DriverManager.getConnection(url);
				System.out.println("Verbindung geklappt");
				logger.info("verbindung geklappt");
			} catch (Exception e) {
				con = null;
				e.printStackTrace();
				logger.info("netzwerkverbindung fehlgeschlagen");
			}
		}

		// Zurückgegeben der Verbindung
		return con;
	}

}
