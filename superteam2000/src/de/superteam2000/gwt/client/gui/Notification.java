package de.superteam2000.gwt.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;

/**
 * Die Klasse Notification erstellt einen Popup, der in der oberen Rechten Ecke des Bildschirms
 * angezeigt wird. Dieser Popup (toast) dient dazu Feedback (warining, info, error) an den Benutzer
 * heranzutragen.
 * 
 * @author Volz
 *
 */
public class Notification extends PopupPanel implements ClickHandler {
  /*
   * Alle notwendigen Instanzvariablen werden deklariert
   */
  timer t1 = new timer();

  /**
   * Erstellt einen Notification-Popup, welcher 7 Sekunden lang in der oberen rechten Ecke des
   * Bildschrims angezeigt wird.
   * 
   * @param text Der Text der Notification
   * @param alert Die Stufe der Notification (info, warning, error, success)
   */
  public Notification(String text, String alert) {
    super(false);
    this.setWidget(new Label(text));
    HTML contents = new HTML(text);
    this.setWidget(contents);

    UIObject.setStyleName(getContainerElement(), "toast-message");
    this.setStyleName("toast toast-" + alert);

    // Clickhandler hanzufügen um Popup zu löschen
    sinkEvents(Event.ONCLICK);
    this.addHandler(this, ClickEvent.getType());

    getElement().setAttribute("style", "display: block;");
    show();
  }

  /**
   * Fügt das Notification Objekt der Div "toast-container" hinzu und lässt es 7 Sekunden lang
   * anzeigen.
   */
  @Override
  public void show() {
    RootPanel.get("toast-container").add(Notification.this);
    // Popups nach 7 sec löschen
    t1.schedule(7000);

  }
/**
 * Löscht das Notification Popup beim onClick
 */
  @Override
  public void onClick(ClickEvent event) {
    RootPanel.get("toast-container").clear();
    t1.schedule(0);
  }

  /**
   * Löscht das Notification Popup 
   */
  public class timer extends Timer {
    @Override
    public void run() {
      RootPanel.get("toast-container").clear();
    }

  }

}
