package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.superteam2000.gwt.client.gui.CustomButton;
import de.superteam2000.gwt.client.gui.CustomPopupPanel;
import de.superteam2000.gwt.client.gui.DataGridProfiles;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Klasse zum Anzeigen von allen, neuen und nicht besuchten Profilen
 * 
 * @author Funke, Volz
 *
 */
public class AllProfilesTable extends BasicFrame {

  CustomPopupPanel pop = new CustomPopupPanel(false, true);
  
  private ArrayList<Profil> profilListe = new ArrayList<Profil>();

  /**
   * Konstruktor der eine ArrayList an Profilen erwartet
   * @param list 
   */
  public AllProfilesTable(ArrayList<Profil> list) {
    profilListe = list;
  }
  
  /**
   * NO-Argument Konstruktor
   */
  public AllProfilesTable() {
  }

  /**
   * Gibt Profilliste aus
   * @return profilListe
   */
  public ArrayList<Profil> getProfilListe() {
    return profilListe;
  }

  /**
   * Profilliste setzen
   * @param profilListe
   */
  public void setProfilListe(ArrayList<Profil> profilListe) {
    this.profilListe = profilListe;
  }

  /**
   * Headline Text getter
   * @return Text
   */
  @Override
  public String getHeadlineText() {
    return "Alle Profile";
  }

  /**
   * SubHeadline Text getter
   * @return Text
   */
  @Override
  protected String getSubHeadlineText() {
    return "Hier findes du alle Profile der Partnerbörse2000";
  }


  // pb Verwaltung über ClientsideSettings holen
  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

  Profil profil = ClientsideSettings.getCurrentUser();

  /**
   * Erstellen der Buttons
   * 
   */

  final CustomButton nichtBesuchteProfilAnzeigenButton = new CustomButton("Nicht besuchte");
  final CustomButton neueProfilAnzeigenButton = new CustomButton("Neue");
  final CustomButton profileAnzeigenButton = new CustomButton("Alle");
  
  @Override
  public void run() {
    
    
    
    
    
    neueProfilAnzeigenButton.setStyleName("pure-button");
    
    
    nichtBesuchteProfilAnzeigenButton.setStyleName("pure-button");

    
    profileAnzeigenButton.setStyleName("pure-button");
    profileAnzeigenButton.setPushed(true);
    
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
//    fPanel2.add(dgp.start());
    RootPanel.get("main").add(contentPanel);
    RootPanel.get("search-table").add(dgp.start());

    neueProfilAnzeigenButton.addClickHandler(new NeueProfilAnzeigeClickHandler());

    nichtBesuchteProfilAnzeigenButton.addClickHandler(new NichtBesuchteProfileClickHandler());

    profileAnzeigenButton.addClickHandler(new AlleProfileClickHandler());

  }

  /**
   * ClickHandler für den profileAnzeigenButton
   * 
   * @author Funke, Volz
   *
   */
  private class AlleProfileClickHandler implements ClickHandler {
    
	  /**
	   * geklickten Button "pushed" setzen und Profilliste abfragen
	   */
	  @Override
    public void onClick(ClickEvent event) {
      profileAnzeigenButton.setPushed(true);
      neueProfilAnzeigenButton.setPushed(false);
      nichtBesuchteProfilAnzeigenButton.setPushed(false);
      
      pop.load();
      pbVerwaltung.getProfilesByAehnlichkeitsmass(profil,
          new ProfileProfilesByAehnlichkeitsmassCallback());
    }
  }

  /**
   * Nested Class die onSuccess die ArrayList an Profilen zurückgibt
   * 
   * @author Funke, Volz
   *
   */
  private class ProfileProfilesByAehnlichkeitsmassCallback
      implements AsyncCallback<ArrayList<Profil>> {
    @Override
    public void onSuccess(ArrayList<Profil> result) {
      addProfilesToTable(result);
    }

    @Override
    public void onFailure(Throwable caught) {}
  }

  /**
   * ClickHandler für den nichtBesuchteProfilAnzeigenButton
   * 
   * @author Funke, Volz
   *
   */
  private class NichtBesuchteProfileClickHandler implements ClickHandler {

	  /**
	   * Button "pushed" setzen und alle nicht besuchten Profile abfragen
	   */
    @Override
    public void onClick(ClickEvent event) {
      profileAnzeigenButton.setPushed(false);
      neueProfilAnzeigenButton.setPushed(false);
      nichtBesuchteProfilAnzeigenButton.setPushed(true);
      pop.load();
      pbVerwaltung.getAllNotVisitedProfilesByAehnlichkeitsmass(profil,
          new AllNotVisitedProfilesByAehnlichkeitsmassCallback());
    }
  }

  /**
   * Nested Class für den Profile nach Ähnlichkeitsmaß auslesen callback
   * 
   *
   */
  private class AllNotVisitedProfilesByAehnlichkeitsmassCallback
      implements AsyncCallback<ArrayList<Profil>> {
   
	  /**
	   * Alle nicht besuchten Profile werden ausgegeben.
	   */
	  @Override
    public void onSuccess(ArrayList<Profil> result) {
      addProfilesToTable(result);
    }

    @Override
    public void onFailure(Throwable caught) {}
  }

  /**
   * ClickHandler für den neueProfilAnzeigenButton
   * 
   * @author Funke, Volz
   *
   */
  private class NeueProfilAnzeigeClickHandler implements ClickHandler {

	  /**
	   * Button "pushed" setzen und alle neuen Profile abfragen
	   */
    @Override
    public void onClick(ClickEvent event) {
      profileAnzeigenButton.setPushed(false);
      neueProfilAnzeigenButton.setPushed(true);
      nichtBesuchteProfilAnzeigenButton.setPushed(false);
      pop.load();
      pbVerwaltung.getAllNewProfilesByAehnlichkeitsmass(profil,
          new AllNewProfilesByAehnlichkeitsmassCallback());
    }
  }
  
  /**
   * 
   * Nested Class für den alle Profile nach Ähnlichkeitsmaß callback 
   *
   */
  private class AllNewProfilesByAehnlichkeitsmassCallback
      implements AsyncCallback<ArrayList<Profil>> {
    
	  /**
	   *  Profile und Aehnlichkeitsmass ausgeben.
	   */
	  @Override
    public void onSuccess(ArrayList<Profil> result) {
      addProfilesToTable(result);
    }

    @Override
    public void onFailure(Throwable caught) {}
  }
  
  
  /**
   * Methode um Profile anzuzeigen. Dies geschieht mit Hilfe
   * der Klasse DataGridProfiles.
   * @param result
   */
  private void addProfilesToTable(ArrayList<Profil> result) {
    pop.stop();
    profilListe = result;
    DataGridProfiles dgp = new DataGridProfiles(profilListe);
    dgp.addClickFremdProfil();
    RootPanel.get("search-table").clear();
    RootPanel.get("search-table").add(dgp.start());
  }
}
