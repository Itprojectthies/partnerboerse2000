package de.superteam2000.gwt.shared.report;

import java.util.Vector;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import de.superteam2000.gwt.client.ClientsideSettings;

/**
 * Ein <code>ReportWriter</code>, der Reports mittels HTML formatiert. Das im Zielformat vorliegende
 * Ergebnis wird in der Variable <code>reportText</code> abgelegt und kann nach Aufruf der
 * entsprechenden Prozessierungsmethode mit <code>getReportText()</code> ausgelesen werden.
 * 
 * @author Thies
 */
public class HTMLReportWriter extends ReportWriter {

  /**
   * Diese Variable wird mit dem Ergebnis einer Umwandlung (vgl. <code>process...</code>-Methoden)
   * belegt. Format: HTML-Text
   */
  private String reportText = "";

  /**
   * Zurücksetzen der Variable <code>reportText</code>.
   */
  public void resetReportText() {
    this.reportText = "";
  }

  /**
   * Umwandeln eines <code>Paragraph</code>-Objekts in HTML.
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
   * Umwandeln eines <code>CompositeParagraph</code>-Objekts in HTML.
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
   * Umwandeln eines <code>SimpleParagraph</code>-Objekts in HTML.
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
   * Prozessieren des übergebenen Reports und Ablage im Zielformat. Ein Auslesen des Ergebnisses
   * kann später mittels <code>getReportText()</code> erfolgen.
   * 
   * @param r der zu prozessierende Report
   */
  @Override
  public void process(AllNotVisitedProfileReport r) {
    // Zunächst löschen wir das Ergebnis vorhergehender Prozessierungen.
    this.resetReportText();

    /*
     * In diesen Buffer schreiben wir während der Prozessierung sukzessive unsere Ergebnisse.
     */
    StringBuffer result = new StringBuffer();

    /*
     * Nun werden Schritt für Schritt die einzelnen Bestandteile des Reports ausgelesen und in
     * HTML-Form übersetzt.
     */
    HTML titel = new HTML("<h1>" + r.getTitle() + "</h1>");
    titel.setStyleName("header");
    RootPanel.get("main").add(titel);
    
    /*
     * Da AllAccountsOfAllCustomersReport ein CompositeReport ist, enth�lt r eine Menge von
     * Teil-Reports des Typs AllAccountsOfCustomerReport. F�r jeden dieser Teil-Reports rufen wir
     * processAllAccountsOfCustomerReport auf. Das Ergebnis des jew. Aufrufs f�gen wir dem Buffer
     * hinzu.
     */
    for (int i = 0; i < r.getNumSubReports(); i++) {
      /*
       * AllAccountsOfCustomerReport wird als Typ der SubReports vorausgesetzt. Sollte dies in einer
       * erweiterten Form des Projekts nicht mehr gelten, so m�sste hier eine detailliertere
       * Implementierung erfolgen.
       */
      ProfilReport subReport = (ProfilReport) r.getSubReportAt(i);

      this.process(subReport);

      result.append(this.reportText + "\n");

      /*
       * Nach jeder �bersetzung eines Teilreports und anschlie�endem Auslesen sollte die
       * Ergebnisvariable zur�ckgesetzt werden.
       */
      this.resetReportText();
    }

    /*
     * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und der reportText-Variable
     * zugewiesen. Dadurch wird es m�glich, anschlie�end das Ergebnis mittels getReportText()
     * auszulesen.
     */
    this.reportText = result.toString();
  }

