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

public class Sperre extends BasicFrame {

  @Override
  public String getHeadlineText() {

    return "Sperrliste";
  }

  @Override
  protected String getSubHeadlineText() {
    // TODO Auto-generated method stub
    return "Hier findest du deine gesperrten Profile";
  }

  CustomButton profilEntfernenButton = new CustomButton("Entfernen ");
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

    this.profilEntfernenButton.setEnabled(false);
    this.profilEntfernenButton.setIcon("fa fa-times");
    this.profilEntfernenButton.setStyleName("pure-button");


    FlowPanel fPanel = new FlowPanel();

    fPanel.setStyleName("pure-form pure-form-aligned content");
    this.fPanel2.setStyleName("content");


    fPanel.add(this.profilEntfernenButton);
    this.fPanel2.add(fPanel);

    this.profilEntfernenButton.addClickHandler(new EntfernenButtonClickhandler());

    this.selectionModel.addSelectionChangeHandler(new Handler() {
      @Override
      public void onSelectionChange(SelectionChangeEvent event) {
        Sperre.this.profilEntfernenButton.setEnabled(true);
        // ausgewähltes Profil setzen
        Sperre.this.selected = Sperre.this.selectionModel.getSelectedObject();
      }
    });

    // Hole alle gesperrte Kontakte, um damit die Tabelle zu füllen und um diese anzuzeigen
    this.pbVerwaltung.getKontaktsperreForProfil(this.profil, new AsyncCallback<Kontaktsperre>() {

      @Override
      public void onSuccess(Kontaktsperre result) {

        Sperre.this.profile = result.getGesperrteProfile();
        DataGridProfiles dgp = new DataGridProfiles(Sperre.this.profile);

        Sperre.this.fPanel2.add(dgp.start());
        RootPanel.get("main").add(Sperre.this.fPanel2);

        dgp.getTable().setSelectionModel(Sperre.this.selectionModel);
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

      if (Sperre.this.selected != null) {
        ClientsideSettings.getPartnerboerseVerwaltung().deleteSperre(
            ClientsideSettings.getCurrentUser(), Sperre.this.selected, new AsyncCallback<Void>() {

              @Override
              public void onSuccess(Void result) {
                new Notification("Sperre für " + Sperre.this.selected.getVorname() + " entfernt",
                    "info");
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
