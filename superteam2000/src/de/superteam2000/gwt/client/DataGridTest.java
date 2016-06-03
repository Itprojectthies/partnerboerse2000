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

import de.superteam2000.gwt.client.Suche.MerkenButtonClickhandler;
import de.superteam2000.gwt.client.Suche.ProfilAnzeigenButtonClickhandler;
import de.superteam2000.gwt.client.Suche.SperrenButtonClickhandler;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Kontaktsperre;
import de.superteam2000.gwt.shared.bo.Merkzettel;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.report.HTMLReportWriter;
import de.superteam2000.gwt.shared.report.ProfilReport;

public class DataGridTest extends BasicFrame {
	
	private Profil selected = null;

	
	@Override
	public String getHeadlineText() {
		// TODO Auto-generated method stub
		return null;
	}
	
	ArrayList<Profil> profile = new ArrayList<>();
	//pb Verwaltung über ClientsideSettings holen
	PartnerboerseAdministrationAsync pbVerwaltung = 
			ClientsideSettings.getPartnerboerseVerwaltung();

	@Override
	public void run() {

		

		//Alle Profile aus der db holen
		pbVerwaltung.getAllProfiles(new AsyncCallback<ArrayList<Profil>>() {

			@Override
			public void onSuccess(ArrayList<Profil> allProfilesResult) {
				
				final Button merkenButton = new Button("Profil merken");
				final Button sperrenButton = new Button("Profil sperren");
				final Button profilAnzeigenButton = new Button("Profil anzeigen");
				RootPanel.get("Details").add(merkenButton);
				RootPanel.get("Details").add(sperrenButton);
				RootPanel.get("Details").add(profilAnzeigenButton);
				
					profile = allProfilesResult;
					
					//eigenes Profil aus der Liste entfernen
					if(profile.contains(ClientsideSettings.getCurrentUser())){
						profile.remove(ClientsideSettings.getCurrentUser());
					}
					
					pbVerwaltung.getMerkzettelForProfil(ClientsideSettings.getCurrentUser(), new AsyncCallback<Merkzettel>() {
						
						@Override
						public void onSuccess(Merkzettel merkzettelResult) {
							ArrayList<Profil> gemerkteProfile = merkzettelResult.getGemerkteProfile();
							
							for(Profil p: gemerkteProfile){
								if(profile.contains(p)){
									profile.remove(p);
								}
							}
							//alle gesperrten Profile holen
							pbVerwaltung.getKontaktsperreForProfil(ClientsideSettings.getCurrentUser(),
									new AsyncCallback<Kontaktsperre>() {
								
								@Override
								public void onSuccess(Kontaktsperre kontaktsperreResult) {
									ArrayList<Profil> gesperrteProfile = kontaktsperreResult.getGesperrteProfile();
									
									//alle gesperrten Profile aus der Liste entfernen
									for(Profil p: gesperrteProfile){
										if(profile.contains(p)){
											profile.remove(p);
										}
									}
									
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
											 selected = selectionModel.getSelectedObject();										
												
												sperrenButton.addClickHandler(new SperrenButtonClickhandler());
												
												merkenButton.addClickHandler(new MerkenButtonClickhandler());
												
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

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().
				info("Fehler AsyncCallback alle Profile");

			}
		});
		
		

	}
	
	public class SperrenButtonClickhandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (selected != null) {
				ClientsideSettings.getPartnerboerseVerwaltung().createSperre(
						ClientsideSettings.getCurrentUser(), selected,
						new AsyncCallback<Void>() {

							@Override
							public void onSuccess(Void result) {
								RootPanel.get("Details").clear();
								DataGridTest d = new DataGridTest();
								RootPanel.get("Details").add(d);
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
			if (selected != null) {

				ClientsideSettings.getPartnerboerseVerwaltung().createMerken(
						ClientsideSettings.getCurrentUser(), selected,
						new AsyncCallback<Void>() {

							@Override
							public void onSuccess(Void result) {
								RootPanel.get("Details").clear();
								DataGridTest d = new DataGridTest();
								RootPanel.get("Details").add(d);
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
				;

			}

		}
	}
	public class ProfilAnzeigenButtonClickhandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
		if (selected != null) {

			ClientsideSettings.getReportGenerator().createProfilReport(selected, new AsyncCallback<ProfilReport>() {
				
				@Override
				public void onSuccess(ProfilReport result) {
					
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
					
					//Hier wird der Report prozessiert und ausgegeben
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
	}

}
