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

	}
}
