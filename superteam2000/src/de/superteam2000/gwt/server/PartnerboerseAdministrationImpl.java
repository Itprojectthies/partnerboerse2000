package de.superteam2000.gwt.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.labs.repackaged.com.google.common.base.Objects;
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

		this.aehnlichMapper = AehnlichkeitsmassMapper.aehnlichkeitsmassMapper();
		// this.alternativeMapper alternativMapper = null;
		this.auswahlMapper = AuswahlMapper.auswahlMapper();
		this.beschrMapper = BeschreibungMapper.beschreibungMapper();
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
				ClientsideSettings.getLogger().severe("Userobjekt email " + user.getEmail() + "bestehender user mail  "
						+ bestehendesProfil.getEmail());
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
		 * Setzen einer vorlÃ¤ufigen Kundennr. Der insert-Aufruf liefert dann ein
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
	public ArrayList<Auswahl> getAllAuswahl() throws IllegalArgumentException {
		return this.auswahlMapper.findAll();
	}

	@Override
	public Auswahl getAuswahlById(int id) throws IllegalArgumentException {
		return this.auswahlMapper.findByKey(id);
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
	public void createInfosFor(Map<Integer, Info> infos) throws IllegalArgumentException {
		
		for (Map.Entry<Integer, Info> entry: infos.entrySet()) {
			this.iMapper.insert(entry.getValue());
		}
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
		/* 	Einbeziehung der Auswahleigenschaften und Info-Objekte.
		 * Mögliche Realisierung: 
		 * 
		 * Vergleich der String-Info-Objekte eines Profils mittels
		 * equalsIgnoreCase() oder regionMatches()
		 * 
		 * Auch möglich: Algorithmus, der die Ähnlichkeit von Strings vergleicht, z.B. Jaro-Winkler von http://secondstring.sourceforge.net/,
		 * der einen Wert zwischen 0 und 1 liefert.
		 * Individuelle Gewichtung der Einflussfaktoren möglich
	
		 */
		
		/* speichert alle für das Ähnlichkeitsmaß relevanten Profileigenschaften aus der Datenbank in einer ArrayList */
		ArrayList<Profil> ProfilEig = this.getAllProfils(); 
		/* speichert alle textuellen Info-Objekte aus der Datenbank eines Profils in einer ArrayList*/
		ArrayList<Info>	Info	=this.getallInfos();
		
		
		/* für die Berechnung und Ausgabe des Ähnlichkeitsmaßes in einer ArrayList wird ein Referenzprofil
		 * und ein weiteres Profil zum Vergleich benötigt. Solange noch weitere Profile vorhanden sind, werden diese auch mit
		 * dem Refernzprofil verglichen
		 */
		while (p.next())
		{
			
			
			
			
			
		}
		
		
		 
		
		ArrayList<Profil> result = new ArrayList<>();
		
		
		return result;
		
		
		
		
		
		
		
		
		
		
		
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
	public Beschreibung getBeschreibungById(int id) throws IllegalArgumentException {
		return this.beschrMapper.findByKey(id);
	}

	@Override
	public String getEigenschaftsNameById(int id) throws IllegalArgumentException {
		if (this.beschrMapper.findByKey(id) != null) {
			Beschreibung b = this.beschrMapper.findByKey(id);
			String name = b.getBeschreibungstext();
			return  name;
		}
		else if (this.auswahlMapper.findByKey(id) != null) {
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

	@Override
	public ArrayList<Profil> getProfilesBySuche(Profil p) throws IllegalArgumentException {
		
		if(p != null){ClientsideSettings.getLogger().info("getProfilesBySuche Methode in pbImpl aufgerufen");}
		
		ArrayList<Profil> profile = this.pMapper.findAll();
		ArrayList<Profil> result = new ArrayList<>();
		
		for(Profil profil: profile){
			if(Objects.equal(profil.getGeschlecht(), p.getGeschlecht()) &&
					Objects.equal(profil.getRaucher(), p.getRaucher()) &&
					Objects.equal(profil.getReligion(), p.getReligion()) &&  
					Objects.equal(profil.getHaarfarbe(), p.getHaarfarbe()) ) {
				
				ClientsideSettings.getLogger().info("passendes Profil hinzugefÃ¼gt");
				result.add(profil);
				
			}
		}
		
		
		
		return result;
	}




}