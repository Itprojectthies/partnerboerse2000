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

public class ShowFremdProfil extends BasicFrame {
  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
  Profil profil = ClientsideSettings.getCurrentUser();

  CustomButton merkenBtn = new CustomButton();
  CustomButton sperrenBtn = new CustomButton();

  Profil p = null;
  FlowPanel fPanel = new FlowPanel();
  FlowPanel buttonsPanel = new FlowPanel();


  public ShowFremdProfil(Profil p) {
    this.p = p;
  }

  @Override
  protected String getHeadlineText() {
    return "Profil von " + this.p.getVorname();
  }

  @Override
  protected String getSubHeadlineText() {
    // TODO Auto-generated method stub
    return "Wie gefällt dir " + this.p.getVorname() + " " + this.p.getNachname() + "?";
  }


  @Override
  protected void run() {
    this.buttonsPanel.setStyleName("pure-controls-group");

    HTML legend = new HTML();
    String aenhnlickeit = "Ähnlickeitsmaß " + String.valueOf(this.p.getAehnlichkeit()) + "%";
    legend.setHTML("<legend>" + aenhnlickeit + "</legend>");



    this.merkenBtn.setStyleName("pure-button");
    this.merkenBtn.setIcon("fa fa-heart-o");
    this.merkenBtn.setText("Like ");
    this.merkenBtn.addClickHandler(new MerkenButtonClickhandler());



    this.sperrenBtn.setStyleName("pure-button");
    this.sperrenBtn.setIcon("fa fa-ban");
    this.sperrenBtn.setText("Block ");
    this.sperrenBtn.addClickHandler(new SperrenButtonClickhandler());

    this.buttonsPanel.add(this.merkenBtn);
    this.buttonsPanel.add(this.sperrenBtn);
    this.buttonsPanel.add(legend);
    this.fPanel.add(this.buttonsPanel);


    this.setStyleName("pure-form pure-form-aligned");
    this.fPanel.setStyleName("pure-controls-group content");

    this.pbVerwaltung.getMerkzettelForProfil(this.profil, new MerkzettelCallback());


    this.pbVerwaltung.getAllAuswahlProfilAttribute(new GetAllAuswahlProfilAttributeCallback());
    this.add(this.fPanel);
  }

  public void createProfileLabels(String eigenschaft, String text) {
    FlowPanel f = new FlowPanel();
    f.setStyleName("pure-control-group");
    Label l1 = new Label(eigenschaft);
    Label l2 = new Label(text);
    l2.setStyleName("label-rechts");
    f.add(l1);
    f.add(l2);

    this.fPanel.add(f);
  }


  private final class MerkzettelCallback implements AsyncCallback<Merkzettel> {
    private ArrayList<Profil> profile;

    @Override
    public void onSuccess(Merkzettel result) {
      this.profile = result.getGemerkteProfile();
      for (Profil profil : this.profile) {
        if (ShowFremdProfil.this.p.equals(profil)) {
          ShowFremdProfil.this.merkenBtn.setIcon("fa fa-heart");
          ShowFremdProfil.this.merkenBtn.setPushed(true);
        }
      }


    }

    @Override
    public void onFailure(Throwable caught) {

    }
  }

  public class SperrenButtonClickhandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      new Notification("Profil von " + ShowFremdProfil.this.p.getVorname() + " gesperrt", "info");

