package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

import de.superteam2000.gwt.client.gui.ProfilAttributeBoxPanel;
import de.superteam2000.gwt.client.gui.ProfilAttributeListBox;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Profil;

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

	Button suchButton = new Button("Suche");

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

	FlowPanel fPanel = new FlowPanel();

	Profil user = ClientsideSettings.getCurrentUser();
	Logger logger = ClientsideSettings.getLogger();

	@Override
	protected String getHeadlineText() {

		return "Suche";
	}

	@Override
	protected void run() {

		Grid customerGrid = new Grid(5, 2);
		// this.add(customerGrid);

		// Raucher
		Label raucherLabel = new Label("Raucher");
		lbRaucher.addItem("Raucher");
		lbRaucher.addItem("Nichtraucher");
		lbRaucher.addItem("Gelegenheitsraucher");
		customerGrid.setWidget(0, 0, raucherLabel);
		customerGrid.setWidget(0, 1, lbRaucher);

		// Religion
		Label reliLabel = new Label("Religion");
		lbReligion.addItem("römisch-katholisch");
		lbReligion.addItem("evangelisch");
		lbReligion.addItem("jüdisch");
		lbReligion.addItem("buddhistisch");
		lbReligion.addItem("orthodox");
		lbReligion.addItem("islamistisch");
		lbReligion.addItem("atheistisch");
		lbReligion.addItem("sonstige Zugeh�rigkeit");
		customerGrid.setWidget(1, 0, reliLabel);
		customerGrid.setWidget(1, 1, lbReligion);

		// Geschlecht
		Label geschlechtLabel = new Label("Geschlecht");
		lbGeschlecht.addItem("männlich");
		lbGeschlecht.addItem("weiblich");
		lbGeschlecht.addItem("beides");
		customerGrid.setWidget(2, 0, geschlechtLabel);
		customerGrid.setWidget(2, 1, lbGeschlecht);

		// Haarfarbe
		Label haarLabel = new Label("Haarfarbe");
		lbHaarfarbe.addItem("braun");
		lbHaarfarbe.addItem("blond");
		lbHaarfarbe.addItem("schwarz");
		lbHaarfarbe.addItem("rot");
		lbHaarfarbe.addItem("grau/wei�");
		lbHaarfarbe.addItem("andere Haarfarbe");
		customerGrid.setWidget(3, 0, haarLabel);
		customerGrid.setWidget(3, 1, lbHaarfarbe);

		// Suchprofilname
		Label suchprofilLabel = new Label("Suchprofilname");
		// tbSuchprofilName(style);
		customerGrid.setWidget(3, 0, suchprofilLabel);
		customerGrid.setWidget(3, 1, tbSuchprofilName);

		//

		// pb Verwaltung über ClientsideSettings holen
		PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

		pbVerwaltung.getAllAuswahlProfilAttribute(new GetAllAuswahlProfilAttributeCallback());

		suchButton.addClickHandler(new SuchButtonClickHandler());

		RootPanel.get("Details").add(fPanel);

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
				fPanel.add(clb);
			}

			// Körpergröße und Geburtstags Listboxen werden nach den
			// AuswahlProfilAttributen zum Panel hinzugefügt
			fPanel.add(suchButton);
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * Clickhandler für den "suche" Button. onClick sollen sämltiche der Suche
	 * entsprechenden Profile ausgegeben werden
	 */

	private class SuchButtonClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			final Button merkenButton = new Button("Profil merken");
			final Button sperrenButton = new Button("Profil sperren");
			RootPanel.get("Details").clear();

			// "dummmy-Profil" erstellen
			Profil p = new Profil();

			// Schleifen zum Auslesen der Listboxen, welche in 2 Panels
			// verschachtelt sind

			for (Widget child : fPanel) {
				if (child instanceof FlowPanel) {
					FlowPanel childPanel = (FlowPanel) child;
					for (Widget box : childPanel) {
						if (box instanceof ProfilAttributeListBox) {
							ProfilAttributeListBox lb = (ProfilAttributeListBox) box;

							switch (lb.getName()) {

							case "Raucher":
								p.setRaucher(lb.getSelectedItemText());

								break;
							case "Haarfarbe":
								p.setHaarfarbe(lb.getSelectedItemText());
								break;
							case "Religion":
								p.setReligion(lb.getSelectedItemText());
								break;
							case "Geschlecht":
								p.setGeschlecht(lb.getSelectedItemText());
								break;
							case "Körpergröße":
								p.setGroesse(Integer.valueOf(lb.getSelectedItemText()));
								break;

							}

						}

					}
				}

			}

			// getProfilesBySuche wird mit dem "dummy-Profil" aufgerufen
			ClientsideSettings.getPartnerboerseVerwaltung().getProfilesBySuche(p,
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
								RootPanel.get("Details").add(merkenButton);
								RootPanel.get("Details").add(sperrenButton);

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

								// Add a selection model to handle user
								// selection.
								final SingleSelectionModel<Profil> selectionModel = new SingleSelectionModel<Profil>();
								table.setSelectionModel(selectionModel);
								selectionModel.addSelectionChangeHandler(new Handler() {

									@Override
									public void onSelectionChange(SelectionChangeEvent event) {
										final Profil selected = selectionModel.getSelectedObject();
										sperrenButton.addClickHandler(new ClickHandler() {

											@Override
											public void onClick(ClickEvent event) {
												if (selected != null) {
													ClientsideSettings.getPartnerboerseVerwaltung().createSperre(
															ClientsideSettings.getCurrentUser(), selected,
															new AsyncCallback<Void>() {

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
										});
										merkenButton.addClickHandler(new ClickHandler() {

											@Override
											public void onClick(ClickEvent event) {
												if (selected != null) {

													ClientsideSettings.getPartnerboerseVerwaltung().createMerken(
															ClientsideSettings.getCurrentUser(), selected,
															new AsyncCallback<Void>() {

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
													;

												}

											}
										});

									}
								});

								table.setRowCount(profile.size(), true);
								table.setRowData(0, profile);
								table.setWidth("100%");

								LayoutPanel panel = new LayoutPanel();
								panel.setSize("30em", "10em");
								panel.add(table);
								RootPanel.get("Details").add(panel);

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
							// ClientsideSettings.getReportGenerator().createSuchreport(result,
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

	}

}
