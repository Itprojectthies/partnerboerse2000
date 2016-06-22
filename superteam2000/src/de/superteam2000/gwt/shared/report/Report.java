package de.superteam2000.gwt.shared.report;

import java.io.Serializable;
import java.util.Date;

import de.superteam2000.gwt.shared.bo.Profil;

/**
 * <p>
 * Basisklasse aller Reports. Reports sind als <code>Serializable</code>
 * deklariert, damit sie von dem Server an den Client gesendet werden können.
 * Der Zugriff auf Reports erfolgt also nach deren Bereitstellung lokal auf dem
 * Client.
 * </p>
 * <p>
 * Ein Report besitzt eine Reihe von Standardelementen. Sie werden mittels
 * Attributen modelliert und dort dokumentiert.
 * </p>
 * 
 * @see Report
 * @author Thies
 */
public abstract class Report implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Profil profil = null;

	/**
	 * Ein kleines Impressum, das eine Art Briefkopf darstellt. Jedes
	 * Unternehmen einige Daten wie Firmenname, Adresse, Logo, etc. auf
	 * Geschäftsdokumenten ab. Dies gilt auch für die hier realisierten Reports.
	 */
	private Paragraph name = null;
	
	private CompositeParagraph attribute = null;
	private CompositeParagraph attributeBez = null;
	
	private Paragraph imprint3 = null;

	/**
	 * Kopfdaten des Berichts.
	 */
	private Paragraph aehnlichkeit = null;

	/**
	 * Jeder Bericht kann einen individuellen Titel besitzen.
	 */
	private String title = "Report";
	private String subtitle = "";
	/**
	 * Datum der Erstellung des Berichts.
	 */
	private Date created = new Date();

	/**
	 * Auslesen des Impressums.
	 * 
	 * @return Text des Impressums
	 */
	public Paragraph getName() {
		return this.name;
	}
	
	 public void setName(Paragraph name) {
	   this.name = name;
	 }
	/**
	 * Setzen des Impressums.
	 * 
	 * @param attribute
	 *            Text des Impressums
	 */
	public void setProfilAttribute(CompositeParagraph attribute) {
		this.attribute = attribute;
	}
	
	public CompositeParagraph getProfilAttribute() {
	  return attribute;
	}

	public void setProfilAttributeBez(CompositeParagraph attributeBez) {
      this.attributeBez = attributeBez;
  }
  
  public CompositeParagraph getProfilAttributeBez() {
    return attributeBez;
  }
	/**
	 * Auslesen der Kopfdaten.
	 * 
	 * @return Text der Kopfdaten.
	 */
	public Paragraph getAehnlichkeit() {
		return this.aehnlichkeit;
	}

	/**
	 * Setzen der Kopfdaten.
	 * 
	 * @param aehnlichkeit Text der Kopfdaten.
	 */
	public void setAehnlichekit(Paragraph aehnlichkeit) {
		this.aehnlichkeit = aehnlichkeit;
	}

	/**
	 * Auslesen des Berichtstitels.
	 * 
	 * @return Titeltext
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Setzen des Berichtstitels.
	 * 
	 * @param title
	 *            Titeltext
	 */
	public void setSubTitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getSubTitle() {
      return this.subtitle;
  }

  /**
   * Setzen des Berichtstitels.
   * 
   * @param title
   *            Titeltext
   */
  public void setTitle(String title) {
      this.title = title;
  }

	/**
	 * Auslesen des Erstellungsdatums.
	 * 
	 * @return Datum der Erstellung des Berichts
	 */
	public Date getCreated() {
		return this.created;
	}

	/**
	 * Setzen des Erstellungsdatums. <b>Hinweis:</b> Der Aufruf dieser Methoden
	 * ist nicht unbedingt erforderlich, da jeder Report bei seiner Erstellung
	 * automatisch den aktuellen Zeitpunkt festhält.
	 * 
	 * @param created
	 *            Zeitpunkt der Erstellung
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	public Profil getProfil() {
		return profil;
	}

	public void setProfil(Profil profil) {
		this.profil = profil;
	}

	public Paragraph getImprint3() {
		return imprint3;
	}

	public void setImprint3(Paragraph imprint3) {
		this.imprint3 = imprint3;
	}

}
