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

  timer t1 = new timer();

  public Notification(String text, String alert) {
    super(false);
    setWidget(new Label(text));
    HTML contents = new HTML(text);
    setWidget(contents);
    this.setStyleName(getContainerElement(), "toast-message");
    setStyleName("toast toast-" + alert);

    // Clickhandler hanzufügen um Popup zu löschen
    sinkEvents(Event.ONCLICK);
    addHandler(this, ClickEvent.getType());

    getElement().setAttribute("style", "display: block;");
    show();
  }

  @Override
  public void show() {
    RootPanel.get("toast-container").add(Notification.this);
    // Popups nach 7 sec löschen
    t1.schedule(7000);

  }

  @Override
  public void onClick(ClickEvent event) {
    RootPanel.get("toast-container").clear();
    t1.schedule(0);
  }

  public class timer extends Timer {
    @Override
    public void run() {
      RootPanel.get("toast-container").clear();
    }

  }

}
