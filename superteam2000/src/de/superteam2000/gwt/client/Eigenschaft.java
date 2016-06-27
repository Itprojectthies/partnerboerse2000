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

/**
 * Klasse zum Anzeigen, Hinzufügen und Löschen von Eigenschaften
 *
 * @author Volz Daniel
 */

public class Eigenschaft extends BasicFrame {
  /*
   * Alle notwendigen Instanzvariablen werden deklariert
   */

  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

  Profil profil = ClientsideSettings.getCurrentUser();

  ArrayList<Info> infoListe = new ArrayList<Info>();

  FlowPanel alignPanel = new FlowPanel();
  FlowPanel contentPanel = new FlowPanel();


  @Override
  protected String getHeadlineText() {
    return "Eigenschaften";
  }

  @Override
  protected String getSubHeadlineText() {
    return "Was passt zu Dir?";
  }


  @Override
  protected void run() {
    alignPanel.setStyleName("pure-form pure-form-aligned");
    contentPanel.setStyleName("content");

    contentPanel.add(alignPanel);

    RootPanel.get("main").add(contentPanel);

    pbVerwaltung.getInfoByProfile(profil, new InfoByProfileCallback());

    pbVerwaltung.getAllAuswahl(new AuswahlCallback());


  }

  private class InfoByProfileCallback implements AsyncCallback<ArrayList<Info>> {
    @Override
    public void onSuccess(ArrayList<Info> result) {
      infoListe = result;
    }

    @Override
    public void onFailure(Throwable caught) {}
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
          alignPanel.add(ePanel);
        }
        pbVerwaltung.getAllBeschreibung(new BeschreibungCallback());
      } else {
        ClientsideSettings.getLogger().info("result == null");
      }
    }
  }

  class BeschreibungCallback implements AsyncCallback<ArrayList<Beschreibung>> {

    @Override
    public void onFailure(Throwable caught) {}

    @Override
    public void onSuccess(ArrayList<Beschreibung> result) {
      if (result != null) {
        for (Beschreibung b : result) {
          EigenschaftPanel ePanel = new EigenschaftPanel(b, false, infoListe);
          alignPanel.add(ePanel);
        }

      } else {
        ClientsideSettings.getLogger().info("result == null");
      }
    }
  }
}


