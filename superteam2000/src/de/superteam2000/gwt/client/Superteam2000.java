package de.superteam2000.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Superteam2000 implements EntryPoint {

	@Override
	public void onModuleLoad() {

		PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
		pbVerwaltung.login(GWT.getHostPageBaseURL() + "Superteam2000.html", new LoginCallback());

	}

	/**
	 * Asynchrone Anmelde-Klasse. Showcase in dem die Antwort des Callbacks
	 * eingefügt wird.
	 * 
	 * @author Volz, Funke
	 *
	 */
	class LoginCallback implements AsyncCallback<Profil> {

		@Override
		public void onFailure(Throwable caught) {

			ClientsideSettings.getLogger().severe("Login fehlgeschlagen!");
		}

		@Override
		public void onSuccess(Profil result) {
			

			// User eingeloggt und nicht in der db vorhanden?
			if (result.isLoggedIn() && !result.isCreated()) {
				ClientsideSettings.setCurrentUser(result);
				profilErstellen();
				ClientsideSettings.getLogger().info("Erstelle profil für " + result.getEmail());

				// User exisitiert in der db und loggt sich ein.
			} else if (result.isLoggedIn()) {

				ClientsideSettings.setCurrentUser(result);
				loadProfil();
				ClientsideSettings.getLogger().info("Lade vorhandenes Profil");

				// signup link für login anzeigen
			} else {
				VerticalPanel loginPanel = new VerticalPanel();
				Label loginLabel = new Label(
						"Logge Dich mit deinem Google Account ein, um die Partnerboerse2000 zu nutzen!");
				Anchor signInLink = new Anchor("Sign In");

				signInLink.setHref(result.getLoginUrl());
				loginPanel.add(loginLabel);
				loginPanel.add(signInLink);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(loginPanel);

			}
		}

		private void profilErstellen() {

			Navbar nb = new Navbar();
			RootPanel.get("Navigator").clear();
			RootPanel.get("Navigator").add(nb);
			
			RootPanel.get("Details").add(new Home());

			CreateProfil cf = new CreateProfil();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(cf);

		}

		private void loadProfil() {

			Navbar nb = new Navbar();
			
			RootPanel.get("Navigator").clear();
			RootPanel.get("Navigator").add(nb);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(new Home());
			
			ShowProfil sp = new ShowProfil();
			FlowPanel fp = new FlowPanel();
			fp.add(sp);
			RootPanel.get("Details").add(fp);

		}
	}

}
