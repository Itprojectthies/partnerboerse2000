package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.superteam2000.gwt.client.gui.CustomPopupPanel;
import de.superteam2000.gwt.client.gui.ListItemWidget;
import de.superteam2000.gwt.client.gui.UnorderedListWidget;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Dient zur Anzeige der Navigationsleiste und zum aufrufen von einzelnen Seiten (z.B. eigenes
 * Profil, Suche, Eigenschaften usw.)
 *
 * @author Volz
 *
 */
public class Navbar extends VerticalPanel {
  /*
   * Alle notwendigen Instanzvariablen werden deklariert
   */

  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

  Profil user = ClientsideSettings.getCurrentUser();

  CustomPopupPanel pop = new CustomPopupPanel(false, true);

  /**
   * Hier wird die Navigationsbar geladen, die Eigenschaften und das Aussehen uebergeben.
   */
  @Override
  protected void onLoad() {
    RootPanel.get("menu").getElement().getStyle().setBackgroundColor("#b75d6b");

    /*
     * Fuer einen eingeloggten User werden die folgenden Dinge definiert.
     */
    if ((user != null) && user.isLoggedIn()) {

      // Menupanel
      FlowPanel menu = new FlowPanel();
      FlowPanel pureMenu = new FlowPanel();
      UnorderedListWidget menuList = new UnorderedListWidget();

      // Home "Button"
      Anchor home = new Anchor("PartnerBörse", GWT.getHostPageBaseURL() + "Superteam2000.html");

      // Menustiles
      menuList.setStyleName("pure-menu-list");
      home.setStyleName("pure-menu-heading");
      pureMenu.setStyleName("pure-menu");

      // "Button" Definition wie Logout, Profil, Merkliste, ...
      Anchor logoutAnchor = new Anchor("Logout");
      Anchor profilAnchor = new Anchor("Profil");
      Anchor merklisteAnchor = new Anchor("Merkliste");
      Anchor sperrlisteAnchor = new Anchor("Sperrliste");
      Anchor suchprofilAnchor = new Anchor("Suchprofil");
      Anchor eigenschaftenAnchor = new Anchor("Eigenschaften");
      Anchor allProfilesAnchor = new Anchor("Alle Profile");
      Anchor reportAnchor = new Anchor("Report");

      /*
       * "Buttons" bzw. Anker werden dem Widget hinzugefuegt
       */
      profilAnchor.setStyleName("pure-menu-link");
      menuList.add(new ListItemWidget(profilAnchor));

      eigenschaftenAnchor.setStyleName("pure-menu-link");
      menuList.add(new ListItemWidget(eigenschaftenAnchor));

      merklisteAnchor.setStyleName("pure-menu-link");
      menuList.add(new ListItemWidget(merklisteAnchor));

      sperrlisteAnchor.setStyleName("pure-menu-link");
      menuList.add(new ListItemWidget(sperrlisteAnchor));

      suchprofilAnchor.setStyleName("pure-menu-link");
      menuList.add(new ListItemWidget(suchprofilAnchor));

      allProfilesAnchor.setStyleName("pure-menu-link");
      menuList.add(new ListItemWidget(allProfilesAnchor));

      reportAnchor.setStyleName("pure-menu-link");
      menuList.add(new ListItemWidget(reportAnchor));

      logoutAnchor.setStyleName("pure-menu-link");
      menuList.add(new ListItemWidget(logoutAnchor));

      pureMenu.add(home);
      pureMenu.add(menuList);
      menu.add(pureMenu);

      RootPanel.get("menu").add(menu);

      /*
       * Die "Buttons" bzw. Anker werden dem ClickHandler ubergeben, sodass sie reagieren koennen,
       * wenn sie geklickt werden.
       */
      logoutAnchor.addClickHandler(new LogoutClickHandler());
      profilAnchor.addClickHandler(new ProfilClickHandler());
      merklisteAnchor.addClickHandler(new MerklisteClickHandler());
      sperrlisteAnchor.addClickHandler(new SperrlisteClickHandler());
      suchprofilAnchor.addClickHandler(new SuchprofilClickHandler());
      eigenschaftenAnchor.addClickHandler(new EigenschaftenClickHandler());
      allProfilesAnchor.addClickHandler(new AlleProfileClickHandler());
      reportAnchor.addClickHandler(new ReportClickHandler());

    }
  }

