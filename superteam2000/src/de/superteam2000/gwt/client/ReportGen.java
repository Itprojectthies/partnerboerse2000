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
import de.superteam2000.gwt.shared.report.AllProfilesBySucheReport;
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


    this.suchProfilListBox.setSize("11em", "8em");

    RootPanel.get("menu").getElement().getStyle().setBackgroundColor("#191818");
    Anchor anchor = new Anchor("PartnerBörse", GWT.getHostPageBaseURL() + "Superteam2000.html");

    this.menuList.setStyleName("pure-menu-list");
    anchor.setStyleName("pure-menu-heading");
    this.pureMenu.setStyleName("pure-menu");

    this.profilAnzeigenButton.setStyleName("pure-menu-link");
    this.menuList.add(new ListItemWidget(this.profilAnzeigenButton));

    this.alleProfileAnzeigenButton.setStyleName("pure-menu-link");
    this.menuList.add(new ListItemWidget(this.alleProfileAnzeigenButton));

    this.alleNeuenProfileAnzeigenButton.setStyleName("pure-menu-link");
    this.menuList.add(new ListItemWidget(this.alleNeuenProfileAnzeigenButton));

    this.alleNichtBesuchtProfileAnzeigenButton.setStyleName("pure-menu-link");
    this.menuList.add(new ListItemWidget(this.alleNichtBesuchtProfileAnzeigenButton));

    this.sucheBtn.setStyleName("pure-menu-link");

    this.suchProfilListBox.setStyleName("suchprofilListbox");
    this.suchProfilListBox.setStyleName("pure-menu-link");

    this.menuList.add(new ListItemWidget(this.suchProfilListBox));
    this.menuList.add(new ListItemWidget(this.sucheBtn));

    this.menu.add(this.pureMenu);
    this.pureMenu.add(anchor);
    this.pureMenu.add(this.menuList);

    this.pbVerwaltung.login(GWT.getHostPageBaseURL() + "Reportgen.html", new LoginCallback());

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
        RootPanel.get("menu").add(ReportGen.this.menu);
      } else {

        Anchor loginAnchor = new Anchor("Login");
        loginAnchor.setStyleName("pure-menu-link");
        loginAnchor.setHref(result.getLoginUrl());

        RootPanel.get("menu").add(loginAnchor);
      }
      ReportGen.this.p = result;
      ReportGen.this.p.setAehnlichkeit(100);

      ReportGen.this.pbVerwaltung.getAllSuchprofileForProfil(ReportGen.this.p,
          new SuchProfileCallback());
      ReportGen.this.pbVerwaltung.getAllProfiles(new AllProfilesCallback());

      ReportGen.this.sucheBtn.addClickHandler(new SucheBtnClickHandeler());
      ReportGen.this.suchProfilListBox.addClickHandler(new SuchProfilClickhandler());
      ReportGen.this.alleProfileAnzeigenButton.addClickHandler(new AlleProfileClickhandler());
      ReportGen.this.alleNeuenProfileAnzeigenButton.addClickHandler(new NeueProfileClickHandler());
      ReportGen.this.alleNichtBesuchtProfileAnzeigenButton
          .addClickHandler(new NichtBesuchteProfileClickHandler());
      ReportGen.this.profilAnzeigenButton.addClickHandler(new ProfilClickHandler());

    }

  }


  class createProfilReportCallback implements AsyncCallback<ProfilReport> {

    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().info(caught.toString());
    }

    @Override
    public void onSuccess(ProfilReport result) {
      ReportGen.this.addProfileToRootPanel(result);
    }

  }

  private class AllProfilesCallback implements AsyncCallback<ArrayList<Profil>> {
    @Override
    public void onSuccess(ArrayList<Profil> result) {
      ReportGen.this.profile = result;
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
      ReportGen.this.sp = result;
    }

    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("Fehler getSuchprofileForProfilByName");
    }
  }

  private class SuchProfileCallback implements AsyncCallback<ArrayList<Suchprofil>> {
    @Override
    public void onSuccess(ArrayList<Suchprofil> result) {
      ReportGen.this.suchProfilListe = result;
      for (Suchprofil sp : ReportGen.this.suchProfilListe) {
        ReportGen.this.suchProfilListBox.addItem(sp.getName());
      }
      ReportGen.this.reportGenerator.createProfilReport(ReportGen.this.p,
          new createProfilReportCallback());
    }

    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().info("Fehler AsyncCallback alle Suchprofile");
    }
  }

  private final class ProfileBySucheReportCallback
      implements AsyncCallback<AllProfilesBySucheReport> {
    @Override
    public void onSuccess(AllProfilesBySucheReport result) {
      ReportGen.this.addProfileToRootPanel(result);
    }

    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("Fehler createSuchreport");
    }
  }

  private final class NotVIsitedCallback implements AsyncCallback<AllNotVisitedProfileReport> {
    @Override
    public void onSuccess(AllNotVisitedProfileReport result) {
      ReportGen.this.addProfileToRootPanel(result);
    }

    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("createallnewprofiles funktioniert nicht");
    }
  }

  private final class NewProfilesCallback implements AsyncCallback<AllNewProfileReport> {
    @Override
    public void onSuccess(AllNewProfileReport result) {
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

      ReportGen.this.pbVerwaltung.getSuchprofileForProfilByName(ReportGen.this.p,
          clickedLb.getSelectedItemText(), new SuchProfilCallback());

    }
  }

  private class ProfilClickHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      ClientsideSettings.getLogger().info("onClick profilAnzeigenButton");
      ReportGen.this.reportGenerator.createProfilReport(ReportGen.this.p,
          new createProfilReportCallback());

    }
  }

  private class NichtBesuchteProfileClickHandler implements ClickHandler {


    @Override
    public void onClick(ClickEvent event) {
      ReportGen.this.reportGenerator.createAllNotVisitedProfileReport(ReportGen.this.p,
          new NotVIsitedCallback());
    }
  }

  private class NeueProfileClickHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {
      ReportGen.this.reportGenerator.createAllNewProfilesReport(ReportGen.this.p,
          new NewProfilesCallback());
    }
  }

  private class AlleProfileClickhandler implements ClickHandler {


    @Override
    public void onClick(ClickEvent event) {
      ClientsideSettings.getLogger().info("onClick Methode aufgerufen");

      ReportGen.this.reportGenerator.createAllProfilesReport(ReportGen.this.p,
          new AllProfilesReportCallback());
    }
  }

  private class AllProfilesReportCallback implements AsyncCallback<AllProfilesReport> {
    @Override
    public void onSuccess(AllProfilesReport result) {
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
      ReportGen.this.reportGenerator.createSuchreportBySuchprofil(ReportGen.this.sp,
          ReportGen.this.p, new ProfileBySucheReportCallback());
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
