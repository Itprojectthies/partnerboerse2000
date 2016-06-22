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

public class EigenschaftPanel extends BoxPanel implements ClickHandler, ChangeHandler {

  SimpleCheckBox check1 = new SimpleCheckBox();
  SimpleCheckBox check2 = new SimpleCheckBox();

  public EigenschaftPanel(Auswahl a) {
    super(a);
    // TODO Auto-generated constructor stub
  }

  public EigenschaftPanel(Beschreibung b) {
    super(b);
    // TODO Auto-generated constructor stub
  }

  public EigenschaftPanel(Auswahl a, String selectedItem, boolean isNameListbox) {
    super(a, selectedItem, isNameListbox);
    check1.setStyleName("pure-checkbox");
    // TODO Auto-generated constructor stub
  }

  public EigenschaftPanel(Beschreibung b, String text, boolean isNameTextbox) {
    super(b, text, isNameTextbox);
    // TODO Auto-generated constructor stub
  }

  public EigenschaftPanel(Beschreibung b, boolean isNameTextbox, ArrayList<Info> infoListe) {
    super(b, isNameTextbox);
    this.add(check2);
    check2.addClickHandler(this);
    for (Info info : infoListe) {
      if (b.getId() == info.getEigenschaftId()) {
        check2.setValue(true);
        this.setText(info.getText());
      }
    }

    check2.setStyleName("pure-checkbox");
  }

  public EigenschaftPanel(Auswahl a, boolean isNameListbox, ArrayList<Info> infoListe) {
    super(a, isNameListbox);
    this.add(check1);
    check1.addClickHandler(this);
    this.profilAttributListBox.addChangeHandler(this);
    for (Info info : infoListe) {
      if (a.getId() == info.getEigenschaftId()) {
        check1.setValue(true);
        this.setSelectedItem(info.getText());
      }
    }

    check1.setStyleName("pure-checkbox");
    // TODO Auto-generated constructor stub
  }

  public EigenschaftPanel(String text) {
    super(text);
    // TODO Auto-generated constructor stub
  }

  public EigenschaftPanel() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void onClick(ClickEvent event) {
    if (this.check1.getValue()) {
      saveAuswahlSelection();
      
    } else if (this.check2.getValue()) {
      saveBeschreibung();
     
    } else {
      try {
        deleteAuswahlInfo();
        
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      try {
        deleteBeschreibungInfo();
        
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  private void deleteAuswahlInfo() {
    ClientsideSettings.getPartnerboerseVerwaltung().getInfoByEigenschaftsId(auswahl.getId(),
        new AsyncCallback<Info>() {

          @Override
          public void onSuccess(Info result) {
            Notification n1 = new Notification("Auswahl "+ result.getText() +" gelöscht", "success");
            ClientsideSettings.getPartnerboerseVerwaltung().delete(result,
                new AsyncCallback<Void>() {

                  @Override
                  public void onSuccess(Void result) {
                    
                    ClientsideSettings.getLogger().info("Auswahl Info gelöscht");
                  }

                  @Override
                  public void onFailure(Throwable caught) {
                    ClientsideSettings.getLogger().severe("Fehler bei Auswahl Info löschen");
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
    ClientsideSettings.getPartnerboerseVerwaltung().getInfoByEigenschaftsId(beschreibung.getId(),
        new AsyncCallback<Info>() {

          @Override
          public void onSuccess(Info result) {
            Notification n1 = new Notification("Beschreibung " + result.getText() + " gelöscht", "success");
            ClientsideSettings.getPartnerboerseVerwaltung().delete(result,
                new AsyncCallback<Void>() {

                  @Override
                  public void onSuccess(Void result) {
                    
                    ClientsideSettings.getLogger().info("beschreibungs Info gelöscht");
                  }

                  @Override
                  public void onFailure(Throwable caught) {
                    ClientsideSettings.getLogger().severe("Fehler bei beschreibungs Info löschen");
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
    ClientsideSettings.getPartnerboerseVerwaltung().createInfoFor(auswahl, this.getSelectedItem(),
        new AsyncCallback<Info>() {

          @Override
          public void onSuccess(Info result) {
            Notification n1 = new Notification("Auswahl "+ result.getText() +" gespeichert", "success");
            ClientsideSettings.getLogger().info("juhu auswahl");
          }

          @Override
          public void onFailure(Throwable caught) {
            ClientsideSettings.getLogger().info("neeeiinn auswahl");

          }
        });
  }

  private void saveBeschreibung() {
    ClientsideSettings.getPartnerboerseVerwaltung().createInfoFor(beschreibung,
        this.profilAttributTextBox.getText(), new AsyncCallback<Info>() {

          @Override
          public void onSuccess(Info result) {
            Notification n1 = new Notification("Beschreibung " + result.getText() + " gespeichert", "success");
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
    saveAuswahlSelection();
  }

}
