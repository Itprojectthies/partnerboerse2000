package de.superteam2000.gwt.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Diese Klasse ist die Basisklasse aller BasicFrames. Jeder BasicFrame ist ein FlowPanel
 *
 * @author Thies, Volz
 * @version 1.0
 *
 */
public abstract class BasicFrame extends FlowPanel {


  @Override
  public void onLoad() {
    /*
     * Die Super-Klasse initialisiert das Widget
     */
    super.onLoad();
    
    /*
     * Füge die jeweilige Implementation der Headline und der Subheadline zum FlowPanel hinzu
     */
    this.add(createHeadline(getHeadlineText(), getSubHeadlineText()));
    
    /*
     * Lösche die Datagrid für jede konkretisierte Klasse 
     */
    RootPanel.get("search-table").clear();
   
    run();

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
   * Abstrakte Einschubmethode, die die Überschrift setzt.
   *
   * @return der Text, den wir als Headline setzen wollen.
   */
  protected abstract String getHeadlineText();
  
  /**
  * Abstrakte Einschubmethode, die die Sub-Überschrift setzt
  *
  * @return der Text, den wir als Headline setzen wollen.
  */
  protected abstract String getSubHeadlineText();

  /**
   * Abstrakte Einschubmethode, die in den Subklassen zu realisieren ist.
   */
  protected abstract void run();
}
