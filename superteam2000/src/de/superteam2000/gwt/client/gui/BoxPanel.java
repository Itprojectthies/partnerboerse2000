package de.superteam2000.gwt.client.gui;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;

/**
 * Die Klasse BoxPanel ist ein CustomFlowPanel, welches Info-Objekte mit ihrem Namen und einer
 * Auswahlliste bzw. eines Textfeldes darstellt.
 * 
 * Das BoxPanel besteht entwerder aus einer EigenschaftsListbox + Label oder einer
 * ProfilAttributTextBox + Label
 *
 * @author Volz
 *
 */
public class BoxPanel extends FlowPanel {
  /*
   * Alle notwendigen Instanzvariablen werden deklariert
   */

  Auswahl auswahl = null;
  Beschreibung beschreibung = null;

  EigenschaftListBox profilAttributListBox = new EigenschaftListBox();
  ProfilAttributTextBox profilAttributTextBox = new ProfilAttributTextBox();

  /**
   * Konstruktor für eine Auswahleigenschaft
   * 
   * @param a Auswahleigenschaft
   */
  public BoxPanel(Auswahl a) {
    auswahl = a;
    profilAttributListBox = new EigenschaftListBox(a);
    this.add(profilAttributListBox);

    // set style name for entire widget
    this.setStyleName("pure-control-group-1");

    // set style name for text box
    profilAttributListBox.setStyleName("pure-input-1-4");

  }

  /**
   * Konstruktor für eine Beschreibungseigenschaft
   * 
   * @param b Beschreibungseigenschaft
   */
  public BoxPanel(Beschreibung b) {
    beschreibung = b;
    profilAttributTextBox = new ProfilAttributTextBox(b);
    this.add(profilAttributTextBox);

    // set style name for entire widget
    this.setStyleName("pure-control-group-1");

    // set style name for text box
    profilAttributTextBox.setStyleName("pure-input-1-4");

  }

  /**
   * 
   * @param b Beschreibungseigenschaft
   * @param isNameTextbox Variable die angibt ob der Beschreibungstext oder der Name der Eigenschaft
   *        angezeigt werden soll
   */
  public BoxPanel(Beschreibung b, boolean isNameTextbox) {
    beschreibung = b;
    profilAttributTextBox = new ProfilAttributTextBox(b);
    addLabelBeschreibung(isNameTextbox);
    this.add(profilAttributTextBox);

    // set style name for entire widget
    this.setStyleName("pure-control-group-1");
    // set style name for text box
    profilAttributTextBox.setStyleName("pure-input-1-4");

  }

  /**
   * Konstruktor für eine Auswahleigenschaft
   * 
   * @param a Auswahleigenschaft
   * @param isNameTextbox Variable die angibt ob der Beschreibungstext oder der Name der Eigenschaft
   *        angezeigt werden soll
   */
  public BoxPanel(Auswahl a, boolean isNameListbox) {
    auswahl = a;
    profilAttributListBox = new EigenschaftListBox(a);
    addLabelAuswahl(isNameListbox);

    // set style name for entire widget
    this.add(profilAttributListBox);
    this.setStyleName("pure-control-group-1");
  }


  /**
   * Konstruktor für eine Auswahleigenschaft
   * 
   * @param a Auswahleigenschaft
   * @param isNameTextbox Variable die angibt ob der Beschreibungstext oder der Name der Eigenschaft
   *        angezeigt werden soll
   * @param selectedItem Vorselektiertes Item, das die Listbox anzeigen soll
   */
  public BoxPanel(Auswahl a, String selectedItem, boolean isNameListbox) {
    auswahl = a;
    addLabelAuswahl(isNameListbox);
    profilAttributListBox = new EigenschaftListBox(a, selectedItem);
    this.add(profilAttributListBox);

    // set style name for entire widget
    this.setStyleName("pure-control-group-1");
  }

  /**
   * Konstruktor für eine Auswahleigenschaft
   * 
   * @param b Beschreibungseigenschaft
   * @param isNameTextbox Variable die angibt ob der Beschreibungstext oder der Name der Eigenschaft
   *        angezeigt werden soll
   * @param text Text der in der Textbox stehen soll
   */
  public BoxPanel(Beschreibung b, String text, boolean isNameTextbox) {
    beschreibung = b;
    addLabelBeschreibung(isNameTextbox);
    profilAttributTextBox = new ProfilAttributTextBox(b, text);
    this.add(profilAttributTextBox);

    // set style name for entire widget
    this.setStyleName("pure-control-group-1");
  }

  /**
   * Konstruktor einer "leeren" BoxPanel
   * 
   * @param text Überschrift des BoxPanels
   */
  public BoxPanel(String text) {
    this.add(new HTML(text));
  }

  /**
   * Default
   * 
   */
  public BoxPanel() {}

  /**
   * Fügt Beschreibungen oder den Namen einer Auswahleigenschaft zum BoxPanel hinzu
   * 
   * @param isNameListbox Gibt an ob der Name oder der Beschreibungstext angezeigt werden soll
   */
  public void addLabelAuswahl(boolean isNameListbox) {
    if (!isNameListbox) {
      this.add(new Label(auswahl.getBeschreibungstext()));
    } else {
      this.add(new Label(auswahl.getName()));
    }
  }

  /**
   * Fügt Beschreibungen oder den Namen einer Beschreibungseigenschaft zum BoxPanel hinzu
   * 
   * @param isNameListbox Gibt an ob der Name oder der Beschreibungstext angezeigt werden soll
   */
  public void addLabelBeschreibung(boolean isNameTextbox) {
    if (!isNameTextbox) {
      this.add(new Label(beschreibung.getBeschreibungstext()));
    } else {
      this.add(new Label(beschreibung.getName()));
    }
  }

  /**
   * Macht die Listbox klickbar
   * 
   * @param isEnabled
   */
  public void setEnable(boolean isEnabled) {
    profilAttributTextBox.setEnabled(isEnabled);
    profilAttributListBox.setEnabled(isEnabled);
  }

  /**
   * Für der Listbox eine "Keine Angabe" Auswahl hinzu
   */
  public void addKeineAngabenItem() {
    profilAttributListBox.insertItem("Keine Angabe", 0);
    profilAttributListBox.setSelectedIndex(0);
  }


  public void setName(String name) {
    profilAttributListBox.setName(name);
  }

  public String getName() {
    return profilAttributListBox.getName();
  }

  public void setId(int id) {
    profilAttributListBox.setListBoxAuswahlId(id);
  }

  public int getId() {
    return profilAttributListBox.getListBoxAuswahlId();
  }

  public Auswahl getAuswahl() {
    return auswahl;
  }

  public void setText(String text) {
    this.profilAttributTextBox.setText(text);
  }

  public String getText() {
    return this.profilAttributTextBox.getText();
  }

  public Beschreibung getBeschreibung() {
    return beschreibung;
  }

  public String getSelectedItem() {
    return profilAttributListBox.getSelectedItemText();
  }


  public void setSelectedItem(String text) {
    profilAttributListBox.setSelectedItemByText(text);
  }

  public void setSelectedItemForSP(String text) {
    profilAttributListBox.setSelectedItemByTextForSPLB(text);
  }

  public void setSelectedItemByIndex(int i) {
    profilAttributListBox.setSelectedItemByIndex(i);
  }

  public void setGroesse(int groesse) {}

  public void setAlter(int alter) {}


}
