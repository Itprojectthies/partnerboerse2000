package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

import de.superteam2000.gwt.client.gui.DataGridProfiles;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Profil;

public class AllProfilesTable extends BasicFrame {

  private ArrayList<Profil> profilListe;

  public AllProfilesTable(ArrayList<Profil> list) {
    this.profilListe = list;
  }

  public ArrayList<Profil> getProfilListe() {
    return profilListe;
  }

  public void setProfilListe(ArrayList<Profil> profilListe) {
    this.profilListe = profilListe;
  }

  @Override
  public String getHeadlineText() {
    return "Alle Profile";
  }

  @Override
  protected String getSubHeadlineText() {
    return "Hier findes du alle Profile der Partnerbörse2000";
  }


  // pb Verwaltung über ClientsideSettings holen
  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

  Profil profil = ClientsideSettings.getCurrentUser();

  @Override
  public void run() {

    final Button neueProfilAnzeigenButton = new Button("Neue");
    neueProfilAnzeigenButton.setStyleName("pure-button");

    final Button nichtBesuchteProfilAnzeigenButton = new Button("Nicht besuchte");
    nichtBesuchteProfilAnzeigenButton.setStyleName("pure-button");

    final Button profileAnzeigenButton = new Button("Alle");
    profileAnzeigenButton.setStyleName("pure-button");

    FlowPanel fPanel = new FlowPanel();
    FlowPanel fPanel2 = new FlowPanel();
    FlowPanel buttonsPanel = new FlowPanel();

    fPanel.setStyleName("pure-form pure-form-aligned content");
//    fPanel2.setStyleName("content");



    // buttonsPanel.setStyleName("pure-controls");
    buttonsPanel.add(profileAnzeigenButton);
    buttonsPanel.add(nichtBesuchteProfilAnzeigenButton);
    buttonsPanel.add(neueProfilAnzeigenButton);


    fPanel.add(buttonsPanel);
    fPanel2.add(fPanel);
    DataGridProfiles dgp = new DataGridProfiles(profilListe);
    dgp.addClickFremdProfil();
    fPanel2.add(dgp.start());
    RootPanel.get("main").add(fPanel2);
    

    neueProfilAnzeigenButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        pbVerwaltung.getAllNewProfilesByAehnlichkeitsmass(profil,
            new AsyncCallback<ArrayList<Profil>>() {

              @Override
              public void onSuccess(ArrayList<Profil> result) {
                AllProfilesTable dgt = new AllProfilesTable(result);
                RootPanel.get("main").clear();
                RootPanel.get("main").add(dgt);

              }

              @Override
              public void onFailure(Throwable caught) {
                // TODO Auto-generated method stub

              }
            });


      }
    });

    nichtBesuchteProfilAnzeigenButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        pbVerwaltung.getAllNotVisitedProfilesByAehnlichkeitsmass(profil,
            new AsyncCallback<ArrayList<Profil>>() {

              @Override
              public void onSuccess(ArrayList<Profil> result) {
                AllProfilesTable dgt = new AllProfilesTable(result);
                RootPanel.get("main").clear();
                RootPanel.get("main").add(dgt);

              }

              @Override
              public void onFailure(Throwable caught) {
                // TODO Auto-generated method stub

              }
            });


      }
    });

    profileAnzeigenButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        pbVerwaltung.getProfilesByAehnlichkeitsmass(profil, new AsyncCallback<ArrayList<Profil>>() {

          @Override
          public void onSuccess(ArrayList<Profil> result) {
            profilListe = result;
            AllProfilesTable dgt = new AllProfilesTable(result);
            RootPanel.get("main").clear();
            RootPanel.get("main").add(dgt);

          }

          @Override
          public void onFailure(Throwable caught) {
            // TODO Auto-generated method stub

          }
        });


      }
    });

   

    // eigenes Profil aus der Liste entfernen
    // if(profile.contains(ClientsideSettings.getCurrentUser())){
    // profile.remove(ClientsideSettings.getCurrentUser());
    // }


  }

}
