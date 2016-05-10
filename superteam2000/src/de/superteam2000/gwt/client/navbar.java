package de.superteam2000.gwt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.superteam2000.gwt.shared.bo.Profil;

public class navbar extends HorizontalPanel {
	
	public void onLoad() {
		/*
		 * Bevor wir unsere eigene Formatierung veranslassen, überlassen wir es
		 * der Superklasse eine Initialisierung vorzunehmen.
		 */
		super.onLoad();

		/*
		 * Wenn alles vorbereitet ist, stoßen wir die run()-Methode an. Auch
		 * run() ist als abstrakte Methode bzw. als Einschubmethode realisiert.
		 * Auch hier ist es Aufgabe der Subklassen, für deren Implementierung zu
		 * sorgen.
		 */
		this.run();
	}

	Profil user = ClientsideSettings.getCurrentUser();

	public void append(Widget wi) {
		this.add(wi);
	}

	// this.setCellHorizontalAlignment(hp, ALIGN_RIGHT);
	// hp.setHorizontalAlignment(ALIGN_RIGHT);
	void run() {
		if (user != null && user.isLoggedIn()) {
			


			Button logoutBtn = new Button("Logout");
			// hp.add(logoutBtn);
			logoutBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					Window.open(user.getLogoutUrl(), "_self", "");
				}
			});
			append(logoutBtn);

			Button merklisteBtn = new Button("Merkliste");
			// hp.add(merklisteBtn);
			merklisteBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					Window.alert("Merkliste");

				}
			});
			append(merklisteBtn);

			Button sperrlisteBtn = new Button("Sperrliste");
			sperrlisteBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					Window.alert("SPerrliste");
				}

			});
			append(sperrlisteBtn);

			Button suchprofilBtn = new Button("Suchprofil");
			suchprofilBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					Window.alert("Suchprofil");
				}
			});
			append(suchprofilBtn);

			Button eigenschaftenBtn = new Button("Eigenschaften");
			eigenschaftenBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					Window.alert("Eignschaften");
				}
			});
			append(eigenschaftenBtn);
			
			Button reportButton = new Button("Report");
			// hp.add(logoutBtn);
			reportButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
//					Window.open(user.getLogoutUrl(), "_self", "");
					Window.confirm("OK = true      Cancel = false");
				}
			});
			append(reportButton);


			// Anchor logOutLink = new Anchor("Logout");
			// logOutLink.setHref(user.getLogoutUrl());
			// this.append(logOutLink);
		}
	}

}
