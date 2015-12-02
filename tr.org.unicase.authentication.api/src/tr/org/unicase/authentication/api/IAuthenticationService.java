package tr.org.unicase.authentication.api;

import tr.org.unicase.authentication.service.model.User;

public interface IAuthenticationService {

	public int getFactoryId();
	
	public String getName();
	
	public User authenticate(String username, String password) throws Exception;
	
}