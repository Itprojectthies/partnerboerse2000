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
 * Klasse mit Eigenschaften und Diensten, die für alle Client-seitigen Klassen relevant sind.
 *
 * @author thies, volz
 * @version 1.0
 * @since 28.02.2012
 *
 */
public class ClientsideSettings extends CommonSettings {

  /**
   * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen Dienst namens
   * <code>BankAdministration</code>.
   */

  private static PartnerboerseAdministrationAsync partnerboerseVerwaltung = null;

  /**
   * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen Dienst namens
   * <code>ReportGenerator</code>.
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
   * @return die Logger-Instanz für die Server-Seite
   */
  public static Logger getLogger() {
    return log;
  }

  /**
   * <p>
   * Anlegen und Auslesen der applikationsweit eindeutigen PartnerboerseAdministration. Diese Methode
   * erstellt die PartnerboerseAdministration, sofern sie noch nicht existiert. Bei wiederholtem Aufruf
   * dieser Methode wird stets das bereits zuvor angelegte Objekt zurückgegeben.
   * </p>
   *
   * @return eindeutige Instanz des Typs <code>PartnerboerseAdministrationAsync</code>
   * @author Peter Thies
   * @since 28.02.2012
   */
  public static PartnerboerseAdministrationAsync getPartnerboerseVerwaltung() {
    // Gab es bislang noch keine BankAdministration-Instanz, dann...
    if (partnerboerseVerwaltung == null) {
      // Zunächst instantiieren wir BankAdministration
      partnerboerseVerwaltung = GWT.create(PartnerboerseAdministration.class);
    }

    // So, nun brauchen wir die BankAdministration nur noch zurückzugeben.
    return partnerboerseVerwaltung;
  }

  /**
   * <p>
   * Anlegen und Auslesen des applikationsweit eindeutigen ReportGenerators. Diese Methode erstellt
   * den ReportGenerator, sofern dieser noch nicht existiert. Bei wiederholtem Aufruf dieser Methode
   * wird stets das bereits zuvor angelegte Objekt zurückgegeben.
   * </p>
   * @return eindeutige Instanz des Typs <code>ReportGeneratorAsync</code>
   * @author Peter Thies
   * @since 28.02.2012
   */
  public static ReportGeneratorAsync getReportGenerator() {
    // Gab es bislang noch keine ReportGenerator-Instanz, dann...
    if (reportGenerator == null) {
      // Zunächst instantiieren wir ReportGenerator
      reportGenerator = GWT.create(ReportGenerator.class);

      final AsyncCallback<Void> initReportGeneratorCallback = new AsyncCallback<Void>() {
        @Override
        public void onFailure(Throwable caught) {
          ClientsideSettings.getLogger()
              .severe("Der ReportGenerator konnte nicht initialisiert werden!");
        }

        @Override
        public void onSuccess(Void result) {
          ClientsideSettings.getLogger().info("Der ReportGenerator wurde initialisiert.");
        }
      };

      reportGenerator.init(initReportGeneratorCallback);
    }

    return reportGenerator;
  }

  /**
   * Auslesen des momentanen Benutzers
   *
   * @return Momentaner Benutzer
   */
  public static Profil getCurrentUser() {
    return currentUser;
  }

  /**
   * Setzen des momentanen Benutzers
   *
   * @param currentUser Momentaner Benutzer
   *
   */
  public static void setCurrentUser(Profil currentUser) {
    ClientsideSettings.currentUser = currentUser;
  }

}
