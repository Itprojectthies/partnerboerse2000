package de.superteam2000.gwt.client.gui;

import java.util.Date;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;

public class ProfilAttributeBoxPanel extends FlowPanel {

	private Auswahl auswahl = null;
	private Beschreibung beschreibung = null;

	ProfilAttributeListBox gebDatumTagListBox = new ProfilAttributeListBox();
	ProfilAttributeListBox gebDatumMonatListBox = new ProfilAttributeListBox();
	ProfilAttributeListBox gebDatumJahrListBox = new ProfilAttributeListBox();

	ProfilAttributeListBox koerpergroesseListBox = new ProfilAttributeListBox();

	ProfilAttributeListBox profilAttributListBox = new ProfilAttributeListBox();
	ProfilAttributeTextBox profilAttributTextBox = new ProfilAttributeTextBox();

	// Konstruktor für ein Auswahlobjekt mit vorselektiertem Item in der Listbox
	public ProfilAttributeBoxPanel(Auswahl a, String selectedItem, boolean isNameListbox) {
		this.auswahl = a;
		addLabelAuswahl(isNameListbox);
		this.profilAttributListBox = new ProfilAttributeListBox(a, selectedItem);
		this.add(profilAttributListBox);

		// set style name for entire widget
		setStyleName("CompositeProfilAttributeBox");

		// set style name for text box
		profilAttributListBox.setStyleName("prof-attrib-listbox");

	}

	public ProfilAttributeBoxPanel(Beschreibung b, String text, boolean isNameTextbox) {
		this.beschreibung = b;
		this.addLabelBeschreibung(isNameTextbox);
		this.profilAttributTextBox = new ProfilAttributeTextBox(b, text);
		this.add(this.profilAttributTextBox);

		// set style name for entire widget
		setStyleName("CompositeProfilAttributeBox");

	}

	public ProfilAttributeBoxPanel(Beschreibung b, boolean isNameTextbox) {
		this.beschreibung = b;
		addLabelBeschreibung(isNameTextbox);
		this.profilAttributTextBox = new ProfilAttributeTextBox(b);
		this.add(this.profilAttributTextBox);

		// set style name for entire widget
		setStyleName("CompositeProfilAttributeBox");

	}

	public ProfilAttributeBoxPanel(Auswahl a, boolean isNameListbox) {
		this.auswahl = a;
		addLabelAuswahl(isNameListbox);
		this.profilAttributListBox = new ProfilAttributeListBox(a);
		
		// set style name for entire widget
		this.add(profilAttributListBox);
		setStyleName("CompositeProfilAttributeBox");
	}

	public ProfilAttributeBoxPanel(String text) {
		this.add(new HTML(text));
	}

	public ProfilAttributeBoxPanel() {
	}

	public void addLabelAuswahl(boolean isNameListbox) {
		if (!isNameListbox) {
			this.add(new HTML(this.auswahl.getBeschreibungstext()));
		} else {
			this.add(new HTML(this.auswahl.getName()));
		}
	}

	public void addLabelBeschreibung(boolean isNameTextbox) {
		if (!isNameTextbox) {
			this.add(new HTML(this.beschreibung.getBeschreibungstext()));
		} else {
			this.add(new HTML(this.beschreibung.getName()));
		}
	}

	public void createGroesseListBox() {
		for (int i = 140; i < 210; i++) {
			profilAttributListBox.addItem(String.valueOf(i));
		}
		
		this.profilAttributListBox.setName("Körpergröße");
		this.add(this.profilAttributListBox);
		setStyleName("CompositeProfilAttributeBox");
	}

	public void createGebtaListobx() {
		for (int i = 1; i <= 31; i++) {
			gebDatumTagListBox.addItem(String.valueOf(i));
		}
		
		for (int i = 1; i <= 12; i++) {
			gebDatumMonatListBox.addItem(String.valueOf(i));
		}
		
		for (int i = 1900; i <= 2000; i++) {
			gebDatumJahrListBox.addItem(String.valueOf(i));
		}

		this.gebDatumTagListBox.setName("GeburtstagTag");
		this.gebDatumMonatListBox.setName("GeburtstagMonat");
		this.gebDatumJahrListBox.setName("GeburtstagJahr");
		
		this.add(this.gebDatumTagListBox);
		this.add(this.gebDatumMonatListBox);
		this.add(this.gebDatumJahrListBox);
		
		setStyleName("CompositeProfilAttributeBox");
	}

	public void setGebtag(Date date) {
		String dateString = DateTimeFormat.getFormat("yyyy-MM-dd").format(date);
		String[] gebDaten = dateString.split("-");
		this.gebDatumTagListBox.setItemSelected(Integer.valueOf(gebDaten[2]) - 1, true);
		this.gebDatumMonatListBox.setItemSelected(Integer.valueOf(gebDaten[1]) - 1, true);
		this.gebDatumJahrListBox.setItemSelected(Integer.valueOf(gebDaten[0]) - 1900, true);
	}

	public void setGroesse(int groesse) {
		this.profilAttributListBox.setItemSelected(groesse - 140, true);
	}

	public void setEnable(boolean isEnabled) {
		this.profilAttributTextBox.setEnabled(isEnabled);
		this.profilAttributListBox.setEnabled(isEnabled);
		this.koerpergroesseListBox.setEnabled(isEnabled);
		this.gebDatumTagListBox.setEnabled(isEnabled);
		this.gebDatumMonatListBox.setEnabled(isEnabled);
		this.gebDatumJahrListBox.setEnabled(isEnabled);
	}
	
	public void addKeineAngabenItem () {
		this.profilAttributListBox.insertItem("Keine Angabe", 0);
		this.profilAttributListBox.setSelectedIndex(0);
	}
	
	public void setName (String name) {
		this.profilAttributListBox.setName(name);
	}

}
