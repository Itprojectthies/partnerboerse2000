package de.superteam2000.gwt.client.gui;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;

public class BoxPanel extends FlowPanel {

	Auswahl auswahl = null;
	Beschreibung beschreibung = null;

	EigenschaftListBox profilAttributListBox = new EigenschaftListBox();
	ProfilAttributTextBox profilAttributTextBox = new ProfilAttributTextBox();

	public BoxPanel(Auswahl a) {
		this.auswahl = a;
		this.profilAttributListBox = new EigenschaftListBox(a);
		this.add(profilAttributListBox);

		// set style name for entire widget
		setStyleName("pure-control-group-1");

		// set style name for text box
		profilAttributListBox.setStyleName("pure-input-1-4");

	}
	
	public BoxPanel(Beschreibung b) {
		this.beschreibung = b;
		this.profilAttributTextBox = new ProfilAttributTextBox(b);
		this.add(profilAttributTextBox);

		// set style name for entire widget
		setStyleName("pure-control-group-1");

		// set style name for text box
		profilAttributTextBox.setStyleName("pure-input-1-4");

	}
	
	// Konstruktor für ein Auswahlobjekt mit vorselektiertem Item in der Listbox
	public BoxPanel(Auswahl a, String selectedItem, boolean isNameListbox) {
		this.auswahl = a;
		addLabelAuswahl(isNameListbox);
		this.profilAttributListBox = new EigenschaftListBox(a, selectedItem);
		this.add(profilAttributListBox);

		// set style name for entire widget
		setStyleName("pure-control-group-1");

		// set style name for text box

	}

	public BoxPanel(Beschreibung b, String text, boolean isNameTextbox) {
		this.beschreibung = b;
		this.addLabelBeschreibung(isNameTextbox);
		this.profilAttributTextBox = new ProfilAttributTextBox(b, text);
		this.add(this.profilAttributTextBox);

		// set style name for entire widget
		setStyleName("pure-control-group-1");

	}

	public BoxPanel(Beschreibung b, boolean isNameTextbox) {
		this.beschreibung = b;
		addLabelBeschreibung(isNameTextbox);
		this.profilAttributTextBox = new ProfilAttributTextBox(b);
		this.add(this.profilAttributTextBox);

		// set style name for entire widget
		setStyleName("pure-control-group-1");

	}

	public BoxPanel(Auswahl a, boolean isNameListbox) {
		this.auswahl = a;
		addLabelAuswahl(isNameListbox);
		this.profilAttributListBox = new EigenschaftListBox(a);
		
		// set style name for entire widget
		this.add(profilAttributListBox);
		setStyleName("pure-control-group-1");
	}

	public BoxPanel(String text) {
		this.add(new HTML(text));
	}

	public BoxPanel() {
	}

	public void addLabelAuswahl(boolean isNameListbox) {
		if (!isNameListbox) {
			this.add(new Label(this.auswahl.getBeschreibungstext()));
		} else {
			this.add(new Label(this.auswahl.getName()));
		}
	}

	public void addLabelBeschreibung(boolean isNameTextbox) {
		if (!isNameTextbox) {
			this.add(new Label(this.beschreibung.getBeschreibungstext()));
		} else {
			this.add(new Label(this.beschreibung.getName()));
		}
	}

	public void setEnable(boolean isEnabled) {
		this.profilAttributTextBox.setEnabled(isEnabled);
		this.profilAttributListBox.setEnabled(isEnabled);
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
	
	public void setGroesse(int groesse) {
	  }
	
	public void setAlter(int alter) {
	  }
	  
	  
}