      if (ShowFremdProfil.this.p != null) {
        ClientsideSettings.getPartnerboerseVerwaltung().createSperre(
            ClientsideSettings.getCurrentUser(), ShowFremdProfil.this.p, new AsyncCallback<Void>() {

              @Override
              public void onSuccess(Void result) {
                ShowFremdProfil.this.sperrenBtn.setEnabled(false);
                ShowFremdProfil.this.merkenBtn.setIcon("fa fa-heart-o");
                ShowFremdProfil.this.merkenBtn.setEnabled(false);
              }

              @Override
              public void onFailure(Throwable caught) {

            }
            });
      }

    }
  }

  public class MerkenButtonClickhandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {


      if (!ShowFremdProfil.this.merkenBtn.isPushed()) {

        ClientsideSettings.getPartnerboerseVerwaltung().createMerken(
            ClientsideSettings.getCurrentUser(), ShowFremdProfil.this.p, new AsyncCallback<Void>() {

              @Override
              public void onSuccess(Void result) {
                new Notification("Profil von " + ShowFremdProfil.this.p.getVorname() + " gemerkt",
                    "info");
                ShowFremdProfil.this.merkenBtn.setIcon("fa fa-heart");
                ShowFremdProfil.this.merkenBtn.setPushed(true);
              }

              @Override
              public void onFailure(Throwable caught) {


            }
            });

      } else {
        ShowFremdProfil.this.pbVerwaltung.deleteMerken(ClientsideSettings.getCurrentUser(),
            ShowFremdProfil.this.p, new AsyncCallback<Void>() {

              @Override
              public void onSuccess(Void result) {
                new Notification("Profil von " + ShowFremdProfil.this.p.getVorname() + " entmerkt",
                    "info");
                ShowFremdProfil.this.merkenBtn.setIcon("fa fa-heart-o");
                ShowFremdProfil.this.merkenBtn.setPushed(false);
              }

              @Override
              public void onFailure(Throwable caught) {
                // TODO Auto-generated method stub

              }
            });
      }

    }
  }

  private class GetAllAuswahlProfilAttributeCallback implements AsyncCallback<ArrayList<Auswahl>> {

    @Override
    public void onSuccess(ArrayList<Auswahl> result) {
      for (Auswahl a : result) {
        switch (a.getName()) {
          case "Religion":
            ShowFremdProfil.this.createProfileLabels(a.getName(),
                ShowFremdProfil.this.p.getReligion());
            break;
          case "Haarfarbe":
            ShowFremdProfil.this.createProfileLabels(a.getName(),
                ShowFremdProfil.this.p.getHaarfarbe());
            break;
          case "Geschlecht":
            ShowFremdProfil.this.createProfileLabels(a.getName(),
                ShowFremdProfil.this.p.getGeschlecht());
            break;
          case "Raucher":
            ShowFremdProfil.this.createProfileLabels(a.getName(),
                ShowFremdProfil.this.p.getRaucher());
            break;
        }
      }
      ShowFremdProfil.this.createProfileLabels("Alter",
          String.valueOf(ShowFremdProfil.this.p.getAlter()) + " Jahre ");
      ShowFremdProfil.this.createProfileLabels("Größe",
          String.valueOf(ShowFremdProfil.this.p.getGroesse()) + " cm");



      ShowFremdProfil.this.pbVerwaltung.getInfoByProfile(ShowFremdProfil.this.p,
          new InfoCallback());

    }

    @Override
    public void onFailure(Throwable caught) {
      // TODO Auto-generated method stub

    }
  }

  private class InfoCallback implements AsyncCallback<ArrayList<Info>> {

    // private BasicFrame b = null;
    //
    // public InfoCallback(BasicFrame b) {
    // this.b = b;
    // }

    @Override
    public void onFailure(Throwable caught) {}

    @Override
    public void onSuccess(ArrayList<Info> result) {
      for (Info i : result) {
        if (i != null) {
          // table.setText(rowCounter, 1, i.getText());

          ShowFremdProfil.this.pbVerwaltung.getAuswahlById(i.getEigenschaftId(),
              new GetAuswahlCallback(i));
          ShowFremdProfil.this.pbVerwaltung.getBeschreibungById(i.getEigenschaftId(),
              new GetBeschreibungCallback(i));

        }

      }



    }
  }

  private class GetAuswahlCallback implements AsyncCallback<Auswahl> {

    // private BasicFrame b = null;
    private Info i = null;
    // HTML html = new HTML();

    public GetAuswahlCallback(Info i) {
      this.i = i;
    }

    @Override
    public void onFailure(Throwable caught) {

    }

    @Override
    public void onSuccess(Auswahl result) {

      ShowFremdProfil.this.createProfileLabels(result.getName(), this.i.getText());
    }

  }

  private class GetBeschreibungCallback implements AsyncCallback<Beschreibung> {

    // private BasicFrame b = null;
    private Info i = null;
    // HTML html = new HTML();

    public GetBeschreibungCallback(Info i) {
      // this.b = b;
      this.i = i;
    }

    @Override
    public void onFailure(Throwable caught) {

    }

    @Override
    public void onSuccess(Beschreibung result) {
      ShowFremdProfil.this.createProfileLabels(result.getName(), this.i.getText());
    }

  }

}
