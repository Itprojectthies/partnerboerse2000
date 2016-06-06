package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.superteam2000.gwt.client.gui.DateTimeFormat;
import de.superteam2000.gwt.client.gui.ProfilAttributeBoxPanel;
import de.superteam2000.gwt.client.gui.ProfilAttributeListBox;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Formular für die Darstellung des selektierten Kunden
 * 
 * @author Rathke, Volz
 */
public class ShowProfil extends BasicFrame {

	PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */

	ProfilAttributeBoxPanel gebTag = null;
	ProfilAttributeBoxPanel groesse = null;
	FlowPanel fPanel = new FlowPanel();
	FlowPanel fPanelEigenschaften = new FlowPanel();
	Button saveButton = new Button("Speichern");

	ProfilAttributeBoxPanel clb = null;

	Profil currentProfil = ClientsideSettings.getCurrentUser();
	Logger logger = ClientsideSettings.getLogger();

	@Override
	public String getHeadlineText() {
		return null;
	}

	@Override
	public void run() {

		final PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
		
//		pbVerwaltung.getInfoByProfile(user, new InfoCallback(this));

		// Geburtstags- und KörpergrößeListbox müssen seperat erstellt werden,
		// weil sie Speziallfälle
		// von ProfilAttributListBox und ProfilAttributtextBox sind

		gebTag = new ProfilAttributeBoxPanel("Geburtstag");
		gebTag.createGebtaListobx();
		gebTag.setGebtag(currentProfil.getGeburtsdatum());
		gebTag.setEnable(false);

		groesse = new ProfilAttributeBoxPanel("Körpergröße");
		groesse.createGroesseListBox();
		groesse.setGroesse(currentProfil.getGroesse());
		groesse.setEnable(false);

		// Profilbeschreibungsattribute (Vorname, Nachname) werden vom Server
		// abgefragt, damit sie als Textboxen
		// dargestellt werden können
		
		pbVerwaltung.getAllBeschreibungProfilAttribute(new GetAllBeschreibungProfilAttributeCallback());
		pbVerwaltung.getAllAuswahlProfilAttribute(new GetAllAuswahlProfilAttributeCallback());

		VerticalPanel menuButtonsPanel = new VerticalPanel();
		RootPanel.get("Menu").add(menuButtonsPanel);

		Button editButton = new Button("Bearbeiten");
		editButton.addClickHandler(new EditButtonClickHandler());
		menuButtonsPanel.add(editButton);

		saveButton.addClickHandler(new SaveButtonClickHandler());
		menuButtonsPanel.add(saveButton);
		saveButton.setEnabled(false);

		Button deleteBtn = new Button("Profil löschen");
		menuButtonsPanel.add(deleteBtn);
		deleteBtn.addClickHandler(new DeleteClickHandler());
		

		RootPanel.get("Details").add(fPanel);

	}

	private class EditButtonClickHandler implements ClickHandler {
		// Schleifen zum Auslesen der Listboxen und Textboxen, welche in 2
		// Panels verschachtelt sind, um diese bearbeitbar zu machen
		
		@Override
		public void onClick(ClickEvent event) {
			// Save Button klickbar machen
			saveButton.setEnabled(true);
			for (Widget child : fPanel) {
				FlowPanel childPanel = (FlowPanel) child;
				for (Widget box : childPanel) {
					if (box instanceof ProfilAttributeListBox) {
						ProfilAttributeListBox lb = (ProfilAttributeListBox) box;
						// Listbox auswählbar machen
						lb.setEnabled(true);
					} else if (box instanceof TextBox) {
						TextBox tb = (TextBox) box;
						// Textbox beschreibbar machen
						tb.setEnabled(true);
					}
				}
			}
		}
	}

	private class SaveButtonClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			Profil p = new Profil();

			int geburtsTag = 1;
			int geburtsMonat = 1;
			int geburtsJahr = 1900;

			// Schleifen zum Auslesen der Listboxen, welche in 2 Panels
			// verschachtelt sind

