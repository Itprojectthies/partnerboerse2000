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
 * Formular für die Darstellung des selektierten Kunden
 *
 * @author Christian Rathke
 */
public class CreateProfil extends BasicFrame {

  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

  /*
   * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
   */

  Profil user = ClientsideSettings.getCurrentUser();
  Logger logger = ClientsideSettings.getLogger();

  FlowPanel fPanel = new FlowPanel();
  FlowPanel fPanel2 = new FlowPanel();
  ProfilAttributListbox gebTag = null;
  ProfilAttributListbox groesse = null;
  Button confirmBtn = null;

  @Override
  public String getHeadlineText() {
    return "Profil erstellen";
  }

  /*
   * Im Konstruktor werden die anderen Widgets erzeugt. Alle werden in einem Raster angeordnet,
   * dessen Größe sich aus dem Platzbedarf der enthaltenen Widgets bestimmt.
   */

  @Override
  public void run() {
    this.fPanel.setStyleName("pure-form pure-form-aligned");
    this.fPanel2.setStyleName("content");
    this.gebTag = new ProfilAttributListbox();
    this.gebTag.createGebtaListobx("Was ist dein Geburtstag");
    this.gebTag.setEnable(true);

    this.groesse = new ProfilAttributListbox();
    this.groesse.createGroesseListBox("Was ist deine Körpergröße");
    this.groesse.setEnable(true);

    this.pbVerwaltung
        .getAllBeschreibungProfilAttribute(new GetAllBeschreibungProfilAttributeCallBack());
    this.pbVerwaltung.getAllAuswahlProfilAttribute(new GetAllAuswahlProfilAttributeCallBack());


    this.confirmBtn = new Button("Weiter");
    this.confirmBtn.setStyleName("pure-button pure-button-primary");

    this.confirmBtn.addClickHandler(new ConfirmClickHandler());
    this.fPanel2.add(this.fPanel);
    RootPanel.get("main").add(this.fPanel2);

  }

  private class GetAllBeschreibungProfilAttributeCallBack
      implements AsyncCallback<ArrayList<Beschreibung>> {

    @Override
    public void onSuccess(ArrayList<Beschreibung> result) {
      for (Beschreibung b : result) {

        BoxPanel clb = new BoxPanel(b, false);
        CreateProfil.this.fPanel.add(clb);
      }

    }

    @Override
    public void onFailure(Throwable caught) {

    }
  }

  private class GetAllAuswahlProfilAttributeCallBack implements AsyncCallback<ArrayList<Auswahl>> {

    @Override
    public void onSuccess(ArrayList<Auswahl> result) {
      for (Auswahl a : result) {

        BoxPanel clb = new BoxPanel(a, false);
        CreateProfil.this.fPanel.add(clb);
      }

      CreateProfil.this.fPanel.add(CreateProfil.this.groesse);
      CreateProfil.this.fPanel.add(CreateProfil.this.gebTag);
      CreateProfil.this.fPanel.add(CreateProfil.this.confirmBtn);
    }

    @Override
    public void onFailure(Throwable caught) {

    }
  }

  private class ConfirmClickHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {

      String firstName = "";
      String lastName = "";

      String haarfarbe = "";
      String raucher = "";
      String religion = "";
      String geschlecht = "";
      String email = CreateProfil.this.user.getEmail();

      int groesse = 141;

      int geburtsTag = 2;
      int geburtsMonat = 2;
      int geburtsJahr = 1901;

      // Schleifen zum Auslesen der Listboxen, welche in 2 Panels
      // verschachtelt sind

      for (Widget child : CreateProfil.this.fPanel) {
        if (child instanceof FlowPanel) {
          FlowPanel childPanel = (FlowPanel) child;
          for (Widget box : childPanel) {
            if (box instanceof EigenschaftListBox) {
              EigenschaftListBox lb = (EigenschaftListBox) box;
              CreateProfil.this.logger.severe("test " + lb.getName());

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

            } else if (box instanceof TextBox) {
              TextBox tb = (TextBox) box;
              CreateProfil.this.logger.severe("test " + tb.getName());
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
        }

      }

      // Date-Objekt aus den 3 Geburtstagswerten Tag, Monat und Jahr
      // konstruieren und in
      // ein SQL-Date-Objekt umwandeln

      Date gebTag2 = DateTimeFormat.getFormat("yyyy-MM-dd")
          .parse(geburtsJahr + "-" + geburtsMonat + "-" + geburtsTag);
      java.sql.Date gebTag = new java.sql.Date(gebTag2.getTime());

      if (!firstName.isEmpty() && !lastName.isEmpty()) {
        CreateProfil.this.pbVerwaltung.createProfil(lastName, firstName, email, gebTag, haarfarbe,
            raucher, religion, groesse, geschlecht, new CreateCustomerCallback());
      } else {
        new Notification("Bitte füllen sie alle Felder aus", "warning");
      }

    }
  }

  class CreateCustomerCallback implements AsyncCallback<Profil> {

    @Override
    public void onFailure(Throwable caught) {
      CreateProfil.this.logger.severe("Erstellen des useres hat nicht funktioniert");

    }

    @Override
    public void onSuccess(Profil p) {
      p.setLogoutUrl(CreateProfil.this.user.getLogoutUrl());
      ClientsideSettings.setCurrentUser(p);
      p.setLoggedIn(true);
      ShowProfil sp = new ShowProfil();
      Navbar nb = new Navbar();
      RootPanel.get("main").clear();
      RootPanel.get("menu").add(nb);
      RootPanel.get("main").add(sp);
    }

  }

  @Override
  protected String getSubHeadlineText() {
    // TODO Auto-generated method stub
    return "Erstelle dein Profil und lege gleich los!";
  }

}
