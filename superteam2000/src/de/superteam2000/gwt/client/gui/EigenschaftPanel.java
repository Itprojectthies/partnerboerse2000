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
    return this.infoId;
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
    this.check1.setStyleName("pure-checkbox");
  }

  public EigenschaftPanel(Beschreibung b, String text, boolean isNameTextbox) {
    super(b, text, isNameTextbox);
  }

  public EigenschaftPanel(Beschreibung b, boolean isNameTextbox, ArrayList<Info> infoListe) {
    super(b, isNameTextbox);
    this.add(this.check2);
    this.check2.addClickHandler(this);

    for (Info info : infoListe) {
      if (b.getId() == info.getEigenschaftId()) {
        this.check2.setValue(true);
        this.setText(info.getText());
      }
    }

    this.check2.setStyleName("pure-checkbox");
  }

  public EigenschaftPanel(Auswahl a, boolean isNameListbox, ArrayList<Info> infoListe) {
    super(a, isNameListbox);
    this.add(this.check1);
    this.check1.addClickHandler(this);
    this.profilAttributListBox.addChangeHandler(this);

    for (Info info : infoListe) {
      if (a.getId() == info.getEigenschaftId()) {
        this.check1.setValue(true);
        this.i = info;
        this.setInfoId(info.getId());
        this.setSelectedItem(info.getText());
      }
    }

    this.check1.setStyleName("pure-checkbox");
  }

  public EigenschaftPanel(String text) {
    super(text);
  }

  public EigenschaftPanel() {}

  @Override
  public void onClick(ClickEvent event) {
    if (this.check1.getValue()) {
      this.saveAuswahlSelection();


    } else if (this.check2.getValue()) {
      this.saveBeschreibung();

    } else {
      try {
        this.deleteAuswahlInfo();
        ClientsideSettings.getLogger().info("Info " + this.i.getText());

      } catch (Exception e) {
        e.printStackTrace();
      }
      try {
        this.deleteBeschreibungInfo();

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void deleteAuswahlInfo() {

    ClientsideSettings.getPartnerboerseVerwaltung().getInfoById(this.getInfoId(),
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
    this.profilAttributTextBox.setText("");
    ClientsideSettings.getPartnerboerseVerwaltung()
        .getInfoByEigenschaftsId(this.beschreibung.getId(), new AsyncCallback<Info>() {

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
    ClientsideSettings.getPartnerboerseVerwaltung().createInfoFor(this.profil, this.auswahl,
        this.getSelectedItem(), new AsyncCallback<Info>() {

          @Override
          public void onSuccess(Info result) {
            EigenschaftPanel.this.i = result;
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
    ClientsideSettings.getPartnerboerseVerwaltung().createInfoFor(this.profil, this.beschreibung,
        this.profilAttributTextBox.getText(), new AsyncCallback<Info>() {

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
    if (this.check1.getValue()) {
      this.i.setText(this.getSelectedItem());
      new Notification("Eigenschaft auf " + this.i.getText() + " geändert!", "success");

      ClientsideSettings.getPartnerboerseVerwaltung().save(this.i, new AsyncCallback<Void>() {

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