			for (Widget child : fPanel) {
				FlowPanel childPanel = (FlowPanel) child;
				for (Widget box : childPanel) {
					if (box instanceof ProfilAttributeListBox) {
						ProfilAttributeListBox lb = (ProfilAttributeListBox) box;

						switch (lb.getName()) {

							case "Raucher" :
								p.setRaucher(lb.getSelectedItemText());
								break;
							case "Haarfarbe" :
								p.setHaarfarbe(lb.getSelectedItemText());
								break;
							case "Religion" :
								p.setReligion(lb.getSelectedItemText());
								break;
							case "Geschlecht" :
								p.setGeschlecht(lb.getSelectedItemText());
								break;
							case "Körpergröße" :
								p.setGroesse(Integer
										.valueOf(lb.getSelectedItemText()));
								break;
							case "GeburtstagTag" :
								geburtsTag = Integer
										.valueOf(lb.getSelectedItemText());
								break;
							case "GeburtstagMonat" :
								geburtsMonat = Integer
										.valueOf(lb.getSelectedItemText());
								break;
							case "GeburtstagJahr" :
								geburtsJahr = Integer
										.valueOf(lb.getSelectedItemText());
								break;

						}

					} else if (box instanceof TextBox) {
						TextBox tb = (TextBox) box;
						switch (tb.getName()) {
							case "Vorname" :
								p.setVorname(tb.getText());
								break;
							case "Nachname" :
								p.setNachname(tb.getText());
								break;

						}
					}

				}

			}

			// Date-Objekt aus den 3 Geburtstagswerten Tag, Monat und Jahr
			// konstruieren und in
			// ein SQL-Date-Objekt umwandeln

			Date gebTagDate = DateTimeFormat.getFormat("yyyy-MM-dd")
					.parse(geburtsJahr + "-" + geburtsMonat + "-" + geburtsTag);

			java.sql.Date gebTagMySqlDate = new java.sql.Date(
					gebTagDate.getTime());

			p.setGeburtsdatum(gebTagMySqlDate);
			p.setEmail(currentProfil.getEmail());
			p.setId(currentProfil.getId());

			ClientsideSettings.setCurrentUser(p);

			pbVerwaltung.save(p, new SaveProfilCallBack());

		}
	}
	

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (Window.confirm("Möchtest du dein Profil wirklich löschen?")) {
				pbVerwaltung.delete(currentProfil, new AsyncCallback<Void>() {

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
	}

	private class SaveProfilCallBack implements AsyncCallback<Void> {
		
		@Override
		public void onSuccess(Void result) {
			logger.severe("Ändern der Profildaten hat funktioniert");
			
			ShowProfil sp = new ShowProfil();
			
			RootPanel.get("Details").clear();
			RootPanel.get("Menu").clear();
			RootPanel.get("Details").add(new Home());
			RootPanel.get("Details").add(sp);
		}
		
		@Override
		public void onFailure(Throwable caught) {
			logger.severe("Ändern der Profildaten hat nicht funktioniert");
		}
		
	}
	
	private class GetAllAuswahlProfilAttributeCallback implements AsyncCallback<ArrayList<Auswahl>> {
		@Override
		public void onSuccess(ArrayList<Auswahl> result) {
			for (Auswahl a : result) {
				switch (a.getName()) {
				case "Religion":
					clb = new ProfilAttributeBoxPanel(a, currentProfil.getReligion(), true);
					clb.setEnable(false);
					fPanel.add(clb);
					break;
				case "Haarfarbe":
					clb = new ProfilAttributeBoxPanel(a, currentProfil.getHaarfarbe(), true);
					clb.setEnable(false);
					fPanel.add(clb);
					break;
				case "Geschlecht":
					clb = new ProfilAttributeBoxPanel(a, currentProfil.getGeschlecht(), true);
					clb.setEnable(false);
					fPanel.add(clb);
					break;
				case "Raucher":
					clb = new ProfilAttributeBoxPanel(a, currentProfil.getRaucher(), true);
					clb.setEnable(false);
					fPanel.add(clb);
					break;

				default:
					break;
				}
			}

			// Körpergröße und Geburtstags Listboxen werden nach den
			// AuswahlProfilAttributen zum Panel hinzugefügt
			fPanel.add(groesse);
			fPanel.add(gebTag);
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}
	}

	private class GetAllBeschreibungProfilAttributeCallback implements AsyncCallback<ArrayList<Beschreibung>> {

		@Override
		public void onSuccess(ArrayList<Beschreibung> result) {
			for (Beschreibung b : result) {
				// Für die Beschreibung Vorname und Nachname wird eine
				// Textbox erstellt und mit den Werten des aktuellen Nutzers
				// befüllt
				switch (b.getName()) {
				case "Vorname":
					clb = new ProfilAttributeBoxPanel(b, currentProfil.getVorname(), true);
					clb.setEnable(false);
					fPanel.add(clb);
					break;
				case "Nachname":
					clb = new ProfilAttributeBoxPanel(b, currentProfil.getNachname(), true);
					clb.setEnable(false);
					fPanel.add(clb);
					break;
				default:
					break;
				}
			}
		}

		@Override
		public void onFailure(Throwable caught) {
			logger.severe("Erstellen der Beschreibungstextboxen (z.B. Vorname) fehlgeschlagen!");
		}
	}

}
