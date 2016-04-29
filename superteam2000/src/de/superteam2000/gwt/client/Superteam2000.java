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
		PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
		pbVerwaltung.login(GWT.getHostPageBaseURL() + "Superteam2000.html", new LoginCallback()
				
//				new AsyncCallback<LoginInfo>() {
//			@Override
//			public void onFailure(Throwable error) {
//			}
//
//			@Override
//			public void onSuccess(LoginInfo result) {
//				loginInfo = result;
//				if (loginInfo.isLoggedIn()) {
//					ClientsideSettings.setCurrentUser(result);
//					loadPartnerboerse();
//				} else {
//					
//					loadLogin();
//					
//				}
//			}
//		}
				);

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
				RootPanel.get("main").clear();
				RootPanel.get("clientTitle").clear();

				
				
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

				// StyleSheet festlegen
				clientTitle.setStylePrimaryName("btn btn-link navbar-brand");

				// Titel-Button dem entsprechenden Layer hinzufügen.
				RootPanel.get("clientTitle").add(clientTitle);

				loadPartnerboerse();
			} else {
				
				signInLink.setHref(result.getLoginUrl());
				loginPanel.add(loginLabel);
				loginPanel.add(signInLink);
				RootPanel.get("main").add(loginPanel);
				RootPanel.get("main").add(new Home());
			}
		}
	}
	
	
	


	private void loadPartnerboerse() {

		NavigationBar.load();
		RootPanel.get("main").add(new Home());


		CustomerForm cf = new CustomerForm();

		/*
		 * Die Panels und der CellTree werden erzeugt und angeordnet und in das
		 * RootPanel eingefügt.
		 */
		VerticalPanel detailsPanel = new VerticalPanel();
		detailsPanel.add(cf);

		RootPanel.get("main").add(detailsPanel);

		/*
		 * Auch dem Report-Generator weisen wir dieses Bank-Objekt zu. Es wird
		 * dort für die Darstellung der Adressdaten des Kreditinstituts
		 * benötigt.
		 */
		// ReportGeneratorAsync reportGenerator = ClientsideSettings
		// .getReportGenerator();
		// reportGenerator.setBank(bank, new SetBankCallback());

		/*
		 * Wir bereiten nun die Erstellung eines bescheidenen Navigators vor,
		 * der einige Schaltflächen (Buttons) für die Ausführung von
		 * Unterprogrammen enthalten soll.
		 * 
		 * Die jeweils ausgeführten Unterprogramme sind Demonstratoren
		 * exemplarischer Anwendungsfälle des Systems. Auf eine professionelle
		 * Gestaltung der Benutzungsschnittstelle wurde bewusst verzichtet, um
		 * den Blick nicht von den wesentlichen Funktionen abzulenken. Eine
		 * exemplarische GUI-Realisierung findet sich separat.
		 * 
		 * Die Demonstratoren werden nachfolgend als Showcase bezeichnet. Aus
		 * diesem Grund existiert auch eine Basisklasse für sämtliche
		 * Showcase-Klassen namens Showcase.
		 */

		/*
		 * Der Navigator ist als einspaltige Aneinanderreihung von Buttons
		 * realisiert. Daher bietet sich ein VerticalPanel als Container an.
		 */
		VerticalPanel navPanel = new VerticalPanel();

		/*
		 * Das VerticalPanel wird einem DIV-Element namens "Navigator" in der
		 * zugehörigen HTML-Datei zugewiesen und erhält so seinen
		 * Darstellungsort.
		 */
		RootPanel.get("Navigator").add(navPanel);

		/*
		 * Ab hier bauen wir sukzessive den Navigator mit seinen Buttons aus.
		 */

		/*
		 * Neues Button Widget erzeugen und eine Beschriftung festlegen.
		 */
		final Button findCustomerButton = new Button("Finde Kunde");

		/*
		 * Unter welchem Namen können wir den Button durch die CSS-Datei des
		 * Projekts formatieren?
		 */
		findCustomerButton.setStylePrimaryName("bankproject-menubutton");

		/*
		 * Hinzufügen des Buttons zum VerticalPanel.
		 */
		navPanel.add(findCustomerButton);

		/*
		 * Natürlich benötigt der Button auch ein Verhalten, wenn man mit der
		 * Maus auf ihn klickt. Hierzu registrieren wir einen ClickHandler,
		 * dessen onClick()-Methode beim Mausklick auf den zugehörigen Button
		 * aufgerufen wird.
		 */
		findCustomerButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				/*
				 * Showcase instantiieren.
				 */

				BasicFrame showcase = new FindCustomersByNameDemo();

				/*
				 * Für die Ausgaben haben wir ein separates DIV-Element namens
				 * "Details" in die zugehörige HTML-Datei eingefügt. Bevor wir
				 * den neuen Showcase dort einbetten, löschen wir
				 * vorsichtshalber sämtliche bisherigen Elemente dieses DIV.
				 */
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(showcase);
			}
		});
	}
}
