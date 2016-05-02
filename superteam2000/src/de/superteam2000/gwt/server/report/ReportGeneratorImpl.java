package de.superteam2000.gwt.server.report;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.superteam2000.gwt.server.*;
import de.superteam2000.gwt.shared.*;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.report.AllNewProfileReport;
import de.superteam2000.gwt.shared.report.AllNotVisitedProfileReport;
import de.superteam2000.gwt.shared.report.AllProfileBySuche;
import de.superteam2000.gwt.shared.report.ProfilReport;

public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {

	private PartnerboerseAdministration administration = null;

	public ReportGeneratorImpl() throws IllegalArgumentException {
	}

	@Override
	public void init() throws IllegalArgumentException {
		/*
		 * Ein ReportGeneratorImpl-Objekt instantiiert für seinen Eigenbedarf
		 * eine BankVerwaltungImpl-Instanz.
		 */
		PartnerboerseAdministrationImpl a = new PartnerboerseAdministrationImpl();
		a.init();
		this.administration = a;
	}

	@Override
	public ProfilReport createProfilReport(Profil p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
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
