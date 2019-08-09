package si.telekom.erender.webui2.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>LoginService</code>.
 */
public interface LoginServiceAsync {
	void login(String loginName, String password, AsyncCallback<String> callback) throws IllegalArgumentException;
}
