package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

import de.superteam2000.gwt.client.gui.CustomButton;
import de.superteam2000.gwt.client.gui.Label;
import de.superteam2000.gwt.client.gui.Notification;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Merkzettel;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Diese Klasse dient den Anzeigen eines fremden Profils. Somit koennen alle Profile angezeigt
 * werden, ausser dem des derzeitigen Users.
 * 
 * @author Volz, Funke
 */
public class ShowFremdProfil extends BasicFrame {
  /*
   * Alle notwendigen Instanzvariablen werden deklariert
   */
  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
  Profil user = ClientsideSettings.getCurrentUser();

  CustomButton merkenBtn = new CustomButton();
  CustomButton sperrenBtn = new CustomButton();

  Profil p = null;
  FlowPanel contentPanel = new FlowPanel();
  FlowPanel buttonsPanel = new FlowPanel();

  /**
   * Konstruktor der Klasse ShowFremdProfil mit einem Profil-Objekt als Parameter
   * 
   * @param p Profil-Objekt
   */
  public ShowFremdProfil(Profil p) {
    this.p = p;
  }

  /**
   * Headline Text zurückgeben.
   * 
   * @return Text
   */
  @Override
  protected String getHeadlineText() {
    return "Profil von " + p.getVorname();
  }

  /**
   * SubHeadline Text zurückgeben.
   * 
   * @return Text
   */
  @Override
  protected String getSubHeadlineText() {
    return "Wie gefällt dir " + p.getVorname() + " " + p.getNachname() + "?";
  }

  /**
   * Layout und grundlegendes Design des Profils werden festgelegt.
   */
  @Override
  protected void run() {
    buttonsPanel.setStyleName("pure-controls-group");

    HTML legend = new HTML();
    String aenhnlickeit = "Ähnlickeitsmaß " + String.valueOf(p.getAehnlichkeit()) + "%";
    legend.setHTML("<legend>" + aenhnlickeit + "</legend>");

    //Beiden Button wird ein Style, ein Icon, ein Text und ein Clickhandler hinzugefuegt.
    merkenBtn.setStyleName("pure-button");
    merkenBtn.setIcon("fa fa-heart-o");
    merkenBtn.setText("Like ");
    merkenBtn.addClickHandler(new MerkenButtonClickhandler());

    sperrenBtn.setStyleName("pure-button");
    sperrenBtn.setIcon("fa fa-ban");
    sperrenBtn.setText("Block ");
    sperrenBtn.addClickHandler(new SperrenButtonClickhandler());

    buttonsPanel.add(merkenBtn);
    buttonsPanel.add(sperrenBtn);
    buttonsPanel.add(legend);
    contentPanel.add(buttonsPanel);

    this.setStyleName("pure-form pure-form-aligned");
    contentPanel.setStyleName("pure-controls-group content");

    pbVerwaltung.getMerkzettelForProfil(user, new MerkzettelCallback());
    pbVerwaltung.getAllAuswahlProfilAttribute(new GetAllAuswahlProfilAttributeCallback());

    this.add(contentPanel);
  }

  /**
   * Methode zum Darstellen der Fremdprofil-Eigenschaften (z.B. Raucher: ja)
   */
  public void createProfileLabels(String eigenschaft, String text) {
    //Panel, um Eigenschaften anzuzeigen
    FlowPanel fremdProfilEigenschaftenPanel = new FlowPanel();
    fremdProfilEigenschaftenPanel.setStyleName("pure-control-group");
    
    //speichert Namen ab
    Label eigenschaftName = new Label(eigenschaft);
    
    //speichert Auswahl ab
    Label eigenschaftText = new Label(text);
    
    eigenschaftText.setStyleName("label-rechts");

    fremdProfilEigenschaftenPanel.add(eigenschaftName);
    fremdProfilEigenschaftenPanel.add(eigenschaftText);

    contentPanel.add(fremdProfilEigenschaftenPanel);
  }

