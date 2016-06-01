package de.superteam2000.gwt.client.gui;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;

import de.superteam2000.gwt.shared.bo.Auswahl;

public class ProfilAttributeListBox extends ListBox {

	public static final String CLASSNAME = "profil-attribute-listbox";

	private ArrayList<String> alternativenListe = new ArrayList<>();

	public ProfilAttributeListBox(Auswahl a, String name) {
		this(a);
		this.setSelectedItdem(name);
		setStyleName(CLASSNAME);
	}

	public ProfilAttributeListBox(Auswahl a) {
		this.setName(a.getName());
		this.addAuswahlAlternativen(a);
		setStyleName(CLASSNAME);
	}
	
	public ProfilAttributeListBox() {
	}

	public void addAuswahlAlternativen(Auswahl a) {
		this.alternativenListe = a.getAlternativen();
		for (String string : alternativenListe) {
			this.addItem(string);
		}
	}

	public void setSelectedItdem(String name) {
		int i = this.alternativenListe.indexOf(name);
		this.setItemSelected(i, true);
	}
	
}
