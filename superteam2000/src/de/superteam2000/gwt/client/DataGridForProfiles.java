package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.report.ProfilReport;

/**
 * In dieser Klasse wird eine Liste aller Profile zusammengestellt
 * und kann ausgegeben werden. Dazu wird das Table Widget DataGrid verwendet,
 * um alle Profile welche ausgegeben werden sollen, in einer �bersichtlichen Form
 * darstellen zu k�nnen. Die ausgegebenen Profile werden nach ihrer prozentualen �bereinstimmung
 * (�hnlichkeitsma�) sortiert ausgegeben.
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
		this.isForSuchprofil = false;
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
	 * ??
	 * @param profilListe
	 */
	public void setProfilListe(ArrayList<Profil> profilListe) {
		this.profilListe = profilListe;
	}

	// pb Verwaltung über ClientsideSettings holen
	PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

	Profil profil = ClientsideSettings.getCurrentUser();
	
	/**
	 * ??
	 * 
	 * @param merkenButton Der Button wird erstellt, benannt und auf der Seite eingebunden (zum merken eines fremden Profils)
	 * @param sperrenButton Der Button wird angelegt, benannt und auf der Seite eingebunden (zum sperren eines fremden Profils)
	 * @param profilAnzeigenButton Der Button wird angelegt, benannt und auf der Seite eingebunden (Profil anzeigen allgemein)
	 * @param neueProfilAnzeigenButton Button wird angelegt, benannt und eingebunden (soll alle neu dazugekommenen Profile anzeigen)
	 * @param nichtBesuchteProfilAnzeigenButton Button wird angelegt, benannt und eingebunden (soll alle noch nicht besuchten Profile anzeigen)
	 * @param profileAnzeigenButton Button wird angelegt, benannt und eingebunden (soll alle Profile ausser dem eigenen anzeigen)
	 * @param hPanel Die Extra Buttons werden in einem horizontal Panel angeordnet und auf der Seite ausgegeben.
	 * @param table ??
	 * @param selectionModel Damit der User ausgew�hlt werden kann.
	 */
	@Override
	public void run() {

		final Button merkenButton = new Button("Profil merken");
		final Button sperrenButton = new Button("Profil sperren");
		final Button profilAnzeigenButton = new Button("Profil anzeigen");
		final Button neueProfilAnzeigenButton = new Button("Neue Profile anzeigen");
		final Button nichtBesuchteProfilAnzeigenButton = new Button("Nicht besuchte Profile anzeigen");
		final Button profileAnzeigenButton = new Button("Alle Profile anzeigen");
		
		VerticalPanel hPanel = new VerticalPanel();
		
		hPanel.add(profileAnzeigenButton);
		hPanel.add(neueProfilAnzeigenButton);
		hPanel.add(nichtBesuchteProfilAnzeigenButton);
		
		RootPanel.get("rechts").add(merkenButton);
		RootPanel.get("rechts").add(sperrenButton);
		RootPanel.get("rechts").add(profilAnzeigenButton);
		RootPanel.get("rechts").add(neueProfilAnzeigenButton);
		RootPanel.get("rechts").add(nichtBesuchteProfilAnzeigenButton);
		
		
		/*
		 * Der Button zum anzeigen aller neuen Profile wird dem Click Handler und damit Mouse Listener hinzugef�gt.
		 * Beim Dr�cken des Buttons wird die folgende Methode aufgerufen.
		 */
		neueProfilAnzeigenButton.addClickHandler(new ClickHandler() {

			/**
			 * Alle neuen Profile werden mit dem Aehnlichkeitsmass, passend zum eigenen Profil, berechnet.
			 */
			@Override
			public void onClick(ClickEvent event) {
				pbVerwaltung.getAllNewProfilesByAehnlichkeitsmass(profil, new AsyncCallback<ArrayList<Profil>>() {
					
					/**
					 * Die Seite wird neu aufgerufen mit allen neuen Profilen, sortiert nach Aehnlichkeitsmass.
					 * @param dgt Hierin werden die Profile gespeichert, die von der Anfrage zur�ckkommen.
					 */
					@Override
					public void onSuccess(ArrayList<Profil> result) {
						DataGridForProfiles dgt = new DataGridForProfiles(result);
						RootPanel.get("Details").clear();
						RootPanel.get("Menu").clear();
						RootPanel.get("rechts").clear();
						RootPanel.get("Details").add(dgt);
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				} );
			}
		});
		
		
		/*
		 * Der Button zum anzeigen aller bisher noch nicht besuchten Profile wird dem Click Handler und damit Mouse Listener hinzugef�gt.
		 * Beim Dr�cken des Buttons wird die folgende Methode aufgerufen.
		 */
		nichtBesuchteProfilAnzeigenButton.addClickHandler(new ClickHandler() {

			/**
			 * Alle noch nicht besuchten Profile werden ausgegeben, sortiert nach dem Aehnlichkeitsmass.
			 */
			@Override
			public void onClick(ClickEvent event) {
				pbVerwaltung.getAllNotVisitedProfilesByAehnlichkeitsmass(profil, new AsyncCallback<ArrayList<Profil>>() {
					
					/**
					 * Die Seite wird neu aufgerufen mit allen Profilen, die noch nicht besucht wurden, sortiert nach Aehnlichkeitsmass.
					 * @param dgt Hierin wird das Ergebnis der Anfrage gespeichert um ausgegeben werden zu k�nnen.
					 */
					@Override
					public void onSuccess(ArrayList<Profil> result) {
						DataGridForProfiles dgt = new DataGridForProfiles(result);
						RootPanel.get("Details").clear();
						RootPanel.get("rechts").clear();
						RootPanel.get("Menu").clear();
						RootPanel.get("Details").add(dgt);	
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
					}
				} );
			}
		});
		
		/*
		 * Der Button zum anzeigen aller Profile ausser dem eigenen wird dem Click Handler und damit Mouse Listener hinzugef�gt.
		 * Beim Dr�cken des Buttons wird die folgende Methode aufgerufen.
		 */
		profileAnzeigenButton.addClickHandler(new ClickHandler() {

			/**
			 * 
			 */
			@Override
			public void onClick(ClickEvent event) {
				pbVerwaltung.getProfilesByAehnlichkeitsmass(profil, new AsyncCallback<ArrayList<Profil>>() {
					
					@Override
					public void onSuccess(ArrayList<Profil> result) {
						DataGridForProfiles dgt = new DataGridForProfiles(result);
						RootPanel.get("Details").clear();
						RootPanel.get("rechts").clear();
						RootPanel.get("Menu").clear();
						RootPanel.get("Details").add(dgt);
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				} );


			}
		});
		
		//??
		DataGrid<Profil> table = new DataGrid<Profil>();
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		TextColumn<Profil> vorname = new TextColumn<Profil>() {
			
			/** 
			 * Der Vorname wird ausgelesen und anschliessend in die Tabelle hinzugef�gt.
			 */
			@Override
			public String getValue(Profil p) {
				return p.getVorname();
			}
		};
		table.addColumn(vorname, "Vorname");

		TextColumn<Profil> nachname = new TextColumn<Profil>() {
			
			/**
			 * Der Nachname wird ausgelesen und anschliessend in die Tabelle eingef�gt.
			 * @return
			 */
			@Override
			public String getValue(Profil p) {
				return p.getNachname();
			}
		};
		table.addColumn(nachname, "Nachname");

		TextColumn<Profil> alter = new TextColumn<Profil>() {
			
			/**
			 * Das Alter wird ausgelesen und anschliessend in die Tabelle eingef�gt.
			 */
			@Override
			public String getValue(Profil p) {
				return String.valueOf(p.getAlter());
			}
		};
		table.addColumn(alter, "Alter");

		TextColumn<Profil> aehnlichkeit = new TextColumn<Profil>() {
			
			/**
			 * Die Aehnlichkeitsmessung wird gesucht und in Prozent zur�ckgegeben.
			 */
			@Override
			public String getValue(Profil p) {
				;

				return String.valueOf(p.getAehnlichkeit()) + "%";
			}
		};
		
		/**
		 *??
		 */
		if (!isForSuchprofil) {
			table.addColumn(aehnlichkeit, "Ähnlichkeit");
		}
		
		
		// Add a selection model to handle user selection. //??
		final SingleSelectionModel<Profil> selectionModel = new SingleSelectionModel<Profil>();
		table.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new Handler() {

			/**
			 * Diese Methode gew�hrleistet eine entsprechende Reaktion, wenn ein Button gedr�ckt wird.
			 * Die Seite wird nicht aktualisiert, die Aenderung laeuft im Hintergrund.
			 */
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				selected = selectionModel.getSelectedObject();

				sperrenButton.addClickHandler(new SperrenButtonClickhandler());
				merkenButton.addClickHandler(new MerkenButtonClickhandler());
				profilAnzeigenButton.addClickHandler(new ProfilAnzeigenButtonClickhandler());
			}
		});

		/**
		 * Ausgabe der Profile
		 */
		table.setRowCount(profilListe.size(), true);
		table.setRowData(0, profilListe);
		table.setWidth("80%");

		LayoutPanel panel = new LayoutPanel();
		panel.setSize("40em", "50em");
		panel.add(table);
		FlowPanel fPanel = new FlowPanel();
		fPanel.add(panel);
		RootPanel.get("rechts").add(panel);
		RootPanel.get("Details").add(hPanel);
		

	}

	/**
	 * Wenn der User den Button "Sperren" dr�ckt, soll das Profil des bestimmten anderen Users,
	 * gesperrt werden. Das Profil wird zur Sperrliste hinzugef�gt und soll nirgends mehr angezeigt werden.
	 * Daf�r muss aber der bestimmte User ausgew�hlt sein.
	 */
	public class SperrenButtonClickhandler implements ClickHandler {
		
		/**
		 * Sobald der Button gedr�ckt wird, wird das Profil der Sperrliste hinzugef�gt.
		 */
		@Override
		public void onClick(ClickEvent event) {
			if (selected != null) {
				ClientsideSettings.getPartnerboerseVerwaltung().createSperre(ClientsideSettings.getCurrentUser(),
						selected, new AsyncCallback<Void>() {

							/**
							 * Die rechte Navigationsbar wird aktualisiert und das Profil aus der aktuellen Liste der angezeigten
							 * Profile entfernt, da es gesperrt wurde.
							 */
							@Override
							public void onSuccess(Void result) {
								RootPanel.get("rechts").clear();
								profilListe.remove(selected);
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
	 * Wenn der User den Button "merken" dr�ckt, soll das Profil auf seiner Merkliste gespeichert
	 * werden. Daf�r muss aber der bestimmte User ausgew�hlt sein.
	 */
	public class MerkenButtonClickhandler implements ClickHandler {
		
		/**
		 * Sobald der Button gedr�ckt wird, wird das Profil der Merkliste hinzugef�gt.
		 */
		@Override
		public void onClick(ClickEvent event) {
			if (selected != null) {

				ClientsideSettings.getPartnerboerseVerwaltung().createMerken(ClientsideSettings.getCurrentUser(),
						selected, new AsyncCallback<Void>() {

					/**
					 * Die Liste der aktuell angezeigten Profile wird aktualisiert sowie die rechte Navigationsbar.
					 */
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
	 * Wird der Button "Profil anzeigen" gedr�ckt, soll das Profil eines anderen Users angezeigt werden.
	 * Daf�r muss aber der bestimmte User ausgew�hlt sein. Zeitgleich wird der User als "bereits besucht markiert"
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
					 * Wenn das selektierte Profil bestimmt werden konnte, wird es f�r den momentanen User
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
