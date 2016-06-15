package de.superteam2000.gwt.client.gui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class Label extends FocusWidget implements HasText {

  public Label() {
    setElement(DOM.createLabel());
  }
  
  public Label(String text) {
    this();
    getElement().setInnerText((text == null) ? "" : text);
  }
  
  @Override
  public String getText() {
    return getElement().getInnerText();
  }

  @Override
  public void setText(String text) {
    getElement().setInnerText((text == null) ? "" : text);
  }

  public void setFor(String forWho) {
    getElement().setAttribute("for", forWho);
  }
}
