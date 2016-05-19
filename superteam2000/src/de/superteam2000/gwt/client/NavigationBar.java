package de.superteam2000.gwt.client;



import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

import de.superteam2000.gwt.shared.bo.Profil;
	

public class NavigationBar extends BasicFrame {

	Profil user = ClientsideSettings.getCurrentUser();
	private String headlineText;
	HorizontalPanel hp = new HorizontalPanel();
	
	@Override
	protected String getHeadlineText() {
		return this.headlineText;
	}
	

	@Override
	protected void run() {
		this.setCellHorizontalAlignment(hp, ALIGN_RIGHT);
		hp.setHorizontalAlignment(ALIGN_RIGHT);
		if (user != null && user.isLoggedIn()) {


			Button logoutBtn = new Button("Logout");
			hp.add(logoutBtn);
			logoutBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					Window.open(user.getLogoutUrl(), "_self", "");					
				}
			});
			append(logoutBtn);
			
			Button merklisteBtn = new Button("Merkliste");
			hp.add(merklisteBtn);
			merklisteBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					Window.alert("Merkliste");
					
				}
			});
			append(merklisteBtn);
			
			Button sperrlisteBtn = new Button("Sperrliste");
			hp.add(sperrlisteBtn);
			sperrlisteBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					Window.alert("SPerrliste");
				}
				
			});
			append(sperrlisteBtn);
			
			Button suchprofilBtn = new Button("Suchprofil");
			hp.add(suchprofilBtn);
			suchprofilBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					Window.alert("Suchprofil");
				}
			});
			append(suchprofilBtn);
			
			Button eigenschaftenBtn = new Button("Eigenschaften");
			hp.add(eigenschaftenBtn);
			eigenschaftenBtn.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					Window.alert("Eignschaften");
				}
			});
			append(eigenschaftenBtn);

			this.add(hp);
			

//			Anchor logOutLink = new Anchor("Logout");
//						logOutLink.setHref(user.getLogoutUrl());
//			this.append(logOutLink);
		}


	}
}


