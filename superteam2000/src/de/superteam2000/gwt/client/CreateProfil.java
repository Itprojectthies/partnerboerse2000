package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.superteam2000.gwt.client.gui.ProfilAttributeBoxPanel;
import de.superteam2000.gwt.client.gui.ProfilAttributeListBox;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Formular fÃ¼r die Darstellung des selektierten Users
 * 
 * @param user momentaner Benutzer
 * @param logger momentaner Logger
 * @param fPanel
 * @param fPanel2
 * @param gebTag Hier kann der Geburtstag eingegeben werden
 * @param groesse Hier kann die Körpergrösse angegeben werden
 * 
 * 
 * @author Christian Rathke, Volz, Funke
 */
public class CreateProfil extends BasicFrame {

	/*
	 * Hier wird die Verbindung zum Datenbankmapper und somit zur Datenbank und zur Partnerboerse gemacht
	 */
	PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

	/*
	 * Widgets, deren Inhalte variabel sind, werden als Attribute angelegt.
	 */
	Profil user = ClientsideSettings.getCurrentUser();
	Logger logger = ClientsideSettings.getLogger();

	FlowPanel fPanel = new FlowPanel();
	FlowPanel fPanel2 = new FlowPanel();
	ProfilAttributeBoxPanel gebTag = null;
	ProfilAttributeBoxPanel groesse = null;

	@Override
	public String getHeadlineText() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Im Konstruktor werden die anderen Widgets erzeugt. Alle werden in einem
	 * Raster angeordnet, dessen GrÃ¶ÃŸe sich aus dem Platzbedarf der enthaltenen
	 * Widgets bestimmt.
	 */

	/**
	 * Wenn ein neuer User sich registrieren/anmelden will, werden die Grunddaten, welche nicht
	 * gelöscht werden können angelegt. Diese Methode bietet die Möglichkeit, die Werte/Daten
	 * einzugeben und mit der Registrierung fortzufahren. Die beiden Attribute Körpergröße und
	 * Geburtstag sind Drop-down-Listen.
	 * 
	 * @param gebTag Der Beschreibungstext des Panels wird gespeichert, es ist eine Drop-down-Liste.
	 * @param groesse Der Beschreibungstext des Panels wird gespeichert, es ist eine Drop-down-Liste.
	 * @param pbVerwaltung Die eingegebenen Werte werden gespeichert.
	 * @param confirmBtn Ein neuer Button namens "Weiter" wird erstellt.
	 */
	@Override
	public void run() {
		gebTag = new ProfilAttributeBoxPanel("Was ist dein Geburtstag?");
		gebTag.createGebtaListobx();

		groesse = new ProfilAttributeBoxPanel("Was ist dein KÃ¶rpergrÃ¶ÃŸe?");
		groesse.createGroesseListBox();

		pbVerwaltung.getAllBeschreibungProfilAttribute(new GetAllBeschreibungProfilAttributeCallBack());
		pbVerwaltung.getAllAuswahlProfilAttribute(new GetAllAuswahlProfilAttributeCallBack());

		Button confirmBtn = new Button("Weiter");
		this.fPanel.add(confirmBtn);
		confirmBtn.addClickHandler(new ConfirmClickHandler());

		RootPanel.get("Details").add(fPanel);
		
	}

