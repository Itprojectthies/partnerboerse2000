package de.superteam2000.gwt.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;

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
	TextBox emailTextBox = new TextBox();
	// DateBox gebDatumDateBox = new DateBox();
	TextBox gebDatumDateBox = new TextBox();
	TextBox haarfarbeTextBox = new TextBox();
	TextBox raucherTextBox = new TextBox();
	TextBox religionTextBox = new TextBox();
	TextBox geschlechtTextBox = new TextBox();
	IntegerBox koerpergroesseIntegerBox = new IntegerBox();

	Label test = new Label("");

	/*
	 * Im Konstruktor werden die anderen Widgets erzeugt. Alle werden in einem
	 * Raster angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen
	 * Widgets bestimmt.
	 */
	public CustomerForm() {
		Grid customerGrid = new Grid(11, 2);
		this.add(customerGrid);

		Label idLabel = new Label("ID");
		customerGrid.setWidget(0, 0, idLabel);
		customerGrid.setWidget(0, 1, idValueLabel);

		Label firstNameLabel = new Label("Vorname");
		customerGrid.setWidget(1, 0, firstNameLabel);
		customerGrid.setWidget(1, 1, firstNameTextBox);

		Label lastNameLabel = new Label("Nachname");
		customerGrid.setWidget(2, 0, lastNameLabel);
		customerGrid.setWidget(2, 1, lastNameTextBox);

		Label emailLabel = new Label("Email");
		customerGrid.setWidget(3, 0, emailLabel);
		customerGrid.setWidget(3, 1, emailTextBox);

		// Label gebDatumLabel = new Label("Geburtstag");
		// customerGrid.setWidget(4, 0, gebDatumLabel);
		// customerGrid.setWidget(4, 1, gebDatumDateBox);

		Label gebDatumLabel = new Label("Geburtstag");
		customerGrid.setWidget(4, 0, gebDatumLabel);
		customerGrid.setWidget(4, 1, gebDatumDateBox);

		Label haarfarbeLabel = new Label("Haarfarbe");
		customerGrid.setWidget(5, 0, haarfarbeLabel);
		customerGrid.setWidget(5, 1, haarfarbeTextBox);

		Label raucherLabel = new Label("Raucher");
		customerGrid.setWidget(6, 0, raucherLabel);
		customerGrid.setWidget(6, 1, raucherTextBox);

		Label religionLabel = new Label("Religion");
		customerGrid.setWidget(7, 0, religionLabel);
		customerGrid.setWidget(7, 1, religionTextBox);

		Label geschlechtLabel = new Label("Geschlecht");
		customerGrid.setWidget(8, 0, geschlechtLabel);
		customerGrid.setWidget(8, 1, geschlechtTextBox);

		Label groesseLabel = new Label("Körpergröße");
		customerGrid.setWidget(9, 0, groesseLabel);
		customerGrid.setWidget(9, 1, koerpergroesseIntegerBox);

		HorizontalPanel customerButtonsPanel = new HorizontalPanel();
		this.add(customerButtonsPanel);

		Button newButton = new Button("Neu");
		newButton.addClickHandler(new NewClickHandler());
		customerButtonsPanel.add(newButton);

		customerButtonsPanel.add(test);
	}

	/*
	 * Click Handlers.
	 */

	/**
	 * Auch wenn ein neuer Kunde hinzukommt, muss der Kunden- und Kontenbaum
	 * aktualisiert werden.
	 * 
	 */
	private class NewClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO: erstelldatum im adminimpl hinzufügen

			String firstName = firstNameTextBox.getText();
			String lastName = lastNameTextBox.getText();
			String email = emailTextBox.getText();
			String geburtsdatum = gebDatumDateBox.getText();
			String haarfarbe = haarfarbeTextBox.getText();
			String raucher = raucherTextBox.getText();
			String religion = religionTextBox.getText();
			String geschlecht = geschlechtTextBox.getText();
			int groesse = koerpergroesseIntegerBox.getValue();
			pbVerwaltung.createProfil(lastName, firstName, email, geburtsdatum, haarfarbe, raucher, religion, groesse,
					geschlecht, new CreateCustomerCallback());

		}
	}

	class CreateCustomerCallback implements AsyncCallback<Profil> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Profil p) {
			test.setText("Der neue User heißt " + p.getVorname());

			firstNameTextBox.setText("");
			lastNameTextBox.setText("");
			idValueLabel.setText("");
		}

	}

	// @Override
	// public void onFailure(Throwable caught) {
	// Window.alert("Das Anlegen eines neuen Kunden ist fehlgeschlagen!");
	// }
	//
	// @Override
	// public void onSuccess(Profil profil) {
	// if (profil != null) {
	// // Das erfolgreiche Hinzufügen eines Kunden wird an den Kunden- und
	// // Kontenbaum propagiert.
	// catvm.addCustomer(customer);
	// }
	// }
	// }
	//
	// // catvm setter
	// void setCatvm(CustomerAccountsTreeViewModel catvm) {
	// this.catvm = catvm;
	// }

	/*
	 * Wenn der anzuzeigende Kunde gesetzt bzw. gelöscht wird, werden die
	 * zugehörenden Textfelder mit den Informationen aus dem Kundenobjekt
	 * gefüllt bzw. gelöscht.
	 */
	// void setSelected(Customer c) {
	// if (c != null) {
	// customerToDisplay = c;
	// firstNameTextBox.setText(customerToDisplay.getFirstName());
	// lastNameTextBox.setText(customerToDisplay.getLastName());
	// idValueLabel.setText(Integer.toString(customerToDisplay.getId()));
	// } else {
	// firstNameTextBox.setText("");
	// lastNameTextBox.setText("");
	// idValueLabel.setText("");
	// }
	// }

}
