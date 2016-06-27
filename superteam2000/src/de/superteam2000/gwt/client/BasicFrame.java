package de.superteam2000.gwt.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Diese Klasse ist die Basisklasse aller BasicFrames. Jeder BasicFrame ist ein VerticalPanel und
 * lässt sich somit unter GWT entsprechend anordnen.
 *
 * @author thies
 * @version 1.0
 *
 */
public abstract class BasicFrame extends FlowPanel {

  /**
   * Jedes GWT Widget muss diese Methode implementieren. Sie gibt an, sas geschehen soll, wenn eine
   * Widget-Instanz zur Anzeige gebracht wird.
   */
  @Override
  public void onLoad() {
    /*
     * Bevor wir unsere eigene Formatierung veranslassen, überlassen wir es der Superklasse eine
     * Initialisierung vorzunehmen.
     */

    super.onLoad();
    this.add(this.createHeadline(this.getHeadlineText(), this.getSubHeadlineText()));
    RootPanel.get("search-table").clear();
    /*
     * Als erstes geben wir stets die Headline des BasicFrame aus. Da getHeadlineText() als
     * abstrakte Methode bzw. als Einschubmethode realisiert wird, obliegt es den Subklassen, für
     * eine Ausgestaltung also Implementierung zu sorgen.
     */


    /*
     * Wenn alles vorbereitet ist, stoßen wir die run()-Methode an. Auch run() ist als abstrakte
     * Methode bzw. als Einschubmethode realisiert. Auch hier ist es Aufgabe der Subklassen, für
     * deren Implementierung zu sorgen.
     */
    this.run();

  }

  /**
   * Mit Hilfe dieser Methode erstellen wir aus einem String ein mittels CSS formatierbares
   * HTML-Element. Unter CSS lässt sich das Ergebnis über <code>.bankproject-headline</code>
   * referenzieren bzw. formatieren.
   * 
   * @param text der String, den wir als andernorts HTML setzen wollen.
   * @return GWT HTML Widget.
   */
  protected HTML createHeadline(String header, String subHeader) {
    HTML content = new HTML();
    content.setStylePrimaryName("header");
    content.setHTML("<h1>" + header + "</h1><h2>" + subHeader + "</h2>");
    return content;
  }


  /**
   * Mit Hilfe dieser Methode erstellen wir aus einem Strinng ein mittels CSS formatierbares
   * HTML-Element, das an das Ende der bisherigen Ausgabe dieses BasicFrame angehängt wird. Unter
   * CSS lässt sich das Ergebnis über <code>.bankproject-simpletext</code> referenzieren bzw.
   * formatieren.
   * 
   * @param text der String, den wir als HTML an die bisherige BasicFrame-Ausgabe anhängen wollen.
   */
  protected void append(String text) {
    HTML content = new HTML(text);
    // content.setStylePrimaryName("bankproject-simpletext");
    this.add(content);
  }

  /**
   * Mit Hilfe dieser Methode erstellen wir aus einem Strinng ein mittels CSS formatierbares
   * HTML-Element, das an das Ende der bisherigen Ausgabe dieses BasicFrame angehängt wird. Unter
   * CSS lässt sich das Ergebnis über <code>.bankproject-simpletext</code> referenzieren bzw.
   * formatieren.
   * 
   * @param text der String, den wir als HTML an die bisherige BasicFrame-Ausgabe anhängen wollen.
   */
  protected void append(Widget wi) {
    this.add(wi);
  }

  /**
   * Abstrakte Einschubmethode, die in den Subklassen zu realisieren ist.
   * 
   * @return der Text, den wir als Headline setzen wollen.
   */
  protected abstract String getHeadlineText();

  protected abstract String getSubHeadlineText();

  /**
   * Abstrakte Einschubmethode, die in den Subklassen zu realisieren ist.
   */
  protected abstract void run();
}
