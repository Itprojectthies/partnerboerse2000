package de.superteam2000.gwt.client;

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
import de.superteam2000.gwt.shared.report.HTMLReportWriter;
import de.superteam2000.gwt.shared.report.ProfilReport;




public class ReportGen implements EntryPoint {
	
	ReportGeneratorAsync reportGenerator = null;
	Button profilAnzeigenButton = new Button("Profil anzeigen");
	PartnerboerseAdministrationAsync pb = ClientsideSettings.getPartnerboerseVerwaltung();
	
	Profil p = new Profil();
	
	

	@Override
	public void onModuleLoad() {
//		p.setVorname("Hans");
//		p.setNachname("Maier");
//		p.setEmail("test@gmail.com");
//		p.setGeburtsdatum("11.11.11");
//		p.setGeschlecht("m");
		

		pb.getProfilById(17, new AsyncCallback<Profil>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Profil result) {
				 p = result;

				
			}
		});
		

		RootPanel.get("Details").add(profilAnzeigenButton);
		
		
		if (reportGenerator == null) {
			reportGenerator = ClientsideSettings.getReportGenerator();
		}
		
		
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

