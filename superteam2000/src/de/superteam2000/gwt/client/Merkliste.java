package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Merkzettel;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Diese Klasse ist zum Anzeigen sämtlicher vom Benutzer gemerkten Profile.
 * 
 * @author Christopher
 *
 */
public class Merkliste extends BasicFrame {

	@Override
	public String getHeadlineText() {

		return "Merkliste";
	}

	ArrayList<Profil> profile = new ArrayList<>();

	@Override
	public void run() {

		// pb Verwaltung über ClientsideSettings holen
		PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

		// Merkliste abfragen und anzeigen
		pbVerwaltung.getMerkzettelForProfil(ClientsideSettings.getCurrentUser(), new AsyncCallback<Merkzettel>() {

			@Override
			public void onSuccess(Merkzettel result) {
				ClientsideSettings.getLogger().info(String.valueOf(result.getMerkerId()));
				profile = result.getGemerkteProfile();

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
						Profil selected = selectionModel.getSelectedObject();
						if (selected != null) {
							ClientsideSettings.getPartnerboerseVerwaltung().deleteMerken
							(ClientsideSettings.getCurrentUser(), selected, new AsyncCallback<Void>() {
								
								@Override
								public void onSuccess(Void result) {
									
									Window.alert("Profil wurde von der Merkliste entfernt!");
									
								}
								
								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									
								}
							});
							
						}

					}
				});
				table.setRowCount(profile.size(), true);
				table.setRowData(0, profile);
				table.setWidth("100%");

				LayoutPanel panel = new LayoutPanel();
				panel.setSize("30em", "10em");
				panel.add(table);
				RootPanel.get("Details").add(panel);

			}

			@Override
			public void onFailure(Throwable caught) {
				
				ClientsideSettings.getLogger().info("Fehler AsyncCallback Merkzettel in Merkliste");

			}
		});

	}

}
