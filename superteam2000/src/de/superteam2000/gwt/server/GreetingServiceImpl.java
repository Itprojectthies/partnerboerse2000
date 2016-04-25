package de.superteam2000.gwt.server;

import de.superteam2000.gwt.client.GreetingService;
import de.superteam2000.gwt.shared.FieldVerifier;
import de.superteam2000.gwt.shared.bo.Profil;

import javax.validation.constraints.Null;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.superteam2000.gwt.server.db.*;
import de.superteam2000.gwt.shared.bo.*;
/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {
	
	private ProfilMapper pMapper = null;
	
	
	@Override
	public void init() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	
		this.pMapper = ProfilMapper.profilMapper();
		//private SuchprofilMapper sMapper = null;
		
		
	}
	@Override
	public Profil getProfilById(int id) {
		// TODO Auto-generated method stub
		
		
		return this.pMapper.findByKey(id);
	}

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException("Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo + ".<br><br>It looks like you are using:<br>"
				+ userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
}
