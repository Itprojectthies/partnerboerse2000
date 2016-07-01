package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.superteam2000.gwt.client.gui.BoxPanel;
import de.superteam2000.gwt.client.gui.CustomButton;
import de.superteam2000.gwt.client.gui.DateTimeFormat;
import de.superteam2000.gwt.client.gui.EigenschaftListBox;
import de.superteam2000.gwt.client.gui.Notification;
import de.superteam2000.gwt.client.gui.ProfilAttributListbox;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Klasse zur Darstellung des Profils eines eingeloggten Users
 *
 * @author Rathke, Volz
 */
public class ShowProfil extends BasicFrame {

  /*
   * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
   */

  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();


  ProfilAttributListbox gebTag = null;
  ProfilAttributListbox groesse = null;
  FlowPanel alignPanel = new FlowPanel();
  FlowPanel contentPanel = new FlowPanel();

  CustomButton saveButton = new CustomButton();
  CustomButton editButton = new CustomButton();
  CustomButton deleteBtn = new CustomButton();

  BoxPanel clb = null;
  FlowPanel buttonsPanel = new FlowPanel();
  Profil user = ClientsideSettings.getCurrentUser();
  Logger logger = ClientsideSettings.getLogger();

  /**
   * Headline Text zurückgeben
   * 
   * @return Begruessung
   */
  @Override
  public String getHeadlineText() {
    return "Hallo " + user.getVorname() + "!";
  }

  /**
   * SubHeadline Text zurückgeben
   * 
   * @return Text
   */
  @Override
  protected String getSubHeadlineText() {
    return "Hier kannst du deine Profileinstellungen bearbeiten";
  }

  /**
   * Profilbeschreibungsattribute (Vorname, Nachname, ...) werden vom Server abgefragt, damit sie
   * als Textboxen dargestellt werden koennen.
   */
  @Override
  public void run() {
    alignPanel.setStyleName("pure-form pure-form-aligned");
    contentPanel.setStyleName("content");
    // Panels setzen Styles und fuegen Elemente hinzu
    buttonsPanel.setStyleName("pure-controls-group");
    
    //Style fuer Editier-Button setzen
    editButton.setIcon("fa fa-pencil");
    editButton.addClickHandler(new EditButtonClickHandler());

    //Style fuer Speichern-Button setzen
    saveButton.setIcon("fa fa-floppy-o");
    saveButton.addClickHandler(new SaveButtonClickHandler());
    saveButton.setEnabled(false);

    //Style fuer Loeschen-Button setzen 
    deleteBtn.setIcon("fa fa-trash");
    deleteBtn.addClickHandler(new DeleteClickHandler());

    buttonsPanel.add(deleteBtn);
    buttonsPanel.add(editButton);
    buttonsPanel.add(saveButton);

    alignPanel.add(buttonsPanel);
    
    //Moeglichkeit zum setzen von Geburstag geben
    gebTag = new ProfilAttributListbox();
    gebTag.createGebtaListobx("Geburtstag");
    gebTag.setGebtag(user.getGeburtsdatum());
    
    //Moeglichkeit zur Angabe der Koerpergroesse geben
    groesse = new ProfilAttributListbox();
    groesse.createGroesseListBox("Körpergröße");
    groesse.setGroesse(user.getGroesse());

    pbVerwaltung.getAllBeschreibungProfilAttribute(new GetAllBeschreibungProfilAttributeCallback());
    

    contentPanel.add(alignPanel);
    RootPanel.get("main").add(contentPanel);

  }

  /**
   * Alle Attribute aus Datenbank auslesen und anzeigen.
   */
  private class GetAllAuswahlProfilAttributeCallback implements AsyncCallback<ArrayList<Auswahl>> {

