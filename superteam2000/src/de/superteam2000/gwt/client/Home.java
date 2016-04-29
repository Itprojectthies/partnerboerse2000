package de.superteam2000.gwt.client;

import com.google.gwt.user.client.ui.HTML;

/**
 * Home bietet ein Showcase um die Begrüßungsfläche (rechtes Panel) darstellen
 * zu können.
 * 
 * 
 * @author Thomas Burkart
 * @version 1.0
 * @since 07.07.2015
 */
public class Home extends BasicFrame {

	/**
	 * Überschrift des Showcase (graue Überschrift)
	 */
	private String headlineText;

	/**
	 * StyleSheet Klasse für die Überschrift des Showcase
	 */
	private String headlineTextStyle;

	/**
	 * Konstruktor der Klasse Home erzeugt die Willkommens-Fläche in der, der
	 * eingeloggt Benutzer mit dem von Google übergebenen Username begrüßt wird.
	 */
	public Home() {
		LoginInfo user = ClientsideSettings.getCurrentUser();
		this.headlineText = "Herzlich Willkommen " + user.getNickname();
		this.headlineTextStyle = "formTitle";
	}

	/**
	 * Auslesen des Titels von jeweiligen Showcase (graue Überschrift)
	 */
	@Override
	protected String getHeadlineText() {
		return this.headlineText;
	}

	/**
	 * Auslesen der Formatierung des Titels
	 */
	protected String getHeadlineTextStyle() {
		return this.headlineTextStyle;
	}

	/**
	 * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie
	 * ist eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
	 * Aufbau des Willkommen-Panels (rechte Fläche), in welcher eine kurze
	 * Beschreibung der Appplikation steht.
	 */
	@Override
	protected void run() {
		HTML welcomeText = new HTML("Das ist eine Partnerbörse");
		this.add(welcomeText);
		

	}

}
