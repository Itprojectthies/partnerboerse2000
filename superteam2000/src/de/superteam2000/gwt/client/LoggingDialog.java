package de.superteam2000.gwt.client;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.logging.client.HasWidgetsLogHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class LoggingDialog {
	private static DialogBox dialog;
	
	public static DialogBox getDialogBox() {
		if (dialog == null) {
			dialog = new DialogBox();
			dialog.setHTML("Log-Information<div style=\"float: right; cursor: pointer;\">x</div>");
			Button closeBtn = new Button("x");
			closeBtn.setStylePrimaryName("btn btn-link");
			closeBtn.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					dialog.hide();
				}
			});
			
			
			
			dialog.getCaption().addMouseDownHandler(new MouseDownHandler() {
				
				@Override
				public void onMouseDown(MouseDownEvent event) {
					dialog.hide();
				}
			});
			
			dialog.setModal(true);
			dialog.setWidth("100%");
			VerticalPanel logPanel = new VerticalPanel();
			logPanel.getElement().setId("logDialogBox");
			
			ScrollPanel scrollPanel = new ScrollPanel(logPanel);
			scrollPanel.setWidth("300px");
			scrollPanel.setHeight("350px");
			
			HorizontalPanel contentPane = new HorizontalPanel();
			contentPane.add(scrollPanel);
			
		
			dialog.add(contentPane);
			dialog.setGlassEnabled(true);
			dialog.setAnimationEnabled(true);
			dialog.center();
			dialog.hide();
			
			
			Logger logger = ClientsideSettings.getLogger();
			logger.addHandler(new HasWidgetsLogHandler(logPanel));
			
			logger.info("Logger Dialog Box initialisiert.");
		}
		return dialog;
	}
}
