package tr.org.unicase.authentication.service.internals;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import tr.org.unicase.authentication.api.IAuthenticationService;
import tr.org.unicase.authentication.service.internals.config.Configuration;
import tr.org.unicase.authentication.service.model.User;

public class AuthenticationServiceImpl implements IAuthenticationService {

	private AuthenticationManager authenticationManager = null;

	@Override
	public User authenticate(String username, String password) throws Exception {
		Authentication aut = new UsernamePasswordAuthenticationToken(username, password);
		try {
			aut = authenticationManager.authenticate(aut);
			SecurityContextHolder.getContext().setAuthentication(aut);
		} catch (BadCredentialsException bce) {
			throw new Exception(Configuration.ExceptionMessage.BAD_CREDENTIALS);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return (User) aut.getPrincipal();
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public int getFactoryId() {
		return getName().hashCode();
	}

}
