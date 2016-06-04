package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

import de.superteam2000.gwt.client.gui.ProfilAttributeBoxPanel;
import de.superteam2000.gwt.client.gui.ProfilAttributeTextBox;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.bo.Suchprofil;

/**
 * Die Klasse Suche ist für die Darstellung von Möglichen Auswahlen und eine
 * anschließende Suche anhand dieser Kriterien
 * 
 * @author Christopher
 *
 */
public class Suche extends BasicFrame {

	ArrayList<Profil> profile = null;
	Profil profil = null;

	Button suchprofilCreateButton = new Button("Suchprofil erstellen");
	Button sucheButton = new Button("Suchen");

	ListBox lbRaucher = new ListBox();
	ListBox lbReligion = new ListBox();
	ListBox lbGeschlecht = new ListBox();
	ListBox lbHaarfarbe = new ListBox();

	TextBox tbSuchprofilName = new TextBox();
	TextBox tbAlterVon = new TextBox();
	TextBox tbAlterBis = new TextBox();
	TextBox tbGroesseVon = new TextBox();
	TextBox tbGroesseBis = new TextBox();
	// weitere Sucheigenschaften mussen noch ergänzt werden (siehe Mockup)
	public ProfilAttributeBoxPanel clb = null;
	ArrayList<Suchprofil> suchProfilListe = new ArrayList<>();
	@SuppressWarnings("deprecation")
	ListBox suchProfilListBox = new ListBox(true);

	FlowPanel fPanel = new FlowPanel();
	FlowPanel fPanel2 = new FlowPanel();
	FlowPanel fPanel3 = new FlowPanel();

	Suchprofil sp = new Suchprofil();
	ProfilAttributeTextBox suchProfilTextbox = null;
	Button suchprofilLöschen = null;

	Profil user = ClientsideSettings.getCurrentUser();
	Logger logger = ClientsideSettings.getLogger();
	PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
	private Button suchprofilSpeichern;

	@Override
	protected String getHeadlineText() {

		return "Suche";
	}

