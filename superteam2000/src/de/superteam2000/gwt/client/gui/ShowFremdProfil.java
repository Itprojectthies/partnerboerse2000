package de.superteam2000.gwt.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import de.superteam2000.gwt.client.BasicFrame;
import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Merkzettel;
import de.superteam2000.gwt.shared.bo.Profil;

public class ShowFremdProfil extends BasicFrame {
  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
  
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
    return "Profil von " + p.getVorname();
  }

  @Override
  protected String getSubHeadlineText() {
    // TODO Auto-generated method stub
    return "Wie gefällt dir " + p.getVorname() + " " + p.getNachname() + "?";
  }
  
  
  @Override
  protected void run() {
    buttonsPanel.setStyleName("pure-controls-group");
    
    HTML legend = new HTML();
    String aenhnlickeit = "Ähnlickeitsmaß " + String.valueOf(p.getAehnlichkeit()) + "%";
    legend.setHTML("<legend>"+ aenhnlickeit + "</legend>");
    
    
    
    merkenBtn.setStyleName("pure-button");
    merkenBtn.setIcon("fa fa-heart-o");
    merkenBtn.setText("Like ");
    merkenBtn.addClickHandler(new MerkenButtonClickhandler());
     
  
    
    sperrenBtn.setStyleName("pure-button");
    sperrenBtn.setIcon("fa fa-ban");
    sperrenBtn.setText("Block ");
    sperrenBtn.setToast("alert", "gesperrt");
    sperrenBtn.addClickHandler(new SperrenButtonClickhandler());
    
    buttonsPanel.add(merkenBtn);
    buttonsPanel.add(sperrenBtn);
    buttonsPanel.add(legend);
    fPanel.add(buttonsPanel);
    
    
    this.setStyleName("pure-form pure-form-aligned");
    fPanel.setStyleName("pure-controls-group content"); 
    
    pbVerwaltung.getMerkzettelForProfil(new MerkzettelCallback());
    
    
    pbVerwaltung.getAllAuswahlProfilAttribute(new GetAllAuswahlProfilAttributeCallback());
    this.add(fPanel);
  }

  public void createProfileLabels(String eigenschaft, String text) {
    FlowPanel f = new FlowPanel();
    f.setStyleName("pure-control-group");
    Label l1 = new Label(eigenschaft);
    Label l2 = new Label(text);
    l2.setStyleName("label-rechts");
    f.add(l1);
    f.add(l2);
    
    fPanel.add(f);
  }
  
  
  private final class MerkzettelCallback implements AsyncCallback<Merkzettel> {
    private ArrayList<Profil> profile;

    @Override
    public void onSuccess(Merkzettel result) {
      profile = result.getGemerkteProfile();
      for (Profil profil : profile) {
        if(p.equals(profil)) {
          merkenBtn.setIcon("fa fa-heart");
          merkenBtn.setPushed(true);
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
      Notification n1 = new Notification("Profil von "+ p.getVorname() +" gesperrt", "info");
     
      if (p != null) {
        ClientsideSettings.getPartnerboerseVerwaltung()
            .createSperre(ClientsideSettings.getCurrentUser(), p, new AsyncCallback<Void>() {

              @Override
              public void onSuccess(Void result) {
                sperrenBtn.setEnabled(false);
                merkenBtn.setIcon("fa fa-heart-o");
                merkenBtn.setEnabled(false);
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
     
      
      if (!merkenBtn.isPushed()) {

        ClientsideSettings.getPartnerboerseVerwaltung()
            .createMerken(ClientsideSettings.getCurrentUser(), p, new AsyncCallback<Void>() {

              @Override
              public void onSuccess(Void result) {
                Notification n1 = new Notification("Profil von "+ p.getVorname() +" gemerkt", "info");
                  merkenBtn.setIcon("fa fa-heart");
                  merkenBtn.setPushed(true);
          }

              @Override
              public void onFailure(Throwable caught) {


          }
            });

      } else {
        pbVerwaltung.deleteMerken(ClientsideSettings.getCurrentUser(), p, new AsyncCallback<Void>() {
          
          @Override
          public void onSuccess(Void result) {
            Notification n1 = new Notification("Profil von "+ p.getVorname() +" entmerkt", "info");
            merkenBtn.setIcon("fa fa-heart-o");
            merkenBtn.setPushed(false);
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
    public void onFailure(Throwable caught) {
      // TODO Auto-generated method stub

    }
  }

  private class InfoCallback implements AsyncCallback<ArrayList<Info>> {

//    private BasicFrame b = null;
//
//    public InfoCallback(BasicFrame b) {
//        this.b = b;
//    }

    @Override
    public void onFailure(Throwable caught) {
    }

    @Override
    public void onSuccess(ArrayList<Info> result) {
        for (Info i : result) {
            if (i != null) {
            // table.setText(rowCounter, 1, i.getText());

            pbVerwaltung.getAuswahlById(i.getEigenschaftId(), new GetAuswahlCallback(i));
            pbVerwaltung.getBeschreibungById(i.getEigenschaftId(), new GetBeschreibungCallback(i));
            
            }
           
        }

        
        
    }
  }

    private class GetAuswahlCallback implements AsyncCallback<Auswahl> {

//      private BasicFrame b = null;
      private Info i = null;
//      HTML html = new HTML();

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

//      private BasicFrame b = null;
      private Info i = null;
//      HTML html = new HTML();

      public GetBeschreibungCallback(Info i) {
//          this.b = b;
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
