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

  @Override
  public void onModuleLoad() {      
    
    
    
    
    suchProfilListBox.setSize("11em", "8em");

    RootPanel.get("menu").getElement().getStyle().setBackgroundColor("#191818");

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

    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("Login fehlgeschlagen!");
    }

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


  class createProfilReportCallback implements AsyncCallback<ProfilReport> {

    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().info(caught.toString());
    }

    @Override
    public void onSuccess(ProfilReport result) {
      pop.stop();
      ReportGen.this.addProfileToRootPanel(result);
    }

  }

  private class AllProfilesCallback implements AsyncCallback<ArrayList<Profil>> {
    @Override
    public void onSuccess(ArrayList<Profil> result) {
      pop.stop();
      profile = result;
      ClientsideSettings.getLogger().info("async callback get all profiles");
    }

    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("Fehler im Asynccallback Reportgen getAllProfiles");

    }
  }


  private class SuchProfilCallback implements AsyncCallback<Suchprofil> {
    @Override
    public void onSuccess(Suchprofil result) {
      pop.stop();
      if (result == null) {
        ClientsideSettings.getLogger().info("Result == null");
      }
      sp = result;
    }

    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("Fehler getSuchprofileForProfilByName");
    }
  }

  private class SuchProfileCallback implements AsyncCallback<ArrayList<Suchprofil>> {
    @Override
    public void onSuccess(ArrayList<Suchprofil> result) {
      suchProfilListe = result;
      for (Suchprofil sp : suchProfilListe) {
        suchProfilListBox.addItem(sp.getName());
      }
      reportGenerator.createProfilReport(p, new createProfilReportCallback());
    }

    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().info("Fehler AsyncCallback alle Suchprofile");
    }
  }

  private class ProfileBySucheReportCallback implements AsyncCallback<AllProfilesBySucheReport> {
    @Override
    public void onSuccess(AllProfilesBySucheReport result) {
      ReportGen.this.addProfileToRootPanel(result);
    }

    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("Fehler createSuchreport");
    }
  }

  private class NotVIsitedCallback implements AsyncCallback<AllNotVisitedProfileReport> {
    @Override
    public void onSuccess(AllNotVisitedProfileReport result) {
      pop.stop();
      ReportGen.this.addProfileToRootPanel(result);
    }

    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("createallnewprofiles funktioniert nicht");
    }
  }

  private class NewProfilesCallback implements AsyncCallback<AllNewProfileReport> {
    @Override
    public void onSuccess(AllNewProfileReport result) {
      pop.stop();
      ReportGen.this.addProfileToRootPanel(result);
    }

    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("createallnewprofiles funktioniert nicht");
    }
  }

  private class SuchProfilClickhandler implements ClickHandler {
    // ClickHandler

    @Override
    public void onClick(ClickEvent event) {
      ListBox clickedLb = (ListBox) event.getSource();

      pbVerwaltung.getSuchprofileForProfilByName(p, clickedLb.getSelectedItemText(),
          new SuchProfilCallback());

    }
  }

  private class ProfilClickHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      RootPanel.get("main").clear();
      pop.load();
      ClientsideSettings.getLogger().info("onClick profilAnzeigenButton");
      reportGenerator.createProfilReport(p, new createProfilReportCallback());

    }
  }

  private class NichtBesuchteProfileClickHandler implements ClickHandler {


    @Override
    public void onClick(ClickEvent event) {
      RootPanel.get("main").clear();
      pop.load();
      reportGenerator.createAllNotVisitedProfileReport(p, new NotVIsitedCallback());
    }
  }

  private class NeueProfileClickHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {
      RootPanel.get("main").clear();
      pop.load();
      reportGenerator.createAllNewProfilesReport(p, new NewProfilesCallback());
    }
  }

  private class AlleProfileClickhandler implements ClickHandler {


    @Override
    public void onClick(ClickEvent event) {
      RootPanel.get("main").clear();
      pop.load();
      ClientsideSettings.getLogger().info("onClick Methode aufgerufen");

      reportGenerator.createAllProfilesReport(p, new AllProfilesReportCallback());
    }
  }

  private class AllProfilesReportCallback implements AsyncCallback<AllProfilesReport> {
    @Override
    public void onSuccess(AllProfilesReport result) {
      pop.stop();
      ReportGen.this.addProfileToRootPanel(result);
    }

    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("createallprofiles funktioniert nicht");
    }
  }

  private class SucheBtnClickHandeler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {
      reportGenerator.createSuchreportBySuchprofil(sp, p, new ProfileBySucheReportCallback());
    }
  }


  private void addProfileToRootPanel(AllProfilesReport result) {
    RootPanel.get("main").clear();
    HTMLReportWriter writer = new HTMLReportWriter();
    writer.process(result);
    HTML report = new HTML(writer.getReportText());
    RootPanel.get("main").add(report);
  }

  private void addProfileToRootPanel(ProfilReport result) {
    RootPanel.get("main").clear();
    HTMLReportWriter writer = new HTMLReportWriter();
    writer.process(result);
    HTML report = new HTML(writer.getReportText());
    RootPanel.get("main").add(report);
  }

  private void addProfileToRootPanel(AllProfilesBySucheReport result) {
    RootPanel.get("main").clear();
    HTMLReportWriter writer = new HTMLReportWriter();
    writer.process(result);
    HTML report = new HTML(writer.getReportText());
    RootPanel.get("main").add(report);
  }

  private void addProfileToRootPanel(AllNotVisitedProfileReport result) {
    RootPanel.get("main").clear();
    HTMLReportWriter writer = new HTMLReportWriter();
    writer.process(result);
    HTML report = new HTML(writer.getReportText());
    RootPanel.get("main").add(report);
  }

  private void addProfileToRootPanel(AllNewProfileReport result) {
    RootPanel.get("main").clear();
    HTMLReportWriter writer = new HTMLReportWriter();
    writer.process(result);
    HTML report = new HTML(writer.getReportText());
    RootPanel.get("main").add(report);
  }


}
