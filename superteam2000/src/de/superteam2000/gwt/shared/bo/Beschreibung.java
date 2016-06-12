package de.superteam2000.gwt.shared.bo;

/**
 * Hier wird ein exemplarisches Beschreibungsobjekt eines Profils realisiert.
 * 
 * @author Christopher Funke, Daniel Volz
 * @version 1.0
 */

public class Beschreibung extends Eigenschaft {

	private static final long serialVersionUID = 1L;
	
/**
 * Deklaration des Parameters text
 * @param text Bezeichnungswert der Auswahl	
 */

	private String text = "";
	
/**
 * Auslesen des Parameters Text
 * @return text
 */
	public String getText() {
		return text;
	}

/**
 * Setzen des Parameters Text	
 * @param text
 */
	public void setText(String text) {
		this.text = text;
	}

}
