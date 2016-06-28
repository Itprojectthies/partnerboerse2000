package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Merkzettel;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.report.HTMLReportWriter;
import de.superteam2000.gwt.shared.report.ProfilReport;

/**
 * Diese Klasse ist zum Anzeigen saemtlicher vom Benutzer gemerkten Profile.
 * 
 * @param selected Hier wird das ausgewaehlte Profil gespeichert.
 * @author Christopher
 *
 */
public class Merkliste extends BasicFrame {

	// pb Verwaltung ueber ClientsideSettings holen
	PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

	/**
	 * Text ausgeben
	 */
	@Override
	public String getHeadlineText() {

		return "Von ihnen gemerkte Profile:";
	}
	
	private Profil selected = null;

	ArrayList<Profil> profile = new ArrayList<>();

	/**
	 * Methoden für Merkzettel enthalten.
	 */
	@Override
	public void run() {

		/**
		 * Die Merkliste wird abfragt und anschliessend anzeigt
		 */
		pbVerwaltung.getMerkzettelForProfil(ClientsideSettings.getCurrentUser(), new AsyncCallback<Merkzettel>() {

			/**
			 * 
			 * @param profile Hierin werden alle gemerkten Profile geschrieben.
			 * @param profilEntfernenButton Mithilfe dieses Buttons kann der User ein gemerktes Profil entfernen.
			 * @param profilAnzeigenButton Mit diesem Button kann der User ein gemerktes Profil anzeigen lassen.
			 * @param table In dieser Tabelle werden später alle Profile hineingeschrieben.
			 * @param selectionModel Damit soll die Auswahl der Userprofile gesteuert werden
			 */
			@Override
			public void onSuccess(Merkzettel result) {
				
				profile = result.getGemerkteProfile();
				
				final Button profilEntfernenButton = new Button("Profil entfernen");
				final Button profilAnzeigenButton = new Button("Profil anzeigen");
				
				/**
				 * Die zuvor erzeugten Buttons werden der Navigation hinzugefuegt.
				 */
				RootPanel.get("Details").add(profilEntfernenButton);
				RootPanel.get("Details").add(profilAnzeigenButton);
				
				//DataGrid Mit den gemerkten Profilen erstellen
				DataGrid<Profil> table = new DataGrid<Profil>();
				table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

				/**
				 * Vorname wird ausgelesen
				 * @return Vorname
				 */
				TextColumn<Profil> vorname = new TextColumn<Profil>() {
					@Override
					public String getValue(Profil p) {
						return p.getVorname();
					}
				};
				
				// Vorname wird Tabelle hinzugefuegt
				table.addColumn(vorname, "Vorname");

				/**
				 * Nachname wird ausgelesen
				 * @return Nachname
				 */
				TextColumn<Profil> nachname = new TextColumn<Profil>() {
					@Override
					public String getValue(Profil p) {
						return p.getNachname();
					}
				};
				// Nachname wird der Tabelle hinzugefuegt
				table.addColumn(nachname, "Nachname");

				/**
				 * Alter wird ausgelesen
				 * @return Alter
				 */
				TextColumn<Profil> alter = new TextColumn<Profil>() {
					@Override
					public String getValue(Profil p) {
						return String.valueOf(p.getAlter());
					}
				};
				// Alter wird der Tabelle hinzugefuegt
				table.addColumn(alter, "Alter");

				final SingleSelectionModel<Profil> selectionModel = new SingleSelectionModel<Profil>();
				table.setSelectionModel(selectionModel);
				selectionModel.addSelectionChangeHandler(new Handler() {

					/**
					 * Mit dem selektierten fremden Profil moechte der User etwas machen.
					 * @param selected ausgewaehltes Profil
					 */
					@Override
					public void onSelectionChange(SelectionChangeEvent event) {
						//ausgewaehltes Profil setzen
						selected = selectionModel.getSelectedObject();
						
						// Entfernen und Anzeigen Button dem ClickHandler hinzufuegen
						profilEntfernenButton.addClickHandler(new EntfernenButtonClickhandler());
						profilAnzeigenButton.addClickHandler(new ProfilAnzeigenButtonClickhandler());


					}
				});
				
				// Groesse der Tabelle wird festgelegt
				table.setRowCount(profile.size(), true);
				table.setRowData(0, profile);
				table.setWidth("100%");

				// Panel fuer die Tabelle wird generiert
				LayoutPanel panel = new LayoutPanel();
				panel.setSize("80em", "50em");
				panel.add(table);
				RootPanel.get("Details").add(panel);

			}

			/**
			 * Ausgabe einer Fehlermeldung falls etwas schief ging
			 */
			@Override
			public void onFailure(Throwable caught) {
				
				ClientsideSettings.getLogger().info("Fehler AsyncCallback Merkzettel in Merkliste");

			}
		});

	}
	/**
	 * Clickhandler fuer den entfernenButton von der Merkliste
	 * ausgewaehltes Element wird von der Liste entfernt (auch aus DB)
	 * @author Christopher
	 *
	 */
	public class EntfernenButtonClickhandler implements ClickHandler {
		
		/**
		 * Wenn Button gedrueckt wird
		 * @param selected ausgewaehltes Profil
		 */
		@Override
		public void onClick(ClickEvent event) {
			if (selected != null) {
				ClientsideSettings.getPartnerboerseVerwaltung().deleteMerken
				
				/**
				 * Selektierter User soll entfernt werden
				 */
				(ClientsideSettings.getCurrentUser(), selected, new AsyncCallback<Void>() {
					
					/**
					 * Profil wird von Merkliste entfernt, geaenderte Merkliste wird neu angezeigt.
					 * @param m Profile der Merkliste werden gespeichert.
					 */
					@Override
					public void onSuccess(Void result) {
						RootPanel.get("Details").clear();
						Merkliste m = new Merkliste();
						RootPanel.get("Details").add(m);
						
						Window.alert("Profil wurde von der Merkliste entfernt!");
						
					}
					
					/**
					 * um Fehler abzufangen
					 */
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
				
			}
			
		}
	}
	/**
	 * Clickhandler der das ausgewaehlte Profil anzeigt und es als besucht markiert
	 * @author Christopher
	 *
	 */
	public class ProfilAnzeigenButtonClickhandler implements ClickHandler {
		
		/**
		 * Wenn Button gedrueckt wird
		 * @param selected selektiertes Userprofil der Merkliste
		 */
		@Override
		public void onClick(ClickEvent event) {
		if (selected != null) {

			/**
			 * Report des Profils erstellen um angezeigt zu werden
			 */
			ClientsideSettings.getReportGenerator().createProfilReport(selected, new AsyncCallback<ProfilReport>() {
				
				/**
				 * @param fp enthaelt Daten und Informationen ueber anzuzeigendes Profil
				 */
				@Override
				public void onSuccess(ProfilReport result) {
					
					// Anzeige wird gecleart und anschliessend neu angezeigt mit fremden Userprofil
					RootPanel.get("Details").clear();
					FremdProfil fp = new FremdProfil(selected);
					RootPanel.get("Details").add(fp);
					
					//Profil als besucht setzen
					pbVerwaltung.setVisited(ClientsideSettings.getCurrentUser(), selected, new AsyncCallback<Void>() {
						
						/**
						 * Ausgabe einer Meldung dass Profil als besucht gesetzt wurde
						 */
						@Override
						public void onSuccess(Void result) {
							ClientsideSettings.getLogger().info("User wurde als besucht markiert!");
							
						}
						
						/**
						 * um Fehler abzufangen
						 */
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}
					});
				}
				
				/**
				 * um Fehler abzufangen
				 */
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}
	}

}
