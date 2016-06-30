package de.superteam2000.gwt.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimpleCheckBox;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Die Klasse EigenschaftPanel erweitert BoxPanel. Sie dient dazu Info-Objekte mit ihrem Namen und
 * einer Auswahlliste bzw. eines Textfeldes darstellt. Zusätzlich wid eine Checkbox zum hinzufügen
 * der ausgewählten Eigenschaften hinzugefügt.
 * 
 * @author Volz
 *
 */
public class EigenschaftPanel extends BoxPanel implements ClickHandler, ChangeHandler {
  /*
   * Alle notwendigen Instanzvariablen werden deklariert
   */

  Profil user = ClientsideSettings.getCurrentUser();
  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

  SimpleCheckBox check = new SimpleCheckBox();
  int infoId = 0;
  Info i = new Info();

  /**
   * Erstellt ein BoxPanel und fügt ihm eine Checkbox hinzu. Setzt die Checkbox true und setzt den
   * Text der Beschreibung, wenn der User ein Info-Objekt zu der Auswahl hat.
   * 
   * @param b Beschreibungseigenschaft
   * @param isNameTextbox Variable die angibt ob der Beschreibungstext oder der Name der Eigenschaft
   *        angezeigt werden soll
   * @param infoListe Liste mit Info-Objekten des Benutzers
   */
  public EigenschaftPanel(Beschreibung b, boolean isNameTextbox, ArrayList<Info> infoListe) {
    super(b, isNameTextbox);
    this.add(check);
    check.addClickHandler(this);

    for (Info info : infoListe) {
      if (b.getId() == info.getEigenschaftId()) {
        check.setValue(true);
        i = info;
        setInfoId(info.getId());
        setText(info.getText());
      }
    }

    check.setStyleName("pure-checkbox");
  }

  /**
   * Erstellt ein BoxPanel und fügt ihm eine Checkbox hinzu. Setzt die Checkbox true und setzt die
   * die Auswahlalternative der Listbox, wenn der User ein Info-Objekt zu der Auswahl hat.
   * 
   * @param a Auswahleigenschaften
   * @param isNameTextbox Variable die angibt ob der Beschreibungstext oder der Name der Eigenschaft
   *        angezeigt werden soll
   * @param infoListe Liste mit Info-Objekten des Benutzers
   */
  public EigenschaftPanel(Auswahl a, boolean isNameListbox, ArrayList<Info> infoListe) {
    super(a, isNameListbox);
    this.add(check);
    check.addClickHandler(this);
    profilAttributListBox.addChangeHandler(this);

    for (Info info : infoListe) {
      if (a.getId() == info.getEigenschaftId()) {
        check.setValue(true);
        i = info;
        setInfoId(info.getId());
        setSelectedItem(info.getText());
      }
    }

    check.setStyleName("pure-checkbox");
  }

  public int getInfoId() {
    return infoId;
  }

  public void setInfoId(int infoId) {
    this.infoId = infoId;
  }


  @Override
  public void onClick(ClickEvent event) {
    // Prüft mit einer If-Kaskade, ob die Checkbox einer Auswahleigenschaft true oder false ist und löscht oder fügt diese
    // also Info-Objekt zur DB hinzu

    if (check.getValue() && auswahl != null) {
      saveAuswahlSelection();
    } else if (!check.getValue() && auswahl != null) {
      deleteAuswahlInfo();
    }

    // Prüft mit einer If-Kaskade, ob die Checkbox einer Beschreibungseigenschaft true oder false ist und löscht oder fügt
    // diese also Info-Objekt zur DB hinzu
    if (check.getValue() && beschreibung != null) {
      saveBeschreibung();
    } else if (!check.getValue() && beschreibung != null) {
      deleteBeschreibungInfo();
    }
  } 
  /**
   * Löscht ein Auswahl-Info-Objekt eines Benutzers aus der DB
   */
  private void deleteAuswahlInfo() {

    pbVerwaltung.getInfoById(getInfoId(), new AsyncCallback<Info>() {
      @Override
      public void onSuccess(Info result) {
        new Notification("Auswahl " + result.getText() + " gelöscht", "success");
        pbVerwaltung.delete(result, new AsyncCallback<Void>() {

          @Override
          public void onSuccess(Void result) {
            ClientsideSettings.getLogger().info("Auswahl Info gelöscht");
          }

          @Override
          public void onFailure(Throwable caught) {
            new Notification("Fehler beim Löschen der Eigenschaft", "error");
          }
        });
      }

      @Override
      public void onFailure(Throwable caught) {
        ClientsideSettings.getLogger().severe("Info nicht geholt");
      }
    });
  }

  /**
   * Löscht ein Beschreibungs-Info-Objekt eines Benutzers aus der DB
   */
  private void deleteBeschreibungInfo() {
    profilAttributTextBox.setText("");

    pbVerwaltung.getInfoById(getInfoId(), new AsyncCallback<Info>() {

      @Override
      public void onSuccess(Info result) {
        // Fängt einen Fehler ab, der öfters auftitt aber nicht reproduzierbar ist
        try {
          new Notification("Beschreibung " + result.getText() + " gelöscht", "success");
        } catch (Exception e) {
          new Notification("Hups, da ist wohl was schief gelaufen. Lade die Seite bitte neu",
              "info");
        }
        pbVerwaltung.delete(result, new AsyncCallback<Void>() {

          @Override
          public void onSuccess(Void result) {

            ClientsideSettings.getLogger().info("beschreibungs Info gelöscht");
          }

          @Override
          public void onFailure(Throwable caught) {
            new Notification("Fehler beim Löschen der Eigenschaft", "error");
          }
        });
      }

      @Override
      public void onFailure(Throwable caught) {
        ClientsideSettings.getLogger().severe("Info nicht geholt");

      }
    });
  }

  /**
   * Speichert ein Auswahl-Info-Objekt eines Benutzers in die DB
   */
  private void saveAuswahlSelection() {
    pbVerwaltung.createInfoFor(user, auswahl, getSelectedItem(), new AsyncCallback<Info>() {

      @Override
      public void onSuccess(Info result) {
        i = result;
        EigenschaftPanel.this.setInfoId(result.getId());
        result.setId(EigenschaftPanel.this.getInfoId());

        new Notification("Auswahl " + result.getText() + " gespeichert", "success");
      }

      @Override
      public void onFailure(Throwable caught) {
        ClientsideSettings.getLogger().info("Fehler Auswahl speichern");

      }
    });
  }

  /**
   * Speichert ein Beschreibungs-Info-Objekt eines Benutzers in die DB
   */
  private void saveBeschreibung() {
    pbVerwaltung.createInfoFor(user, beschreibung, profilAttributTextBox.getText(),
        new AsyncCallback<Info>() {

          @Override
          public void onSuccess(Info result) {
            new Notification("Beschreibung " + result.getText() + " gespeichert", "success");
          }

          @Override
          public void onFailure(Throwable caught) {
            ClientsideSettings.getLogger().info("Fehler Beschreibung speichern");

          }
        });
  }

  /**
   * Speichert die Selektion einer Auswahleigenschafts-Listox eines Benutzers in die DB
   */
  @Override
  public void onChange(ChangeEvent event) {
    if (check.getValue()) {
      i.setText(getSelectedItem());
      new Notification("Eigenschaft auf " + i.getText() + " geändert!", "success");

      pbVerwaltung.save(i, new AsyncCallback<Void>() {

        @Override
        public void onSuccess(Void result) {}

        @Override
        public void onFailure(Throwable caught) {}
      });
    }
  }

}
