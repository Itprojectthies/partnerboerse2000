package de.superteam2000.gwt.client.gui;

import com.google.gwt.user.client.ui.TextBox;

import de.superteam2000.gwt.shared.bo.Beschreibung;

public class ProfilTextBox extends TextBox {

    
  
	public ProfilTextBox(Beschreibung b, String text) {
		this(b);
		this.setSelectedItem(text);
	}
	public ProfilTextBox(Beschreibung b) {
		this.setName(b.getName());
		this.getElement().setAttribute("type", "text");
		this.getElement().setAttribute("placeholder", b.getName());
		
	}
	
	public ProfilTextBox() {
	}

	public void setSelectedItem(String name) {
		this.setText(name);
	}
}
