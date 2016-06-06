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
 * Diese Klasse ist zum Anzeigen sämtlicher vom Benutzer gemerkten Profile.
 * 
 * @author Christopher
 *
 */
public class Merkliste extends BasicFrame {

	// pb Verwaltung über ClientsideSettings holen
	PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

	@Override
	public String getHeadlineText() {

		return "Von ihnen gemerkte Profile:";
	}
	
	//Das ausgewählte Profil
	private Profil selected = null;

	ArrayList<Profil> profile = new ArrayList<>();

	@Override
	public void run() {


		// Merkliste abfragen und anzeigen
		pbVerwaltung.getMerkzettelForProfil(ClientsideSettings.getCurrentUser(), new AsyncCallback<Merkzettel>() {

			@Override
			public void onSuccess(Merkzettel result) {

				profile = result.getGemerkteProfile();
				
				//Buttons erzeugen
				final Button profilEntfernenButton = new Button("Profil entfernen");
				final Button profilAnzeigenButton = new Button("Profil anzeigen");
				
				//Button hinzufügen
				RootPanel.get("Details").add(profilEntfernenButton);
				RootPanel.get("Details").add(profilAnzeigenButton);
				
				//DataGrid Mit den gemerkten Profilen erstellen
				DataGrid<Profil> table = new DataGrid<Profil>();
				table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

				TextColumn<Profil> vorname = new TextColumn<Profil>() {
					@Override
					public String getValue(Profil p) {
						return p.getVorname();
					}
				};
				table.addColumn(vorname, "Vorname");

				TextColumn<Profil> nachname = new TextColumn<Profil>() {
					@Override
					public String getValue(Profil p) {
						return p.getNachname();
					}
				};
				table.addColumn(nachname, "Nachname");

				TextColumn<Profil> alter = new TextColumn<Profil>() {
					@Override
					public String getValue(Profil p) {
						return String.valueOf(p.getAlter());
					}
				};
				table.addColumn(alter, "Alter");

				// Add a selection model to handle user selection.
				final SingleSelectionModel<Profil> selectionModel = new SingleSelectionModel<Profil>();
				table.setSelectionModel(selectionModel);
				selectionModel.addSelectionChangeHandler(new Handler() {

					@Override
					public void onSelectionChange(SelectionChangeEvent event) {
						//ausgewähltes Profil setzen
						selected = selectionModel.getSelectedObject();
						
						profilEntfernenButton.addClickHandler(new EntfernenButtonClickhandler());
						profilAnzeigenButton.addClickHandler(new ProfilAnzeigenButtonClickhandler());


					}
				});
				table.setRowCount(profile.size(), true);
				table.setRowData(0, profile);
				table.setWidth("100%");

				LayoutPanel panel = new LayoutPanel();
				panel.setSize("80em", "50em");
				panel.add(table);
				RootPanel.get("Details").add(panel);

			}

			@Override
			public void onFailure(Throwable caught) {
				
				ClientsideSettings.getLogger().info("Fehler AsyncCallback Merkzettel in Merkliste");

			}
		});

	}
	/**
	 * Clickhandler für den entfernenButton
	 * ausgewähltes Element wird von der Liste entfernt (auch aus db)
	 * @author Christopher
	 *
	 */
	public class EntfernenButtonClickhandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (selected != null) {
				ClientsideSettings.getPartnerboerseVerwaltung().deleteMerken
				(ClientsideSettings.getCurrentUser(), selected, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						RootPanel.get("Details").clear();
						Merkliste m = new Merkliste();
						RootPanel.get("Details").add(m);
						
						Window.alert("Profil wurde von der Merkliste entfernt!");
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
				
			}
			
		}
	}
	/**
	 * Clickhandler der das ausgewählte Profil anzeigt,
	 *   und es als besucht markiert
	 * @author Christopher
	 *
	 */
	public class ProfilAnzeigenButtonClickhandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
		if (selected != null) {

			ClientsideSettings.getReportGenerator().createProfilReport(selected, new AsyncCallback<ProfilReport>() {
				
				@Override
				public void onSuccess(ProfilReport result) {
					
					RootPanel.get("Details").clear();
					FremdProfil fp = new FremdProfil(selected);
					RootPanel.get("Details").add(fp);
					
					//Profil als besucht setzen
					pbVerwaltung.setVisited(ClientsideSettings.getCurrentUser(), selected, new AsyncCallback<Void>() {
						
						@Override
						public void onSuccess(Void result) {
							ClientsideSettings.getLogger().info("User wurde als besucht markiert!");
							
						}
						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}
					});
					
					
					
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}
	}

}
