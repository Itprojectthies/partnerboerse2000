package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.superteam2000.gwt.client.gui.DataGridProfiles;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Merkzettel;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Diese Klasse ist zum Anzeigen sämtlicher vom Benutzer gemerkten Profile.
 *
 * @author Volz, Funke
 *
 */
public class Merkliste extends BasicFrame {
  /*
   * Alle notwendigen Instanzvariablen werden deklariert
   */

  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

  Profil user = ClientsideSettings.getCurrentUser();

  ArrayList<Profil> profile = new ArrayList<Profil>();

  /**
   * Headline Text wird zurüchgegeben
   * @return Text
   */
  @Override
  public String getHeadlineText() {

    return "Merkliste";
  }

  /**
   * SubHeadline Text wird zurüchgegeben
   * @return auszugebender Text
   */
  @Override
  protected String getSubHeadlineText() {
    return "Hier findest du deine gemerkten Profile";
  }

  PopupPanel pop = new PopupPanel();
  
  /**
   * Die Merkliste wird abgefragt und angezeigt.
   */
  @Override
  public void run() {
    
    pbVerwaltung.getMerkzettelForProfil(user, new MerkzettelForProfilCallback());

  }

  /**
   * Diese Klasse enthaelt alle Methoden zum anzeigen und bearbeiten der Merkliste eines Users.
   * @author Funke
   *
   */
  private class MerkzettelForProfilCallback implements AsyncCallback<Merkzettel> {
    
	  /**
	   * Falls eine Merkliste gefunden werden konnte, werden alle dort gelisteten Profile
	   * gesucht und in Panel uebergeben, welches die Profile ausgibt.
	   */
	  @Override
    public void onSuccess(Merkzettel result) {
      profile = result.getGemerkteProfile();
      DataGridProfiles dgp = new DataGridProfiles(profile);
      dgp.addClickFremdProfil();

      RootPanel.get("main").add(dgp.start());
    }

	  /**
	   * Falls kein Merkzettel gefunden werden konnte und zum abfangen sonstiger Fehler.
	   */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().info("Fehler AsyncCallback Merkzettel in Merkliste");
    }
  }
}
