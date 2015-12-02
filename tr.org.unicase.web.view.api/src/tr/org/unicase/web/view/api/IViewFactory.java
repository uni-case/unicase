package tr.org.unicase.web.view.api;

import tr.org.unicase.web.controller.api.Controller;

public interface IViewFactory {

	public int getFactoryId();

	public String getName();

	public View<Controller<Object>> createInstance(String clazz, Long entityTypeId, Long ownerId, String refOwner);

}
