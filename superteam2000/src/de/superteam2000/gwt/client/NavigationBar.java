package de.superteam2000.gwt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Hier werden Elemente der Navigationsleiste erzeugt
 * 
 * @author Thomas Burkart
 *
 */
public class NavigationBar {

	/**
	 * Diese Methode ladet die Navigationsleiste, mit Buttons etc.
	 */
	public static void load() {
		
		//Button infoBtn = new Button("<span class=\"glyphicon glyphicon-dashboard\" aria-hidden=\"true\"></span>");
		Button infoBtn = new Button("Logger");
		infoBtn.setStylePrimaryName("btn btn-default navbar-btn");
		infoBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				LoggingDialog.getDialogBox().show();
			}
		});
		RootPanel.get("navbar-right").add(infoBtn);
	
		final Profil user = ClientsideSettings.getCurrentUser();
		
		//Button logoutBtn = new Button("<span class=\"glyphicon glyphicon-log-out\" aria-hidden=\"true\"></span>");
		Button logoutBtn = new Button("Logout");
		logoutBtn.setStylePrimaryName("btn btn-default navbar-btn");
		logoutBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				Window.Location.assign(user.getLogoutUrl());
			}
		});
		RootPanel.get("navbar-right").add(logoutBtn);
	}
	/*
	 * Diese Methode ladet die Navigationsleiste für den Report Generator, hier
	 * ist kein "anlegen"-Button enthalten.
	 */
//	public static void loadForReportGen() {
//		// Anlegen-Button einfügen
//
//		Button infoBtn = new Button("<img src=\"img/info.png\" style=\"width: 19px\" />");
//		infoBtn.setStylePrimaryName("btn btn-link");
//		infoBtn.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				LoggingDialog.getDialogBox().show();
//			}
//		});
//		RootPanel.get("navbar").add(infoBtn);
//
//		Button logoutBtn = new Button("<img src=\"img/logout.png\" style=\"width: 19px\" />");
//		logoutBtn.setStylePrimaryName("btn btn-link");
//		logoutBtn.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				RootPanel.get("main").clear();
//				RootPanel.get("navigator").clear();
//				RootPanel.get("navbar").clear();
//				// LoginLogout.load();
//				// AdministrationCommonAsync administration = ClientsideSettings
//				// .getAdministration();
//				// administration.logoutUser(true, new LogoutCallback());
//			}
//		});
//		RootPanel.get("navbar").add(logoutBtn);
//	}
	
}


