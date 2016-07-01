package de.superteam2000.gwt.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.bo.Suchprofil;
import de.superteam2000.gwt.shared.report.AllNewProfileReport;
import de.superteam2000.gwt.shared.report.AllNotVisitedProfileReport;
import de.superteam2000.gwt.shared.report.AllProfilesBySucheReport;
import de.superteam2000.gwt.shared.report.AllProfilesReport;
import de.superteam2000.gwt.shared.report.ProfilReport;

/**
 * Synchrones Interface welches das Interface RemoteService erweitert.
* In ihr finden sich sämtliche Methodensignaturen der Methoden, welche von
* der Klasse ReportGeneratorImpl zu implementieren sind.
* 
 * @author Thies, Funke
 *
 */
@RemoteServiceRelativePath("rg")
public interface ReportGenerator extends RemoteService {

  public void init() throws IllegalArgumentException;

  public abstract ProfilReport createProfilReport(Profil p) throws IllegalArgumentException;


  public abstract AllNotVisitedProfileReport createAllNotVisitedProfileReport(Profil p)
      throws IllegalArgumentException;

  public abstract AllNewProfileReport createAllNewProfilesReport(Profil p);


  public abstract AllProfilesReport createAllProfilesReport(Profil profil)
      throws IllegalArgumentException;

  public abstract AllProfilesBySucheReport createSuchreportBySuchprofil(Suchprofil sp, Profil p);



}
