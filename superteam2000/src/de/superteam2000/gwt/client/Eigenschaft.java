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

import de.superteam2000.gwt.client.gui.ProfilAttributeBoxPanel;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * Hierin werden die Eigenschaften eines Profiles verwaltet. Dies sind die zusätzlichen Infos, die ein User über sich preisgeben möchte.
 * @param rowCounter1 ??
 * @param rowCounter2 ??
 * @param checkBox1 Hiermit soll eine Auswahl für den User ermöglicht werden welche hinterher gespeichert wird.
 * @param flexTableAuswahl In dieser Tabelle wird die Eingabe des Benutzers gespeichert.
 * @param flexTableBeschreibung Diese Tabelle zeigt alle möglichen Eigenschaften an.
 * @author 
 *
 */
public class Eigenschaft extends BasicFrame {

	// Verbindung zur Datenbank herstellen und den aktuell eingeloggten Benutzer abrufen.
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

	/**
	 * Die Eigenschaften eines Profiles werden angezeigt.
	 */
	@Override
	protected void run() {
		pbVerwaltung.getInfoByProfile(currentProfil, new AsyncCallback<ArrayList<Info>>() {

			/**
			 * Die Liste der bereits gespeicherten Infos wird ausgelesen.
			 */
			@Override
			public void onSuccess(ArrayList<Info> result) {
				infoListe = result;

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

		/*
		 * Die FlexTable Auswahl wird platziert.
		 * Danach werden das Aussehen und die Groesse angegeben.
		 */
		flexTableAuswahl = new FlexTable();
		flexTableAuswahl.addStyleName("flexTable");
		flexTableAuswahl.setWidth("40em");
		flexTableAuswahl.setCellSpacing(5);
		flexTableAuswahl.setCellPadding(10);

		/*
		 * Die FlexTable Beschreibung wird platziert.
		 * Danach werden das Aussehen und die Groesse angepasst.
		 */
		flexTableBeschreibung = new FlexTable();
		flexTableBeschreibung.addStyleName("flexTable2");
		flexTableBeschreibung.setWidth("40em");
		flexTableBeschreibung.setCellSpacing(5);
		flexTableBeschreibung.setCellPadding(10);


		/**
		 * Es wird ein neues Panel angelegt für die FlexTable.
		 * Die bearbeiteten Eigenschaften können daraufhin per Button gespeichert werden.
		 */
		VerticalPanel buttonPanel = new VerticalPanel();
		buttonPanel.setStyleName("flexTable-buttonPanel");
		Button addInfo = new Button("Speichern");
		
		/**
		 * Sobald der User ein neues Info Objekt bzw. Eigenschaft hinzufuegen will, wird diese Methode aufgerufen da sie
		 * vom ClickHandler benachrichtigt wurde.
		 */
		addInfo.addClickHandler(new ClickHandler() {

			/**
			 * Diese Methode setzt die Moeglichkeit des Users um, eigene Infoeigenschaften hinzufügen zu können.
			 * @param box Hierin wird die Auswahlmoeglichkeit später eingefügt um angezeigt werden zu können.
			 * @param a Hierin werden die Auswahlmoeglichkeiten gespeichert.
			 * @param text Hierin wird die Auswahl im Textformat gespeichert.
			 */
			@Override
			public void onClick(ClickEvent event) {
				
				/**
				 * Es werden alle Auswahlen fuer den User ausgegeben.
				 */
				for (int i = 0, n = flexTableAuswahl.getRowCount(); i < n; i++) {
					CheckBox box = (CheckBox) flexTableAuswahl.getWidget(i, 0);
					if (box.getValue()) {
						Auswahl a = ((ProfilAttributeBoxPanel)flexTableAuswahl.getWidget(i, 2)).getAuswahl();
						String text = ((ProfilAttributeBoxPanel)flexTableAuswahl.getWidget(i, 2)).getSelectedItem();

						// Hinzufugefügte Info wird abgespeichert
						pbVerwaltung.createInfoFor(currentProfil, a, text, new AsyncCallback<Info>() {

							/**
							 * Die neue Infoeigenschaft wird erstellt und gespeichert.
							 */
							@Override
							public void onSuccess(Info result) {
								ClientsideSettings.getLogger().info("Info erstellt");

							}

							/**
							 *  Fehlermeldung, falls die Infoeigenschaft nicht erstellt werden konnte.
							 */
							@Override
							public void onFailure(Throwable caught) {
								ClientsideSettings.getLogger().info("Info nicht erstellt");								
							}
						});
						
						/**
						 * ??
						 */
						ClientsideSettings.getLogger().info("CheckBox is " +  
								((ProfilAttributeBoxPanel)flexTableAuswahl.getWidget(i, 2)).getSelectedItem()+ 
								" checked");
					} else {
						Auswahl a = ((ProfilAttributeBoxPanel)flexTableAuswahl.getWidget(i, 2)).getAuswahl();
						
						// Die Infoeigenschaft wird ausgelesen
						pbVerwaltung.getInfoByEigenschaftsId(a.getId(), new AsyncCallback<Info>() {

							/**
							 * Wenn die Infoeigenschaft wieder geloescht werden soll.
							 * Gespeicherte Infoeigenschaften werden ausgelesen.
							 */
							@Override
							public void onSuccess(Info result) {
								pbVerwaltung.delete(result, new AsyncCallback<Void>() {

									/**
									 * Meldung über erfolgreiche Loeschung der Eigenschaft.
									 */
									@Override
									public void onSuccess(Void result) {
										ClientsideSettings.getLogger().info("Info gelÃ¶scht");										
									}

									/**
									 * Fehlermeldung, wenn Info nicht geloescht werden konnte.
									 */
									@Override
									public void onFailure(Throwable caught) {
										ClientsideSettings.getLogger().severe("Fehler bei Info lÃ¶schen");											
									}
								});								
							}

							/**
							 * Fehlermeldung, wenn die Infoeigenschaft nicht gefunden und somit verarbeitet werden konnte.
							 */
							@Override
							public void onFailure(Throwable caught) {
								ClientsideSettings.getLogger().severe("Info nicht geholt");	

							}
						});

					}
				}

				/**
				 * Der User erhaelt nun die Beschreibung der moeglichen Auswahlen.
				 * @param box Hierin wird die Beschreibung eingefuegt.
				 * @param b ??
				 * @param text Hierin wird die Beschreibung in Textform gespeichert.
				 */
				for (int i = 0, n = flexTableBeschreibung.getRowCount(); i < n; i++) {
					CheckBox box = (CheckBox) flexTableBeschreibung.getWidget(i, 0);
					if (box.getValue()) {
						Beschreibung b = ((ProfilAttributeBoxPanel)flexTableBeschreibung.getWidget(i, 2)).getBeschreibung();
						String text = ((ProfilAttributeBoxPanel)flexTableBeschreibung.getWidget(i, 2)).getText();

						// DB-Verbindung um Info zu speichern
						pbVerwaltung.createInfoFor(currentProfil, b, text, new AsyncCallback<Info>() {

							/**
							 * Meldung, dass Info erstellt und beim richtigen User gespeichert wurde.
							 */
							@Override
							public void onSuccess(Info result) {
								ClientsideSettings.getLogger().info("Info erstellt");
							}

							/**
							 * Fehlermeldung dass Info nicht gespeichert werden konnte.
							 */
							@Override
							public void onFailure(Throwable caught) {
								ClientsideSettings.getLogger().info("Info nicht erstellt");								
							}
						});
						
						/**
						 * ??
						 */
						ClientsideSettings.getLogger().info("CheckBox is " +  
								((ProfilAttributeBoxPanel)flexTableBeschreibung.getWidget(i, 2)).getText()+ 
								" checked");

					} else {
						Beschreibung b = ((ProfilAttributeBoxPanel)flexTableBeschreibung.getWidget(i, 2)).getBeschreibung();
						pbVerwaltung.getInfoByEigenschaftsId(b.getId(), new AsyncCallback<Info>() {

							/**
							 * Wird ebenso beim loeschen von Eigenschaften benoetigt.
							 */
							@Override
							public void onSuccess(Info result) {
								pbVerwaltung.delete(result, new AsyncCallback<Void>() {

									/**
									 * Wenn Eigenschaft erfolgreich geloescht wurde.
									 */
									@Override
									public void onSuccess(Void result) {
										ClientsideSettings.getLogger().info("Info gelÃ¶scht");										
									}

									/**
									 * Fehlermeldung wenn Eigenschaft nicht geloescht werden konnte.
									 */
									@Override
									public void onFailure(Throwable caught) {
										ClientsideSettings.getLogger().severe("Fehler bei Info lÃ¶schen");											
									}
								});								
							}

							/**
							 * Die Infoeigenschaft konnte nicht gefunden werden.
							 */
							@Override
							public void onFailure(Throwable caught) {
								ClientsideSettings.getLogger().severe("Info nicht geholt");	

							}
						});

					}
				}

			}
		});

		// Die beiden FlexTables wird dem Panel hinzugefügt und ausgegeben.
		RootPanel.get("Details").add(flexTableAuswahl);
		RootPanel.get("Details").add(flexTableBeschreibung);
		RootPanel.get("Details").add(addInfo);

		// Die Auswahl des Users wird gespeichert.
		pbVerwaltung.getAllAuswahl(new AuswahlCallback());
	}
	
	/**
	 * Wenn der User eine Auswahl getroffen hat wird dies hier bearbeitet.
	 * @author 
	 *
	 */
	class AuswahlCallback implements AsyncCallback<ArrayList<Auswahl>> {

		/**
		 * Fehlermeldungen werden abgefangen.
		 */
		@Override
		public void onFailure(Throwable caught) {
		}

		/**
		 * Sobald der User etwas ausgewaehlt hat.
		 */
		@Override
		public void onSuccess(ArrayList<Auswahl> result) {
			if (result != null) {
				for (Auswahl a : result) {
					
					/*
					 * Die Zeilen der Tabelle werden mit Auswahlobjektinformationen befüllt.
					 * Ebenso die Checkbox wird befüllt mit den jeweiligen Daten.
					 */
					CheckBox checkBox1 = new CheckBox();
					ProfilAttributeBoxPanel pabp = new ProfilAttributeBoxPanel(a);
					flexTableAuswahl.setWidget(rowCounter1, 0, checkBox1);
					flexTableAuswahl.setText(rowCounter1, 1, a.getBeschreibungstext());
					flexTableAuswahl.setWidget(rowCounter1, 2, pabp);
					rowCounter1++;

					/**
					 * ??
					 * @param box
					 * @param a1
					 */
					for (int i = 0, n = flexTableAuswahl.getRowCount(); i < n; i++) {
						CheckBox box = (CheckBox) flexTableAuswahl.getWidget(i, 0);
						Auswahl a1 = ((ProfilAttributeBoxPanel)flexTableAuswahl.getWidget(i, 2)).getAuswahl();
						int auswahlId = a1.getId();
						for (Info info : infoListe) {
							if (auswahlId == info.getEigenschaftId()) {
								box.setValue(true);
								((ProfilAttributeBoxPanel)flexTableAuswahl.getWidget(i, 2)).setSelectedItem(info.getText());
							}
						}
					}

				}
				
				// ??
				pbVerwaltung.getAllBeschreibung(new BeschreibungCallback());
				
				/**
				 * ??
				 */
			} else {
				ClientsideSettings.getLogger().info("result == null");
			}
		}
	}

	/**
	 * 
	 * @author 
	 *
	 */
	class BeschreibungCallback implements AsyncCallback<ArrayList<Beschreibung>> {

		/**
		 * Meldung wenn Fehler passiert
		 */
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		/**
		 * ??
		 * @param pabp
		 */
		@Override
		public void onSuccess(ArrayList<Beschreibung> result) {
			if (result != null) {
				for (Beschreibung b : result) {
					CheckBox checkBox1 = new CheckBox();
					ProfilAttributeBoxPanel pabp = new ProfilAttributeBoxPanel(b);
					flexTableBeschreibung.setWidget(rowCounter2, 0, checkBox1);
					flexTableBeschreibung.setText(rowCounter2, 1, b.getBeschreibungstext());
					flexTableBeschreibung.setWidget(rowCounter2, 2, pabp);
					rowCounter2++;

					/**
					 * ??
					 * @param box
					 * @param b1
					 * @param beschreibungId Hierin wird die ID der Beschreibung gespeichert
					 */
					for (int i = 0, n = flexTableBeschreibung.getRowCount(); i < n; i++) {
						CheckBox box = (CheckBox) flexTableBeschreibung.getWidget(i, 0);
						Beschreibung b1 = ((ProfilAttributeBoxPanel)flexTableBeschreibung.getWidget(i, 2)).getBeschreibung();
						int beschreibungId = b1.getId();
						
						/**
						 * ??
						 */
						for (Info info : infoListe) {
							if (beschreibungId == info.getEigenschaftId()) {
								box.setValue(true);
								ClientsideSettings.getLogger().info("was ist hier los?" + 
										info.getText());
								((ProfilAttributeBoxPanel)flexTableBeschreibung.getWidget(i, 2)).setText(info.getText());
							}
						}
					}

				}

				/**
				 * ??
				 */
			} else {
				ClientsideSettings.getLogger().info("result == null");
			}
		}
	}
}


