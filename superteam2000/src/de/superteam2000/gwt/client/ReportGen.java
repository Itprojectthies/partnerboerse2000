package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

import de.superteam2000.gwt.client.gui.CustomPopupPanel;
import de.superteam2000.gwt.client.gui.ListItemWidget;
import de.superteam2000.gwt.client.gui.UnorderedListWidget;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.ReportGeneratorAsync;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.bo.Suchprofil;
import de.superteam2000.gwt.shared.report.AllNewProfileReport;
import de.superteam2000.gwt.shared.report.AllNotVisitedProfileReport;
import de.superteam2000.gwt.shared.report.AllProfilesBySucheReport;
import de.superteam2000.gwt.shared.report.AllProfilesReport;
import de.superteam2000.gwt.shared.report.HTMLReportWriter;
import de.superteam2000.gwt.shared.report.ProfilReport;

/**
 * Entry point Klasse des ReportGenerators
 * 
 * @author Volz, Funke
 */

public class ReportGen implements EntryPoint {
  /**
   * Alle notwendigen Instanzvariablen werden deklariert
   */

  Logger logger = ClientsideSettings.getLogger();
  ReportGeneratorAsync reportGenerator = ClientsideSettings.getReportGenerator();
  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

  Anchor profilAnzeigenAnchor = new Anchor("Mein Profil");
  Anchor alleProfileAnzeigenAnchor = new Anchor("Alle Profile");
  Anchor alleNeuenProfileAnzeigenAnchor = new Anchor("Neue Profile");
  Anchor alleNichtBesuchtProfileAnzeigenAnchor = new Anchor("Nicht besuchte Profile");
  Anchor sucheAnchor = new Anchor("Suche");

  @SuppressWarnings("deprecation")
  ListBox suchProfilListBox = new ListBox(true);


  ArrayList<Profil> profile = new ArrayList<Profil>();
  ArrayList<Profil> profileForSuchprofil = new ArrayList<Profil>();
  Profil p = new Profil();

  Suchprofil sp = new Suchprofil();
  ArrayList<Suchprofil> suchProfilListe = new ArrayList<>();

  FlowPanel menu = new FlowPanel();
  UnorderedListWidget menuList = new UnorderedListWidget();
  FlowPanel pureMenu = new FlowPanel();
  
  CustomPopupPanel pop = new CustomPopupPanel(false, true);

  /**
   * Einstiegspunkt. Da ReportGen das Interface EntryPoint implementiert, muss diese 
   * Methode implementiert werden.
   * Sie kann analog zur main-methode in Java Applikationen gesehen werden.
   */
  @Override
  public void onModuleLoad() {      
    
    suchProfilListBox.setSize("11em", "8em");

    RootPanel.get("menu").getElement().getStyle().setBackgroundColor("#b75d6b");

    Anchor anchor = new Anchor("PartnerBörse", GWT.getHostPageBaseURL() + "Superteam2000.html");

    menuList.setStyleName("pure-menu-list");
    anchor.setStyleName("pure-menu-heading");
    pureMenu.setStyleName("pure-menu");

    profilAnzeigenAnchor.setStyleName("pure-menu-link");
    menuList.add(new ListItemWidget(profilAnzeigenAnchor));

    alleProfileAnzeigenAnchor.setStyleName("pure-menu-link");
    menuList.add(new ListItemWidget(alleProfileAnzeigenAnchor));

    alleNeuenProfileAnzeigenAnchor.setStyleName("pure-menu-link");
    menuList.add(new ListItemWidget(alleNeuenProfileAnzeigenAnchor));

    alleNichtBesuchtProfileAnzeigenAnchor.setStyleName("pure-menu-link");
    menuList.add(new ListItemWidget(alleNichtBesuchtProfileAnzeigenAnchor));

    sucheAnchor.setStyleName("pure-menu-link");

    suchProfilListBox.setStyleName("suchprofilListbox");
    suchProfilListBox.setStyleName("pure-menu-link");

    menuList.add(new ListItemWidget(suchProfilListBox));
    menuList.add(new ListItemWidget(sucheAnchor));

    menu.add(pureMenu);
    pureMenu.add(anchor);
    pureMenu.add(menuList);

    pbVerwaltung.login(GWT.getHostPageBaseURL() + "Reportgen.html", new LoginCallback());

  }

