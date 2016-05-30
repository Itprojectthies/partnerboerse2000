package de.superteam2000.gwt.client.gui;

import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.client.Home;
import de.superteam2000.gwt.client.Navbar;
import de.superteam2000.gwt.client.ShowProfil;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Formular für die Darstellung des selektierten Kunden
 * 
 * @author Christian Rathke
 */
public class CustomerForm extends VerticalPanel {

	PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */

	Profil user = ClientsideSettings.getCurrentUser();
	Logger logger = ClientsideSettings.getLogger();
	FlowPanel fPanel = new FlowPanel();

	/*
	 * Im Konstruktor werden die anderen Widgets erzeugt. Alle werden in einem
	 * Raster angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen
	 * Widgets bestimmt.
	 */
	public CustomerForm() {

		final ProfilAttributBox palb = new ProfilAttributBox();
		palb.createTextboxPanel("Vorname", 1);
		palb.createTextboxPanel("Nachname", 2);
		palb.createListobxPanel("Haarfarbe", 3, "rot");
		// palb.createListobxPanel("Religion", 4);
		// palb.createListobxPanel("Geschlecht", 5);
		// palb.createListobxPanel("Raucher", 6);
		palb.createGroessePanel("Körpergröße", 7);
		palb.createGebTagPanel("Geburtstag", 8);
		
	
		this.fPanel = palb.getfPanel();
		RootPanel.get("Details").add(fPanel);

		Button confirmBtn = new Button("Weiter");
		this.add(confirmBtn);

		confirmBtn.addClickHandler(new ConfirmClickHandler());

		pbVerwaltung.getAuswahlProfilAttributByName("Haarfarbe", AuswahlCallback);
	}

	AsyncCallback<Auswahl> AuswahlCallback = new AsyncCallback<Auswahl>() {

		@Override
		public void onSuccess(Auswahl a) {
			CompositeProfilAttributeBox test = new CompositeProfilAttributeBox(a, "schwarz", true);
			RootPanel.get("Details").add(test);
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}
	};

	private class ConfirmClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			String firstName = "";
			String lastName = "";

			String haarfarbe = "";
			String raucher = "";
			String religion = "";
			String geschlecht = "";
			String email = user.getEmail();

			int groesse = 0;

			int geburtsTag = 0;
			int geburtsMonat = 0;
			int geburtsJahr = 0;

			// Schleifen zum Auslesen der Listboxen, welche in 2 Panels
			// verschachtelt sind

			for (Widget widget2 : fPanel) {
				FlowPanel vp1 = (FlowPanel) widget2;
				for (Widget widget3 : vp1) {
					if (widget3 instanceof ListBox) {
						ListBox lb = (ListBox) widget3;
						logger.severe("test " + lb.getName());

						switch (lb.getName()) {

						case "Raucher":
							raucher = lb.getSelectedItemText();
							break;
						case "Haarfarbe":
							haarfarbe = lb.getSelectedItemText();
							break;
						case "Religion":
							religion = lb.getSelectedItemText();
							break;
						case "Geschlecht":
							geschlecht = lb.getSelectedItemText();
							break;
						case "Körpergröße":
							groesse = Integer.valueOf(lb.getSelectedItemText());
							break;
						case "GeburtstagTag":
							geburtsTag = Integer.valueOf(lb.getSelectedItemText());
							break;
						case "GeburtstagMonat":
							geburtsMonat = Integer.valueOf(lb.getSelectedItemText());
							break;
						case "GeburtstagJahr":
							geburtsJahr = Integer.valueOf(lb.getSelectedItemText());
							break;

						}

					} else if (widget3 instanceof TextBox) {
						TextBox tb = (TextBox) widget3;
						logger.severe("test " + tb.getName());
						switch (tb.getName()) {
						case "Vorname":
							firstName = tb.getText();
							break;
						case "Nachname":
							lastName = tb.getText();
							break;

						}
					}

				}

			}

			// }

			// Date-Objekt aus den 3 Geburtstagswerten Tag, Monat und Jahr
			// konstruieren und in
			// ein SQL-Date-Objekt umwandeln

			Date gebTag2 = DateTimeFormat.getFormat("yyyy-MM-dd")
					.parse(geburtsJahr + "-" + geburtsMonat + "-" + geburtsTag);
			java.sql.Date gebTag = new java.sql.Date(gebTag2.getTime());

			pbVerwaltung.createProfil(lastName, firstName, email, gebTag, haarfarbe, raucher, religion, groesse,
					geschlecht, new CreateCustomerCallback());

		}
	}

	class CreateCustomerCallback implements AsyncCallback<Profil> {

		@Override
		public void onFailure(Throwable caught) {
			logger.severe("Erstellen des useres hat nicht funktioniert");

		}

		@Override
		public void onSuccess(Profil p) {

			ClientsideSettings.setCurrentUser(p);
			p.setLoggedIn(true);
			ShowProfil sp = new ShowProfil();
			Navbar nb = new Navbar();
			VerticalPanel detailsPanel = new VerticalPanel();
			detailsPanel.add(sp);
			RootPanel.get("Navigator").clear();
			RootPanel.get("Navigator").add(nb);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(new Home());
			RootPanel.get("Details").add(detailsPanel);
		}

	}

}
