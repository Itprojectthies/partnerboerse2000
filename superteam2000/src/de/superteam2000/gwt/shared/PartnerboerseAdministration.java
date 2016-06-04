package de.superteam2000.gwt.shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Kontaktsperre;
import de.superteam2000.gwt.shared.bo.Merkzettel;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.bo.Suchprofil;

@RemoteServiceRelativePath("pba")
public interface PartnerboerseAdministration extends RemoteService {

	public void init() throws IllegalArgumentException;

	public Profil login(String requestUri) throws IllegalArgumentException;

	public Profil createProfil(String nachname, String vorname, String email, Date date, String haarfarbe,
			String raucher, String religion, int groesse, String geschlecht);

	public void delete(Profil profil) throws IllegalArgumentException;

	public void save(Profil profil) throws IllegalArgumentException;

	public ArrayList<Profil> getAllProfiles() throws IllegalArgumentException;

	public Profil getProfilById(int id) throws IllegalArgumentException;
	
	// Auswahl Eigenschaft
	public Auswahl createAuswahl(String name, String beschreibungstext, ArrayList<String> alternativen)
			throws IllegalArgumentException;

	public void delete(Auswahl auswahl) throws IllegalArgumentException;

	public void save(Auswahl auswahl) throws IllegalArgumentException;

	public ArrayList<Auswahl> getAllAuswahl() throws IllegalArgumentException;

	public Auswahl getAuswahlById(int id) throws IllegalArgumentException;

	// Beschreibung Eigenschaft
	public Beschreibung createBeschreibung(String name, String beschreibungstext) throws IllegalArgumentException;

	public void delete(Beschreibung beschreibung) throws IllegalArgumentException;

	public void save(Beschreibung beschreibung) throws IllegalArgumentException;

	public ArrayList<Beschreibung> getAllBeschreibung() throws IllegalArgumentException;

	public Beschreibung getBeschreibungById(int id) throws IllegalArgumentException;

	// Info

	public void save(Info info) throws IllegalArgumentException;

	public void delete(Info info) throws IllegalArgumentException;

	public ArrayList<Info> getInfoByProfile(Profil profil) throws IllegalArgumentException;

	public Info getInfoByEigenschaftsId(int id) throws IllegalArgumentException;

	// 'hnlichkeitsmaß
	public ArrayList<Profil> getProfilesByAehnlichkeitsmass(Profil profil) throws IllegalArgumentException;

	// Profilbesuch
	public void setVisited(Profil a, Profil b) throws IllegalArgumentException;

	public ArrayList<Profil> getVisitedProfiles(Profil profil) throws IllegalArgumentException;

	// Merkzettel
	public void createMerken(Profil a, Profil b) throws IllegalArgumentException;

	public void deleteMerken(Profil entferner, Profil entfernter) throws IllegalArgumentException;


	// Kontaktsperre
	public void createKontaktsperre(Profil sperrer, Profil gesperrter) throws IllegalArgumentException;

	public void deleteKontaktsperre(Kontaktsperre kontaktsperre) throws IllegalArgumentException;

	public Kontaktsperre getKontaktsperreForProfil(Profil profil) throws IllegalArgumentException;

	public Info createInfoFor(Profil profil, Auswahl auswahl, String text) throws IllegalArgumentException;

	public Info createInfoFor(Profil profil, Beschreibung beschreibung, String text) throws IllegalArgumentException;

	public Profil getProfilByMail(String email) throws IllegalArgumentException;

	public String getEigenschaftsNameById(int id) throws IllegalArgumentException;

	public void createInfosFor(Map<Integer, Info> infos) throws IllegalArgumentException;

	public Auswahl getAuswahlProfilAttributByName(String name) throws IllegalArgumentException;

	public Beschreibung getBeschreibungProfilAttributByName(String name) throws IllegalArgumentException;

	public ArrayList<Auswahl> getAllAuswahlProfilAttribute();

	public String getSelectionForProfilAttributAuswahl(String name, Profil p);

	public ArrayList<Beschreibung> getAllBeschreibungProfilAttribute();

	public Merkzettel getMerkzettelForProfil(Profil profil) throws IllegalArgumentException;

	public void createSperre(Profil a, Profil b) throws IllegalArgumentException;

	public void deleteSperre(Profil entferner, Profil entfernter);

	public void createSuchprofil(Suchprofil sp) throws IllegalArgumentException;

	public ArrayList<Suchprofil> getAllSuchprofileForProfil(Profil p) throws IllegalArgumentException;

	public Suchprofil getSuchprofileForProfilByName(Profil p, String name) throws IllegalArgumentException;

	public void deleteSuchprofil(Suchprofil sp);

	public void save(Suchprofil sp) throws IllegalArgumentException;

	public ArrayList<Profil> getProfilesBySuchprofil(Suchprofil sp) throws IllegalArgumentException;



	// Suchprofil

}
