package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.superteam2000.gwt.shared.PartnerboerseAdministration;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.ReportGeneratorAsync;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.report.AllProfilesReport;
import de.superteam2000.gwt.shared.report.HTMLReportWriter;
import de.superteam2000.gwt.shared.report.ProfilReport;




public class ReportGen implements EntryPoint {
	
	ReportGeneratorAsync reportGenerator = null;
	Button profilAnzeigenButton = new Button("Profil anzeigen");
	PartnerboerseAdministrationAsync pb = ClientsideSettings.getPartnerboerseVerwaltung();
	
	Button alleProfileAnzeigenButton = new Button("alle Profile anzeigen");
	
	Profil p = new Profil();
	ArrayList<Profil> profile = new ArrayList<Profil>();
	
	Logger logger = ClientsideSettings.getLogger();
	Profil p2 = new Profil();
	Profil p3= new Profil();
	Profil p4 = new Profil();
	int x;

	@Override
	public void onModuleLoad() {
		
//		p2.setVorname("hans");
//		p2.setNachname("Müller");
//		p3.setVorname("hans");
//		p3.setNachname("Müller");
//		p4.setVorname("hans");
//		p4.setNachname("Müller");
//		profile.add(p2);
//		profile.add(p3);
//		profile.add(p4);

		RootPanel.get("Details").add(profilAnzeigenButton);
		RootPanel.get("Details").add(alleProfileAnzeigenButton);
		
		if (reportGenerator == null) {
			reportGenerator = ClientsideSettings.getReportGenerator();
		}
		
		
		pb.getAllProfiles(new AsyncCallback<ArrayList<Profil>>() {
			
			@Override
			public void onSuccess(ArrayList<Profil> result) {
				profile = result;
				ClientsideSettings.getLogger().severe("async callback get all profiles");
	
				
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Fehler im Asynccallback Reportgen getAllProfiles");
				
			}
		});
		
		alleProfileAnzeigenButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				reportGenerator.createAllProfilesReport(new AsyncCallback<AllProfilesReport>() {
					
					@Override
					public void onSuccess(AllProfilesReport result) {
						RootPanel.get("Details").clear();
//						HTMLReportWriter writer = new HTMLReportWriter();
//						writer.process(result);
//						HTML html = new HTML(writer.getReportText());
//						RootPanel.get("Details").add(html);
//						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
				
			}
		});
		

		

//		pb.getProfilById(17, new AsyncCallback<Profil>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onSuccess(Profil result) {
//				 p = result;
//
//				
//			}
//		});
		

		

		
		
		profilAnzeigenButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				reportGenerator.createProfilReport(p, new createProfilReportCallback());
				
			}
		});
	
	
		


	}

}


class createProfilReportCallback implements AsyncCallback<ProfilReport>{

	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(ProfilReport report) {
		
		if(report != null){
			HTMLReportWriter writer = new HTMLReportWriter();
			writer.process(report);
			RootPanel.get("Details").clear();
			HTML html = new HTML(writer.getReportText());
			RootPanel.get("Details").add(html);
		}
	}
	
}
