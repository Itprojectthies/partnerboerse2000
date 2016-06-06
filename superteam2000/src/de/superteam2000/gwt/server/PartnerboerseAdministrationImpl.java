package de.superteam2000.gwt.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.server.db.AuswahlMapper;
import de.superteam2000.gwt.server.db.BeschreibungMapper;
import de.superteam2000.gwt.server.db.InfoMapper;
import de.superteam2000.gwt.server.db.KontaktsperreMapper;
import de.superteam2000.gwt.server.db.MerkzettelMapper;
import de.superteam2000.gwt.server.db.ProfilMapper;
import de.superteam2000.gwt.server.db.SuchprofilMapper;
import de.superteam2000.gwt.shared.PartnerboerseAdministration;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Kontaktsperre;
import de.superteam2000.gwt.shared.bo.Merkzettel;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.bo.Suchprofil;

public class PartnerboerseAdministrationImpl extends RemoteServiceServlet implements PartnerboerseAdministration {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AuswahlMapper auswahlMapper = null;
	private BeschreibungMapper beschrMapper = null;
	private InfoMapper iMapper = null;
	private KontaktsperreMapper kMapper = null;
	private MerkzettelMapper mMapper = null;
	private ProfilMapper pMapper = null;
	private SuchprofilMapper sMapper = null;

	/**
	 * Der momentane Benutzer
	 */

	public PartnerboerseAdministrationImpl() throws IllegalArgumentException {
	}

	@Override
	public void init() throws IllegalArgumentException {

		this.auswahlMapper = AuswahlMapper.auswahlMapper();
		this.beschrMapper = BeschreibungMapper.beschreibungMapper();
		this.iMapper = InfoMapper.infoMapper();
		this.kMapper = KontaktsperreMapper.kontaktsperreMapper();
		this.mMapper = MerkzettelMapper.merkzettelMapper();
		this.pMapper = ProfilMapper.profilMapper();
		this.sMapper = SuchprofilMapper.suchprofilMapper();

	}

	@Override
	public Profil login(String requestUri) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		Profil profil = new Profil();

		if (user != null) {
			Profil bestehendesProfil = this.pMapper.findByEmail(user.getEmail());

			if (bestehendesProfil != null) {
				ClientsideSettings.getLogger().severe("Userobjekt E-Mail =" + user.getEmail() 
					+ ". Bestehender User E-Mail  ="	+ bestehendesProfil.getEmail());
				bestehendesProfil.setLoggedIn(true);
				bestehendesProfil.setLogoutUrl(userService.createLogoutURL(requestUri));

				return bestehendesProfil;
			}

			profil.setLoggedIn(true);
			profil.setLogoutUrl(userService.createLogoutURL(requestUri));
			profil.setEmail(user.getEmail());

		} else {
			profil.setLoggedIn(false);
			profil.setLoginUrl(userService.createLoginURL(requestUri));
		}

