package de.superteam2000.gwt.client;

import java.util.ArrayList;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.ListItem;
import org.gwtbootstrap3.client.ui.html.UnorderedList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.superteam2000.gwt.client.gui.ListItemWidget;
import de.superteam2000.gwt.client.gui.UnorderedListWidget;
import de.superteam2000.gwt.shared.bo.Profil;

public class Navbar extends HorizontalPanel {

  @Override
  public void onLoad() {
    /*
     * Bevor wir unsere eigene Formatierung veranslassen, überlassen wir es der Superklasse eine
     * Initialisierung vorzunehmen.
     */
    super.onLoad();

    /*
     * Wenn alles vorbereitet ist, stoßen wir die run()-Methode an. Auch run() ist als abstrakte
     * Methode bzw. als Einschubmethode realisiert. Auch hier ist es Aufgabe der Subklassen, für
     * deren Implementierung zu sorgen.
     */
    this.run();
  }

  Profil user = ClientsideSettings.getCurrentUser();

  public void append(Widget wi) {
    this.add(wi);
  }

//  FlowPanel menu = new FlowPanel();
//  FlowPanel pureMenu = new FlowPanel();
//  Anchor anchor = new Anchor("PartnerBörse2000", GWT.getHostPageBaseURL() + "Superteam2000.html");
//  Anchor anchor2 = new Anchor("PartnerBörse2000", GWT.getHostPageBaseURL() + "Superteam2000.html");
//  UnorderedListWidget menuList = new UnorderedListWidget();
//  
  // this.setCellHorizontalAlignment(hp, ALIGN_RIGHT);
  // hp.setHorizontalAlignment(ALIGN_RIGHT);
  void run() {
//    menuList.setStyleName("pure-menu-list");
//    anchor.setStyleName("pure-menu-heading");
//    menu.getElement().setId("menu");
//    menu.setStyleName("pure-menu");
//    menuList.add(new ListItemWidget(anchor));
//    
//   menu.add(menuList);
//   RootPanel.get("Details").clear();
    
    if (user != null && user.isLoggedIn()) {

      Button logoutBtn = new Button("Logout");
      // hp.add(logoutBtn);
      logoutBtn.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          Window.open(user.getLogoutUrl(), "_self", "");
        }
      });
      append(logoutBtn);


      final Button profilBtn = new Button("Profil");
      profilBtn.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {



          ShowProfil sp = new ShowProfil();
          RootPanel.get("Details").clear();
          RootPanel.get("Menu").clear();
          RootPanel.get("rechts").clear();

          RootPanel.get("Details").add(sp);
        }
      });
      append(profilBtn);


      Button merklisteBtn = new Button("Merkliste");
      // hp.add(merklisteBtn);
      merklisteBtn.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          Merkliste m = new Merkliste();
          RootPanel.get("Details").clear();
          RootPanel.get("rechts").clear();
          RootPanel.get("Menu").clear();
          RootPanel.get("Details").add(m);


        }
      });
      append(merklisteBtn);

      Button sperrlisteBtn = new Button("Sperrliste");
      sperrlisteBtn.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          Sperre s = new Sperre();
          RootPanel.get("Details").clear();
          RootPanel.get("rechts").clear();
          RootPanel.get("Menu").clear();
          RootPanel.get("Details").add(s);
        }

      });
      append(sperrlisteBtn);

      Button suchprofilBtn = new Button("Suchprofil");
      suchprofilBtn.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          Suche s = new Suche();

          RootPanel.get("Details").clear();
          RootPanel.get("rechts").clear();
          RootPanel.get("Menu").clear();
          RootPanel.get("Details").add(s);
        }
      });
      append(suchprofilBtn);

      Button eigenschaftenBtn = new Button("Eigenschaften");
      eigenschaftenBtn.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {

          Eigenschaft e = new Eigenschaft();
          VerticalPanel detailsPanel = new VerticalPanel();
          detailsPanel.add(e);
          RootPanel.get("Details").clear();
          RootPanel.get("rechts").clear();
          RootPanel.get("Menu").clear();
          RootPanel.get("Details").add(detailsPanel);
        }
      });
      append(eigenschaftenBtn);

      Button dataGridBtn = new Button("Alle Profile");
      dataGridBtn.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          ClientsideSettings.getPartnerboerseVerwaltung().getProfilesByAehnlichkeitsmass(user,
              new AsyncCallback<ArrayList<Profil>>() {

                @Override
                public void onSuccess(ArrayList<Profil> result) {
                  DataGridForProfiles dgt = new DataGridForProfiles(result);
                  VerticalPanel detailsPanel = new VerticalPanel();
                  detailsPanel.add(dgt);
                  RootPanel.get("Details").clear();
                  RootPanel.get("rechts").clear();
                  RootPanel.get("Menu").clear();
                  RootPanel.get("Details").add(detailsPanel);

                }

                @Override
                public void onFailure(Throwable caught) {
                  // TODO Auto-generated method stub

                }
              });


        }
      });
      append(dataGridBtn);

      Button reportButton = new Button("Report");
      // hp.add(logoutBtn);
      reportButton.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          // Window.open(user.getLogoutUrl(), "_self", "");
          // Window.confirm("OK = true Cancel = false");
          Window.Location.replace("http://127.0.0.1:8888/Reportgen.html");
        }
      });
      append(reportButton);

      // Anchor logOutLink = new Anchor("Logout");
      // logOutLink.setHref(user.getLogoutUrl());
      // this.append(logOutLink);
    }
  }

}
