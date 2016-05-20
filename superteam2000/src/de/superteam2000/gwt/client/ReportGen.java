package de.superteam2000.gwt.client;


import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
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
import de.superteam2000.gwt.shared.report.WidgetReport;

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
		
		
		
		
		pb.getProfilById(19, new AsyncCallback<Profil>() {
			
			@Override
			public void onSuccess(Profil result) {
				p = result;
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		if (reportGenerator == null) {
			reportGenerator = ClientsideSettings.getReportGenerator();
		}

		pb.getAllProfiles(new AsyncCallback<ArrayList<Profil>>() {

			@Override
			public void onSuccess(ArrayList<Profil> result) {
				if (result != null) {
					profile = result;
					ClientsideSettings.getLogger().severe("async callback get all profiles");
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

				reportGenerator.createAllProfilesReport(new AsyncCallback<AllProfilesReport>() {

					@Override
					public void onSuccess(AllProfilesReport result) {
						if (result != null) {
							for(int i = 0; i<result.getNumSubReports(); i++){
								ProfilReport r = (ProfilReport) result.getSubReportAt(i);
								
								int profilId = r.getProfilId();
								Button button = new Button("Profil:" +r.getProfilId());

								HTMLReportWriter writer = new HTMLReportWriter();
								writer.process(r);
								RootPanel.get("Details").add(new HTML(writer.getReportText()));
								RootPanel.get("Details").add(button);
								

								
								
								
							}
//						RootPanel.get("Details").clear();
//						HTMLReportWriter writer = new HTMLReportWriter();
//						writer.process(result);
//						RootPanel.get("Details").add(new HTML(writer.getReportText()));
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

//		profilAnzeigenButton.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				reportGenerator.createProfilReport(p, new createProfilReportCallback());
//
//			}
//		});
		
		
		profilAnzeigenButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				reportGenerator.createProfilReport2(p, new AsyncCallback<WidgetReport>() {
					
					@Override
					public void onSuccess(WidgetReport result) {
						RootPanel.get("Details").add(result);
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});

			}
		});

	}
}



//class createProfilReportCallback implements AsyncCallback<ProfilReport> {
//
//
//	@Override
//	public void onFailure(Throwable caught) {
//		// TODO Auto-generated method stub
//
//
//	}
//
//	@Override
//	public void onSuccess(ProfilReport report) {
//
//		if (report != null) {
//
//			HTMLReportWriter writer = new HTMLReportWriter();
//			writer.process(report);
//			RootPanel.get("Details").clear();
//			HTML html = new HTML(writer.getReportText());
//			RootPanel.get("Details").add(html);
//			
//
//			
//		}
//	}
//
//}
