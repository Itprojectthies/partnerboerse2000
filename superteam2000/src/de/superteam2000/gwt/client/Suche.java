
package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import de.superteam2000.gwt.client.gui.BoxPanel;
import de.superteam2000.gwt.client.gui.CustomPopupPanel;
import de.superteam2000.gwt.client.gui.DataGridProfiles;
import de.superteam2000.gwt.client.gui.Notification;
import de.superteam2000.gwt.client.gui.ProfilAttributListbox;
import de.superteam2000.gwt.client.gui.ProfilAttributTextBox;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.bo.Suchprofil;

/**
 * Die Klasse Suche ist für die Darstellung von möglichen Auswahlen und eine anschließende Suche
 * anhand dieser Kriterien verantwortlich.
 *
 * @author Funke, Volz
 *
 */
public class Suche extends BasicFrame {

  /* alle benoetigten Instanzvariablen werden deklariert */
  Profil user = ClientsideSettings.getCurrentUser();
  Logger logger = ClientsideSettings.getLogger();
  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

  CustomPopupPanel pop = new CustomPopupPanel(false, true);

  ArrayList<Profil> profile = null;
  ArrayList<Suchprofil> suchProfilListe = null;
  ArrayList<Auswahl> auswahlListe = null;

  Profil profil = null;

  Suchprofil sp = null;
  ProfilAttributTextBox suchProfilTextbox = null;
  BoxPanel clb = null;

  Button suchprofilErstellButton = new Button("Suchprofil erstellen");
  Button sucheButton = new Button("Suchen");
  Button suchprofilLöschenButton = null;
  Button suchprofilSpeichernButton = null;

  @SuppressWarnings("deprecation")
  ListBox suchProfilListBox = new ListBox(true);

  /**
   * Headline Text zurückgeben
   * 
   * @return Text
   */
  @Override
  protected String getHeadlineText() {
    return "Suche und finde deine bessere Hälfte";
  }

  /**
   * SubHeadline Text zurückgeben
   * 
   * @return Text
   */
  @Override
  protected String getSubHeadlineText() {
    return "Suche den perfekten Match!";
  }

  FlowPanel searchPanel = new FlowPanel();
  FlowPanel searchBoxListBox = new FlowPanel();
  FlowPanel searchBoxProfilAttribute = new FlowPanel();
  FlowPanel searchBoxInfo = new FlowPanel();

