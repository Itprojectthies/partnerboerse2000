package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.ReportGeneratorAsync;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.report.AllProfilesReport;
import de.superteam2000.gwt.shared.report.HTMLReportWriter;
import de.superteam2000.gwt.shared.report.ProfilReport;

public class ReportGen implements EntryPoint {
	
	
	Button profilAnzeigenButton = new Button("Profil anzeigen");
	Button alleProfileAnzeigenButton = new Button("alle Profile anzeigen");

	
	ArrayList<Profil> profile = new ArrayList<Profil>();
	Profil p = new Profil();
	ReportGeneratorAsync reportGenerator = null;
	Logger logger = ClientsideSettings.getLogger();
	PartnerboerseAdministrationAsync pb = ClientsideSettings.getPartnerboerseVerwaltung();
	

	@Override
	public void onModuleLoad() {

		RootPanel.get("Details").add(profilAnzeigenButton);
		RootPanel.get("Details").add(alleProfileAnzeigenButton);

		PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
		pbVerwaltung.login(GWT.getHostPageBaseURL() + "Superteam2000.html", new LoginCallback());

	}




	/**
	 * Asynchrone Anmelde-Klasse. Showcase in dem die Antwort des Callbacks
	 * eingefügt wird.
	 * 
	 * @author Volz, Funke
	 *
	 */
	class LoginCallback implements AsyncCallback<Profil> {

		@Override
		public void onFailure(Throwable caught) {

			ClientsideSettings.getLogger().severe("Login fehlgeschlagen!");
	}

		@Override
		public void onSuccess(Profil result) {
			
			p = result;
			
			if(reportGenerator == null){
				reportGenerator = ClientsideSettings.getReportGenerator();
			}
			

		pb.getAllProfiles(new AsyncCallback<ArrayList<Profil>>() {

			@Override
			public void onSuccess(ArrayList<Profil> result) {
				if (result != null) {
					profile = result;
					ClientsideSettings.getLogger().info("async callback get all profiles");
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Fehler im Asynccallback Reportgen getAllProfiles");

			}
		});



			/**
			 * ClickHandler der onClick alle Profile der Partnerbörse ausgibt
			 */
			alleProfileAnzeigenButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					ClientsideSettings.getLogger().info("onClick Methode aufgerufen");

					reportGenerator.createAllProfilesReport(new AsyncCallback<AllProfilesReport>() {

								
								
							
					@Override
					public void onSuccess(AllProfilesReport result) {
						ClientsideSettings.getLogger().info("onSuccess AllprofilesReport");
						if (result != null) {
						RootPanel.get("Details").clear();
						HTMLReportWriter writer = new HTMLReportWriter();
						writer.process(result);
						RootPanel.get("Details").add(new HTML(writer.getReportText()));
						//

						}
					}

					@Override
					public void onFailure(Throwable caught) {
						ClientsideSettings.getLogger().severe("createallprofiles funktioniert nicht");

					}
				});


			}
		});

		// pb.getProfilById(17, new AsyncCallback<Profil>() {
		//
		// @Override
		// public void onFailure(Throwable caught) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onSuccess(Profil result) {
		// p = result;
		//
		//
		// }
		// });



		profilAnzeigenButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ClientsideSettings.getLogger().info("onClick profilAnzeigenButton");
				reportGenerator.createProfilReport(p, new createProfilReportCallback());

			}
		});
			
		}

}

class createProfilReportCallback implements AsyncCallback<ProfilReport> {

	@Override
	public void onFailure(Throwable caught) {
		ClientsideSettings.getLogger().info(caught.toString());


	}

		
	@Override
	public void onSuccess(ProfilReport report) {
	if(report != null){	ClientsideSettings.getLogger().info("report != null" );}

		if (report != null) {
			HTMLReportWriter writer = new HTMLReportWriter();
			writer.process(report);
			RootPanel.get("Details").clear();
			HTML html = new HTML(writer.getReportText());
			RootPanel.get("Details").add(html);
			
		}
	}

} }
