package de.superteam2000.gwt.client;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;

import de.superteam2000.gwt.shared.bo.Profil;


public class NavigationBar extends BasicFrame {
	/**
	 * Diese Methode ladet die Navigationsleiste, mit Buttons etc.
	 */
	private String headlineText;
	Profil user = ClientsideSettings.getCurrentUser();

	//Button infoBtn = new Button("<span class=\"glyphicon glyphicon-dashboard\" aria-hidden=\"true\"></span>");

	//
	//		RootPanel.get("navbar").add(infoBtn);
	//
	//		Anchor logOutLink = new Anchor("Logout");
	//
	//		//logOutLink.addStyleName("nav navbar-nav navbar-right");
	//
	//		logOutLink.setHref(user.getLogoutUrl());
	//
	//		RootPanel.get("navbar").add(logOutLink);



	@Override
	protected String getHeadlineText() {
		return this.headlineText;
	}
	
	
	
	@Override
	protected void run() {
		
		if (user != null && user.isLoggedIn()) {


			Button infoBtn = new Button("Logout");
			infoBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					Window.open(user.getLogoutUrl(), "_self", "");					
				}
			});
			append(infoBtn);



//			Anchor logOutLink = new Anchor("Logout");
//						logOutLink.setHref(user.getLogoutUrl());
//			this.append(logOutLink);
		}


	}
}


