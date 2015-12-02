package tr.org.unicase.reference.web.view.action.internals;

import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.reference.web.view.action.OpenParentActionImpl;
import tr.org.unicase.reference.web.view.action.ReportActionImpl;
import tr.org.unicase.reference.web.view.action.UploadActionImpl;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.action.api.IActionFactory;
import tr.org.unicase.web.view.api.View;

public class ReferenceViewActionFactory implements IActionFactory {

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public int getFactoryId() {
		return getName().hashCode();
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Action createInstance(String clazz, String id, Action ownerAction, String icon, String text, String actionClass, Controller<?> controller, View<?> view, Field field) {
		Action result = null;

		if (OpenParentActionImpl.class.getName().equals(clazz))
			result = new OpenParentActionImpl(id, ownerAction, icon, text, actionClass, controller, view);
		else if (UploadActionImpl.class.getName().equals(clazz))
			result = new UploadActionImpl(id, ownerAction, icon, text, actionClass, controller, view);
		else if (ReportActionImpl.class.getName().equals(clazz))
			result = new ReportActionImpl(id, ownerAction, icon, text, actionClass, controller, view);
		return result;
	}

}
