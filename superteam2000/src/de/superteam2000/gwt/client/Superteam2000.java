package de.superteam2000.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
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
				
				FlowPanel splashContaiern = new FlowPanel();
				splashContaiern.setStyleName("splash-container");
				FlowPanel splash = new FlowPanel();
				splash.setStyleName("splash");
				
				HTML headingElement= new HTML();
				headingElement.setHTML("Willkommen auf PartnerBörse2000");  
				headingElement.setStyleName("splash-head");
				
				
				FlowPanel splashSubhead = new FlowPanel(ParagraphElement.TAG);
				splashSubhead.setStyleName("splash-subhead");
				HTML splahParagraph = new HTML("Melde dich an und finde deine besser Hälfte");
				splashSubhead.add(splahParagraph);
				
				Anchor loginAnchor = new Anchor("Los!");
				loginAnchor.setStyleName("pure-button pure-button-primary");
				loginAnchor.setHref(result.getLoginUrl());
				
				
				splash.add(headingElement);
				splash.add(splashSubhead);
				splash.add(loginAnchor);
				splashContaiern.add(splash);
				
				RootPanel.get("main").add(splashContaiern);
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
			
			RootPanel.get("main").clear();
			RootPanel.get("main").add(nb);
//			RootPanel.get("Details").clear();
//			RootPanel.get("Details").add(new Home());
			
			ShowProfil sp = new ShowProfil();
			FlowPanel fp = new FlowPanel();
			fp.add(sp);
			RootPanel.get("main").add(fp);

		}
	}

}
