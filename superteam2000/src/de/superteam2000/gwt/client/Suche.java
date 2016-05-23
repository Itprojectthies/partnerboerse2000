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
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
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

	Button suchButton = new Button("Suche");
	Button suchAllButton = new Button("Alle Profile anzeigen");

	ListBox lbRaucher = new ListBox();
	ListBox lbReligion = new ListBox();
	ListBox lbGeschlecht = new ListBox();
	ListBox lbHaarfarbe = new ListBox();

	TextBox tbGroesse = new TextBox();

	@Override
	public String getHeadlineText() {

		return "";
	}

	@Override
	public void run() {

		Grid customerGrid = new Grid(5, 2);
		RootPanel.get("Menu").add(customerGrid);

		RootPanel.get("Menu").add(suchButton);
		RootPanel.get("Menu").add(suchAllButton);
		suchAllButton.addClickHandler(new suchAllClickHandler());
		suchButton.addClickHandler(new SuchButtonClickHandler());

		// Raucher
		Label raucherLabel = new Label("Raucher");
		lbRaucher.addItem("Raucher");
		lbRaucher.addItem("Nichtraucher");
		customerGrid.setWidget(0, 0, raucherLabel);
		customerGrid.setWidget(0, 1, lbRaucher);

		// Religion
		Label reliLabel = new Label("Religion");
		lbReligion.addItem("römisch-katholisch");
		lbReligion.addItem("evangelisch");
		lbReligion.addItem("jüdisch");
		customerGrid.setWidget(1, 0, reliLabel);
		customerGrid.setWidget(1, 1, lbReligion);

		// Geschlecht
		Label geschlechtLabel = new Label("Geschlecht");
		lbGeschlecht.addItem("männlich");
		lbGeschlecht.addItem("weiblich");
		customerGrid.setWidget(2, 0, geschlechtLabel);
		customerGrid.setWidget(2, 1, lbGeschlecht);

		// Haarfarbe
		Label haarLabel = new Label("Haarfarbe");
		lbHaarfarbe.addItem("braun");
		lbHaarfarbe.addItem("blond");
		lbHaarfarbe.addItem("schwarz");
		customerGrid.setWidget(3, 0, haarLabel);
		customerGrid.setWidget(3, 1, lbHaarfarbe);

		// pb Verwaltung über ClientsideSettings holen
		PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

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

	/**
	 * Clickhandler für den "suche" Button. onClick sollen sämltiche der Suche
	 * entsprechenden Profile ausgegeben werden
	 */

	private class SuchButtonClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			RootPanel.get("Details").clear();

			// "dummmy-Profil" erstellen
			Profil p = new Profil();
			p.setRaucher(lbRaucher.getSelectedValue());
			p.setReligion(lbReligion.getSelectedValue());
			p.setGeschlecht(lbGeschlecht.getSelectedValue());
			p.setHaarfarbe(lbHaarfarbe.getSelectedValue());

			// getProfilesBySuche wird mit dem "dummy-Profil" aufgerufen
			ClientsideSettings.getPartnerboerseVerwaltung().getProfilesBySuche(p,
					new AsyncCallback<ArrayList<Profil>>() {

				/**
				 * onSuccess wird mit der ArrayList an Profilen die der
				 * Suche entsprochen haben ein "Suchreport" erstellt
				 *
				 */
				@Override
				public void onSuccess(ArrayList<Profil> result) {

					if (result != null) {
						profile = result;
						ClientsideSettings.getLogger()
						.info("AsyncCallback alle Profile hat funktioniert " + profile.size());

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

						TextColumn<Profil> id = new TextColumn<Profil>() {
							@Override
							public String getValue(Profil p) {
								return String.valueOf(p.getId());
							}
						};
						table.addColumn(id, "Id");

						// Add a selection model to handle user
						// selection.
						final SingleSelectionModel<Profil> selectionModel = new SingleSelectionModel<Profil>();
						table.setSelectionModel(selectionModel);
						selectionModel.addSelectionChangeHandler(new Handler() {

							@Override
							public void onSelectionChange(SelectionChangeEvent event) {
								Profil selected = selectionModel.getSelectedObject();
								if (selected != null) {
									Window.alert("You selected: " + selected.getNachname() + " "
											+ selected.getVorname() + " " + selected.getAlter() + " "
											+ selected.getEmail() + " " + selected.getHaarfarbe());
								}
							}
						});

						// table.setRowCount(profile.size(), true);
						table.setRowData(0, profile);
						table.setWidth("100%");

						LayoutPanel panel = new LayoutPanel();
						panel.setSize("30em", "10em");
						panel.add(table);
						RootPanel.get("Details").add(panel);

					}

				}

				@Override
				public void onFailure(Throwable caught) {
					ClientsideSettings.getLogger().info("Fehler Callback getProfilesBySuche");

				}
			});

		}

	}

	private class suchAllClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			
		}

	}

}
