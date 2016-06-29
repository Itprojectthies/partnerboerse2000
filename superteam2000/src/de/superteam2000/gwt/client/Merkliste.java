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

  @Override
  public String getHeadlineText() {

    return "Merkliste";
  }

  @Override
  protected String getSubHeadlineText() {
    return "Hier findest du deine gemerkten Profile";
  }

  PopupPanel pop = new PopupPanel();
  @Override
  public void run() {
    
    // Merkliste abfragen und anzeigen
    pbVerwaltung.getMerkzettelForProfil(user, new MerkzettelForProfilCallback());

  }

  private class MerkzettelForProfilCallback implements AsyncCallback<Merkzettel> {
    @Override
    public void onSuccess(Merkzettel result) {
      profile = result.getGemerkteProfile();
      DataGridProfiles dgp = new DataGridProfiles(profile);
      dgp.addClickFremdProfil();

      RootPanel.get("main").add(dgp.start());
    }

    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().info("Fehler AsyncCallback Merkzettel in Merkliste");
    }
  }
}
