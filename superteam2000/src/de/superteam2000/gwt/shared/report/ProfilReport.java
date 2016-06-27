package de.superteam2000.gwt.shared.report;

import java.io.Serializable;

/**
 * Report f�r das anzeigen eines Profils
 */
public class ProfilReport extends SimpleReport implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private int profilId;

  public int getProfilId() {
    return profilId;
  }

  public void setProfilId(int profilId) {
    this.profilId = profilId;
  }

}
