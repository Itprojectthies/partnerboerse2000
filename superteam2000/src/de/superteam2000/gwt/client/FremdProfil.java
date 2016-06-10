package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Dies dient der Verwaltung und der Anzeige eines fremden Profils.
 * Somit alle anderen Userprofile bis auf das des derzeit angezeigten Users.
 * @param profil Hierin werden die Informationen des fremden Profils gespeichert.
 * @param rowCounter Hilfsvariable
 * @param table In dieser Tabelle werden die Profilinformationen gespeichert um strukturiert angezeigt werden zu können.
 * @param merkenButton Mit Hilfe dieses Buttons kann das fremde Profil vom momentanen User gemerkt werden.
 * @param sperrenButton Mit Hilfe dieses Buttons kann das fremde Profil vom momentanen User gesperrt werden.
 * @author 
 *
 */
public class FremdProfil extends BasicFrame {

	//Verbindung zur Datenbank herstellen
    PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
    Profil profil = null;
    int rowCounter = 7;
    FlexTable table = new FlexTable();

    /**
     * ??
     * @param p
     */
    public FremdProfil(Profil p) {
	this.profil = p;
    }

    /*
     * Buttons für merken und sperren des fremden Profils hinzufügen.
     */
    Button merkenButton = new Button("Profil merken");
    Button sperrenButton = new Button("Profil sperren");

