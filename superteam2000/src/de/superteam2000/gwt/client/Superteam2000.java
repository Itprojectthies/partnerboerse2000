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

	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the StockWatcher application.");
	private Anchor signInLink = new Anchor("Sign In");
	final Anchor logOutLink = new Anchor("Logout");

	Logger logger = ClientsideSettings.getLogger();

	@Override
	public void onModuleLoad() {

		PartnerboerseAdministrationAsync pbVerwaltung = 
				ClientsideSettings.getPartnerboerseVerwaltung();

		pbVerwaltung.login(GWT.getHostPageBaseURL() + 
				"Superteam2000.html", new LoginCallback());

	}

	/**
	 * Asynchrone Anmelde-Klasse. Showcase in dem die Antwort des Callbacks
	 * eingefügt wird.
	 * 
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

			ClientsideSettings.getLogger().severe("Login fehlgeschlagen!");
		}

		@Override
		public void onSuccess(Profil result) {
			if (result.isLoggedIn() && !result.isCreated()) {

				ClientsideSettings.setCurrentUser(result);
				profilErstellen();
				ClientsideSettings.getLogger().info("Erstelle profil für " + result.getEmail());
				
			} else if (result.isLoggedIn() && result.isCreated()) {

				ClientsideSettings.setCurrentUser(result);
				loadProfil();
				ClientsideSettings.getLogger().info("Lade vorhandenes Profil");

			} else {

				signInLink.setHref(result.getLoginUrl());
				loginPanel.add(loginLabel);
				loginPanel.add(signInLink);
				RootPanel.get("main").add(signInLink);

			}
		}
	}

	private void profilErstellen() {

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

		ShowProfil fc = new ShowProfil();

		VerticalPanel detailsPanel = new VerticalPanel();
		detailsPanel.add(fc);

		RootPanel.get("main").add(detailsPanel);

	}

}
