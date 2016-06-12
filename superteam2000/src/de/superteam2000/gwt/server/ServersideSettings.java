package de.superteam2000.gwt.server;

import java.util.logging.Logger;

import de.superteam2000.gwt.shared.CommonSettings;

/**
 * Klasse mit Eigenschaften und Diensten, die für alle Server-seitigen Klassen
 * relevant sind. Es wird ein
 * applikationszentraler Logger realisiert, der mittels
 * ServerSideSettings.getLogger() genutzt werden kann.
 * 
 * @author thies, Christopher Funke, Daniel Volz
 * @version 1.0
 * @since 28.02.2012
 * 
 */
public class ServersideSettings extends CommonSettings {
	private static final String LOGGER_NAME = "Partnerbörse Server";
	private static final Logger log = Logger.getLogger(LOGGER_NAME);
	public static final String URL_EDITOR = "http://127.0.0.1:8888/Superteam2000.html";

	/**
	 * Auslesen des applikationsweiten (Server-seitig!) zentralen Loggers.
	 * 
	 * Anwendungsbeispiel: Zugriff auf den Logger herstellen durch:
	 * 
	 * Logger logger = ServerSideSettings.getLogger();
	 * 
	 * und dann Nachrichten schreiben etwa mittels
	 * 
	 * logger.severe(&quot;Sie sind nicht berechtigt, ...&quot;);
	 * 
	 * oder
	 *
	 * logger.info(&quot;Lege neuen Kunden an.&quot;);
	 * 
	 * 
	 * @return die Logger-Instanz für die Server-Seite
	 */
	
	public static Logger getLogger() {
		return log;
	}

}
