package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.report.AllProfileBySuche;
import de.superteam2000.gwt.shared.report.HTMLReportWriter;
import de.superteam2000.gwt.shared.report.ProfilReport;



/**
 * Die Klasse Suche ist f√ºr die Darstellung von M√∂glichen Auswahlen
 * und eine anschlie√üende Suche anhand dieser Kriterien
 * 
 * @author Christopher
 *
 */
public class Suche extends BasicFrame {

	ArrayList<Profil> profile = null;

	Button suchButton = new Button("Suche");



	ListBox lbRaucher = new ListBox();
	ListBox lbReligion = new ListBox();
	ListBox lbGeschlecht = new ListBox();
	ListBox lbHaarfarbe = new ListBox();
	ListBox lbEigenschaften = new ListBox();
	
	TextBox tbSuchprofilName = new TextBox();
	TextBox tbAlterVon = new TextBox();
	TextBox tbAlterBis = new TextBox();
	TextBox tbGroesseVon = new TextBox();
	TextBox tbGroesseBis = new TextBox();

	
	
	@Override
	protected String getHeadlineText() {

		return "Suche";
	}

	@Override
	protected void run() {

		Grid customerGrid = new Grid(5, 2);
		this.add(customerGrid);

		this.add(suchButton);
		suchButton.addClickHandler(new SuchButtonClickHandler());

		//Raucher
		Label raucherLabel = new Label("Raucher");
		lbRaucher.addItem("Raucher");
		lbRaucher.addItem("Nichtraucher");
		lbRaucher.addItem("Gelegenheitsraucher");
		customerGrid.setWidget(0, 0, raucherLabel);
		customerGrid.setWidget(0, 1, lbRaucher);

		//Religion
		Label reliLabel = new Label("Religion");
		lbReligion.addItem("r√∂misch-katholisch");
		lbReligion.addItem("evangelisch");
		lbReligion.addItem("j√ºdisch");
		lbReligion.addItem("buddhistisch");
		lbReligion.addItem("orthodox");
		lbReligion.addItem("islamistisch");
		lbReligion.addItem("atheistisch");
		lbReligion.addItem("sonstige Zugehˆrigkeit");
		customerGrid.setWidget(1, 0, reliLabel);
		customerGrid.setWidget(1, 1, lbReligion);

		//Geschlecht
		Label geschlechtLabel = new Label("Geschlecht");
		lbGeschlecht.addItem("m√§nnlich");
		lbGeschlecht.addItem("weiblich");
		lbGeschlecht.addItem("beides");
		customerGrid.setWidget(2, 0, geschlechtLabel);
		customerGrid.setWidget(2, 1, lbGeschlecht);

		//Haarfarbe
		Label haarLabel = new Label("Haarfarbe");
		lbHaarfarbe.addItem("braun");
		lbHaarfarbe.addItem("blond");
		lbHaarfarbe.addItem("schwarz");
		lbHaarfarbe.addItem("rot");
		lbHaarfarbe.addItem("grau/weiﬂ");
		lbHaarfarbe.addItem("andere Haarfarbe");
		customerGrid.setWidget(3, 0, haarLabel);
		customerGrid.setWidget(3, 1, lbHaarfarbe);
		
		//Suchprofilname
		Label suchprofilLabel = new Label ("Suchprofilname");
		//tbSuchprofilName(style);
		customerGrid.setWidget(3, 0, suchprofilLabel);
		customerGrid.setWidget(3, 1, tbSuchprofilName);
		
		//weitere Eigenschaften ausw‰hlen
		Label weitEigenschaft = new Label ("weitere Eigenschaften");
		lbEigenschaften.addItem("Was ist dein Lieblingsessen?");
		lbEigenschaften.addItem("Welche Sportart betreibst du?");
		lbEigenschaften.addItem("Hast du Haustiere und wenn ja welche?");
		lbEigenschaften.addItem("Was sind deine Lieblingsfilme?");
		lbEigenschaften.addItem("Was sind deine Lieblingsserien?");
		lbEigenschaften.addItem("Was ist deine Lieblingsfarbe?");
		lbEigenschaften.addItem("Was ist deine Lieblingszahl?");
		lbEigenschaften.addItem("Was sind deine Freizeitaktivit‰ten?");
		lbEigenschaften.addItem("Was ist deine sonstigen Interessen?");
		lbEigenschaften.addItem("Was du sonst noch ¸ber dich sagen willst");


		//pb Verwaltung √ºber ClientsideSettings holen
		PartnerboerseAdministrationAsync pbVerwaltung = 
				ClientsideSettings.getPartnerboerseVerwaltung();

		//Alle Profile aus der db holen
		pbVerwaltung.getAllProfiles(new AsyncCallback<ArrayList<Profil>>() {

			@Override
			public void onSuccess(ArrayList<Profil> result) {
				if(result != null){
					profile = result;}

			}

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().
				info("Fehler AsyncCallback alle Profile");

			}
		});
	}


	/**
	 *  Clickhandler f√ºr den "suche" Button. onClick sollen
	 *  s√§mltiche der Suche entsprechenden Profile ausgegeben werden 
	 */


	private class SuchButtonClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			// "dummmy-Profil" erstellen
			Profil p = new Profil();
			p.setRaucher(lbRaucher.getSelectedValue());
			p.setReligion(lbReligion.getSelectedValue());
			p.setGeschlecht(lbGeschlecht.getSelectedValue());
			p.setHaarfarbe(lbHaarfarbe.getSelectedValue());
			p.setEigenschaften(lbEigenschaften.getSelectedValue());
			
			


			// getProfilesBySuche wird mit dem "dummy-Profil" aufgerufen
			ClientsideSettings.getPartnerboerseVerwaltung().getProfilesBySuche(p,
					new AsyncCallback<ArrayList<Profil>>() {

				/**
				 * onSuccess wird mit der ArrayList an Profilen die der Suche
				 *  entsprochen haben ein "Suchreport" erstellt
				 *
				 */
				@Override
				public void onSuccess(ArrayList<Profil> result) {
					ClientsideSettings.getReportGenerator().createSuchreport(result,
							new AsyncCallback<AllProfileBySuche>() {


						//onSuccess wird der Suchreport ausgegeben
						@Override
						public void onSuccess(AllProfileBySuche result) {
							if (result != null) {

								RootPanel.get("Details").clear();
								HTMLReportWriter writer = new HTMLReportWriter();
								writer.process(result);
								RootPanel.get("Details").add(new HTML(writer.getReportText()));
							}


						}

						@Override
						public void onFailure(Throwable caught) {
							ClientsideSettings.getLogger().severe("Fehler ReportGenerator createSuchreport");

						}
					});

				}

				@Override
				public void onFailure(Throwable caught) {
					ClientsideSettings.getLogger().info("Fehler Callback getProfilesBySuche");

				}
			});


		}

	}


}


