package de.superteam2000.gwt.client.gui;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;

import de.superteam2000.gwt.client.ClientsideSettings;

public class CustomPopupPanel extends PopupPanel {
  CustomTimer t1 = new CustomTimer();
  
  public CustomPopupPanel(boolean autoHide, boolean isImage) {
    super(autoHide);
    int left = (Window.getClientWidth() - 100) ;
    int top = (Window.getClientHeight() -  600) ;
    this.setPopupPosition(left, top);
    if (isImage) {
      Image i = new Image("/img/heart.svg");
      i.setPixelSize(70, 70);
      this.add(i);
    } else {
      HTML load = new HTML();
      load.setHTML("<i class=\"fa fa-cog fa-spin fa-3x fa-fw\"></i>");
      this.add(load);
    }
    
  }

  public void load() {
    t1.schedule(500);
  }
  
  public void stop() {
    t1.cancel();
    this.hide();
  }
  
  public class CustomTimer extends Timer {
    @Override
    public void run() {
      show();
    }

  }
}
