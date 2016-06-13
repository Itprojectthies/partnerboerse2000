package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

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

  Button profilAnzeigenButton = new Button("Profil anzeigen");
  Button alleProfileAnzeigenButton = new Button("alle Profile anzeigen");
  Button alleNeuenProfileAnzeigenButton = new Button("alle neuen Profile anzeigen");
  Button alleNichtBesuchtProfileAnzeigenButton =
      new Button("alle nicht besuchten Profile anzeigen");
  Button sucheBtn = new Button("Suche nach Suchprofilen");

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

  @Override
  public void onModuleLoad() {

    RootPanel.get("Details").add(profilAnzeigenButton);
    RootPanel.get("Details").add(alleProfileAnzeigenButton);
    RootPanel.get("Details").add(alleNeuenProfileAnzeigenButton);
    RootPanel.get("Details").add(alleNichtBesuchtProfileAnzeigenButton);
    RootPanel.get("Details").add(sucheBtn);


    pbVerwaltung.login(GWT.getHostPageBaseURL() + "Superteam2000.html", new LoginCallback());

    suchProfilListBox.setSize("8em", "14em");



    RootPanel.get("Details").add(suchProfilListBox);
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

      p = result;

      pbVerwaltung.getAllSuchprofileForProfil(p, new AsyncCallback<ArrayList<Suchprofil>>() {

        // Befülle die SuchProfilListBox mit bereits gespeichtern Suchprofilen


        @Override
        public void onSuccess(ArrayList<Suchprofil> result) {
          suchProfilListe = result;
          for (Suchprofil sp : suchProfilListe) {
            suchProfilListBox.addItem(sp.getName());
          }

        }

        @Override
        public void onFailure(Throwable caught) {
          ClientsideSettings.getLogger().info("Fehler AsyncCallback alle Suchprofile");
        }
      });

      sucheBtn.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          Suchprofil sp2 = sp;
          ClientsideSettings.getLogger().info(sp2.getRaucher());
          ClientsideSettings.getPartnerboerseVerwaltung().getProfilesBySuchprofil(sp2, p,
              new AsyncCallback<ArrayList<Profil>>() {

                @Override
                public void onSuccess(ArrayList<Profil> result) {
                  if (result == null) {
                    ClientsideSettings.getLogger().info("Result == null");
                  }
                  if (result.isEmpty()) {

                    ClientsideSettings.getLogger().info("Result == empty");
                  }
                  profileForSuchprofil = result;
                  reportGenerator.createSuchreport(profileForSuchprofil,
                      new AsyncCallback<AllProfileBySuche>() {

                        @Override
                        public void onSuccess(AllProfileBySuche result) {
                          if (result != null) {
                            ClientsideSettings.getLogger().info("hier bin ich schonmal");
                            RootPanel.get("Details").clear();
                            result.getTitle();
                            HTMLReportWriter writer = new HTMLReportWriter();
                            writer.process(result);
                            RootPanel.get("Details").add(new HTML(writer.getReportText()));
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

                @Override
                public void onFailure(Throwable caught) {
                  ClientsideSettings.getLogger().severe("Fehler getProfilesBySuchprofil");
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
                RootPanel.get("Details").clear();
                HTMLReportWriter writer = new HTMLReportWriter();
                writer.process(result);
                RootPanel.get("Details").add(new HTML(writer.getReportText()));
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
              RootPanel.get("Details").clear();
              HTMLReportWriter writer = new HTMLReportWriter();
              writer.process(result);
              RootPanel.get("Details").add(new HTML(writer.getReportText()));
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
                  RootPanel.get("Details").clear();
                  HTMLReportWriter writer = new HTMLReportWriter();
                  writer.process(result);
                  RootPanel.get("Details").add(new HTML(writer.getReportText()));
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
        RootPanel.get("Details").clear();
        HTML html = new HTML(writer.getReportText());
        RootPanel.get("Details").add(html);

      }
    }

  }
}
