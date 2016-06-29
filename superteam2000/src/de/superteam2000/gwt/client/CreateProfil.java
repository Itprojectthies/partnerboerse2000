package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.superteam2000.gwt.client.gui.BoxPanel;
import de.superteam2000.gwt.client.gui.EigenschaftListBox;
import de.superteam2000.gwt.client.gui.Notification;
import de.superteam2000.gwt.client.gui.ProfilAttributListbox;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Formular zum Erstellen eines Nutzers
 *
 * @author Christian Rathke, Volz Daniel, Christopher Funke
 */
public class CreateProfil extends BasicFrame {

	/**
	 * Hier wird die Verbindung zum Datenbankmapper und somit zur Datenbank und zur Partnerboerse
	 * hergestellt bzw. gespeichert.
	 */
  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

  /*
   * Alle notwendigen Instanzvariablen werden deklariert
   */

  Profil user = ClientsideSettings.getCurrentUser();
  Logger logger = ClientsideSettings.getLogger();

  FlowPanel contentPanel = new FlowPanel();
  FlowPanel alignPanel = new FlowPanel();

  ProfilAttributListbox gebTag = null;
  ProfilAttributListbox groesse = null;

  Button confirmBtn = null;

  /**
   * HeadlineText wird erzeugt
   * @return Text wird zurückgegeben
   */
  @Override
  public String getHeadlineText() {
    return "Profil erstellen";
  }

  /**
   * SubHeadline wird erzeugt
   * @return Text wird zurückgegeben
   */
  @Override
  protected String getSubHeadlineText() {
    return "Erstelle dein Profil und lege gleich los!";
  }

  /**
   * Im Konstruktor werden die anderen Widgets erzeugt. Alle werden in einem Raster
   * angeordnet, dessen Groesse sich aus dem Platzbedarf der enthaltenen Widgets bestimmmt.
   * Wenn ein neuer User sich registrieren/anmelden will, werden die Grunddaten, welche
   * nicht geloescht werden koennen, angelegt. Diese Methode bietet die Moeglichekit,
   * die Werte/Daten einzugeben und mit der Registrierung fortzufahren. Die beiden Attribute
   * Koerpergroesse und Geburtstag sind als Drop-down-Listen realisiert.
   * 
   * @param gebTag Der Beschreibungstext des Panels wird gespeichert.
   * @param groesse Der Beschreibungstext des Panels wird hierin gespeichert.
   * @param pbVerwaltung Die eingegebenen Werte werden hier gespeichert.
   * @param confirmBtn Ein neuer Button namens "Weiter" wird erstellt, zum absenden der
   * 					eingegebenen Daten an die DB.
   */
  @Override
  public void run() {
    contentPanel.setStyleName("content");
    alignPanel.setStyleName("pure-form pure-form-aligned");

    gebTag = new ProfilAttributListbox();
    gebTag.createGebtaListobx("Was ist dein Geburtstag");
    gebTag.setEnable(true);

    groesse = new ProfilAttributListbox();
    groesse.createGroesseListBox("Was ist deine KÃ¶rpergrÃ¶ÃŸe");
    groesse.setEnable(true);

    confirmBtn = new Button("Weiter");
    confirmBtn.setStyleName("pure-button pure-button-primary");

    confirmBtn.addClickHandler(new ConfirmClickHandler());

    pbVerwaltung.getAllBeschreibungProfilAttribute(new GetAllBeschreibungProfilAttributeCallBack());
    pbVerwaltung.getAllAuswahlProfilAttribute(new GetAllAuswahlProfilAttributeCallBack());

    contentPanel.add(alignPanel);

    RootPanel.get("main").add(contentPanel);

  }

  /**
   * Für den neuen User wird ein leeres Formular angelegt.
   * 
   * @param clb Befüllte Formularergebnisse werden uebertragen
   * @author Volz, Funke
   *
   */
  private class GetAllBeschreibungProfilAttributeCallBack
      implements AsyncCallback<ArrayList<Beschreibung>> {

    @Override
    public void onSuccess(ArrayList<Beschreibung> result) {
      for (Beschreibung b : result) {

        BoxPanel clb = new BoxPanel(b, false);
        alignPanel.add(clb);
      }
    }

    /**
     * Um Fehler abzufangen
     */
    @Override
    public void onFailure(Throwable caught) {}
  }

  /**
   * Die beiden Attribute Groesse und Geburtstag werden dem Panel hinzugefuegt.
   * 
   * @param clb Die ausgewaehlten Elemente werden abgespeichert.
   * @author Volz, Funke
   *
   */
  private class GetAllAuswahlProfilAttributeCallBack implements AsyncCallback<ArrayList<Auswahl>> {

    @Override
    public void onSuccess(ArrayList<Auswahl> result) {
      for (Auswahl a : result) {
        BoxPanel clb = new BoxPanel(a, false);
        alignPanel.add(clb);
      }

      alignPanel.add(groesse);
      alignPanel.add(gebTag);
      alignPanel.add(confirmBtn);
    }

    /**
     * Um Fehler abzufangen.
     */
    @Override
    public void onFailure(Throwable caught) {}
  }

