package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.superteam2000.gwt.client.gui.BoxPanel;
import de.superteam2000.gwt.client.gui.EigenschaftListBox;
import de.superteam2000.gwt.client.gui.Notification;
import de.superteam2000.gwt.client.gui.ProfilAttributListbox;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Formular zum Erstellen eines Nutzers
 *
 * @author Christian Rathke, Volz Daniel
 */
public class CreateProfil extends BasicFrame {

  /*
   * Alle notwendigen Instanzvariablen werden deklariert
   */

  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

  Profil user = ClientsideSettings.getCurrentUser();
  Logger logger = ClientsideSettings.getLogger();

  FlowPanel contentPanel = new FlowPanel();
  FlowPanel alignPanel = new FlowPanel();

  ProfilAttributListbox gebTag = null;
  ProfilAttributListbox groesse = null;

  Button confirmBtn = null;

  @Override
  public String getHeadlineText() {
    return "Profil erstellen";
  }

  @Override
  protected String getSubHeadlineText() {
    // TODO Auto-generated method stub
    return "Erstelle dein Profil und lege gleich los!";
  }

  @Override
  public void run() {
    contentPanel.setStyleName("content");
    alignPanel.setStyleName("pure-form pure-form-aligned");

    gebTag = new ProfilAttributListbox();
    gebTag.createGebtaListobx("Was ist dein Geburtstag");
    gebTag.setEnable(true);

    groesse = new ProfilAttributListbox();
    groesse.createGroesseListBox("Was ist deine Körpergröße");
    groesse.setEnable(true);

    confirmBtn = new Button("Weiter");
    confirmBtn.setStyleName("pure-button pure-button-primary");

    confirmBtn.addClickHandler(new ConfirmClickHandler());

    pbVerwaltung.getAllBeschreibungProfilAttribute(new GetAllBeschreibungProfilAttributeCallBack());
    pbVerwaltung.getAllAuswahlProfilAttribute(new GetAllAuswahlProfilAttributeCallBack());

    contentPanel.add(alignPanel);

    RootPanel.get("main").add(contentPanel);

  }

  private class GetAllBeschreibungProfilAttributeCallBack
      implements AsyncCallback<ArrayList<Beschreibung>> {

    @Override
    public void onSuccess(ArrayList<Beschreibung> result) {
      for (Beschreibung b : result) {

        BoxPanel clb = new BoxPanel(b, false);
        alignPanel.add(clb);
      }
    }

    @Override
    public void onFailure(Throwable caught) {}
  }

  private class GetAllAuswahlProfilAttributeCallBack implements AsyncCallback<ArrayList<Auswahl>> {

    @Override
    public void onSuccess(ArrayList<Auswahl> result) {
      for (Auswahl a : result) {
        BoxPanel clb = new BoxPanel(a, false);
        alignPanel.add(clb);
      }

      alignPanel.add(groesse);
      alignPanel.add(gebTag);
      alignPanel.add(confirmBtn);
    }

    @Override
    public void onFailure(Throwable caught) {}
  }

  private class ConfirmClickHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {
      createProfil();

    }
  }

  class CreateCustomerCallback implements AsyncCallback<Profil> {

    @Override
    public void onFailure(Throwable caught) {
      logger.severe("Erstellen des useres hat nicht funktioniert");

    }

    @Override
    public void onSuccess(Profil p) {
      p.setLogoutUrl(user.getLogoutUrl());
      p.setLoggedIn(true);
      
      ClientsideSettings.setCurrentUser(p);
      
      ShowProfil sp = new ShowProfil();
      Navbar nb = new Navbar();
      RootPanel.get("main").clear();
      RootPanel.get("menu").add(nb);
      RootPanel.get("main").add(sp);
    }

  }



  private void createProfil() {
    String firstName = "";
    String lastName = "";

    String haarfarbe = "";
    String raucher = "";
    String religion = "";
    String geschlecht = "";
    String email = user.getEmail();

    int groesse = 141;

    int geburtsTag = 2;
    int geburtsMonat = 2;
    int geburtsJahr = 1901;

    // Schleifen zum Auslesen der Listboxen, welche in 2 Panels
    // verschachtelt sind

    for (Widget child : alignPanel) {
      if (child instanceof EigenschaftListBox) {
        EigenschaftListBox lb = (EigenschaftListBox) child;
        logger.severe("test " + lb.getName());

        switch (lb.getName()) {

          case "Raucher":
            raucher = lb.getSelectedItemText();
            break;
          case "Haarfarbe":
            haarfarbe = lb.getSelectedItemText();
            break;
          case "Religion":
            religion = lb.getSelectedItemText();
            break;
          case "Geschlecht":
            geschlecht = lb.getSelectedItemText();
            break;
          case "Körpergröße":
            groesse = Integer.valueOf(lb.getSelectedItemText());
            break;
          case "GeburtstagTag":
            geburtsTag = Integer.valueOf(lb.getSelectedItemText());
            break;
          case "GeburtstagMonat":
            geburtsMonat = Integer.valueOf(lb.getSelectedItemText());
            break;
          case "GeburtstagJahr":
            geburtsJahr = Integer.valueOf(lb.getSelectedItemText());
            break;

        }

      } else if (child instanceof TextBox) {
        TextBox tb = (TextBox) child;
        logger.severe("test " + tb.getName());
        switch (tb.getName()) {
          case "Vorname":
            firstName = tb.getText();
            break;
          case "Nachname":
            lastName = tb.getText();
            break;

        }
      }


    }

    // Date-Objekt aus den 3 Geburtstagswerten Tag, Monat und Jahr
    // konstruieren und in
    // ein SQL-Date-Objekt umwandeln

    Date gebTag2 = DateTimeFormat.getFormat("yyyy-MM-dd")
        .parse(geburtsJahr + "-" + geburtsMonat + "-" + geburtsTag);
    java.sql.Date gebTag = new java.sql.Date(gebTag2.getTime());

    if (!firstName.isEmpty() && !lastName.isEmpty()) {
      pbVerwaltung.createProfil(lastName, firstName, email, gebTag, haarfarbe, raucher, religion,
          groesse, geschlecht, new CreateCustomerCallback());
    } else {
      new Notification("Bitte füllen sie alle Felder aus", "warning");
    }
  }

}
