package de.superteam2000.gwt.client;


import de.superteam2000.gwt.shared.FieldVerifier;
import de.superteam2000.gwt.shared.PartnerboerseAdministration;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.client.FindCustomersByNameDemo;
import de.superteam2000.gwt.client.Showcase;
import de.superteam2000.gwt.server.*;

import java.util.Date;

import com.gargoylesoftware.htmlunit.javascript.host.Console;
import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.logging.client.ConsoleLogHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import de.superteam2000.gwt.shared.bo.*;
import com.google.gwt.logging.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Superteam2000 implements EntryPoint {
	
	//private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	
	public void onModuleLoad() {
		
		
		
		
		//PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

		//AsyncCallback<Profil> callback = 
		
		
		
		//pbVerwaltung.getProfilById(1, callback);
		
		/*
	     * Auch dem Report-Generator weisen wir dieses Bank-Objekt zu. Es wird dort
	     * für die Darstellung der Adressdaten des Kreditinstituts benötigt.
	     */
//	    ReportGeneratorAsync reportGenerator = ClientsideSettings
//	        .getReportGenerator();
//	    reportGenerator.setBank(bank, new SetBankCallback());

	    /*
	     * Wir bereiten nun die Erstellung eines bescheidenen Navigators vor, der
	     * einige Schaltflächen (Buttons) für die Ausführung von Unterprogrammen
	     * enthalten soll.
	     * 
	     * Die jeweils ausgeführten Unterprogramme sind Demonstratoren
	     * exemplarischer Anwendungsfälle des Systems. Auf eine professionelle
	     * Gestaltung der Benutzungsschnittstelle wurde bewusst verzichtet, um den
	     * Blick nicht von den wesentlichen Funktionen abzulenken. Eine
	     * exemplarische GUI-Realisierung findet sich separat.
	     * 
	     * Die Demonstratoren werden nachfolgend als Showcase bezeichnet. Aus diesem
	     * Grund existiert auch eine Basisklasse für sämtliche Showcase-Klassen
	     * namens Showcase.
	     */

	    /*
	     * Der Navigator ist als einspaltige Aneinanderreihung von Buttons
	     * realisiert. Daher bietet sich ein VerticalPanel als Container an.
	     */
	    VerticalPanel navPanel = new VerticalPanel();

	    /*
	     * Das VerticalPanel wird einem DIV-Element namens "Navigator" in der
	     * zugehörigen HTML-Datei zugewiesen und erhält so seinen Darstellungsort.
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
	     * Natürlich benötigt der Button auch ein Verhalten, wenn man mit der Maus
	     * auf ihn klickt. Hierzu registrieren wir einen ClickHandler, dessen
	     * onClick()-Methode beim Mausklick auf den zugehörigen Button aufgerufen
	     * wird.
	     */
	    findCustomerButton.addClickHandler(new ClickHandler() {
	      @Override
		public void onClick(ClickEvent event) {
	        /*
	         * Showcase instantiieren.
	         */
	    	
	        Showcase showcase = new FindCustomersByNameDemo();
	        

	        /*
	         * Für die Ausgaben haben wir ein separates DIV-Element namens "Details"
	         * in die zugehörige HTML-Datei eingefügt. Bevor wir den neuen Showcase
	         * dort einbetten, löschen wir vorsichtshalber sämtliche bisherigen
	         * Elemente dieses DIV.
	         */
	        RootPanel.get("Details").clear();
	        RootPanel.get("Details").add(showcase);
	      }
	    });
	   
	    
	    
		final Button sendButton = new Button("Send");
		findCustomerButton.setStylePrimaryName("bankproject-menubutton");
		navPanel.add(sendButton);
//		sendButton.addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				pbVerwaltung.getProfilById(1, new AsyncCallback<Profil>() {
//					public void onFailure(Throwable caught) {
//						// Show the RPC error message to the user
//		;
//					}
//
//					public void onSuccess(Profil p) {
//							System.out.println("es hat geklappt");
//							dialogBox.setText("Remote Procedure Call");
//							
//					}
//				});
//				
//				
//				
//			}
//		});
		
//		final TextBox nameField = new TextBox();
//		nameField.setText("GWT User");
//		final Label errorLabel = new Label();
//
//		// We can add style names to widgets
//		sendButton.addStyleName("sendButton");
//
//		// Add the nameField and sendButton to the RootPanel
//		// Use RootPanel.get() to get the entire body element
//		RootPanel.get("nameFieldContainer").add(nameField);
//		RootPanel.get("sendButtonContainer").add(sendButton);
//		RootPanel.get("errorLabelContainer").add(errorLabel);
//
//		// Focus the cursor on the name field when the app loads
//		nameField.setFocus(true);
//		nameField.selectAll();
//
//		// Create the popup dialog box
//		final DialogBox dialogBox = new DialogBox();
//		dialogBox.setText("Remote Procedure Call");
//		dialogBox.setAnimationEnabled(true);
//		final Button closeButton = new Button("Close");
//		// We can set the id of a widget by accessing its Element
//		closeButton.getElement().setId("closeButton");
//		final Label textToServerLabel = new Label();
//		final HTML serverResponseLabel = new HTML();
//		VerticalPanel dialogVPanel = new VerticalPanel();
//		dialogVPanel.addStyleName("dialogVPanel");
//		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
//		dialogVPanel.add(textToServerLabel);
//		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
//		dialogVPanel.add(serverResponseLabel);
//		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
//		dialogVPanel.add(closeButton);
//		dialogBox.setWidget(dialogVPanel);
//
//		// Add a handler to close the DialogBox
//		closeButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				dialogBox.hide();
//				sendButton.setEnabled(true);
//				sendButton.setFocus(true);
//			}
//		});
//
//		// Create a handler for the sendButton and nameField
//		class MyHandler implements ClickHandler, KeyUpHandler {
//			/**
//			 * Fired when the user clicks on the sendButton.
//			 */
//			public void onClick(ClickEvent event) {
//				sendNameToServer();
//			}
//
//			/**
//			 * Fired when the user types in the nameField.
//			 */
//			public void onKeyUp(KeyUpEvent event) {
//				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
//					sendNameToServer();
//				}
//			}
//
//			/**
//			 * Send the name from the nameField to the server and wait for a response.
//			 */
//			private void sendNameToServer() {
//				// First, we validate the input.
//				errorLabel.setText("");
//				String textToServer = nameField.getText();
//				if (!FieldVerifier.isValidName(textToServer)) {
//					errorLabel.setText("Please enter at least four characters");
//					return;
//				}
//
//				// Then, we send the input to the server.
//				sendButton.setEnabled(false);
//				textToServerLabel.setText(textToServer);
//				serverResponseLabel.setText("");
//				//pbVerwaltung.getProfilById(1, new AsyncCallback<Profil>() {
//					public void onFailure(Throwable caught) {
//						// Show the RPC error message to the user
//						dialogBox.setText("Remote Procedure Call - Failure");
//						serverResponseLabel.addStyleName("serverResponseLabelError");
//						//serverResponseLabel.setHTML(SERVER_ERROR);
//						dialogBox.center();
//						closeButton.setFocus(true);
//					}
//
//					public void onSuccess(Profil p) {
//						dialogBox.setText("Remote Procedure Call");
//						serverResponseLabel.removeStyleName("serverResponseLabelError");
//						//serverResponseLabel.setHTMtL(result);
//						serverResponseLabel.setHTML("Kunde #" + p.getId() + ": " + p.getNachname()
//                + ", " + p.getVorname());
//						dialogBox.center();
//						closeButton.setFocus(true);
//					}
//				});
//			}
//		}
//
//		// Add a handler to send the name to the server
//		MyHandler handler = new MyHandler();
//		sendButton.addClickHandler(handler);
//		nameField.addKeyUpHandler(handler);
	}
}
