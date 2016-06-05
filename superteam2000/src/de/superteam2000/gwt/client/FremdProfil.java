package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Profil;

public class FremdProfil extends BasicFrame {

	PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
	Profil profil = null;
	int rowCounter = 7;
	FlexTable table = new FlexTable();

	public FremdProfil(Profil p) {
		this.profil = p;
	}

	// merken und sperren Buttons erstellen
	Button merkenButton = new Button("Profil merken");
	Button sperrenButton = new Button("Profil sperren");

	@Override
	protected String getHeadlineText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void run() {

		// hier werden den Buttons ihre jeweiligen Clickhandler übergeben
		merkenButton.addClickHandler(new MerkenButtonClickhandler());
		sperrenButton.addClickHandler(new SperrenButtonClickhandler());

		// InfoObjekte für das Profil abfragen
		pbVerwaltung.getInfoByProfile(profil, new InfoCallback(this));

		// Table wird mit Profilattributen befüllt
		table.setText(0, 0, "Vorname:");
		table.setText(0, 1, profil.getVorname());

		table.setText(1, 0, "Nachname:");
		table.setText(1, 1, profil.getNachname());

		table.setText(2, 0, "Alter:");
		table.setText(2, 1, String.valueOf(profil.getAlter()));

		table.setText(3, 0, "Geschlecht:");
		table.setText(3, 1, profil.getGeschlecht());

		table.setText(4, 0, "Größe:");
		table.setText(4, 1, String.valueOf(profil.getGroesse()));

		table.setText(5, 0, "Raucher");
		table.setText(5, 1, profil.getRaucher());

		table.setText(6, 0, "Haarfarbe");
		table.setText(6, 1, profil.getHaarfarbe());

		table.setText(7, 0, "Religion");
		table.setText(7, 1, profil.getReligion());

		RootPanel.get("Details").add(merkenButton);
		RootPanel.get("Details").add(sperrenButton);
		table.setStyleName("fremdProfilAnzeigen");
		RootPanel.get("Details").add(table);
		rowCounter++;

	}

	private class InfoCallback implements AsyncCallback<ArrayList<Info>> {

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
					if (i != null) {
						// table.setText(rowCounter, 1, i.getText());

						pbVerwaltung.getAuswahlById(i.getEigenschaftId(), new GetAuswahlCallback(this.b, i));
						pbVerwaltung.getBeschreibungById(i.getEigenschaftId(), new GetBeschreibungCallback(this.b, i));

						// pbVerwaltung.getBeschreibungById(i.getEigenschaftId(),
						// new GetBeschreibungCallback(this.b, i));
					} else {
						this.b.append("Result ist leer");
					}

				}
				// pbVerwaltung.getEigenschaftsNameById(i.getEigenschaftId(),
				// new AsyncCallback<String>() {
				//
				// @Override
				// public void onSuccess(String result) {
				//
				// table.setText(rowCounter, 0, result);
				// rowCounter++;
				// }
				//
				// @Override
				// public void onFailure(Throwable caught) {
				// // TODO Auto-generated method stub
				//
				// }
				// });

			} catch (Exception e) {
				ClientsideSettings.getLogger().severe("Fehler " + e.getMessage());
			}
		}

	}

	private class GetAuswahlCallback implements AsyncCallback<Auswahl> {

		private BasicFrame b = null;
		private Info i = null;
		HTML html = new HTML();

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

			table.setText(rowCounter, 0, result.getName());
			table.setText(rowCounter, 1, i.getText());
			rowCounter++;

		}

	}

	private class GetBeschreibungCallback implements AsyncCallback<Beschreibung> {

		private BasicFrame b = null;
		private Info i = null;
		HTML html = new HTML();

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
			table.setText(rowCounter, 0, result.getName());
			table.setText(rowCounter, 1, i.getText());
			rowCounter++;

		}

	}

	public class SperrenButtonClickhandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (profil != null) {
				ClientsideSettings.getPartnerboerseVerwaltung().createSperre(ClientsideSettings.getCurrentUser(),
						profil, new AsyncCallback<Void>() {

							@Override
							public void onSuccess(Void result) {
								Window.alert("Profil gesperrt!");
							}

							@Override
							public void onFailure(Throwable caught) {
								// TODO
								// Auto-generated
								// method
								// stub

							}
						});
			}

		}
	}

	public class MerkenButtonClickhandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (profil != null) {

				ClientsideSettings.getPartnerboerseVerwaltung().createMerken(ClientsideSettings.getCurrentUser(),
						profil, new AsyncCallback<Void>() {

							@Override
							public void onSuccess(Void result) {
								Window.alert("Profil gemerkt.");

							}

							@Override
							public void onFailure(Throwable caught) {
								// TODO
								// Auto-generated
								// method
								// stub

							}
						});

			}

		}
	}

}
