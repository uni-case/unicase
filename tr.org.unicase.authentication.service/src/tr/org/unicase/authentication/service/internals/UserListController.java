package tr.org.unicase.authentication.service.internals;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import tr.org.unicase.authentication.service.UserService;
import tr.org.unicase.authentication.service.model.User;

public class UserListController implements UserDetailsService, UserDetailsManager {

	private UserService userService;
		
	@Override
	public boolean userExists(String userName) {
		User user = getUserService().findByUserName(userName);
		return (user != null);
	}

	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = getUserService().findByUserName(userName);
		if (user == null)
			throw new UsernameNotFoundException(userName + " kullanicisi bulunamadi");
		return user;
	}
	
	@Override
	public void changePassword(String oldPassword, String newPassword) {
		
	}

	@Override
	public void createUser(UserDetails userDetails) {
		
	}

	@Override
	public void deleteUser(String userName) {

	}

	@Override
	public void updateUser(UserDetails userDetails) {

	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	
	

}
