package tr.org.unicase.web.view.action.api;

import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.api.View;

public interface IActionFactory {

	public String getName();
	
	public int getFactoryId();
	
	public Action createInstance(String clazz, String id, Action ownerAction, String icon, String text, String actionClass, Controller<?> controller, View<?> view, Field field);
	
}
