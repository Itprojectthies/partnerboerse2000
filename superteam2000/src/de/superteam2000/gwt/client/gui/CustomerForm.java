package de.superteam2000.gwt.client.gui;

import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.client.Home;
import de.superteam2000.gwt.client.Navbar;
import de.superteam2000.gwt.client.ShowProfil;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Formular für die Darstellung des selektierten Kunden
 * 
 * @author Christian Rathke
 */
public class CustomerForm extends VerticalPanel {

	PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
	Profil profilToDisplay = null;

	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */
	TextBox firstNameTextBox = new TextBox();
	TextBox lastNameTextBox = new TextBox();
	Label idValueLabel = new Label();
	Label emailTextBox = new Label();
	ListBox gebDatumTagListBox = new ListBox();
	ListBox gebDatumMonatListBox = new ListBox();
	ListBox gebDatumJahrListBOx = new ListBox();
	ListBox haarfarbeListBox = new ListBox();
	ListBox raucherListBox = new ListBox();
	ListBox religionListBox = new ListBox();
	ListBox geschlechtListBox = new ListBox();
	ListBox koerpergroesseListBox = new ListBox();
	IntegerBox koerpergroesseIntegerBox = new IntegerBox();

	Label test = new Label("");
	Profil user = ClientsideSettings.getCurrentUser();
	Logger logger = ClientsideSettings.getLogger();
	
	/*
	 * Im Konstruktor werden die anderen Widgets erzeugt. Alle werden in einem
	 * Raster angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen
	 * Widgets bestimmt.
	 */
	public CustomerForm() {

		Grid customerGrid = new Grid(11, 4);
		this.add(customerGrid);

		Label firstNameLabel = new Label("Vorname");
		customerGrid.setWidget(1, 0, firstNameLabel);
		customerGrid.setWidget(1, 1, firstNameTextBox);

		Label lastNameLabel = new Label("Nachname");
		customerGrid.setWidget(2, 0, lastNameLabel);
		customerGrid.setWidget(2, 1, lastNameTextBox);

		Label emailLabel = new Label("Email");
		customerGrid.setWidget(3, 0, emailLabel);
		customerGrid.setWidget(3, 1, emailTextBox);
		emailTextBox.setText(user.getEmail());

		Label gebDatumLabel = new Label("Geburtstag");
		customerGrid.setWidget(4, 0, gebDatumLabel);
		customerGrid.setWidget(4, 1, gebDatumTagListBox);
		customerGrid.setWidget(4, 2, gebDatumMonatListBox);
		customerGrid.setWidget(4, 3, gebDatumJahrListBOx);
		
		for (int i = 1; i <= 31; i++) {
			gebDatumTagListBox.addItem(String.valueOf(i));
		}
		for (int i = 1; i <= 12; i++) {
			gebDatumMonatListBox.addItem(String.valueOf(i));
		}
		for (int i = 1900; i <= 2000 ; i++) {
			gebDatumJahrListBOx.addItem(String.valueOf(i));
		}

		Label haarfarbeLabel = new Label("Haarfarbe");
		customerGrid.setWidget(5, 0, haarfarbeLabel);
		customerGrid.setWidget(5, 1, haarfarbeListBox);
		haarfarbeListBox.addItem("braun");
		haarfarbeListBox.addItem("blond");
		haarfarbeListBox.addItem("schwarz");
		haarfarbeListBox.addItem("grün");

		Label raucherLabel = new Label("Raucher");
		customerGrid.setWidget(6, 0, raucherLabel);
		customerGrid.setWidget(6, 1, raucherListBox);
		raucherListBox.addItem("Raucher");
		raucherListBox.addItem("Nichtraucher");

		Label religionLabel = new Label("Religion");
		customerGrid.setWidget(7, 0, religionLabel);
		customerGrid.setWidget(7, 1, religionListBox);
		religionListBox.addItem("römisch-katholisch");
		religionListBox.addItem("evangelisch");
		religionListBox.addItem("jüdisch");
		religionListBox.addItem("muslimisch");
		religionListBox.addItem("sonstige");

		Label geschlechtLabel = new Label("Geschlecht");
		customerGrid.setWidget(8, 0, geschlechtLabel);
		customerGrid.setWidget(8, 1, geschlechtListBox);
		geschlechtListBox.addItem("männlich");
		geschlechtListBox.addItem("weiblich");

		Label groesseLabel = new Label("Körpergröße");
		customerGrid.setWidget(9, 0, groesseLabel);
		
		for (int i = 140; i < 210; i++) {
			koerpergroesseListBox.addItem("" + i);
			
		}
		customerGrid.setWidget(9, 1, koerpergroesseListBox);


		HorizontalPanel customerButtonsPanel = new HorizontalPanel();
		this.add(customerButtonsPanel);

		Button newButton = new Button("Weiter");
		newButton.addClickHandler(new NewClickHandler());
		customerButtonsPanel.add(newButton);

	}

	private class NewClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO: erstelldatum im adminimpl hinzufügen

			
			String firstName = firstNameTextBox.getText();
			String lastName = lastNameTextBox.getText();
			String email = emailTextBox.getText();
			int geburtsTag = Integer.valueOf(gebDatumTagListBox.getSelectedItemText());
			int geburtsMonat = Integer.valueOf(gebDatumMonatListBox.getSelectedItemText());
			int geburtsJahr = Integer.valueOf(gebDatumJahrListBOx.getSelectedItemText());
			Date gebTag2 = DateTimeFormat.getFormat("yyyy-MM-dd").parse(geburtsJahr +"-"+ geburtsMonat+"-"+geburtsTag);
			java.sql.Date gebTag = new java.sql.Date(gebTag2.getTime());
			String haarfarbe = haarfarbeListBox.getSelectedItemText();
			String raucher = raucherListBox.getSelectedItemText();
			String religion = religionListBox.getSelectedItemText();
			String geschlecht = geschlechtListBox.getSelectedItemText();
			int groesse = Integer.valueOf(koerpergroesseListBox.getSelectedItemText());
			
			pbVerwaltung.createProfil(lastName, firstName, email, gebTag, haarfarbe, raucher, religion, groesse,
					geschlecht, new CreateCustomerCallback());

		}
	}

	class CreateCustomerCallback implements AsyncCallback<Profil> {

		@Override
		public void onFailure(Throwable caught) {
			logger.severe("Erstellen des useres hat nicht funktioniert");

		}

		@Override
		public void onSuccess(Profil p) {

			ClientsideSettings.setCurrentUser(p);
			p.setLoggedIn(true);
			ShowProfil sp = new ShowProfil();
			Navbar nb = new Navbar();
			VerticalPanel detailsPanel = new VerticalPanel();
			detailsPanel.add(sp);
			RootPanel.get("Navigator").clear();
			RootPanel.get("Navigator").add(nb);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(new Home());
			RootPanel.get("Details").add(detailsPanel);
		}

	}

}
