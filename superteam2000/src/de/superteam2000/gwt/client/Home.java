package de.superteam2000.gwt.client;

import com.google.gwt.user.client.ui.HTML;

import de.superteam2000.gwt.shared.bo.Profil;

/**
 * ??
 * @param headlineText Hierin wird ein Willkommenstext gespeichert.
 * @param headlineTextStyle Hierin wird die Formatierung des Titels gespeichert.
 * @author 
 *
 */
public class Home extends BasicFrame {

	
	private String headlineText;

	
	private String headlineTextStyle;

	/**
	 * ??
	 * @param user Hierin wird der aktuell eingeloggte User gespeichert.
	 */
	public Home() {
		Profil user = ClientsideSettings.getCurrentUser();
		this.headlineText = "Herzlich Willkommen " + user.getEmail();
		this.headlineTextStyle = "formTitle";
	}

	/**
	 * ??
	 * @return headlineText 
	 */
	@Override
	protected String getHeadlineText() {
		return this.headlineText;
	}

	/**
	 * ??
	 * @return headlineTextStyle
	 */
	protected String getHeadlineTextStyle() {
		return this.headlineTextStyle;
	}

	/**
	 * ??
	 * @param welcomeText Der Willkommenstext als Infotext wird gespeichert.
	 */
	@Override
	protected void run() {
		HTML welcomeText = new HTML("Das ist eine Partnerbörse");
		welcomeText.setSize("100em", "4em");
		this.add(welcomeText);
		

	}

}