  /**
   * Asynchrone Anmelde-Klasse in dem die Antwort des Callbacks eingefügt wird.
   *
   * @author Volz, Funke
   *
   */
  class LoginCallback implements AsyncCallback<Profil> {

	  /**
	   * Fehlermeldung, falls Login fehlgeschlagen
	   */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("Login fehlgeschlagen!");
    }

    /**
     * 
     */
    @Override
    public void onSuccess(Profil result) {
      if (result.isLoggedIn()) {
        RootPanel.get("menu").add(menu);
      } else {
        Anchor loginAnchor = new Anchor("Login");
        loginAnchor.setStyleName("pure-menu-link");
        loginAnchor.setHref(result.getLoginUrl());

        RootPanel.get("menu").add(loginAnchor);
      }
      p = result;
      p.setAehnlichkeit(100);

      pbVerwaltung.getAllSuchprofileForProfil(p, new SuchProfileCallback());
      pbVerwaltung.getAllProfiles(new AllProfilesCallback());

      sucheAnchor.addClickHandler(new SucheBtnClickHandeler());
      suchProfilListBox.addClickHandler(new SuchProfilClickhandler());
      alleProfileAnzeigenAnchor.addClickHandler(new AlleProfileClickhandler());
      alleNeuenProfileAnzeigenAnchor.addClickHandler(new NeueProfileClickHandler());
      alleNichtBesuchtProfileAnzeigenAnchor.addClickHandler(new NichtBesuchteProfileClickHandler());
      profilAnzeigenAnchor.addClickHandler(new ProfilClickHandler());

    }

  }

	/**
	 * Klasse die AsyncCallback implementiert.
	 * onSuccess wird der Report für das Profil des eingeloggten Users erstellt.
	 * 
	 * @author Funke, Volz
	 *
	 */
  class createProfilReportCallback implements AsyncCallback<ProfilReport> {

	  /**
	   * Gibt Fehlermeldung aus falls onSuccess nicht erreicht wird
	   */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().info(caught.toString());
    }

    /**
     * onSuccess wird der Report für den User angezeigt
     */
    @Override
    public void onSuccess(ProfilReport result) {
      pop.stop();
      ReportGen.this.addProfileToRootPanel(result);
    }
  }

  /**
   * Klasse die AsyncCallback implementiert.
   * onSuccess werden sämtliche Profile der Partnerbörse in profile geschrieben.
   * 
   * @author Funke, Volz
   *
   */
  private class AllProfilesCallback implements AsyncCallback<ArrayList<Profil>> {
    
	  /**
	   * schreibt alle Profile in die Variable profile
	   * @param ArrayList mit allen Profilen
	   */
	  @Override
    public void onSuccess(ArrayList<Profil> result) {
      pop.stop();
      profile = result;
      ClientsideSettings.getLogger().info("async callback get all profiles");
    }

	  /**
	   * Fehlermeldung, wenn onSuccess nicht erreicht wird
	   */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("Fehler im Asynccallback Reportgen getAllProfiles");

    }
  }

  /**
   *  Klasse die AsyncCallback implementiert.
   *  Schreibt onSuccess das Suchprofil in die Variable sp
   * 
   * @author Funke, Volz
   *
   */
  private class SuchProfilCallback implements AsyncCallback<Suchprofil> {
    
	  /**
	   * das result (ein Suchprofil) wird in sp gespeichert.
	   */
	  @Override
    public void onSuccess(Suchprofil result) {
      pop.stop();
      if (result == null) {
        ClientsideSettings.getLogger().info("Result == null");
      }
      sp = result;
    }

	  /**
	   * Fehlermeldung wenn onSuccess nicht erreicht wird
	   */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("Fehler getSuchprofileForProfilByName");
    }
  }

  /**
   *  Klasse die AsyncCallback implementiert.
   *  onSuccess werden alle Suchprofile des Users in die dafür vorgesehne
   *  ListBox geschrieben
   * 
   * @author Funke, Volz
   *
   */
  private class SuchProfileCallback implements AsyncCallback<ArrayList<Suchprofil>> {
    
	  /**
	   * alle Suchprofile des Users werden in die dafür vorgesehene ListBox
	   * geschrieben. Außerdem wird die createProfilReport Methode (zum erstellen
	   * des Profilreports des eingeloggten Users) aufgerufen
	   * @param suchProfilListe 
	   */
	  @Override
    public void onSuccess(ArrayList<Suchprofil> result) {
      suchProfilListe = result;
      for (Suchprofil sp : suchProfilListe) {
        suchProfilListBox.addItem(sp.getName());
      }
      reportGenerator.createProfilReport(p, new createProfilReportCallback());
    }

	  /**
	   * Fehlermeldung wenn onSuccess nicht erreicht wird
	   */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().info("Fehler AsyncCallback alle Suchprofile");
    }
  }

  /**
   * Klasse die AsyncCallback implementiert.
   * onSuccess wird der Report für das Suchprofil erstellt, also 
   * für alle Profile die dem Suchprofil entsprechen.
   * 
   * @author Funke, Volz
   *
   */
  private class ProfileBySucheReportCallback implements AsyncCallback<AllProfilesBySucheReport> {
    
	  /**
	   * Anzeigen aller Profile die dem Suchprofil entsprechen
	   */
	  @Override
    public void onSuccess(AllProfilesBySucheReport result) {
      pop.stop();
      ReportGen.this.addProfileToRootPanel(result);
    }

	  /**
	   * Fehlermeldung wenn onSuccess nicht erreicht wird
	   */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("Fehler createSuchreport");
    }
  }

  /**
   * Klasse die AsyncCallback implementiert.
   * Zeigt den Report für alle nicht besuchten Profile an
   * @author Volz, Funke
   *
   */
  private class NotVIsitedCallback implements AsyncCallback<AllNotVisitedProfileReport> {
   
	  /**
	   * Zeigt den Report für alle nicht besuchten Profile an
	   */
	  @Override
    public void onSuccess(AllNotVisitedProfileReport result) {
      pop.stop();
      ReportGen.this.addProfileToRootPanel(result);
    }

	  /**
	   * Fehlermeldung wenn onSuccess nicht erreicht wird.
	   */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("createallnewprofiles funktioniert nicht");
    }
  }

  /**
   * Klasse die AsyncCallback implementiert.
   * zeigt onSuccess den Report für alle neuen Profile an
   * @author Volz, Funke
   *
   */
  private class NewProfilesCallback implements AsyncCallback<AllNewProfileReport> {
    
	  /**
	   * anzeigen des Reports für alle neuen Profile
	   */
	  @Override
    public void onSuccess(AllNewProfileReport result) {
      pop.stop();
      ReportGen.this.addProfileToRootPanel(result);
    }

	  /**
	   * Fehlermeldung ausgeben, wenn onSuccess nicht erreicht wird
	   */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("createallnewprofiles funktioniert nicht");
    }
  }

  /**
   * Klasse die das ClickHandler Interface implementiert
   * onClick wird die klasse SuchProfilCallback aufgerufen und das ausgewählte 
   * Suchprofil übergeben
   * @author Funke, Volz
   *
   */
  private class SuchProfilClickhandler implements ClickHandler {


    @Override
    public void onClick(ClickEvent event) {
      ListBox clickedLb = (ListBox) event.getSource();

      pbVerwaltung.getSuchprofileForProfilByName(p, clickedLb.getSelectedItemText(),
          new SuchProfilCallback());

    }
  }

  /**
   * Klasse die das ClickHandler Interface implementiert
   * onClick wird der Report für das Profil des Users kreiert.
   * @author volz, funke
   *
   */
  private class ProfilClickHandler implements ClickHandler {
   

	  @Override
    public void onClick(ClickEvent event) {
      RootPanel.get("main").clear();
      pop.load();
      ClientsideSettings.getLogger().info("onClick profilAnzeigenButton");
      reportGenerator.createProfilReport(p, new createProfilReportCallback());

    }
  }

  /**
   * Klasse die das ClickHandler Interface implementiert
   * onClick wird der Report für alle nicht besuchten Profile erstellt.
   * @author Funke, Volz
   *
   */
  private class NichtBesuchteProfileClickHandler implements ClickHandler {


    @Override
    public void onClick(ClickEvent event) {
      RootPanel.get("main").clear();
      pop.load();
      reportGenerator.createAllNotVisitedProfileReport(p, new NotVIsitedCallback());
    }
  }

  /**
   * Klasse die das ClickHandler Interface implementiert
   * onClick wird der Report für alle neuen Profile erstellt
   * @author Funke, Volz
   *
   */
  private class NeueProfileClickHandler implements ClickHandler {


    @Override
    public void onClick(ClickEvent event) {
      RootPanel.get("main").clear();
      pop.load();
      reportGenerator.createAllNewProfilesReport(p, new NewProfilesCallback());
    }
  }

  /**
   * Klasse die das ClickHandler Interface implementiert
   * onClick wird der Report für alle Profile erstellt
   * @author Funke, Volz
   *
   */
  private class AlleProfileClickhandler implements ClickHandler {


    @Override
    public void onClick(ClickEvent event) {
      RootPanel.get("main").clear();
      pop.load();
      ClientsideSettings.getLogger().info("onClick Methode aufgerufen");

      reportGenerator.createAllProfilesReport(p, new AllProfilesReportCallback());
    }
  }

  /**
   * Klasse die AsyncCallback implementiert.
   * onSuccess wird der Report für alle Profile angezeigt
   * @author Funke, Volz
   *
   */
  private class AllProfilesReportCallback implements AsyncCallback<AllProfilesReport> {
    
	  /**
	   * anzeigen des Reports für alle Profile
	   */
	  @Override
    public void onSuccess(AllProfilesReport result) {
      pop.stop();
      ReportGen.this.addProfileToRootPanel(result);
    }

	  /**
	   * Fehlermeldung wenn onSuccess nicht erreicht wird
	   */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("createallprofiles funktioniert nicht");
    }
  }

  /**
   * Klasse die das ClickHandler Interface implementiert
   * onClick wird der Report für das selektierte Suchprofil erstellt
   * @author Funke, Volz
   *
   */
  private class SucheBtnClickHandeler implements ClickHandler {


    @Override
    public void onClick(ClickEvent event) {
      RootPanel.get("main").clear();
      pop.load();
      reportGenerator.createSuchreportBySuchprofil(sp, p, new ProfileBySucheReportCallback());
    }
  }

	/**
	 * Methode Anzeigen des Reports
	 * @param AllProfilesReport
	 */
  private void addProfileToRootPanel(AllProfilesReport result) {
    RootPanel.get("main").clear();
    HTMLReportWriter writer = new HTMLReportWriter();
    writer.process(result);
    HTML report = new HTML(writer.getReportText());
    RootPanel.get("main").add(report);
  }
  
  /**
   * Methode Anzeigen des Reports
   * @param ProfilReport
   */
  private void addProfileToRootPanel(ProfilReport result) {
    RootPanel.get("main").clear();
    HTMLReportWriter writer = new HTMLReportWriter();
    writer.process(result);
    HTML report = new HTML(writer.getReportText());
    RootPanel.get("main").add(report);
  }
  
  /**
   * Methode Anzeigen des Reports
   * @param AllProfilesBySucheReport
   */
  private void addProfileToRootPanel(AllProfilesBySucheReport result) {
    RootPanel.get("main").clear();
    HTMLReportWriter writer = new HTMLReportWriter();
    writer.process(result);
    HTML report = new HTML(writer.getReportText());
    RootPanel.get("main").add(report);
  }

  /**
   * Methode Anzeigen des Reports
   * @param AllNotVisitedProfileReport
   */
  private void addProfileToRootPanel(AllNotVisitedProfileReport result) {
    RootPanel.get("main").clear();
    HTMLReportWriter writer = new HTMLReportWriter();
    writer.process(result);
    HTML report = new HTML(writer.getReportText());
    RootPanel.get("main").add(report);
  }

  /**
   * Methode Anzeigen des Reports
   * @param AllNewProfileReport
   */
  private void addProfileToRootPanel(AllNewProfileReport result) {
    RootPanel.get("main").clear();
    HTMLReportWriter writer = new HTMLReportWriter();
    writer.process(result);
    HTML report = new HTML(writer.getReportText());
    RootPanel.get("main").add(report);
  }
}
