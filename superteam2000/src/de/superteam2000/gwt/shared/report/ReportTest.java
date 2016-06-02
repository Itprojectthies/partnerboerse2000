package de.superteam2000.gwt.shared.report;

import java.util.Vector;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;



import de.superteam2000.gwt.shared.ReportGeneratorAsync;
import de.superteam2000.gwt.client.ShowProfil;
import de.superteam2000.gwt.shared.ReportGenerator;
import de.superteam2000.gwt.shared.bo.Profil;
import de.superteam2000.gwt.shared.bo.Eigenschaft;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Info;


public class ReportTest {

	VerticalPanel mainPanel = new VerticalPanel();
	ListBox raumListBox = new ListBox();

	
	ReportGeneratorAsync partnerboerse = GWT
			.create(ReportGenerator.class);

	/**
	 * Die Button und die Listbox wird hier in die eine FlexTable
	 * reingeschrieben. Wenn der Button betätigt wird dann läuft die onClick
	 * Methode und löscht den vorherigen mainPanel. Danach wird die Methode
	 * zeigeProfil() durchgeführt und die Werte vom Listbox übergeben.
	 * 
	 * @return mainPanel
	 */
	public Widget reportProfil() {
		FlexTable navigationProfilReport = new FlexTable();
		navigationProfilReport.setText(0, 0, "Profil:");
		Button d = new Button("Report");

		return d;
		}
		
		
		VerticalPanel setProfilListBox(){
		HTMLTable navigationProfilReport = null;
		Widget profilListBox = null;
		navigationProfilReport.setWidget(0, 0, profilListBox);
		//navigationProfilReport.setWidget(0, 1, );
		mainPanel.clear();
		mainPanel.add(navigationProfilReport);
		return mainPanel;
	}

	/**
	 * Um den Profilname in den Report zu schreiben, benötigt diese
	 * Methode den Parameter, die sie erst von der Methode
	 * reportProfil() übergeben wird.
	 * 
	 * @see reportProfil() dann wird ein FlexTable rt instanziiert um die
	 *      Tabelle für den Profilname zu gestalten. In der onSuccess()
	 *      Methode wird das Profil rausgelesen und in die nullte Zeile, erste
	 *      Spalte reingeschrieben.
	 * @param profilID
	 */
	public void zeigeProfil(final String id) {

		final FlexTable profilTabelle = new FlexTable();
		final int profilID;
		profilID = Integer.parseInt(id);
		mainPanel.clear();
		profilTabelle.setText(0, 0, "Profil: ");

	}

	/**
	 * Mit dieser Methode wird das gesamte Profil eines Benutzers ausgegeben.
	 * Dazu braucht man den Parameter bez. Es wird dann der Grundgerüst eines
	 * Profils gebaut. Anschließend wird die reportLVbyRaum aufgerufen und
	 * den Parameter übergeben. Zum Schluss wird alles in den mainPanel
	 * hinzugefügt.
	 * 
	 * @param 
	 * 
	 */
	public void zeigeTabelle(int profilID) {
		final FlexTable profilReportTabelle = new FlexTable();
		
		//HTML class hinzufügen, damit die Tabelle das Design annimmt.
		DOM.setElementAttribute(profilReportTabelle.getElement(), "geschlecht", "groesse");
		
		
		RootPanel.get("starter").clear();
		//
		profilReportTabelle.setText(0, 0, "Vorname");
		profilReportTabelle.setText(0, 1, "Nachname");
		
		
		profilReportTabelle.setText(1, 0, "Geschlecht");
		profilReportTabelle.setText(2, 0, "Größe");
		profilReportTabelle.setText(3, 0, "Alter");
		profilReportTabelle.setText(4, 0, "Haarfarbe");
		profilReportTabelle.setText(5, 0, "Raucher");
		profilReportTabelle.setText(6, 0, "Religion");
		

						

		Button refresh = new Button("Zurück");
		refresh.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				Window.Location.reload();
			}
		});
		// mainPanel.add(rt);
		mainPanel.add(profilReportTabelle);
		mainPanel.add(refresh);
		RootPanel.get().add(mainPanel);
	}

			}
	
