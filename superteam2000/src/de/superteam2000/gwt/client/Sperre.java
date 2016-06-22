package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SelectionChangeEvent.HasSelectionChangedHandlers;
import com.google.gwt.view.client.SingleSelectionModel;

import de.superteam2000.gwt.client.gui.DataGridProfiles;
import de.superteam2000.gwt.client.gui.Notification;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Kontaktsperre;
import de.superteam2000.gwt.shared.bo.Profil;

public class Sperre extends BasicFrame {

  @Override
  public String getHeadlineText() {

    return "Sperrliste";
  }

  @Override
  protected String getSubHeadlineText() {
    // TODO Auto-generated method stub
    return "Hier findest du deine gemerkten Profile";
  }

  ArrayList<Profil> profile = new ArrayList<>();

  // pbVerwaltung über ClientsideSettings holen
  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
  Profil profil = ClientsideSettings.getCurrentUser();

  FlowPanel fPanel2 = new FlowPanel();
  Profil selected;
  DataGridProfiles dgp;

  final SingleSelectionModel<Profil> selectionModel = new SingleSelectionModel<Profil>();

  @Override
  public void run() {
    final Button profilEntfernenButton = new Button("Entfernen");
    profilEntfernenButton.setStyleName("pure-button");


    FlowPanel fPanel = new FlowPanel();
    FlowPanel buttonsPanel = new FlowPanel();

    fPanel.setStyleName("pure-form pure-form-aligned content");
    // fPanel2.setStyleName("content");

    buttonsPanel.add(profilEntfernenButton);

    fPanel.add(buttonsPanel);
    fPanel2.add(fPanel);

    profilEntfernenButton.addClickHandler(new EntfernenButtonClickhandler());

    selectionModel.addSelectionChangeHandler(new Handler() {
      @Override
      public void onSelectionChange(SelectionChangeEvent event) {
        // ausgewähltes Profil setzen
        selected = selectionModel.getSelectedObject();
      }
    });

    // Hole alle gesperrte Kontakte, um damit die Tabelle zu füllen und um diese anzuzeigen
    pbVerwaltung.getKontaktsperreForProfil(profil, new AsyncCallback<Kontaktsperre>() {

          @Override
          public void onSuccess(Kontaktsperre result) {

            profile = result.getGesperrteProfile();
            DataGridProfiles dgp = new DataGridProfiles(profile);

            fPanel2.add(dgp.start());
            RootPanel.get("main").add(fPanel2);

            dgp.getTable().setSelectionModel(selectionModel);
          }

          @Override
          public void onFailure(Throwable caught) {
            ClientsideSettings.getLogger().info("Fehler !!");

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
            .deleteSperre(ClientsideSettings.getCurrentUser(), selected, new AsyncCallback<Void>() {

              @Override
              public void onSuccess(Void result) {
                Notification n1 =
                    new Notification("Sperre für " + selected.getVorname() + " entfernt", "info");
                RootPanel.get("main").clear();
                Sperre s = new Sperre();
                RootPanel.get("main").add(s);
              }

              @Override
              public void onFailure(Throwable caught) {
                // TODO Auto-generated method stub

              }
            });

      }

    }
  }

}
