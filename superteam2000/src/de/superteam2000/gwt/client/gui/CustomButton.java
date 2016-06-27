package de.superteam2000.gwt.client.gui;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;

public class CustomButton extends Button {
    private String text;
    HTML iHeading= new HTML();
    boolean isPushed = false;
    
    /**
     * @return the isPushed
     */
    public boolean isPushed() {
      return isPushed;
    }

    /**
     * @param isPushed the isPushed to set
     */
    public void setPushed(boolean isPushed) {
      this.isPushed = isPushed;
    }

    public CustomButton(String text){
        super(text);
        this.setStyleName("pure-button");
    }
    public CustomButton(){
      super();
      this.setStyleName("pure-button");
    }

    public void setResource(ImageResource imageResource){
        Image img = new Image(imageResource);
        String definedStyles = img.getElement().getAttribute("style");
        img.getElement().setAttribute("style", definedStyles + "; vertical-align:middle;");
        DOM.insertBefore(getElement(), img.getElement(), DOM.getFirstChild(getElement()));
    }
    
    public void setIcon(String iconText){
      iHeading.setStyleName(iconText);
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
        return this.text;
    }
}