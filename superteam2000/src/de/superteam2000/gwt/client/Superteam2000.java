package de.superteam2000.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Entry point Klasse der Partnerbörse2000
 * @author Volz, Funke
 */

public class Superteam2000 implements EntryPoint {
  /*
   * Alle notwendigen Instanzvariablen werden deklariert
   */

  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

  /**
   * Anmeldevorgang eines Users
   */
  @Override
  public void onModuleLoad() {
    
    pbVerwaltung.login(GWT.getHostPageBaseURL() + "Superteam2000.html", new LoginCallback());

  }

  /**
   * Asynchrone Anmelde-Klasse.
   *
   * @author Volz, Funke
   *
   */
  class LoginCallback implements AsyncCallback<Profil> {

	  /**
	   * Fehlermeldung, wenn Login nicht durchgeführt werden konnte
	   */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().severe("Login fehlgeschlagen!");
    }

    /**
     * Entscheidungen werden abgefragt und geloest, fuer verschiedene Login-Faelle
     */
    @Override
    public void onSuccess(Profil result) {

      ClientsideSettings.setCurrentUser(result);

      // User eingeloggt und nicht in der db vorhanden?
      if (result.isLoggedIn() && !result.isCreated()) {
        ClientsideSettings.getLogger().info("Erstelle profil für " + result.getEmail());
        profilErstellen();

        // User exisitiert in der db und loggt sich ein.
      } else if (result.isLoggedIn()) {
        ClientsideSettings.getLogger().info("Lade vorhandenes Profil");
        loadProfil();

        // signup link für login anzeigen
      } else {
        createLoginScreen(result);
      }
    }

    /**
     * 
     * @param splashContaiern
     * @param splash
     * @param headingElement
     * @param splashSubhead
     * @param splashParagraph
     * @param loginAnchor
     */
    private void createLoginScreen(Profil result) {
      FlowPanel splashContaiern = new FlowPanel();
      splashContaiern.setStyleName("splash-container");
      
      FlowPanel splash = new FlowPanel();
      splash.setStyleName("splash");

      HTML headingElement = new HTML();
      headingElement.setHTML("Willkommen auf PartnerBörse2000");
      headingElement.setStyleName("splash-head");

      FlowPanel splashSubhead = new FlowPanel(ParagraphElement.TAG);
      splashSubhead.setStyleName("splash-subhead");
      HTML splahParagraph = new HTML("Melde dich an und finde deine bessere Hälfte");
      splashSubhead.add(splahParagraph);

      Anchor loginAnchor = new Anchor("Los!");
      loginAnchor.setStyleName("pure-button-login pure-button-primary-login");
      loginAnchor.setHref(result.getLoginUrl());

      splash.add(headingElement);
      splash.add(splashSubhead);
      splash.add(loginAnchor);
      
      splashContaiern.add(splash);
      RootPanel.get("main").add(splashContaiern);
    }

    /**
     * Profil soll erstellt werden und angezeigt werden
     * @param cf Profildaten laden
     */
    private void profilErstellen() {
      BasicFrame cf = new CreateProfil();
      RootPanel.get("main").clear();
      RootPanel.get("main").add(cf);
    }

    /**
     * Profil laden und anzeigen
     * @param nb Navigationsbar laden
     */
    private void loadProfil() {
      Navbar nb = new Navbar();
      RootPanel.get("menu").clear();
      RootPanel.get("menu").add(nb);

      ShowProfil sp = new ShowProfil();
      RootPanel.get("main").add(sp);
    }
  }

}
