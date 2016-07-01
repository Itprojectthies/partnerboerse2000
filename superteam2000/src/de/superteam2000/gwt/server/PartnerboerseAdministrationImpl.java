package de.superteam2000.gwt.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.client.gui.DateTimeFormat;
import de.superteam2000.gwt.server.db.AuswahlMapper;
import de.superteam2000.gwt.server.db.BeschreibungMapper;
import de.superteam2000.gwt.server.db.InfoMapper;
import de.superteam2000.gwt.server.db.KontaktsperreMapper;
import de.superteam2000.gwt.server.db.MerkzettelMapper;
import de.superteam2000.gwt.server.db.ProfilMapper;
import de.superteam2000.gwt.server.db.SuchprofilMapper;
import de.superteam2000.gwt.server.report.ReportGeneratorImpl;
import de.superteam2000.gwt.shared.PartnerboerseAdministration;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Kontaktsperre;
import de.superteam2000.gwt.shared.bo.Merkzettel;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.bo.Suchprofil;

/**
 * <p>
 * Implementierungsklasse des Interface PartnerboerseAdministration. Diese Klasse ist die Klasse,
 * die neben {@link ReportGeneratorImpl} sämtliche Applikationslogik beinhaltet. Die
 * Applikationslogik befindet sich in den Methoden dieser Klasse. Sie ist der Dreh- und Angelpunkt
 * des Projekts.
 * 
 * PartnerboerseAdministrationImpl und PartnerboerseAdministration bilden nur die Server-seitige
 * Sicht der Applikationslogik ab. Diese basiert vollständig auf synchronen Funktionsaufrufen. Jede
 * Server-seitig instantiierbare und Client-seitig über GWT RPC nutzbare Klasse muss die Klasse
 * RemoteServiceServlet implementieren.
 * 
 * @see PartnerboerseAdministration
 * @see PartnerboerseAdministrationAsync
 * @see RemoteServiceServlet
 * @author Thies, Funke, Volz
 */
