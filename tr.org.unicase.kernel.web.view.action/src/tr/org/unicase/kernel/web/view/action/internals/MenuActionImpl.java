package tr.org.unicase.kernel.web.view.action.internals;

import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.action.api.AbstractAction;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.api.View;

public class MenuActionImpl<E> extends AbstractAction<E> {

	public MenuActionImpl(String id, Action ownerAction, String icon, String text, String clazz, Controller<?> controller, View<?> view) {
		super(id, ownerAction, icon, text, clazz, controller, view, null);
	}

	@Override
	protected void executeAfterController() {

	}

}
