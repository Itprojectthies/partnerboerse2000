package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.superteam2000.gwt.shared.bo.Profil;

public class Navbar extends HorizontalPanel {
	
	@Override
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
			
			Button logBtn = new Button("Logger");
			logBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					
					LogConsole.getDialogBox().show();
					
				}
			});
			append(logBtn);

			final Button profilBtn = new Button("Profil");
			profilBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					
					
					

					ShowProfil sp = new ShowProfil();
					RootPanel.get("Details").clear();
					RootPanel.get("Menu").clear();
					RootPanel.get("Details").add(sp);				
				}
			});
			append(profilBtn);
			
			
			Button merklisteBtn = new Button("Merkliste");
			// hp.add(merklisteBtn);
			merklisteBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					Merkliste m = new Merkliste();
					RootPanel.get("Details").clear();
					RootPanel.get("Menu").clear();
					RootPanel.get("Details").add(m);
					
					
				}
			});
			append(merklisteBtn);

			Button sperrlisteBtn = new Button("Sperrliste");
			sperrlisteBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					Sperre s = new Sperre();
					RootPanel.get("Details").clear();
					RootPanel.get("Menu").clear();
					RootPanel.get("Details").add(s);
				}

			});
			append(sperrlisteBtn);

			Button suchprofilBtn = new Button("Suchprofil");
			suchprofilBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					Suche s = new Suche();
					
					RootPanel.get("Details").clear();
					RootPanel.get("Menu").clear();
					RootPanel.get("Details").add(s);
				}
			});
			append(suchprofilBtn);

			Button eigenschaftenBtn = new Button("Eigenschaften");
			eigenschaftenBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					Eigenschaft e = new Eigenschaft();
					VerticalPanel detailsPanel = new VerticalPanel();
					detailsPanel.add(e);
					RootPanel.get("Details").clear();
					RootPanel.get("Menu").clear();
					 RootPanel.get("Details").add(detailsPanel);
				}
			});
			append(eigenschaftenBtn);
			
			Button dataGridBtn = new Button("DataGrid");
			dataGridBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					ClientsideSettings.getPartnerboerseVerwaltung().getProfilesByAehnlichkeitsmass(user, new AsyncCallback<ArrayList<Profil>>() {
						
						@Override
						public void onSuccess(ArrayList<Profil> result) {
							DataGridTest dgt = new DataGridTest(result);
							VerticalPanel detailsPanel = new VerticalPanel();
							detailsPanel.add(dgt);
							RootPanel.get("Details").clear();
							RootPanel.get("Menu").clear();
							RootPanel.get("Details").add(detailsPanel);
							
						}
						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}
					} );


				}
			});
			append(dataGridBtn);
			
			Button reportButton = new Button("Report");
			// hp.add(logoutBtn);
			reportButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
//					Window.open(user.getLogoutUrl(), "_self", "");
//					Window.confirm("OK = true      Cancel = false");
					Window.Location.replace("http://127.0.0.1:8888/Reportgen.html");
				}
			});
			append(reportButton);

			// Anchor logOutLink = new Anchor("Logout");
			// logOutLink.setHref(user.getLogoutUrl());
			// this.append(logOutLink);
		}
	}

}
