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

import de.superteam2000.gwt.client.gui.ListItemWidget;
import de.superteam2000.gwt.client.gui.UnorderedListWidget;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.ReportGeneratorAsync;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.bo.Suchprofil;
import de.superteam2000.gwt.shared.report.AllNewProfileReport;
import de.superteam2000.gwt.shared.report.AllNotVisitedProfileReport;
import de.superteam2000.gwt.shared.report.AllProfileBySuche;
import de.superteam2000.gwt.shared.report.AllProfilesReport;
import de.superteam2000.gwt.shared.report.HTMLReportWriter;
import de.superteam2000.gwt.shared.report.ProfilReport;

public class ReportGen implements EntryPoint {

  Anchor profilAnzeigenButton = new Anchor("Mein Profil");
  Anchor alleProfileAnzeigenButton = new Anchor("Alle Profile");
  Anchor alleNeuenProfileAnzeigenButton = new Anchor("Neuen Profile");
  Anchor alleNichtBesuchtProfileAnzeigenButton = new Anchor("Nicht besuchte Profile");
  Anchor sucheBtn = new Anchor("Suche");

  @SuppressWarnings("deprecation")
  ListBox suchProfilListBox = new ListBox(true);


  ArrayList<Profil> profile = new ArrayList<Profil>();
  ArrayList<Profil> profileForSuchprofil = new ArrayList<Profil>();
  Profil p = new Profil();

  Suchprofil sp = new Suchprofil();
  ArrayList<Suchprofil> suchProfilListe = new ArrayList<>();

  Logger logger = ClientsideSettings.getLogger();
  ReportGeneratorAsync reportGenerator = ClientsideSettings.getReportGenerator();
  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

  FlowPanel menu = new FlowPanel();
  UnorderedListWidget menuList = new UnorderedListWidget();
  FlowPanel pureMenu = new FlowPanel();

  @Override
  public void onModuleLoad() {
    RootPanel.get("menu").getElement().getStyle().setBackgroundColor("#191818");
    Anchor anchor = new Anchor("PartnerBörse", GWT.getHostPageBaseURL() + "Superteam2000.html");

    menuList.setStyleName("pure-menu-list");
    anchor.setStyleName("pure-menu-heading");
    pureMenu.setStyleName("pure-menu");

    profilAnzeigenButton.setStyleName("pure-menu-link");
    menuList.add(new ListItemWidget(profilAnzeigenButton));

    alleProfileAnzeigenButton.setStyleName("pure-menu-link");
    menuList.add(new ListItemWidget(alleProfileAnzeigenButton));

    alleNeuenProfileAnzeigenButton.setStyleName("pure-menu-link");
    menuList.add(new ListItemWidget(alleNeuenProfileAnzeigenButton));

    alleNichtBesuchtProfileAnzeigenButton.setStyleName("pure-menu-link");
    menuList.add(new ListItemWidget(alleNichtBesuchtProfileAnzeigenButton));

    sucheBtn.setStyleName("pure-menu-link");

    suchProfilListBox.setStyleName("suchprofilListbox");
    suchProfilListBox.setStyleName("pure-menu-link");

    menuList.add(new ListItemWidget(suchProfilListBox));
    menuList.add(new ListItemWidget(sucheBtn));

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

      pbVerwaltung.getAllSuchprofileForProfil(p, new SuchProfileCallback());
      pbVerwaltung.getAllProfiles(new AllProfilesCallback());

      sucheBtn.addClickHandler(new SucheBtnClickHandeler());
      suchProfilListBox.addClickHandler(new SuchProfilClickhandler());
      alleProfileAnzeigenButton.addClickHandler(new AlleProfileClickhandler());
      alleNeuenProfileAnzeigenButton.addClickHandler(new NeueProfileClickHandler());
      alleNichtBesuchtProfileAnzeigenButton.addClickHandler(new NichtBesuchteProfileClickHandler());
      profilAnzeigenButton.addClickHandler(new ProfilClickHandler());

    }

  }


  class createProfilReportCallback implements AsyncCallback<ProfilReport> {

    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().info(caught.toString());
    }

    @Override
    public void onSuccess(ProfilReport result) {
      addProfileToRootPanel(result);
    }

  }

  private class AllProfilesCallback implements AsyncCallback<ArrayList<Profil>> {
    @Override
    public void onSuccess(ArrayList<Profil> result) {
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
  
  private final class ProfileBySucheReportCallback implements AsyncCallback<AllProfileBySuche> {
    @Override
    public void onSuccess(AllProfileBySuche result) {
      addProfileToRootPanel(result);
    }

    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("Fehler createSuchreport");
    }
  }
  
  private final class NotVIsitedCallback implements AsyncCallback<AllNotVisitedProfileReport> {
    @Override
    public void onSuccess(AllNotVisitedProfileReport result) {
      addProfileToRootPanel(result);
    }

    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("createallnewprofiles funktioniert nicht");
    }
  }
  
  private final class NewProfilesCallback implements AsyncCallback<AllNewProfileReport> {
    @Override
    public void onSuccess(AllNewProfileReport result) {
      addProfileToRootPanel(result);
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
      ClientsideSettings.getLogger().info("onClick profilAnzeigenButton");
      reportGenerator.createProfilReport(p, new createProfilReportCallback());

    }
  }

  private class NichtBesuchteProfileClickHandler implements ClickHandler {
   

    @Override
    public void onClick(ClickEvent event) {
      reportGenerator.createAllNotVisitedProfileReport(p, new NotVIsitedCallback());
    }
  }

  private class NeueProfileClickHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {
      reportGenerator.createAllNewProfilesReport(p, new NewProfilesCallback());
    }
  }

  private class AlleProfileClickhandler implements ClickHandler {


    @Override
    public void onClick(ClickEvent event) {
      ClientsideSettings.getLogger().info("onClick Methode aufgerufen");

      reportGenerator.createAllProfilesReport(new AllProfilesReportCallback());
    }
  }

  private class AllProfilesReportCallback implements AsyncCallback<AllProfilesReport> {
    @Override
    public void onSuccess(AllProfilesReport result) {
      addProfileToRootPanel(result);
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

  private void addProfileToRootPanel(AllProfileBySuche result) {
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
