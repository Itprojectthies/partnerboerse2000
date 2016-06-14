package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.logging.Logger;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
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
  Anchor alleNichtBesuchtProfileAnzeigenButton =
      new Anchor("Nicht besuchte Profile");
  Anchor sucheBtn = new Anchor("Suche");

  @SuppressWarnings("deprecation")
  ListBox suchProfilListBox = new ListBox(true);


  ArrayList<Profil> profile = new ArrayList<Profil>();
  ArrayList<Profil> profileForSuchprofil = new ArrayList<Profil>();
  Profil p = new Profil();
  ReportGeneratorAsync reportGenerator = null;
  Logger logger = ClientsideSettings.getLogger();
  // Profil profil = ClientsideSettings.getCurrentUser();
  PartnerboerseAdministrationAsync pb = ClientsideSettings.getPartnerboerseVerwaltung();

  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
  Suchprofil sp = new Suchprofil();

  ArrayList<Suchprofil> suchProfilListe = new ArrayList<>();
  FlowPanel menu = new FlowPanel();
  UnorderedListWidget menuList = new UnorderedListWidget();
  FlowPanel pureMenu = new FlowPanel();
  
  @Override
  public void onModuleLoad() {
    
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
    
    suchProfilListBox.setStyleDependentName("suchprofilListbox", true);
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
      
      pbVerwaltung.getAllSuchprofileForProfil(p, new AsyncCallback<ArrayList<Suchprofil>>() {

        // Befülle die SuchProfilListBox mit bereits gespeichtern Suchprofilen


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
      });

      sucheBtn.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {

          reportGenerator.createSuchreportBySuchprofil(sp, p,
              new AsyncCallback<AllProfileBySuche>() {

                @Override
                public void onSuccess(AllProfileBySuche result) {
                  if (result != null) {
                    ClientsideSettings.getLogger().info("hier bin ich schonmal");
                    RootPanel.get("main").clear();
                    result.getTitle();
                    HTMLReportWriter writer = new HTMLReportWriter();
                    writer.process(result);
                    HTML alleProfileBySuche = new HTML(writer.getReportText());
                    alleProfileBySuche.setStyleName("content");
                    RootPanel.get("main").add(alleProfileBySuche);
                  } else {
                    ClientsideSettings.getLogger().info("Result == null");
                  }

                }

                @Override
                public void onFailure(Throwable caught) {
                  ClientsideSettings.getLogger().severe("Fehler createSuchreport");
                }
              });

        }
      });

      suchProfilListBox.addClickHandler(new ClickHandler() {

        // ClickHandler um die Listboxen mit den jeweiligen Daten zu aktualisieren,
        // wenn ein Suchprofil angeklickt wird

        @Override
        public void onClick(ClickEvent event) {

          ListBox clickedLb = (ListBox) event.getSource();

          pbVerwaltung.getSuchprofileForProfilByName(p, clickedLb.getSelectedItemText(),
              new AsyncCallback<Suchprofil>() {

                @Override
                public void onSuccess(Suchprofil result) {
                  if (result == null) {
                    ClientsideSettings.getLogger().info("Result == null");
                  }
                  sp = result;
                  ClientsideSettings.getLogger().info("" + sp.getProfilId());


                }

                @Override
                public void onFailure(Throwable caught) {
                  ClientsideSettings.getLogger().severe("Fehler getSuchprofileForProfilByName");
                }
              });



        }
      });



      if (reportGenerator == null) {
        reportGenerator = ClientsideSettings.getReportGenerator();
      }


      pb.getAllProfiles(new AsyncCallback<ArrayList<Profil>>() {

        @Override
        public void onSuccess(ArrayList<Profil> result) {
          if (result != null) {
            profile = result;
            ClientsideSettings.getLogger().info("async callback get all profiles");
          }
        }

        @Override
        public void onFailure(Throwable caught) {
          ClientsideSettings.getLogger().severe("Fehler im Asynccallback Reportgen getAllProfiles");

        }
      });



      /**
       * ClickHandler der onClick alle Profile der Partnerbörse ausgibt
       */
      alleProfileAnzeigenButton.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          ClientsideSettings.getLogger().info("onClick Methode aufgerufen");

          reportGenerator.createAllProfilesReport(new AsyncCallback<AllProfilesReport>() {



            @Override
            public void onSuccess(AllProfilesReport result) {
              ClientsideSettings.getLogger().info("onSuccess AllprofilesReport");
              if (result != null) {
                RootPanel.get("main").clear();
                HTMLReportWriter writer = new HTMLReportWriter();
                writer.process(result);
                RootPanel.get("main").add(new HTML(writer.getReportText()));
              }
            }

            @Override
            public void onFailure(Throwable caught) {
              ClientsideSettings.getLogger().severe("createallprofiles funktioniert nicht");

            }
          });



        }
      });

      alleNeuenProfileAnzeigenButton.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          ClientsideSettings.getLogger().severe("createallnewprofiles klick funktioniert");
          reportGenerator.createAllNewProfilesReport(p, new AsyncCallback<AllNewProfileReport>() {

            @Override
            public void onSuccess(AllNewProfileReport result) {
              if (result != null) {
                ClientsideSettings.getLogger().severe("result != null");
              }
              ClientsideSettings.getLogger().severe("createallnewprofiles funktioniert ");
              RootPanel.get("main").clear();
              HTMLReportWriter writer = new HTMLReportWriter();
              writer.process(result);
              RootPanel.get("main").add(new HTML(writer.getReportText()));
            }

            @Override
            public void onFailure(Throwable caught) {
              ClientsideSettings.getLogger().severe("createallnewprofiles funktioniert nicht");
            }
          });
        }
      });

      alleNichtBesuchtProfileAnzeigenButton.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          ClientsideSettings.getLogger()
              .severe("createallnicht besuchtenprofiles klick funktioniert");
          reportGenerator.createAllNotVisitedProfileReport(p,
              new AsyncCallback<AllNotVisitedProfileReport>() {

                @Override
                public void onSuccess(AllNotVisitedProfileReport result) {
                  if (result != null) {
                    ClientsideSettings.getLogger().severe("result != null");
                  }
                  ClientsideSettings.getLogger().severe("createallnewprofiles funktioniert ");
                  RootPanel.get("main").clear();
                  HTMLReportWriter writer = new HTMLReportWriter();
                  writer.process(result);
                  RootPanel.get("main").add(new HTML(writer.getReportText()));
                }

                @Override
                public void onFailure(Throwable caught) {
                  ClientsideSettings.getLogger().severe("createallnewprofiles funktioniert nicht");
                }
              });
        }
      });

      // pb.getProfilById(17, new AsyncCallback<Profil>() {
      //
      // @Override
      // public void onFailure(Throwable caught) {
      // // TODO Auto-generated method stub
      //
      // }
      //
      // @Override
      // public void onSuccess(Profil result) {
      // p = result;
      //
      //
      // }
      // });



      profilAnzeigenButton.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          ClientsideSettings.getLogger().info("onClick profilAnzeigenButton");
          reportGenerator.createProfilReport(p, new createProfilReportCallback());

        }
      });

    }

  }

  class createProfilReportCallback implements AsyncCallback<ProfilReport> {

    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().info(caught.toString());


    }


    @Override
    public void onSuccess(ProfilReport report) {
      if (report != null) {
        ClientsideSettings.getLogger().info("report != null");
      }

      if (report != null) {
        HTMLReportWriter writer = new HTMLReportWriter();
        writer.process(report);
        RootPanel.get("main").clear();
        HTML html = new HTML(writer.getReportText());
        RootPanel.get("main").add(html);

      }
    }

  }
}
