package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Formular für die Darstellung des selektierten Kunden
 * 
 * @author Christian Rathke
 */
public class ShowProfil extends BasicFrame {

	PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */
	TextBox firstNameTextBox = new TextBox();
	TextBox lastNameTextBox = new TextBox();
	Label idValueLabel = new Label();
	TextBox emailTextBox = new TextBox();
	TextBox alterTextBox = new TextBox();
	TextBox haarfarbeTextBox = new TextBox();
	TextBox raucherTextBox = new TextBox();
	TextBox religionTextBox = new TextBox();
	TextBox geschlechtTextBox = new TextBox();
	IntegerBox koerpergroesseIntegerBox = new IntegerBox();
	ListBox gebDatumTagListBox = new ListBox();
	ListBox gebDatumMonatListBox = new ListBox();
	ListBox gebDatumJahrListBOx = new ListBox();
	

	Button saveButton = new Button("Speichern");
	DialogBox dialogBox = new DialogBox();

	Profil user = ClientsideSettings.getCurrentUser();
	Logger logger = ClientsideSettings.getLogger();

	/*
	 * Im Konstruktor werden die anderen Widgets erzeugt. Alle werden in einem
	 * Raster angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen
	 * Widgets bestimmt.
	 */

	@Override
	protected String getHeadlineText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void run() {
		final PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
		Grid customerGrid = new Grid(11, 4);
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

		Label alterLabel = new Label("Alter");
		customerGrid.setWidget(4, 0, alterLabel);
		customerGrid.setWidget(4, 1, alterTextBox);
		alterTextBox.setText(String.valueOf(user.getAlter()));
		alterTextBox.setEnabled(false);
		
//		Label gebDatumLabel = new Label("Geburtstag");
//		customerGrid.setWidget(5, 0, gebDatumLabel);
//		customerGrid.setWidget(5, 1, gebDatumTagListBox);
//		customerGrid.setWidget(5, 2, gebDatumMonatListBox);
//		customerGrid.setWidget(5, 3, gebDatumJahrListBOx);
//		
//		for (int i = 1; i < 31; i++) {
//			gebDatumTagListBox.addItem(String.valueOf(i));
//		}
//		for (int i = 1; i < 12; i++) {
//			gebDatumMonatListBox.addItem(String.valueOf(i));
//		}
//		for (int i = 1900; i < 2000 ; i++) {
//			gebDatumJahrListBOx.addItem(String.valueOf(i));
//		}

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
		editButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				koerpergroesseIntegerBox.setEnabled(true);
				geschlechtTextBox.setEnabled(true);
				religionTextBox.setEnabled(true);
				raucherTextBox.setEnabled(true);
				haarfarbeTextBox.setEnabled(true);
				emailTextBox.setEnabled(false);
				lastNameTextBox.setEnabled(true);
				firstNameTextBox.setEnabled(true);
				saveButton.setEnabled(true);
			}
		});
		customerButtonsPanel.add(editButton);

		saveButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Profil p = new Profil();
				String vorname = firstNameTextBox.getText();
				String nachname = lastNameTextBox.getText();
				String haarfarbe = haarfarbeTextBox.getText();
				String raucher = raucherTextBox.getText();
				String religion = religionTextBox.getText();
				int groesse = koerpergroesseIntegerBox.getValue();
				int geburtsTag = Integer.valueOf(gebDatumTagListBox.getSelectedItemText());
				int geburtsMonat = Integer.valueOf(gebDatumMonatListBox.getSelectedItemText());
				int geburtsJahr = Integer.valueOf(gebDatumJahrListBOx.getSelectedItemText());
				Date gebTag2 = DateTimeFormat.getFormat("yyyy-MM-dd").parse(geburtsJahr +"-"+ geburtsMonat+"-"+geburtsTag);
				java.sql.Date gebTag = new java.sql.Date(gebTag2.getTime());
				p.setVorname(vorname);
				p.setNachname(nachname);
				p.setGeburtsdatum(gebTag);
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
		});
		customerButtonsPanel.add(saveButton);
		saveButton.setEnabled(false);

		Button deleteBtn = new Button("Profil löschen");
		deleteBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (Window.confirm("Möchtest du dein Profil wirklich löschen?")) {
					pbVerwaltung.delete(user, new AsyncCallback<Void>() {

						@Override
						public void onSuccess(Void result) {
							logger.severe("Profil gelöscht");

							VerticalPanel detailsPanel = new VerticalPanel();
							RootPanel.get("Details").clear();
							detailsPanel.add(new HTML("Profil erflogreich gelöscht"));
							RootPanel.get("Details").add(detailsPanel);
						}

						@Override
						public void onFailure(Throwable caught) {
							logger.severe("Fehler beim löschen des Profils");
						}
					});
				}
			}
		});
		customerButtonsPanel.add(deleteBtn);

		pbVerwaltung.getInfoByProfile(user, new InfoCallback(this));

	}

	class InfoCallback implements AsyncCallback<ArrayList<Info>> {

		private BasicFrame b = null;

		public InfoCallback(BasicFrame b) {
			this.b = b;
		}

		@Override
		public void onFailure(Throwable caught) {
			this.b.append("Fehler bei der Abfrage " + caught.getMessage());
		}

		@Override
		public void onSuccess(ArrayList<Info> result) {
			try {
				for (Info i : result) {
					if (result != null) {
//						this.b.append("Info #" + i.getId() + ": " + i.getText());
						pbVerwaltung.getAuswahlById(i.getEigenschaftId(), new GetAuswahlCallback(this.b, i));
						pbVerwaltung.getBeschreibungById(i.getEigenschaftId(), new GetBeschreibungCallback(this.b, i));
					} else {
						this.b.append("Result ist leer");
					}

				} 
			} catch (Exception e) {
				ClientsideSettings.getLogger().severe("Fehler " + e.getMessage());
			}
		}

	}


	class GetBeschreibungCallback implements AsyncCallback<Beschreibung> {


		private BasicFrame b = null;
		private Info i = null;
		public GetBeschreibungCallback(BasicFrame b, Info i) {
			this.b = b;
			this.i = i;
		}

		@Override
		public void onFailure(Throwable caught) {
			this.b.append("Fehler bei der Abfrage " + caught.getMessage());

		}

		@Override
		public void onSuccess(Beschreibung result) {
			this.b.append("Frage: " +result.getBeschreibungstext() + " Antwort: " + i.getText());
		}


	}

	class GetAuswahlCallback implements AsyncCallback<Auswahl> {


		private BasicFrame b = null;
		private Info i = null;
		public GetAuswahlCallback(BasicFrame b, Info i) {
			this.b = b;
			this.i = i;
		}

		@Override
		public void onFailure(Throwable caught) {
			this.b.append("Fehler bei der Abfrage " + caught.getMessage());

		}

		@Override
		public void onSuccess(Auswahl result) {
			this.b.append("Frage: " +result.getBeschreibungstext() + " Antwort: " + i.getText());
		}

	}
}

