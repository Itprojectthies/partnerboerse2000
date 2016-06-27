package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import de.superteam2000.gwt.client.gui.DataGridProfiles;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Merkzettel;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Diese Klasse ist zum Anzeigen sämtlicher vom Benutzer gemerkten Profile.
 *
 * @author Christopher
 *
 */
public class Merkliste extends BasicFrame {

  // pb Verwaltung über ClientsideSettings holen
  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
  Profil profil = ClientsideSettings.getCurrentUser();

  @Override
  public String getHeadlineText() {

    return "Merkliste";
  }

  // Das ausgewählte Profil
  private Profil selected = null;

  ArrayList<Profil> profile = new ArrayList<Profil>();

  @Override
  public void run() {

    // Merkliste abfragen und anzeigen
    pbVerwaltung.getMerkzettelForProfil(profil, new AsyncCallback<Merkzettel>() {

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
    });

  }

  /**
   * Clickhandler für den entfernenButton ausgewähltes Element wird von der Liste entfernt (auch aus
   * db)
   *
   * @author Christopher
   *
   */
  public class EntfernenButtonClickhandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      if (selected != null) {
        ClientsideSettings.getPartnerboerseVerwaltung()
            .deleteMerken(ClientsideSettings.getCurrentUser(), selected, new AsyncCallback<Void>() {

              @Override
              public void onSuccess(Void result) {
                RootPanel.get("main").clear();
                Merkliste m = new Merkliste();
                RootPanel.get("main").add(m);

                Window.alert("Profil wurde von der Merkliste entfernt!");

              }

              @Override
              public void onFailure(Throwable caught) {
                // TODO Auto-generated method stub

              }
            });

      }

    }
  }

  /**
   * Clickhandler der das ausgewählte Profil anzeigt, und es als besucht markiert
   *
   * @author Christopher
   *
   */


  @Override
  protected String getSubHeadlineText() {
    // TODO Auto-generated method stub
    return "Hier findest du deine gemerkten Profile";
  }

}
