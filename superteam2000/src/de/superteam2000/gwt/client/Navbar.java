package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.superteam2000.gwt.client.gui.Label;
import de.superteam2000.gwt.client.gui.ListItemWidget;
import de.superteam2000.gwt.client.gui.UnorderedListWidget;
import de.superteam2000.gwt.shared.bo.Profil;

public class Navbar extends BasicFrame {

  
  Profil user = ClientsideSettings.getCurrentUser();

  public void append(Widget wi) {
    this.add(wi);
  }

  @Override
  public void run() {

    if (user != null && user.isLoggedIn()) {

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

//      Label profilLabel = new Label();
      profilBtn.setText("Profil");
      profilBtn.setFor("accordion-button");
      profilBtn.setStyleName("pure-menu-link accordion-label");
      
      SimpleCheckBox profilCheck = new SimpleCheckBox();
      profilCheck.setStyleName("accordion-button");
      profilCheck.getElement().setAttribute("id", "accordion-button");
      
      FlowPanel contentDiv = new FlowPanel(DivElement.TAG);
      contentDiv.setStyleName("content");
      FlowPanel test = new FlowPanel(DivElement.TAG);
      
      test.setStyleName("pure-menu-item");
      
      test.add(profilBtn);
      test.add(profilCheck);
      
      contentDiv.add(suchprofilBtn);
      
      test.add(contentDiv);
      
      menuList.add(test);
      pureMenu.add(anchor);
      pureMenu.add(menuList);
      menu.add(pureMenu);
      
      
      RootPanel.get("menu").add(menu);


      logoutBtn.addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          Window.open(user.getLogoutUrl(), "_self", "");
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
          VerticalPanel detailsPanel = new VerticalPanel();
          detailsPanel.add(e);
          RootPanel.get("main").clear();
          RootPanel.get("main").add(detailsPanel);
        }
      });


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
                  RootPanel.get("main").clear();
                  RootPanel.get("main").add(detailsPanel);

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
          // Window.open(user.getLogoutUrl(), "_self", "");
          // Window.confirm("OK = true Cancel = false");
          Window.Location.replace("http://127.0.0.1:8888/Reportgen.html");
        }
      });

      // Anchor logOutLink = new Anchor("Logout");
      // logOutLink.setHref(user.getLogoutUrl());
      // this.append(logOutLink);
    }
  }


  @Override
  protected String getHeadlineText() {
    // TODO Auto-generated method stub
    return "hallo";
  }

}
