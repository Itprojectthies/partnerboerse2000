package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
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
  /*
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
   * 
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
   * Asynchrone Anmelde-Klasse. Showcase in dem die Antwort des Callbacks eingefügt wird.
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
	 * Report fuer Profil erstellen
	 * 
	 * @author Funke, Volz
	 *
	 */
  class createProfilReportCallback implements AsyncCallback<ProfilReport> {

	  /**
	   * Fehlermeldung falls Login nicht erfolgreich
	   */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().info(caught.toString());
    }

    /**
     * Wenn Login erfolgreich, Report Generator ausgeben.
     */
    @Override
    public void onSuccess(ProfilReport result) {
      pop.stop();
      ReportGen.this.addProfileToRootPanel(result);
    }
  }

  /**
   * Alle Profile im Report anzeigen lassen
   * 
   * @author Funke, Volz
   *
   */
  private class AllProfilesCallback implements AsyncCallback<ArrayList<Profil>> {
    
	  /**
	   * @param profile alle Profile enthalten
	   */
	  @Override
    public void onSuccess(ArrayList<Profil> result) {
      pop.stop();
      profile = result;
      ClientsideSettings.getLogger().info("async callback get all profiles");
    }

	  /**
	   * Fehlermeldung, wenn nicht alle Profile angezeigt werden konnten.
	   */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("Fehler im Asynccallback Reportgen getAllProfiles");

    }
  }

  /**
   * Login fuer Suchprofile pruefen; alle Suchprofile anzeigen
   * 
   * @author Funke, Volz
   *
   */
  private class SuchProfilCallback implements AsyncCallback<Suchprofil> {
    
	  /**
	   * Login pruefen
	   * @param sp speichert Resultat von Abfrag ob User eingeloggt
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
	   * Fehlermeldung ausgeben, wenn Suchprofile nicht gefunden werden konnten.
	   */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("Fehler getSuchprofileForProfilByName");
    }
  }

  /**
   * Suchprofile suchen
   * 
   * @author Funke, Volz
   *
   */
  private class SuchProfileCallback implements AsyncCallback<ArrayList<Suchprofil>> {
    
	  /**
	   * @param suchProfilListe alle Profile von Suchprofil speichern
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
	   * Fehlermeldung ausgeben, wenn nicht alle Suchprofile gefunden wurden
	   */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().info("Fehler AsyncCallback alle Suchprofile");
    }
  }

  /**
   * 
   * 
   * @author Funke, Volz
   *
   */
  private class ProfileBySucheReportCallback implements AsyncCallback<AllProfilesBySucheReport> {
    
	  /**
	   * 
	   */
	  @Override
    public void onSuccess(AllProfilesBySucheReport result) {
      pop.stop();
      ReportGen.this.addProfileToRootPanel(result);
    }

	  /**
	   * Fehlermeldung ausgeben, wenn Suchreport nicht erstellt werden konnte
	   */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("Fehler createSuchreport");
    }
  }

  /**
   * 
   * @author Volz, Funke
   *
   */
  private class NotVIsitedCallback implements AsyncCallback<AllNotVisitedProfileReport> {
   
	  /**
	   * 
	   */
	  @Override
    public void onSuccess(AllNotVisitedProfileReport result) {
      pop.stop();
      ReportGen.this.addProfileToRootPanel(result);
    }

	  /**
	   * 
	   */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("createallnewprofiles funktioniert nicht");
    }
  }

  /**
   * 
   * @author Volz, Funke
   *
   */
  private class NewProfilesCallback implements AsyncCallback<AllNewProfileReport> {
    
	  /**
	   * 
	   */
	  @Override
    public void onSuccess(AllNewProfileReport result) {
      pop.stop();
      ReportGen.this.addProfileToRootPanel(result);
    }

	  /**
	   * Fehlermeldung ausgeben, wenn 
	   */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("createallnewprofiles funktioniert nicht");
    }
  }

  /**
   * 
   * @author Funke, Volz
   *
   */
  private class SuchProfilClickhandler implements ClickHandler {

	  /**
	   * 
	   */
    @Override
    public void onClick(ClickEvent event) {
      ListBox clickedLb = (ListBox) event.getSource();

      pbVerwaltung.getSuchprofileForProfilByName(p, clickedLb.getSelectedItemText(),
          new SuchProfilCallback());

    }
  }

  /**
   * 
   * @author volz, funke
   *
   */
  private class ProfilClickHandler implements ClickHandler {
   
	  /**
	   * 
	   */
	  @Override
    public void onClick(ClickEvent event) {
      RootPanel.get("main").clear();
      pop.load();
      ClientsideSettings.getLogger().info("onClick profilAnzeigenButton");
      reportGenerator.createProfilReport(p, new createProfilReportCallback());

    }
  }

  /**
   * 
   * @author Funke, Volz
   *
   */
  private class NichtBesuchteProfileClickHandler implements ClickHandler {

	  /**
	   * 
	   */
    @Override
    public void onClick(ClickEvent event) {
      RootPanel.get("main").clear();
      pop.load();
      reportGenerator.createAllNotVisitedProfileReport(p, new NotVIsitedCallback());
    }
  }

  /**
   * 
   * @author Funke, Volz
   *
   */
  private class NeueProfileClickHandler implements ClickHandler {

	  /**
	   * 
	   */
    @Override
    public void onClick(ClickEvent event) {
      RootPanel.get("main").clear();
      pop.load();
      reportGenerator.createAllNewProfilesReport(p, new NewProfilesCallback());
    }
  }

  /**
   * 
   * @author Funke, Volz
   *
   */
  private class AlleProfileClickhandler implements ClickHandler {

	  /**
	   * 
	   */
    @Override
    public void onClick(ClickEvent event) {
      RootPanel.get("main").clear();
      pop.load();
      ClientsideSettings.getLogger().info("onClick Methode aufgerufen");

      reportGenerator.createAllProfilesReport(p, new AllProfilesReportCallback());
    }
  }

  /**
   * 
   * @author Funke, Volz
   *
   */
  private class AllProfilesReportCallback implements AsyncCallback<AllProfilesReport> {
    
	  /**
	   * 
	   */
	  @Override
    public void onSuccess(AllProfilesReport result) {
      pop.stop();
      ReportGen.this.addProfileToRootPanel(result);
    }

	  /**
	   * Fehlermeldung ausgeben, wenn
	   */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("createallprofiles funktioniert nicht");
    }
  }

  /**
   * 
   * @author Funke, Volz
   *
   */
  private class SucheBtnClickHandeler implements ClickHandler {

	  /**
	   * 
	   */
    @Override
    public void onClick(ClickEvent event) {
      RootPanel.get("main").clear();
      pop.load();
      reportGenerator.createSuchreportBySuchprofil(sp, p, new ProfileBySucheReportCallback());
    }
  }

	/**
	 * 
	 * @param
	 */
  private void addProfileToRootPanel(AllProfilesReport result) {
    RootPanel.get("main").clear();
    HTMLReportWriter writer = new HTMLReportWriter();
    writer.process(result);
    HTML report = new HTML(writer.getReportText());
    RootPanel.get("main").add(report);
  }
  
  /**
   * 
   * @param 
   */
  private void addProfileToRootPanel(ProfilReport result) {
    RootPanel.get("main").clear();
    HTMLReportWriter writer = new HTMLReportWriter();
    writer.process(result);
    HTML report = new HTML(writer.getReportText());
    RootPanel.get("main").add(report);
  }
  
  /**
   * 
   * @param 
   */
  private void addProfileToRootPanel(AllProfilesBySucheReport result) {
    RootPanel.get("main").clear();
    HTMLReportWriter writer = new HTMLReportWriter();
    writer.process(result);
    HTML report = new HTML(writer.getReportText());
    RootPanel.get("main").add(report);
  }

  /**
   * 
   * @param 
   */
  private void addProfileToRootPanel(AllNotVisitedProfileReport result) {
    RootPanel.get("main").clear();
    HTMLReportWriter writer = new HTMLReportWriter();
    writer.process(result);
    HTML report = new HTML(writer.getReportText());
    RootPanel.get("main").add(report);
  }

  /**
   * 
   * @param 
   */
  private void addProfileToRootPanel(AllNewProfileReport result) {
    RootPanel.get("main").clear();
    HTMLReportWriter writer = new HTMLReportWriter();
    writer.process(result);
    HTML report = new HTML(writer.getReportText());
    RootPanel.get("main").add(report);
  }
}
