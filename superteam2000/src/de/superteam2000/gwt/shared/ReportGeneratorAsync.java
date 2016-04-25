package de.superteam2000.gwt.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.report.AllNewProfileReport;
import de.superteam2000.gwt.shared.report.AllNotVisitedProfileReport;
import de.superteam2000.gwt.shared.report.AllProfileBySuche;
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

// wieso hat er einen setbank? brauchen wir da auch?
//  void setBank(Bank b, AsyncCallback<Void> callback);

	void init(AsyncCallback<Void> callback);
	
	void createProfilReport(Profil p,
			AsyncCallback<ProfilReport> callback);
	
	void createAllNotVisitedProfileReport (
			Profil p,  AsyncCallback<AllNotVisitedProfileReport> callback);
	
	void createAllNewProfilesReport(
			Profil p, AsyncCallback<AllNewProfileReport> callback);
	
	void createAllProfileBySucheReport(
			Profil p, AsyncCallback<AllProfileBySuche> callback);
	
}
