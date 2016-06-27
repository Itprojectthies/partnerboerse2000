package de.superteam2000.gwt.client.gui;

import java.util.ArrayList;

import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.client.ShowFremdProfil;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Profil;

public class DataGridProfiles {
  // pb Verwaltung über ClientsideSettings holen
  PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();

  Profil profil = ClientsideSettings.getCurrentUser();

  FlowPanel fPanel = new FlowPanel();

  private ArrayList<Profil> profilListe;

  public DataGridProfiles(ArrayList<Profil> list) {
    this.profilListe = list;
  }

  private Profil selected = null;



  public ArrayList<Profil> getProfilListe() {
    return this.profilListe;
  }

  public void setProfilListe(ArrayList<Profil> profilListe) {
    this.profilListe = profilListe;
  }

  /**
   * @return the table
   */
  public DataGrid<Profil> getTable() {
    return this.table;
  }

  /**
   * @param table the table to set
   */
  public void setTable(DataGrid<Profil> table) {
    this.table = table;
  }

  DataGrid<Profil> table = new DataGrid<Profil>();

  public FlowPanel start() {
    this.fPanel.setStyleName("content");

    this.table.setStyleName("pure-table pure-table-horizontal");
    TextColumn<Profil> vorname = new TextColumn<Profil>() {
      @Override
      public String getValue(Profil p) {
        return p.getVorname();
      }
    };
    this.table.addColumn(vorname, "Vorname");

    TextColumn<Profil> nachname = new TextColumn<Profil>() {
      @Override
      public String getValue(Profil p) {
        return p.getNachname();
      }
    };
    this.table.addColumn(nachname, "Nachname");

    TextColumn<Profil> alter = new TextColumn<Profil>() {
      @Override
      public String getValue(Profil p) {
        return String.valueOf(p.getAlter());
      }
    };
    this.table.addColumn(alter, "Alter");

    TextColumn<Profil> aehnlichkeit = new TextColumn<Profil>() {
      @Override
      public String getValue(Profil p) {
        ;

        return String.valueOf(p.getAehnlichkeit()) + "%";
      }
    };
    aehnlichkeit.setCellStyleNames("test");
    this.table.addColumn(aehnlichkeit, "Ähnlichkeit");

    this.table.setRowCount(this.profilListe.size(), true);
    this.table.setRowData(0, this.profilListe);
    this.table.setWidth("80%");

    LayoutPanel panel = new LayoutPanel();
    panel.setSize("50em", "40em");
    panel.add(this.table);
    // panel.setStyleName("pure-controls");
    this.fPanel.add(panel);
    return this.fPanel;
  }

  public void addClickFremdProfil() {
    // Add a selection model to handle user selection.
    final SingleSelectionModel<Profil> selectionModel = new SingleSelectionModel<Profil>();
    this.table.setSelectionModel(selectionModel);
    selectionModel.addSelectionChangeHandler(new Handler() {

      @Override
      public void onSelectionChange(SelectionChangeEvent event) {
        DataGridProfiles.this.selected = selectionModel.getSelectedObject();
        // History.newItem(selected.getNachname());
        ClientsideSettings.getLogger().info(DataGridProfiles.this.selected.getNachname());
        ShowFremdProfil fp = new ShowFremdProfil(DataGridProfiles.this.selected);
        RootPanel.get("main").clear();

        // Profil als besucht setzen
        DataGridProfiles.this.pbVerwaltung.setVisited(ClientsideSettings.getCurrentUser(),
            DataGridProfiles.this.selected, new AsyncCallback<Void>() {

              @Override
              public void onSuccess(Void result) {
                ClientsideSettings.getLogger().info("User wurde als besucht markiert!");

              }

              @Override
              public void onFailure(Throwable caught) {

            }
            });
        RootPanel.get("main").add(fp);
      }
    });
  }

}