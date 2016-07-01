package de.superteam2000.gwt.shared.report;

import java.util.Vector;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import de.superteam2000.gwt.client.ClientsideSettings;

/**
 * Ein ReportWriter, der Reports mittels HTML formatiert. Das im Zielformat vorliegende
 * Ergebnis wird in der Variable reportText abgelegt und kann nach Aufruf der
 * entsprechenden Prozessierungsmethode mit getReportText() ausgelesen werden.
 *
 * @author Thies, Volz, Funke
 */
public class HTMLReportWriter extends ReportWriter {

  /**
   * In dieser Variable wird der HTML Text gespeichert
   */
  private String reportText = "";

  /**
   * Zurücksetzen der Variable reportText.
   */
  public void resetReportText() {
    reportText = "";
  }

  /**
   * Umwandeln eines Paragraph-Objekts in HTML.
   *
   * @param p der Paragraph
   * @return HTML-Text
   */
  public String paragraph2HTML(Paragraph p) {
    if (p instanceof CompositeParagraph) {
      return this.paragraph2HTML((CompositeParagraph) p);
    } else {
      return this.paragraph2HTML((SimpleParagraph) p);
    }
  }

  /**
   * Umwandeln eines CompositeParagraph-Objekts in HTML.
   *
   * @param p der CompositeParagraph
   * @return HTML-Text
   */
  public String paragraph2HTML(CompositeParagraph p) {
    StringBuffer result = new StringBuffer();

    for (int i = 0; i < p.getNumParagraphs(); i++) {
      result.append("<p>" + p.getParagraphAt(i) + "</p>");
    }

    return result.toString();
  }

  /**
   * Umwandeln eines SimpleParagraph-Objekts in HTML.
   *
   * @param p der SimpleParagraph
   * @return HTML-Text
   */
  public String paragraph2HTML(SimpleParagraph p) {
    return "<p>" + p.toString() + "</p>";
  }

  /**
   * HTML-Header-Text produzieren.
   *
   * @return HTML-Text
   */
  public String getHeader() {
    StringBuffer result = new StringBuffer();

    result.append("<html><head><title></title></head><body>");
    return result.toString();
  }

  /**
   * HTML-Trailer-Text produzieren.
   *
   * @return HTML-Text
   */
  public String getTrailer() {
    return "</body></html>";
  }

  /**
   * Prozessieren des übergebenen Reports und Ablage im Zielformat. 
   *
   * @param r der zu prozessierende AllNotVisitedProfileReport
   */
  @Override
  public void process(AllNotVisitedProfileReport r) {

    resetReportText();

    StringBuffer result = new StringBuffer();

    HTML titel = new HTML("<h1>" + r.getTitle() + "</h1>");
    titel.setStyleName("header");
    RootPanel.get("main").add(titel);


    for (int i = 0; i < r.getNumSubReports(); i++) {

      ProfilReport subReport = (ProfilReport) r.getSubReportAt(i);

      this.process(subReport);

      result.append(reportText + "\n");

      resetReportText();
    }
    reportText = result.toString();
  }

  /**
   * Prozessieren des übergebenen Reports und Ablage im Zielformat. 
   *
   * @param r der zu prozessierende AllNewProfileReport
   */
  @Override
  public void process(AllNewProfileReport r) {

    resetReportText();

    StringBuffer result = new StringBuffer();

    HTML titel = new HTML("<h1>" + r.getTitle() + "</h1>");
    titel.setStyleName("header");
    RootPanel.get("main").add(titel);

    for (int i = 0; i < r.getNumSubReports(); i++) {

      ProfilReport subReport = (ProfilReport) r.getSubReportAt(i);

      this.process(subReport);

      result.append(reportText + "\n");

      resetReportText();
    }

    reportText = result.toString();

  }

  /**
   * Auslesen des Ergebnisses der zuletzt aufgerufenen Prozessierungsmethode.
   *
   * @return ein String im HTML-Format
   */
  public String getReportText() {
    return getHeader() + reportText + getTrailer();
  }
  
  /**
   * Prozessieren des übergebenen Reports und Ablage im Zielformat. 
   *
   * @param r der zu prozessierende ProfilReport
   */
  @Override
  public void process(ProfilReport r) {
    resetReportText();

    StringBuffer result = new StringBuffer();

    result.append("<div class=\"container\">");
    result.append("<div class=\"avatar-flip\">");
    result.append("<img src=\"http://lorempixel.com/200/200/cats/\" height=\"150\" width=\"150\">");
    result.append("<img src=\"http://lorempixel.com/200/200/cats/\" height=\"150\" width=\"150\">");
    result.append("</div>");
    result.append("<h4> Ähnlichkeit " + r.getAehnlichkeit() + "%</h4>");

    result.append("<h2>" + r.getName() + "</h2>");
    result.append("<div class=\"profil-attribute\">");
    result.append("<p>" + r.getProfilAttributeBez().toString() + "</p>");
    result.append("<p>" + r.getProfilAttribute().toString() + "</p>");
    result.append("</div>");

    result.append("<h3>Interessen</h3>");



    Vector<Row> rows = r.getRows();
    if (rows != null) {
      ClientsideSettings.getLogger().info("Vector an Rows im HTMLWriter ungleich null");
    }
    result.append("<div>");
    for (int i = 0; i < rows.size(); i++) {
      Row row = rows.elementAt(i);
      result.append("<div class=\"profil-attribute-combo\">");
      for (int k = 0; k < row.getNumColumns(); k++) {

        result.append("<p>");
        result.append(" " + row.getColumnAt(k));
        result.append("</p>");

      }
      result.append("</div>");
    }
    result.append("</div>");
    result.append("</div>");


    reportText = result.toString();

  }

  /**
   * Prozessieren des übergebenen Reports und Ablage im Zielformat. 
   *
   * @param r der zu prozessierende AllProfilesReport
   */

  @Override
  public void process(AllProfilesReport r) {

    resetReportText();

    StringBuffer result = new StringBuffer();

    HTML titel = new HTML("<h1>" + r.getTitle() + "</h1>");
    titel.setStyleName("header");
    RootPanel.get("main").add(titel);

    for (int i = 0; i < r.getNumSubReports(); i++) {

      ProfilReport subReport = (ProfilReport) r.getSubReportAt(i);

      this.process(subReport);

      result.append(reportText + "\n");

      resetReportText();
    }
    result.append("</div>");

    reportText = result.toString();

  }

  /**
   * Prozessieren des übergebenen Reports und Ablage im Zielformat. 
   *
   * @param r der zu prozessierende AllProfilesBySucheReport
   */

  @Override
  public void process(AllProfilesBySucheReport r) {

    resetReportText();

    StringBuffer result = new StringBuffer();

    HTML titel = new HTML("<h1>" + r.getTitle() + "</h1>");
    titel.setStyleName("header");
    RootPanel.get("main").add(titel);

    HTML subTitel = new HTML("<h2>" + r.getSubTitle() + "</h2>");
    subTitel.setStyleName("header");
    RootPanel.get("main").add(subTitel);


    for (int i = 0; i < r.getNumSubReports(); i++) {

      ProfilReport subReport = (ProfilReport) r.getSubReportAt(i);

      this.process(subReport);

      result.append(reportText + "\n");

      resetReportText();
    }

    reportText = result.toString();

  }

}
