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

  public static final String CLASSNAME = "pure-input-1-4";

  private int listBoxAuswahlId = 0;

  private ArrayList<String> alternativenListe = new ArrayList<>();

  public EigenschaftListBox(Auswahl a, String name) {
    this(a);
    setSelectedItemByText(name);
    this.setStyleName(CLASSNAME);

  }

  public EigenschaftListBox(Auswahl a) {
    setName(a.getName());
    addAuswahlAlternativen(a);
    this.setStyleName(CLASSNAME);
  }

  public EigenschaftListBox() {}

  
  public void addAuswahlAlternativen(Auswahl a) {
    alternativenListe = a.getAlternativen();
    for (String string : alternativenListe) {
      this.addItem(string);
    }
  }

  public void setSelectedItemByText(String name) {
    int i = alternativenListe.indexOf(name);
    setItemSelected(i, true);
  }

  public void setSelectedItemByTextForSPLB(String name) {
    int i = alternativenListe.indexOf(name);
    // Plus 1, weil dies SuchprofilListboxen in ihrer Auswahl auch "Keine Angabe" entahlten
    setItemSelected(i + 1, true);
  }

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
