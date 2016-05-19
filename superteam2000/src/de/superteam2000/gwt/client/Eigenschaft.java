package de.superteam2000.gwt.client;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Auswahl;
import de.superteam2000.gwt.shared.bo.Beschreibung;
import de.superteam2000.gwt.shared.bo.Info;
import de.superteam2000.gwt.shared.bo.Profil;

public class Eigenschaft extends BasicFrame {

	@Override
	protected String getHeadlineText() {
		// TODO Auto-generated method stub
		return "Eigenschaften";
	}

	@Override
	protected void run() {
		PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
		pbVerwaltung.getAllAuswahl(new AuswahlCallback(this));
		pbVerwaltung.getAllBeschreibung(new BeschreibungCallback(this));
	}

	class BeschreibungCallback implements AsyncCallback<ArrayList<Beschreibung>> {
		private BasicFrame showcase = null;
		protected String a;

		public BeschreibungCallback(BasicFrame b) {
			this.showcase = b;
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(ArrayList<Beschreibung> result) {
			final PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
			final Profil p = ClientsideSettings.getCurrentUser();
			if (result != null) {
				for (final Beschreibung b : result) {
					if (result != null) {
						// Kundennummer und Name ausgeben
						this.showcase
								.append("Auswahl #" + b.getId() + ": " + b.getName() + ", " + b.getBeschreibungstext());

						final TextBox tb = new TextBox();

						pbVerwaltung.getInfoByEigenschaftsId(b.getId(), new AsyncCallback<Info>() {
							@Override
							public void onSuccess(Info result) {
								tb.setText(result.getText());

							}

							@Override
							public void onFailure(Throwable caught) {
								ClientsideSettings.getLogger().severe("fehler");
							}
						});
						tb.setText(a);
						this.showcase.add(tb);

						ArrayList<String> al = new ArrayList<>();

						Button addBtn = new Button("Speichern", new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {

								ClientsideSettings.getLogger().severe("Speichern gedrückt");
								pbVerwaltung.createInfoFor(p, b, tb.getText(), new AsyncCallback<Info>() {

									@Override
									public void onSuccess(Info result) {
										ClientsideSettings.getLogger().severe("Info erstellt");
									}

									@Override
									public void onFailure(Throwable caught) {
										ClientsideSettings.getLogger().severe("Info nicht erstellt");
									}
								});
							}
						});
						this.showcase.add(addBtn);
					}
				}

				if (result.size() == 1)
					this.showcase.append("1 Element erhalten");
				else
					this.showcase.append(result.size() + " Elemente erhalten");

			} else {
				ClientsideSettings.getLogger().info("result == null");
			}
		}
	}
}

class AuswahlCallback implements AsyncCallback<ArrayList<Auswahl>> {
	private BasicFrame showcase = null;

	public AuswahlCallback(BasicFrame b) {
		this.showcase = b;
	}

	@Override
	public void onFailure(Throwable caught) {
		this.showcase.append("Fehler bei der Abfrage " + caught.getMessage());
	}

	@Override
	public void onSuccess(ArrayList<Auswahl> result) {
		final PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
		final Profil p = ClientsideSettings.getCurrentUser();
		if (result != null) {
			for (final Auswahl a : result) {
				if (result != null) {
					// Kundennummer und Name ausgeben
					this.showcase
							.append("Auswahl #" + a.getId() + ": " + a.getName() + ", " + a.getBeschreibungstext());

					final ListBox lb = new ListBox();
					ArrayList<String> al = new ArrayList<>();
					al = a.getAlternativen();
					for (String string : al) {
						lb.addItem(string);
					}
					this.showcase.add(lb);

					Button addBtn = new Button("Speichern", new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {

							ClientsideSettings.getLogger().severe("Speichern gedrückt");
							pbVerwaltung.createInfoFor(p, a, lb.getSelectedValue(), new AsyncCallback<Info>() {

								@Override
								public void onSuccess(Info result) {
									ClientsideSettings.getLogger().severe("Info erstellt");
								}

								@Override
								public void onFailure(Throwable caught) {
									ClientsideSettings.getLogger().severe("Info nicht erstellt");
								}
							});
						}
					});
					this.showcase.add(addBtn);
				}
			}

			if (result.size() == 1)
				this.showcase.append("1 Element erhalten");
			else
				this.showcase.append(result.size() + " Elemente erhalten");

		} else {
			ClientsideSettings.getLogger().info("result == null");
		}
	}

}
