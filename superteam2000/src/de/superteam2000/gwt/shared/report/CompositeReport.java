package de.superteam2000.gwt.shared.report;

import java.io.Serializable;
import java.util.Vector;

/**
 * Ein zusammengesetzter Report. Dieser Report kann aus einer Menge von Teil-Reports
 * @author Thies, Volz, Funke
 */
public abstract class CompositeReport extends Report implements Serializable {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * Die Menge der Teil-Reports.
   */
  private Vector<Report> subReports = new Vector<Report>();

  /**
   * Hinzufügen eines Teil-Reports.
   *
   * @param r der hinzuzufügende Teil-Report.
   */
  public void addSubReport(Report r) {
    subReports.addElement(r);
  }

  /**
   * Entfernen eines Teil-Reports.
   *
   * @param r der zu entfernende Teil-Report.
   */
  public void removeSubReport(Report r) {
    subReports.removeElement(r);
  }

  /**
   * Auslesen der Anzahl von Teil-Reports.
   *
   * @return int Anzahl der Teil-Reports.
   */
  public int getNumSubReports() {
    return subReports.size();
  }

  /**
   * Auslesen eines einzelnen Teil-Reports.
   *
   * @param i 
   *
   * @return Position des Teil-Reports.
   */
  public Report getSubReportAt(int i) {
    return subReports.elementAt(i);
  }
}
