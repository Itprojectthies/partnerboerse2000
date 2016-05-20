package de.superteam2000.gwt.server.report;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.server.PartnerboerseAdministrationImpl;
import de.superteam2000.gwt.shared.PartnerboerseAdministration;
import de.superteam2000.gwt.shared.ReportGenerator;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.report.AllNewProfileReport;
import de.superteam2000.gwt.shared.report.AllNotVisitedProfileReport;
import de.superteam2000.gwt.shared.report.AllProfileBySuche;
import de.superteam2000.gwt.shared.report.AllProfilesReport;
import de.superteam2000.gwt.shared.report.AllProfilesReport2;
import de.superteam2000.gwt.shared.report.Column;
import de.superteam2000.gwt.shared.report.CompositeParagraph;
import de.superteam2000.gwt.shared.report.ProfilReport;
import de.superteam2000.gwt.shared.report.Row;
import de.superteam2000.gwt.shared.report.SimpleParagraph;
import de.superteam2000.gwt.shared.report.WidgetReport;

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
		 * Ein ReportGeneratorImpl-Objekt instantiiert für seinen Eigenbedarf
		 * eine pbAdministration-Instanz.
		 */
		PartnerboerseAdministrationImpl a = new PartnerboerseAdministrationImpl();
		a.init();
		this.administration = a;
	}

	@Override
	public ProfilReport createProfilReport(Profil p) throws IllegalArgumentException {
		if (this.administration == null) {
			return null;
		}

		// zu befüllenden Report erstellen
		ProfilReport result = new ProfilReport();

		// ab hier result mit Inhalten befüllen
		result.setTitle(p.getVorname()+" "+p.getNachname());
//		result.setCreated(new Date());
//		Anchor a = new Anchor(p.getVorname()+" "+p.getNachname());
		// Header des Reports erstellen
//		RootPanel.get("test").add(a);
		CompositeParagraph header = new CompositeParagraph();
		header.addSubParagraph(new SimpleParagraph(p.getVorname() + " " + p.getNachname()));
		
		header.addSubParagraph(new SimpleParagraph(String.valueOf(p.getId())));

		result.setHeaderData(header);

		// "Impressum" mit Attributen des Profils befüllen
		// TODO restliche benötigten Attribute hinzufügen
		CompositeParagraph imprint = new CompositeParagraph();
		imprint.addSubParagraph(new SimpleParagraph("Email: " + p.getEmail()));
		imprint.addSubParagraph(new SimpleParagraph("Geschlecht: " + p.getGeschlecht()));
		imprint.addSubParagraph(new SimpleParagraph("Alter: " + p.getAlter()));
		imprint.addSubParagraph(new SimpleParagraph(p.getRaucher()));
		imprint.addSubParagraph(new SimpleParagraph("Religion: "+p.getReligion()));
		result.setImprint(imprint);
		
		// Eigenschaften anhängen als Tabelle mit zwei Spalten
		// TODO ggf Info anpassen für besseres auslesen

		ArrayList<Info> infos = this.administration.getInfoByProfile(p);
		
		//Kopfzeile anlegen
		Row headline = new Row();
		headline.addColumn(new Column("Eigenschaften:"));
		result.addRow(headline);
	
		if(infos != null){
		
			for(Info i: infos){
				Row infoRow = new Row();
				// Füge zwei zweilen zur eigschaftsspalte hinzu
				// 1. Spalte = Eigenschafstname des Infoobjekts
				// 2. Spalte = Infotext des jeweiligen Infoobjekts
				infoRow.addColumn(new Column(this.administration.getEigenschaftsNameById(i.getEigenschaftId())));
				infoRow.addColumn(new Column(i.getText()));
				result.addRow(infoRow);

			}}
			
		
		return result;

	}
	@Override
	public WidgetReport createProfilReport2(Profil p) throws IllegalArgumentException {
		if (this.administration == null) {
			return null;
		}
		
		// zu befüllenden Report erstellen
		WidgetReport result = new WidgetReport() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public String getHeadlineText() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		result.append(String.valueOf(p.getAlter()));
		result.append(new Anchor(p.getVorname()+" "+p.getNachname()));
		
		
		
		// ab hier result mit Inhalten befüllen
		result.setTitle(p.getVorname()+" "+p.getNachname());
//		result.setCreated(new Date());
		Anchor a = new Anchor(p.getVorname()+" "+p.getNachname());
		// Header des Reports erstellen
		RootPanel.get("test").add(result);
		CompositeParagraph header = new CompositeParagraph();
		header.addSubParagraph(new SimpleParagraph(p.getVorname() + " " + p.getNachname()));
		
		header.addSubParagraph(new SimpleParagraph(String.valueOf(p.getId())));
		
//		result.setHeaderData(header);
		
		// "Impressum" mit Attributen des Profils befüllen
		// TODO restliche benötigten Attribute hinzufügen
		CompositeParagraph imprint = new CompositeParagraph();
		imprint.addSubParagraph(new SimpleParagraph("Email: " + p.getEmail()));
		imprint.addSubParagraph(new SimpleParagraph("Geschlecht: " + p.getGeschlecht()));
		imprint.addSubParagraph(new SimpleParagraph("Alter: " + p.getAlter()));
		imprint.addSubParagraph(new SimpleParagraph(p.getRaucher()));
		imprint.addSubParagraph(new SimpleParagraph("Religion: "+p.getReligion()));
//		result.setImprint(imprint);
		
		// Eigenschaften anhängen als Tabelle mit zwei Spalten
		// TODO ggf Info anpassen für besseres auslesen
		
		ArrayList<Info> infos = this.administration.getInfoByProfile(p);
		
		//Kopfzeile anlegen
		Row headline = new Row();
		headline.addColumn(new Column("Eigenschaften:"));
//		result.addRow(headline);
//		
//		if(infos != null){
//			
//			for(Info i: infos){
//				Row infoRow = new Row();
//				// Füge zwei zweilen zur eigschaftsspalte hinzu
//				// 1. Spalte = Eigenschafstname des Infoobjekts
//				// 2. Spalte = Infotext des jeweiligen Infoobjekts
//				infoRow.addColumn(new Column(this.administration.getEigenschaftsNameById(i.getEigenschaftId())));
//				infoRow.addColumn(new Column(i.getText()));
//				result.addRow(infoRow);
//				
//			}}
//		
//		
		return result;
		
	}

	@Override
	public AllProfileBySuche createSuchreport(ArrayList<Profil> p){
		
		ClientsideSettings.getLogger().info("createSuchreport Methode in ReportGenerator aufgerufen");
		if (this.administration == null) {
			return null;
		}
		
		AllProfileBySuche result = new AllProfileBySuche();
		
		// mit Inhalt befüllen
		result.setTitle("Die Suche ergab: " + p.size() + " Treffer");
		result.setCreated(new Date());
		
		for (Profil profil : p) {
			result.addSubReport(this.createProfilReport(profil));

		}
		return result;
		
	}
	@Override
	public AllProfilesReport createAllProfilesReport() throws IllegalArgumentException {
		if (this.administration == null) {
			return null;
		}

		// zu befüllenden Report erstellen
		AllProfilesReport result = new AllProfilesReport();


		//
		//		// mit Inhalt befüllen
//		result.setTitle("Alle Profile anzeigen Report");
//		result.setCreated(new Date());


		// alle Profile abfragen
		ArrayList<Profil> profile = this.administration.getAllProfiles();

		for (Profil p : profile) {
			result.addSubReport(this.createProfilReport(p));

		}

		return result;
	}
	@Override
	public AllProfilesReport2 createAllProfilesReport2() throws IllegalArgumentException {
		if (this.administration == null) {
			return null;
		}
		
		// zu befüllenden Report erstellen
		AllProfilesReport2 result = new AllProfilesReport2();
		
		
		//
		//		// mit Inhalt befüllen
//		result.setTitle("Alle Profile anzeigen Report");
//		result.setCreated(new Date());
		
		
		// alle Profile abfragen
		ArrayList<Profil> profile = this.administration.getAllProfiles();
		
		for (Profil p : profile) {
			result.addSubReport(this.createProfilReport2(p));
			
		}
		
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
