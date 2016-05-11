package de.superteam2000.gwt.server;

import java.util.ArrayList;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.server.db.*;
import de.superteam2000.gwt.shared.PartnerboerseAdministration;
import de.superteam2000.gwt.shared.bo.*;

public class PartnerboerseAdministrationImpl extends RemoteServiceServlet implements PartnerboerseAdministration {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AehnlichkeitsmassMapper aehnlichMapper = null;
	private AuswahlMapper auswahlMapper = null;
	private BeschreibungMapper beschrMapper = null;
	private EigenschaftMapper eMapper = null;
	private InfoMapper iMapper = null;
	private KontaktsperreMapper kMapper = null;
	private MerkzettelMapper mMapper = null;
	private ProfilMapper pMapper = null;
	private SuchprofilMapper sMapper = null;

	/**
	 * Der momentane Benutzer
	 */
	private static Profil currentUser = null;

	public PartnerboerseAdministrationImpl() throws IllegalArgumentException {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws IllegalArgumentException {
		// TODO Auto-generated method stub

		this.aehnlichMapper = AehnlichkeitsmassMapper.aehnlichkeitsmassMapper();
		// this.alternativeMapper alternativMapper = null;
		this.auswahlMapper = AuswahlMapper.auswahlMapper();
		this.beschrMapper = BeschreibungMapper.beschreibungMapper();
		this.eMapper = EigenschaftMapper.eigenschaftMapper();
		this.iMapper = InfoMapper.infoMapper();
		this.kMapper = KontaktsperreMapper.kontaktsperreMapper();
		this.mMapper = MerkzettelMapper.merkzettelMapper();
		this.pMapper = ProfilMapper.profilMapper();
		// private SuchprofilMapper sMapper = null;

	}

	@Override
	public Profil login(String requestUri) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		Profil profil = new Profil();

		if (user != null) {
			Profil bestehendesProfil = this.pMapper.findByEmail(user.getEmail());
			
			if (bestehendesProfil != null) {
				ClientsideSettings.getLogger().severe("Userobjekt email "+user.getEmail() + "bestehender user mail  "+ bestehendesProfil.getEmail() );
				bestehendesProfil.setLoggedIn(true);
				bestehendesProfil.setLogoutUrl(userService.createLogoutURL(requestUri));
				
				return bestehendesProfil;	
			}
			
			profil.setLoggedIn(true);
			profil.setLogoutUrl(userService.createLogoutURL(requestUri));
			profil.setEmail(user.getEmail());
			ClientsideSettings.getLogger().severe(" email user " + user.getEmail());

		} else {
			profil.setLoggedIn(false);
			profil.setLoginUrl(userService.createLoginURL(requestUri));
		}

		return profil;

	}
	
//	@Override
//	public Profil getCurrentProfil() {
//		UserService userService = UserServiceFactory.getUserService();
//		User user = userService.getCurrentUser();
//		String Uri = GWT.getHostPageBaseURL() + "Superteam2000.html";
//
//		Profil bestehendesProfil = this.pMapper.findByEmail(user.getEmail());
//
//		ClientsideSettings.getLogger().severe(
//				"Userobjekt email " + user.getEmail() + "bestehender user mail  " + bestehendesProfil.getEmail());
//		
//		bestehendesProfil.setLoggedIn(true);
//		bestehendesProfil.setLogoutUrl(userService.createLogoutURL(Uri));
//
//		return bestehendesProfil;
//
//	}

	@Override
	public Profil createProfil(String nachname, String vorname, String email, String geburtsdatum, String haarfarbe,
			String raucher, String religion, int groesse, String geschlecht) throws IllegalArgumentException {

		Profil p = new Profil();
		p.setNachname(nachname);
		p.setVorname(vorname);
		p.setEmail(email);
		p.setGeburtsdatum(geburtsdatum);
		p.setHaarfarbe(haarfarbe);
		p.setRaucher(raucher);
		p.setReligion(religion);
		p.setGroesse(groesse);
		p.setGeschlecht(geschlecht);

		/*
		 * Setzen einer vorläufigen Kundennr. Der insert-Aufruf liefert dann ein
		 * Objekt, dessen Nummer mit der Datenbank konsistent ist.
		 */
		p.setId(1);
		ClientsideSettings.setCurrentUser(p);
		ClientsideSettings.getLogger().info("user \"" + p.getNachname() + "\" erstellt");

		// Objekt in der DB speichern.
		return this.pMapper.insert(p);

	}

	@Override
	public void delete(Profil profil) throws IllegalArgumentException {
		this.pMapper.delete(profil);
	}

	@Override
	public void save(Profil profil) throws IllegalArgumentException {
		this.pMapper.update(profil);
		
	}

	@Override
	public ArrayList<Profil> getAllProfiles() throws IllegalArgumentException {
		return this.pMapper.findAll();
	}

	@Override
	public Profil getProfilById(int id) {
		

		return this.pMapper.findByKey(id);
	}
	
	@Override
	public Profil getProfilByMail(String email) {
	
		return this.pMapper.findByEmail(email);
	}

	@Override
	public Auswahl createAuswahl(String name, String beschreibungstext, ArrayList<String> alternativen)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Auswahl auswahl) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(Auswahl auswahl) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Auswahl> getAllAuswahl() throws IllegalArgumentException {
		return this.auswahlMapper.findAll();
	}

	@Override
	public Auswahl getAuswahlById(int id) throws IllegalArgumentException {
		
		return this.auswahlMapper.findByKey(id);
	}

	@Override
	public void delete(Beschreibung beschreibung) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(Beschreibung beschreibung) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}
	
	@Override
	public Info createInfoFor(Profil profil, Auswahl auswahl, String text) throws IllegalArgumentException {

		Info i = new Info();
		i.setText(text);
		i.setEigenschaftId(auswahl.getId());
		i.setProfilId(profil.getId());
		
		return this.iMapper.insert(i);
	}

	@Override
	public Info createInfoFor(Profil profil, Beschreibung beschreibung, String text) throws IllegalArgumentException {
		Info i = new Info();
		i.setText(text);
		i.setEigenschaftId(beschreibung.getId());
		i.setProfilId(profil.getId());
		
		return this.iMapper.insert(i);
	}

	@Override
	public void saveInfoForProfil(Profil profil, Info info) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteInfoForProfil(Profil profil, Info info) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Info> getInfoByProfile(Profil profil) throws IllegalArgumentException {
		return this.iMapper.findAllByProfilId(profil.getId());
	}

	@Override
	public Info getInfoByEigenschaftsId(int id) throws IllegalArgumentException {
		return this.iMapper.findByKey(id);
	}

	@Override
	public ArrayList<Profil> getProfilesByAehnlichkeitsmass(Profil profil) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVisited(Profil a, Profil b) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Profil> getVisitedProfiles(Profil profil) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createMerken(Profil a, Profil b) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMerken(Merkzettel merkzettel) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Merkzettel> getAllMerkenForProfil(Profil profil) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createKontaktsperre(Profil sperrer, Profil gesperrter) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteKontaktsperre(Kontaktsperre kontaktsperre) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Kontaktsperre> getKontaktsperreForProfil(Profil profil) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	public void createSuchprofilForProfil(Profil profil) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public Beschreibung createBeschreibung(String name, String beschreibungstext) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Beschreibung getBeschreibungById(int id) throws IllegalArgumentException {
		return this.beschrMapper.findByKey(id);
	}

	@Override
	public ArrayList<Beschreibung> getAllBeschreibung() throws IllegalArgumentException {
		return this.beschrMapper.findAll();
	}


}