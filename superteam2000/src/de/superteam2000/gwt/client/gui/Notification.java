package de.superteam2000.gwt.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class Notification extends PopupPanel implements ClickHandler {

  public Notification(String text, String alert) {
    super(false);
    setWidget(new Label(text));
    HTML contents = new HTML(text);
    setWidget(contents);
    this.setStyleName(getContainerElement(), "toast-message");
    setStyleName("toast toast-" + alert);
    
    //Clickhandler hanzufügen um Popup zu löschen
    sinkEvents(Event.ONCLICK);
    addHandler(this, ClickEvent.getType());

    getElement().setAttribute("style", "display: block;");
    show();
  }

  @Override
  public void show() {
    RootPanel.get("toast-container").add(Notification.this);
    Timer t = new Timer() {
      @Override
      public void run() {
        Notification.this.hide();
        RootPanel.get("toast-container").clear();
      }
    };

    //Popups nach 7 sec löschen
    
    t.schedule(7000);

  }

  @Override
  public void onClick(ClickEvent event) {
    RootPanel.get("toast-container").clear();
    
  }



}
