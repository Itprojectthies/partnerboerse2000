package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.superteam2000.gwt.client.gui.Label;
import de.superteam2000.gwt.client.gui.ListItemWidget;
import de.superteam2000.gwt.client.gui.UnorderedListWidget;
import de.superteam2000.gwt.shared.bo.Profil;

public class Navbar extends VerticalPanel {


  Profil profil = ClientsideSettings.getCurrentUser();


  @Override
  protected void onLoad() {
    RootPanel.get("menu").getElement().getStyle().setBackgroundColor("#191818");

    if ((profil != null) && profil.isLoggedIn()) {

      FlowPanel menu = new FlowPanel();
      UnorderedListWidget menuList = new UnorderedListWidget();
      FlowPanel pureMenu = new FlowPanel();
      Anchor anchor = new Anchor("PartnerBörse", GWT.getHostPageBaseURL() + "Superteam2000.html");


      menuList.setStyleName("pure-menu-list");
      anchor.setStyleName("pure-menu-heading");
      pureMenu.setStyleName("pure-menu");

      Anchor logoutBtn = new Anchor("Logout");
      Label profilBtn = new Label();
      Anchor merklisteBtn = new Anchor("Merkliste");
      Anchor sperrlisteBtn = new Anchor("Sperrliste");
      Anchor suchprofilBtn = new Anchor("Suchprofil");
      Anchor eigenschaftenBtn = new Anchor("Eigenschaften");
      Anchor dataGridBtn = new Anchor("Alle Profile");
      Anchor reportButton = new Anchor("Report");
      // Anchor edit = new Anchor("edit");
      // edit.setStyleName("pure-menu-link");

      FlowPanel test = new FlowPanel(DivElement.TAG);
      menuList.add(test);

      profilBtn.setStyleName("pure-menu-link");
      menuList.add(new ListItemWidget(profilBtn));

      eigenschaftenBtn.setStyleName("pure-menu-link");
      menuList.add(new ListItemWidget(eigenschaftenBtn));

      merklisteBtn.setStyleName("pure-menu-link");
      menuList.add(new ListItemWidget(merklisteBtn));

      sperrlisteBtn.setStyleName("pure-menu-link");
      menuList.add(new ListItemWidget(sperrlisteBtn));

      suchprofilBtn.setStyleName("pure-menu-link");
      menuList.add(new ListItemWidget(suchprofilBtn));

      dataGridBtn.setStyleName("pure-menu-link");
      menuList.add(new ListItemWidget(dataGridBtn));

      reportButton.setStyleName("pure-menu-link");
      menuList.add(new ListItemWidget(reportButton));

      logoutBtn.setStyleName("pure-menu-link");
      menuList.add(new ListItemWidget(logoutBtn));

      // Label profilLabel = new Label();
      profilBtn.setText("Profil");
      profilBtn.setFor("accordion-button");
      profilBtn.setStyleName("pure-menu-link accordion-label");

      // final SimpleCheckBox profilCheck = new SimpleCheckBox();
      // profilCheck.setStyleName("accordion-button");
      // profilCheck.getElement().setAttribute("id", "accordion-button");

      // edit.getElement().setAttribute("id", "1");

      FlowPanel contentDiv = new FlowPanel(DivElement.TAG);
      contentDiv.setStyleName("subLink");

      test.setStyleName("pure-menu-item");

      test.add(profilBtn);
      // test.add(profilCheck);

      // contentDiv.add(edit);

      test.add(contentDiv);


      pureMenu.add(anchor);
      pureMenu.add(menuList);
      menu.add(pureMenu);


      RootPanel.get("menu").add(menu);


      logoutBtn.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          Window.open(profil.getLogoutUrl(), "_self", "");
        }
      });



      profilBtn.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          ShowProfil sp = new ShowProfil();
          RootPanel.get("main").clear();
          RootPanel.get("main").add(sp);
        }
      });



      merklisteBtn.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          Merkliste m = new Merkliste();
          RootPanel.get("main").clear();
          RootPanel.get("main").add(m);


        }
      });


      sperrlisteBtn.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          Sperre s = new Sperre();
          RootPanel.get("main").clear();
          RootPanel.get("main").add(s);
        }

      });


      suchprofilBtn.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          Suche s = new Suche();

          RootPanel.get("main").clear();
          RootPanel.get("main").add(s);
        }
      });


      eigenschaftenBtn.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {

          Eigenschaft e = new Eigenschaft();
          RootPanel.get("main").clear();
          RootPanel.get("main").add(e);
        }
      });


      dataGridBtn.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          ClientsideSettings.getPartnerboerseVerwaltung().getProfilesByAehnlichkeitsmass(profil,
              new AsyncCallback<ArrayList<Profil>>() {

                @Override
                public void onSuccess(ArrayList<Profil> result) {
                  AllProfilesTable dgt = new AllProfilesTable(result);
                  RootPanel.get("main").clear();
                  RootPanel.get("main").add(dgt);

                }

                @Override
                public void onFailure(Throwable caught) {
                  // TODO Auto-generated method stub

                }
              });


        }
      });


      reportButton.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          Window.Location.replace(GWT.getHostPageBaseURL() + "ReportGen.html");
        }
      });

    }
  }



}