  /**
   * Die Buttons und deren Panel fuer die Suchoptionen werden eingefuegt.
   */
  @Override
  protected void run() {
    HTML searchBoxLabel = new HTML("Suchprofile");
    searchBoxListBox.add(searchBoxLabel);
    searchPanel.setStyleName("content");
    searchBoxListBox.setStyleName("pure-u-1-3 l-box pure-form");
    searchBoxProfilAttribute.setStyleName("pure-u-1-3 l-box pure-form");
    searchBoxInfo.setStyleName("pure-u-1-3 l-box pure-form");

    searchPanel.add(searchBoxListBox);
    searchPanel.add(searchBoxProfilAttribute);
    searchPanel.add(searchBoxInfo);

    this.add(searchPanel);

    BoxPanel suchProfilName = new BoxPanel("Name des Suchprofils");
    suchProfilTextbox = new ProfilAttributTextBox();
    suchProfilTextbox.setName("suchProfilName");

    suchProfilName.add(suchProfilTextbox);

    suchprofilSpeichernButton = new Button("Suchprofil Speichern");
    suchprofilSpeichernButton.setStyleName("pure-button button-success");
    suchprofilLöschenButton = new Button("Suchprofil löschen");
    suchprofilLöschenButton.setStyleName("pure-button button-warning");

    suchprofilErstellButton.setStyleName("pure-button pure-button-primary");
    suchprofilLöschenButton.setEnabled(false);
    sucheButton.setStyleName("pure-button pure-button-primary");

    searchBoxListBox.add(suchProfilListBox);
    searchBoxListBox.add(suchProfilName);
    searchBoxListBox.add(suchprofilErstellButton);
    searchBoxListBox.add(suchprofilSpeichernButton);
    searchBoxListBox.add(suchprofilLöschenButton);
    searchBoxListBox.add(sucheButton);

    suchProfilListBox.setStyleName("search-box");

    suchProfilListBox.addClickHandler(new SuchProfilListBoxClickHandler());
    sucheButton.addClickHandler(new SucheButtonClickHandler());
    suchprofilLöschenButton.addClickHandler(new SuchprofilLöschenButtonClickHandler());
    suchprofilSpeichernButton.addClickHandler(new SuchprofilSpeichernButtonClickHandler());
    suchprofilErstellButton.addClickHandler(new SuchButtonClickHandler());

    pbVerwaltung.getAllAuswahl(new AllAuswahlCallback());
    pbVerwaltung.getAllSuchprofileForProfil(user, new AllSuchprofileForProfilCallback());
    pbVerwaltung.getAllAuswahlProfilAttribute(new GetAllAuswahlProfilAttributeCallback());

  }
  /**
   * 
   */
  private class AllSuchprofileForProfilCallback implements AsyncCallback<ArrayList<Suchprofil>> {
    /**
     * Fügt die Suchprofile des Benutzers in die Suchlistbox hinzu
     */
    @Override
    public void onSuccess(ArrayList<Suchprofil> result) {
      suchProfilListe = result;
      for (Suchprofil sp : suchProfilListe) {
        suchProfilListBox.addItem(sp.getName());
      }
      if (result.size() == 0) {
        new Notification("Sie haben keine Suchprofile", "info");
      }
    }
    
