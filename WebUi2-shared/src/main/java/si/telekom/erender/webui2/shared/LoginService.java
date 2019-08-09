package si.telekom.erender.webui2.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface LoginService extends RemoteService {
	String login(String loginName, String password) throws IllegalArgumentException;
}
