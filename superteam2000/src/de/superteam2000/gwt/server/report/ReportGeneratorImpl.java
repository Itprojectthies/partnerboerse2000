package de.superteam2000.gwt.server.report;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.server.PartnerboerseAdministrationImpl;
import de.superteam2000.gwt.shared.ReportGenerator;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.bo.Suchprofil;
import de.superteam2000.gwt.shared.report.AllNewProfileReport;
import de.superteam2000.gwt.shared.report.AllNotVisitedProfileReport;
import de.superteam2000.gwt.shared.report.AllProfilesBySucheReport;
import de.superteam2000.gwt.shared.report.AllProfilesReport;
import de.superteam2000.gwt.shared.report.Column;
import de.superteam2000.gwt.shared.report.CompositeParagraph;
import de.superteam2000.gwt.shared.report.ProfilReport;
import de.superteam2000.gwt.shared.report.Row;
import de.superteam2000.gwt.shared.report.SimpleParagraph;

/**
 * Implementierung des synchronen Interfaces ReportGenerator.
 * Diese enthält sämtliche Applikationslogik die für die 
 * Erstellung und Verwaltung von Reports notwendig ist.
 * 
 * @author Funke, Volz
 *
 */
public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {

  private static final long serialVersionUID = 1L;
  private PartnerboerseAdministrationImpl administration = null;

  public ReportGeneratorImpl() throws IllegalArgumentException {}

  @Override
  public void init() throws IllegalArgumentException {
    /**
     * Ein ReportGeneratorImpl-Objekt instantiiert für seinen Eigenbedarf eine
     * pbAdministration-Instanz.
     */
    PartnerboerseAdministrationImpl a = new PartnerboerseAdministrationImpl();
    a.init();
    administration = a;
  }

  /**
   * Diese Methode erwartet als Übergabeparameter ein Profil-Objekt,
   * welches in ein ProfilReport-Objekt "umgewandelt" wird.
   * Dies geschieht mit Hilfe der Klassen welche sich in 
   * shared.report befinden. 
   * So werden z.B. einzelne Profilattribute in SimpleParagraph 's 
   * gespeichert.
   */
  @Override
  public ProfilReport createProfilReport(Profil p) throws IllegalArgumentException {
    if (administration == null) {
      return null;
    }

  
    ProfilReport result = new ProfilReport();
    result.setProfilId(p.getId());


    result.setTitle("Mein Profil");


    SimpleParagraph aehnlickeit = new SimpleParagraph(String.valueOf(p.getAehnlichkeit()));
    result.setAehnlichekit(aehnlickeit);

    SimpleParagraph name = new SimpleParagraph(p.getVorname() + " " + p.getNachname());
    result.setName(name);

    CompositeParagraph profilAttributBez = new CompositeParagraph();
    profilAttributBez.addSubParagraph(new SimpleParagraph("Email: "));
    profilAttributBez.addSubParagraph(new SimpleParagraph("Geschlecht: "));
    profilAttributBez.addSubParagraph(new SimpleParagraph("Alter: "));
    profilAttributBez.addSubParagraph(new SimpleParagraph("Raucher: "));
    profilAttributBez.addSubParagraph(new SimpleParagraph("Religion: "));
    profilAttributBez.addSubParagraph(new SimpleParagraph("Haarfarbe: "));
    result.setProfilAttributeBez(profilAttributBez);

    CompositeParagraph profilAttribute = new CompositeParagraph();
    profilAttribute.addSubParagraph(new SimpleParagraph(p.getEmail()));
    profilAttribute.addSubParagraph(new SimpleParagraph(p.getGeschlecht()));
    profilAttribute.addSubParagraph(new SimpleParagraph("" + p.getAlter()));
    profilAttribute.addSubParagraph(new SimpleParagraph(p.getRaucher()));
    profilAttribute.addSubParagraph(new SimpleParagraph(p.getReligion()));
    profilAttribute.addSubParagraph(new SimpleParagraph(p.getHaarfarbe()));
    result.setProfilAttribute(profilAttribute);




    ArrayList<Info> infos = administration.getInfoByProfile(p);

    if (infos != null) {
      for (Info i : infos) {
        Row infoRow = new Row();

        infoRow.addColumn(
            new Column(administration.getEigenschaftsBeschreibungById(i.getEigenschaftId())));
        infoRow.addColumn(new Column(i.getText()));
        result.addRow(infoRow);

      }
    }

    return result;

  }

  /**
   * Methode zum Erstellen des Reports für alle dem Suchprofil
   * entsprechenden Profile.
   * Diese Methode macht von der bereits implementierten Methode
   * createProfilReport gebrauch, welche für jedes einzelne
   * anzuzeigende Profil aufgerufen wird.
   */
  @Override
  public AllProfilesBySucheReport createSuchreportBySuchprofil(Suchprofil sp, Profil p) {

    ClientsideSettings.getLogger().info("createSuchreport Methode in ReportGenerator aufgerufen");
    if (administration == null) {
      return null;
    }

    ArrayList<Profil> profilesList = administration.getProfilesBySuchprofil(sp, p);
    AllProfilesBySucheReport result = new AllProfilesBySucheReport();
    ArrayList<String> suchprofilItems = administration.getItemsOfSuchprofil(sp);

    result.setTitle("Suche nach Suchprofilen");
    StringBuilder items = new StringBuilder();

    for (String string : suchprofilItems) {
      items.append(string + " <br>");
    }

    result.setSubTitle("Die Suche ergab: " + profilesList.size() + " Treffer"
        + "<p>Suchkritierien: <br>" + items.toString() + "</p>");

    for (Profil profil : profilesList) {
      result.addSubReport(createProfilReport(profil));

    }
    return result;
  }

  /**
   * Methode zum Erstellen des Reports für alle Profile.
   * Diese Methode macht von der bereits implementierten Methode
   * createProfilReport gebrauch, welche für jedes einzelne
   * anzuzeigende Profil aufgerufen wird.
   */ 
  @Override
  public AllProfilesReport createAllProfilesReport(Profil p) throws IllegalArgumentException {
    if (administration == null) {
      return null;
    }

    // zu befüllenden Report erstellen
    AllProfilesReport result = new AllProfilesReport();


    result.setTitle("Alle Profile");
    result.setCreated(new Date());


    // alle Profile abfragen
    ArrayList<Profil> profile = administration.getProfilesByAehnlichkeitsmass(p);

    for (Profil profil : profile) {
      result.addSubReport(createProfilReport(profil));
    }

    return result;
  }

  /**
   * Methode zum Erstellen des Reports für alle nicht besuchten Profile.
   * Diese Methode macht von der bereits implementierten Methode
   * createProfilReport gebrauch, welche für jedes einzelne
   * anzuzeigende Profil aufgerufen wird.
   */ 
  @Override
  public AllNotVisitedProfileReport createAllNotVisitedProfileReport(Profil p)
      throws IllegalArgumentException {
    if (administration == null) {
      return null;
    }

    // zu befüllenden Report erstellen
    AllNotVisitedProfileReport result = new AllNotVisitedProfileReport();

    result.setTitle("Nicht besuchte Profile");

    // alle nicht besuchten Profile abfragen
    ArrayList<Profil> profile = administration.getAllNotVisitedProfilesByAehnlichkeitsmass(p);

    for (Profil profil : profile) {
      result.addSubReport(createProfilReport(profil));
    }

    return result;
  }

  /**
   * Methode zum Erstellen des Reports für alle neuen Profile.
   * Diese Methode macht von der bereits implementierten Methode
   * createProfilReport gebrauch, welche für jedes einzelne
   * anzuzeigende Profil aufgerufen wird.
   */ 
  @Override
  public AllNewProfileReport createAllNewProfilesReport(Profil p) {
    if (administration == null) {
      return null;
    }

    // zu befüllenden Report erstellen
    AllNewProfileReport result = new AllNewProfileReport();

    result.setTitle("Neue Profile");

    // alle neuen Profile abfragen
    ArrayList<Profil> profile = administration.getAllNewProfilesByAehnlichkeitsmass(p);

    for (Profil profil : profile) {
      result.addSubReport(createProfilReport(profil));

    }
    return result;
  }



}
