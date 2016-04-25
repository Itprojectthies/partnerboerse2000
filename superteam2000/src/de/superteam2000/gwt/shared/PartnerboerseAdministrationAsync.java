package de.superteam2000.gwt.shared;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.superteam2000.gwt.shared.bo.*;

/**
 * Das asynchrone Gegenstück des Interface {@link BankAdministration}. Es wird
 * semiautomatisch durch das Google Plugin erstellt und gepflegt. Daher erfolgt
 * hier keine weitere Dokumentation. Für weitere Informationen siehe das
 * synchrone Interface {@link BankAdministration}.
 * 
 * @author thies, volz, funke
 */
public interface PartnerboerseAdministrationAsync {
	void init(AsyncCallback<Void> callback);
	
//Profile
	void save(Profil profil, AsyncCallback<Void> callback);
	void createProfil(String nachname, String vorname, String email,
			String geburtsdatum, String haarfarbe, String raucher, String 
			religion, int groesse,String geschlecht, AsyncCallback<Profil> callback);
	void delete(Profil profil, AsyncCallback<Void> callback);
	void getAllProfiles(AsyncCallback<ArrayList<Profil>> callback);
	void getProfilById(int id, AsyncCallback<Profil> callback);
	
//Auswahl Eigenschaft
		void createAuswahl(String name, String beschreibungstext,
			ArrayList<String> alternativen, AsyncCallback<Auswahl> callback);
		void delete(Auswahl auswahl, AsyncCallback<Void> callback);
		void save(Auswahl auswahl, AsyncCallback<Void> callback);
		void getAllAuswahl(AsyncCallback<ArrayList<Auswahl>> callback);
		void getAuswahlById(int id, AsyncCallback<Auswahl> callback);
	
	
//Beschreibung Eigenschaft
		void createBeschreibung(String name, String beschreibungstext,
				AsyncCallback<Beschreibung> callback);
		void delete(Beschreibung beschreibung, AsyncCallback<Void> callback);
		void save(Beschreibung beschreibung, AsyncCallback<Void> callback);
		void getAllBeschreibung(AsyncCallback<ArrayList<Beschreibung>> callback);
		void getBeschreibungById(int id, AsyncCallback<Beschreibung> callback);	

// Info 
		void createInfoFor(Profil profil, Auswahl auswahl, 
				AsyncCallback<Info> callback);
		void createInfoFor(Profil profil, Beschreibung beschreibung,
				AsyncCallback<Info> callback);
		void saveInfoForProfil(Profil profil, Info info, 
				AsyncCallback<Void> callback);
		void deleteInfoForProfil(Profil profil, Info info, 
				AsyncCallback<Void> callback);
		void getInfoByProfile(Profil profil, 
				AsyncCallback<ArrayList<Info>> callback);
		void getInfoByEigenschaftsId(int id, 
				AsyncCallback<ArrayList<Info>> callback);
		
		
//Aehnlichkeitsmass
		void getProfilesByAehnlichkeitsmass(Profil profil, 
				AsyncCallback<ArrayList<Profil>> callback);
		
//Profilbesuch
		void setVisited(Profil a, Profil b,
				AsyncCallback<Void> callback);
		void getVisitedProfiles(Profil profil,
				AsyncCallback<ArrayList<Profil>> callback);
		
//Merkzettel
		void createMerken(Profil a, Profil b,
				AsyncCallback<Void> callback);
		void deleteMerken(Merkzettel merkzettel,
				AsyncCallback<Void> callback);
		void getAllMerkenForProfil(Profil profil,
				AsyncCallback<ArrayList<Merkzettel>> callback);
		
//Kontaktsperre
		void createKontaktsperre(Profil sperrer, Profil gesperrter,
				AsyncCallback<Void> callback);
		void deleteKontaktsperre(Kontaktsperre kontaktsperre,
				AsyncCallback<Void> callback);
		void getKontaktsperreForProfil(Profil profil,
				AsyncCallback<ArrayList<Kontaktsperre>> callback);

//Suchprofil
		
		
		
		

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
