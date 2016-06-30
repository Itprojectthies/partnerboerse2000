package de.superteam2000.gwt.client.gui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
/**
 * Die Klasse ListItemWidget wird für das CSS Styling benötigt.
 * 
 * @author Volz
 *
 */
public class ListItemWidget extends SimplePanel {
  public ListItemWidget() {
    super((Element) Document.get().createLIElement().cast());
  }

  public ListItemWidget(String s) {
    this();
    getElement().setInnerText(s);
  }

  public ListItemWidget(Widget w) {
    this();
    this.add(w);
  }
}