    /**
     * Hilfsmethode
     */
    @Override
    protected String getHeadlineText() {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * Diese Methode liest die Profileigenschaften des fremden Profils aus und bringt diese zur Anzeige.
     */
    @Override
    protected void run() {

    /*
     * Buttons werden an ClickHandler uebergeben.
     */
    merkenButton.addClickHandler(new MerkenButtonClickhandler());
	sperrenButton.addClickHandler(new SperrenButtonClickhandler());

	/*
	 *  InfoObjekte fuer das Profil abfragen
	 */
	pbVerwaltung.getInfoByProfile(profil, new InfoCallback(this));

	/*
	 *  Tabelle wird mit Pflicht Profilattributen befuellt.
	 */
	table.setText(0, 0, "Vorname:");
	table.setText(0, 1, profil.getVorname());

	table.setText(1, 0, "Nachname:");
	table.setText(1, 1, profil.getNachname());

	table.setText(2, 0, "Alter:");
	table.setText(2, 1, String.valueOf(profil.getAlter()));

	table.setText(3, 0, "Geschlecht:");
	table.setText(3, 1, profil.getGeschlecht());

	table.setText(4, 0, "GrÃ¶ÃŸe:");
	table.setText(4, 1, String.valueOf(profil.getGroesse()));

	table.setText(5, 0, "Raucher");
	table.setText(5, 1, profil.getRaucher());

	table.setText(6, 0, "Haarfarbe");
	table.setText(6, 1, profil.getHaarfarbe());

	table.setText(7, 0, "Religion");
	table.setText(7, 1, profil.getReligion());

	/*
	 * Die Buttons werden dem Panel zur Anzeige hinzugefügt.
	 * Ebenso wird die Tabelle mit den Eigenschaften des fremden Profils hinzugefuegt.
	 */
	RootPanel.get("rechts").add(merkenButton);
	RootPanel.get("rechts").add(sperrenButton);
	table.setStyleName("fremdProfilAnzeigen");
	RootPanel.get("rechts").add(table);
	rowCounter++;

    }

    /**
     * ??
     * @param b
     * @author 
     *
     */
    private class InfoCallback implements AsyncCallback<ArrayList<Info>> {

	private BasicFrame b = null;

	public InfoCallback(BasicFrame b) {
	    this.b = b;
	}

	/**
	 * Wenn bei der Abfrage ein Fehler passiert ist, wird eine Fehlermeldung ausgegeben.
	 */
	@Override
	public void onFailure(Throwable caught) {
	    this.b.append("Fehler bei der Abfrage " + caught.getMessage());
	}

	/**
	 * Es wird versucht die Auswahl und die Beschreibung aus der Datenbank auszulesen.
	 */
	@Override
	public void onSuccess(ArrayList<Info> result) {
	    try {
		for (Info i : result) {
		    if (i != null) {

			pbVerwaltung.getAuswahlById(i.getEigenschaftId(), new GetAuswahlCallback(this.b, i));
			pbVerwaltung.getBeschreibungById(i.getEigenschaftId(), new GetBeschreibungCallback(this.b, i));

			/**
			 * ??
			 */
		    } else {
			this.b.append("Result ist leer");
		    }

		}
		
		/**
		 * Fehlermeldung wird ausgegeben.
		 */
	    } catch (Exception e) {
		ClientsideSettings.getLogger().severe("Fehler " + e.getMessage());
	    }
	}

    }

    /**
     * ??
     * 
     * @param b
     * @param i
     * @param html
     * @author 
     *
     */
    private class GetAuswahlCallback implements AsyncCallback<Auswahl> {

	private BasicFrame b = null;
	private Info i = null;
	HTML html = new HTML();

	/**
	 * ??
	 */
	public GetAuswahlCallback(BasicFrame b, Info i) {
	    this.b = b;
	    this.i = i;
	}

	/**
	 * Fehlermeldung wenn die Datenbankabfrage schief lief.
	 */
	@Override
	public void onFailure(Throwable caught) {
	    this.b.append("Fehler bei der Abfrage " + caught.getMessage());

	}

	/**
	 * ??
	 */
	@Override
	public void onSuccess(Auswahl result) {

	    table.setText(rowCounter, 0, result.getName());
	    table.setText(rowCounter, 1, i.getText());
	    rowCounter++;

	}

    }

    /**
     * 
     * @param b
     * @param i
     * @param html
     * @author 
     *
     */
    private class GetBeschreibungCallback implements AsyncCallback<Beschreibung> {

	private BasicFrame b = null;
	private Info i = null;
	HTML html = new HTML();

	/**
	 * 
	 */
	public GetBeschreibungCallback(BasicFrame b, Info i) {
	    this.b = b;
	    this.i = i;
	}

	/**
	 * Fehlermeldung wird ausgegeben.
	 */
	@Override
	public void onFailure(Throwable caught) {
	    this.b.append("Fehler bei der Abfrage " + caught.getMessage());

	}

	/**
	 * ??
	 */
	@Override
	public void onSuccess(Beschreibung result) {
	    table.setText(rowCounter, 0, result.getName());
	    table.setText(rowCounter, 1, i.getText());
	    rowCounter++;

	}

    }

    /**
     * Hierin finden sich alle Methoden welche benoetigt werden um ein Profil zu sperren.
     * @author 
     *
     */
    public class SperrenButtonClickhandler implements ClickHandler {
    	
    /**
     * Das Profil eines fremden Users soll gesperrt werden und auf die Sperrliste gesetzt werden.
     */
	@Override
	public void onClick(ClickEvent event) {
	    if (profil != null) {
	    	// Datenbankverbindung um aktuellen User zu finden
		ClientsideSettings.getPartnerboerseVerwaltung().createSperre(ClientsideSettings.getCurrentUser(),
			profil, new AsyncCallback<Void>() {

			/**
			 * Meldung über erfolgreiches Sperren des fremden Userprofils.
			 */
		    @Override
		    public void onSuccess(Void result) {
			Window.alert("Profil gesperrt!");
		    }

		    /**
		     * Fehlermeldung wenn der fremde User nicht gesperrt werden konnte.
		     */
		    @Override
		    public void onFailure(Throwable caught) {
			// TODO
			// Auto-generated
			// method
			// stub

		    }
		});
	    }

	}
    }

    /**
     * Hierin sind alle Methoden enthalten welche für das merken eines fremden Profils benoetigt werden.
     * @author 
     *
     */
    public class MerkenButtonClickhandler implements ClickHandler {
    
    /**
     * Das Profil eines fremden Users soll gemerkt und somit auf die Merkliste gesetzt werden.
     */
	@Override
	public void onClick(ClickEvent event) {
	    if (profil != null) {

	    // Datenbankverbindung um aktuellen User zu bestimmen
		ClientsideSettings.getPartnerboerseVerwaltung().createMerken(ClientsideSettings.getCurrentUser(),
			profil, new AsyncCallback<Void>() {

			/**
			 * Meldung dass Profil erfolgreich gemerkt wurde.
			 */
		    @Override
		    public void onSuccess(Void result) {
			Window.alert("Profil gemerkt.");

		    }

		    /**
		     * Fehlermeldung wenn es nicht geklappt hat.
		     */
		    @Override
		    public void onFailure(Throwable caught) {
			// TODO
			// Auto-generated
			// method
			// stub

		    }
		});

	    }

	}
    }

}