    /**
     * Um Fehler abzufangen.
     */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().info("Fehler AsyncCallback alle Suchprofile");
    }
  }

  private class SuchprofilSpeichernButtonClickHandler implements ClickHandler {
    /**
     * Suchprofil anlegen und speichern
     */
    @Override
    public void onClick(ClickEvent event) {
      Suchprofil tmpSp = Suche.this.createSP();
      tmpSp.setId(sp.getId());
      sp = tmpSp;

      pbVerwaltung.save(sp, new SaveSuchprofilCallback());
    }
  }

  private class SaveSuchprofilCallback implements AsyncCallback<Void> {
    /**
     * Speichert das Suchprofil in die Datenbank
     */
    @Override
    public void onSuccess(Void result) {
      new Notification("Suchprofil gespeichert", "success");
      logger.info("Suchprofil erflogreich gespeichert");
    }
    
    /**
     * Um Fehler abzufangen.
     */
    @Override
    public void onFailure(Throwable caught) {
      logger.info("Suchprofil nicht erflogreich gespeichert");
    }
  }

  private class SuchprofilLöschenButtonClickHandler implements ClickHandler {
    /**
     * Infoobjekte und Profilattribute des Suchprofils in die Listboxen schreiben
     */
    @Override
    public void onClick(ClickEvent event) {
      int i = suchProfilListBox.getSelectedIndex();
      suchProfilListBox.removeItem(i);
      new Notification("Suchprofil gelöscht", "info");
      pbVerwaltung.deleteSuchprofil(sp, new DeleteSuchprofilCallback());

    }
  }

  private class DeleteSuchprofilCallback implements AsyncCallback<Void> {
    /**
     * wenn loeschen erfolgreich, entsprechend melden
     */
    @Override
    public void onSuccess(Void result) {

      logger.info("Suchprofil erflogreich gelöscht");

    }

    /**
     * Um Fehler abzufangen.
     */
    @Override
    public void onFailure(Throwable caught) {
      logger.info("Suchprofil nicht erfolgreich gelöscht");
    }
  }

  private class SucheButtonClickHandler implements ClickHandler {
    /**
     * Profile nach Suchprofil laden (aus DB)
     */
    @Override
    public void onClick(ClickEvent event) {
      sp = Suche.this.createSP();
      pop.load();
      pbVerwaltung.getProfilesBySuchprofil(sp, user, new GetProfilesBySuchProfilCallback());

    }
  }

  private class SuchProfilListBoxClickHandler implements ClickHandler {
    /**
     * ClickHandler um die Listboxen mit den jeweiligen Daten zu aktualisieren,
     * wenn ein Suchprofil angeklickt wird/wurde.
     */
    @Override
    public void onClick(ClickEvent event) {
      suchprofilLöschenButton.setEnabled(true);

      ListBox clickedLb = (ListBox) event.getSource();
      suchProfilTextbox.setText(clickedLb.getSelectedItemText());

      pbVerwaltung.getSuchprofileForProfilByName(user, clickedLb.getSelectedItemText(),
          new SuchprofileForProfilByNameCallback());

    }
  }
  
  /**
   * Listboxen für alle Auswahleigesnchaften erstellen
   */
  private class AllAuswahlCallback implements AsyncCallback<ArrayList<Auswahl>> {
    /**
     * Erstellt Listboxen fuer die Auswahleigenschaften und fuegt diese dem FlowPanel hinzu
     */
    @Override
    public void onSuccess(ArrayList<Auswahl> result) {
      auswahlListe = result;

      for (Auswahl a : result) {
        BoxPanel clb = new BoxPanel(a, false);
        clb.setId(a.getId());
        clb.addKeineAngabenItem();
        searchBoxInfo.add(clb);
      }
    }

    /**
     * Um Fehler abzufangen.
     */
    @Override
    public void onFailure(Throwable caught) {}
  }

  /**
   * Suche durchfuehren, Profile die gepasst haben ausgeben mit Aehnlichkeitsmass.
   */
  private final class GetProfilesBySuchProfilCallback implements AsyncCallback<ArrayList<Profil>> {

    /**
     * onSuccess wird mit der ArrayList an Profilen die der Suche entsprochen haben ein Datagrid
     * erstellt welcher die Profile enthält
     *
     */
    @Override
    public void onSuccess(ArrayList<Profil> result) {
      if (result != null) {
        pop.stop();
        profile = result;

        DataGridProfiles dgp = new DataGridProfiles(profile);
        dgp.addClickFremdProfil();
        RootPanel.get("search-table").clear();
        RootPanel.get("search-table").add(dgp.start());
      }
    }
    
    /**
     * Um Fehler abzufangen.
     */
    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().info("Fehler Callback getProfilesBySuche");
    }
  }

  /**
   * Suchprofilattribute in die Listbox-Felder schreiben
   */
  private class SuchprofileForProfilByNameCallback implements AsyncCallback<Suchprofil> {
    /**
     * Infoobjekte und Profilattribute des Suchprofils in die Listboxen schreiben
     */
    @Override
    public void onSuccess(Suchprofil result) {
      sp = result;

      final HashMap<Integer, String> auswahlListeSp = sp.getAuswahlListe();

      for (Widget child : searchBoxInfo) {
        if (child instanceof BoxPanel) {
          BoxPanel childPanel = (BoxPanel) child;

          // Infoobjekte des Suchporifls in die Listboxen schreiben
          for (Auswahl a : auswahlListe) {
            if (childPanel.getId() == a.getId()) {
              childPanel.setSelectedItemForSP(auswahlListeSp.get(childPanel.getAuswahl().getId()));
            }
          }
        }
      }


      for (Widget child : searchBoxProfilAttribute) {
        if (child instanceof BoxPanel) {
          BoxPanel childPanel = (BoxPanel) child;

          // Infoobjekte des Suchporifls in die Listboxen schreiben
          for (Auswahl a : auswahlListe) {
            if (childPanel.getId() == a.getId()) {
              childPanel.setSelectedItemForSP(auswahlListeSp.get(childPanel.getAuswahl().getId()));
            }
          }
          // Profilattribute in die Listboxen schreiben
          switch (childPanel.getName()) {
            case "Raucher":
              childPanel.setSelectedItemForSP(sp.getRaucher());
              break;
            case "Haarfarbe":
              childPanel.setSelectedItemForSP(sp.getHaarfarbe());
              break;
            case "Religion":
              childPanel.setSelectedItemForSP(sp.getReligion());
              break;
            case "Geschlecht":
              childPanel.setSelectedItemForSP(sp.getGeschlecht());
              break;
            case "Körpergröße_min":
              childPanel.setGroesse(sp.getGroesse_min() + 1);
              break;
            case "Körpergröße_max":
              childPanel.setGroesse(sp.getGroesse_max() + 1);
              break;
            case "Alter_min":
              childPanel.setAlter(sp.getAlter_min());
              break;
            case "Alter_max":
              childPanel.setAlter(sp.getAlter_max());
              break;
          }
        }
      }
    }

    /**
     * Wenn Fehler bei Suche passierte, Meldung ausgeben.
     */
    @Override
    public void onFailure(Throwable caught) {
      logger.severe("Fehler bei Ausgabe eines Suchprofils");
    }
  }

  /**
   * Die Koerpergroesse und das Alter sollen eingrenzbar sein im Suchprofil.
   */
  private class GetAllAuswahlProfilAttributeCallback implements AsyncCallback<ArrayList<Auswahl>> {

    /**
     * Listboxen fuer die Ober- und Untergrenze fuer Koerpergroesse und Alter werden nur fuer
     * Suchprofil angelegt und verwendet. Dies wird den Profilattributen hinzugefuegt.
     */
    @Override
    public void onSuccess(ArrayList<Auswahl> result) {
      for (Auswahl a : result) {
        BoxPanel clb = new BoxPanel(a, true);
        clb.addKeineAngabenItem();
        searchBoxProfilAttribute.add(clb);
      }
      // Speichern die Ober- und Untergrenzen des Users bei der Suche nach anderen
      // Suchprofilen ab (damit Koerpergroesse und Alter eingegrenzt werden kann im Suchprofil)
      ProfilAttributListbox groesseMin = new ProfilAttributListbox();
      groesseMin.createGroesseListBox("Minimale Größe");
      groesseMin.setName("Körpergröße_min");
      groesseMin.addKeineAngabenItem();
      groesseMin.setEnable(true);

      ProfilAttributListbox groesseMax = new ProfilAttributListbox();
      groesseMax.createGroesseListBox("Maximale Größe");
      groesseMax.addKeineAngabenItem();
      groesseMax.setName("Körpergröße_max");
      groesseMax.setEnable(true);

      ProfilAttributListbox alterMin = new ProfilAttributListbox("Minimales Alter");
      alterMin.createAlterListbox();
      alterMin.setName("Alter_min");
      alterMin.addKeineAngabenItem();

      ProfilAttributListbox alterMax = new ProfilAttributListbox("Maximales Alter");
      alterMax.createAlterListbox();
      alterMax.addKeineAngabenItem();
      alterMax.setName("Alter_max");

      searchBoxProfilAttribute.add(groesseMin);
      searchBoxProfilAttribute.add(groesseMax);
      searchBoxProfilAttribute.add(alterMin);
      searchBoxProfilAttribute.add(alterMax);
    }

    /**
     * Um Fehler abzufangen; gibt Fehlermeldung aus.
     */
    @Override
    public void onFailure(Throwable caught) {
      logger.severe("Fehler beim GetAllAuswahlProfilAttributeCallback");
    }
  }

  /**
   * Clickhandler für den "suche" Button. Bei onClick sollen sämltiche der Suche entsprechenden
   * Profile ausgegeben werden.
   */

  private class SuchButtonClickHandler implements ClickHandler {

    /**
     * alle Suchprofile werden gesucht und aufgerufen.
     */
    @Override
    public void onClick(ClickEvent event) {
      sp = createSP();

      if (suchProfilListe.contains(sp)) {
        new Notification("Suchprofilname \"" + sp.getName() + "\" exisitiert bereits", "info");
      } else {
        suchProfilListe.add(sp);
        suchProfilListBox.addItem(sp.getName());
        pbVerwaltung.createSuchprofil(sp, new CreateSuchprofilCallback());
      }
    }
  }

  private class CreateSuchprofilCallback implements AsyncCallback<Void> {
    
    @Override
    public void onSuccess(Void result) {
      logger.info("Suchprofil in DB geschreiben " + sp.getName());
    }
    
    /**
     * Um Fehler abzufangen.
     */
    @Override
    public void onFailure(Throwable caught) {
      logger.severe("Suchprofil in DB geschreiben Fehler= " + sp.getName());
    }
  }

  /**
   * Suchprofil soll erstellt werden. Damit koennen alle angelegten Profile durchsucht werden, um
   * Profile zu finden, welche zum Suchprofil passen.
   * 
   * @return sp Suchprofil
   */
  public Suchprofil createSP() {
    final Suchprofil sp = new Suchprofil();

    sp.setProfilId(user.getId());
    sp.setName(suchProfilTextbox.getText());

    HashMap<Integer, String> auswahlListeSp = new HashMap<>();

    for (Widget child : searchBoxInfo) {
      if (child instanceof BoxPanel) {
        BoxPanel childPanel = (BoxPanel) child;
        if (childPanel.getName() != null) {

          /*
           * Auswahleigenschaftslistboxen dynamisch auslesen und in das Hashmap auswahlListeSp
           * speichern
           */
          for (Auswahl a : auswahlListe) {
            if (childPanel.getId() == a.getId()) {

              if (!childPanel.getSelectedItem().equals("Keine Angabe")) {
                auswahlListeSp.put(childPanel.getAuswahl().getId(), childPanel.getSelectedItem());
              }
            }
          }
        }
      }
    }

    for (Widget child : searchBoxProfilAttribute) {
      if (child instanceof BoxPanel) {
        BoxPanel childPanel = (BoxPanel) child;
        if (childPanel.getName() != null) {

          /*
           * Auswahleigenschaftslistboxen dynamisch auslesen und in des Hashmap auswahlListeSp
           * speichern
           */
          for (Auswahl a : auswahlListe) {
            if (childPanel.getId() == a.getId()) {

              if (!childPanel.getSelectedItem().equals("Keine Angabe")) {
                auswahlListeSp.put(childPanel.getAuswahl().getId(), childPanel.getSelectedItem());
              }
            }
          }

          // Profilattribute mit Hilfe des Suchprofils setzen
          switch (childPanel.getName()) {

            case "Raucher":
              sp.setRaucher(childPanel.getSelectedItem());
              break;
            case "Haarfarbe":
              sp.setHaarfarbe(childPanel.getSelectedItem());
              break;
            case "Religion":
              sp.setReligion(childPanel.getSelectedItem());
              break;
            case "Geschlecht":
              sp.setGeschlecht(childPanel.getSelectedItem());
              break;
            case "Körpergröße_min":
              if (!childPanel.getSelectedItem().equals("Keine Angabe")) {
                sp.setGroesse_min(Integer.valueOf(childPanel.getSelectedItem()));
              }
              break;
            case "Körpergröße_max":
              if (!childPanel.getSelectedItem().equals("Keine Angabe")) {
                sp.setGroesse_max(Integer.valueOf(childPanel.getSelectedItem()));
              }
              break;
            case "Alter_min":
              if (!childPanel.getSelectedItem().equals("Keine Angabe")) {
                sp.setAlter_min(Integer.valueOf(childPanel.getSelectedItem()));
              }
              break;
            case "Alter_max":
              if (!childPanel.getSelectedItem().equals("Keine Angabe")) {
                sp.setAlter_max(Integer.valueOf(childPanel.getSelectedItem()));
              }
              break;
          }
        }
      }
    }

    sp.setAuswahlListe(auswahlListeSp);
    return sp;
  }

}