  /**
   * Prozessieren des übergebenen Reports und Ablage im Zielformat. Ein Auslesen des Ergebnisses
   * kann später mittels <code>getReportText()</code> erfolgen.
   * 
   * @param r der zu prozessierende Report
   */
  @Override
  public void process(AllNewProfileReport r) {
    // Zunächst löschen wir das Ergebnis vorhergehender Prozessierungen.
    this.resetReportText();

    /*
     * In diesen Buffer schreiben wir während der Prozessierung sukzessive unsere Ergebnisse.
     */
    StringBuffer result = new StringBuffer();

    /*
     * Nun werden Schritt für Schritt die einzelnen Bestandteile des Reports ausgelesen und in
     * HTML-Form übersetzt.
     */
    HTML titel = new HTML("<h1>" + r.getTitle() + "</h1>");
    titel.setStyleName("header");
    RootPanel.get("main").add(titel);


    /*
     * Da AllAccountsOfAllCustomersReport ein CompositeReport ist, enth�lt r eine Menge von
     * Teil-Reports des Typs AllAccountsOfCustomerReport. F�r jeden dieser Teil-Reports rufen wir
     * processAllAccountsOfCustomerReport auf. Das Ergebnis des jew. Aufrufs f�gen wir dem Buffer
     * hinzu.
     */
    for (int i = 0; i < r.getNumSubReports(); i++) {
      /*
       * AllAccountsOfCustomerReport wird als Typ der SubReports vorausgesetzt. Sollte dies in einer
       * erweiterten Form des Projekts nicht mehr gelten, so m�sste hier eine detailliertere
       * Implementierung erfolgen.
       */
      ProfilReport subReport = (ProfilReport) r.getSubReportAt(i);

      this.process(subReport);

      result.append(this.reportText + "\n");

      /*
       * Nach jeder �bersetzung eines Teilreports und anschlie�endem Auslesen sollte die
       * Ergebnisvariable zur�ckgesetzt werden.
       */
      this.resetReportText();
    }

    /*
     * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und der reportText-Variable
     * zugewiesen. Dadurch wird es m�glich, anschlie�end das Ergebnis mittels getReportText()
     * auszulesen.
     */
    this.reportText = result.toString();

  }

  /**
   * Auslesen des Ergebnisses der zuletzt aufgerufenen Prozessierungsmethode.
   * 
   * @return ein String im HTML-Format
   */
  public String getReportText() {
    return this.getHeader() + this.reportText + this.getTrailer();
  }

  @Override
  public void process(ProfilReport r) {
    this.resetReportText();

    /*
     * In diesen Buffer schreiben wir während der Prozessierung sukzessive unsere Ergebnisse.
     */
    StringBuffer result = new StringBuffer();

    /*
     * Nun werden Schritt für Schritt die einzelnen Bestandteile des Reports ausgelesen und in
     * HTML-Form übersetzt.
     */
   
    result.append("<div class=\"container\">");
    result.append("<div class=\"avatar-flip\">");
    result.append("<img src=\"http://lorempixel.com/200/200/cats/\" height=\"150\" width=\"150\">");
    result.append("<img src=\"http://lorempixel.com/200/200/cats/\" height=\"150\" width=\"150\">");
    result.append("</div>");
    result.append("<h4> Ähnlichkeit " + r.getAehnlichkeit() + "%</h4>");
    
    result.append("<h2>" + r.getName() + "</h2>");
    result.append("<div>");
    result.append("<p>" + r.getProfilAttributeBez().toString() + "</p>");
    result.append("<p>" + r.getProfilAttribute().toString() + "</p>");
    result.append("</div>");
    
//    result.append(this.paragraph2HTML(r.getProfilAttribute()));
    result.append("<h3>Interessen</h3>");



    Vector<Row> rows = r.getRows();
    if (rows != null) {
      ClientsideSettings.getLogger().info("Vector an Rows im HTMLWriter ungleich null");
    }
    for (int i = 0; i < rows.size(); i++) {
      Row row = rows.elementAt(i);
     
      for (int k = 0; k < row.getNumColumns(); k++) {
        result.append("<p>");
        result.append(" " + row.getColumnAt(k));
        result.append("</p>");
      }
      
    }
    result.append("</div>");

    /*
     * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und der reportText-Variable
     * zugewiesen. Dadurch wird es möglich, anschließend das Ergebnis mittels getReportText()
     * auszulesen.
     */
    this.reportText = result.toString();

  }
 

