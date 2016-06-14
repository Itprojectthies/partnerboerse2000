package de.superteam2000.gwt.server;

import java.util.logging.Logger;

import de.superteam2000.gwt.shared.CommonSettings;

/**
 * Klasse mit Eigenschaften und Diensten, die für alle Server-seitigen Klassen
 * relevant sind.
 * In ihrem aktuellen Entwicklungsstand bietet die Klasse eine rudimentäre
 * Unterstützung der Logging-Funkionalität unter Java. Es wird ein
 * applikationszentraler Logger realisiert, der mittels
 * ServerSideSettings.getLogger() genutzt werden kann.
 *
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
	 * Zugriff auf den Logger herstellen durch:
	 * Logger log = Logger.getLogger(LOGGER_NAME)
	 * 
	 * Weitere Infos siehe Dokumentation zu Java Logging.
	 * 
	 * @return die Logger-Instanz für die Server-Seite
	 */
	public static Logger getLogger() {
		return log;
	}

}
