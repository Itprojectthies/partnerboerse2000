package de.superteam2000.gwt.client;

import com.google.gwt.user.client.ui.HTML;

import de.superteam2000.gwt.shared.bo.Profil;


public class Home extends BasicFrame {

	
	private String headlineText;

	
	private String headlineTextStyle;

	
	public Home() {
		Profil user = ClientsideSettings.getCurrentUser();
		this.headlineText = "Herzlich Willkommen " + user.getNickname();
		this.headlineTextStyle = "formTitle";
	}

	
	@Override
	protected String getHeadlineText() {
		return this.headlineText;
	}


	protected String getHeadlineTextStyle() {
		return this.headlineTextStyle;
	}


	@Override
	protected void run() {
		HTML welcomeText = new HTML("Das ist eine Partnerbörse");
		this.add(welcomeText);
		

	}

}