  /**
   * Sobald sich ein Profil auf dem Merkzettel befindet, soll der Button zum merken des Profils als
   * gedrueckt bzw. ausgefuellt erscheinen.
   * 
   */
  private class MerkzettelCallback implements AsyncCallback<Merkzettel> {
    private ArrayList<Profil> profile;

    /**
     * Button als gedrueckt bzw. Profil als markiert markieren, falls Profil au Merkliste gefunden
     * werden konnte.
     */
    @Override
    public void onSuccess(Merkzettel result) {
      //Liste der gemerkten Profile
      profile = result.getGemerkteProfile();

      for (Profil profil : profile) {
        if (p.equals(profil)) {
          merkenBtn.setIcon("fa fa-heart");
          merkenBtn.setPushed(true);
        }
      }
    }

    /**
     * Falls Profil nicht auf Merkliste ist, Fehler ausgeben
     */
    @Override
    public void onFailure(Throwable caught) {}
  }

  /**
   * Funktion des Sperren-Buttons durchfuehren
   * 
   */
  public class SperrenButtonClickhandler implements ClickHandler {

    /**
     * Profil wird auf Sperrliste gesetzt, entsprechende Meldung wird ausgegeben.
     */
    @Override
    public void onClick(ClickEvent event) {
      new Notification("Profil von " + p.getVorname() + " gesperrt", "info");
      pbVerwaltung.createSperre(user, p, new CreateSperreCallback());
    }
  }

  /**
   * Wenn der Sperrbutton eines Profiles gedrueckt wurde, soll sich das Aussehen des Buttons
   * dahingehend veraendern, dass er als gedrueckt bzw. markiert gesetzt wird.
   *
   */
  private class CreateSperreCallback implements AsyncCallback<Void> {

    /**
     * Der Sperren Button wird veraendert, wenn ein Profil gesperrt ist.
     * 
     */
    @Override
    public void onSuccess(Void result) {
      sperrenBtn.setEnabled(false);

      merkenBtn.setIcon("fa fa-heart-o");
      merkenBtn.setEnabled(false);
    }

    /**
     * Falls Profil nicht gesperrt werden konnte oder andere Fehler auftauchen, Fehler- meldungen
     * abfangen.
     */
    @Override
    public void onFailure(Throwable caught) {}
  }

  /**
   * Damit ein User auf die Merkliste gesetzt und davon wieder geloescht werden kann.
   *
   */
  public class MerkenButtonClickhandler implements ClickHandler {

    /**
     * Um Userprofil auf Merkliste setzen und davon wieder entfernen zu koennen.
     */
    @Override
    public void onClick(ClickEvent event) {

      if (!merkenBtn.isPushed()) {
        pbVerwaltung.createMerken(user, p, new CreateMerkenCallback());

      } else {
        pbVerwaltung.deleteMerken(user, p, new DeleteMerkenCallback());
      }
    }
  }

  /**
   * Wird der User von einer Merkliste entfernt, wird der Button veraendert, sodass er das Profil
   * nicht mehr als gemerkt darstellt, sondern wieder normal zu sehen ist.
   */
  private class DeleteMerkenCallback implements AsyncCallback<Void> {

    /**
     * Wenn User von Merkliste entfernt wird, taucht Meldung darueber auf und Button zum merken des
     * Profils im Profil selbst, wird wieder als nicht markiert dargestellt.
     */
    @Override
    public void onSuccess(Void result) {
      new Notification("Profil von " + p.getVorname() + " entmerkt", "info");
      merkenBtn.setIcon("fa fa-heart-o");
      merkenBtn.setPushed(false);
    }

    /**
     * Um etwaige Fehler abzufangen.
     */
    @Override
    public void onFailure(Throwable caught) {}
  }

  /**
   * Wurde ein Userprofil gemerkt, wird der Button zum merken des Profils veraendert. Er wird danach
   * als selektiert bzw. markiert (Icon ist ausgefuellt) dargestellt.
   *
   */
  private class CreateMerkenCallback implements AsyncCallback<Void> {
    @Override
    public void onSuccess(Void result) {
      new Notification("Profil von " + p.getVorname() + " gemerkt", "info");
      merkenBtn.setIcon("fa fa-heart");
      merkenBtn.setPushed(true);
    }

