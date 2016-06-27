package de.superteam2000.gwt.shared.bo;

/**
 * Hier wird ein exemplarisches Infoobjekt eines Users realisiert. Ein Infoobjekt ist eine vom User
 * festgelegte Eigenschaft.
 *
 * @author Christopher Funke, Daniel Volz
 * @version 1.0
 */
public class Info extends BusinessObject {

  private static final long serialVersionUID = 1L;

  /**
   * Deklaration der Parameter eines Infoobjektes
   * 
   * @param text Die Bezeichnung der Eigenschaft
   * @param eigenschaftId Die ID der Eigenschaft, zu der das Infoobjekt gehört
   * @param profilId Die ID des Profils des eingeloggten Users, zu dem das Infoobjekt gehört
   */
  private String text = "";
  private int eigenschaftId = 0;
  private int profilId = 0;


  /**
   * Auslesen des Parameters Text
   * 
   * @return text
   */
  public String getText() {
    return this.text;
  }

  /**
   * Setzen des Parameters Text
   * 
   * @param text
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * Erzeugen einer ganzen Zahl, die das Info-Objekt charakterisiert.
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = (prime * result) + this.eigenschaftId;
    result = (prime * result) + ((this.text == null) ? 0 : this.text.hashCode());
    return result;
  }

  /**
   * Feststellen der inhaltlichen Gleichheit zweier Info-Objekte anhand der Eigenschafts-ID.
   * 
   * @see java.lang.Object#equals(Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean result = false;
    if ((object == null) || (object.getClass() != this.getClass())) {
      result = false;
    } else {
      Info i = (Info) object;
      if (this.getText().equals(i.getText()) && (this.getEigenschaftId() == i.getEigenschaftId())) {
        result = true;
      }
    }
    return result;
  }

  /**
   * Auslesen der Eigenschafts-ID
   * 
   * @return eigenschaftId
   */
  public int getEigenschaftId() {
    return this.eigenschaftId;
  }

  /**
   * Setzen der Eigenschafts-ID
   * 
   * @param eigenschaft_id
   */
  public void setEigenschaftId(int eigenschaft_id) {
    this.eigenschaftId = eigenschaft_id;
  }

  /**
   * Auslesen der Profil-Id
   * 
   * @return profilId
   */
  public int getProfilId() {
    return this.profilId;
  }

  /**
   * Setzen der Profil-Id
   * 
   * @param profil_id
   */
  public void setProfilId(int profil_id) {
    this.profilId = profil_id;
  }

}
