package si.telekom.erender.webui2;

import java.util.HashMap;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import si.telekom.erender.webui2.shared.FieldVerifier;
import si.telekom.erender.webui2.shared.LoginService;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	public boolean checkPassword(String user, String password) throws Exception {

		// Set up the environment for creating the initial context
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://srdc01.ts.telekom.si:389");

		// Authenticate as S. User and password "mysecret"
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "CN=" + user + ",OU=Users,OU=TelekomSlovenije,DC=ts,DC=telekom,DC=si");
		//env.put(Context.SECURITY_PRINCIPAL, "ts\\" + user);

		env.put(Context.SECURITY_CREDENTIALS, String.valueOf(password));

		// Create the initial context
		DirContext ctx = null;
		try {
			ctx = new InitialDirContext(env);

		} catch (NamingException ex) {
			// 525 user not found
			// 52e invalid credentials
			// 530 not permitted to logon at this time
			// 531 not permitted to logon at this workstation
			// 532 password expired
			// 533 account disabled
			// 701 account expired
			// 773 user must reset password
			// 775 user account locked
			HashMap errors = new HashMap<String, String>();
			errors.put("525", "user not found");
			errors.put("52e", "invalid credentials");
			errors.put("530", "not permitted to logon at this time");
			errors.put("532", "password expired");
			errors.put("533", "account disabled");
			errors.put("569", "denied access to computer via network");
			errors.put("701", "account expired");
			errors.put("773", "user must reset password");
			errors.put("775", "account locked");
			String foundStr = ", data";
			int loc = ex.getMessage().indexOf(foundStr);
			String errCode = ex.getMessage().substring(loc + foundStr.length() + 1, loc + foundStr.length() + 4);
			throw new Exception("Problem with ldap, " + errors.get(errCode));
		}
		return true;

	}

	public String login(String loginName, String loginPassword) throws IllegalArgumentException {
		// Verify that the input is valid.
		if (!FieldVerifier.isValidName(loginName)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException("Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		loginName = escapeHtml(loginName);
		userAgent = escapeHtml(userAgent);

		try {
			if (checkPassword(loginName, loginPassword))
				return "Hello, " + loginName + "!<br><br>I am running " + serverInfo + ".<br><br>It looks like you are using:<br>" + userAgent
						+ "<br>Login suceeded.";
			else
				return "Hello, " + loginName + "!<br><br>I am running " + serverInfo + ".<br><br>It looks like you are using:<br>" + userAgent
						+ "<br>Login NOT suceeded.";
		} catch (Exception ex) {
			return "Hello, " + loginName + "!<br><br>I am running " + serverInfo + ".<br><br>It looks like you are using:<br>" + userAgent
					+ "<br><br>Login NOT suceeded because of:<br>" + ex.getMessage();
		}
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *          the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
}
