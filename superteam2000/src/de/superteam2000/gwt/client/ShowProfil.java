package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
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
 * Formular für die Darstellung des selektierten Kunden
 * 
 * @author Rathke, Volz
 */
public class ShowProfil extends BasicFrame {

  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

  /*
   * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
   */

  ProfilAttributListbox gebTag = null;
  ProfilAttributListbox groesse = null;
  FlowPanel fPanel = new FlowPanel();
  FlowPanel fPanel2 = new FlowPanel();
  
  CustomButton saveButton = new CustomButton();
  CustomButton editButton = new CustomButton();
  CustomButton deleteBtn = new CustomButton();

  BoxPanel clb = null;
  FlowPanel buttonsPanel = new FlowPanel();
  Profil currentProfil = ClientsideSettings.getCurrentUser();
  Logger logger = ClientsideSettings.getLogger();

  @Override
  public String getHeadlineText() {
    return "Hallo " + currentProfil.getVorname() +"!";
  }
  
  @Override
  protected String getSubHeadlineText() {
    return "Hier kannst du deine Profileinstellungen bearbeiten";
  }
  
  @Override
  public void run() {
    fPanel.setStyleName("pure-form pure-form-aligned");
    fPanel2.setStyleName("content");
    buttonsPanel.setStyleName("pure-controls-group");

    HTML legend = new HTML();
//    String aenhnlickeit = "";
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
    
    fPanel.add(buttonsPanel);

    gebTag = new ProfilAttributListbox();
    gebTag.createGebtaListobx();
    gebTag.setGebtag(currentProfil.getGeburtsdatum());
    
    groesse = new ProfilAttributListbox();
    groesse.createGroesseListBox();
    groesse.setGroesse(currentProfil.getGroesse());

    // Profilbeschreibungsattribute (Vorname, Nachname) werden vom Server
    // abgefragt, damit sie als Textboxen
    // dargestellt werden können

    pbVerwaltung.getAllBeschreibungProfilAttribute(new GetAllBeschreibungProfilAttributeCallback());
    pbVerwaltung.getAllAuswahlProfilAttribute(new GetAllAuswahlProfilAttributeCallback());

    

    fPanel2.add(fPanel);
    RootPanel.get("main").add(fPanel2);

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
      for (Widget child : fPanel) {
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

      for (Widget child : fPanel) {
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
      p.setEmail(currentProfil.getEmail());
      p.setId(currentProfil.getId());

      ClientsideSettings.setCurrentUser(p);

      pbVerwaltung.save(p, new SaveProfilCallBack());

    }
  }


  private class DeleteClickHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {

      if (Window.confirm("Möchtest du dein Profil wirklich löschen?")) {
        pbVerwaltung.delete(currentProfil, new AsyncCallback<Void>() {

          @Override
          public void onSuccess(Void result) {
            logger.severe("Profil gelöscht");

            VerticalPanel detailsPanel = new VerticalPanel();
            RootPanel.get("main").clear();
            detailsPanel.add(new HTML("Profil erflogreich gelöscht"));
            RootPanel.get("main").add(detailsPanel);
          }

          @Override
          public void onFailure(Throwable caught) {
            logger.severe("Fehler beim löschen des Profils");
          }
        });
      }
    }
  }

  private class SaveProfilCallBack implements AsyncCallback<Void> {

    @Override
    public void onSuccess(Void result) {
      Notification n1 = new Notification("Profiländerung gespeichert", "success");
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

  private class GetAllAuswahlProfilAttributeCallback implements AsyncCallback<ArrayList<Auswahl>> {
    
    @Override
    public void onSuccess(ArrayList<Auswahl> result) {
      for (Auswahl a : result) {
        switch (a.getName()) {
          case "Religion":
            clb = new BoxPanel(a, currentProfil.getReligion(), true);
            clb.setEnable(false);
            clb.setStyleName("pure-control-group");
            fPanel.add(clb);
            break;
          case "Haarfarbe":
            clb = new BoxPanel(a, currentProfil.getHaarfarbe(), true);
            clb.setEnable(false);
            clb.setStyleName("pure-control-group");
            fPanel.add(clb);
            break;
          case "Geschlecht":
            clb = new BoxPanel(a, currentProfil.getGeschlecht(), true);
            clb.setEnable(false);
            clb.setStyleName("pure-control-group");
            fPanel.add(clb);
            break;
          case "Raucher":
            clb = new BoxPanel(a, currentProfil.getRaucher(), true);
            clb.setEnable(false);
            clb.setStyleName("pure-control-group");
            fPanel.add(clb);
            break;

          default:
            break;
        }
      }

      // Körpergröße und Geburtstags Listboxen werden nach den
      // AuswahlProfilAttributen zum Panel hinzugefügt
      fPanel.add(groesse);
      fPanel.add(gebTag);
      
    }

    @Override
    public void onFailure(Throwable caught) {
      // TODO Auto-generated method stub

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
            clb = new BoxPanel(b, currentProfil.getVorname(), true);
            clb.setEnable(false);
            clb.setStyleName("pure-control-group");
            fPanel.add(clb);
            break;
          case "Nachname":
            clb = new BoxPanel(b, currentProfil.getNachname(), true);
            clb.setEnable(false);
            clb.setStyleName("pure-control-group");
            fPanel.add(clb);
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