  /**
   * Der ClickHandler wird eingebunden.
   * 
   * @author Funke
   *
   */
  private class ConfirmClickHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {
      createProfil();

    }
  }

  /**
   * In dieser Klasse sind alle Methoden enthalten, womit das erstellte Profil eines Users
   * angezeigt werden kann.
   * @author Volz
   *
   */
  class CreateCustomerCallback implements AsyncCallback<Profil> {

	  /**
	   * @throws exception Wenn User nicht angemeldet werden konnte, wird eine Fehler-
	   * 					meldung zurueckgegeben.
	   */
    @Override
    public void onFailure(Throwable caught) {
      logger.severe("Erstellen des useres hat nicht funktioniert");

    }

    /**
     * Wenn das Profil erfolgreich erstellt und gespeichert werden konnte. Danach
     * wird das gespeicherte Profil angezeigt. Dazu werden die Navigationsbar und
     * die Details ausgegeben.
     * 
     * @param p gegenwaertig gespeichertes Profil
     * @param nb Navigationsleiste/-bar
     */
    @Override
    public void onSuccess(Profil p) {
      p.setLogoutUrl(user.getLogoutUrl());
      p.setLoggedIn(true);
      
      ClientsideSettings.setCurrentUser(p);
      
      ShowProfil sp = new ShowProfil();
      Navbar nb = new Navbar();
      RootPanel.get("main").clear();
      RootPanel.get("menu").add(nb);
      RootPanel.get("main").add(sp);
    }

  }

  /**
   * Das Profil wird angelegt.
   * 
   * @param firstName Vorname wird abgespeichert.
   * @param lasName Nachname wird abgespeichert.
   * @param haarfarbe Haarfarbe wird gespeichert.
   * @param raucher Auswahl bei Raucher wird gespeichert.
   * @param religion Religionsauswahl wird gespeichert.
   * @param geschlecht Auswahl des Geschlechts wird gespeichert.
   * @param email EMail Adresse speichern.
   * @param groesse gibt Mindestwert fuer Koerpergroesse an.
   * @param geburtsTag Hilfsvariable um das Alter zu berechnen/Geburtstag kann nicht negativ sein
   * @param geburtsMonat Hilfsvariable um das Alter zu berechnen/Geburtstag kann nicht negativ sein
   * @param geburtsJahr Hilfsvariable um das Alter zu berechnen/Geburtstag kann nicht negativ sein
   */
  private void createProfil() {
    String firstName = "";
    String lastName = "";

    String haarfarbe = "";
    String raucher = "";
    String religion = "";
    String geschlecht = "";
    String email = user.getEmail();

    int groesse = 141;

    int geburtsTag = 2;
    int geburtsMonat = 2;
    int geburtsJahr = 1901;

    /*
     * Verschachtelte Schleifen, damit die Listboxen der beiden ineinander verschachtel-
     * ten Panels ausgelesen werden koennen. Im folgenden werden die einzelnen Werte
     * fuer die Attribute des Users eingelesen und in den jeweiligen Variablen gespeichert.
     */

    for (Widget child : alignPanel) {
      if (child instanceof FlowPanel) {
        FlowPanel childPanel = (FlowPanel) child;
        for (Widget box : childPanel) {
          if (box instanceof EigenschaftListBox) {
            EigenschaftListBox lb = (EigenschaftListBox) box;
            logger.severe("test " + lb.getName());

            switch (lb.getName()) {

              case "Raucher":
                raucher = lb.getSelectedItemText();
                break;
              case "Haarfarbe":
                haarfarbe = lb.getSelectedItemText();
                break;
              case "Religion":
                religion = lb.getSelectedItemText();
                break;
              case "Geschlecht":
                geschlecht = lb.getSelectedItemText();
                break;
              case "KÃ¶rpergrÃ¶ÃŸe":
                groesse = Integer.valueOf(lb.getSelectedItemText());
                break;
              case "GeburtstagTag":
                geburtsTag = Integer.valueOf(lb.getSelectedItemText());
                break;
              case "GeburtstagMonat":
                geburtsMonat = Integer.valueOf(lb.getSelectedItemText());
                break;
              case "GeburtstagJahr":
                geburtsJahr = Integer.valueOf(lb.getSelectedItemText());
                break;

            }

          } else if (box instanceof TextBox) {
            TextBox tb = (TextBox) box;
            logger.severe("test " + tb.getName());
            switch (tb.getName()) {
              case "Vorname":
                firstName = tb.getText();
                break;
              case "Nachname":
                lastName = tb.getText();
                break;

            }
          }

        }
      }

    }

    /*
     * Um das Alter abspeichern zu koennen, wird das Geburtsdatum eingelesen und in der richtigen
     * Reihenfolge abgespeichert. Danach wird das korrekte Geburtsdatum in ein SQL-Date-Objekt 
     * umgewandelt, um dort richtig gespeichert zu werden.
     */
    Date gebTag = DateTimeFormat.getFormat("yyyy-MM-dd")
        .parse(geburtsJahr + "-" + geburtsMonat + "-" + geburtsTag);
    java.sql.Date gebTagSql = new java.sql.Date(gebTag.getTime());

    /*
     * Profil wird angelegt und in die Datenbank geschrieben, mit allen eingegebenen Werten.
     */
    if (!firstName.isEmpty() && !lastName.isEmpty()) {
      pbVerwaltung.createProfil(lastName, firstName, email, gebTagSql, haarfarbe, raucher, religion,
          groesse, geschlecht, new CreateCustomerCallback());
    } 
    /*
     * Falls nicht alle Felder ausgefuellt wurden, erfolgt eine Warnung.
     * Alle Pflichtfelder muessen ausgefuellt werden.
     */
    else {
      new Notification("Bitte fÃ¼llen sie alle Felder aus", "warning");
    }
  }

}