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

public class DataGridForProfiles extends BasicFrame {

	private ArrayList<Profil> profilListe = new ArrayList<>();

	public DataGridForProfiles(ArrayList<Profil> list, boolean isForSuchprofil) {
		this(list);
		this.isForSuchprofil = isForSuchprofil;
	}
	
	public DataGridForProfiles(ArrayList<Profil> list) {
		this.profilListe = list;
	}

	private Profil selected = null;
	private boolean isForSuchprofil = false;
	
	@Override
	public String getHeadlineText() {
		return null;
	}

	public ArrayList<Profil> getProfilListe() {
		return profilListe;
	}

	public void setProfilListe(ArrayList<Profil> profilListe) {
		this.profilListe = profilListe;
	}

	// pb Verwaltung über ClientsideSettings holen
	PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

	Profil profil = ClientsideSettings.getCurrentUser();
	
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
		
		neueProfilAnzeigenButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				pbVerwaltung.getAllNewProfilesByAehnlichkeitsmass(profil, new AsyncCallback<ArrayList<Profil>>() {
					
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
		
		nichtBesuchteProfilAnzeigenButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				pbVerwaltung.getAllNotVisitedProfilesByAehnlichkeitsmass(profil, new AsyncCallback<ArrayList<Profil>>() {
					
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
		
		profileAnzeigenButton.addClickHandler(new ClickHandler() {

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
		
		// eigenes Profil aus der Liste entfernen
		// if(profile.contains(ClientsideSettings.getCurrentUser())){
		// profile.remove(ClientsideSettings.getCurrentUser());
		// }

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

		TextColumn<Profil> aehnlichkeit = new TextColumn<Profil>() {
			@Override
			public String getValue(Profil p) {
				;

				return String.valueOf(p.getAehnlichkeit()) + "%";
			}
		};
		
		if (!isForSuchprofil) {
			table.addColumn(aehnlichkeit, "Ähnlichkeit");
		}
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

	public class SperrenButtonClickhandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (selected != null) {
				ClientsideSettings.getPartnerboerseVerwaltung().createSperre(ClientsideSettings.getCurrentUser(),
						selected, new AsyncCallback<Void>() {

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

	public class MerkenButtonClickhandler implements ClickHandler {
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

	public class ProfilAnzeigenButtonClickhandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (selected != null) {
				FremdProfil fp = new FremdProfil(selected);
				RootPanel.get("rechts").clear();
				RootPanel.get("rechts").add(fp);

				ClientsideSettings.getReportGenerator().createProfilReport(selected, new AsyncCallback<ProfilReport>() {

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
