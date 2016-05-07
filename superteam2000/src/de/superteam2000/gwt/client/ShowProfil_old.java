package de.superteam2000.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.superteam2000.gwt.shared.PartnerboerseAdministrationAsync;
import de.superteam2000.gwt.shared.bo.Profil;

/**
 * <p>
 * Ein weiterer Showcase. Dieser sucht nach allen Kunden, deren Nachname 'Kohl'
 * ist und gibt diese aus.
 * </p>
 * <p>
 * Ein detaillierter beschriebener Showcase findet sich in
 * {@link CreateAccountDemo}.
 * </p>
 * 
 * @see CreateAccountDemo
 * @author thies, volz
 * @version 1.0
 * 
 */
public class ShowProfil_old extends BasicFrame {
	
	
	/**
	 * Jeder Showcase besitzt eine einleitende Überschrift, die durch diese
	 * Methode zu erstellen ist.
	 * 
	 * @see BasicFrame#getHeadlineText()
	 */
	@Override
	protected String getHeadlineText() {
		return "Profil:";
	}

	/**
	 * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie
	 * ist eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
	 */
	@Override
	protected void run() {
		
		PartnerboerseAdministrationAsync pbVerwaltung = ClientsideSettings.getPartnerboerseVerwaltung();
		Profil profil = ClientsideSettings.getCurrentUser();
		pbVerwaltung.getProfilById(profil.getId() , new KundenAusgebenCallback(this));
	}

	/**
	 * <p>
	 * Wir nutzen eine Nested Class, um die zurückerhaltenen Objekte weiter zu
	 * bearbeiten.
	 * </p>
	 * <p>
	 * <b>Amerkungen:</b> Eine Nested Class besitzt den Vorteil, die Lokalität
	 * des Gesamtsystems zu fördern, da der Klassenname (hier: "UseCustomer")
	 * außerhalb von DeleteAccountDemo nicht "verbraucht" wird. Doch Vorsicht!
	 * Wenn eine Klasse mehrfach, also gewissermaßen an mehreren Stellen im
	 * Programm, nutzbar ist, sollte man überlegen, ob man eine solche Klasse
	 * als normale - also separate - Klasse realisiert bzw. anordnet.
	 * </p>
	 * <p>
	 * Weitere Dokumentation siehe <code>CreateAccountDemo.UseCustomer</code>.
	 * </p>
	 * 
	 * @see CreateAccountDemo.UseCustomer
	 */
	class KundenAusgebenCallback implements AsyncCallback<Profil> {
		private BasicFrame showcase = null;

		public KundenAusgebenCallback(BasicFrame c) {
			this.showcase = c;
		}

		@Override
		public void onFailure(Throwable caught) {
			this.showcase.append("Fehler bei der Abfrage " + caught.getMessage());
		}

		@Override
		public void onSuccess(Profil p) {
			
			if (p != null) {
		
			this.showcase.append("Profil ID:" + p.getId() + ": " + p.getNachname() + ", " + p.getVorname());
			} else {
				this.showcase.append("Profil nicht vorhanden");
				ClientsideSettings.getLogger().severe("Profil-Objekt ist nicht vorhanden");
			}
		}

	}
}
