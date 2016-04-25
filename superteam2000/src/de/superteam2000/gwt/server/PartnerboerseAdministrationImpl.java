package de.superteam2000.gwt.server;


import java.util.ArrayList;
import java.util.Date;

import de.superteam2000.gwt.server.db.*;
import de.superteam2000.gwt.shared.PartnerboerseAdministration;
import de.superteam2000.gwt.shared.bo.*;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


@SuppressWarnings("serial")
public class PartnerboerseAdministrationImpl extends RemoteServiceServlet
	implements PartnerboerseAdministration {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AehnlichkeitsmassMapper aehnlichMapper=null;
	private AlternativeMapper alternativMapper = null;
	private AuswahlMapper auswahlMapper = null;
	private BeschreibungMapper beschrMapper = null;
	private EigenschaftMapper eMapper = null;
	private InfoMapper iMapper = null;
	private KontaktsperreMapper kMapper = null;
	private MerkzettelMapper mMapper = null;
	private ProfilMapper pMapper = null;
	private SuchprofilMapper sMapper = null;
	
	
	
	public PartnerboerseAdministrationImpl() throws IllegalArgumentException{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		this.aehnlichMapper = AehnlichkeitsmassMapper.aehnlichkeitsmassMapper();
		//this.alternativeMapper alternativMapper = null;
		this.auswahlMapper = AuswahlMapper.auswahlMapper();
		this.beschrMapper = BeschreibungMapper.beschreibungMapper();
		this.eMapper = EigenschaftMapper.eigenschaftMapper();
		this.iMapper = InfoMapper.infoMapper();
		this.kMapper = KontaktsperreMapper.kontaktsperreMapper();
		this.mMapper = MerkzettelMapper.merkzettelMapper();
		this.pMapper = ProfilMapper.profilMapper();
		//private SuchprofilMapper sMapper = null;
		
		
	}

	@Override
	public Profil createProfil(String nachname, String vorname, String email, Date geburtsdatum, String haarfarbe,
			String raucher, String religion, int groesse) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Profil p = new Profil();
		return p;
	}

	@Override
	public void delete(Profil profil) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(Profil profil) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Profil> getAllProfiles() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return this.pMapper.findAll();
	}

	@Override
	public Profil getProfilById(int id) {
		// TODO Auto-generated method stub
		
		
		return this.pMapper.findByKey(id);
	}

	@Override
	public Auswahl createAuswahl(String name, String beschreibungstext, 
			ArrayList<String> alternativen) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Auswahl auswahl) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(Auswahl auswahl) throws IllegalArgumentException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Auswahl> getAllAuswahl() throws IllegalArgumentException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Auswahl getAuswahlById(int id) throws IllegalArgumentException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Beschreibung beschreibung) throws IllegalArgumentException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(Beschreibung beschreibung) throws IllegalArgumentException{
		// TODO Auto-generated method stub
		
	}


	@Override
	public Info createInfoFor(Profil profil, Auswahl auswahl) throws IllegalArgumentException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Info createInfoFor(Profil profil, 
			Beschreibung beschreibung) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveInfoForProfil(Profil profil, Info info) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteInfoForProfil(Profil profil, Info info) throws IllegalArgumentException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Info> getInfoByProfile(Profil profil) throws IllegalArgumentException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Info> getInfoByEigenschaftsId(int id) throws IllegalArgumentException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Profil> getProfilesByAehnlichkeitsmass(Profil profil) 
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVisited(Profil a, Profil b) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Profil> getVisitedProfiles(Profil profil) 
			throws IllegalArgumentException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createMerken(Profil a, Profil b) throws IllegalArgumentException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMerken(Merkzettel merkzettel) 
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Merkzettel> getAllMerkenForProfil(Profil profil)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createKontaktsperre(Profil sperrer, Profil gesperrter) 
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteKontaktsperre(Kontaktsperre kontaktsperre) 
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Kontaktsperre> getKontaktsperreForProfil(Profil profil) 
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	public void createSuchprofilForProfil(Profil profil) 
			throws IllegalArgumentException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Beschreibung createBeschreibung(String name, String beschreibungstext) 
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Beschreibung getBeschreibungById(int id) throws IllegalArgumentException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Beschreibung> getAllBeschreibung() 
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}