package de.superteam2000.gwt.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.bo.Suchprofil;
import de.superteam2000.gwt.shared.report.AllNewProfileReport;
import de.superteam2000.gwt.shared.report.AllNotVisitedProfileReport;
import de.superteam2000.gwt.shared.report.AllProfilesBySucheReport;
import de.superteam2000.gwt.shared.report.AllProfilesReport;
import de.superteam2000.gwt.shared.report.ProfilReport;


/**
 * Das asynchrone Gegenstück des Interface {@link ReportGenerator}. Es wird
 * semiautomatisch durch das Google Plugin erstellt und gepflegt. Daher erfolgt
 * hier keine weitere Dokumentation. Für weitere Informationen siehe das
 * synchrone Interface {@link ReportGenerator}.
 * 
 * @author thies, volz
 */
public interface ReportGeneratorAsync {



	void init(AsyncCallback<Void> callback);

	//F�r das Erstellen von einem Report f�r das Anzeigen von einem Profil
	void createProfilReport(Profil p, AsyncCallback<ProfilReport> callback);

	void createAllNotVisitedProfileReport(Profil p, AsyncCallback<AllNotVisitedProfileReport> callback);

	void createAllNewProfilesReport(Profil p, AsyncCallback<AllNewProfileReport> callback);

	void createAllProfilesReport(Profil profil, AsyncCallback<AllProfilesReport> callback);

	void createSuchreportBySuchprofil(Suchprofil sp, Profil p, AsyncCallback<AllProfilesBySucheReport> callback);







}