		return profil;

	}

	@Override
	public Profil createProfil(String nachname, String vorname, String email, Date geburtsdatum, String haarfarbe,
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
	public void save(Suchprofil sp) throws IllegalArgumentException {
		this.sMapper.update(sp);

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
	public ArrayList<Suchprofil> getAllSuchprofileForProfil(Profil p) throws IllegalArgumentException {
		return this.sMapper.findAllForProfil(p);
	}

	@Override
	public Suchprofil getSuchprofileForProfilByName(Profil p, String name) throws IllegalArgumentException {
		return this.sMapper.findSuchprofilForProfilByName(p, name);
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
	public Auswahl getAuswahlProfilAttributByName(String name) throws IllegalArgumentException {
		return this.auswahlMapper.findByName(name);
	}

	@Override
	public Beschreibung getBeschreibungProfilAttributByName(String name) throws IllegalArgumentException {
		return this.beschrMapper.findByName(name);
	}

	@Override
	public Info createInfoFor(Profil profil, Auswahl auswahl, String text) throws IllegalArgumentException {
			
			
			Info i = new Info();
			i.setText(text);
			i.setEigenschaftId(auswahl.getId());
			i.setProfilId(profil.getId());
			
			ArrayList<Info> infoListe = this.iMapper.findAllByProfilId(profil.getId());
			
			for (Info info : infoListe) {
				if (info.getEigenschaftId() == i.getEigenschaftId() && 
						info.getProfilId() == i.getProfilId() && 
						!info.getText().equals(i.getText()) ) {
					
					log("Info upgedatet");
					return this.iMapper.update(i);
				}else if (info.getEigenschaftId() == i.getEigenschaftId() && 
						info.getProfilId() == i.getProfilId() && 
						info.getText().equals(i.getText())) {
					return null;
				}
			}
			log("Info neuangelegt");
			return this.iMapper.insert(i);
		
	}
	
	@Override
	public Info createInfoFor(Profil profil, Beschreibung beschreibung, String text) throws IllegalArgumentException {
		Info i = new Info();
		i.setText(text);
		i.setEigenschaftId(beschreibung.getId());
		i.setProfilId(profil.getId());

		ArrayList<Info> infoListe = this.iMapper.findAllByProfilId(profil.getId());
		
		for (Info info : infoListe) {
			if (info.getEigenschaftId() == i.getEigenschaftId() && 
					info.getProfilId() == i.getProfilId() && 
					!info.getText().equals(i.getText()) ) {
				
				log("Info upgedatet");
				return this.iMapper.update(i);
			}else if (info.getEigenschaftId() == i.getEigenschaftId() && 
					info.getProfilId() == i.getProfilId() && 
					info.getText().equals(i.getText())) {
				return null;
			}
		}
		log("Info neuangelegt");
		return this.iMapper.insert(i);
	}

	@Override
	public void save(Info info) throws IllegalArgumentException {
		this.iMapper.update(info);
	}

	@Override
	public void delete(Info info) throws IllegalArgumentException {
		if (info != null) {
			this.iMapper.delete(info);
		}
	}

	@Override
	public ArrayList<Info> getInfoByProfile(Profil profil) throws IllegalArgumentException {
		return this.iMapper.findAllByProfilId(profil.getId());
	}

	@Override
	public Info getInfoByEigenschaftsId(int id) throws IllegalArgumentException {
		return this.iMapper.findByKey(id);
	}

	public int berechneAehnlichkeit(Profil p1, Profil p2){
		// 6 Profilattribute: Geb, Geschlecht, Groesse, Haarfarbe, Raucher, Religion
		float i = 6;
		float aehnlichkeit = 0;
		
		if (p1.getAlter() == p2.getAlter()){aehnlichkeit++;}
		if (p1.getGeschlecht().equals( p2.getGeschlecht())){aehnlichkeit++;}
		if (p1.getGroesse() == p2.getGroesse()){aehnlichkeit++;}
		if (p1.getHaarfarbe().equals( p2.getHaarfarbe())){aehnlichkeit++;}
		if (p1.getRaucher().equals( p2.getRaucher())){aehnlichkeit++;}
		if (p1.getReligion().equals( p2.getReligion())){aehnlichkeit++;}
		
		ArrayList<Info> infoP1 = iMapper.findAllByProfilId(p1.getId());
		ArrayList<Info> infoP2 = iMapper.findAllByProfilId(p2.getId());
		
		for (Info meineInfo: infoP1){
			for(Info referenzInfo: infoP2){
				if(meineInfo.equals(referenzInfo)){
					aehnlichkeit++;
					i++;
				}
			}
		}

		int result = Math.round(aehnlichkeit * (100f/i));
		
		return result;
	}
	
	@Override
	public ArrayList<Profil> getAllNewProfilesByAehnlichkeitsmass(Profil p) {
		ArrayList<Profil> alleProfile = pMapper.findAll();
		ArrayList<Profil> neueProfile = new ArrayList<>();
		Kontaktsperre kontaktsperreforProfil = kMapper.findAllForProfil(p);
		ArrayList<Profil> gesperrteProfile = kontaktsperreforProfil.getGesperrteProfile();

		for(Profil profil: gesperrteProfile){
			if(alleProfile.contains(profil)){
				alleProfile.remove(profil);
			}
		}
		for (Profil profil : alleProfile) {
			if(p.getErstelldatum().before(profil.getErstelldatum())) {
				neueProfile.add(profil);
			}			
		}
		
		for(Profil aktProfil: alleProfile){
			int f = this.berechneAehnlichkeit(p, aktProfil);
			aktProfil.setAehnlichkeit(f);
		
		}
		
        Collections.sort(alleProfile, new Comparator<Profil>() {

			@Override
			public int compare(Profil p1, Profil p2) {
				
				return p2.getAehnlichkeit() - p1.getAehnlichkeit();
			}
        	
		}); 
		
		return neueProfile;
	}
	
	@Override
	public ArrayList<Profil> getAllNotVisitedProfilesByAehnlichkeitsmass(Profil p) {
		
		ArrayList<Profil> alleBesuchtenProfile = getVisitedProfiles(p);
		ArrayList<Profil> alleProfile = pMapper.findAll();
		
		Kontaktsperre kontaktsperreforProfil = kMapper.findAllForProfil(p);
		ArrayList<Profil> gesperrteProfile = kontaktsperreforProfil.getGesperrteProfile();

		for(Profil profil: gesperrteProfile){
			if(alleProfile.contains(profil)){
				alleProfile.remove(profil);
			}
		}
		
		for (Profil profil : alleBesuchtenProfile) {
			if(alleProfile.contains(profil)) {
				alleProfile.remove(profil);
			}			
		}
		
		for(Profil aktProfil: alleProfile){
			int f = this.berechneAehnlichkeit(p, aktProfil);
			aktProfil.setAehnlichkeit(f);
		
		}
		
        Collections.sort(alleProfile, new Comparator<Profil>() {

			@Override
			public int compare(Profil p1, Profil p2) {
				
				return p2.getAehnlichkeit() - p1.getAehnlichkeit();
			}
        	
		}); 
		
		return alleProfile;
	}
	
	@Override
	public ArrayList<Profil> getProfilesByAehnlichkeitsmass(Profil profil) throws IllegalArgumentException {
		ArrayList<Profil> alleProfile = pMapper.findAll();
		Kontaktsperre kontaktsperreforProfil = kMapper.findAllForProfil(profil);
		ArrayList<Profil> gesperrteProfile = kontaktsperreforProfil.getGesperrteProfile();

		for(Profil p: gesperrteProfile){
			if(alleProfile.contains(p)){
				alleProfile.remove(p);
			}
		}
		
		for(Profil aktProfil: alleProfile){
			int f = this.berechneAehnlichkeit(profil, aktProfil);
			aktProfil.setAehnlichkeit(f);
		
		}
		
        Collections.sort(alleProfile, new Comparator<Profil>() {

			@Override
			public int compare(Profil p1, Profil p2) {
				
				return p2.getAehnlichkeit() - p1.getAehnlichkeit();
			}
        	
		}); 
        
		return alleProfile;
	}

	@Override
	public void setVisited(Profil besucher, Profil besuchter) throws IllegalArgumentException {
		this.pMapper.setVisited(besucher, besuchter);

	}

	@Override
	public ArrayList<Profil> getVisitedProfiles(Profil profil) throws IllegalArgumentException {
		return this.pMapper.getVisitedProfiles(profil);
	}

	@Override
	public void createMerken(Profil a, Profil b) throws IllegalArgumentException {
		Merkzettel m = mMapper.findAllForProfil(a);
		ArrayList<Profil> profile = m.getGemerkteProfile();
		if (!profile.contains(b)) {
			mMapper.insertMerkenForProfil(a, b);
		}

	}

	@Override
	public void createSperre(Profil a, Profil b) throws IllegalArgumentException {
		kMapper.insertForProfil(a, b);

	}

	@Override
	public void deleteSperre(Profil entferner, Profil entfernter) {
		kMapper.deleteSperreFor(entferner, entfernter);
	}

	@Override
	public void deleteSuchprofil(Suchprofil sp) {
		this.sMapper.delete(sp);
	}

	@Override
	public void deleteMerken(Profil entferner, Profil entfernter) throws IllegalArgumentException {
		mMapper.deleteMerkenFor(entferner, entfernter);
	}

	@Override
	public Merkzettel getMerkzettelForProfil(Profil profil) throws IllegalArgumentException {

		Merkzettel m = mMapper.findAllForProfil(profil);
		Kontaktsperre k = kMapper.findAllForProfil(profil);
		ArrayList<Profil> mListe = m.getGemerkteProfile();
		ArrayList<Profil> kListe = k.getGesperrteProfile();
		
		for(Profil p: kListe){
			if(mListe.contains(p)){
				mListe.remove(p);
			}
		}
		
		return m;
	}

	@Override
	public Kontaktsperre getKontaktsperreForProfil(Profil profil) throws IllegalArgumentException {
		Kontaktsperre k = kMapper.findAllForProfil(profil);
		return k;
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
	public void createSuchprofil(Suchprofil sp) throws IllegalArgumentException {
		this.sMapper.insert(sp);
	}

	@Override
	public Beschreibung getBeschreibungById(int id) throws IllegalArgumentException {
		return this.beschrMapper.findByKey(id);
	}

	@Override
	public String getEigenschaftsNameById(int id) throws IllegalArgumentException {
		if (this.beschrMapper.findByKey(id) != null) {
			Beschreibung b = this.beschrMapper.findByKey(id);
			String name = b.getName();
			return name;
		} else if (this.auswahlMapper.findByKey(id) != null) {
			Auswahl a = this.auswahlMapper.findByKey(id);
			String name = a.getBeschreibungstext();
			return name;
		}
		return "nichts gefunden!";
	}

	@Override
	public ArrayList<Beschreibung> getAllBeschreibung() throws IllegalArgumentException {
		return this.beschrMapper.findAll();
	}

	/*
	 * Diese Methoden brauchen wir wohl nicht
	 */

	@Override
	public void delete(Auswahl auswahl) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(Auswahl auswahl) throws IllegalArgumentException {
		// TODO Auto-generated method stub

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
	public Beschreibung createBeschreibung(String name, String beschreibungstext) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	// Gibt true zurück, wenn die Elemente des SuchprofilListe
	// auch in einer Profilliste vorkommen
	public boolean compare(ArrayList<Info> suchprofilListe, ArrayList<Info> profilListe) {
		int i = suchprofilListe.size();
		int j = 0;
		for (Info spInfo : suchprofilListe) {
			for (Info pInfo : profilListe) {
				if (spInfo.equals(pInfo)) {
					j++;
				}
			}
		}
		if (i == j) {
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<Profil> getProfilesBySuchprofil(Suchprofil sp) throws IllegalArgumentException {

		ArrayList<Profil> profile = this.pMapper.findAll();
		ArrayList<Profil> result = new ArrayList<>();

		ArrayList<Info> suchprofilInfoListe = new ArrayList<>();
		HashMap<Integer, String> auswahlListe = sp.getAuswahlListe();

		// Erstelle aus den infomationen der Hashmap des Suchprofils, Info-
		// Objekte um sie mit den Info-Objekten eines Profils zu vergelichen

		for (Map.Entry<Integer, String> entry : auswahlListe.entrySet()) {
			Info i = new Info();
			i.setEigenschaftId(entry.getKey());
			i.setText(entry.getValue());
			suchprofilInfoListe.add(i);
			// ClientsideSettings.getLogger().info("infos für passendes
			// suchprofil: Id=" + i.getProfilId() + " text= "
			// + i.getText() + " E-Id=" + i.getEigenschaftId());
		}

		for (Profil p : profile) {
			// Liste (profilInfoListe) mit Info-Objekten, die mit der Liste
			// (suchprofilInfoListe) des
			// Suchprofils vergleichen wird

			ArrayList<Info> profilInfoListe = getInfoByProfile(p);
			// for (Info i : profilInfoListe) {
			// ClientsideSettings.getLogger().info("infos für jedes profil: " +
			// i.getProfilId() + " " + i.getText());
			// }
			// Abfragen nach welchen Prfoilattributen gesucht wird
			if ((sp.getHaarfarbe().equals("Keine Angabe") || p.getHaarfarbe().equals(sp.getHaarfarbe()))
					&& (sp.getRaucher().equals("Keine Angabe") || p.getRaucher().equals(sp.getRaucher()))
					&& (sp.getReligion().equals("Keine Angabe") || p.getReligion().equals(sp.getReligion()))
					&& (sp.getGeschlecht().equals("Keine Angabe") || p.getGeschlecht().equals(sp.getGeschlecht()))
					&& (suchprofilInfoListe.size() == 0 || compare(suchprofilInfoListe, profilInfoListe))) {

				// for (Info i : profilInfoListe) {
				// ClientsideSettings.getLogger().info("infos für passendes
				// profil: Id=" + i.getProfilId() + " text= "
				// + i.getText() + " E-Id=" + i.getEigenschaftId());
				// }
				// abfragen on nach Größe oder Alter gesucht wird
				if ((sp.getGroesse_min() != 0 && sp.getGroesse_max() != 0)
						|| (sp.getAlter_min() != 0 && sp.getAlter_max() != 0)) {

					// abfragen on nach Größe und Alter gesucht wird
					if ((sp.getGroesse_min() != 0 && sp.getGroesse_max() != 0)
							&& (sp.getAlter_min() != 0 && sp.getAlter_max() != 0)) {

						// gehe den angegeben Größebereich durch und adde das
						// Profil, wenn es im Bereich liegt
						for (int i = sp.getGroesse_min(); i <= sp.getGroesse_max(); i++) {
							if (p.getGroesse() == i) {
								for (int j = sp.getAlter_min(); j <= sp.getAlter_max(); j++) {
									if (p.getAlter() == j) {
										result.add(p);
									}
								}
							}

						}
					}
					// Abfragen on nur nach Größe gesucht wird
					if ((sp.getGroesse_min() != 0 && sp.getGroesse_max() != 0)
							&& (sp.getAlter_min() == 0 && sp.getAlter_max() == 0)) {
						// gehe den angegeben Größebereich durch und adde das
						// Profil, wenn es im Bereich liegt
						for (int i = sp.getGroesse_min(); i <= sp.getGroesse_max(); i++) {
							if (p.getGroesse() == i) {
								result.add(p);
							}

						}
					}
					// Abfragen on nur nach Alter gesucht wird
					if ((sp.getAlter_min() != 0 && sp.getAlter_max() != 0)
							&& (sp.getGroesse_min() == 0 && sp.getGroesse_max() == 0)) {

						// gehe den angegeben Alters durch und adde das Profil,
						// wenn es im Bereich liegt
						for (int j = sp.getAlter_min(); j <= sp.getAlter_max(); j++) {
							if (p.getAlter() == j) {
								result.add(p);
							}

						}
					}

				} else {

					result.add(p);
				}
			}

		}

		return result;
	}

	@Override
	public ArrayList<Auswahl> getAllAuswahlProfilAttribute() {
		return this.auswahlMapper.findAllProfilAtrribute();
	}

	@Override
	public ArrayList<Beschreibung> getAllBeschreibungProfilAttribute() {
		return this.beschrMapper.findAllProfilAttribute();
	}

	@Override
	public Auswahl createAuswahl(String name, String beschreibungstext, ArrayList<String> alternativen)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

}