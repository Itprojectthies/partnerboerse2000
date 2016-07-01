package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

import de.superteam2000.gwt.client.gui.CustomButton;
import de.superteam2000.gwt.client.gui.DataGridProfiles;
import de.superteam2000.gwt.client.gui.Notification;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Kontaktsperre;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Klasse zur Darstellung des Profils eines eingeloggten Users
 *
 * @author Volz, Funke
 */
public class Sperre extends BasicFrame {
  /*
   * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
   */

  // pbVerwaltung über ClientsideSettings holen
  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
  Profil user = ClientsideSettings.getCurrentUser();

  CustomButton profilEntfernenButton = new CustomButton("Entfernen ");
  ArrayList<Profil> profile = new ArrayList<>();

  FlowPanel alignPanel = new FlowPanel();
  FlowPanel contentPanel = new FlowPanel();

  Profil selectedProfile;
  DataGridProfiles dgp;

  SingleSelectionModel<Profil> selectionModel = new SingleSelectionModel<Profil>();

  /**
   * Headline Text zurückgeben
   * @return Text
   */
  @Override
  public String getHeadlineText() {
    return "Sperrliste";
  }

  /**
   * SubHeadline Text zurückgeben
   * @return Text
   */
  @Override
  protected String getSubHeadlineText() {
    return "Hier findest du deine gesperrten Profile";
  }

  /**
   * Alle notwendigen Buttons werden hinzugefuegt und alle gesperrten Kontakte aus der DB
   * ausgelesen, um die Tabelle zum anzeigen zu fuellen.
   */
  @Override
  public void run() {

    profilEntfernenButton.setEnabled(false);
    profilEntfernenButton.setIcon("fa fa-times");
    profilEntfernenButton.setStyleName("pure-button");

    alignPanel.setStyleName("pure-form pure-form-aligned content");
//    contentPanel.setStyleName("content");

    alignPanel.add(profilEntfernenButton);
    contentPanel.add(alignPanel);

    profilEntfernenButton.addClickHandler(new EntfernenButtonClickhandler());

    selectionModel.addSelectionChangeHandler(new SelectionChangeHandler());

    pbVerwaltung.getKontaktsperreForProfil(user, new KontaktsperreForProfilCallback());

  }

  /**
   * Kontaktsperre umsetzen, sodass ein gesperrtes Profil das sperrende Profil nicht kontaktieren kann.
   */
  private class KontaktsperreForProfilCallback implements AsyncCallback<Kontaktsperre> {
   
	  /**
	   * Kontaktsperre fuer gesperrtes Profil durchfuehren
	   */
	  @Override
    public void onSuccess(Kontaktsperre result) {

      profile = result.getGesperrteProfile();
      DataGridProfiles dgp = new DataGridProfiles(profile);

      contentPanel.add(dgp.start());
      RootPanel.get("main").add(contentPanel);

      dgp.getTable().setSelectionModel(selectionModel);
    }

	  /**
	   * Um Fehler abzufangen und Fehlermeldung auszugeben
	   */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().info("Fehler KontaktsperreForProfilCallback");
    }
  }

  /**
   * Selektieren eines Profils
   */
  private class SelectionChangeHandler implements Handler {
    
	  /**
	   * Selektiertes Profil kann entfernt werden
	   */
	  @Override
    public void onSelectionChange(SelectionChangeEvent event) {
	  //Button sichtbar machen, sodass Sperre aufgehoben werden kann
      profilEntfernenButton.setEnabled(true);
      //Selektiertes Profil setzen
      selectedProfile = selectionModel.getSelectedObject();
    }
  }

  /**
   * Clickhandler für den entfernenButton ausgewähltes Element wird von der Liste entfernt (auch aus
   * db)
   */
  public class EntfernenButtonClickhandler implements ClickHandler {

	  /**
	   * Sperre wird fuer selektiertes Profil aufgehoben
	   */
    @Override
    public void onClick(ClickEvent event) {

      if (selectedProfile != null) {
        pbVerwaltung.deleteSperre(user, selectedProfile, new DeleteSperreCallback());
      }
    }
  }

  /**
   * Nachdem Sperre aufgehoben wurde, wird Sperrliste erneut angezeigt.
   */
  private class DeleteSperreCallback implements AsyncCallback<Void> {
    
	  /**
	   * Sperrliste erneut anzeigen und Meldung ueber Aufhebung anzeigen.
	   */
	  @Override
    public void onSuccess(Void result) {
      new Notification("Sperre für " + selectedProfile.getVorname() + " entfernt", "info");
      
      RootPanel.get("main").clear();
      Sperre s = new Sperre();
      RootPanel.get("main").add(s);
    }

	  /**
	   * Um Fehler abzufangen
	   */
    @Override
    public void onFailure(Throwable caught) {}
  }

}
