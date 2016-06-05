package de.superteam2000.gwt.client;



import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.superteam2000.gwt.shared.CommonSettings;
import de.superteam2000.gwt.shared.PartnerboerseAdministration;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.ReportGenerator;
import de.superteam2000.gwt.shared.ReportGeneratorAsync;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Klasse mit Eigenschaften und Diensten, die für alle Client-seitigen Klassen
 * relevant sind.
 * 
 * @author thies, volz, funke
 * @version 2.0
 * @since 28.02.2012
 * 
 */
public class ClientsideSettings extends CommonSettings {

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitigen
	 * Dienst namens <code>partnerboerseVerwaltung</code>.
	 */

	private static PartnerboerseAdministrationAsync partnerboerseVerwaltung = null;

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen
	 * Dienst namens <code>ReportGenerator</code>.
	 */
	private static ReportGeneratorAsync reportGenerator = null;

	/**
	 * Der momentane Benutzer
	 */
	private static Profil currentUser = null;

	/**
	 * Name des Client-seitigen Loggers.
	 */
	private static final String LOGGER_NAME = "Partnerbörse Web Client";

	/**
	 * Instanz des Client-seitigen Loggers.
	 */
	private static final Logger log = Logger.getLogger(LOGGER_NAME);

	/**
	 * <p>
	 * Auslesen des applikationsweiten (Client-seitig!) zentralen Loggers.
	 * </p>
	 * 
	 * <h2>Anwendungsbeispiel:</h2> Zugriff auf den Logger herstellen durch:
	 * 
	 * <pre>
	 * Logger logger = ClientSideSettings.getLogger();
	 * </pre>
	 * 
	 * und dann Nachrichten schreiben etwa mittels
	 * 
	 * <pre>
	 * logger.severe(&quot;Sie sind nicht berechtigt, ...&quot;);
	 * </pre>
	 * 
	 * oder
	 * 
	 * <pre>
	 * logger.info(&quot;Lege neuen Kunden an.&quot;);
	 * </pre>
	 * 
	 * <p>
	 * Bitte auf <em>angemessene Log Levels</em> achten! Severe und info sind
	 * nur Beispiele.
	 * </p>
	 * 
	 * <h2>HINWEIS:</h2>
	 * <p>
	 * Beachten Sie, dass Sie den auszugebenden Log nun nicht mehr durch
	 * bedarfsweise Einfügen und Auskommentieren etwa von
	 * <code>System.out.println(...);</code> steuern. Sie belassen künftig
	 * sämtliches Logging im Code und können ohne abermaliges Kompilieren den
	 * Log Level "von außen" durch die Datei <code>logging.properties</code>
	 * steuern. Sie finden diese Datei in Ihrem <code>war/WEB-INF</code>-Ordner.
	 * Der dort standardmäßig vorgegebene Log Level ist <code>WARN</code>. Dies
	 * würde bedeuten, dass Sie keine <code>INFO</code>-Meldungen wohl aber
	 * <code>WARN</code>- und <code>SEVERE</code>-Meldungen erhielten. Wenn Sie
	 * also auch Log des Levels <code>INFO</code> wollten, müssten Sie in dieser
	 * Datei <code>.level = INFO</code> setzen.
	 * </p>
	 * 
	 * Weitere Infos siehe Dokumentation zu Java Logging.
	 * 
	 * @return die Logger-Instanz für die Server-Seite
	 */
	public static Logger getLogger() {
		return log;
	}

	/**
	 * <p>
	 * Anlegen und Auslesen der applikationsweit eindeutigen PartnerboerseVerwaltung.
	 * Diese Methode erstellt die PartnerboerseVerwaltung, sofern sie noch nicht
	 * existiert. Bei wiederholtem Aufruf dieser Methode wird stets das bereits
	 * zuvor angelegte Objekt zurückgegeben.
	 * </p>
	 * 
	 * <p>
	 * Der Aufruf dieser Methode erfolgt im Client z.B. durch
	 * <code>partnerboerseVerwaltung = GWT.create(PartnerboerseAdministration.class)</code>
	 * .
	 * </p>
	 * 
	 * @return eindeutige Instanz des Typs
	 *         <code>PartnerboerseAdministrationAsync</code>
	 * @author Peter Thies
	 * @since 28.02.2012
	 */
	public static PartnerboerseAdministrationAsync getPartnerboerseVerwaltung() {
		/*
		 * Wenn es bishr noch keine BankAdministrations-Instanz gab, wird hiermit
		 * nun eine erzeugt.
		 */
		
		if (partnerboerseVerwaltung == null) {
			/*
			 * In diesem Schritt wird die PartnerboerseAdministration instantiiert
			 */
			partnerboerseVerwaltung = GWT.create(PartnerboerseAdministration.class);
		}

		/*
		 * Die PartnerboerseVerwaltung wird zur�ckgegeben
		 */
		return partnerboerseVerwaltung;
	}

	/**
	 * <p>
	 * Anlegen und Auslesen des applikationsweit eindeutigen ReportGenerators.
	 * Diese Methode erstellt den ReportGenerator, sofern dieser noch nicht
	 * existiert. Bei wiederholtem Aufruf dieser Methode wird stets das bereits
	 * zuvor angelegte Objekt zurückgegeben.
	 * </p>
	 * 
	 * 
	 * @return eindeutige Instanz des Typs <code>partnerboerseVerwaltung</code>
	 * @author Peter Thies, volz
	 * @since 28.02.2012
	 */
	public static ReportGeneratorAsync getReportGenerator() {
		/*
		 * Wenn noch keine ReportGenerator-Instanz vorhanden war,
		 * wird diese hier erzeugt.
		 */
		if (reportGenerator == null) {
			/*
			 * Hier wird der ReportGenerator instantiiert
			 */
			reportGenerator = GWT.create(ReportGenerator.class);

			final AsyncCallback<Void> initReportGeneratorCallback = new AsyncCallback<Void>() {
				@Override
				public void onFailure(Throwable caught) {
					ClientsideSettings.getLogger().severe("Der ReportGenerator konnte nicht initialisiert werden!");
				}

				@Override
				public void onSuccess(Void result) {
					ClientsideSettings.getLogger().info("Der ReportGenerator wurde initialisiert.");
				}
			};

			reportGenerator.init(initReportGeneratorCallback);
		}

		/*
		 * ReportGenerator wird zur�ckgegeben
		 */
		return reportGenerator;
	}

	/**
	 * Auslesen des momentanen Benutzers
	 * @return currentUser momentan genutzter Benutzer 
	 */
	public static Profil getCurrentUser() {
		return currentUser;
	}

	/**
	 * Setzen des momentanen Benutzers
	 * @param currentUser Momentaner Benutzer
	 * 
	 */
	public static void setCurrentUser(Profil currentUser) {
		ClientsideSettings.currentUser = currentUser;
	}

}
