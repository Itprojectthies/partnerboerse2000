package de.superteam2000.gwt.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;

public class Notification extends PopupPanel implements ClickHandler {

  timer t1 = new timer();

  public Notification(String text, String alert) {
    super(false);
    this.setWidget(new Label(text));
    HTML contents = new HTML(text);
    this.setWidget(contents);
    UIObject.setStyleName(this.getContainerElement(), "toast-message");
    this.setStyleName("toast toast-" + alert);

    // Clickhandler hanzufügen um Popup zu löschen
    this.sinkEvents(Event.ONCLICK);
    this.addHandler(this, ClickEvent.getType());

    this.getElement().setAttribute("style", "display: block;");
    this.show();
  }

  @Override
  public void show() {
    RootPanel.get("toast-container").add(Notification.this);
    // Popups nach 7 sec löschen
    this.t1.schedule(7000);

  }

  @Override
  public void onClick(ClickEvent event) {
    RootPanel.get("toast-container").clear();
    this.t1.schedule(0);
  }

  public class timer extends Timer {
    @Override
    public void run() {
      RootPanel.get("toast-container").clear();
    }

  }

}
