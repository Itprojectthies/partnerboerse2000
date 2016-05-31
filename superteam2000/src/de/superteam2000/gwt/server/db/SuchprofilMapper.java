package de.superteam2000.gwt.server.db;
import java.sql.*;

import de.superteam2000.gwt.shared.bo.Suchprofil;

public class SuchprofilMapper {

	//insert, delete, update, findall

	  private static SuchprofilMapper suchprofilMapper = null;

	  protected SuchprofilMapper() {
	  }

	  public static SuchprofilMapper suchprofilMapper() {
	    if (suchprofilMapper == null) {
	      suchprofilMapper = new SuchprofilMapper();
	    }

	    return suchprofilMapper;
	  }
	  
	  public Suchprofil findall (int id){
		  Connection con = DBConnection.connection();
		  
		  try {
			  Statement stmt = con.createStatement();
			  String query= "";
			  ResultSet rs = stmt.executeQuery(query);

			  while(rs.next()){
				  SuchprofilMapper sp = new SuchprofilMapper();
				  
			  }}
				  catch (SQLException e) {
				      e.printStackTrace();
				    }
	return null;
}}
