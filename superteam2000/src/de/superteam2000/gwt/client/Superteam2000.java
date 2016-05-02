package de.superteam2000.gwt.client;


import java.util.logging.Logger;
import de.superteam2000.gwt.client.gui.CustomerForm;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.*;

import de.superteam2000.gwt.client.ClientsideSettings;
import com.google.gwt.core.client.*;
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
	
	final Anchor logOutLink = new Anchor("Logout");
	
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
	 * @author Volz, Funke
	 *
	 */
	class LoginCallback implements AsyncCallback<Profil> {

		/**
		 * Konstruktor der Callback Klasse, diese legt bei der Instanziierung
		 * das übergebene Showcase fest.
		 */
		public LoginCallback() {
		}

		
		@Override
		public void onFailure(Throwable caught) {

			ClientsideSettings.getLogger().severe("login nicht geklappt");
		}


		@Override
		public void onSuccess(Profil result) {
			if (result.isLoggedIn() && !result.isCreated()) {
				ClientsideSettings.getLogger().severe(" erstell datum " + result.getGeburtsdatum() +
						"User " + result.getEmail()
								+ " erfolgreich eingeloggt.");
				ClientsideSettings.setCurrentUser(result);
				

				loadProfilErstellen();
			}
			else if (result.isLoggedIn() && result.isCreated()) {
				ClientsideSettings.setCurrentUser(result);
				loadProfil();
				ClientsideSettings.getLogger().severe(" erstell datum " + result.getGeburtsdatum() +
						"User " + result.getEmail()
								+ " erfolgreich eingeloggt.");
			} else {
				signInLink.setHref(result.getLoginUrl());
				loginPanel.add(loginLabel);
				loginPanel.add(signInLink);
				RootPanel.get("main").add(signInLink);
				ClientsideSettings.getLogger().severe(" zeige login url" + result.getEmail() 
				+ " Created? " + result.isCreated() + " Loggedin? " + result.isLoggedIn());
			}
		}
	}
	
	
	private void loadProfilErstellen() {

		NavigationBar.load();
		RootPanel.get("main").add(new Home());


		CustomerForm cf = new CustomerForm();
		VerticalPanel detailsPanel = new VerticalPanel();
		detailsPanel.add(cf);

		RootPanel.get("main").add(detailsPanel);

	}
	
	private void loadProfil() {

		NavigationBar.load();
		RootPanel.get("main").add(new Home());

		FindCustomersByNameDemo fc = new FindCustomersByNameDemo();
		
		VerticalPanel detailsPanel = new VerticalPanel();
		detailsPanel.add(fc);

		RootPanel.get("main").add(detailsPanel);

	}
	
}
