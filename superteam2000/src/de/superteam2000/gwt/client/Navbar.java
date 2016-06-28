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

import de.superteam2000.gwt.client.gui.ListItemWidget;
import de.superteam2000.gwt.client.gui.UnorderedListWidget;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Diese Klasse bildet die Navigationsleiste ab
 *
 * @author Volz Daniel
 *
 */
public class Navbar extends VerticalPanel {
  /*
   * Alle notwendigen Instanzvariablen werden deklariert
   */

  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

  Profil user = ClientsideSettings.getCurrentUser();

  @Override
  protected void onLoad() {
    RootPanel.get("menu").getElement().getStyle().setBackgroundColor("#191818");

    if ((user != null) && user.isLoggedIn()) {

      FlowPanel menu = new FlowPanel();
      FlowPanel pureMenu = new FlowPanel();
      UnorderedListWidget menuList = new UnorderedListWidget();

      Anchor home = new Anchor("PartnerBörse", GWT.getHostPageBaseURL() + "Superteam2000.html");

      menuList.setStyleName("pure-menu-list");
      home.setStyleName("pure-menu-heading");
      pureMenu.setStyleName("pure-menu");

      Anchor logoutAnchor = new Anchor("Logout");
      Anchor profilAnchor = new Anchor("Profil");
      Anchor merklisteAnchor = new Anchor("Merkliste");
      Anchor sperrlisteAnchor = new Anchor("Sperrliste");
      Anchor suchprofilAnchor = new Anchor("Suchprofil");
      Anchor eigenschaftenAnchor = new Anchor("Eigenschaften");
      Anchor allProfilesAnchor = new Anchor("Alle Profile");
      Anchor reportAnchor = new Anchor("Report");


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

  private class ReportClickHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      Window.Location.replace(GWT.getHostPageBaseURL() + "ReportGen.html");
    }
  }

  private class AlleProfileClickHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {
      
      pbVerwaltung.getProfilesByAehnlichkeitsmass(user, new ProfilesByAehnlichkeitsmassCallback());
    }
  }

  private class ProfilesByAehnlichkeitsmassCallback implements AsyncCallback<ArrayList<Profil>> {
    @Override
    public void onSuccess(ArrayList<Profil> result) {
      AllProfilesTable dgt = new AllProfilesTable(result);
      RootPanel.get("main").clear();
      RootPanel.get("main").add(dgt);
    }

    @Override
    public void onFailure(Throwable caught) {}
  }

  private class EigenschaftenClickHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      Eigenschaft e = new Eigenschaft();
      RootPanel.get("main").clear();
      RootPanel.get("main").add(e);
    }
  }

  private class SuchprofilClickHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      Suche s = new Suche();
      RootPanel.get("main").clear();
      RootPanel.get("main").add(s);
    }
  }

  private class SperrlisteClickHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      Sperre s = new Sperre();
      RootPanel.get("main").clear();
      RootPanel.get("main").add(s);
    }
  }

  private class MerklisteClickHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      Merkliste m = new Merkliste();
      RootPanel.get("main").clear();
      RootPanel.get("main").add(m);
    }
  }

  private class ProfilClickHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      ShowProfil sp = new ShowProfil();
      RootPanel.get("main").clear();
      RootPanel.get("main").add(sp);
    }
  }

  private class LogoutClickHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      Window.open(user.getLogoutUrl(), "_self", "");
    }
  }

}
