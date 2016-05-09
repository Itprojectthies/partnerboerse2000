package de.superteam2000.gwt.client;

import java.util.logging.Logger;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.client.Home;
import de.superteam2000.gwt.client.ShowProfil_old;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Formular für die Darstellung des selektierten Kunden
 * 
 * @author Christian Rathke
 */
public class ShowProfil extends VerticalPanel {

	PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */
	TextBox firstNameTextBox = new TextBox();
	TextBox lastNameTextBox = new TextBox();
	Label idValueLabel = new Label();
	TextBox emailTextBox = new TextBox();
	TextBox gebDatumDateBox = new TextBox();
	TextBox haarfarbeTextBox = new TextBox();
	TextBox raucherTextBox = new TextBox();
	TextBox religionTextBox = new TextBox();
	TextBox geschlechtTextBox = new TextBox();
	IntegerBox koerpergroesseIntegerBox = new IntegerBox();
	
	Button saveButton = new Button("Speichern");
	DialogBox dialogBox = new DialogBox();
	
	Profil user = ClientsideSettings.getCurrentUser();
	Logger logger = ClientsideSettings.getLogger();

	/*
	 * Im Konstruktor werden die anderen Widgets erzeugt. Alle werden in einem
	 * Raster angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen
	 * Widgets bestimmt.
	 */
	public ShowProfil() {
		
		Grid customerGrid = new Grid(11, 2);
		this.add(customerGrid);

		Label firstNameLabel = new Label("Vorname");
		customerGrid.setWidget(1, 0, firstNameLabel);
		customerGrid.setWidget(1, 1, firstNameTextBox);
		firstNameTextBox.setText(user.getVorname());
		firstNameTextBox.setEnabled(false);
		
		Label lastNameLabel = new Label("Nachname");
		customerGrid.setWidget(2, 0, lastNameLabel);
		customerGrid.setWidget(2, 1, lastNameTextBox);
		lastNameTextBox.setText(user.getNachname());
		lastNameTextBox.setEnabled(false);
		
		Label emailLabel = new Label("Email");
		customerGrid.setWidget(3, 0, emailLabel);
		customerGrid.setWidget(3, 1, emailTextBox);
		emailTextBox.setText(user.getEmail());
		emailTextBox.setEnabled(false);

		Label gebDatumLabel = new Label("Geburtstag");
		customerGrid.setWidget(4, 0, gebDatumLabel);
		customerGrid.setWidget(4, 1, gebDatumDateBox);
		gebDatumDateBox.setText(user.getGeburtsdatum());
		gebDatumDateBox.setEnabled(false);
		
		Label haarfarbeLabel = new Label("Haarfarbe");
		customerGrid.setWidget(5, 0, haarfarbeLabel);
		customerGrid.setWidget(5, 1, haarfarbeTextBox);
		haarfarbeTextBox.setText(user.getHaarfarbe());
		haarfarbeTextBox.setEnabled(false);
		
		Label raucherLabel = new Label("Raucher");
		customerGrid.setWidget(6, 0, raucherLabel);
		customerGrid.setWidget(6, 1, raucherTextBox);
		raucherTextBox.setText(user.getRaucher());
		raucherTextBox.setEnabled(false);
		
		Label religionLabel = new Label("Religion");
		customerGrid.setWidget(7, 0, religionLabel);
		customerGrid.setWidget(7, 1, religionTextBox);
		religionTextBox.setText(user.getReligion());
		religionTextBox.setEnabled(false);
		
		Label geschlechtLabel = new Label("Geschlecht");
		customerGrid.setWidget(8, 0, geschlechtLabel);
		customerGrid.setWidget(8, 1, geschlechtTextBox);
		geschlechtTextBox.setText(user.getGeschlecht());
		geschlechtTextBox.setEnabled(false);
		
		Label groesseLabel = new Label("Körpergröße");
		customerGrid.setWidget(9, 0, groesseLabel);
		customerGrid.setWidget(9, 1, koerpergroesseIntegerBox);
		koerpergroesseIntegerBox.setValue(user.getGroesse());
		koerpergroesseIntegerBox.setEnabled(false);
		
		HorizontalPanel customerButtonsPanel = new HorizontalPanel();
		this.add(customerButtonsPanel);

		Button editButton = new Button("Bearbeiten");
		editButton.addClickHandler(new NewClickHandlerEdit());
		customerButtonsPanel.add(editButton);
		
		saveButton.addClickHandler(new NewClickHandlerSave());
		customerButtonsPanel.add(saveButton);
		saveButton.setEnabled(false);
		
		Button deleteBtn = new Button("Profil löschen");
		deleteBtn.addClickHandler(new NewClickHandlerDelete());
		customerButtonsPanel.add(deleteBtn);

	}
	
	private class NewClickHandlerDelete implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			
		}
	}
	
	private class NewClickHandlerEdit implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			koerpergroesseIntegerBox.setEnabled(true);
			geschlechtTextBox.setEnabled(true);
			religionTextBox.setEnabled(true);
			raucherTextBox.setEnabled(true);
			haarfarbeTextBox.setEnabled(true);
			gebDatumDateBox.setEnabled(true);
			emailTextBox.setEnabled(false);
			lastNameTextBox.setEnabled(true);
			firstNameTextBox.setEnabled(true);
			saveButton.setEnabled(true);
			
		}
	}

	private class NewClickHandlerSave implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Profil p = new Profil();
			String vorname = firstNameTextBox.getText();
			String nachname = lastNameTextBox.getText();
			String geburtsdatum = gebDatumDateBox.getText();
			String haarfarbe = haarfarbeTextBox.getText();
			String raucher = raucherTextBox.getText();
			String religion = religionTextBox.getText();
			int groesse = koerpergroesseIntegerBox.getValue();
			
			p.setVorname(vorname);
			p.setNachname(nachname);
			p.setGeburtsdatum(geburtsdatum);
			p.setHaarfarbe(haarfarbe);
			p.setRaucher(raucher);
			p.setReligion(religion);
			p.setGroesse(groesse);
			p.setId(user.getId());
			
			p.setEmail(user.getEmail());
			p.setGeschlecht(user.getGeschlecht());
			ClientsideSettings.setCurrentUser(p);
			pbVerwaltung.save(p, new AsyncCallback<Void>() {
				
				@Override
				public void onSuccess(Void result) {
					// TODO Auto-generated method stub
					logger.severe("Ändern der Profildaten hat  funktioniert");
					ShowProfil sep = new ShowProfil();
					VerticalPanel detailsPanel = new VerticalPanel();
					detailsPanel.add(sep);
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(new Home());
					RootPanel.get("Details").add(detailsPanel);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					logger.severe("Ändern der Profildaten hat nicht funktioniert");
				}
			});
			
		}
	}
}
