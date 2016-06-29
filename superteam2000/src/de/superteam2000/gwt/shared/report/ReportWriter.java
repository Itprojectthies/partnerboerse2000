package de.superteam2000.gwt.shared.report;

/**
 * Diese Klasse wird benötigt, um auf dem Client die ihm vom Server zur Verfügung gestellten
 * Report-Objekte in ein menschenlesbares Format zu überführen.
 *  In dieser Klasse werden die Signaturen
 * der Methoden deklariert, die für die Prozessierung der Quellinformation zuständig sind.
 * </p>
 *
 * @author Thies, Volz, Funke
 */
public abstract class ReportWriter {

  public abstract void process(AllProfilesBySucheReport r);

  public abstract void process(ProfilReport r);

  public abstract void process(AllNotVisitedProfileReport r);

  public abstract void process(AllNewProfileReport r);

  public abstract void process(AllProfilesReport r);


}
