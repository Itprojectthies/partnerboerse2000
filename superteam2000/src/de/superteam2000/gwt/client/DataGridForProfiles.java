package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.report.ProfilReport;

/**
 * In dieser Klasse wird eine Liste aller Profile zusammengestellt
 * und kann ausgegeben werden. Dazu wird das Table Widget DataGrid verwendet,
 * um alle Profile welche ausgegeben werden sollen, in einer übersichtlichen Form
 * darstellen zu können. Die ausgegebenen Profile werden nach ihrer prozentualen Übereinstimmung
 * (Ähnlichkeitsmaß) sortiert ausgegeben.
 * 
 * @param profilListe Hierin werden alle Profile in einer Liste gespeichert.
 * @param profil Hierin wird das aktuell ausgelesene Profil gespeichert.
 * @param isForSuchprofil Dies dient der Sortierung der Profile.
 *
 */
public class DataGridForProfiles extends BasicFrame {

	private ArrayList<Profil> profilListe = new ArrayList<>();

	/*
	 * ??
	 */
	public DataGridForProfiles(ArrayList<Profil> list, boolean isForSuchprofil) {
		this(list);
		this.isForSuchprofil = isForSuchprofil;
	}
	
	/*
	 * ??
	 */
	public DataGridForProfiles(ArrayList<Profil> list) {
		this.profilListe = list;
	}

	private Profil selected = null;
	private boolean isForSuchprofil = false;
	
	@Override
	public String getHeadlineText() {
		return null;
	}

	/**
	 * Die Profilliste wird ausgegeben
	 */
	public ArrayList<Profil> getProfilListe() {
		return profilListe;
	}

	/**
	 * 
	 * @param profilListe
	 */
	public void setProfilListe(ArrayList<Profil> profilListe) {
		this.profilListe = profilListe;
	}

	// pb Verwaltung Ã¼ber ClientsideSettings holen
	PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

