package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.superteam2000.gwt.client.gui.BoxPanel;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Profil;

public class Eigenschaft extends BasicFrame {

  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
  Profil currentProfil = ClientsideSettings.getCurrentUser();

  int rowCounter1 = 0;
  int rowCounter2 = 0;
  CheckBox checkBox1 = new CheckBox();
  FlexTable flexTableAuswahl = null;
  FlexTable flexTableBeschreibung = null;
  ArrayList<Info> infoListe;

  @Override
  protected String getHeadlineText() {
    return "Eigenschaften";
  }

  @Override
  protected void run() {
    pbVerwaltung.getInfoByProfile(currentProfil, new AsyncCallback<ArrayList<Info>>() {

      @Override
      public void onSuccess(ArrayList<Info> result) {
        infoListe = result;

      }

      @Override
      public void onFailure(Throwable caught) {
        // TODO Auto-generated method stub

      }
    });

    // Create a Flex Table
    flexTableAuswahl = new FlexTable();
    // FlexCellFormatter cellFormatter = flexTable.getFlexCellFormatter();
    flexTableAuswahl.addStyleName("flexTable");
    flexTableAuswahl.setWidth("40em");
    flexTableAuswahl.setCellSpacing(5);
    flexTableAuswahl.setCellPadding(10);

    // Create a Flex Table
    flexTableBeschreibung = new FlexTable();
    // FlexCellFormatter cellFormatter = flexTable.getFlexCellFormatter();
    flexTableBeschreibung.addStyleName("flexTable2");
    flexTableBeschreibung.setWidth("40em");
    flexTableBeschreibung.setCellSpacing(5);
    flexTableBeschreibung.setCellPadding(10);


    // Add some text
    // cellFormatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
    // cellFormatter.setColSpan(0, 0, 3);

    VerticalPanel buttonPanel = new VerticalPanel();
    buttonPanel.setStyleName("flexTable-buttonPanel");
    // cellFormatter.setVerticalAlignment(0, 2, HasVerticalAlignment.ALIGN_MIDDLE);
    Button addInfo = new Button("Speichern");
    addInfo.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        for (int i = 0, n = flexTableAuswahl.getRowCount(); i < n; i++) {
          CheckBox box = (CheckBox) flexTableAuswahl.getWidget(i, 0);
          if (box.getValue()) {
            Auswahl a = ((BoxPanel) flexTableAuswahl.getWidget(i, 2)).getAuswahl();
            String text =
                ((BoxPanel) flexTableAuswahl.getWidget(i, 2)).getSelectedItem();

            pbVerwaltung.createInfoFor(currentProfil, a, text, new AsyncCallback<Info>() {

              @Override
              public void onSuccess(Info result) {
                ClientsideSettings.getLogger().info("Info erstellt");

              }

              @Override
              public void onFailure(Throwable caught) {
                ClientsideSettings.getLogger().info("Info nicht erstellt");
              }
            });
            ClientsideSettings.getLogger()
                .info("CheckBox is "
                    + ((BoxPanel) flexTableAuswahl.getWidget(i, 2)).getSelectedItem()
                    + " checked");
          } else {
            Auswahl a = ((BoxPanel) flexTableAuswahl.getWidget(i, 2)).getAuswahl();
            pbVerwaltung.getInfoByEigenschaftsId(a.getId(), new AsyncCallback<Info>() {

              @Override
              public void onSuccess(Info result) {
                pbVerwaltung.delete(result, new AsyncCallback<Void>() {

                  @Override
                  public void onSuccess(Void result) {
                    ClientsideSettings.getLogger().info("Info gelöscht");
                  }

                  @Override
                  public void onFailure(Throwable caught) {
                    ClientsideSettings.getLogger().severe("Fehler bei Info löschen");
                  }
                });
              }

              @Override
              public void onFailure(Throwable caught) {
                ClientsideSettings.getLogger().severe("Info nicht geholt");

              }
            });

          }
        }

        for (int i = 0, n = flexTableBeschreibung.getRowCount(); i < n; i++) {
          CheckBox box = (CheckBox) flexTableBeschreibung.getWidget(i, 0);
          if (box.getValue()) {
            Beschreibung b =
                ((BoxPanel) flexTableBeschreibung.getWidget(i, 2)).getBeschreibung();
            String text =
                ((BoxPanel) flexTableBeschreibung.getWidget(i, 2)).getText();

            pbVerwaltung.createInfoFor(currentProfil, b, text, new AsyncCallback<Info>() {

              @Override
              public void onSuccess(Info result) {
                ClientsideSettings.getLogger().info("Info erstellt");
              }

              @Override
              public void onFailure(Throwable caught) {
                ClientsideSettings.getLogger().info("Info nicht erstellt");
              }
            });
            ClientsideSettings.getLogger()
                .info("CheckBox is "
                    + ((BoxPanel) flexTableBeschreibung.getWidget(i, 2)).getText()
                    + " checked");

          } else {
            Beschreibung b =
                ((BoxPanel) flexTableBeschreibung.getWidget(i, 2)).getBeschreibung();
            pbVerwaltung.getInfoByEigenschaftsId(b.getId(), new AsyncCallback<Info>() {

              @Override
              public void onSuccess(Info result) {
                pbVerwaltung.delete(result, new AsyncCallback<Void>() {

                  @Override
                  public void onSuccess(Void result) {
                    ClientsideSettings.getLogger().info("Info gelöscht");
                  }

                  @Override
                  public void onFailure(Throwable caught) {
                    ClientsideSettings.getLogger().severe("Fehler bei Info löschen");
                  }
                });
              }

              @Override
              public void onFailure(Throwable caught) {
                ClientsideSettings.getLogger().severe("Info nicht geholt");

              }
            });

          }
        }

      }
    });

    RootPanel.get("main").add(flexTableAuswahl);
    RootPanel.get("main").add(flexTableBeschreibung);
    RootPanel.get("main").add(addInfo);

    pbVerwaltung.getAllAuswahl(new AuswahlCallback());


  }

  class AuswahlCallback implements AsyncCallback<ArrayList<Auswahl>> {

    @Override
    public void onFailure(Throwable caught) {}

    @Override
    public void onSuccess(ArrayList<Auswahl> result) {
      if (result != null) {
        for (Auswahl a : result) {
          // Befülle die Zeilen der Tabelle mit Auswahlobjektinformationen und der Checkbox
          CheckBox checkBox1 = new CheckBox();
          BoxPanel pabp = new BoxPanel(a);
          flexTableAuswahl.setWidget(rowCounter1, 0, checkBox1);
          flexTableAuswahl.setText(rowCounter1, 1, a.getBeschreibungstext());
          flexTableAuswahl.setWidget(rowCounter1, 2, pabp);
          rowCounter1++;



          for (int i = 0, n = flexTableAuswahl.getRowCount(); i < n; i++) {
            CheckBox box = (CheckBox) flexTableAuswahl.getWidget(i, 0);
            Auswahl a1 = ((BoxPanel) flexTableAuswahl.getWidget(i, 2)).getAuswahl();
            int auswahlId = a1.getId();
            for (Info info : infoListe) {
              if (auswahlId == info.getEigenschaftId()) {
                box.setValue(true);
                ((BoxPanel) flexTableAuswahl.getWidget(i, 2))
                    .setSelectedItem(info.getText());
              }
            }
          }

        }
        pbVerwaltung.getAllBeschreibung(new BeschreibungCallback());
      } else {
        ClientsideSettings.getLogger().info("result == null");
      }
    }
  }

  class BeschreibungCallback implements AsyncCallback<ArrayList<Beschreibung>> {

    @Override
    public void onFailure(Throwable caught) {
      // TODO Auto-generated method stub

    }

    @Override
    public void onSuccess(ArrayList<Beschreibung> result) {
      if (result != null) {
        for (Beschreibung b : result) {
          CheckBox checkBox1 = new CheckBox();
          BoxPanel pabp = new BoxPanel(b);
          flexTableBeschreibung.setWidget(rowCounter2, 0, checkBox1);
          flexTableBeschreibung.setText(rowCounter2, 1, b.getBeschreibungstext());
          flexTableBeschreibung.setWidget(rowCounter2, 2, pabp);
          rowCounter2++;



          for (int i = 0, n = flexTableBeschreibung.getRowCount(); i < n; i++) {
            CheckBox box = (CheckBox) flexTableBeschreibung.getWidget(i, 0);
            Beschreibung b1 =
                ((BoxPanel) flexTableBeschreibung.getWidget(i, 2)).getBeschreibung();
            int beschreibungId = b1.getId();
            for (Info info : infoListe) {
              if (beschreibungId == info.getEigenschaftId()) {
                box.setValue(true);
                ClientsideSettings.getLogger().info("was ist hier los?" + info.getText());
                ((BoxPanel) flexTableBeschreibung.getWidget(i, 2))
                    .setText(info.getText());
              }
            }
          }

        }

      } else {
        ClientsideSettings.getLogger().info("result == null");
      }
    }
  }

  @Override
  protected String getSubHeadlineText() {
    // TODO Auto-generated method stub
    return "Was passt zu Dir passt?";
  }
}