	/**
	 * Für den neuen User wird ein leeres Formular angelegt.
	 * 
	 * @param clb Befüllte Formularergebnisse werden übertragen.
	 *
	 */
	private class GetAllBeschreibungProfilAttributeCallBack implements AsyncCallback<ArrayList<Beschreibung>> {
		@Override
		public void onSuccess(ArrayList<Beschreibung> result) {
			for (Beschreibung b : result) {

				ProfilAttributeBoxPanel clb = new ProfilAttributeBoxPanel(b, false);
				fPanel.add(clb);
			}

		}

		
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * Die beiden Attribute Größe und Geburtstag werden dem Panel hinzugefügt.
	 * 
	 * @param clb Die ausgewählten Elemente werden abgespeichert.
	 */
	private class GetAllAuswahlProfilAttributeCallBack implements AsyncCallback<ArrayList<Auswahl>> {
		@Override
		public void onSuccess(ArrayList<Auswahl> result) {
			for (Auswahl a : result) {

				ProfilAttributeBoxPanel clb = new ProfilAttributeBoxPanel(a, false);
				fPanel.add(clb);
			}

			fPanel.add(groesse);
			fPanel.add(gebTag);
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * ClickHandler einbinden.
	 *
	 */
	private class ConfirmClickHandler implements ClickHandler {

		/**
		 * @param firstName Hier wird der Vorname gespeichert.
		 * @param lastName Hier wird der Nachname gespeichert.
		 * @param haarfarbe Hier wird die Haarfarbe gespeichert.
		 * @param raucher Hier wird die Auswahl bei Raucher gespeichert.
		 * @param religion Hier wird die Auswahl bei Religion gespeichert.
		 * @geschlecht Hier wird das Geschlecht gespeichert.
		 * @email Hier wird die Email Adresse gespeichert.
		 * @param geburtsTag Dies ist eine Hilfsvariable, um das Alter zu bestimmen.
		 * @param geburtsMonat Dies ist eine Hilfsvariable, um das Alter zu errechnen.
		 * @param geburtsJahr Dies ist eine Hilfsvariable um das Alter zu errechnen.
		 */
		@Override
		public void onClick(ClickEvent event) {

			String firstName = "";
			String lastName = "";

			String haarfarbe = "";
			String raucher = "";
			String religion = "";
			String geschlecht = "";
			String email = user.getEmail();

			int groesse = 140;

			int geburtsTag = 1;
			int geburtsMonat = 1;
			int geburtsJahr = 1900;

			/*
			 * Verschachtelte Schleifen, damit die Listboxen der beiden ineinander
			 * verschachtelten Panels, ausgelesen werden können.
			 * Im Folgenden werden die einzelnen eingegebenen Werte für Attribute
			 * des Users eingelesen und in den jeweiligen Variablen gespeichert.
			 */
			for (Widget child : fPanel) {
				
				if (child instanceof FlowPanel) {
					
					FlowPanel vp1 = (FlowPanel) child;
					for (Widget widget3 : vp1) {
						if (widget3 instanceof ProfilAttributeListBox) {
							ProfilAttributeListBox lb = (ProfilAttributeListBox) widget3;
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
							case "KÃ¶rpergrÃ¶ÃŸe":
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

			}

			
			/**
			 * Um das Alter abspeichern zu können, wird das Geburtsdatum eingelesen und
			 * in der richtigen Reihenfolge abgespeichert.
			 * Danach wird das korrekte Geburtsdatum in ein SQL-Date-Objekt umgewandelt um
			 * dort richtig gespeichert zu werden.
			 */
			Date gebTag2 = DateTimeFormat.getFormat("yyyy-MM-dd")
					.parse(geburtsJahr + "-" + geburtsMonat + "-" + geburtsTag);
			java.sql.Date gebTag = new java.sql.Date(gebTag2.getTime());

			/*
			 * Das Profil wird angelegt und in die Datenbank geschrieben mit allen eingegebenen Werten
			 * und Attributen.
			 */
			pbVerwaltung.createProfil(lastName, firstName, email, gebTag, haarfarbe, raucher, religion, groesse,
					geschlecht, new CreateCustomerCallback());

		}
	}

	/**
	 * In dieser Klasse sind alle Methoden enthalten, womit das erstellte Profil eines User angezeigt werden kann.
	 */
	class CreateCustomerCallback implements AsyncCallback<Profil> {

		/**
		 * @throws exception Wenn User nicht angemeldet werden konnte wird Fehlermeldung ausgegeben.
		 */
		@Override
		public void onFailure(Throwable caught) {
			logger.severe("Erstellen des Users hat nicht funktioniert");

		}

		/**
		 * Wenn das Profil erfolgreich erstellt und gespeichert werden konnte.
		 * Danach wird das gespeicherte Profil angezeigt.
		 * Dazu wird die Navigationsbar und die Details ausgegeben.
		 */
		@Override
		public void onSuccess(Profil p) {

			ClientsideSettings.setCurrentUser(p);
			p.setLoggedIn(true);
			ShowProfil sp = new ShowProfil();
			Navbar nb = new Navbar();
			RootPanel.get("Navigator").clear();
			RootPanel.get("Navigator").add(nb);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(new Home());
			RootPanel.get("Details").add(sp);
		}

	}

}
=======
package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.superteam2000.gwt.client.gui.ProfilAttributeBoxPanel;
import de.superteam2000.gwt.client.gui.ProfilAttributeListBox;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Formular fÃ¼r die Darstellung des selektierten Kunden
 * 
 * @author Christian Rathke
 */
public class CreateProfil extends BasicFrame {

	PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */

	Profil user = ClientsideSettings.getCurrentUser();
	Logger logger = ClientsideSettings.getLogger();

	FlowPanel fPanel = new FlowPanel();
	FlowPanel fPanel2 = new FlowPanel();
	ProfilAttributeBoxPanel gebTag = null;
	ProfilAttributeBoxPanel groesse = null;

	@Override
	public String getHeadlineText() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Im Konstruktor werden die anderen Widgets erzeugt. Alle werden in einem
	 * Raster angeordnet, dessen GrÃ¶ÃŸe sich aus dem Platzbedarf der enthaltenen
	 * Widgets bestimmt.
	 */

	@Override
	public void run() {
		gebTag = new ProfilAttributeBoxPanel("Was ist dein Geburtstag?");
		gebTag.createGebtaListobx();

		groesse = new ProfilAttributeBoxPanel("Was ist dein KÃ¶rpergrÃ¶ÃŸe?");
		groesse.createGroesseListBox();

		pbVerwaltung.getAllBeschreibungProfilAttribute(new GetAllBeschreibungProfilAttributeCallBack());
		pbVerwaltung.getAllAuswahlProfilAttribute(new GetAllAuswahlProfilAttributeCallBack());

		Button confirmBtn = new Button("Weiter");
		this.fPanel.add(confirmBtn);
		confirmBtn.addClickHandler(new ConfirmClickHandler());

		RootPanel.get("Details").add(fPanel);
		
	}

	private class GetAllBeschreibungProfilAttributeCallBack implements AsyncCallback<ArrayList<Beschreibung>> {
		@Override
		public void onSuccess(ArrayList<Beschreibung> result) {
			for (Beschreibung b : result) {

				ProfilAttributeBoxPanel clb = new ProfilAttributeBoxPanel(b, false);
				fPanel.add(clb);
			}

		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}
	}

	private class GetAllAuswahlProfilAttributeCallBack implements AsyncCallback<ArrayList<Auswahl>> {
		@Override
		public void onSuccess(ArrayList<Auswahl> result) {
			for (Auswahl a : result) {

				ProfilAttributeBoxPanel clb = new ProfilAttributeBoxPanel(a, false);
				fPanel.add(clb);
			}

			fPanel.add(groesse);
			fPanel.add(gebTag);
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}
	}

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

			int groesse = 140;

			int geburtsTag = 1;
			int geburtsMonat = 1;
			int geburtsJahr = 1900;

			// Schleifen zum Auslesen der Listboxen, welche in 2 Panels
			// verschachtelt sind

			for (Widget child : fPanel) {
				
				if (child instanceof FlowPanel) {
					
					FlowPanel vp1 = (FlowPanel) child;
					for (Widget widget3 : vp1) {
						if (widget3 instanceof ProfilAttributeListBox) {
							ProfilAttributeListBox lb = (ProfilAttributeListBox) widget3;
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
							case "KÃ¶rpergrÃ¶ÃŸe":
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

			}

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
			RootPanel.get("Navigator").clear();
			RootPanel.get("Navigator").add(nb);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(new Home());
			RootPanel.get("Details").add(sp);
		}

	}

}
