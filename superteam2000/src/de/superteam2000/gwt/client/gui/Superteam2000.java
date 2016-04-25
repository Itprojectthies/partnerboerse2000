package de.superteam2000.gwt.client.gui;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.superteam2000.gwt.client.ClientsideSettings;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;


/**
 * Entry-Point-Klasse des Projekts <b>BankProjekt</b>.
 */
public class Superteam2000 implements EntryPoint {
	


	PartnerboerseAdministrationAsync bpVerwaltung = null;

	/**
	 * Da diese Klasse die Implementierung des Interface <code>EntryPoint</code>
	 * zusichert, benötigen wir eine Methode
	 * <code>public void onModuleLoad()</code>. Diese ist das GWT-Pendant der
	 * <code>main()</code>-Methode normaler Java-Applikationen.
	 */
	@Override
	public void onModuleLoad() {
		
		/*
		 * Zunächst weisen wir der BankAdministration eine Bank-Instanz zu, die
		 * das Kreditinstitut repräsentieren soll, für das diese Applikation
		 * arbeitet.
		 */
		if (bpVerwaltung == null) {
			bpVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
		}


		/*
		 * Die Bankanwendung besteht aus einem Navigationsteil mit Baumstruktur
		 * und einem Datenteil mit Formularen für den ausgewählten Kunden und das
		 * ausgewählte Konto.
		 */
		
		CustomerForm cf = new CustomerForm();
		

				
		/*
		 * Die Panels und der CellTree werden erzeugt und angeordnet und in das RootPanel eingefügt.
		 */
		VerticalPanel detailsPanel = new VerticalPanel();
		detailsPanel.add(cf);

		RootPanel.get("Details").add(detailsPanel);
	}

	/**
	 * Diese Nested Class wird als Callback für das Setzen des Bank-Objekts bei
	 * der BankAdministration und bei dem ReportGenerator benötigt.
	 * 
	 * @author thies
	 * @version 1.0
	 */
	class SetBankCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			/*
			 * Wenn ein Fehler auftritt, dann geben wir eine kurze Log Message
			 * aus.
			 */
			ClientsideSettings.getLogger().severe(
					"Setzen der Bank fehlgeschlagen!");
		}

		@Override
		public void onSuccess(Void result) {
			/*
			 * Wir erwarten diesen Ausgang, wollen aber keine Notifikation
			 * ausgeben.
			 */
		}

	}

}