  @Override
  public void process(AllProfilesReport r) {
    // Zunächst löschen wir das Ergebnis vorhergehender Prozessierungen.
    this.resetReportText();

    /*
     * In diesen Buffer schreiben wir während der Prozessierung sukzessive unsere Ergebnisse.
     */
    StringBuffer result = new StringBuffer();

    /*
     * Nun werden Schritt für Schritt die einzelnen Bestandteile des Reports ausgelesen und in
     * HTML-Form übersetzt.
     */
    HTML titel = new HTML("<h1>" + r.getTitle() + "</h1>");
    titel.setStyleName("header");
    RootPanel.get("main").add(titel);
    // result.append("<div class=\"header\">");
    // result.append("<h1>" + r.getTitle() + "</h1>");
    // result.append("</div>");

    // result.append("<td>" + paragraph2HTML(r.getImprint()) + "</td>");
    // result.append("</tr><tr><td></td><td>" + r.getCreated().toString() + "</td></tr></table>");

    /*
     * Da AllAccountsOfAllCustomersReport ein CompositeReport ist, enth�lt r eine Menge von
     * Teil-Reports des Typs AllAccountsOfCustomerReport. F�r jeden dieser Teil-Reports rufen wir
     * processAllAccountsOfCustomerReport auf. Das Ergebnis des jew. Aufrufs f�gen wir dem Buffer
     * hinzu.
     */
    for (int i = 0; i < r.getNumSubReports(); i++) {
      /*
       * AllAccountsOfCustomerReport wird als Typ der SubReports vorausgesetzt. Sollte dies in einer
       * erweiterten Form des Projekts nicht mehr gelten, so m�sste hier eine detailliertere
       * Implementierung erfolgen.
       */
      ProfilReport subReport = (ProfilReport) r.getSubReportAt(i);

      this.process(subReport);

      result.append(this.reportText + "\n");

      /*
       * Nach jeder �bersetzung eines Teilreports und anschlie�endem Auslesen sollte die
       * Ergebnisvariable zur�ckgesetzt werden.
       */
      this.resetReportText();
    }
    result.append("</div>");
    /*
     * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und der reportText-Variable
     * zugewiesen. Dadurch wird es m�glich, anschlie�end das Ergebnis mittels getReportText()
     * auszulesen.
     */
    this.reportText = result.toString();

  }

  @Override
  public void process(AllProfilesBySucheReport r) {
    // Zun�chst l�schen wir das Ergebnis vorhergehender Prozessierungen.
    this.resetReportText();

    /*
     * In diesen Buffer schreiben wir w�hrend der Prozessierung sukzessive unsere Ergebnisse.
     */
    StringBuffer result = new StringBuffer();

    /*
     * Nun werden Schritt f�r Schritt die einzelnen Bestandteile des Reports ausgelesen und in
     * HTML-Form �bersetzt.
     */
    HTML titel = new HTML("<h1>" + r.getTitle() + "</h1>");
    titel.setStyleName("header");
    RootPanel.get("main").add(titel);
    
    HTML subTitel = new HTML("<h2>" + r.getSubTitle() + "</h2>");
    subTitel.setStyleName("header");
    RootPanel.get("main").add(subTitel);
    
    
    for (int i = 0; i < r.getNumSubReports(); i++) {
      /*
       * AllAccountsOfCustomerReport wird als Typ der SubReports vorausgesetzt. Sollte dies in einer
       * erweiterten Form des Projekts nicht mehr gelten, so m�sste hier eine detailliertere
       * Implementierung erfolgen.
       */
      ProfilReport subReport = (ProfilReport) r.getSubReportAt(i);

      this.process(subReport);

      result.append(this.reportText + "\n");

      /*
       * Nach jeder �bersetzung eines Teilreports und anschlie�endem Auslesen sollte die
       * Ergebnisvariable zur�ckgesetzt werden.
       */
      this.resetReportText();
    }

    /*
     * Zum Schluss wird unser Arbeits-Buffer in einen String umgewandelt und der reportText-Variable
     * zugewiesen. Dadurch wird es m�glich, anschlie�end das Ergebnis mittels getReportText()
     * auszulesen.
     */
    this.reportText = result.toString();

  }

}