    /**
     * Alle Eigenschaften werden ausgelesen und angezeigt. Koerpergroesse und Geburtstags Listboxen
     * werden nach den AuswahlProfilAttributen zum Panel hinzugefuegt.
     */
    @Override
    public void onSuccess(ArrayList<Auswahl> result) {
      for (Auswahl a : result) {
        String boxPanelValue = "";
        //Elemente werden in Panel geschrieben
        switch (a.getName()) {
          case "Religion":
            boxPanelValue = user.getReligion();
            break;
          case "Haarfarbe":
            boxPanelValue = user.getHaarfarbe();
            break;
          case "Geschlecht":
            boxPanelValue = user.getGeschlecht();
            break;
          case "Raucher":
            boxPanelValue = user.getRaucher();
            break;
          default:
            continue;
        }

        clb = new BoxPanel(a, boxPanelValue, true);
        clb.setEnable(false);
        clb.setStyleName("pure-control-group");
        alignPanel.add(clb);
      }

      alignPanel.add(groesse);
      alignPanel.add(gebTag);

    }

    /**
     * Um Fehler abzufangen.
     */
    @Override
    public void onFailure(Throwable caught) {}
  }

  /**
   * Die Listboxen und Textboxen werden ausgelesen und die Inhalte koennen abgeaendert werden.
   * 
   * @author Volz
   *
   */
  private class EditButtonClickHandler implements ClickHandler {
    // Schleifen zum Auslesen der Listboxen und Textboxen, welche in 2
    // Panels verschachtelt sind, um diese bearbeitbar zu machen

    /**
     * Schleifen dienen zum Auslesen der Listboxen und Textboxen, welche in zwei Panels
     * verschachtelt sind, um diese editierbar zu machen.
     */
    @Override
    public void onClick(ClickEvent event) {
      /*
       * Save Button klickbar machen, um Aenderungen zu speichern.
       */
      //Button wird sichtbar gemacht um Aenderungen zu speichern
      saveButton.setEnabled(true);
      //deselektieren, da bereits genutzt
      editButton.setEnabled(false);
      //bei Eigenschaften nicht moeglich
      deleteBtn.setEnabled(false);

      for (Widget child : alignPanel) {
        FlowPanel childPanel = (FlowPanel) child;
        for (Widget box : childPanel) {

          if (box instanceof EigenschaftListBox) {

            EigenschaftListBox lb = (EigenschaftListBox) box;
            /* Listbox auswählbar machen */
            lb.setEnabled(true);
          } else if (box instanceof TextBox) {

            TextBox tb = (TextBox) box;
            /* Textbox beschreibbar machen */
            tb.setEnabled(true);

          }
        }
      }
    }
  }

  /**
   * Wurden die Eigenschaften editiert, werden die Aenderungen gespeichert.
   */
  private class SaveButtonClickHandler implements ClickHandler {

    /**
     * Die Aenderungen werden geprueft und anschliessend abgespeichert wenn moeglich.
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onClick(ClickEvent event) {
      //wird wieder sichtbar gemacht
      editButton.setEnabled(true);
      //wird wieder sichtbar gemacht
      deleteBtn.setEnabled(true);
      
      Profil p = new Profil();

      int geburtsTag = 1;
      int geburtsMonat = 1;
      int geburtsJahr = 1900;

      /*
       * Schleifen zum Auslesen der Listboxen, welche in 2 Panels verschachtelt sind.
       */
      for (Widget child : alignPanel) {
        FlowPanel childPanel = (FlowPanel) child;
        for (Widget box : childPanel) {
          if (box instanceof EigenschaftListBox) {
            EigenschaftListBox lb = (EigenschaftListBox) box;

            switch (lb.getName()) {

              case "Raucher":
                p.setRaucher(lb.getSelectedItemText());
                break;
              case "Haarfarbe":
                p.setHaarfarbe(lb.getSelectedItemText());
                break;
              case "Religion":
                p.setReligion(lb.getSelectedItemText());
                break;
              case "Geschlecht":
                p.setGeschlecht(lb.getSelectedItemText());
                break;
              case "Körpergröße":
                p.setGroesse(Integer.valueOf(lb.getSelectedItemText()));
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

            /* Vorname und Nachname werden auch ausgelesen */
          } else if (box instanceof TextBox) {
            TextBox tb = (TextBox) box;
            switch (tb.getName()) {
              case "Vorname":
                p.setVorname(tb.getText());
                break;
              case "Nachname":
                p.setNachname(tb.getText());
                break;

            }
          }

        }

      }

      /*
       * Date-Objekt aus den 3 Geburtswerten Tag, Monat und Jahr berechnen und in SQL-State- Objekt
       * umwandeln
       */
      Date gebTagDate = DateTimeFormat.getFormat("yyyy-MM-dd")
          .parse(geburtsJahr + "-" + geburtsMonat + "-" + geburtsTag);
      gebTagDate.setHours(20);
      java.sql.Date gebTagMySqlDate = new java.sql.Date(gebTagDate.getTime());

      p.setGeburtsdatum(gebTagMySqlDate);
      p.setEmail(user.getEmail());
      p.setId(user.getId());

      ClientsideSettings.setCurrentUser(p);

      pbVerwaltung.save(p, new SaveProfilCallBack());

    }
  }

