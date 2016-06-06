package de.superteam2000.gwt.client.gui;

import com.google.gwt.user.client.ui.TextBox;

import de.superteam2000.gwt.shared.bo.Beschreibung;

public class ProfilAttributeTextBox extends TextBox {

	public static final String CLASSNAME = "profil-attribute-textbox";

	public ProfilAttributeTextBox(Beschreibung b, String text) {
		this(b);
		this.setSelectedItdem(text);
	}
	public ProfilAttributeTextBox(Beschreibung b) {
		this.setName(b.getName());
		setStyleName(CLASSNAME);
	}
	
	public ProfilAttributeTextBox() {
	}

	public void setSelectedItdem(String name) {
		this.setText(name);
	}
}
