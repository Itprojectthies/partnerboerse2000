package de.superteam2000.gwt.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.bo.Suchprofil;
import de.superteam2000.gwt.shared.report.AllNewProfileReport;
import de.superteam2000.gwt.shared.report.AllNotVisitedProfileReport;
import de.superteam2000.gwt.shared.report.AllProfilesBySucheReport;
import de.superteam2000.gwt.shared.report.AllProfilesReport;
import de.superteam2000.gwt.shared.report.ProfilReport;


/**
 * Das asynchrone Gegenstück des Interface {@link ReportGenerator}. Es wird semiautomatisch durch
 * das Google Plugin erstellt und gepflegt.
 * Es enthält sämtliche Methodensignaturen analog zum synchronen Interface, allerdings
 * sind sämtliche Rückgabetypen vom Typ void und es wird ein zusätzlicher Übergabeparameter
 * "AsyncCallback<>" übergeben. Dies ist notwendig um die asynchrone kommunikation zwischen
 * Client und Server zu realisieren.

 *
 * @author Thies, Funke
 */
public interface ReportGeneratorAsync {



  void init(AsyncCallback<Void> callback);

  void createProfilReport(Profil p, AsyncCallback<ProfilReport> callback);

  void createAllNotVisitedProfileReport(Profil p,
      AsyncCallback<AllNotVisitedProfileReport> callback);

  void createAllNewProfilesReport(Profil p, AsyncCallback<AllNewProfileReport> callback);

  void createAllProfilesReport(Profil profil, AsyncCallback<AllProfilesReport> callback);

  void createSuchreportBySuchprofil(Suchprofil sp, Profil p,
      AsyncCallback<AllProfilesBySucheReport> callback);



}
