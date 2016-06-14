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
import de.superteam2000.gwt.shared.bo.Suchprofil;
import de.superteam2000.gwt.shared.report.AllNewProfileReport;
import de.superteam2000.gwt.shared.report.AllNotVisitedProfileReport;
import de.superteam2000.gwt.shared.report.AllProfileBySuche;
import de.superteam2000.gwt.shared.report.AllProfilesReport;
import de.superteam2000.gwt.shared.report.Column;
import de.superteam2000.gwt.shared.report.CompositeParagraph;
import de.superteam2000.gwt.shared.report.Paragraph;
import de.superteam2000.gwt.shared.report.ProfilReport;
import de.superteam2000.gwt.shared.report.Row;
import de.superteam2000.gwt.shared.report.SimpleParagraph;

public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {

  private static final long serialVersionUID = 1L;
  private PartnerboerseAdministration administration = null;

  public ReportGeneratorImpl() throws IllegalArgumentException {}

  @Override
  public void init() throws IllegalArgumentException {
    /*
     * Ein ReportGeneratorImpl-Objekt instantiiert für seinen Eigenbedarf eine
     * pbAdministration-Instanz.
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
    result.setTitle("Mein Profil");
    // result.setCreated(new Date());

    // Header des Reports erstellen
    // CompositeParagraph header = new CompositeParagraph();
    // header.addSubParagraph(new SimpleParagraph(p.getVorname() + " " + p.getNachname()));
    // header.addSubParagraph(new SimpleParagraph(String.valueOf(p.getAehnlichkeit())));

    SimpleParagraph aehnlickeit = new SimpleParagraph(String.valueOf(p.getAehnlichkeit()));
    result.setAehnlichekit(aehnlickeit);

    SimpleParagraph name = new SimpleParagraph(p.getVorname() + " " + p.getNachname());
    result.setName(name);

    CompositeParagraph profilAttribute = new CompositeParagraph();
    profilAttribute.addSubParagraph(new SimpleParagraph("Email: " + p.getEmail()));
    profilAttribute.addSubParagraph(new SimpleParagraph("Geschlecht: " + p.getGeschlecht()));
    profilAttribute.addSubParagraph(new SimpleParagraph("Alter: " + p.getAlter()));
    profilAttribute.addSubParagraph(new SimpleParagraph("Raucher: " + p.getRaucher()));
    profilAttribute.addSubParagraph(new SimpleParagraph("Religion: " + p.getReligion()));
    profilAttribute.addSubParagraph(new SimpleParagraph("Haarfarbe: " + p.getHaarfarbe()));
    result.setProfilAttribute(profilAttribute);

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
  public AllProfileBySuche createSuchreportBySuchprofil(Suchprofil sp, Profil p) {

    ClientsideSettings.getLogger().info("createSuchreport Methode in ReportGenerator aufgerufen");
    if (this.administration == null) {
      return null;
    }


    ArrayList<Profil> profilesList = administration.getProfilesBySuchprofil(sp, p);
    AllProfileBySuche result = new AllProfileBySuche();
    ArrayList<String> suchprofeilItems = administration.getItemsOfSuchprofil(sp);

    result.setTitle("Suche nach Suchprofilen");
    StringBuilder items = new StringBuilder();
    
    for (String string : suchprofeilItems) {
      items.append(string + " <br>");
    }
    result.setSubTitle("Die Suche ergab: " + profilesList.size()
        + " Treffer" + "<p>Suchkritierien: <br>" + items.toString() + "</p>");

    for (Profil profil : profilesList) {
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

    result.setTitle("Alle Profile");
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
  public AllNotVisitedProfileReport createAllNotVisitedProfileReport(Profil p)
      throws IllegalArgumentException {
    if (this.administration == null) {
      return null;
    }

    // zu befüllenden Report erstellen
    AllNotVisitedProfileReport result = new AllNotVisitedProfileReport();

    // // mit Inhalt befüllen
    result.setTitle("Nicht besuchte Profile");
    // result.setCreated(new Date());

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
    result.setTitle("Neue Profile");
    // result.setCreated(new Date());

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
