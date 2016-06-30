package de.superteam2000.gwt.client.gui;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.ListBox;

import de.superteam2000.gwt.shared.bo.Auswahl;

/**
 * Die Klasse EigenschaftListBox erweitert Listobx. Sie dient dazu Auswahleigenschaften in
 * Listeboxen darzustellen.
 *
 * @author Volz
 *
 */
public class EigenschaftListBox extends ListBox {
  /*
   * Alle notwendigen Instanzvariablen werden deklariert
   */
  
  public static final String CLASSNAME = "pure-input-1-4";
  private int listBoxAuswahlId = 0;
  private ArrayList<String> alternativenListe = new ArrayList<>();

  /**
   * Konstruktor für die Listbox einer Auswahleignschaft
   * 
   * @param a Auswahleigenschaft
   * @param name Vorselektierte Auswahleigenschaft
   */
  public EigenschaftListBox(Auswahl a, String name) {
    this(a);
    setSelectedItemByText(name);
    this.setStyleName(CLASSNAME);

  }

  /**
   * Konstruktor für die Listbox einer Auswahleignschaft
   * 
   * @param a Auswahleigenschaft
   */
  public EigenschaftListBox(Auswahl a) {
    setName(a.getName());
    addAuswahlAlternativen(a);
    this.setStyleName(CLASSNAME);
  }

  public EigenschaftListBox() {}

  /**
   * Fügt die Auswahlalternativen zur Listbox hinzu
   * 
   * @param a Auswahleigenschaft
   */
  public void addAuswahlAlternativen(Auswahl a) {
    alternativenListe = a.getAlternativen();
    for (String string : alternativenListe) {
      this.addItem(string);
    }
  }

  /**
   * Setzte eine beliebige Auswahlalternative einer Listbox über den Alternativennamen.
   * 
   * @param name Name der Alternative
   */
  public void setSelectedItemByText(String name) {
    int i = alternativenListe.indexOf(name);
    setItemSelected(i, true);
  }

  /**
   * Hilfsmethode, die die Auswahlalternative einer Listbox für ein Suchprofil setzt.
   * 
   * Der Itemindex wird um 1 erhöht, weil dies SuchprofilListboxen in ihrer Auswahl auch
   * "Keine Angabe" entahlten
   * 
   * @param name Name der Alternative
   */
  public void setSelectedItemByTextForSPLB(String name) {
    int i = alternativenListe.indexOf(name);
    setItemSelected(i + 1, true);
  }
  /**
   *  Setzte eine beliebige Auswahlalternative einer Listbox über den Index.
   *  
   * @param i Index der Alternative
   */
  public void setSelectedItemByIndex(int i) {
    setItemSelected(i, true);
  }

  public int getListBoxAuswahlId() {
    return listBoxAuswahlId;
  }

  public void setListBoxAuswahlId(int listBoxAuswahlId) {
    this.listBoxAuswahlId = listBoxAuswahlId;
  }

}
