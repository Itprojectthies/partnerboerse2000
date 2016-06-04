package de.superteam2000.gwt.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.superteam2000.gwt.client.gui.ProfilAttributeBoxPanel;
import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Profil;

public class Eigenschaft extends BasicFrame {

	PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
	Profil p = ClientsideSettings.getCurrentUser();
	
	int rowCounter = 0;
	final CheckBox checkBox1 = new CheckBox();
	FlexTable flexTable = null;
	ArrayList<Info> infoListe;
	@Override
	protected String getHeadlineText() {
		// TODO Auto-generated method stub
		return "Eigenschaften";
	}

	@Override
	protected void run() {
		pbVerwaltung.getInfoByProfile(p, new AsyncCallback<ArrayList<Info>>() {
			
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
		// Create a Flex Table
		flexTable = new FlexTable();
//		FlexCellFormatter cellFormatter = flexTable.getFlexCellFormatter();
		flexTable.addStyleName("flexTable");
		flexTable.setWidth("32em");
		flexTable.setCellSpacing(5);
		flexTable.setCellPadding(3);
		
		
		// Add some text
//		cellFormatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
//		cellFormatter.setColSpan(0, 0, 3);

		VerticalPanel buttonPanel = new VerticalPanel();
		buttonPanel.setStyleName("flexTable-buttonPanel");
//		cellFormatter.setVerticalAlignment(0, 2, HasVerticalAlignment.ALIGN_MIDDLE);
		Button addInfo = new Button("Speichern");
		addInfo.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for (int i = 0, n = flexTable.getRowCount(); i < n; i++) {
	                CheckBox box = (CheckBox) flexTable.getWidget(i, 0);
	                if (box.getValue()) {
	                	Auswahl a = ((ProfilAttributeBoxPanel)flexTable.getWidget(i, 2)).getAuswahl();
	                	String text = ((ProfilAttributeBoxPanel)flexTable.getWidget(i, 2)).getSelectedItem();
	                	
	                	pbVerwaltung.createInfoFor(p, a, text, new AsyncCallback<Info>() {
							
							@Override
							public void onSuccess(Info result) {
								ClientsideSettings.getLogger().info("Info erstellt");
								
							}
							
							@Override
							public void onFailure(Throwable caught) {
								ClientsideSettings.getLogger().info("Info nicht erstellt");								
							}
						});
	                	 ClientsideSettings.getLogger().info("CheckBox is " +  
	 							((ProfilAttributeBoxPanel)flexTable.getWidget(i, 2)).getSelectedItem()+ 
	 							" checked");
//	                	 
	                } else {
	                	Auswahl a = ((ProfilAttributeBoxPanel)flexTable.getWidget(i, 2)).getAuswahl();
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
				
			}
		});
		
		RootPanel.get("Details").add(flexTable);
		RootPanel.get("Details").add(addInfo);

		pbVerwaltung.getAllAuswahl(new AuswahlCallback());
		pbVerwaltung.getAllBeschreibung(new BeschreibungCallback());
		
	}
	class AuswahlCallback implements AsyncCallback<ArrayList<Auswahl>> {

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(ArrayList<Auswahl> result) {
			if (result != null) {
				for (final Auswahl a : result) {
					CheckBox checkBox1 = new CheckBox();
					ProfilAttributeBoxPanel pabp = new ProfilAttributeBoxPanel(a);
					flexTable.setWidget(rowCounter, 0, checkBox1);
					flexTable.setText(rowCounter, 1, a.getBeschreibungstext());
					flexTable.setWidget(rowCounter, 2, pabp);
					rowCounter++;
					
					
					
					//users is the flextable object.
					ClickHandler userCheck = new ClickHandler() {
					        public void onClick(ClickEvent event) {
//					        	 Window.alert("CheckBox is " +  
//				 							((ProfilAttributeBoxPanel)flexTable.getWidget(0, 2)).getSelectedItem()+ 
//				 							" checked");
					            CheckBox src = (CheckBox) event.getSource();
					            for (int i = 0, n = flexTable.getRowCount(); i < n; i++) {
					                CheckBox box = (CheckBox) flexTable.getWidget(i, 0);

					                if (box.equals(src)) {
//					                	 Window.alert("CheckBox is " +  
//					 							((ProfilAttributeBoxPanel)flexTable.getWidget(i, 2)).getSelectedItem()+ 
//					 							" checked");
					                	 
//					                	 if (box.getValue()) {
//					                		 box.setValue(false, false);
//					                	 } else {
//					                		 
//					                		 box.setValue(true, false);
//					                	 }
					                }
					            }

					        }
					    };
					    
					    checkBox1.addClickHandler(userCheck);
					    for (int i = 0, n = flexTable.getRowCount(); i < n; i++) {
				            CheckBox box = (CheckBox) flexTable.getWidget(i, 0);
				            Auswahl a1 = ((ProfilAttributeBoxPanel)flexTable.getWidget(i, 2)).getAuswahl();
				            int auswahlId = a1.getId();
				            for (Info info : infoListe) {
								if (auswahlId == info.getEigenschaftId()) {
									box.setValue(true);
									((ProfilAttributeBoxPanel)flexTable.getWidget(i, 2)).setSelectedItem(info.getText());
								}
							}
				        }
					
				}
				
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
			final Profil p = ClientsideSettings.getCurrentUser();
			if (result != null) {
				for (final Beschreibung b : result) {

				}

			} else {
				ClientsideSettings.getLogger().info("result == null");
			}
		}
	}
}


