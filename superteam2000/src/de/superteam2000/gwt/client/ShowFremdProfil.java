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
 * Klasse zum anzeigen eines Fremdprofils
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


  public ShowFremdProfil(Profil p) {
    this.p = p;
  }

  @Override
  protected String getHeadlineText() {
    return "Profil von " + p.getVorname();
  }

  @Override
  protected String getSubHeadlineText() {
    return "Wie gefällt dir " + p.getVorname() + " " + p.getNachname() + "?";
  }


  @Override
  protected void run() {
    buttonsPanel.setStyleName("pure-controls-group");

    HTML legend = new HTML();
    String aenhnlickeit = "Ähnlickeitsmaß " + String.valueOf(p.getAehnlichkeit()) + "%";
    legend.setHTML("<legend>" + aenhnlickeit + "</legend>");

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

  /*
   * Methode zum Darstellen der Fremdprofil-Eigenschaften (z.B. Raucher: ja)
   */
  public void createProfileLabels(String eigenschaft, String text) {
    FlowPanel fremdProfilEigenschaftenPanel = new FlowPanel();
    fremdProfilEigenschaftenPanel.setStyleName("pure-control-group");

    Label eigenschaftName = new Label(eigenschaft);
    Label eigenschaftText = new Label(text);
    eigenschaftText.setStyleName("label-rechts");

    fremdProfilEigenschaftenPanel.add(eigenschaftName);
    fremdProfilEigenschaftenPanel.add(eigenschaftText);

    contentPanel.add(fremdProfilEigenschaftenPanel);
  }


  private class MerkzettelCallback implements AsyncCallback<Merkzettel> {
    private ArrayList<Profil> profile;

    @Override
    public void onSuccess(Merkzettel result) {
      profile = result.getGemerkteProfile();
      
      // Wenn Fremdprofil auf dem Merkzettel ist, soll der MerkButton gesetzt sein (Herz ausgefüllt)
      for (Profil profil : profile) {
        if (p.equals(profil)) {
          merkenBtn.setIcon("fa fa-heart");
          merkenBtn.setPushed(true);
        }
      }
    }

    @Override
    public void onFailure(Throwable caught) {}
  }

  public class SperrenButtonClickhandler implements ClickHandler {


    @Override
    public void onClick(ClickEvent event) {
      new Notification("Profil von " + p.getVorname() + " gesperrt", "info");
      pbVerwaltung.createSperre(user, p, new CreateSperreCallback());
    }
  }

  private class CreateSperreCallback implements AsyncCallback<Void> {
    @Override
    public void onSuccess(Void result) {
      sperrenBtn.setEnabled(false);

      merkenBtn.setIcon("fa fa-heart-o");
      merkenBtn.setEnabled(false);
    }

    @Override
    public void onFailure(Throwable caught) {}
  }


  public class MerkenButtonClickhandler implements ClickHandler {

    @Override
    public void onClick(ClickEvent event) {

      if (!merkenBtn.isPushed()) {
        pbVerwaltung.createMerken(user, p, new CreateMerkenCallback());

      } else {
        pbVerwaltung.deleteMerken(user, p, new DeleteMerkenCallback());
      }
    }
  }

  private class DeleteMerkenCallback implements AsyncCallback<Void> {
    @Override
    public void onSuccess(Void result) {
      new Notification("Profil von " + p.getVorname() + " entmerkt", "info");
      merkenBtn.setIcon("fa fa-heart-o");
      merkenBtn.setPushed(false);
    }

    @Override
    public void onFailure(Throwable caught) {}
  }

  private class CreateMerkenCallback implements AsyncCallback<Void> {
    @Override
    public void onSuccess(Void result) {
      new Notification("Profil von " + p.getVorname() + " gemerkt", "info");
      merkenBtn.setIcon("fa fa-heart");
      merkenBtn.setPushed(true);
    }

    @Override
    public void onFailure(Throwable caught) {}
  }

  private class GetAllAuswahlProfilAttributeCallback implements AsyncCallback<ArrayList<Auswahl>> {

    @Override
    public void onSuccess(ArrayList<Auswahl> result) {
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
      createProfileLabels("Alter", String.valueOf(p.getAlter()) + " Jahre ");
      createProfileLabels("Größe", String.valueOf(p.getGroesse()) + " cm");

      pbVerwaltung.getInfoByProfile(p, new InfoCallback());

    }

    @Override
    public void onFailure(Throwable caught) {}
  }

  private class InfoCallback implements AsyncCallback<ArrayList<Info>> {

    @Override
    public void onFailure(Throwable caught) {}

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

  private class GetAuswahlCallback implements AsyncCallback<Auswahl> {

    private Info i = null;

    public GetAuswahlCallback(Info i) {
      this.i = i;
    }

    @Override
    public void onFailure(Throwable caught) {
    }

    @Override
    public void onSuccess(Auswahl result) {
      createProfileLabels(result.getName(), i.getText());
    }

  }

  private class GetBeschreibungCallback implements AsyncCallback<Beschreibung> {

    private Info i = null;

    public GetBeschreibungCallback(Info i) {
      this.i = i;
    }

    @Override
    public void onFailure(Throwable caught) {
    }

    @Override
    public void onSuccess(Beschreibung result) {
      createProfileLabels(result.getName(), i.getText());
    }

  }

}
