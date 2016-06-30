package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
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
   * Headline Text setzen
   * @return Begruessung
   */
  @Override
  public String getHeadlineText() {
    return "Hallo " + user.getVorname() + "!";
  }

  /**
   * SubHeadline Text setzen
   * @return Text
   */
  @Override
  protected String getSubHeadlineText() {
    return "Hier kannst du deine Profileinstellungen bearbeiten";
  }

  /**
   * Profilbeschreibungsattribute (Vorname, Nachname, ...) werden vom Server abgefragt, damit sie als Textboxen
   * dargestellt werden koennen.
   * 
   * @param legend 
   * @param alignPanel 
   * @param contentPanel
   * @param buttonsPanel
   * 					Panels setzen Styles und fuegen Elemente hinzu
   * @param editButton Style fuer Button zum editieren
   * @param saveButton Style fuer speichern Button setzen
   * @param deleteBtn Style fuer loeschen Button setzen
   * 					mit Buttons koennen Eigenschaften und Infoobjekte verwaltet werden
   * @param buttonsPanel zeigt Buttons an
   * @param gebTag Moeglichkeit zum setzen von Geburstag geben
   * @param groesse Moeglichkeit zur Angabe der Koerpergroesse geben
   */
  @Override
  public void run() {
    alignPanel.setStyleName("pure-form pure-form-aligned");
    contentPanel.setStyleName("content");
    buttonsPanel.setStyleName("pure-controls-group");

    editButton.setIcon("fa fa-pencil");
    editButton.addClickHandler(new EditButtonClickHandler());

    saveButton.setIcon("fa fa-floppy-o");
    saveButton.addClickHandler(new SaveButtonClickHandler());
    saveButton.setEnabled(false);


    deleteBtn.setIcon("fa fa-trash");
    deleteBtn.addClickHandler(new DeleteClickHandler());

    buttonsPanel.add(deleteBtn);
    buttonsPanel.add(editButton);
    buttonsPanel.add(saveButton);

    alignPanel.add(buttonsPanel);

    gebTag = new ProfilAttributListbox();
    gebTag.createGebtaListobx("Geburtstag");
    gebTag.setGebtag(user.getGeburtsdatum());

    groesse = new ProfilAttributListbox();
    groesse.createGroesseListBox("Körpergröße");
    groesse.setGroesse(user.getGroesse());

    pbVerwaltung.getAllBeschreibungProfilAttribute(new GetAllBeschreibungProfilAttributeCallback());
    pbVerwaltung.getAllAuswahlProfilAttribute(new GetAllAuswahlProfilAttributeCallback());

    contentPanel.add(alignPanel);
    RootPanel.get("main").add(contentPanel);

  }

  /**
   * Alle Attribute aus Datenbank auslesen und anzeigen.
   * 
   * @author Volz
   *
   */
  private class GetAllAuswahlProfilAttributeCallback implements AsyncCallback<ArrayList<Auswahl>> {

	  /**
	   * Alle Eigenschaften werden ausgelesen und angezeigt.
	   * Koerpergroesse und Geburtstags Listboxen werden nach den AuswahlProfilAttributen
	   * zum Panel hinzugefuegt.
	   * @param boxPanelValue Elemente werden in Panel geschrieben
	   */
    @Override
    public void onSuccess(ArrayList<Auswahl> result) {
      for (Auswahl a : result) {
        String boxPanelValue = "";
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
	   * Schleifen dienen zum Auslesen der Listboxen und Textboxen, welche in zwei
	   * Panels verschachtelt sind, um diese editierbar zu machen.
	   * 
	   * @param saveButton wird sichtbar gemacht um Aenderungen zu speichern
	   * @param editButton deselektieren, da bereits genutzt
	   * @param deleteBtn bei Eigenschaften nicht moeglich
	   */
    @Override
    public void onClick(ClickEvent event) {
      /*
       *  Save Button klickbar machen, um Aenderungen zu speichern.
       */
      saveButton.setEnabled(true);
      editButton.setEnabled(false);
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
   * Wurden die Eigenschaften editiert, wird geprueft ob die Aenderungen in Ordnung sind, danach
   * werden die Aenderungen gespeichert.
   * 
   * @author Volz
   *
   */
  private class SaveButtonClickHandler implements ClickHandler {

	  /**
	   * Die Aenderungen werden geprueft und anschliessend abgespeichert wenn moeglich.
	   * 
	   * @param editButton wird wieder sichtbar gemacht
	   * @param deleteBtn wird wieder sichtbar gemacht
	   * @param p aktuelles Profil
	   * @param geburtsTag Hilfsvariable fuer Alter
	   * @param geburtsMonat Hilfsvariable fuer Alter
	   * @param geburtsJahr Hilfsvariable fuer Alter
	   */
    @SuppressWarnings("deprecation")
    @Override
    public void onClick(ClickEvent event) {
      editButton.setEnabled(true);
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

            /* Vorname und Nachname werden auch ausgelesen*/
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
       * Date-Objekt aus den 3 Geburtswerten Tag, Monat und Jahr berechnen und in SQL-State-
       * Objekt umwandeln
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
   * 
   * @author Volz
   *
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

  /**
   * 
   * @author Volz
   *
   */
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
   * @author Volz
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
   * @author Volz
   *
   */
  private class GetAllBeschreibungProfilAttributeCallback
      implements AsyncCallback<ArrayList<Beschreibung>> {

	  /**
	   * Fuer die Beschreibung Vorname und Nachname wird eine Textbox erstellt
	   * und mit den Werten des aktuellen Nutzers befuellt.
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
