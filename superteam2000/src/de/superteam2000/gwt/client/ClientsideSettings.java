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
 * @author Thies, Volz, Funke
 *
 */
public class ClientsideSettings extends CommonSettings {

  /**
   * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen Dienst namens
   * <code>PartnerboerseAdministrationAsync</code>.
   */

  private static PartnerboerseAdministrationAsync partnerboerseVerwaltung = null;

  /**
   * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen Dienst namens
   * <code>ReportGeneratorAsync</code>.
   */
  private static ReportGeneratorAsync reportGenerator = null;

  /**
   * Der momentane Benutzer wird gesetzt.
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
   * Auslesen des applikationsweiten (Client-seitig!) zentralen Loggers.
   *
   * @return die Logger-Instanz für die Server-Seite
   */
  public static Logger getLogger() {
    return log;
  }

  /**
   * Anlegen und Auslesen der applikationsweit eindeutigen PartnerboerseAdministration. Diese Methode
   * erstellt die PartnerboerseAdministration, sofern sie noch nicht existiert. Bei wiederholtem Aufruf
   * dieser Methode wird stets das bereits zuvor angelegte Objekt zurückgegeben.
   *
   * @return eindeutige Instanz des Typs <code>PartnerboerseAdministrationAsync</code>
   * @author Thies, Volz
   */
  public static PartnerboerseAdministrationAsync getPartnerboerseVerwaltung() {
  
	  /*
	   * Wenn es bisher noch keine partnerboerseVerwaltungs-Instanz gab, wird hiermit nun
	   * eine erzeugt.
	   */
    if (partnerboerseVerwaltung == null) {
      	/*
    	 * In diesem Schritt wird die PartnerboerseAdministration instantiiert.
    	 */
      partnerboerseVerwaltung = GWT.create(PartnerboerseAdministration.class);
    }

    /*
     * Die PartnerboerseVerwaltung wird zurückgegeben.
     */
    return partnerboerseVerwaltung;
  }

  /**
   * Anlegen und Auslesen des applikationsweit eindeutigen ReportGenerators. Diese Methode erstellt
   * den ReportGenerator, sofern dieser noch nicht existiert. Bei wiederholtem Aufruf dieser Methode
   * wird stets das bereits zuvor angelegte Objekt zurückgegeben.
   * 
   * @return eindeutige Instanz des Typs <code>ReportGeneratorAsync</code>
   * @author Thies, Volz
   */
  public static ReportGeneratorAsync getReportGenerator() {
  
	  /*
	   * Wenn es noch keine ReportGenerator-Instanz gab, wird diese hier erzeugt.
	   */
    if (reportGenerator == null) {
      
    	/*
    	 * Hier wird der ReportGenerator instantiiert.
    	 */
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

    /*
     * Report Generator wird zurückgegeben.
     */
    return reportGenerator;
  }

  /**
   * Auslesen des momentanen Benutzers
   *
   * @return currentUser Momentaner Benutzer
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
