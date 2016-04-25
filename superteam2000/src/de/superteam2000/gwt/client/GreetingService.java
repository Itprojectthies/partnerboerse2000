package de.superteam2000.gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.superteam2000.gwt.shared.bo.Profil;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	public Profil getProfilById(int id) throws IllegalArgumentException;
	public void init() throws IllegalArgumentException;
}
