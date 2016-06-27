
package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import de.superteam2000.gwt.client.gui.BoxPanel;
import de.superteam2000.gwt.client.gui.DataGridProfiles;
import de.superteam2000.gwt.client.gui.Notification;
import de.superteam2000.gwt.client.gui.ProfilAttributListbox;
import de.superteam2000.gwt.client.gui.ProfilAttributTextBox;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.bo.Suchprofil;

/**
 * Die Klasse Suche ist für die Darstellung von Möglichen Auswahlen und eine anschließende Suche
 * anhand dieser Kriterien
 *
 * @author Funke, Volz
 *
 */
public class Suche extends BasicFrame {

  ArrayList<Profil> profile = null;
  ArrayList<Suchprofil> suchProfilListe = null;
  ArrayList<Auswahl> auswahlListe = null;

  Profil profil = null;
  FlowPanel fPanel = new FlowPanel();

  Suchprofil sp = null;
  ProfilAttributTextBox suchProfilTextbox = null;
  BoxPanel clb = null;

  Button suchprofilErstellButton = new Button("Suchprofil erstellen");
  Button sucheButton = new Button("Suchen");
  Button suchprofilLöschenButton = null;
  Button suchprofilSpeichernButton = null;

  @SuppressWarnings("deprecation")
  ListBox suchProfilListBox = new ListBox(true);

  Profil user = ClientsideSettings.getCurrentUser();
  Logger logger = ClientsideSettings.getLogger();
  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

  @Override
  protected String getHeadlineText() {
    return "Suche und finde deine bessere Hälfte";
  }

  @Override
  protected String getSubHeadlineText() {
    // TODO Auto-generated method stub
    return "Suche den perfekten Match!";
  }

  FlowPanel searchPanel = new FlowPanel();
  FlowPanel searchBoxListBox = new FlowPanel();
  FlowPanel searchBoxProfilAttribute = new FlowPanel();
  FlowPanel searchBoxInfo = new FlowPanel();