	@Override
	protected void run() {

		fPanel.setStyleName("ProfilAttribute-Suche");
		fPanel2.setStyleName("Info-Suche");

		ProfilAttributeBoxPanel suchProfilName = new ProfilAttributeBoxPanel("Name des Suchprofils");
		suchProfilTextbox = new ProfilAttributeTextBox();
		suchProfilTextbox.setName("suchProfilName");

		suchProfilListBox.setSize("8em", "14em");
		suchProfilListBox.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				suchprofilLöschen.setEnabled(true);

				ListBox asd = (ListBox) event.getSource();
				suchProfilTextbox.setText(asd.getSelectedItemText());
				pbVerwaltung.getSuchprofileForProfilByName(user, asd.getSelectedItemText(),
						new AsyncCallback<Suchprofil>() {

							@Override
							public void onSuccess(Suchprofil result) {
								sp = result;
								final HashMap<Integer, String> auswahlListe = sp.getAuswahlListe();

								for (Widget child : fPanel) {
									if (child instanceof ProfilAttributeBoxPanel) {
										ProfilAttributeBoxPanel childPanel = (ProfilAttributeBoxPanel) child;
										if (childPanel.getName() != null) {
											switch (childPanel.getName()) {
											case "Raucher":
												childPanel.setSelectedItemForSP(sp.getRaucher());
												break;
											case "Haarfarbe":
												childPanel.setSelectedItemForSP(sp.getHaarfarbe());
												break;
											case "Religion":
												childPanel.setSelectedItemForSP(sp.getReligion());
												break;
											case "Geschlecht":
												childPanel.setSelectedItemForSP(sp.getGeschlecht());
												break;
											case "Körpergröße_min":
												childPanel.setGroesse(sp.getGroesse_min() + 1);
												break;
											case "Körpergröße_max":
												childPanel.setGroesse(sp.getGroesse_max() + 1);
												break;
											case "Alter_min":
												childPanel.setAlter(sp.getAlter_min());
												break;
											case "Alter_max":
												childPanel.setAlter(sp.getAlter_max());
												break;
											case "Essen":
												childPanel.setSelectedItemForSP(
														auswahlListe.get(childPanel.getAuswahl().getId()));
												break;
											case "Sport":
												childPanel.setSelectedItemForSP(
														auswahlListe.get(childPanel.getAuswahl().getId()));
												break;
											case "Coolness":
												childPanel.setSelectedItemForSP(
														auswahlListe.get(childPanel.getAuswahl().getId()));
												break;
											}
										}
									}
								}

							}

							@Override
							public void onFailure(Throwable caught) {
								logger.severe("fehler bei ausgabe eines suchprofils");

							}
						});

			}
		});

		suchProfilName.add(suchProfilTextbox);
		fPanel.add(suchProfilName);
		fPanel.add(suchprofilCreateButton);
		fPanel.add(sucheButton);
		suchprofilSpeichern = new Button("Suchprofil Speichern");
		suchprofilLöschen = new Button("Suchprofil löschen");

		RootPanel.get("Menu").add(suchProfilListBox);
		RootPanel.get("Menu").add(suchprofilSpeichern);
		RootPanel.get("Menu").add(suchprofilLöschen);
		sucheButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("rechts").clear();
				ClientsideSettings.getPartnerboerseVerwaltung().getProfilesBySuchprofil(sp,
						new AsyncCallback<ArrayList<Profil>>() {

					/**
					 * onSuccess wird mit der ArrayList an Profilen die der
					 * Suche entsprochen haben ein Datagrid erstellt welcher
					 * die Profile enthält
					 *
					 */
					@Override
					public void onSuccess(ArrayList<Profil> result) {
						if (result != null) {
							profile = result;
//							RootPanel.get("Details").add(merkenButton);
//							RootPanel.get("Details").add(sperrenButton);

							DataGrid<Profil> table = new DataGrid<Profil>();
//							table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

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
							
							TextColumn<Profil> groesse = new TextColumn<Profil>() {
								@Override
								public String getValue(Profil p) {
									return String.valueOf(p.getGroesse());
								}
							};
							table.addColumn(groesse, "Größe");


							// Add a selection model to handle user
							// selection.
							final SingleSelectionModel<Profil> selectionModel = new
									SingleSelectionModel<Profil>();
							table.setSelectionModel(selectionModel);
							selectionModel.addSelectionChangeHandler(new Handler() {

								@Override
								public void onSelectionChange(SelectionChangeEvent event) {
									final Profil selected = selectionModel.getSelectedObject();
//									sperrenButton.addClickHandler(new ClickHandler() {
	//
//										@Override
//										public void onClick(ClickEvent event) {
//											if (selected != null) {
//												ClientsideSettings.getPartnerboerseVerwaltung().createSperre(
//														ClientsideSettings.getCurrentUser(), selected,
//														new AsyncCallback<Void>() {
	//
//															@Override
//															public void onSuccess(Void result) {
//																Window.alert("Profil gesperrt!");
//															}
	//
//															@Override
//															public void onFailure(Throwable caught) {
//																// TODO
//																// Auto-generated
//																// method
//																// stub
	//
//															}
//														});
//											}
	//
//										}
//									});
//									merkenButton.addClickHandler(new ClickHandler() {
	//
//										@Override
//										public void onClick(ClickEvent event) {
//											if (selected != null) {
	//
//												ClientsideSettings.getPartnerboerseVerwaltung().createMerken(
//														ClientsideSettings.getCurrentUser(), selected,
//														new AsyncCallback<Void>() {
	//
//															@Override
//															public void onSuccess(Void result) {
//																Window.alert("Profil gemerkt.");
	//
//															}
	//
//															@Override
//															public void onFailure(Throwable caught) {
//																// TODO
//																// Auto-generated
//																// method
//																// stub
	//
//															}
//														});
//												;
	//
//											}
	//
//										}
//									});

								}
							});

							table.setRowCount(profile.size(), true);
							table.setRowData(0, profile);
							table.setWidth("100%");

							LayoutPanel panel = new LayoutPanel();
							panel.setSize("30em", "10em");
							panel.add(table);
							RootPanel.get("rechts").add(panel);

							// SimpleLayoutPanel slp = new
							// SimpleLayoutPanel();
							// slp.add(table);
							// HTML html = new HTML(""+profile.size());
							// LayoutPanel lp = new LayoutPanel();
							// lp.add(table);
							//
							// // Add it to the root panel.
							// RootLayoutPanel.get().add(lp);
							// RootPanel.get("Details").add(lp);
							// RootPanel.get("Details").add(html);

						}
						//		ClientsideSettings.getReportGenerator().createSuchreport(result,
								// new AsyncCallback<AllProfileBySuche>() {
								//
								//
								// //onSuccess wird der Suchreport ausgegeben
								// @Override
								// public void onSuccess(AllProfileBySuche result) {
								// if (result != null) {
								//
								// RootPanel.get("Details").clear();
								// HTMLReportWriter writer = new HTMLReportWriter();
								// writer.process(result);
								// RootPanel.get("Details").add(new
								// HTML(writer.getReportText()));
								// }
								//
								//
								// }
								//
								// @Override
								// public void onFailure(Throwable caught) {
								// ClientsideSettings.getLogger().severe("Fehler
								// ReportGenerator createSuchreport");
								//
								// }
								// });

					}

					@Override
					public void onFailure(Throwable caught) {
						ClientsideSettings.getLogger().info("Fehler Callback getProfilesBySuche");

					}
				});
				
			}
		});
		suchprofilLöschen.setEnabled(false);
		suchprofilLöschen.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int i = suchProfilListBox.getSelectedIndex();
				suchProfilListBox.removeItem(i);
				pbVerwaltung.deleteSuchprofil(sp, new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {

						logger.info("Suchprofil erflogreich gelöscht");

					}

					@Override
					public void onFailure(Throwable caught) {
						logger.info("Suchprofil nicht erfolgreich gelöscht");
					}
				});

			}
		});

		suchprofilSpeichern.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Suchprofil tmpSp = createSP();
				tmpSp.setId(sp.getId());
				sp = tmpSp;
				pbVerwaltung.save(sp, new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						logger.info("Suchprofil erflogreich gespeichert");

					}

					@Override
					public void onFailure(Throwable caught) {
						logger.info("Suchprofil nicht erflogreich gespeichert");
					}
				});

			}
		});

		pbVerwaltung.getAllSuchprofileForProfil(user, new AsyncCallback<ArrayList<Suchprofil>>() {

			@Override
			public void onSuccess(ArrayList<Suchprofil> result) {
				suchProfilListe = result;
				for (Suchprofil sp : suchProfilListe) {
					suchProfilListBox.addItem(sp.getName());
				}

			}

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().info("Fehler AsyncCallback alle Suchprofile");
			}
		});

		pbVerwaltung.getAllAuswahl(new AsyncCallback<ArrayList<Auswahl>>() {

			@Override
			public void onSuccess(ArrayList<Auswahl> result) {

				for (Auswahl a : result) {
					ProfilAttributeBoxPanel clb = new ProfilAttributeBoxPanel(a, true);
					clb.setId(a.getId());
					clb.addKeineAngabenItem();
					fPanel.add(clb);

				}

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

		pbVerwaltung.getAllAuswahlProfilAttribute(new GetAllAuswahlProfilAttributeCallback());

		suchprofilCreateButton.addClickHandler(new SuchButtonClickHandler());

		RootPanel.get("Details").add(fPanel);
		RootPanel.get("Details").add(fPanel2);

		// Alle Profile aus der db holen
		pbVerwaltung.getAllProfiles(new AsyncCallback<ArrayList<Profil>>() {

			@Override
			public void onSuccess(ArrayList<Profil> result) {
				if (result != null) {
					profile = result;
				}

			}

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().info("Fehler AsyncCallback alle Profile");

			}
		});
	}

	private class GetAllAuswahlProfilAttributeCallback implements AsyncCallback<ArrayList<Auswahl>> {
		@Override
		public void onSuccess(ArrayList<Auswahl> result) {
			for (Auswahl a : result) {
				ProfilAttributeBoxPanel clb = new ProfilAttributeBoxPanel(a, true);
				clb.addKeineAngabenItem();
				fPanel.add(clb);

			}

			ProfilAttributeBoxPanel groesseMin = new ProfilAttributeBoxPanel("Minimale Größe");
			groesseMin.createGroesseListBox();
			groesseMin.setName("Körpergröße_min");
			groesseMin.addKeineAngabenItem();

			ProfilAttributeBoxPanel groesseMax = new ProfilAttributeBoxPanel("Maximale Größe");
			groesseMax.createGroesseListBox();
			groesseMax.addKeineAngabenItem();
			groesseMax.setName("Körpergröße_max");

			ProfilAttributeBoxPanel alterMin = new ProfilAttributeBoxPanel("Minimales Alter");
			alterMin.createAlterListbox();
			alterMin.setName("Alter_min");
			alterMin.addKeineAngabenItem();

			ProfilAttributeBoxPanel alterMax = new ProfilAttributeBoxPanel("Maximales Alter");
			alterMax.createAlterListbox();
			alterMax.addKeineAngabenItem();
			alterMax.setName("Alter_max");

			// Körpergröße und Geburtstags Listboxen werden nach den
			// AuswahlProfilAttributen zum Panel hinzugefügt

			fPanel.add(groesseMin);
			fPanel.add(groesseMax);
			fPanel.add(alterMin);
			fPanel.add(alterMax);

		}

		@Override
		public void onFailure(Throwable caught) {
			logger.severe("Fehler beim GetAllAuswahlProfilAttributeCallback");
		}
	}

	/**
	 * Clickhandler für den "suche" Button. onClick sollen sämltiche der Suche
	 * entsprechenden Profile ausgegeben werden
	 */

	private class SuchButtonClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			sp = createSP();
			RootPanel.get("Menu").clear();
			RootPanel.get("Menu").add(suchProfilListBox);
			RootPanel.get("Menu").add(suchprofilSpeichern);
			RootPanel.get("Menu").add(suchprofilLöschen);


			if (suchProfilListe.contains(sp)) {
				RootPanel.get("Details").add(new HTML("suchprofil schon vorhanden"));
			} else {
				suchProfilListe.add(sp);
				suchProfilListBox.addItem(sp.getName());
				pbVerwaltung.createSuchprofil(sp, new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						logger.severe("Suchprofil in DB geschreiben " + sp.getName() + " " + sp.getGroesse_max());
					}

					@Override
					public void onFailure(Throwable caught) {
						logger.severe(
								"Suchprofil in DB geschreiben Fehler= " + sp.getName() + " " + sp.getGroesse_max());
					}
				});
			}

		}

	}

	public Suchprofil createSP() {
		final Suchprofil sp = new Suchprofil();

		sp.setProfilId(user.getId());

		// final Button merkenButton = new Button("Profil merken");
		// final Button sperrenButton = new Button("Profil sperren");

		// Schleifen zum Auslesen der Listboxen

		HashMap<Integer, String> auswahlListe = new HashMap<>();
		sp.setName(suchProfilTextbox.getText());

		for (Widget child : fPanel) {
			if (child instanceof ProfilAttributeBoxPanel) {
				ProfilAttributeBoxPanel childPanel = (ProfilAttributeBoxPanel) child;
				if (childPanel.getName() != null) {
					// logger.info("es geht mit boxpanel " +
					// childPanel.getName());

					switch (childPanel.getName()) {

					case "Raucher":
						sp.setRaucher(childPanel.getSelectedItem());
						break;
					case "Haarfarbe":
						sp.setHaarfarbe(childPanel.getSelectedItem());
						break;
					case "Religion":
						sp.setReligion(childPanel.getSelectedItem());
						break;
					case "Geschlecht":
						sp.setGeschlecht(childPanel.getSelectedItem());
						break;
					case "Körpergröße_min":
						if (!childPanel.getSelectedItem().equals("Keine Angabe")) {
							sp.setGroesse_min(Integer.valueOf(childPanel.getSelectedItem()));
						}
						break;
					case "Körpergröße_max":
						if (!childPanel.getSelectedItem().equals("Keine Angabe")) {
							sp.setGroesse_max(Integer.valueOf(childPanel.getSelectedItem()));
						}
						break;
					case "Alter_min":
						if (!childPanel.getSelectedItem().equals("Keine Angabe")) {
							sp.setAlter_min(Integer.valueOf(childPanel.getSelectedItem()));
						}
						break;
					case "Alter_max":
						if (!childPanel.getSelectedItem().equals("Keine Angabe")) {
							sp.setAlter_max(Integer.valueOf(childPanel.getSelectedItem()));
						}
						break;
					case "Essen":
						if (!childPanel.getSelectedItem().equals("Keine Angabe")) {
							auswahlListe.put(childPanel.getAuswahl().getId(), childPanel.getSelectedItem());
						}
						break;
					case "Sport":
						if (!childPanel.getSelectedItem().equals("Keine Angabe")) {
							auswahlListe.put(childPanel.getAuswahl().getId(), childPanel.getSelectedItem());
						}
						break;
					case "Coolness":
						if (!childPanel.getSelectedItem().equals("Keine Angabe")) {
							auswahlListe.put(childPanel.getAuswahl().getId(), childPanel.getSelectedItem());
						}
						break;
					}
				}
			}
		}
		sp.setAuswahlListe(auswahlListe);
		return sp;
	}
}
