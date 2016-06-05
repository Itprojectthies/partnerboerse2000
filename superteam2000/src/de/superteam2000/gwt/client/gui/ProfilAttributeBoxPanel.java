package de.superteam2000.gwt.client.gui;

import java.util.Date;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;

public class ProfilAttributeBoxPanel extends FlowPanel {

	Auswahl auswahl = null;
	Beschreibung beschreibung = null;

	ProfilAttributeListBox gebDatumTagListBox = new ProfilAttributeListBox();
	ProfilAttributeListBox gebDatumMonatListBox = new ProfilAttributeListBox();
	ProfilAttributeListBox gebDatumJahrListBox = new ProfilAttributeListBox();

	ProfilAttributeListBox koerpergroesseListBox = new ProfilAttributeListBox();

	ProfilAttributeListBox profilAttributListBox = new ProfilAttributeListBox();
	ProfilAttributeTextBox profilAttributTextBox = new ProfilAttributeTextBox();

	public ProfilAttributeBoxPanel(Auswahl a) {
		this.auswahl = a;
		this.profilAttributListBox = new ProfilAttributeListBox(a);
		this.add(profilAttributListBox);

		// set style name for entire widget
		setStyleName("CompositeProfilAttributeBox");

		// set style name for text box
		profilAttributListBox.setStyleName("prof-attrib-listbox");

	}
	
	public ProfilAttributeBoxPanel(Beschreibung b) {
		this.beschreibung = b;
		this.profilAttributTextBox = new ProfilAttributeTextBox(b);
		this.add(profilAttributTextBox);

		// set style name for entire widget
		setStyleName("CompositeProfilAttributeBox");

		// set style name for text box
		profilAttributTextBox.setStyleName("prof-attrib-textbox");

	}
	
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

	public void createAlterListbox() {
		for (int i = 18; i < 100; i++) {
			this.profilAttributListBox.addItem(String.valueOf(i));
		}

		this.profilAttributListBox.setName("Alter");
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
		this.profilAttributTextBox.setEnabled(isEnabled);
		this.profilAttributListBox.setEnabled(isEnabled);
		this.koerpergroesseListBox.setEnabled(isEnabled);
		this.gebDatumTagListBox.setEnabled(isEnabled);
		this.gebDatumMonatListBox.setEnabled(isEnabled);
		this.gebDatumJahrListBox.setEnabled(isEnabled);
	}

	public void addKeineAngabenItem() {
		this.profilAttributListBox.insertItem("Keine Angabe", 0);
		this.profilAttributListBox.setSelectedIndex(0);
	}

	public void setName(String name) {
		this.profilAttributListBox.setName(name);
	}

	public String getName() {
		return this.profilAttributListBox.getName();
	}

	public void setId(int id) {
		this.profilAttributListBox.setListBoxAuswahlId(id);
	}

	public int getId() {
		return this.profilAttributListBox.getListBoxAuswahlId();
	}

	public Auswahl getAuswahl() {
		return this.auswahl;
	}

	public void setText(String text) {
		this.profilAttributTextBox.setText(text);
	}
	
	public String getText() {
		return this.profilAttributTextBox.getText();
	}
	
	public Beschreibung getBeschreibung() {
		return this.beschreibung;
	}

	public String getSelectedItem() {
		return this.profilAttributListBox.getSelectedItemText();
	}

	public void setSelectedItem(String text) {
		this.profilAttributListBox.setSelectedItemByText(text);
	}

	public void setSelectedItemForSP(String text) {
		this.profilAttributListBox.setSelectedItemByTextForSPLB(text);
	}

	public void setSelectedItemByIndex(int i) {
		this.profilAttributListBox.setSelectedItemByIndex(i);
	}
}
