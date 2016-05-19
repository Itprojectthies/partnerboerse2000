package de.superteam2000.gwt.shared;
import java.util.Date;

import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.report.ProfilReport;
import de.superteam2000.gwt.shared.report.AllNotVisitedProfileReport;
import de.superteam2000.gwt.shared.report.AllNewProfileReport;
import de.superteam2000.gwt.shared.report.AllProfileBySuche;
import de.superteam2000.gwt.shared.report.AllProfilesReport;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * <p>
 * Synchrone Schnittstelle für eine RPC-fähige Klasse zur Erstellung von
 * Reports. Diese Schnittstelle benutzt die gleiche Realisierungsgrundlage wir
 * das Paar {@link BankAdministration} und {@lBankAdministrationImplImpl}. Zu
 * technischen Erläuterung etwa bzgl. GWT RPC bzw. {@link RemoteServiceServlet}
 * siehe {@link BankAdministration} undBankAdministrationImpltungImpl}.
 * </p>
 * <p>
 * Ein ReportGenerator bietet die Möglichkeit, eine Menge von Berichten
 * (Reports) zu erstellen, die Menge von Daten bzgl. bestimmter Sachverhalte des
 * Systems zweckspezifisch darstellen.
 * </p>
 * <p>
 * Die Klasse bietet eine Reihe von <code>create...</code>-Methoden, mit deren
 * Hilfe die Reports erstellt werden können. Jede dieser Methoden besitzt eine
 * dem Anwendungsfall entsprechende Parameterliste. Diese Parameter benötigt der
 * der Generator, um den Report erstellen zu können.
 * </p>
 * <p>
 * Bei neu hinzukommenden Bedarfen an Berichten, kann diese Klasse auf einfache
 * Weise erweitert werden. Hierzu können zusätzliche <code>create...</code>
 * -Methoden implementiert werden. Die bestehenden Methoden bleiben davon
 * unbeeinflusst, so dass bestehende Programmlogik nicht verändert werden muss.
 * </p>
 * 
 * @author thies
 */
@RemoteServiceRelativePath("rg")
public interface ReportGenerator extends RemoteService {

	/**
	 * TODO: überarbeiten Initialisierung des Objekts. Diese Methode ist vor dem
	 * Hintergrund von GWT RPC zusätzlich zum No Argument Constructor der
	 * implementierenden Klasse Impl} notwendig. Bitte diese Methode direkt nach
	 * der Instantiierung aufrufen.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;

//F�r das Erstellen von einem Report f�r das Anzeigen von einem Profil
	public abstract ProfilReport createProfilReport(Profil p) throws IllegalArgumentException;

	/**
	 * TODO: überarbeiten Erstellen eines
	 * <code>AllAccountsOfCustomerReport</code>-Reports. Dieser Report-Typ
	 * stellt sämtliche Konten eines Kunden dar.
	 * 
	 * @param c
	 *            eine Referenz auf das Kundenobjekt bzgl. dessen der Report
	 *            erstellt werden soll.
	 * @return das fertige Reportobjekt
	 * @throws IllegalArgumentException
	 * @see AllAccountsOfCustomerReport
	 */
	public abstract AllNotVisitedProfileReport createAllNotVisitedProfileReport(Profil p)
			throws IllegalArgumentException;
	
	//public AllProfileBySuche createSuchreport(ArrayList<Profil> p)
	


	/**
	 * TODO: überarbeiten Erstellen eines
	 * <code>AllAccountsOfCustomerReport</code>-Reports. Dieser Report-Typ
	 * stellt sämtliche Konten eines Kunden dar.
	 * 
	 * @param c
	 *            eine Referenz auf das Kundenobjekt bzgl. dessen der Report
	 *            erstellt werden soll.
	 * @return das fertige Reportobjekt
	 * @throws IllegalArgumentException
	 * @see AllAccountsOfCustomerReport
	 */
	public abstract AllNewProfileReport createAllNewProfilesReport(Profil p);

	/**
	 * TODO: überarbeiten Erstellen eines
	 * <code>AllAccountsOfCustomerReport</code>-Reports. Dieser Report-Typ
	 * stellt sämtliche Konten eines Kunden dar.
	 * 
	 * @param c
	 *            eine Referenz auf das Kundenobjekt bzgl. dessen der Report
	 *            erstellt werden soll.
	 * @return das fertige Reportobjekt
	 * @throws IllegalArgumentException
	 * @see AllAccountsOfCustomerReport
	 */
	public abstract AllProfileBySuche createAllProfileBySucheReport(Profil p) throws IllegalArgumentException;
	
	public abstract AllProfilesReport createAllProfilesReport() throws IllegalArgumentException;

	public abstract AllProfileBySuche createSuchreport(ArrayList<Profil> p);


}
