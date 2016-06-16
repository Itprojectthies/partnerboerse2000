package de.superteam2000.gwt.client.gui;

import com.google.gwt.user.client.ui.TextBox;

import de.superteam2000.gwt.shared.bo.Beschreibung;

public class ProfilTextBox extends TextBox {

    
    public static final String CLASSNAME = "pure-input-1-4";
    
	public ProfilTextBox(Beschreibung b, String text) {
		this(b);
		this.setSelectedItem(text);
	}
	public ProfilTextBox(Beschreibung b) {
		this.setName(b.getName());
		this.getElement().setAttribute("type", "text");
		this.getElement().setAttribute("placeholder", b.getName());
		setStyleName(CLASSNAME);
	}
	
	public ProfilTextBox() {
	}

	public void setSelectedItem(String name) {
		this.setText(name);
	}
}
