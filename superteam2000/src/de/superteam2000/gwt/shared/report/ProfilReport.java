package de.superteam2000.gwt.shared.report;

import java.io.Serializable;

/**
 * Report für das anzeigen eines Profils
 * @author Funke
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
