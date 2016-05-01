package de.superteam2000.gwt.client;

import java.util.logging.Logger;
import de.superteam2000.gwt.client.FindCustomersByNameDemo;
import de.superteam2000.gwt.client.BasicFrame;
import de.superteam2000.gwt.client.gui.CustomerForm;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.*;

import de.superteam2000.gwt.client.ClientsideSettings;
import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Superteam2000 implements EntryPoint {

	// private final GreetingServiceAsync greetingService =
	// GWT.create(GreetingService.class);

	
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the StockWatcher application.");
	private Anchor signInLink = new Anchor("Sign In");
	Logger logger = ClientsideSettings.getLogger();

	@Override
	public void onModuleLoad() {
		// Check login status using login service.
		PartnerboerseAdministrationAsync pbVerwaltung = 
				ClientsideSettings.getPartnerboerseVerwaltung();
		pbVerwaltung.login(GWT.getHostPageBaseURL() + "Superteam2000.html", 
				new LoginCallback());

	}
	/**
	 * Asynchrone Anmelde-Klasse. 
	 * Showcase in dem die Antwort des Callbacks eingefügt wird.
	 * @author Timo Fesseler
	 *
	 */
	class LoginCallback implements AsyncCallback<Profil> {

		/**
		 * Konstruktor der Callback Klasse, diese legt bei der Instanziierung
		 * das übergebene Showcase fest.
		 */
		public LoginCallback() {
		}

		/**
		 * Wenn der asynchrone Aufruf fehlschlug oder das Element nicht gelöscht
		 * werden konnte wird die onFailure Methode aufgerufen und der Fehler
		 * als ErrorMsg dem Showcase eingefügt, sowie im Client-Logger
		 * verzeichnet.
		 */
		@Override
		public void onFailure(Throwable caught) {

			ClientsideSettings.getLogger().severe(
					"Error: " + caught.getMessage());
		}

		/**
		 * Wenn der asynchrone Aufruf zum löschen des Elements erfolgreich war,
		 * wird eine SuccessMsg im Showcase eingefügt.
		 */
		@Override
		public void onSuccess(Profil result) {
			if (result.isLoggedIn()) {
				ClientsideSettings.getLogger().severe(
						"User " + result.getEmailAddress()
								+ " erfolgreich eingeloggt.");
				ClientsideSettings.setCurrentUser(result);
				// Inhalt zur Sicherheit nochmal entfernen.
//				RootPanel.get("main").clear();
//				RootPanel.get("clientTitle").clear();

				
				
				// Titel des Clients
				Button clientTitle = new Button("&nbsp;Editor & Viewer");
				clientTitle.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						RootPanel.get("main").clear();
						RootPanel.get("main").add(new Home());
						//SearchPanel sp = new SearchPanel();
						//sp.load();
					}
				});
				// Tooltip des Titels
				clientTitle.setTitle("Zurück zur Startseite");

				loadPartnerboerse();
			} else {

				signInLink.setHref(result.getLoginUrl());
				signInLink.addStyleName("nav navbar-nav navbar-right");
				loginPanel.add(loginLabel);
				loginPanel.add(signInLink);
				RootPanel.get("main").add(signInLink);
				//RootPanel.get("login").add(signInLink);
			}
		}
	}
	
	
	private void loadPartnerboerse() {

		NavigationBar.load();
		RootPanel.get("main").add(new Home());


		CustomerForm cf = new CustomerForm();
		VerticalPanel detailsPanel = new VerticalPanel();
		detailsPanel.add(cf);

		RootPanel.get("main").add(detailsPanel);

	}
}
