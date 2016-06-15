package de.superteam2000.gwt.client.gui;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.ListBox;

import de.superteam2000.gwt.shared.bo.Auswahl;

public class EigenschaftListBox extends ListBox {

	public static final String CLASSNAME = "pure-input-1-4";

	private int listBoxAuswahlId = 0;
	
	private ArrayList<String> alternativenListe = new ArrayList<>();

	public EigenschaftListBox(Auswahl a, String name) {
		this(a);
		this.setSelectedItemByText(name);
		setStyleName(CLASSNAME);
	}

	public EigenschaftListBox(Auswahl a) {
		this.setName(a.getName());
		this.addAuswahlAlternativen(a);
		setStyleName(CLASSNAME);
	}
	
	public EigenschaftListBox() {
	}

	public void addAuswahlAlternativen(Auswahl a) {
		this.alternativenListe = a.getAlternativen();
		for (String string : alternativenListe) {
			this.addItem(string);
		}
	}

	public void setSelectedItemByText(String name) {
		int i = this.alternativenListe.indexOf(name);
		this.setItemSelected(i, true);
	}
	
	public void setSelectedItemByTextForSPLB(String name) {
		int i = this.alternativenListe.indexOf(name);
		// Plus 1, weil dies SuchprofilListboxen in ihrer Auswahl auch "Keine Angabe" entahlten
		this.setItemSelected(i + 1, true);
	}
	
	public void setSelectedItemByIndex(int i) {
		this.setItemSelected(i, true);
	}


	public int getListBoxAuswahlId() {
		return listBoxAuswahlId;
	}

	public void setListBoxAuswahlId(int listBoxAuswahlId) {
		this.listBoxAuswahlId = listBoxAuswahlId;
	}
	
}
