package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.superteam2000.gwt.client.gui.EigenschaftPanel;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Profil;

public class Eigenschaft extends BasicFrame {

  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
  Profil profil = ClientsideSettings.getCurrentUser();
  ArrayList<Info> infoListe;
  FlowPanel fPanel = new FlowPanel();
  FlowPanel fPanel2 = new FlowPanel();


  @Override
  protected String getHeadlineText() {
    return "Eigenschaften";
  }

  @Override
  protected void run() {
    fPanel.setStyleName("pure-form pure-form-aligned");
    fPanel2.setStyleName("content");

    fPanel2.add(fPanel);
    RootPanel.get("main").add(fPanel2);
    pbVerwaltung.getInfoByProfile(profil, new AsyncCallback<ArrayList<Info>>() {

      @Override
      public void onSuccess(ArrayList<Info> result) {
        infoListe = result;

      }

      @Override
      public void onFailure(Throwable caught) {
        // TODO Auto-generated method stub

      }
    });

    pbVerwaltung.getAllAuswahl(new AuswahlCallback());


  }

  class AuswahlCallback implements AsyncCallback<ArrayList<Auswahl>> {

    @Override
    public void onFailure(Throwable caught) {}

    @Override
    public void onSuccess(ArrayList<Auswahl> result) {
      if (result != null) {
        for (Auswahl a : result) {
          // Befülle die Zeilen der Tabelle mit Auswahlobjektinformationen und der Checkbox
          EigenschaftPanel ePanel = new EigenschaftPanel(a, false, infoListe);
          fPanel.add(ePanel);


        }
        pbVerwaltung.getAllBeschreibung(new BeschreibungCallback());
      } else {
        ClientsideSettings.getLogger().info("result == null");
      }
    }


  }

  class BeschreibungCallback implements AsyncCallback<ArrayList<Beschreibung>> {

    @Override
    public void onFailure(Throwable caught) {
      // TODO Auto-generated method stub

    }

    @Override
    public void onSuccess(ArrayList<Beschreibung> result) {
      if (result != null) {
        for (Beschreibung b : result) {
          EigenschaftPanel ePanel = new EigenschaftPanel(b, false, infoListe);
          fPanel.add(ePanel);
        }

      } else {
        ClientsideSettings.getLogger().info("result == null");
      }
    }
  }

  @Override
  protected String getSubHeadlineText() {
    // TODO Auto-generated method stub
    return "Was passt zu Dir?";
  }
}


