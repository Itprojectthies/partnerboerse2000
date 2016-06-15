package de.superteam2000.gwt.client.gui;

import java.util.Date;

import com.google.gwt.user.client.ui.FlowPanel;

public class ProfilAttributListbox extends FlowPanel {



  EigenschaftListBox gebDatumTagListBox = new EigenschaftListBox();
  EigenschaftListBox gebDatumMonatListBox = new EigenschaftListBox();
  EigenschaftListBox gebDatumJahrListBox = new EigenschaftListBox();

  EigenschaftListBox koerpergroesseListBox = new EigenschaftListBox();
  EigenschaftListBox alterListBox = new EigenschaftListBox();


  public void createGroesseListBox() {
    this.add(new Label("Körpergröße"));
    for (int i = 140; i < 210; i++) {
      this.koerpergroesseListBox.addItem(String.valueOf(i));
    }

    this.koerpergroesseListBox.setName("Körpergröße");
    this.add(this.koerpergroesseListBox);
    this.koerpergroesseListBox.setEnabled(false);
    setStyleName("pure-control-group");
  }

  public void createAlterListbox() {
    for (int i = 18; i < 100; i++) {
      this.alterListBox.addItem(String.valueOf(i));
    }

    this.alterListBox.setName("Alter");
    this.add(this.alterListBox);
    setStyleName("pure-control-group");
  }

  public void createGebtaListobx() {
    this.add(new Label("Geburtstag"));
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
    
    setStyleName("pure-control-group");
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
      this.koerpergroesseListBox.setSelectedItemByIndex(0);
    } else {
      this.koerpergroesseListBox.setItemSelected(groesse - 140, true);
    }
  }

  public void setAlter(int alter) {
    if (alter == 0) {
      this.koerpergroesseListBox.setSelectedItemByIndex(0);
    } else {
      this.koerpergroesseListBox.setItemSelected(alter - 17, true);
    }
  }

  public void setEnable(boolean isEnabled) {
    this.koerpergroesseListBox.setEnabled(isEnabled);
    this.gebDatumTagListBox.setEnabled(isEnabled);
    this.gebDatumMonatListBox.setEnabled(isEnabled);
    this.gebDatumJahrListBox.setEnabled(isEnabled);
  }

}
