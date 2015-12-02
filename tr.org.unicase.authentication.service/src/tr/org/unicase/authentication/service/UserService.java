package tr.org.unicase.authentication.service;

import java.util.List;

import tr.org.unicase.authentication.service.model.User;

public interface UserService {

	public void save(User user);

	public User findByUserName(String userName);

	public List<User> findAllUser();

}
