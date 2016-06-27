package de.superteam2000.gwt.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimpleCheckBox;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Profil;

public class EigenschaftPanel extends BoxPanel implements ClickHandler, ChangeHandler {
  Profil profil = ClientsideSettings.getCurrentUser();
  SimpleCheckBox check1 = new SimpleCheckBox();
  SimpleCheckBox check2 = new SimpleCheckBox();
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

  public EigenschaftPanel(Auswahl a) {
    super(a);
  }

  public EigenschaftPanel(Beschreibung b) {
    super(b);
  }

  public EigenschaftPanel(Auswahl a, String selectedItem, boolean isNameListbox) {
    super(a, selectedItem, isNameListbox);
    check1.setStyleName("pure-checkbox");
  }

  public EigenschaftPanel(Beschreibung b, String text, boolean isNameTextbox) {
    super(b, text, isNameTextbox);
  }

  public EigenschaftPanel(Beschreibung b, boolean isNameTextbox, ArrayList<Info> infoListe) {
    super(b, isNameTextbox);
    this.add(check2);
    check2.addClickHandler(this);

    for (Info info : infoListe) {
      if (b.getId() == info.getEigenschaftId()) {
        check2.setValue(true);
        setText(info.getText());
      }
    }

    check2.setStyleName("pure-checkbox");
  }

  public EigenschaftPanel(Auswahl a, boolean isNameListbox, ArrayList<Info> infoListe) {
    super(a, isNameListbox);
    this.add(check1);
    check1.addClickHandler(this);
    profilAttributListBox.addChangeHandler(this);

    for (Info info : infoListe) {
      if (a.getId() == info.getEigenschaftId()) {
        check1.setValue(true);
        i = info;
        setInfoId(info.getId());
        setSelectedItem(info.getText());
      }
    }

    check1.setStyleName("pure-checkbox");
  }

  public EigenschaftPanel(String text) {
    super(text);
  }

  public EigenschaftPanel() {}

  @Override
  public void onClick(ClickEvent event) {
    if (check1.getValue()) {
      saveAuswahlSelection();


    } else if (check2.getValue()) {
      saveBeschreibung();

    } else {
      try {
        deleteAuswahlInfo();
        ClientsideSettings.getLogger().info("Info " + i.getText());

      } catch (Exception e) {
        e.printStackTrace();
      }
      try {
        deleteBeschreibungInfo();

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void deleteAuswahlInfo() {

    ClientsideSettings.getPartnerboerseVerwaltung().getInfoById(getInfoId(),
        new AsyncCallback<Info>() {
          @Override
          public void onSuccess(Info result) {
            new Notification("Auswahl " + result.getText() + " gelöscht", "success");
            ClientsideSettings.getLogger()
                .info("name= " + result.getText() + " id= " + result.getId() + "gelöscht");
            ClientsideSettings.getPartnerboerseVerwaltung().delete(result,
                new AsyncCallback<Void>() {

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
    ClientsideSettings.getPartnerboerseVerwaltung().getInfoByEigenschaftsId(beschreibung.getId(),
        new AsyncCallback<Info>() {

          @Override
          public void onSuccess(Info result) {
            new Notification("Beschreibung " + result.getText() + " gelöscht", "success");
            ClientsideSettings.getPartnerboerseVerwaltung().delete(result,
                new AsyncCallback<Void>() {

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
    ClientsideSettings.getPartnerboerseVerwaltung().createInfoFor(profil, auswahl,
        getSelectedItem(), new AsyncCallback<Info>() {

          @Override
          public void onSuccess(Info result) {
            i = result;
            EigenschaftPanel.this.setInfoId(result.getId());
            result.setId(EigenschaftPanel.this.getInfoId());
            new Notification("Auswahl " + result.getText() + " gespeichert", "success");
            ClientsideSettings.getLogger()
                .info("name= " + result.getText() + " id= " + result.getId() + " erstellt");
          }

          @Override
          public void onFailure(Throwable caught) {
            ClientsideSettings.getLogger().info("neeeiinn auswahl");

          }
        });
  }

  private void saveBeschreibung() {
    ClientsideSettings.getPartnerboerseVerwaltung().createInfoFor(profil, beschreibung,
        profilAttributTextBox.getText(), new AsyncCallback<Info>() {

          @Override
          public void onSuccess(Info result) {
            new Notification("Beschreibung " + result.getText() + " gespeichert", "success");
            ClientsideSettings.getLogger().info("juhu beschreibung");
          }

          @Override
          public void onFailure(Throwable caught) {
            ClientsideSettings.getLogger().info("neeeiinn beschreibung");

          }
        });
  }

  @Override
  public void onChange(ChangeEvent event) {
    if (check1.getValue()) {
      i.setText(getSelectedItem());
      new Notification("Eigenschaft auf " + i.getText() + " geändert!", "success");

      ClientsideSettings.getPartnerboerseVerwaltung().save(i, new AsyncCallback<Void>() {

        @Override
        public void onSuccess(Void result) {

        }

        @Override
        public void onFailure(Throwable caught) {

        }
      });
    }
  }

}
