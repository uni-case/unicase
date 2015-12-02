package tr.org.unicase.authentication.service;

import java.util.List;

import org.springframework.security.authentication.encoding.PasswordEncoder;

import tr.org.unicase.authentication.service.activator.Activator;
import tr.org.unicase.authentication.service.internals.config.Configuration;
import tr.org.unicase.authentication.service.model.User;
import tr.org.unicase.web.controller.api.AbstractController;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinSession;

public class UserControllerImp extends AbstractController<User> {

	private UserService service;
	private PasswordEncoder encoder = null;
	private List<User> userList = null;
	public UserControllerImp(Long entityTypeId, Long ownerId, String refOwner) {
		super(entityTypeId, ownerId, refOwner);
		setEntity((User) VaadinSession.getCurrent().getAttribute(User.class.getName()));
		service = Activator.getUserService();
		encoder = Activator.getPasswordEncoder();
	}

	@Override
	public Object[] getNaturalColumnOrder() {
		return null;
	}

	@Override
	public String[] getColumnHeaderValues() {
		return null;
	}

	@Override
	public List<User> findAll(Long entityTypeId, Long ownerId, String refOwner) {
		return null;
	}

	@Override
	public User findById(Long id) {
		return null;
	}

	@Override
	public Container getDataSource(Long entityTypeId, Long ownerId, String refOwner) {
		return null;
	}

	@Override
	public void search() {
		
	}

	@Override
	public void generate() {
		User user = new User();
		setEntity(user);
		user.setStatus(Configuration.STATUS_ADD);
	}

	@Override
	public void open(User entity) {
		
	}
	
	@Override
	public void save(User entity) throws Exception {
		super.save(entity);
		service.save(entity);
	}

	@Override
	public void details(User entity) {
		
	}

	@Override
	public Container getDataSource() {
		BeanItemContainer<User> container = new BeanItemContainer<User>(User.class, userList);
		return container;
	}

	@Override
	public Container getDataSource(List<User> entities) {
		return null;
	}
	
	public User getCurrentUser() {
		return (User) VaadinSession.getCurrent().getAttribute(Configuration.USER_CLASS_NAME);
	}
	
	public List<User> findAllUser() {
		entityList = service.findAllUser();
		return userList;
	}

	public String encodePassword(String password) {
		
		String encodedPassword = null;
		String typeIdAsString = password;
		if (typeIdAsString != null && !typeIdAsString.trim().isEmpty())
			encodedPassword = encoder.encodePassword(typeIdAsString, null);
		
		return encodedPassword;
	}
	
	public void delete(User Entity) throws Exception{
		super.delete(Entity);
		entity.setStatus(Configuration.STATUS_DELETE);
		service.save(Entity);
		if (entityList != null && entityList.contains(Entity))
			entityList.remove(Entity);

	}

	public Boolean isUserAdmin() {
		String [] userActiveRoles = null;
		User user = getCurrentUser();

		if(user != null && user.getRole() != null){
			userActiveRoles = user.getRole().split(Configuration.ROLE_SEPERATOR);
			if(userActiveRoles != null && userActiveRoles.length >0)
				for (String role : userActiveRoles) {
					if(role != null && role.equalsIgnoreCase(Configuration.ROLE_ADMIN))
						return true;
				}
		}

		return false;
	}
}
