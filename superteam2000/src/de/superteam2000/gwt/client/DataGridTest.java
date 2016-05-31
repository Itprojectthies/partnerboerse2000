package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.report.HTMLReportWriter;
import de.superteam2000.gwt.shared.report.ProfilReport;

public class DataGridTest extends BasicFrame {

	
	@Override
	public String getHeadlineText() {
		// TODO Auto-generated method stub
		return null;
	}
	
	ArrayList<Profil> profile = new ArrayList<>();

	@Override
	public void run() {

		
		//pb Verwaltung über ClientsideSettings holen
		PartnerboerseAdministrationAsync pbVerwaltung = 
				ClientsideSettings.getPartnerboerseVerwaltung();

		//Alle Profile aus der db holen
		pbVerwaltung.getAllProfiles(new AsyncCallback<ArrayList<Profil>>() {

			@Override
			public void onSuccess(ArrayList<Profil> result) {
				if(result != null){
					profile = result;
					ClientsideSettings.getLogger().
					info("AsyncCallback alle Profile hat funktioniert " + profile.size());	
					
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

					TextColumn<Profil> id = new TextColumn<Profil>() {
						@Override
						public String getValue(Profil p) {
							return String.valueOf(p.getId());
						}
					};


					// Add a selection model to handle user selection.
					final SingleSelectionModel<Profil> selectionModel = new SingleSelectionModel<Profil>();
					table.setSelectionModel(selectionModel);
					selectionModel.addSelectionChangeHandler(new Handler() {

						@Override
						public void onSelectionChange(SelectionChangeEvent event) {
							Profil selected = selectionModel.getSelectedObject();
							if (selected != null) {
//								Window.alert("You selected: " + selected.getNachname() + " " + selected.getVorname() + " "
//										+ selected.getAlter() + " " + selected.getEmail() + " " + selected.getHaarfarbe());
								ClientsideSettings.getReportGenerator().createProfilReport(selected, new AsyncCallback<ProfilReport>() {
									
									@Override
									public void onSuccess(ProfilReport result) {
										HTMLReportWriter writer = new HTMLReportWriter();
										writer.process(result);
										RootPanel.get("Details").clear();
										HTML html = new HTML(writer.getReportText());
										RootPanel.get("Details").add(html);
										
										
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
					
					
//					SimpleLayoutPanel slp = new SimpleLayoutPanel();
//					slp.add(table);
//					HTML html = new HTML(""+profile.size());
//					LayoutPanel lp = new LayoutPanel();
//					lp.add(table);
//					
//					// Add it to the root panel.
//					RootLayoutPanel.get().add(lp);
//					RootPanel.get("Details").add(lp);
//					RootPanel.get("Details").add(html);
					
					
				}

			}

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().
				info("Fehler AsyncCallback alle Profile");

			}
		});
		
		

	}

}
