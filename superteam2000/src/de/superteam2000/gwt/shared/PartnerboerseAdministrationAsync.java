package de.superteam2000.gwt.shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Kontaktsperre;
import de.superteam2000.gwt.shared.bo.Merkzettel;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.bo.Suchprofil;

/**
 * Das asynchrone Gegenstück des Interface {@link Administration}. Es wird semiautomatisch durch das
 * Google Plugin erstellt und gepflegt. Daher erfolgt hier keine weitere Dokumentation. Für weitere
 * Informationen siehe das synchrone Interface {@link Administration}.
 * 
 * @author thies, volz, funke
 */
public interface PartnerboerseAdministrationAsync {
  void init(AsyncCallback<Void> callback);

  void login(String requestUri, AsyncCallback<Profil> async);

  // Profile
  void save(Profil profil, AsyncCallback<Void> callback);

  void createProfil(String nachname, String vorname, String email, Date date, String haarfarbe,
      String raucher, String religion, int groesse, String geschlecht,
      AsyncCallback<Profil> callback);

  void delete(Profil profil, AsyncCallback<Void> callback);

  void getAllProfiles(AsyncCallback<ArrayList<Profil>> callback);

  void getProfilById(int id, AsyncCallback<Profil> callback);

  // Auswahl Eigenschaft
  void createAuswahl(String name, String beschreibungstext, ArrayList<String> alternativen,
      AsyncCallback<Auswahl> callback);

  void delete(Auswahl auswahl, AsyncCallback<Void> callback);

  void save(Auswahl auswahl, AsyncCallback<Void> callback);

  void getAllAuswahl(AsyncCallback<ArrayList<Auswahl>> callback);

  void getAuswahlById(int id, AsyncCallback<Auswahl> callback);

  // Beschreibung Eigenschaft
  void createBeschreibung(String name, String beschreibungstext,
      AsyncCallback<Beschreibung> callback);

  void delete(Beschreibung beschreibung, AsyncCallback<Void> callback);

  void save(Beschreibung beschreibung, AsyncCallback<Void> callback);

  void getAllBeschreibung(AsyncCallback<ArrayList<Beschreibung>> callback);

  void getBeschreibungById(int id, AsyncCallback<Beschreibung> callback);

  // Info

  void save(Info info, AsyncCallback<Void> callback);

  void delete(Info info, AsyncCallback<Void> callback);

  void getInfoByProfile(Profil profil, AsyncCallback<ArrayList<Info>> callback);

  void getInfoByEigenschaftsId(int id, AsyncCallback<Info> callback);

  // Aehnlichkeitsmass
  void getProfilesByAehnlichkeitsmass(Profil profil, AsyncCallback<ArrayList<Profil>> callback);

  // Profilbesuch
  void setVisited(Profil a, Profil b, AsyncCallback<Void> callback);

  void getVisitedProfiles(Profil profil, AsyncCallback<ArrayList<Profil>> callback);

  // Merkzettel
  void createMerken(Profil a, Profil b, AsyncCallback<Void> callback);

  void deleteMerken(Profil entferner, Profil entfernter, AsyncCallback<Void> callback);

  void getMerkzettelForProfil(Profil profil, AsyncCallback<Merkzettel> callback);

  // Kontaktsperre
  void createKontaktsperre(Profil sperrer, Profil gesperrter, AsyncCallback<Void> callback);

  void deleteKontaktsperre(Kontaktsperre kontaktsperre, AsyncCallback<Void> callback);

  void getKontaktsperreForProfil(Profil profil, AsyncCallback<Kontaktsperre> callback);

  void createInfoFor(Profil profil, Auswahl auswahl, String text, AsyncCallback<Info> callback);

  void createInfoFor(Profil profil, Beschreibung beschreibung, String text, AsyncCallback<Info> callback);

  void getProfilByMail(String email, AsyncCallback<Profil> callback);

  void getEigenschaftsNameById(int id, AsyncCallback<String> callback);

  void getAuswahlProfilAttributByName(String name, AsyncCallback<Auswahl> callback);

  void getBeschreibungProfilAttributByName(String name, AsyncCallback<Beschreibung> callback);

  void createSperre(Profil a, Profil b, AsyncCallback<Void> callback);

  void deleteSperre(Profil entferner, Profil entfernter, AsyncCallback<Void> callback);

  void getAllAuswahlProfilAttribute(AsyncCallback<ArrayList<Auswahl>> callback);

  void getAllBeschreibungProfilAttribute(AsyncCallback<ArrayList<Beschreibung>> callback);

  void createSuchprofil(Suchprofil sp, AsyncCallback<Void> callback);

  void getAllSuchprofileForProfil(Profil p, AsyncCallback<ArrayList<Suchprofil>> callback);

  void getSuchprofileForProfilByName(Profil p, String name, AsyncCallback<Suchprofil> callback);

  void deleteSuchprofil(Suchprofil sp, AsyncCallback<Void> callback);

  void save(Suchprofil sp, AsyncCallback<Void> callback);

  void getProfilesBySuchprofil(Suchprofil sp, Profil user,
      AsyncCallback<ArrayList<Profil>> callback);

  void getAllNotVisitedProfilesByAehnlichkeitsmass(Profil p,
      AsyncCallback<ArrayList<Profil>> callback);

  void getAllNewProfilesByAehnlichkeitsmass(Profil p, AsyncCallback<ArrayList<Profil>> callback);

  void getItemsOfSuchprofil(Suchprofil sp, AsyncCallback<ArrayList<String>> callback);

  void getEigenschaftsBeschreibungById(int id, AsyncCallback<String> callback);

  void getInfoById(int id, AsyncCallback<Info> callback);

  // Suchprofil

}
