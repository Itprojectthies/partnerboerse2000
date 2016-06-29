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

public class EigenschaftPanel extends BoxPanel implements ClickHandler, ChangeHandler {
  Profil user = ClientsideSettings.getCurrentUser();
  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

  SimpleCheckBox check = new SimpleCheckBox();
  

  int infoId = 0;

  Info i = new Info();

  /**
   * @return the infoId
   */
  public synchronized int getInfoId() {
    return infoId;
  }

  /**
   * @param infoId the infoId to set
   */
  public synchronized void setInfoId(int infoId) {
    this.infoId = infoId;
  }

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


  @Override
  public void onClick(ClickEvent event) {

    if (check.getValue() && auswahl != null) {
      ClientsideSettings.getLogger().info("check 1 auswahl gespeichert" + auswahl.getName());
      saveAuswahlSelection();

    } else if (!check.getValue() && auswahl != null) {
      deleteAuswahlInfo();
      ClientsideSettings.getLogger().info("check 1 auswahl gelöscht" + auswahl.getName());
    }
    if (check.getValue() && beschreibung != null) {
      ClientsideSettings.getLogger().info("check 1 auswahl gespeichert" + beschreibung.getName());
      saveBeschreibung();
      
    } else if (!check.getValue() && beschreibung != null) {
      deleteBeschreibungInfo();
      ClientsideSettings.getLogger().info("check 1 auswahl gelöscht" + beschreibung.getName());
    }

//    if (check.getValue()) {
//      saveBeschreibung();
//      ClientsideSettings.getLogger().info("check 2 beschreibung gespeichert" + beschreibung.getName());
//    } else if (!check.getValue()) {
//      deleteBeschreibungInfo();
//      ClientsideSettings.getLogger().info("check 2 beschreibung gelöscht" + beschreibung.getName());
//    }



  }

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

  private void deleteBeschreibungInfo() {
    profilAttributTextBox.setText("");

    pbVerwaltung.getInfoById(getInfoId(), new AsyncCallback<Info>() {

      @Override
      public void onSuccess(Info result) {
        new Notification("Beschreibung " + result.getText() + " gelöscht", "success");
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