  /**
   * Die Report Seite wird an die Navbar angebunden.
   *
   */
  private class ReportClickHandler implements ClickHandler {

    /**
     * HTML Seite des Reports anbinden.
     */
    @Override
    public void onClick(ClickEvent event) {
      Window.Location.replace(GWT.getHostPageBaseURL() + "ReportGen.html");
    }
  }

  /**
   * AlleProfileClickHandler implementriert das Interface ClickHandler. OnClick wird ein Objekt der
   * AllProfilesTable zum RootPanel "main" hinzugefügt und alle Profile nach Aenlichkeitsmaß
   * berechnet.
   *
   */
  private class AlleProfileClickHandler implements ClickHandler {

    /**
     * Eine Tabelle zur Ausgabe aller Profile wird angelegt.
     */
    @Override
    public void onClick(ClickEvent event) {
      pop.load();
      AllProfilesTable dgt = new AllProfilesTable();
      RootPanel.get("main").clear();
      RootPanel.get("main").add(dgt);
      pbVerwaltung.getProfilesByAehnlichkeitsmass(user, new ProfilesByAehnlichkeitsmassCallback());
    }
  }

  /**
   * Die erhaltenen Profile nach Aehnlichekitsmaß werden dem AllProfilesTable kontruktor übergeben
   * und zum RootPanel "main" hinzugefügt.
   *
   */
  private class ProfilesByAehnlichkeitsmassCallback implements AsyncCallback<ArrayList<Profil>> {

    /**
     * Aehnlichkeit wird ausgegeben.
     */
    @Override
    public void onSuccess(ArrayList<Profil> result) {
      AllProfilesTable dgt2 = new AllProfilesTable(result);
      pop.stop();
      RootPanel.get("main").clear();
      RootPanel.get("main").add(dgt2);

    }

    /**
     * Um Fehler abzufangen.
     */
    @Override
    public void onFailure(Throwable caught) {}
  }

  /**
   * EigenschaftenClickHandler zum hinzufügen eines Eigenschaften-Objekts zum RootPanel
   * 
   */
  private class EigenschaftenClickHandler implements ClickHandler {

    /**
     * Eigenschaften koennen angezeigt werden.
     */
    @Override
    public void onClick(ClickEvent event) {
      Eigenschaft e = new Eigenschaft();
      RootPanel.get("main").clear();
      RootPanel.get("main").add(e);
    }
  }

  /**
   * SuchprofilClickHandler zum hinzufügen eines Suche-Objekts zum RootPanel
   */
  private class SuchprofilClickHandler implements ClickHandler {

    /**
     * Die Suchoptionen werden angezeigt.
     */
    @Override
    public void onClick(ClickEvent event) {
      Suche s = new Suche();
      RootPanel.get("main").clear();
      RootPanel.get("main").add(s);
    }
  }

  /**
   * SperrlisteClickHandler zum hinzufügen eines Sperre-Objekts zum RootPanel
   */
  private class SperrlisteClickHandler implements ClickHandler {

    /**
     * Sperrliste wird angezeigt.
     */
    @Override
    public void onClick(ClickEvent event) {
      Sperre s = new Sperre();
      RootPanel.get("main").clear();
      RootPanel.get("main").add(s);
    }
  }

  /**
   * MerklisteClickHandler zum hinzufügen eins Merkliste-Objekts zum RootPanel
   */
  private class MerklisteClickHandler implements ClickHandler {

    /**
     * Merkliste wird der Anzeige hinzugefuegt.
     */
    @Override
    public void onClick(ClickEvent event) {
      Merkliste m = new Merkliste();
      RootPanel.get("main").clear();
      RootPanel.get("main").add(m);
    }
  }

  /**
   * ProfilClickHandler zum hinzufügen eins ShowProfil-Objekts zum RootPanel
   */
  private class ProfilClickHandler implements ClickHandler {

    /**
     * Profil kann angezeigt werden.
     */
    @Override
    public void onClick(ClickEvent event) {
      ShowProfil sp = new ShowProfil();
      RootPanel.get("main").clear();
      RootPanel.get("main").add(sp);
    }
  }

  /**
   * ProfilClickHandler zum Ausloggen des Benutzers
   */
  private class LogoutClickHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {
      Window.open(user.getLogoutUrl(), "_self", "");
    }
  }

}
