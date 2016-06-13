package de.superteam2000.gwt.server.report;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.server.PartnerboerseAdministrationImpl;
import de.superteam2000.gwt.shared.PartnerboerseAdministration;
import de.superteam2000.gwt.shared.ReportGenerator;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.report.AllNewProfileReport;
import de.superteam2000.gwt.shared.report.AllNotVisitedProfileReport;
import de.superteam2000.gwt.shared.report.AllProfileBySuche;
import de.superteam2000.gwt.shared.report.AllProfilesReport;
import de.superteam2000.gwt.shared.report.Column;
import de.superteam2000.gwt.shared.report.CompositeParagraph;
import de.superteam2000.gwt.shared.report.ProfilReport;
import de.superteam2000.gwt.shared.report.Row;
import de.superteam2000.gwt.shared.report.SimpleParagraph;

public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {

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
	result.setProfilId(p.getId());

	// ab hier result mit Inhalten befüllen
	result.setTitle(p.getVorname() + " " + p.getNachname());
	// result.setCreated(new Date());

	// Header des Reports erstellen
	CompositeParagraph header = new CompositeParagraph();
	header.addSubParagraph(new SimpleParagraph(p.getVorname() + " " + p.getNachname()));
	header.addSubParagraph(new SimpleParagraph(String.valueOf(p.getAehnlichkeit())));

	result.setHeaderData(header);

	// "Impressum" mit Attributen des Profils befüllen
	// TODO restliche benötigten Attribute hinzufügen
	CompositeParagraph imprint = new CompositeParagraph();
	imprint.addSubParagraph(new SimpleParagraph("Email: " + p.getEmail()));
	imprint.addSubParagraph(new SimpleParagraph("Geschlecht: " + p.getGeschlecht()));
	result.setImprint(imprint);
	
	CompositeParagraph imprint2 = new CompositeParagraph();
	imprint2.addSubParagraph(new SimpleParagraph("Alter: " + p.getAlter()));
	imprint2.addSubParagraph(new SimpleParagraph("Raucher: " + p.getRaucher()));
	result.setImprint2(imprint2);

	CompositeParagraph imprint3 = new CompositeParagraph();
	imprint3.addSubParagraph(new SimpleParagraph("Religion: " + p.getReligion()));
	imprint3.addSubParagraph(new SimpleParagraph("Haarfarbe: "+ p.getHaarfarbe()));
	result.setImprint3(imprint3);

	// Eigenschaften anhängen als Tabelle mit zwei Spalten
	// TODO ggf Info anpassen für besseres auslesen

	ArrayList<Info> infos = this.administration.getInfoByProfile(p);



	if (infos != null) {

	    for (Info i : infos) {
		Row infoRow = new Row();
		
		
		infoRow.addColumn(new Column(administration.getEigenschaftsNameById(i.getEigenschaftId())));
		infoRow.addColumn(new Column(i.getText()));
		result.addRow(infoRow);
		
	    }
	}

	return result;

    }

    @Override
    public AllProfileBySuche createSuchreport(ArrayList<Profil> p) {

	ClientsideSettings.getLogger().info("createSuchreport Methode in ReportGenerator aufgerufen");
	if (this.administration == null) {
	    return null;
	}

	AllProfileBySuche result = new AllProfileBySuche();

	// mit Inhalt befüllen
	result.setTitle("Die Suche ergab: " + p.size() + " Treffer");
	// result.setCreated(new Date());

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

	// mit Inhalt befüllen
	result.setTitle("Alle Profile anzeigen Report");
	result.setCreated(new Date());

	// Hinzufügen der Kopfzeile
	// result.addRow(headline);

	// alle Profile abfragen
	ArrayList<Profil> profile = this.administration.getAllProfiles();

	for (Profil p : profile) {
	    result.addSubReport(this.createProfilReport(p));

	}

	return result;
    }

    @Override
    public AllNotVisitedProfileReport createAllNotVisitedProfileReport(Profil p) throws IllegalArgumentException {
	if (this.administration == null) {
	    return null;
	}

	// zu befüllenden Report erstellen
	AllNotVisitedProfileReport result = new AllNotVisitedProfileReport();

	// // mit Inhalt befüllen
	result.setTitle("Alle nicht besuchen Profile anzeigen Report");
	result.setCreated(new Date());

	// Hinzufügen der Kopfzeile
	// result.addRow(headline);

	// alle Profile abfragen
	ArrayList<Profil> profile = this.administration.getAllNotVisitedProfilesByAehnlichkeitsmass(p);

	for (Profil profil : profile) {
	    result.addSubReport(this.createProfilReport(profil));

	}

	return result;
    }

    @Override
    public AllNewProfileReport createAllNewProfilesReport(Profil p) {
	if (this.administration == null) {
	    return null;
	}

	// zu befüllenden Report erstellen
	AllNewProfileReport result = new AllNewProfileReport();

	// mit Inhalt befüllen
	result.setTitle("Alle neuen Profile anzeigen Report");
	result.setCreated(new Date());

	// Hinzufügen der Kopfzeile
	// result.addRow(headline);

	// alle Profile abfragen
	ArrayList<Profil> profile = this.administration.getAllNewProfilesByAehnlichkeitsmass(p);

	for (Profil profil : profile) {
	    result.addSubReport(this.createProfilReport(profil));

	}

	return result;
    }

    @Override
    public AllProfileBySuche createAllProfileBySucheReport(Profil p) throws IllegalArgumentException {
	// TODO Auto-generated method stub
	return null;
    }

}
