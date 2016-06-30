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
 * Alle Methoden, welche zum Anzeigen aller Methoden in einer Tabelle dienen.
 * 
 * @author Funke, Volz
 *
 */
public class AllProfilesTable extends BasicFrame {

  CustomPopupPanel pop = new CustomPopupPanel(false, true);
  
  private ArrayList<Profil> profilListe = new ArrayList<Profil>();

  /**
   * Profile werden ausgelesen und als Liste gespeichert.
   * @param list Alle Profile in einer Liste speichern.
   */
  public AllProfilesTable(ArrayList<Profil> list) {
    profilListe = list;
  }
  
  /**
   * leere Methode um Tabelle zu befuellen.
   */
  public AllProfilesTable() {
  }

  /**
   * Profilliste wird ausgegeben
   * @return profilListe
   */
  public ArrayList<Profil> getProfilListe() {
    return profilListe;
  }

  /**
   * Profilliste erstellen
   * @param profilListe
   */
  public void setProfilListe(ArrayList<Profil> profilListe) {
    this.profilListe = profilListe;
  }

  /**
   * Headline Text setzen
   * @return Text
   */
  @Override
  public String getHeadlineText() {
    return "Alle Profile";
  }

  /**
   * SubHeadline Text setzen
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
   * Alle Buttons, welche benoetigt werden, werden definiert und gestylt und dann dem Panel hinzufuegt.
   * 
   * @param neueProfileAnzeigenButton damit alle Profile in einer Liste angezeigt werden koennen
   * @param nichtBesuchteProfilAnzeigenButton damit alle noch nicht besuchten Profile angezeigt werden koennen
   * @param profileAnzeigenButton einzelner Profil anzeigen lassen
   * @param contentPanel fuer Style
   * @param fPanel2 Hilfsvariable
   * @param buttonsPanel Buttons werden darauf angeordnet und angezeigt
   * @param dgp Hilfsvariable fuer DataGrid und somit Anzeige von Profilen
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
   * Damit alle Profile angezeigt werden koennen.
   * 
   * @author Funke, Volz
   *
   */
  private class AlleProfileClickHandler implements ClickHandler {
    
	  /**
	   * Alle Profile und Daten aus Datenbank auslesen, Aehnlichkeit anzeigen
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
   * Aehnlichkeitsmass berechnen und zurueckgeben.
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

    

	  /**
	   * um Fehler abzufangen.
	   */
    @Override
    public void onFailure(Throwable caught) {}
  }

  /**
   * Damit die bisher nicht besuchten Profile angezeigt werden koennen.
   * 
   * @author Funke, Volz
   *
   */
  private class NichtBesuchteProfileClickHandler implements ClickHandler {

	  /**
	   * Alle besuchten Profile werden ausgelesen um die nicht besuchten Profile zu erreichen.
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
   * Alle nicht besuchten Profile werden ausgegeben.
   * 
   * @author Funke, Volz
   *
   */
  private class AllNotVisitedProfilesByAehnlichkeitsmassCallback
      implements AsyncCallback<ArrayList<Profil>> {
   
	  /**
	   * Alle nicht besuchten Profile werden ausgegeben.
	   * @param dgt Alle nicht besuchten Profile werden gespeichert
	   */
	  @Override
    public void onSuccess(ArrayList<Profil> result) {
      addProfilesToTable(result);
    }

	  /**
	   * Um Fehler abzufangen
	   */
    @Override
    public void onFailure(Throwable caught) {}
  }

  /**
   * Neue Profile, d.h. seit dem letzten Login hinzugekommene Profile, anzeigen.
   * 
   * @author Funke, Volz
   *
   */
  private class NeueProfilAnzeigeClickHandler implements ClickHandler {

	  /**
	   * Alle neuen Profile werden aus der Datenbank ausgelesen.
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
   * Aehnlichkeitsmass fuer Profile berechnen und gemeinsam ausgeben.
   * 
   * @author Funke, Volz
   *
   */
  private class AllNewProfilesByAehnlichkeitsmassCallback
      implements AsyncCallback<ArrayList<Profil>> {
    
	  /**
	   * Neue Profile und Aehnlichkeitsmass ausgeben.
	   */
	  @Override
    public void onSuccess(ArrayList<Profil> result) {
      addProfilesToTable(result);
    }

	  /**
	   * Um Fehler abzufangen.
	   */
    @Override
    public void onFailure(Throwable caught) {}
  }
  
  private void addProfilesToTable(ArrayList<Profil> result) {
    pop.stop();
    profilListe = result;
    DataGridProfiles dgp = new DataGridProfiles(profilListe);
    dgp.addClickFremdProfil();
    RootPanel.get("search-table").clear();
    RootPanel.get("search-table").add(dgp.start());
  }
}
