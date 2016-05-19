package de.superteam2000.gwt.server.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ibm.icu.util.Calendar;

import de.superteam2000.gwt.client.ClientsideSettings;
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

		// zu bef�llenden Report erstellen
		ProfilReport result = new ProfilReport();

		// ab hier result mit Inhalten bef�llen
		result.setTitle(p.getVorname()+" "+p.getNachname());
//		result.setCreated(new Date());

		// Header des Reports erstellen
		CompositeParagraph header = new CompositeParagraph();
		header.addSubParagraph(new SimpleParagraph(p.getVorname() + " " + p.getNachname()));
		header.addSubParagraph(new SimpleParagraph(String.valueOf(p.getId())));

		result.setHeaderData(header);

		// "Impressum" mit Attributen des Profils bef�llen
		// TODO restliche ben�tigten Attribute hinzuf�gen
		CompositeParagraph imprint = new CompositeParagraph();
		imprint.addSubParagraph(new SimpleParagraph("Email: " + p.getEmail()));
		imprint.addSubParagraph(new SimpleParagraph("Geschlecht: " + p.getGeschlecht()));
		imprint.addSubParagraph(new SimpleParagraph("geb. am: " + p.getGeburtsdatum()));
		imprint.addSubParagraph(new SimpleParagraph(p.getRaucher()));
		imprint.addSubParagraph(new SimpleParagraph("Religion: "+p.getReligion()));

		result.setImprint(imprint);

		// Eigenschaften anh�ngen als Tabelle mit zwei Spalten
		// TODO ggf Info anpassen f�r besseres auslesen

		ArrayList<Info> infos = this.administration.getInfoByProfile(p);
		
		//Kopfzeile anlegen
		Row headline = new Row();
		headline.addColumn(new Column("Eigenschaften:"));
		result.addRow(headline);

		if(infos != null){
		
			for(Info i: infos){
				Row infoRow = new Row();

				infoRow.addColumn(new Column(i.getText()));
				
				result.addRow(infoRow);

			}}
			
		
		

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
//		result.setCreated(new Date());
		
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

		// zu bef�llenden Report erstellen
		AllProfilesReport result = new AllProfilesReport();


		//
		//		// mit Inhalt bef�llen
		result.setTitle("Alle Profile anzeigen Report");
		result.setCreated(new Date());
		//
		//		CompositeParagraph header = new CompositeParagraph();
		//		CompositeParagraph imprint = new CompositeParagraph();
		//		header.addSubParagraph(new SimpleParagraph("Test"));
		//		header.addSubParagraph(new SimpleParagraph("Test"));
		//		imprint.addSubParagraph(new SimpleParagraph("Test2"));
		//		imprint.addSubParagraph(new SimpleParagraph("Test2"));
		//
		//		// Kopfzeile der Tabelle erstellen
		//		Row headline = new Row();
		//
		//		headline.addColumn(new Column("Vorname"));
		//		headline.addColumn(new Column("Nachname"));

		// Hinzuf�gen der Kopfzeile
		// result.addRow(headline);

		// alle Profile abfragen
		ArrayList<Profil> profile = this.administration.getAllProfiles();

		for (Profil p : profile) {
			result.addSubReport(this.createProfilReport(p));

		}

		////		 alle Profile in Tabelle bef�llen
		//		 for (Profil profil : p) {
		//		 // Eine leere Zeile anlegen.
		//		 Row profilRow = new Row();
		//		
		//		 //erste Spalte: Vorname hinzuf�gen
		//		 profilRow.addColumn(new Column(profil.getVorname()));
		//		 //zweite Spalte: Nachname hinzuf�gen
		//		 profilRow.addColumn(new Column(profil.getNachname()));
		//		
		//		 // und schlie�lich die Zeile dem Report hinzuf�gen.
		//		 result.addRow(profilRow);
		//		 }

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
