package de.superteam2000.gwt.shared.report;

import java.io.Serializable;

import de.superteam2000.gwt.client.BasicFrame;

public class WidgetReport extends BasicFrame implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String headlineText = "";

	public void setHeadlineText(String headlineText) {
		this.headlineText = headlineText;
	}

	@Override
	public String getHeadlineText() {

		return this.headlineText;
	}

	@Override
	public void run() {
		
		
	}


}
