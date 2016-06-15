
package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

import de.superteam2000.gwt.client.gui.ProfilAttributeBoxPanel;
import de.superteam2000.gwt.client.gui.ProfilAttributeTextBox;
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
  FlowPanel fPanel = null;

  Suchprofil sp = null;
  ProfilAttributeTextBox suchProfilTextbox = null;
  ProfilAttributeBoxPanel clb = null;

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
    return null;
  }

  @Override
  protected void run() {
    fPanel = new FlowPanel();
    fPanel.setStyleName("ProfilAttribute-Suche");

    ProfilAttributeBoxPanel suchProfilName = new ProfilAttributeBoxPanel("Name des Suchprofils");
    suchProfilTextbox = new ProfilAttributeTextBox();
    suchProfilTextbox.setName("suchProfilName");

    pbVerwaltung.getAllAuswahl(new AsyncCallback<ArrayList<Auswahl>>() {

      @Override
      public void onSuccess(ArrayList<Auswahl> result) {
        auswahlListe = result;
        // Erstelle Listboxen für die Auswahleigenschaften und füge sie dem FlowPanel hinzu

        for (Auswahl a : result) {
          ProfilAttributeBoxPanel clb = new ProfilAttributeBoxPanel(a, false);
          clb.setId(a.getId());
          clb.addKeineAngabenItem();
          fPanel.add(clb);
        }
      }

      @Override
      public void onFailure(Throwable caught) {
        // TODO Auto-generated method stub

      }
    });

    suchProfilListBox.setSize("8em", "14em");
    suchProfilListBox.addClickHandler(new ClickHandler() {

      // ClickHandler um die Listboxen mit den jeweiligen Daten zu aktualisieren,
      // wenn ein Suchprofil angeklickt wird

      @Override
      public void onClick(ClickEvent event) {
        suchprofilLöschenButton.setEnabled(true);

        ListBox clickedLb = (ListBox) event.getSource();
        suchProfilTextbox.setText(clickedLb.getSelectedItemText());

        pbVerwaltung.getSuchprofileForProfilByName(user, clickedLb.getSelectedItemText(),
            new AsyncCallback<Suchprofil>() {

              @Override
              public void onSuccess(Suchprofil result) {
                sp = result;
                logger.info(sp.getId() + " naem:" + sp.getName() + " pid" + sp.getProfilId()
                    + " haar:" + sp.getHaarfarbe() + " rauch:" + sp.getRaucher() + " rel:"
                    + sp.getReligion() + " g:" + sp.getGeschlecht() + " amax" + sp.getAlter_max()
                    + " amin" + sp.getAlter_min() + " gmax" + sp.getGroesse_max() + " gmin"
                    + sp.getGroesse_min() + " ");

                final HashMap<Integer, String> auswahlListeSp = sp.getAuswahlListe();

                for (Map.Entry<Integer, String> e : auswahlListeSp.entrySet()) {
                  logger.severe(e.getKey() + " " + e.getValue());
                }

                for (Widget child : fPanel) {
                  if (child instanceof ProfilAttributeBoxPanel) {
                    ProfilAttributeBoxPanel childPanel = (ProfilAttributeBoxPanel) child;

                    for (Auswahl a : auswahlListe) {
                      if (childPanel.getId() == a.getId()) {
                        childPanel.setSelectedItemForSP(
                            auswahlListeSp.get(childPanel.getAuswahl().getId()));
                      }
                    }

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

              @Override
              public void onFailure(Throwable caught) {
                logger.severe("Fehler bei Ausgabe eines Suchprofils");

              }
            });

      }
    });

    suchProfilName.add(suchProfilTextbox);
    fPanel.add(suchProfilName);
    fPanel.add(suchprofilErstellButton);
    fPanel.add(sucheButton);

    suchprofilSpeichernButton = new Button("Suchprofil Speichern");
    suchprofilLöschenButton = new Button("Suchprofil löschen");
    suchprofilLöschenButton.setEnabled(false);

    RootPanel.get("Menu").add(suchProfilListBox);
    RootPanel.get("Menu").add(suchprofilSpeichernButton);
    RootPanel.get("Menu").add(suchprofilLöschenButton);

    sucheButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        RootPanel.get("rechts").clear();
        sp = createSP();
        logger.info(sp.getRaucher());
        ClientsideSettings.getPartnerboerseVerwaltung().getProfilesBySuchprofil(sp,
            user, new AsyncCallback<ArrayList<Profil>>() {

              /**
               * onSuccess wird mit der ArrayList an Profilen die der Suche entsprochen haben ein
               * Datagrid erstellt welcher die Profile enthält
               *
               */
              @Override
              public void onSuccess(ArrayList<Profil> result) {
                if (result != null) {
                  profile = result;

                  DataGridForProfiles dgp = new DataGridForProfiles(profile);
                  RootPanel.get("rechts").add(dgp);
                }

              }

              @Override
              public void onFailure(Throwable caught) {
                ClientsideSettings.getLogger().info("Fehler Callback getProfilesBySuche");
              }
            });

      }
    });


    suchprofilLöschenButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        int i = suchProfilListBox.getSelectedIndex();
        suchProfilListBox.removeItem(i);

        pbVerwaltung.deleteSuchprofil(sp, new AsyncCallback<Void>() {

          @Override
          public void onSuccess(Void result) {
            logger.info("Suchprofil erflogreich gelöscht");

          }

          @Override
          public void onFailure(Throwable caught) {
            logger.info("Suchprofil nicht erfolgreich gelöscht");
          }
        });

      }
    });

    suchprofilSpeichernButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        Suchprofil tmpSp = createSP();
        tmpSp.setId(sp.getId());
        sp = tmpSp;

        pbVerwaltung.save(sp, new AsyncCallback<Void>() {

          @Override
          public void onSuccess(Void result) {
            logger.info("Suchprofil erflogreich gespeichert");

          }

          @Override
          public void onFailure(Throwable caught) {
            logger.info("Suchprofil nicht erflogreich gespeichert");
          }
        });

      }
    });

    pbVerwaltung.getAllSuchprofileForProfil(user, new AsyncCallback<ArrayList<Suchprofil>>() {

      // Befülle die SuchProfilListBox mit bereits gespeichtern Suchprofilen


      @Override
      public void onSuccess(ArrayList<Suchprofil> result) {
        suchProfilListe = result;
        for (Suchprofil sp : suchProfilListe) {
          suchProfilListBox.addItem(sp.getName());
        }

      }

      @Override
      public void onFailure(Throwable caught) {
        ClientsideSettings.getLogger().info("Fehler AsyncCallback alle Suchprofile");
      }
    });



    pbVerwaltung.getAllAuswahlProfilAttribute(new GetAllAuswahlProfilAttributeCallback());

    suchprofilErstellButton.addClickHandler(new SuchButtonClickHandler());

    RootPanel.get("Details").add(fPanel);

    // Alle Profile aus der db holen
    // pbVerwaltung.getAllProfiles(new AsyncCallback<ArrayList<Profil>>() {
    //
    // @Override
    // public void onSuccess(ArrayList<Profil> result) {
    // if (result != null) {
    // profile = result;
    // }
    //
    // }
    //
    // @Override
    // public void onFailure(Throwable caught) {
    // ClientsideSettings.getLogger().severe("Fehler AsyncCallback alle Profile");
    //
    // }
    // });
  }

  private class GetAllAuswahlProfilAttributeCallback implements AsyncCallback<ArrayList<Auswahl>> {
    @Override
    public void onSuccess(ArrayList<Auswahl> result) {
      for (Auswahl a : result) {
        ProfilAttributeBoxPanel clb = new ProfilAttributeBoxPanel(a, true);
        clb.addKeineAngabenItem();
        fPanel.add(clb);

      }

      ProfilAttributeBoxPanel groesseMin = new ProfilAttributeBoxPanel("Minimale Größe");
      groesseMin.createGroesseListBox();
      groesseMin.setName("Körpergröße_min");
      groesseMin.addKeineAngabenItem();

      ProfilAttributeBoxPanel groesseMax = new ProfilAttributeBoxPanel("Maximale Größe");
      groesseMax.createGroesseListBox();
      groesseMax.addKeineAngabenItem();
      groesseMax.setName("Körpergröße_max");

      ProfilAttributeBoxPanel alterMin = new ProfilAttributeBoxPanel("Minimales Alter");
      alterMin.createAlterListbox();
      alterMin.setName("Alter_min");
      alterMin.addKeineAngabenItem();

      ProfilAttributeBoxPanel alterMax = new ProfilAttributeBoxPanel("Maximales Alter");
      alterMax.createAlterListbox();
      alterMax.addKeineAngabenItem();
      alterMax.setName("Alter_max");

      // Körpergröße und Geburtstags Listboxen werden nach den
      // AuswahlProfilAttributen zum Panel hinzugefügt

      fPanel.add(groesseMin);
      fPanel.add(groesseMax);
      fPanel.add(alterMin);
      fPanel.add(alterMax);

    }

    @Override
    public void onFailure(Throwable caught) {
      logger.severe("Fehler beim GetAllAuswahlProfilAttributeCallback");
    }
  }

  /**
   * Clickhandler für den "suche" Button. onClick sollen sämltiche der Suche entsprechenden Profile
   * ausgegeben werden
   */

  private class SuchButtonClickHandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {

      sp = createSP();
      RootPanel.get("Menu").clear();
      RootPanel.get("rechts").clear();

      RootPanel.get("Menu").add(suchProfilListBox);
      RootPanel.get("Menu").add(suchprofilSpeichernButton);
      RootPanel.get("Menu").add(suchprofilLöschenButton);


      if (suchProfilListe.contains(sp)) {
        RootPanel.get("Details").add(new HTML("suchprofil schon vorhanden"));
      } else {
        suchProfilListe.add(sp);
        suchProfilListBox.addItem(sp.getName());
        pbVerwaltung.createSuchprofil(sp, new AsyncCallback<Void>() {

          @Override
          public void onSuccess(Void result) {
            logger.info("Suchprofil in DB geschreiben " + sp.getName());
          }

          @Override
          public void onFailure(Throwable caught) {
            logger.severe("Suchprofil in DB geschreiben Fehler= " + sp.getName());
          }
        });
      }

    }

  }

  public Suchprofil createSP() {
    final Suchprofil sp = new Suchprofil();

    sp.setProfilId(user.getId());
    sp.setName(suchProfilTextbox.getText());

    HashMap<Integer, String> auswahlListeSp = new HashMap<>();

    for (Widget child : fPanel) {
      if (child instanceof ProfilAttributeBoxPanel) {
        ProfilAttributeBoxPanel childPanel = (ProfilAttributeBoxPanel) child;
        if (childPanel.getName() != null) {

          // Auswahleigenschaftslistboxen dynamisch auslesen und in des Hashmap auswahlListeSp
          // speichern
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

 

