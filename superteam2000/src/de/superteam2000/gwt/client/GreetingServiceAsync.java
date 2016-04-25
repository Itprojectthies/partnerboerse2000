package de.superteam2000.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.superteam2000.gwt.shared.bo.Profil;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
	void getProfilById(int id, AsyncCallback<Profil> callback);
	void init(AsyncCallback<Void> callback);
}
