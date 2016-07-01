package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.superteam2000.gwt.client.gui.CustomPopupPanel;
import de.superteam2000.gwt.client.gui.EigenschaftPanel;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Klasse zum Anzeigen, Hinzufügen und Löschen von Eigenschaften. Diese Infos werden zusaetzlich
 * zu den Pflichtattributen eines Profils abgespeichert und beschreiben den User genauer.
 *
 * @author Volz
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

/**
 * Headline auslesen
 */
  @Override
  protected String getHeadlineText() {
    return "Eigenschaften";
  }

  /**
   * SubHeadline auslesen
   */
  @Override
  protected String getSubHeadlineText() {
    return "Was passt zu Dir?";
  }

  CustomPopupPanel pop = new CustomPopupPanel(false, true);

  /**
   * Die Eigenschaften werden angezeigt.
   */
  @Override
  protected void run() {
    
    pop.load();
    
    alignPanel.setStyleName("pure-form pure-form-aligned");
    contentPanel.setStyleName("content");

    contentPanel.add(alignPanel);
    RootPanel.get("main").add(contentPanel);
    
    
    pbVerwaltung.getInfoByProfile(profil, new InfoByProfileCallback());
    pbVerwaltung.getAllAuswahl(new AuswahlCallback());
  }

  /**
   * Bisher gespeicherte Eigenschaften werden geholt.
   *
   */
  private class InfoByProfileCallback implements AsyncCallback<ArrayList<Info>> {
    
	  /**
	   * Wenn Infos gefunden werden konnten, werden diese in die infoListe gespeichert
	   */
	  @Override
    public void onSuccess(ArrayList<Info> result) {
      infoListe = result;
    }

    @Override
    public void onFailure(Throwable caught) {}
  }

  /**
   * Wenn der User eine Auswahl getroffen hat, wird diese hier bearbeitet.
   *
   */
  class AuswahlCallback implements AsyncCallback<ArrayList<Auswahl>> {
    
    @Override
    public void onFailure(Throwable caught) {}

    @Override
    public void onSuccess(ArrayList<Auswahl> result) {
      pop.stop();
      if (result != null) {
        for (Auswahl a : result) {

          EigenschaftPanel ePanel = new EigenschaftPanel(a, false, infoListe);
          alignPanel.add(ePanel);
        }
        pbVerwaltung.getAllBeschreibung(new BeschreibungCallback());
      } else {
        ClientsideSettings.getLogger().info("result == null");
      }
    }
  }

  /**
   *Nested Class um die Beschreibung(en) auszulesen und anzuzeigen
   *
   */
  class BeschreibungCallback implements AsyncCallback<ArrayList<Beschreibung>> {

	  /**
	   * Fehler abfangen
	   */
    @Override
    public void onFailure(Throwable caught) {}

 
    @Override
    public void onSuccess(ArrayList<Beschreibung> result) {
      if (result != null) {
        for (Beschreibung b : result) {
          EigenschaftPanel ePanel = new EigenschaftPanel(b, false, infoListe);
          alignPanel.add(ePanel);
        }

      } 
      /*
       * Falls keine gespeicherten Eigenschaften gefunden werden konnten.
       */
      else {
        ClientsideSettings.getLogger().info("result == null");
      }
    }
  }
}