    /**
     * Um Fehler abzufangen.
     */
    @Override
    public void onFailure(Throwable caught) {}
  }

  /**
   * Um alle festen Eigenschaften eines Profils anzeigen zu koennen.
   * 
   */
  private class GetAllAuswahlProfilAttributeCallback implements AsyncCallback<ArrayList<Auswahl>> {

    /**
     * Die Attribute Religion, Haarfarbe, Geschlecht und Raucher sind Auswahlattribute und werden
     * hier ausgelesen und zurueckgegeben.
     */
    @Override
    public void onSuccess(ArrayList<Auswahl> result) {

      createProfileLabels("Email", p.getEmail());
      createProfileLabels("Alter", String.valueOf(p.getAlter()) + " Jahre ");
      createProfileLabels("Größe", String.valueOf(p.getGroesse()) + " cm");

      for (Auswahl a : result) {
        switch (a.getName()) {
          case "Religion":
            createProfileLabels(a.getName(), p.getReligion());
            break;
          case "Haarfarbe":
            createProfileLabels(a.getName(), p.getHaarfarbe());
            break;
          case "Geschlecht":
            createProfileLabels(a.getName(), p.getGeschlecht());
            break;
          case "Raucher":
            createProfileLabels(a.getName(), p.getRaucher());
            break;
        }
      }
      pbVerwaltung.getInfoByProfile(p, new InfoCallback());

    }

    /**
     * Um Fehler abzufangen.
     */
    @Override
    public void onFailure(Throwable caught) {}
  }

  /**
   * Die freiwilligen Eigenschaften sollen ausgegeben werden.
   *
   */
  private class InfoCallback implements AsyncCallback<ArrayList<Info>> {

    /**
     * Um Fehler auszugeben.
     */
    @Override
    public void onFailure(Throwable caught) {}

    /**
     * Die ausgelesenen, vorhandenen Info Eigenschaften des Profils werden ausgegeben.
     */
    @Override
    public void onSuccess(ArrayList<Info> result) {
      for (Info i : result) {
        if (i != null) {
          pbVerwaltung.getAuswahlById(i.getEigenschaftId(), new GetAuswahlCallback(i));
          pbVerwaltung.getBeschreibungById(i.getEigenschaftId(), new GetBeschreibungCallback(i));
        }
      }
    }
  }

  /**
   * Um die Infoeigenschaften auslesen zu koennen.
   * 
   */
  private class GetAuswahlCallback implements AsyncCallback<Auswahl> {

    private Info i = null;

    /**
     * Konstruktor mit Übergabeparameter info
     * 
     * @param i Info-Objekt
     */
    public GetAuswahlCallback(Info i) {
      this.i = i;
    }

    /**
     * Um Fehler abzufangen.
     */
    @Override
    public void onFailure(Throwable caught) {}

    /**
     * Infoeigenschaften werden aus Datenbank ausgelesen.
     */
    @Override
    public void onSuccess(Auswahl result) {
      createProfileLabels(result.getName(), i.getText());
    }

  }

  /**
   * Der Beschreibungstext zum Infoobjekt soll ausgelesen und angezeigt werden.
   * 
   *
   */
  private class GetBeschreibungCallback implements AsyncCallback<Beschreibung> {

    private Info i = null;

    /**
     * Konstruktor mit Übergabeparameter info
     * 
     * @param i Info-Objekt
     */
    public GetBeschreibungCallback(Info i) {
      this.i = i;
    }

    /**
     * Um Fehler abzufangen.
     */
    @Override
    public void onFailure(Throwable caught) {}

    /**
     * Ausgelesene Beschreibungstexte werden in Label uebertragen fuer Anzeige.
     */
    @Override
    public void onSuccess(Beschreibung result) {
      createProfileLabels(result.getName(), i.getText());
    }

  }

}
