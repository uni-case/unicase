package tr.org.unicase.kernel.web.view.action.internals.factory;

import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.kernel.web.view.action.DeleteActionImpl;
import tr.org.unicase.kernel.web.view.action.GenerateActionImpl;
import tr.org.unicase.kernel.web.view.action.LockEntityActionImpl;
import tr.org.unicase.kernel.web.view.action.OpenActionImpl;
import tr.org.unicase.kernel.web.view.action.SaveActionImpl;
import tr.org.unicase.kernel.web.view.action.UnLockEntityActionImpl;
import tr.org.unicase.kernel.web.view.action.internals.BackActionImpl;
import tr.org.unicase.kernel.web.view.action.internals.DetailsActionImpl;
import tr.org.unicase.kernel.web.view.action.internals.MenuActionImpl;
import tr.org.unicase.kernel.web.view.action.internals.RefreshActionImpl;
import tr.org.unicase.kernel.web.view.action.internals.SettingsActionImpl;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.action.api.IActionFactory;
import tr.org.unicase.web.view.api.View;

public class KernelViewActionFactory implements IActionFactory {

	@Override
	public String getName() {
		return this.getClass().getName();
	}

	@Override
	public int getFactoryId() {
		return getName().hashCode();
	}

	@Override
	public Action createInstance(String clazz, String id, Action ownerAction, String icon, String text, String actionClass, Controller<?> controller, View<?> view, Field field) {
		Action result = null;

		if (BackActionImpl.class.getName().equals(clazz))
			result = new BackActionImpl<>(id, ownerAction, icon, text, actionClass, controller, view);
		else if (DeleteActionImpl.class.getName().equals(clazz))
			result = new DeleteActionImpl<>(id, ownerAction, icon, text, actionClass, controller, view);
		else if (DetailsActionImpl.class.getName().equals(clazz))
			result = new DetailsActionImpl<>(id, ownerAction, icon, text, actionClass, controller, view, field);
		else if (GenerateActionImpl.class.getName().equals(clazz))
			result = new GenerateActionImpl<>(id, ownerAction, icon, text, actionClass, controller, view);
		else if (LockEntityActionImpl.class.getName().equals(clazz))
			result = new LockEntityActionImpl<>(id, ownerAction, icon, text, actionClass, controller, view);
		else if (OpenActionImpl.class.getName().equals(clazz))
			result = new OpenActionImpl<>(id, ownerAction, icon, text, actionClass, controller, view);
		else if (RefreshActionImpl.class.getName().equals(clazz))
			result = new RefreshActionImpl<>(id, ownerAction, icon, text, actionClass, controller, view);
		else if (SaveActionImpl.class.getName().equals(clazz))
			result = new SaveActionImpl<>(id, ownerAction, icon, text, actionClass, controller, view);
		else if (SettingsActionImpl.class.getName().equals(clazz))
			result = new SettingsActionImpl<>(id, ownerAction, icon, text, actionClass, controller, view);
		else if (UnLockEntityActionImpl.class.getName().equals(clazz))
			result = new UnLockEntityActionImpl<>(id, ownerAction, icon, text, actionClass, controller, view);
		else if (MenuActionImpl.class.getName().equals(clazz))
			result = new MenuActionImpl<>(id, ownerAction, icon, text, actionClass, controller, view);
		return result;
	}

}