	/**
	 * ??
	 * 
	 * @param merkenButton Der Button wird erstellt, benannt und auf der Seite eingebunden.
	 * @param sperrenButton Der Button wird angelegt, benannt und auf der Seite eingebunden.
	 * @param profilAnzeigenButton Der Button wird angelegt, benannt und auf der Seite eingebunden.
	 * @param table ??
	 * @param selectionModel Damit der User ausgewählt werden kann.
	 */
	@Override
	public void run() {

		final Button merkenButton = new Button("Profil merken");
		final Button sperrenButton = new Button("Profil sperren");
		final Button profilAnzeigenButton = new Button("Profil anzeigen");
		RootPanel.get("rechts").add(merkenButton);
		RootPanel.get("rechts").add(sperrenButton);
		RootPanel.get("rechts").add(profilAnzeigenButton);

		// eigenes Profil aus der Liste entfernen
		// if(profile.contains(ClientsideSettings.getCurrentUser())){
		// profile.remove(ClientsideSettings.getCurrentUser());
		// }

		DataGrid<Profil> table = new DataGrid<Profil>();
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		TextColumn<Profil> vorname = new TextColumn<Profil>() {
			
			// Der Vorname wird ausgelesen und anschliessend in die Tabelle hinzugefügt.
			@Override
			public String getValue(Profil p) {
				return p.getVorname();
			}
		};
		table.addColumn(vorname, "Vorname");

		// Der Nachname wird ausgelesen und anschliessend in die Tabelle eingefügt.
		TextColumn<Profil> nachname = new TextColumn<Profil>() {
			@Override
			public String getValue(Profil p) {
				return p.getNachname();
			}
		};
		table.addColumn(nachname, "Nachname");

		// Das Alter wird ausgelesen und anschliessend in die Tabelle eingefügt.
		TextColumn<Profil> alter = new TextColumn<Profil>() {
			@Override
			public String getValue(Profil p) {
				return String.valueOf(p.getAlter());
			}
		};
		table.addColumn(alter, "Alter");

		// Die Ähnlichkeitsmessung wird gesucht und in Prozent zurückgegeben.
		TextColumn<Profil> aehnlichkeit = new TextColumn<Profil>() {
			@Override
			public String getValue(Profil p) {
				;

				return String.valueOf(p.getAehnlichkeit()) + "%";
			}
		};
		
		if (!isForSuchprofil) {
			table.addColumn(aehnlichkeit, "Ã„hnlichkeit");
		}
		
		
		// Add a selection model to handle user selection.
		final SingleSelectionModel<Profil> selectionModel = new SingleSelectionModel<Profil>();
		table.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new Handler() {

			/**
			 * Diese Methode gewährleistet eine entsprechende Reaktion, wenn ein Button gedrückt wird.
			 */
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				selected = selectionModel.getSelectedObject();

				sperrenButton.addClickHandler(new SperrenButtonClickhandler());
				merkenButton.addClickHandler(new MerkenButtonClickhandler());
				profilAnzeigenButton.addClickHandler(new ProfilAnzeigenButtonClickhandler());
			}
		});

		/*
		 * Ausgabe der Profile
		 */
		table.setRowCount(profilListe.size(), true);
		table.setRowData(0, profilListe);
		table.setWidth("100%");

		LayoutPanel panel = new LayoutPanel();
		panel.setSize("40em", "50em");
		panel.add(table);
		RootPanel.get("rechts").add(panel);

	}

	/**
	 * Wenn der User den Button "Sperren" drückt, soll das Profil des bestimmten anderen Users,
	 * gesperrt werden. Das Profil wird zur Sperrliste hinzugefügt und soll nirgends mehr angezeigt werden.
	 * Dafür muss aber der bestimmte User ausgewählt sein.
	 */
	public class SperrenButtonClickhandler implements ClickHandler {
		
		/**
		 * Sobald der Button gedrückt wird, wird das Profil der Sperrliste hinzugefügt.
		 */
		@Override
		public void onClick(ClickEvent event) {
			if (selected != null) {
				ClientsideSettings.getPartnerboerseVerwaltung().createSperre(ClientsideSettings.getCurrentUser(),
						selected, new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						RootPanel.get("rechts").clear();
						DataGridForProfiles d = new DataGridForProfiles(profilListe);
						RootPanel.get("rechts").add(d);
						// Window.alert("Profil gesperrt!");
					}

					@Override
					public void onFailure(Throwable caught) {

					}
				});
			}
		}
	}

	/**
	 * Wenn der User den Button "merken" drückt, soll das Profil auf seiner Merkliste gespeichert
	 * werden. Dafür muss aber der bestimmte User ausgewählt sein.
	 */
	public class MerkenButtonClickhandler implements ClickHandler {
		
		/**
		 * Sobald der Button gedrückt wird, wird das Profil der Merkliste hinzugefügt.
		 */
		@Override
		public void onClick(ClickEvent event) {
			if (selected != null) {

				ClientsideSettings.getPartnerboerseVerwaltung().createMerken(ClientsideSettings.getCurrentUser(),
						selected, new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						RootPanel.get("rechts").clear();
						DataGridForProfiles d = new DataGridForProfiles(profilListe);
						RootPanel.get("rechts").add(d);
						// Window.alert("Profil gemerkt.");

					}

					@Override
					public void onFailure(Throwable caught) {

					}
				});
			}
		}
	}

	/**
	 * Wird der Button "Profil anzeigen" gedrückt, soll das Profil eines anderen Users angezeigt werden.
	 * Dafür muss aber der bestimmte User ausgewählt sein. Zeitgleich wird der User als "bereits besucht markiert"
	 * und kann somit nicht mehr in der Anzeige der neuen Profile auftauchen.
	 */
	public class ProfilAnzeigenButtonClickhandler implements ClickHandler {
		
		/**
		 * Der selektierte User wird ausgelesen und bestimmt.
		 * @param fp Die Daten des selektierten anderen Users werden hierin gespeichert.
		 */
		@Override
		public void onClick(ClickEvent event) {
			if (selected != null) {
				FremdProfil fp = new FremdProfil(selected);
				RootPanel.get("rechts").clear();
				RootPanel.get("rechts").add(fp);

				ClientsideSettings.getReportGenerator().createProfilReport(selected, new AsyncCallback<ProfilReport>() {

					/**
					 * Wenn das selektierte Profil bestimmt werden konnte, wird es für den momentanen User
					 * als bereits besucht markiert, damit es in der Liste der neuen Profile nicht mehr auftauchen kann.
					 */
					@Override
					public void onSuccess(ProfilReport result) {

						// Profil als besucht setzen
						pbVerwaltung.setVisited(ClientsideSettings.getCurrentUser(), selected,
								new AsyncCallback<Void>() {

							
							@Override
							public void onSuccess(Void result) {
								ClientsideSettings.getLogger().info("User wurde als besucht markiert!");

							}

							@Override
							public void onFailure(Throwable caught) {

							}
						});

					}

					@Override
					public void onFailure(Throwable caught) {

					}
				});
			}
		}
	}

}
