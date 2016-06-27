package de.superteam2000.gwt.client.gui;

import java.util.Date;

import de.superteam2000.gwt.client.ClientsideSettings;

public class ProfilAttributListbox extends BoxPanel {



  EigenschaftListBox gebDatumTagListBox = new EigenschaftListBox();
  EigenschaftListBox gebDatumMonatListBox = new EigenschaftListBox();
  EigenschaftListBox gebDatumJahrListBox = new EigenschaftListBox();

  EigenschaftListBox koerpergroesseListBox = new EigenschaftListBox();
  EigenschaftListBox alterListBox = new EigenschaftListBox();



  public ProfilAttributListbox(String name) {
    this.add(new Label(name));

  }

  public ProfilAttributListbox() {}

  public void createGroesseListBox(String name) {
    this.add(new Label(name));
    for (int i = 140; i < 210; i++) {
      this.profilAttributListBox.addItem(String.valueOf(i));
    }
    this.profilAttributListBox.setStyleName("pure-input-1-4");
    this.profilAttributListBox.setName("Körpergröße");
    this.add(this.profilAttributListBox);
    this.profilAttributListBox.setEnabled(false);
    setStyleName("pure-control-group-1");
  }

  public void createAlterListbox() {
    for (int i = 18; i < 100; i++) {
      this.profilAttributListBox.addItem(String.valueOf(i));
    }
    this.profilAttributListBox.setStyleName("pure-input-1-4");
    this.profilAttributListBox.setName("Alter");
    this.add(this.profilAttributListBox);
    setStyleName("pure-control-group-1");
  }

  public void createGebtaListobx(String name) {
    this.add(new Label(name));
    for (int i = 1; i <= 31; i++) {
      this.gebDatumTagListBox.addItem(String.valueOf(i));
    }

    for (int i = 1; i <= 12; i++) {
      this.gebDatumMonatListBox.addItem(String.valueOf(i));
    }

    for (int i = 1900; i <= 2000; i++) {
      this.gebDatumJahrListBox.addItem(String.valueOf(i));
    }
    this.gebDatumTagListBox.setEnabled(false);
    this.gebDatumMonatListBox.setEnabled(false);
    this.gebDatumJahrListBox.setEnabled(false);

    this.gebDatumTagListBox.setName("GeburtstagTag");
    this.gebDatumMonatListBox.setName("GeburtstagMonat");
    this.gebDatumJahrListBox.setName("GeburtstagJahr");

    this.add(this.gebDatumTagListBox);
    this.add(this.gebDatumMonatListBox);
    this.add(this.gebDatumJahrListBox);

    setStyleName("pure-control-group-1");
  }

  public void setGebtag(Date date) {
    String dateString = DateTimeFormat.getFormat("yyyy-MM-dd").format(date);
    String[] gebDaten = dateString.split("-");
    this.gebDatumTagListBox.setItemSelected(Integer.valueOf(gebDaten[2]) - 1, true);
    this.gebDatumMonatListBox.setItemSelected(Integer.valueOf(gebDaten[1]) - 1, true);
    this.gebDatumJahrListBox.setItemSelected(Integer.valueOf(gebDaten[0]) - 1900, true);
  }

  public void setGroesse(int groesse) {
    if (groesse == 1) {
      this.profilAttributListBox.setSelectedItemByIndex(0);
    } else {
      this.profilAttributListBox.setItemSelected(groesse - 140, true);
    }
  }

  public void setAlter(int alter) {
    if (alter == 0) {
      this.profilAttributListBox.setSelectedItemByIndex(0);
    } else {
      this.profilAttributListBox.setItemSelected(alter - 17, true);
    }
  }

  public void setEnable(boolean isEnabled) {
    this.profilAttributListBox.setEnabled(isEnabled);
    this.gebDatumTagListBox.setEnabled(isEnabled);
    this.gebDatumMonatListBox.setEnabled(isEnabled);
    this.gebDatumJahrListBox.setEnabled(isEnabled);
  }

  public void addKeineAngabenItem() {
    this.profilAttributListBox.insertItem("Keine Angabe", 0);
    this.profilAttributListBox.setSelectedIndex(0);

  }
}
