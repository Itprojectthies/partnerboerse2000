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

//  public FlowPanel createFooter() {
//    FlowPanel footer = new FlowPanel();
//    FlowPanel footer = new FlowPanel();
//    footer.setStyleName("footer");
//    <div class="footer">
//      <div class="legal pure-g">
//        <div class="pure-u-1 u-sm-1-2">
//        <p class="legal-license">
//          This site is built with &lt;3 using Pure v0.6.0<br>
//              All code on this site is licensed under the <a href="https://github.com/yahoo/pure-site/blob/master/LICENSE.md">Yahoo BSD License</a> unless otherwise stated.
//       </p>
//      </div>
//
//<div class="pure-u-1 u-sm-1-2">
//  <ul class="legal-links">
//          <li><a href="https://github.com/yahoo/pure/">GitHub Project</a></li>
//          <li><a href="https://hackerone.com/yahoo/">Security Contact Info</a></li>
//</ul>
//
//<p class="legal-copyright">
//© 2014 Yahoo! Inc. All rights reserved.
//</p>
//</div>
//</div>

//</div>
    
//    return footer;
//  }

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
