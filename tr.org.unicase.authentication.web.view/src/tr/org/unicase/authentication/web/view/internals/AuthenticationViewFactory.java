package tr.org.unicase.authentication.web.view.internals;

import tr.org.unicase.authentication.web.view.UserListViewImp;
import tr.org.unicase.authentication.web.view.UserViewImp;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.api.IViewFactory;
import tr.org.unicase.web.view.api.View;

public class AuthenticationViewFactory implements IViewFactory {

	@Override
	public int getFactoryId() {
		return getName().hashCode();
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	@SuppressWarnings("unchecked")
	public View<Controller<Object>> createInstance(String clazz, Long entityTypeId, Long ownerId, String refOwner) {
		View<?> view = null;
		
		if (UserViewImp.class.getName().equals(clazz)) 
			view = new UserViewImp(entityTypeId, ownerId, refOwner);
		else if (UserListViewImp.class.getName().equals(clazz))
			view = new UserListViewImp(entityTypeId, ownerId, refOwner);
		
		return (View<Controller<Object>>) view;
	}

}
