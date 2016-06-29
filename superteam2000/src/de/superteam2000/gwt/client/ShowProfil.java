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

  @Override
  public String getHeadlineText() {
    return "Hallo " + user.getVorname() + "!";
  }

  @Override
  protected String getSubHeadlineText() {
    return "Hier kannst du deine Profileinstellungen bearbeiten";
  }

  @Override
  public void run() {
    alignPanel.setStyleName("pure-form pure-form-aligned");
    contentPanel.setStyleName("content");
    buttonsPanel.setStyleName("pure-controls-group");

    HTML legend = new HTML();
    legend.setHTML("<legend></legend>");


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
    buttonsPanel.add(legend);

    alignPanel.add(buttonsPanel);

    gebTag = new ProfilAttributListbox();
    gebTag.createGebtaListobx("Geburtstag");
    gebTag.setGebtag(user.getGeburtsdatum());

    groesse = new ProfilAttributListbox();
    groesse.createGroesseListBox("Körpergröße");
    groesse.setGroesse(user.getGroesse());

    // Profilbeschreibungsattribute (Vorname, Nachname) werden vom Server
    // abgefragt, damit sie als Textboxen
    // dargestellt werden können

    pbVerwaltung.getAllBeschreibungProfilAttribute(new GetAllBeschreibungProfilAttributeCallback());
    pbVerwaltung.getAllAuswahlProfilAttribute(new GetAllAuswahlProfilAttributeCallback());



    contentPanel.add(alignPanel);
    RootPanel.get("main").add(contentPanel);

  }

  private class GetAllAuswahlProfilAttributeCallback implements AsyncCallback<ArrayList<Auswahl>> {

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

      // Körpergröße und Geburtstags Listboxen werden nach den
      // AuswahlProfilAttributen zum Panel hinzugefügt
      alignPanel.add(groesse);
      alignPanel.add(gebTag);

    }

    @Override
    public void onFailure(Throwable caught) {}
  }

  private class EditButtonClickHandler implements ClickHandler {
    // Schleifen zum Auslesen der Listboxen und Textboxen, welche in 2
    // Panels verschachtelt sind, um diese bearbeitbar zu machen

    @Override
    public void onClick(ClickEvent event) {
      // Save Button klickbar machen
      saveButton.setEnabled(true);
      editButton.setEnabled(false);
      deleteBtn.setEnabled(false);
      
      for (Widget child : alignPanel) {
        FlowPanel childPanel = (FlowPanel) child;
        for (Widget box : childPanel) {
          
          if (box instanceof EigenschaftListBox) {
            
            EigenschaftListBox lb = (EigenschaftListBox) box;
            // Listbox auswählbar machen
            lb.setEnabled(true);
          } else if (box instanceof TextBox) {
            
            TextBox tb = (TextBox) box;
            // Textbox beschreibbar machen
            tb.setEnabled(true);
            
          }
        }
      }
    }
  }

  private class SaveButtonClickHandler implements ClickHandler {

    @SuppressWarnings("deprecation")
    @Override
    public void onClick(ClickEvent event) {
      editButton.setEnabled(true);
      deleteBtn.setEnabled(true);

      Profil p = new Profil();

      int geburtsTag = 1;
      int geburtsMonat = 1;
      int geburtsJahr = 1900;

      // Schleifen zum Auslesen der Listboxen, welche in 2 Panels
      // verschachtelt sind

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

      // Date-Objekt aus den 3 Geburtstagswerten Tag, Monat und Jahr
      // konstruieren und in
      // ein SQL-Date-Objekt umwandeln

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


  private class DeleteClickHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {
      if (Window.confirm("Möchtest du dein Profil wirklich löschen?")) {
        pbVerwaltung.delete(user, new DeleteProfilCallback());
      }
    }
  }

  private class DeleteProfilCallback implements AsyncCallback<Void> {
    @Override
    public void onSuccess(Void result) {
      logger.severe("Profil gelöscht");
      Window.open(user.getLogoutUrl(), "_self", "");
    }

    @Override
    public void onFailure(Throwable caught) {
      logger.severe("Fehler beim löschen des Profils");
    }
  }


  private class SaveProfilCallBack implements AsyncCallback<Void> {

    @Override
    public void onSuccess(Void result) {
      new Notification("Profiländerung gespeichert", "success");
      logger.severe("Ändern der Profildaten hat funktioniert");

      ShowProfil sp = new ShowProfil();

      RootPanel.get("main").clear();
      RootPanel.get("main").add(sp);
    }

    @Override
    public void onFailure(Throwable caught) {
      logger.severe("Ändern der Profildaten hat nicht funktioniert");
    }

  }



  private class GetAllBeschreibungProfilAttributeCallback
      implements AsyncCallback<ArrayList<Beschreibung>> {

    @Override
    public void onSuccess(ArrayList<Beschreibung> result) {
      for (Beschreibung b : result) {
        // Für die Beschreibung Vorname und Nachname wird eine
        // Textbox erstellt und mit den Werten des aktuellen Nutzers
        // befüllt

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

    @Override
    public void onFailure(Throwable caught) {
      logger.severe("Erstellen der Beschreibungstextboxen (z.B. Vorname) fehlgeschlagen!");
    }
  }



}
