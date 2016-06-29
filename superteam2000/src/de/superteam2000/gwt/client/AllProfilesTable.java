package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.superteam2000.gwt.client.gui.CustomPopupPanel;
import de.superteam2000.gwt.client.gui.DataGridProfiles;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Profil;

public class AllProfilesTable extends BasicFrame {

  CustomPopupPanel pop = new CustomPopupPanel(false, true);
  
  private ArrayList<Profil> profilListe = new ArrayList<Profil>();

  public AllProfilesTable(ArrayList<Profil> list) {
    profilListe = list;
  }
  public AllProfilesTable() {
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

    FlowPanel contentPanel = new FlowPanel();
    FlowPanel fPanel2 = new FlowPanel();
    FlowPanel buttonsPanel = new FlowPanel();

    contentPanel.setStyleName("pure-form pure-form-aligned content");

    buttonsPanel.add(profileAnzeigenButton);
    buttonsPanel.add(nichtBesuchteProfilAnzeigenButton);
    buttonsPanel.add(neueProfilAnzeigenButton);

    contentPanel.add(buttonsPanel);
    fPanel2.add(contentPanel);
    DataGridProfiles dgp = new DataGridProfiles(profilListe);
    dgp.addClickFremdProfil();
    fPanel2.add(dgp.start());
    RootPanel.get("main").add(fPanel2);

    neueProfilAnzeigenButton.addClickHandler(new NeueProfilAnzeigeClickHandler());

    nichtBesuchteProfilAnzeigenButton.addClickHandler(new NichtBesuchteProfileClickHandler());

    profileAnzeigenButton.addClickHandler(new AlleProfileClickHandler());

  }

  private class AlleProfileClickHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      pop.load();
      pbVerwaltung.getProfilesByAehnlichkeitsmass(profil,
          new ProfileProfilesByAehnlichkeitsmassCallback());
    }
  }

  private class ProfileProfilesByAehnlichkeitsmassCallback
      implements AsyncCallback<ArrayList<Profil>> {
    @Override
    public void onSuccess(ArrayList<Profil> result) {
      pop.stop();
      profilListe = result;
      AllProfilesTable dgt = new AllProfilesTable(result);
      RootPanel.get("main").clear();
      RootPanel.get("main").add(dgt);
    }

    @Override
    public void onFailure(Throwable caught) {}
  }

  private class NichtBesuchteProfileClickHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {
      pop.load();
      pbVerwaltung.getAllNotVisitedProfilesByAehnlichkeitsmass(profil,
          new AllNotVisitedProfilesByAehnlichkeitsmassCallback());
    }
  }

  private class AllNotVisitedProfilesByAehnlichkeitsmassCallback
      implements AsyncCallback<ArrayList<Profil>> {
    @Override
    public void onSuccess(ArrayList<Profil> result) {
      pop.stop();
      AllProfilesTable dgt = new AllProfilesTable(result);
      RootPanel.get("main").clear();
      RootPanel.get("main").add(dgt);
    }

    @Override
    public void onFailure(Throwable caught) {}
  }

  private class NeueProfilAnzeigeClickHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {
      pop.load();
      pbVerwaltung.getAllNewProfilesByAehnlichkeitsmass(profil,
          new AllNewProfilesByAehnlichkeitsmassCallback());
    }
  }
  private class AllNewProfilesByAehnlichkeitsmassCallback
      implements AsyncCallback<ArrayList<Profil>> {
    @Override
    public void onSuccess(ArrayList<Profil> result) {
      pop.stop();
      AllProfilesTable dgt = new AllProfilesTable(result);
      RootPanel.get("main").clear();
      RootPanel.get("main").add(dgt);
    }

    @Override
    public void onFailure(Throwable caught) {}
  }
}
