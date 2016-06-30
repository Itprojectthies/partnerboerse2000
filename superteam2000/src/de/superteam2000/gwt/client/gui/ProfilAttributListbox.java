package de.superteam2000.gwt.client.gui;

import java.util.Date;

/**
 * Die Klasse ProfilAttributListbox dient zur Erstellung von Listboxen für die Profilattribute
 * Geburtstag, Alter und Körpergröße.
 * 
 * @author Volz
 *
 */
public class ProfilAttributListbox extends BoxPanel {
  /*
   * Alle notwendigen Instanzvariablen werden deklariert
   */

  EigenschaftListBox gebDatumTagListBox = new EigenschaftListBox();
  EigenschaftListBox gebDatumMonatListBox = new EigenschaftListBox();
  EigenschaftListBox gebDatumJahrListBox = new EigenschaftListBox();

  EigenschaftListBox koerpergroesseListBox = new EigenschaftListBox();
  EigenschaftListBox alterListBox = new EigenschaftListBox();

  /**
   * Erstellt eine "leere" ProfilAttributListbox mit einem Label
   * 
   * @param name Name der ProfilAttributListbox
   */
  public ProfilAttributListbox(String name) {
    this.add(new Label(name));
  }

  public ProfilAttributListbox() {}

  /**
   * Erstellt eine ProfilAttributListbox mit der Auwahl von Körpergrößen von 140 cm bis 210 cm.
   */
  public void createGroesseListBox(String name) {
    this.add(new Label(name));
    for (int i = 140; i < 210; i++) {
      profilAttributListBox.addItem(String.valueOf(i));
    }
    profilAttributListBox.setStyleName("pure-input-1-4");
    profilAttributListBox.setName("Körpergröße");
    this.add(profilAttributListBox);
    profilAttributListBox.setEnabled(false);
    this.setStyleName("pure-control-group");
  }

  /**
   * Erstellt eine ProfilAttributListbox mit einer Alterauswahl von 18 bis 100 Jahren.
   */
  public void createAlterListbox() {
    for (int i = 18; i < 100; i++) {
      profilAttributListBox.addItem(String.valueOf(i));
    }
    profilAttributListBox.setStyleName("pure-input-1-4");
    profilAttributListBox.setName("Alter");
    this.add(profilAttributListBox);
    this.setStyleName("pure-control-group");
  }

  /**
   * Erstellt 3 ProfilAttributListboxen, die zur Selektion eines Genurtstages dienen.
   */
  public void createGebtaListobx(String name) {
    this.add(new Label(name));
    for (int i = 1; i <= 31; i++) {
      gebDatumTagListBox.addItem(String.valueOf(i));
    }

    for (int i = 1; i <= 12; i++) {
      gebDatumMonatListBox.addItem(String.valueOf(i));
    }

    for (int i = 1900; i <= 2000; i++) {
      gebDatumJahrListBox.addItem(String.valueOf(i));
    }
    gebDatumTagListBox.setEnabled(false);
    gebDatumMonatListBox.setEnabled(false);
    gebDatumJahrListBox.setEnabled(false);

    gebDatumTagListBox.setName("GeburtstagTag");
    gebDatumMonatListBox.setName("GeburtstagMonat");
    gebDatumJahrListBox.setName("GeburtstagJahr");

    this.add(gebDatumTagListBox);
    this.add(gebDatumMonatListBox);
    this.add(gebDatumJahrListBox);

    this.setStyleName("pure-control-group");
  }
  
  public void setGebtag(Date date) {
    String dateString = DateTimeFormat.getFormat("yyyy-MM-dd").format(date);
    String[] gebDaten = dateString.split("-");
    gebDatumTagListBox.setItemSelected(Integer.valueOf(gebDaten[2]) - 1, true);
    gebDatumMonatListBox.setItemSelected(Integer.valueOf(gebDaten[1]) - 1, true);
    gebDatumJahrListBox.setItemSelected(Integer.valueOf(gebDaten[0]) - 1900, true);
  }

  @Override
  public void setGroesse(int groesse) {
    if (groesse == 1) {
      profilAttributListBox.setSelectedItemByIndex(0);
    } else {
      profilAttributListBox.setItemSelected(groesse - 140, true);
    }
  }

  @Override
  public void setAlter(int alter) {
    if (alter == 0) {
      profilAttributListBox.setSelectedItemByIndex(0);
    } else {
      profilAttributListBox.setItemSelected(alter - 17, true);
    }
  }

  /*
   * Macht die Boxen selektierbar.
   */
  @Override
  public void setEnable(boolean isEnabled) {
    profilAttributListBox.setEnabled(isEnabled);
    gebDatumTagListBox.setEnabled(isEnabled);
    gebDatumMonatListBox.setEnabled(isEnabled);
    gebDatumJahrListBox.setEnabled(isEnabled);
  }
  
  /**
   * Für der Listbox eine "Keine Angabe" Auswahl hinzu
   */
  @Override
  public void addKeineAngabenItem() {
    profilAttributListBox.insertItem("Keine Angabe", 0);
    profilAttributListBox.setSelectedIndex(0);

  }
}