  @Override
  protected void run() {
    this.searchPanel.setStyleName("content");
    this.searchBoxListBox.setStyleName("pure-u-1-3 pure-form");
    this.searchBoxProfilAttribute.setStyleName("pure-u-1-3 l-box pure-form");
    this.searchBoxInfo.setStyleName("pure-u-1-3 l-box pure-form");

    this.searchPanel.add(this.searchBoxListBox);
    this.searchPanel.add(this.searchBoxProfilAttribute);
    this.searchPanel.add(this.searchBoxInfo);

    this.fPanel.add(this.searchPanel);


    BoxPanel suchProfilName = new BoxPanel("Name des Suchprofils");
    this.suchProfilTextbox = new ProfilAttributTextBox();
    this.suchProfilTextbox.setName("suchProfilName");

    suchProfilName.add(this.suchProfilTextbox);

    this.suchprofilSpeichernButton = new Button("Suchprofil Speichern");
    this.suchprofilSpeichernButton.setStyleName("pure-button button-success");
    this.suchprofilLöschenButton = new Button("Suchprofil löschen");
    this.suchprofilLöschenButton.setStyleName("pure-button button-warning");

    this.suchprofilErstellButton.setStyleName("pure-button pure-button-primary");
    this.suchprofilLöschenButton.setEnabled(false);
    this.sucheButton.setStyleName("pure-button pure-button-primary");

    this.searchBoxListBox.add(this.suchProfilListBox);
    this.searchBoxListBox.add(suchProfilName);
    this.searchBoxListBox.add(this.suchprofilErstellButton);
    this.searchBoxListBox.add(this.suchprofilSpeichernButton);
    this.searchBoxListBox.add(this.suchprofilLöschenButton);
    this.searchBoxListBox.add(this.sucheButton);


    this.suchProfilListBox.setStyleName("search-box");

    RootPanel.get("main").add(this.fPanel);



    this.pbVerwaltung.getAllAuswahl(new AsyncCallback<ArrayList<Auswahl>>() {

      @Override
      public void onSuccess(ArrayList<Auswahl> result) {
        Suche.this.auswahlListe = result;
        // Erstelle Listboxen für die Auswahleigenschaften und füge sie dem FlowPanel hinzu

        for (Auswahl a : result) {
          BoxPanel clb = new BoxPanel(a, false);
          clb.setId(a.getId());
          clb.addKeineAngabenItem();
          Suche.this.searchBoxInfo.add(clb);
        }
      }

      @Override
      public void onFailure(Throwable caught) {
        // TODO Auto-generated method stub

      }
    });



    this.suchProfilListBox.addClickHandler(new ClickHandler() {

      // ClickHandler um die Listboxen mit den jeweiligen Daten zu aktualisieren,
      // wenn ein Suchprofil angeklickt wird

      @Override
      public void onClick(ClickEvent event) {
        Suche.this.suchprofilLöschenButton.setEnabled(true);

        ListBox clickedLb = (ListBox) event.getSource();
        Suche.this.suchProfilTextbox.setText(clickedLb.getSelectedItemText());

        Suche.this.pbVerwaltung.getSuchprofileForProfilByName(Suche.this.user,
            clickedLb.getSelectedItemText(), new AsyncCallback<Suchprofil>() {

              @Override
              public void onSuccess(Suchprofil result) {
                Suche.this.sp = result;

                final HashMap<Integer, String> auswahlListeSp = Suche.this.sp.getAuswahlListe();

                for (Widget child : Suche.this.searchBoxInfo) {
                  if (child instanceof BoxPanel) {
                    BoxPanel childPanel = (BoxPanel) child;

                    // Infoobjekte des Suchporifls in die Listboxen schreiben
                    for (Auswahl a : Suche.this.auswahlListe) {
                      if (childPanel.getId() == a.getId()) {
                        childPanel.setSelectedItemForSP(
                            auswahlListeSp.get(childPanel.getAuswahl().getId()));
                      }
                    }
                  }
                }


                for (Widget child : Suche.this.searchBoxProfilAttribute) {
                  if (child instanceof BoxPanel) {
                    BoxPanel childPanel = (BoxPanel) child;

                    // Infoobjekte des Suchporifls in die Listboxen schreiben
                    for (Auswahl a : Suche.this.auswahlListe) {
                      if (childPanel.getId() == a.getId()) {
                        childPanel.setSelectedItemForSP(
                            auswahlListeSp.get(childPanel.getAuswahl().getId()));
                      }
                    }
                    // Profilattribute in die Listboxen schreiben
                    switch (childPanel.getName()) {
                      case "Raucher":
                        childPanel.setSelectedItemForSP(Suche.this.sp.getRaucher());
                        break;
                      case "Haarfarbe":
                        childPanel.setSelectedItemForSP(Suche.this.sp.getHaarfarbe());
                        break;
                      case "Religion":
                        childPanel.setSelectedItemForSP(Suche.this.sp.getReligion());
                        break;
                      case "Geschlecht":
                        childPanel.setSelectedItemForSP(Suche.this.sp.getGeschlecht());
                        break;
                      case "Körpergröße_min":
                        childPanel.setGroesse(Suche.this.sp.getGroesse_min() + 1);
                        break;
                      case "Körpergröße_max":
                        childPanel.setGroesse(Suche.this.sp.getGroesse_max() + 1);
                        break;
                      case "Alter_min":
                        childPanel.setAlter(Suche.this.sp.getAlter_min());
                        break;
                      case "Alter_max":
                        childPanel.setAlter(Suche.this.sp.getAlter_max());
                        break;
                    }
                  }
                }

              }

              @Override
              public void onFailure(Throwable caught) {
                Suche.this.logger.severe("Fehler bei Ausgabe eines Suchprofils");

              }
            });

      }
    });



    this.sucheButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        // RootPanel.get("main").clear();
        Suche.this.sp = Suche.this.createSP();

        ClientsideSettings.getPartnerboerseVerwaltung().getProfilesBySuchprofil(Suche.this.sp,
            Suche.this.user, new GetProfilesBySuchProfilCallback());

      }
    });


    this.suchprofilLöschenButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        int i = Suche.this.suchProfilListBox.getSelectedIndex();
        Suche.this.suchProfilListBox.removeItem(i);

        Suche.this.pbVerwaltung.deleteSuchprofil(Suche.this.sp, new AsyncCallback<Void>() {

          @Override
          public void onSuccess(Void result) {
            Suche.this.logger.info("Suchprofil erflogreich gelöscht");

          }

          @Override
          public void onFailure(Throwable caught) {
            Suche.this.logger.info("Suchprofil nicht erfolgreich gelöscht");
          }
        });

      }
    });

    this.suchprofilSpeichernButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        Suchprofil tmpSp = Suche.this.createSP();
        tmpSp.setId(Suche.this.sp.getId());
        Suche.this.sp = tmpSp;

        Suche.this.pbVerwaltung.save(Suche.this.sp, new AsyncCallback<Void>() {

          @Override
          public void onSuccess(Void result) {
            Suche.this.logger.info("Suchprofil erflogreich gespeichert");

          }

          @Override
          public void onFailure(Throwable caught) {
            Suche.this.logger.info("Suchprofil nicht erflogreich gespeichert");
          }
        });

      }
    });

    this.pbVerwaltung.getAllSuchprofileForProfil(this.user,
        new AsyncCallback<ArrayList<Suchprofil>>() {

          // Befülle die SuchProfilListBox mit bereits gespeichtern Suchprofilen


          @Override
          public void onSuccess(ArrayList<Suchprofil> result) {
            Suche.this.suchProfilListe = result;
            for (Suchprofil sp : Suche.this.suchProfilListe) {
              Suche.this.suchProfilListBox.addItem(sp.getName());
            }
            if (result.size() == 0) {
              new Notification("Sie haben keine Suchprofile", "info");
            }
          }

          @Override
          public void onFailure(Throwable caught) {
            ClientsideSettings.getLogger().info("Fehler AsyncCallback alle Suchprofile");
          }
        });



    this.pbVerwaltung.getAllAuswahlProfilAttribute(new GetAllAuswahlProfilAttributeCallback());

    this.suchprofilErstellButton.addClickHandler(new SuchButtonClickHandler());

    // RootPanel.get("main").add(fPanel);
  }

  private final class GetProfilesBySuchProfilCallback implements AsyncCallback<ArrayList<Profil>> {
    /**
     * onSuccess wird mit der ArrayList an Profilen die der Suche entsprochen haben ein Datagrid
     * erstellt welcher die Profile enthält
     *
     */
    @Override
    public void onSuccess(ArrayList<Profil> result) {
      if (result != null) {
        Suche.this.profile = result;

        DataGridProfiles dgp = new DataGridProfiles(Suche.this.profile);
        dgp.addClickFremdProfil();
        RootPanel.get("search-table").clear();
        RootPanel.get("search-table").add(dgp.start());
      }

    }

    @Override
    public void onFailure(Throwable caught) {
      ClientsideSettings.getLogger().info("Fehler Callback getProfilesBySuche");
    }
  }

  private class GetAllAuswahlProfilAttributeCallback implements AsyncCallback<ArrayList<Auswahl>> {
    @Override
    public void onSuccess(ArrayList<Auswahl> result) {
      for (Auswahl a : result) {
        BoxPanel clb = new BoxPanel(a, true);
        clb.addKeineAngabenItem();
        Suche.this.searchBoxProfilAttribute.add(clb);

      }

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

      // Körpergröße und Geburtstags Listboxen werden nach den
      // AuswahlProfilAttributen zum Panel hinzugefügt

      Suche.this.searchBoxProfilAttribute.add(groesseMin);
      Suche.this.searchBoxProfilAttribute.add(groesseMax);
      Suche.this.searchBoxProfilAttribute.add(alterMin);
      Suche.this.searchBoxProfilAttribute.add(alterMax);

    }

    @Override
    public void onFailure(Throwable caught) {
      Suche.this.logger.severe("Fehler beim GetAllAuswahlProfilAttributeCallback");
    }
  }

  /**
   * Clickhandler für den "suche" Button. onClick sollen sämltiche der Suche entsprechenden Profile
   * ausgegeben werden
   */

  private class SuchButtonClickHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {

      Suche.this.sp = Suche.this.createSP();


      if (Suche.this.suchProfilListe.contains(Suche.this.sp)) {
        new Notification("Suchprofilname " + Suche.this.sp.getName() + " exisitiert bereits",
            "info");
      } else {
        Suche.this.suchProfilListe.add(Suche.this.sp);
        Suche.this.suchProfilListBox.addItem(Suche.this.sp.getName());
        Suche.this.pbVerwaltung.createSuchprofil(Suche.this.sp, new AsyncCallback<Void>() {

          @Override
          public void onSuccess(Void result) {
            Suche.this.logger.info("Suchprofil in DB geschreiben " + Suche.this.sp.getName());
          }

          @Override
          public void onFailure(Throwable caught) {
            Suche.this.logger
                .severe("Suchprofil in DB geschreiben Fehler= " + Suche.this.sp.getName());
          }
        });
      }

    }

  }

  public Suchprofil createSP() {
    final Suchprofil sp = new Suchprofil();

    sp.setProfilId(this.user.getId());
    sp.setName(this.suchProfilTextbox.getText());

    HashMap<Integer, String> auswahlListeSp = new HashMap<>();



    for (Widget child : this.searchBoxInfo) {
      if (child instanceof BoxPanel) {
        BoxPanel childPanel = (BoxPanel) child;
        if (childPanel.getName() != null) {

          // Auswahleigenschaftslistboxen dynamisch auslesen und in des Hashmap auswahlListeSp
          // speichern
          for (Auswahl a : this.auswahlListe) {
            if (childPanel.getId() == a.getId()) {

              if (!childPanel.getSelectedItem().equals("Keine Angabe")) {
                auswahlListeSp.put(childPanel.getAuswahl().getId(), childPanel.getSelectedItem());
              }
            }
          }
        }
      }
    }

    for (Widget child : this.searchBoxProfilAttribute) {
      if (child instanceof BoxPanel) {
        BoxPanel childPanel = (BoxPanel) child;
        if (childPanel.getName() != null) {

          // Auswahleigenschaftslistboxen dynamisch auslesen und in des Hashmap auswahlListeSp
          // speichern
          for (Auswahl a : this.auswahlListe) {
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