  /**
   * Wenn User sein Profil loeschen moechte.
   */
  private class DeleteClickHandler implements ClickHandler {

    /**
     * Abfrage, ob User Profil loeschen will, anschliessend wird geloescht.
     */
    @Override
    public void onClick(ClickEvent event) {
      if (Window.confirm("Möchtest du dein Profil wirklich löschen?")) {
        pbVerwaltung.delete(user, new DeleteProfilCallback());
      }
    }
  }

  private class DeleteProfilCallback implements AsyncCallback<Void> {

    /**
     * Profil wird geloescht, Meldung wird ausgegeben
     */
    @Override
    public void onSuccess(Void result) {
      logger.severe("Profil gelöscht");
      Window.open(user.getLogoutUrl(), "_self", "");
    }

    /**
     * Fehler abfangen und Meldung ausgeben.
     */
    @Override
    public void onFailure(Throwable caught) {
      logger.severe("Fehler beim löschen des Profils");
    }
  }

  /**
   * Um Aenderungen an Profil zu speichern.
   * 
   */
  private class SaveProfilCallBack implements AsyncCallback<Void> {

    /**
     * Aenderungen des Profils speichern und danach erneut anzeigen.
     */
    @Override
    public void onSuccess(Void result) {
      new Notification("Profiländerung gespeichert", "success");
      logger.severe("Ändern der Profildaten hat funktioniert");

      ShowProfil sp = new ShowProfil();

      RootPanel.get("main").clear();
      RootPanel.get("main").add(sp);
    }

    /**
     * Falls Fehler passieren, abfangen und Meldung ausgeben.
     */
    @Override
    public void onFailure(Throwable caught) {
      logger.severe("Ändern der Profildaten hat nicht funktioniert");
    }

  }

  /**
   * Alle Beschreibungselemente werden ausgelesen und angezeigt.
   *
   */
  private class GetAllBeschreibungProfilAttributeCallback
      implements AsyncCallback<ArrayList<Beschreibung>> {

    /**
     * Fuer die Beschreibung Vorname und Nachname wird eine Textbox erstellt und mit den Werten des
     * aktuellen Nutzers befuellt.
     */
    @Override
    public void onSuccess(ArrayList<Beschreibung> result) {
      for (Beschreibung b : result) {

        switch (b.getName()) {
          case "Vorname":
            clb = new BoxPanel(b, user.getVorname(), true);
            clb.setEnable(false);
            clb.setStyleName("pure-control-group");
            alignPanel.add(clb);
            break;
          case "Nachname":
            clb = new BoxPanel(b, user.getNachname(), true);
            clb.setEnable(false);
            clb.setStyleName("pure-control-group");
            alignPanel.add(clb);
            break;

        }
      }
      //Auswahlattribute des Profils werden erst nachdem Vorname und Nachname geladen worden sind
      //abgefragt, um die Reihenfolge der Darstellung immer gelich zu haben.
      pbVerwaltung.getAllAuswahlProfilAttribute(new GetAllAuswahlProfilAttributeCallback());
    }

    /**
     * Fehler abfangen und Meldung ausgeben.
     */
    @Override
    public void onFailure(Throwable caught) {
      logger.severe("Erstellen der Beschreibungstextboxen (z.B. Vorname) fehlgeschlagen!");
    }
  }
}
