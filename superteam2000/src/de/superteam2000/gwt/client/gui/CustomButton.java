package de.superteam2000.gwt.client.gui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;

/**
 * Die Klasse CustomButton erweiter die Klasse Button. Sie dient dazu Icons (FontAwesome) in einem
 * Button darzustellen.
 *
 * @author Volz
 *
 */
@SuppressWarnings("deprecation")
public class CustomButton extends Button {
  private String text;
  HTML iHeading = new HTML();
  boolean isPushed = false;
  
  public CustomButton(String text) {
    super(text);
    this.setStyleName("pure-button");
  }

  public CustomButton() {
    super();
    this.setStyleName("pure-button");
  }

  public boolean isPushed() {
    return isPushed;
  }

  /**
   * Setzt die Variable isPushed und ändert den CSS Style des Buttons, wenn er geklickt wurde.
   * 
   * @param isPushed the isPushed to set
   */
  public void setPushed(boolean isPushed) {
    if (isPushed) {
      // this.removeStyleName("pure-button");
      this.addStyleName("pure-button-hover");
    } else {
      this.setStyleName("pure-button");
    }

    this.isPushed = isPushed;
  }
  /**
   * Füg einen FontAwesome-Icon über seinen Namen hinzu
   * 
   * @param iconName
   */
  public void setIcon(String iconName) {
    iHeading.setStyleName(iconName);
    DOM.insertChild(getElement(), iHeading.getElement(), 2);
  }

  @Override
  public void setText(String text) {
    this.text = text;
    Element span = DOM.createElement("span");
    span.setInnerText(text);
    span.setAttribute("style", "padding-left:3px; vertical-align:middle;");

    DOM.insertChild(getElement(), span, 0);
  }

  @Override
  public String getText() {
    return text;
  }
}