public class PartnerboerseAdministrationImpl extends RemoteServiceServlet
    implements PartnerboerseAdministration {


  private static final long serialVersionUID = 1L;
  private AuswahlMapper auswahlMapper = null;
  private BeschreibungMapper beschrMapper = null;
  private InfoMapper iMapper = null;
  private KontaktsperreMapper kMapper = null;
  private MerkzettelMapper mMapper = null;
  private ProfilMapper pMapper = null;
  private SuchprofilMapper sMapper = null;

  /**
   * Ein RemoteServiceServlet wird unter GWT mittels GWT.create(Klassenname.class) Client-seitig
   * erzeugt. Hierzu ist ein solcher No-Argument-Konstruktor anzulegen. Ein Aufruf eines anderen
   * Konstruktors ist durch die Client-seitige Instantiierung durch GWT.create(Klassenname.class)
   * nach derzeitigem Stand nicht möglich. Es bietet sich also an, eine separate Instanzenmethode zu
   * erstellen, die Client-seitig direkt nach GWT.create(Klassenname.class) aufgerufen wird, um eine
   * Initialisierung der Instanz vorzunehmen.
   *
   *
   */

  public PartnerboerseAdministrationImpl() throws IllegalArgumentException {}

  /**
   * Initialsierungsmethode. Siehe dazu Anmerkungen zum No-Argument-Konstruktor
   * {@link #ReportGeneratorImpl()}. Diese Methode muss für jede Instanz von
   * PartnerboerseAdministrationImpl aufgerufen werden.
   *
   * @see #ReportGeneratorImpl()
   */

  @Override
  public void init() throws IllegalArgumentException {

    auswahlMapper = AuswahlMapper.auswahlMapper();
    beschrMapper = BeschreibungMapper.beschreibungMapper();
    iMapper = InfoMapper.infoMapper();
    kMapper = KontaktsperreMapper.kontaktsperreMapper();
    mMapper = MerkzettelMapper.merkzettelMapper();
    pMapper = ProfilMapper.profilMapper();
    sMapper = SuchprofilMapper.suchprofilMapper();

  }

  public void addInfosToUsers() {
    ArrayList<Profil> alleProfile = this.pMapper.findAll();
    ArrayList<Auswahl> alleAuswahl = this.auswahlMapper.findAll();


    for (Auswahl auswahl : alleAuswahl) {
      
      ArrayList<String> auswahlAlternativen = auswahl.getAlternativen();
      
      String[] auswahlAlternativenArray = new String[alleAuswahl.size()];
      
      auswahlAlternativen.toArray(auswahlAlternativenArray);

      for (Profil p : alleProfile) {
        String text = auswahlAlternativenArray[new Random().nextInt(auswahlAlternativenArray.length)];
        if (text != null) {
          this.createInfoFor(p, auswahl, text);
          
        }

      }
    }

  }

  public void seed() {
    String[] vornamenMale = {"Paul", "Peter", "Max", "Florian", "Johannes", "Daniel", "Christopher",
        "Benjamin", "Christian", "Michael", "Maximilian", "Julian", "Robert", "Simon", "Jesus",
        "Josef", "Siddatha", "Mario", "Manuel", "Dominik", "Thomas"};

    String[] vornamenFemale = {"Paula", "Petera", "Nora", "Ulrike", "Christina", "Olga", "Meike",
        "Lina", "Caroline", "Christina", "Stefanie", "Elisabeth", "Anne", "Adelheid", "Anna",
        "Simone", "Theresa", "Mia", "Pia", "Melanie", "Vanessa"};

    String[] nachnamen = {"Müller", "Schulz", "Baumann", "Reiter", "Bosch", "Stein", "Burkhardt",
        "Bürkle", "Miller", "Seifert", "Lang", "Schneider", "Weber", "Bauer", "Schröder", "Klein",
        "Schwarz", "Zimmermann", "Krüger", "Hoffmann", "Hafner"};

    String[] haarfarbe = {"blond", "schwarz", "brünett", "dunkel-blond", "rot"};
    String[] religion =
        {"römisch-katholisch", "römisch-orthodox", "muslimisch", "jüdisch", "evangelisch"};

    String[] geschlecht = {"männlich", "weiblich"};
    String[] raucher = {"Ja", "Nein"};



    for (int i = 0; i < 100; i++) {
      Profil p = new Profil();
      p.setGeschlecht(geschlecht[new Random().nextInt(geschlecht.length)]);

      if (p.getGeschlecht().equals("weiblich")) {
        p.setVorname(vornamenFemale[new Random().nextInt(vornamenFemale.length)]);
      } else {
        p.setVorname(vornamenMale[new Random().nextInt(vornamenMale.length)]);
      }

      p.setNachname(nachnamen[new Random().nextInt(nachnamen.length)]);
      p.setHaarfarbe(haarfarbe[new Random().nextInt(haarfarbe.length)]);
      p.setReligion(religion[new Random().nextInt(religion.length)]);
      p.setRaucher(raucher[new Random().nextInt(raucher.length)]);
      p.setGroesse(ThreadLocalRandom.current().nextInt(140, 200 + 1));
      int geburtsJahr = ThreadLocalRandom.current().nextInt(1960, 1995 + 1);
      int geburtsMonat = ThreadLocalRandom.current().nextInt(1, 11 + 1);
      int geburtsTag = ThreadLocalRandom.current().nextInt(1, 29 + 1);

      p.setEmail(p.getVorname() + "." + p.getNachname() + "@mail.com");

      Date gebTag = DateTimeFormat.getFormat("yyyy-MM-dd")
          .parse(geburtsJahr + "-" + geburtsMonat + "-" + geburtsTag);
      java.sql.Date gebTagSql = new java.sql.Date(gebTag.getTime());
      p.setGeburtsdatum(gebTagSql);

      this.pMapper.insert(p);
    }

  }

  /*
   * **************************************************************************************
   * ABSCHNITT, Beginn: Login
   * ***************************************************************************+++++++++++
   */
  /**
   * Login eines Users mit Überprüfung ob User schon ein Profil angelegt hat
   */
  @Override
  public Profil login(String requestUri) {
    // seed();
//    addInfosToUsers();
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();

    Profil profil = new Profil();

    if (user != null) {
      Profil bestehendesProfil = pMapper.findByEmail(user.getEmail());

      if (bestehendesProfil != null) {
        ClientsideSettings.getLogger().severe("Userobjekt E-Mail = " + user.getEmail()
            + "  Bestehender User: E-Mail  =" + bestehendesProfil.getEmail());
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
      profil.setLogoutUrl(userService.createLogoutURL(requestUri));
    }

    return profil;

  }
  /*
   * **************************************************************************************
   * ABSCHNITT, Ende: Login
   * ***************************************************************************+++++++++++
   */

  /*
   * **************************************************************************************
   * ABSCHNITT, Beginn: Methoden für Profil-Objekte
   * **************************************************************************************
   */
  /**
   * Erstellung eines neuen Profils
   */
  @Override
  public Profil createProfil(String nachname, String vorname, String email, Date geburtsdatum,
      String haarfarbe, String raucher, String religion, int groesse, String geschlecht)
      throws IllegalArgumentException {

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
     * Setzen einer vorläufigen Kundennr. Der insert-Aufruf liefert dann ein Objekt, dessen Nummer
     * mit der Datenbank konsistent ist.
     */
    p.setId(1);
    ClientsideSettings.setCurrentUser(p);
    ClientsideSettings.getLogger().info("user " + p.getNachname() + " erstellt");

    // Objekt in der DB speichern.
    return pMapper.insert(p);

  }

  /**
   * Löschen eines bestehendes Profils
   */
  @Override
  public void delete(Profil profil) throws IllegalArgumentException {
    pMapper.delete(profil);
  }

  /**
   * Speichern eines Profils
   */
  @Override
  public void save(Profil profil) throws IllegalArgumentException {
    pMapper.update(profil);

  }

  /**
   * Auslesen von Profile sortiert anhand des Ähnlichkeitsmaß
   */
  @Override
  public ArrayList<Profil> getAllProfilesByAehnlichkeit(Profil p) throws IllegalArgumentException {
    ArrayList<Profil> profile = pMapper.findAll();

    for (Profil aktProfil : profile) {
      int f = berechneAehnlichkeit(p, aktProfil);
      aktProfil.setAehnlichkeit(f);
    }
    Collections.sort(profile, new Comparator<Profil>() {

      @Override
      public int compare(Profil p1, Profil p2) {

        return p2.getAehnlichkeit() - p1.getAehnlichkeit();
      }

    });

    profile.remove(p);

    return profile;
  }

  /**
   * Auslesen aller Profile
   */

  @Override
  public ArrayList<Profil> getAllProfiles() throws IllegalArgumentException {
    return pMapper.findAll();
  }

  /**
   * Auslesen eines Profils mit einer bestimmten Id
   */

  @Override
  public Profil getProfilById(int id) {
    return pMapper.findByKey(id);
  }

  /**
   * Auslesen eines Profils mit einer bestimmten E-Mail-Adresse
   */

  @Override
  public Profil getProfilByMail(String email) {
    return pMapper.findByEmail(email);

  }

  /**
   * Auslesen eines Profils anhand des Suchprofils
   */

  @Override
  public ArrayList<Profil> getProfilesBySuchprofil(Suchprofil sp, Profil user)
      throws IllegalArgumentException {

    ArrayList<Profil> profile = pMapper.findAll();
    ArrayList<Profil> result = new ArrayList<Profil>();

    ArrayList<Info> suchprofilInfoListe = new ArrayList<Info>();
    HashMap<Integer, String> auswahlListe = sp.getAuswahlListe();

    // Erstelle aus den infomationen der Hashmap des Suchprofils, Info-
    // Objekte um sie mit den Info-Objekten eines Profils zu vergelichen

    for (Map.Entry<Integer, String> entry : auswahlListe.entrySet()) {
      if (entry.getValue().equals("Keine Angabe")) {
        continue;
      }
      Info i = new Info();
      i.setEigenschaftId(entry.getKey());
      i.setText(entry.getValue());
      suchprofilInfoListe.add(i);
    }

    for (Profil p : profile) {
      // Liste (profilInfoListe) mit Info-Objekten, die mit der Liste
      // (suchprofilInfoListe) des
      // Suchprofils vergleichen wird

      ArrayList<Info> profilInfoListe = getInfoByProfile(p);
      // Abfragen nach welchen Prfoilattributen gesucht wird
      if ((sp.getHaarfarbe().equals("Keine Angabe") || p.getHaarfarbe().equals(sp.getHaarfarbe()))
          && (sp.getRaucher().equals("Keine Angabe") || p.getRaucher().equals(sp.getRaucher()))
          && (sp.getReligion().equals("Keine Angabe") || p.getReligion().equals(sp.getReligion()))
          && (sp.getGeschlecht().equals("Keine Angabe")
              || p.getGeschlecht().equals(sp.getGeschlecht()))
          && ((suchprofilInfoListe.size() == 0) || compare(suchprofilInfoListe, profilInfoListe))) {

        // abfragen on nach Größe oder Alter gesucht wird
        if (((sp.getGroesse_min() != 0) && (sp.getGroesse_max() != 0))
            || ((sp.getAlter_min() != 0) && (sp.getAlter_max() != 0))) {

          // abfragen on nach Größe und Alter gesucht wird
          if (((sp.getGroesse_min() != 0) && (sp.getGroesse_max() != 0))
              && ((sp.getAlter_min() != 0) && (sp.getAlter_max() != 0))) {

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
          if (((sp.getGroesse_min() != 0) && (sp.getGroesse_max() != 0))
              && ((sp.getAlter_min() == 0) && (sp.getAlter_max() == 0))) {
            // gehe den angegeben Größebereich durch und adde das
            // Profil, wenn es im Bereich liegt
            for (int i = sp.getGroesse_min(); i <= sp.getGroesse_max(); i++) {
              if (p.getGroesse() == i) {
                result.add(p);
              }

            }
          }
          // Abfragen on nur nach Alter gesucht wird
          if (((sp.getAlter_min() != 0) && (sp.getAlter_max() != 0))
              && ((sp.getGroesse_min() == 0) && (sp.getGroesse_max() == 0))) {

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

    for (Profil akt : result) {
      akt.setAehnlichkeit(berechneAehnlichkeitforSuchprofil(user, akt, sp));
    }
    Collections.sort(result, new Comparator<Profil>() {

      @Override
      public int compare(Profil p1, Profil p2) {

        return p2.getAehnlichkeit() - p1.getAehnlichkeit();
      }

    });

    // das eigene Profil entfernen
    result.remove(user);
    return result;
  }

  /**
   * Auslesen aller neuen Profile sortiert anhand des Ähnlichkeitsmaß
   */

  @Override
  public ArrayList<Profil> getAllNewProfilesByAehnlichkeitsmass(Profil p) {
    ArrayList<Profil> alleProfile = pMapper.findAll();
    ArrayList<Profil> neueProfile = new ArrayList<Profil>();
    Kontaktsperre kontaktsperreforProfil = kMapper.findAllForProfil(p);
    ArrayList<Profil> gesperrteProfile = kontaktsperreforProfil.getGesperrteProfile();

    for (Profil profil : gesperrteProfile) {
      if (alleProfile.contains(profil)) {
        alleProfile.remove(profil);
      }
    }

    for (Profil profil : alleProfile) {
      if (p.getErstelldatum().before(profil.getErstelldatum())) {
        neueProfile.add(profil);
      }
    }

    for (Profil aktProfil : neueProfile) {
      int f = berechneAehnlichkeit(p, aktProfil);
      aktProfil.setAehnlichkeit(f);
    }

    Collections.sort(neueProfile, new Comparator<Profil>() {

      @Override
      public int compare(Profil p1, Profil p2) {
        return p2.getAehnlichkeit() - p1.getAehnlichkeit();
      }

    });

    neueProfile.remove(p);
    return neueProfile;
  }

  /**
   * Auslesen aller nicht besuchten Profile sortiert anhnand des Ähnlichkeitsmaß
   */

  @Override
  public ArrayList<Profil> getAllNotVisitedProfilesByAehnlichkeitsmass(Profil p) {

    ArrayList<Profil> alleBesuchtenProfile = getVisitedProfiles(p);
    ArrayList<Profil> alleProfile = pMapper.findAll();

    Kontaktsperre kontaktsperreforProfil = kMapper.findAllForProfil(p);
    ArrayList<Profil> gesperrteProfile = kontaktsperreforProfil.getGesperrteProfile();

    for (Profil profil : gesperrteProfile) {
      if (alleProfile.contains(profil)) {
        alleProfile.remove(profil);
      }
    }

    for (Profil profil : alleBesuchtenProfile) {
      if (alleProfile.contains(profil)) {
        alleProfile.remove(profil);
      }
    }

    for (Profil aktProfil : alleProfile) {
      int f = berechneAehnlichkeit(p, aktProfil);
      aktProfil.setAehnlichkeit(f);

    }


    Collections.sort(alleProfile, new Comparator<Profil>() {

      @Override
      public int compare(Profil p1, Profil p2) {

        return p2.getAehnlichkeit() - p1.getAehnlichkeit();
      }

    });

    alleProfile.remove(p);

    return alleProfile;
  }

  /**
   * Auslesen von Profile sortiert anhand des Ähnlichkeitsmaß
   */

  @Override
  public ArrayList<Profil> getProfilesByAehnlichkeitsmass(Profil profil)
      throws IllegalArgumentException {
    ArrayList<Profil> alleProfile = pMapper.findAll();
    Kontaktsperre kontaktsperreforProfil = kMapper.findAllForProfil(profil);
    ArrayList<Profil> gesperrteProfile = kontaktsperreforProfil.getGesperrteProfile();

    for (Profil p : gesperrteProfile) {
      if (alleProfile.contains(p)) {
        alleProfile.remove(p);
      }
    }

    for (Profil aktProfil : alleProfile) {
      int f = berechneAehnlichkeit(profil, aktProfil);
      aktProfil.setAehnlichkeit(f);

    }

    Collections.sort(alleProfile, new Comparator<Profil>() {

      @Override
      public int compare(Profil p1, Profil p2) {
        return p2.getAehnlichkeit() - p1.getAehnlichkeit();
      }
    });

    alleProfile.remove(profil);


    return alleProfile;
  }

  /**
   * Setzen eines Marker, wenn ein Profil von einem User besucht wurde.
   */

  @Override
  public void setVisited(Profil besucher, Profil besuchter) throws IllegalArgumentException {
    pMapper.setVisited(besucher, besuchter);

  }

  /**
   * Auslesen aller besuchten Profile
   */

  @Override
  public ArrayList<Profil> getVisitedProfiles(Profil profil) throws IllegalArgumentException {
    return pMapper.getVisitedProfiles(profil);
  }

  /*
   * **************************************************************************************
   * ABSCHNITT, Ende: Methoden für Profil-Objekte
   * **************************************************************************************
   */

  /*
   * **************************************************************************************
   * ABSCHNITT, Beginn: Methoden für Suchprofil-Objekte
   * **************************************************************************************
   */

  /**
   * Speichern eines Suchprofils
   */

  @Override
  public void save(Suchprofil sp) throws IllegalArgumentException {
    sMapper.update(sp);

  }

  /**
   * Löschen eines Suchprofils
   */

  @Override
  public void deleteSuchprofil(Suchprofil sp) {
    sMapper.delete(sp);
  }

  /**
   * Erstellen eines Suchprofils
   */

  @Override
  public void createSuchprofil(Suchprofil sp) throws IllegalArgumentException {
    sMapper.insert(sp);
  }


  /**
   * Auslesen aller Suchprofile eines Profils
   */

  @Override
  public ArrayList<Suchprofil> getAllSuchprofileForProfil(Profil p)
      throws IllegalArgumentException {
    return sMapper.findAllForProfil(p);
  }

  /**
   * Auslesen aller Suchprofileinträge und sie in einer Arrayliste zurückgeben
   */

  @Override
  public ArrayList<String> getItemsOfSuchprofil(Suchprofil sp) {
    ArrayList<String> itemsList = new ArrayList<String>();

    if (!sp.getReligion().equals("Keine Angabe"))
      itemsList.add("Religion: " + sp.getReligion());
    if (!sp.getHaarfarbe().equals("Keine Angabe"))
      itemsList.add("Haarfarbe: " + sp.getHaarfarbe());
    if (!sp.getRaucher().equals("Keine Angabe"))
      itemsList.add("Raucher: " + sp.getRaucher());
    if (!sp.getGeschlecht().equals("Keine Angabe"))
      itemsList.add("Geschlecht: " + sp.getGeschlecht());
    if ((sp.getAlter_min() != 0) && (sp.getAlter_max() != 0)) {
      itemsList.add("Minimales Alter: " + String.valueOf(sp.getAlter_min()));
      itemsList.add("Maximales Alter: " + String.valueOf(sp.getAlter_max()));
    }

    if ((sp.getGroesse_min() != 0) && (sp.getGroesse_max() != 0)) {
      itemsList.add("Minimale Größe: " + String.valueOf(sp.getGroesse_min()));
      itemsList.add("Maximale Größe: " + String.valueOf(sp.getGroesse_max()));
    }

    HashMap<Integer, String> auswahlListe = sp.getAuswahlListe();

    for (Map.Entry<Integer, String> entry : auswahlListe.entrySet()) {
      getEigenschaftsNameById(entry.getKey());
      if (getEigenschaftsNameById(entry.getKey()) != null) {
        itemsList.add(getEigenschaftsNameById(entry.getKey()) + ": " + entry.getValue());
      }
    }

    // Keine Suchkriterien im Suhprofil
    if (itemsList.size() == 0) {
      itemsList.add("Keine");

    }

    return itemsList;
  }

  /**
   * Auslesen aller Suchprofile eines Profils mit einem bestimmten Namen
   */

  @Override
  public Suchprofil getSuchprofileForProfilByName(Profil p, String name)
      throws IllegalArgumentException {
    return sMapper.findSuchprofilForProfilByName(p, name);
  }

  /**
   * Gibt true zurück, wenn die Elemente des SuchprofilListe auch in einer Profilliste vorkommen
   */

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


  /*
   * *************************************************************************** ABSCHNITT, Ende:
   * Methoden für Suchprofil-Objekte
   * ***************************************************************************
   */

  /*
   * *************************************************************************** ABSCHNITT, Beginn:
   * Methoden für Auswahl-Objekte
   * ***************************************************************************
   */

  /**
   * Auslesen der gesamten Auswahlmöglichkeiten
   */

  @Override
  public ArrayList<Auswahl> getAllAuswahl() throws IllegalArgumentException {
    return auswahlMapper.findAll();
  }

  /**
   * Auslesen der Auswahl mit einer bestimmten Id
   */

  @Override
  public Auswahl getAuswahlById(int id) throws IllegalArgumentException {
    return auswahlMapper.findByKey(id);
  }

  /**
   * Auslesen der Auswahl von Profilattributen mit einem bestimmten Namen
   */

  @Override
  public Auswahl getAuswahlProfilAttributByName(String name) throws IllegalArgumentException {
    return auswahlMapper.findByName(name);
  }

  /**
   * Löschen eines Auswahl-Objekts
   */

  @Override
  public void delete(Auswahl auswahl) throws IllegalArgumentException {
    // TODO Auto-generated method stub

  }

  /**
   * Speichern eines Auswahl-Objekts
   */

  @Override
  public void save(Auswahl auswahl) throws IllegalArgumentException {
    // TODO Auto-generated method stub

  }

  /**
   * Erstellen der Auswahl-Objekte
   */

  @Override
  public Auswahl createAuswahl(String name, String beschreibungstext,
      ArrayList<String> alternativen) throws IllegalArgumentException {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Auslesen der Auswahl-Objekte eines Profilattributs
   */

  @Override
  public ArrayList<Auswahl> getAllAuswahlProfilAttribute() {
    return auswahlMapper.findAllProfilAtrribute();
  }

  /*
   * *************************************************************************** ABSCHNITT, Ende:
   * Methoden für Auswahl-Objekte
   * ***************************************************************************
   */

  /*
   * *************************************************************************** ABSCHNITT, Beginn:
   * Methoden für Beschreibungs-Objekte
   * ***************************************************************************
   */

  /**
   * Auslesen der Beschreibungs-Objekte von Profilattributen mit einem bestimmten Namen
   */

  @Override
  public Beschreibung getBeschreibungProfilAttributByName(String name)
      throws IllegalArgumentException {
    return beschrMapper.findByName(name);
  }

  /**
   * Auslesen der Beschreibung mit einer bestimmten Id
   */

  @Override
  public Beschreibung getBeschreibungById(int id) throws IllegalArgumentException {
    return beschrMapper.findByKey(id);
  }

  /**
   * Auslesen aller Beschreibungs-Objekte
   */

  @Override
  public ArrayList<Beschreibung> getAllBeschreibung() throws IllegalArgumentException {
    return beschrMapper.findAll();
  }

  /**
   * Löschen eines Beschreibungs-Objekt
   */

  @Override
  public void delete(Beschreibung beschreibung) throws IllegalArgumentException {
    // TODO Auto-generated method stub
  }

  /**
   * Speichern eines Beschreibungs-Objekt
   */

  @Override
  public void save(Beschreibung beschreibung) throws IllegalArgumentException {
    // TODO Auto-generated method stub

  }

  /**
   * Erstellen eines Beschreibungs-Objekt
   */

  @Override
  public Beschreibung createBeschreibung(String name, String beschreibungstext)
      throws IllegalArgumentException {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Auslesen aller Beschreibungs-Objekte eines Profils
   */

  @Override
  public ArrayList<Beschreibung> getAllBeschreibungProfilAttribute() {
    return beschrMapper.findAllProfilAttribute();
  }

  /*
   * *************************************************************************** ABSCHNITT, Ende:
   * Methoden für Beschreibungs-Objekte
   * ***************************************************************************
   */

  /*
   * *************************************************************************** ABSCHNITT, Beginn:
   * Methoden für Info-Objekte
   * ***************************************************************************
   */

  /**
   * Erstellen von Info-Objekte für ein Profil mit vorgegebener Auswahl
   */

  @Override
  public Info createInfoFor(Profil profil, Auswahl auswahl, String text)
      throws IllegalArgumentException {


    Info i = new Info();
    i.setText(text);
    i.setEigenschaftId(auswahl.getId());
    i.setProfilId(profil.getId());

    ArrayList<Info> infoListe = iMapper.findAllByProfilId(profil.getId());

    for (Info info : infoListe) {
      if ((info.getEigenschaftId() == i.getEigenschaftId())
          && (info.getProfilId() == i.getProfilId()) && !info.getText().equals(i.getText())) {

        this.log("Info upgedatet");
        return iMapper.update(i);

      } else if ((info.getEigenschaftId() == i.getEigenschaftId())
          && (info.getProfilId() == i.getProfilId()) && info.getText().equals(i.getText())) {
        return null;
      }
    }
    this.log("Info neuangelegt");
    return iMapper.insert(i);

  }

  /**
   * Erstellen von Info-Objekten für ein Profil mit Freitext
   */

  @Override
  public Info createInfoFor(Profil profil, Beschreibung beschreibung, String text)
      throws IllegalArgumentException {
    Info i = new Info();
    i.setText(text);
    i.setEigenschaftId(beschreibung.getId());
    i.setProfilId(profil.getId());

    ArrayList<Info> infoListe = iMapper.findAllByProfilId(profil.getId());

    for (Info info : infoListe) {
      if ((info.getEigenschaftId() == i.getEigenschaftId())
          && (info.getProfilId() == i.getProfilId()) && !info.getText().equals(i.getText())) {

        this.log("Info upgedatet");
        return iMapper.update(i);
      } else if ((info.getEigenschaftId() == i.getEigenschaftId())
          && (info.getProfilId() == i.getProfilId()) && info.getText().equals(i.getText())) {
        return null;
      }
    }
    this.log("Info neuangelegt");
    return iMapper.insert(i);
  }

  /**
   * Speichern von Info-Objekten
   */

  @Override
  public void save(Info info) throws IllegalArgumentException {
    iMapper.update(info);
  }

  /**
   * Löschen von Info-Objekten
   */

  @Override
  public void delete(Info info) throws IllegalArgumentException {
    if (info != null) {
      iMapper.delete(info);
    }
  }

  /**
   * Auslesen von Info-Objekten eines Profils
   */

  @Override
  public ArrayList<Info> getInfoByProfile(Profil profil) throws IllegalArgumentException {
    return iMapper.findAllByProfilId(profil.getId());
  }

  /**
   * Auslesen von Info-Objekten mit einer bestimmten Eigenschafts-Id
   */

  @Override
  public Info getInfoByEigenschaftsId(int id) throws IllegalArgumentException {
    return iMapper.findByKey(id);
  }

  /**
   * Auslesen eines Infoobjekts über seine ID
   */

  @Override
  public Info getInfoById(int id) throws IllegalArgumentException {
    return iMapper.findByKey(id);
  }

  /*
   * *************************************************************************** ABSCHNITT, Ende:
   * Methoden für Info-Objekte
   * ***************************************************************************
   */

  /*
   * *************************************************************************** ABSCHNITT, Beginn:
   * Methoden für Ähnlichkeitsmaß
   * ***************************************************************************
   */

  /**
   * Berechnen des Ähnlichkeitsmaß für ein Suchprofil
   *
   * @param p1
   * @param p2
   * @param sp
   * @return result (Ähnlichkeitsmaß zwischen zwei Profilen)
   */

  public int berechneAehnlichkeitforSuchprofil(Profil p1, Profil p2, Suchprofil sp) {
    Profil pNeu = p1;
    float i = 4;
    float aehnlichkeit = 0;

    // neues Profil mit "angepassten" Angaben anhand des Suchprofils erstellen

    if (!pNeu.getGeschlecht().equals(sp.getGeschlecht())
        && !sp.getGeschlecht().equals("Keine Angabe")) {
      pNeu.setGeschlecht(sp.getGeschlecht());
    }
    if (!pNeu.getHaarfarbe().equals(sp.getHaarfarbe())
        && !sp.getHaarfarbe().equals("Keine Angabe")) {
      pNeu.setHaarfarbe(sp.getHaarfarbe());
    }
    if (!pNeu.getRaucher().equals(sp.getRaucher()) && !sp.getRaucher().equals("Keine Angabe")) {
      pNeu.setRaucher(sp.getRaucher());
    }
    if (!pNeu.getReligion().equals(sp.getReligion()) && !sp.getReligion().equals("Keine Angabe")) {
      pNeu.setReligion(sp.getReligion());
    }



    HashMap<Integer, String> auswahlListe = sp.getAuswahlListe();

    // Liste der Infos des Suchprofils aus der Map ziehen
    ArrayList<Info> suchprofilInfoListe = new ArrayList<Info>();
    for (Map.Entry<Integer, String> entry : auswahlListe.entrySet()) {
      Info info = new Info();
      info.setEigenschaftId(entry.getKey());
      info.setText(entry.getValue());
      suchprofilInfoListe.add(info);
    }
    ArrayList<Info> neuesProfiInfoListe = getInfoByProfile(p1);

    for (Info spInfo : suchprofilInfoListe) {
      if (!(neuesProfiInfoListe.contains(spInfo))) {
        neuesProfiInfoListe.add(spInfo);
      }
    }

    // JETZT wird erst die Ähnlichkeit zwischen pNeu und p2 berechnet


    if (pNeu.getGeschlecht().equals(p2.getGeschlecht())) {
      aehnlichkeit++;
    }
    if (pNeu.getHaarfarbe().equals(p2.getHaarfarbe())) {
      aehnlichkeit++;
    }
    if (pNeu.getRaucher().equals(p2.getRaucher())) {
      aehnlichkeit++;
    }
    if (pNeu.getReligion().equals(p2.getReligion())) {
      aehnlichkeit++;
    }

    if ((sp.getGroesse_min() != 0) && (sp.getGroesse_min() < p2.getGroesse())) {
      aehnlichkeit++;
      i++;
    }

    if ((sp.getGroesse_max() != 0) && (p2.getGroesse() < sp.getGroesse_max())) {
      aehnlichkeit++;
      i++;
    }

    if ((sp.getAlter_min() != 0) && (sp.getAlter_min() < p2.getAlter())) {
      aehnlichkeit++;
      i++;
    }

    if ((sp.getAlter_max() != 0) && (p2.getAlter() < sp.getAlter_max())) {
      aehnlichkeit++;
      i++;
    }


    ArrayList<Info> infoP2 = getInfoByProfile(p2);

    for (Info meineInfo : neuesProfiInfoListe) {
      for (Info referenzInfo : infoP2) {
        if (meineInfo.equals(referenzInfo)) {
          aehnlichkeit++;
          i++;
        }
      }
    }
    int result = Math.round(aehnlichkeit * (100f / i));

    return result;
  }

  /**
   * Berechnung des Ähnlichkeitsmaßes zwischen zwei Profilen
   *
   * @param p1
   * @param p2
   * @return result (Ähnlichkeitsmaß zwischen zwei Profilen)
   */

  public int berechneAehnlichkeit(Profil user, Profil refernz) {
    // 6 Profilattribute: Geb, Geschlecht, Groesse, Haarfarbe, Raucher, Religion
    float i = 6;
    float aehnlichkeit = 0;

    if (user.getAlter() == refernz.getAlter()) {
      aehnlichkeit++;
    }
    if (user.getGeschlecht().equals(refernz.getGeschlecht())) {
      aehnlichkeit++;
    }
    if (user.getGroesse() == refernz.getGroesse()) {
      aehnlichkeit++;
    }
    if (user.getHaarfarbe().equals(refernz.getHaarfarbe())) {
      aehnlichkeit++;
    }
    if (user.getRaucher().equals(refernz.getRaucher())) {
      aehnlichkeit++;
    }
    if (user.getReligion().equals(refernz.getReligion())) {
      aehnlichkeit++;
    }

    ArrayList<Info> infoP1 = iMapper.findAllByProfilId(user.getId());
    ArrayList<Info> infoP2 = iMapper.findAllByProfilId(refernz.getId());

    for (Info meineInfo : infoP1) {
      for (Info referenzInfo : infoP2) {
        if (meineInfo.equals(referenzInfo)) {
          aehnlichkeit++;
          i++;
        }
      }
    }

    int result = Math.round(aehnlichkeit * (100f / i));

    return result;
  }

  /*
   * *************************************************************************** ABSCHNITT, Ende:
   * Methoden für Ähnlichkeitsmaß
   * ***************************************************************************
   */

  /*
   * *************************************************************************** ABSCHNITT, Beginn:
   * Methoden für Kontaktsperre
   * ***************************************************************************
   */

  /**
   * Erstellen einer Kontaktsperre
   */

  @Override
  public void createSperre(Profil a, Profil b) throws IllegalArgumentException {
    kMapper.insertForProfil(a, b);

  }

  /**
   * Löschen einer Kontaktsperre
   */

  @Override
  public void deleteSperre(Profil entferner, Profil entfernter) {
    kMapper.deleteSperreFor(entferner, entfernter);
  }

  /**
   * Auslesen der gesamten gesperrten Profile eines Profils
   */

  @Override
  public Kontaktsperre getKontaktsperreForProfil(Profil profil) throws IllegalArgumentException {
    Kontaktsperre k = kMapper.findAllForProfil(profil);
    ArrayList<Profil> kListe = k.getGesperrteProfile();

    for (Profil p : kListe) {
      p.setAehnlichkeit(berechneAehnlichkeit(profil, p));
    }

    return k;
  }

  /*
   * *************************************************************************** ABSCHNITT, Ende:
   * Methoden für Kontaktsperre
   * ***************************************************************************
   */

  /*
   * *************************************************************************** ABSCHNITT, Beginn:
   * Methoden für Merkzettel
   * ***************************************************************************
   */

  /**
   * Erstellen einer Sperre für einen Kontakt
   */

  @Override
  public void createMerken(Profil a, Profil b) throws IllegalArgumentException {
    Merkzettel m = mMapper.findAllForProfil(a);
    ArrayList<Profil> profile = m.getGemerkteProfile();
    if (!profile.contains(b)) {
      mMapper.insertMerkenForProfil(a, b);
    }

  }

  /**
   * Löschen einer Sperre für einen Kontakt
   */

  @Override
  public void deleteMerken(Profil entferner, Profil entfernter) throws IllegalArgumentException {
    mMapper.deleteMerkenFor(entferner, entfernter);
  }

  /**
   * Auslesen des Merkzettels für ein Profil
   */

  @Override
  public Merkzettel getMerkzettelForProfil(Profil profil) throws IllegalArgumentException {

    Merkzettel m = mMapper.findAllForProfil(profil);
    Kontaktsperre k = kMapper.findAllForProfil(profil);
    ArrayList<Profil> mListe = m.getGemerkteProfile();
    ArrayList<Profil> kListe = k.getGesperrteProfile();

    for (Profil p : kListe) {
      if (mListe.contains(p)) {
        mListe.remove(p);
      }
    }

    for (Profil p : mListe) {
      p.setAehnlichkeit(berechneAehnlichkeit(profil, p));
    }

    return m;
  }

  /*
   * *************************************************************************** ABSCHNITT, Ende:
   * Methoden für Merkzettel
   * ***************************************************************************
   */

  /*
   * *************************************************************************** ABSCHNITT, Beginn:
   * Methoden für Eigenschafts-Objekte
   * ***************************************************************************
   */

  /**
   * Auslesen der Eigenschaftsnamen mit einer bestimmten Id
   */

  @Override
  public String getEigenschaftsNameById(int id) throws IllegalArgumentException {
    if (beschrMapper.findByKey(id) != null) {
      Beschreibung b = beschrMapper.findByKey(id);
      String name = b.getName();
      return name;
    } else if (auswahlMapper.findByKey(id) != null) {
      Auswahl a = auswahlMapper.findByKey(id);
      String name = a.getName();
      return name;
    }
    return null;
  }

  /**
   * Auslesen der Eigenschaftsbeschreibung mit einer bestimmten Id
   */

  @Override
  public String getEigenschaftsBeschreibungById(int id) throws IllegalArgumentException {
    if (beschrMapper.findByKey(id) != null) {
      Beschreibung b = beschrMapper.findByKey(id);
      String name = b.getBeschreibungstext();
      return name;
    } else if (auswahlMapper.findByKey(id) != null) {
      Auswahl a = auswahlMapper.findByKey(id);
      String name = a.getBeschreibungstext();
      return name;
    }
    return null;
  }


}
