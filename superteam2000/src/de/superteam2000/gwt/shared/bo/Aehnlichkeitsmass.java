package de.superteam2000.gwt.shared.bo;

import java.util.ArrayList;

public class Aehnlichkeitsmass extends BusinessObject{
	
	  private static final long serialVersionUID = 1L;
	  
	  private float aehnlichkeitsmass;

	public float getAehnlichkeitsmass() {
		return aehnlichkeitsmass;
	}

	public void setAehnlichkeitsmass(int aehnlichkeitsmass) {
		this.aehnlichkeitsmass = aehnlichkeitsmass;
	}
	
	public ArrayList<Profil> aehnlichkeitsmassBerechnen(Profil p, 
			Profil referenz){
		return null;
	}
	
	
	

}
