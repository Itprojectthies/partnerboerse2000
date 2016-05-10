package de.superteam2000.gwt.server.report;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.superteam2000.gwt.server.*;
import de.superteam2000.gwt.shared.*;
import de.superteam2000.gwt.shared.bo.*;

import de.superteam2000.gwt.shared.report.*;

public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PartnerboerseAdministration administration = null;

	public ReportGeneratorImpl() throws IllegalArgumentException {
	}

	@Override
	public void init() throws IllegalArgumentException {
		/*
		 * Ein ReportGeneratorImpl-Objekt instantiiert fÃ¼r seinen Eigenbedarf
		 * eine BankVerwaltungImpl-Instanz.
		 */
		PartnerboerseAdministrationImpl a = new PartnerboerseAdministrationImpl();
		a.init();
		this.administration = a;
	}

	@Override
	public ProfilReport createProfilReport(Profil p) throws IllegalArgumentException {
		if(this.administration==null){
		return null;}
		
	// zu befüllenden Report erstellen
		ProfilReport result = new ProfilReport();
		
	//ab hier result mit Inhalten befüllen
		result.setTitle("Profil");
		result.setCreated(new Date());
		
	//Header des Reports erstellen
		CompositeParagraph header = new CompositeParagraph();
		header.addSubParagraph(new SimpleParagraph(p.getVorname()+" "+p.getNachname()));
		header.addSubParagraph(new SimpleParagraph(String.valueOf(p.getId())));
		
		result.setHeaderData(header);
		
	//"Impressum" mit Attributen des Profils befüllen
	//TODO restliche benötigten Attribute hinzufügen
		CompositeParagraph imprint = new CompositeParagraph();
		imprint.addSubParagraph(new SimpleParagraph("Email: "+p.getEmail()));
		imprint.addSubParagraph(new SimpleParagraph("Geschlecht: "+p.getGeschlecht()));
		imprint.addSubParagraph(new SimpleParagraph("geb. am: "+p.getGeburtsdatum()));
		
		result.setImprint(imprint);
	
	//Eigenschaften anhängen als Tabelle mit zwei Spalten
		//TODO ggf Info anpassen für besseres auslesen
//		
//		ArrayList<Info> infos = this.administration.getInfoByProfile(p);
//		
//		for(Info i: infos){
//			Row infoRow = new Row();
//			
//			infoRow.addColumn(new Column(i.getText()));
//			
//		}
//
		

		
		
		return result;
		
		
	}

	@Override
	public AllNotVisitedProfileReport createAllNotVisitedProfileReport(Profil p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AllNewProfileReport createAllNewProfilesReport(Profil p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AllProfileBySuche createAllProfileBySucheReport(Profil p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

}
