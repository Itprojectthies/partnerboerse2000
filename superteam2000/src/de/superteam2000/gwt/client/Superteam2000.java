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
  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

	@Override
	public void onModuleLoad() {
//	  RootPanel.get("menu").getElemen
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
				ClientsideSettings.getLogger().info("Erstelle profil für " + result.getEmail());
				profilErstellen();

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
				HTML splahParagraph = new HTML("Melde dich an und finde deine bessere Hälfte");
				splashSubhead.add(splahParagraph);
				
				Anchor loginAnchor = new Anchor("Los!");
				loginAnchor.setStyleName("pure-button-login pure-button-primary-login");
				loginAnchor.setHref(result.getLoginUrl());
				
				
				splash.add(headingElement);
				splash.add(splashSubhead);
				splash.add(loginAnchor);
				splashContaiern.add(splash);
				ClientsideSettings.setCurrentUser(result);
				
				RootPanel.get("menu").getElement().getStyle().setZIndex(0);
				RootPanel.get("main").add(splashContaiern);
			}
		}

		private void profilErstellen() {


			BasicFrame cf = new CreateProfil();
			RootPanel.get("main").clear();
			RootPanel.get("main").add(cf);

		}

		private void loadProfil() {

			Navbar nb = new Navbar();
			
			RootPanel.get("menu").clear();
			RootPanel.get("menu").add(nb);
			
			ShowProfil sp = new ShowProfil();
			RootPanel.get("main").add(sp);

		}
	}

}
