package tr.org.unicase.kernel.web.view.action;

import com.vaadin.ui.UI;

import tr.org.unicase.kernel.model.IEntity;
import tr.org.unicase.kernel.web.view.MainWindow;
import tr.org.unicase.log.api.LogEvent;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.action.api.AbstractAction;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.api.View;

public class OpenActionImpl<E> extends AbstractAction<E> {

	public OpenActionImpl(String id, Action ownerAction, String icon, String text, String clazz, Controller<?> controller, View<?> view) {
		super(id, ownerAction, icon, text, clazz, controller, view, controller.getEntityTypeId(), false, false, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void log() {
		IEntity entity = (IEntity) ((Controller<E>) view.getController()).getEntity();
		LogEvent logEvent = prepareLogEvent();
		logEvent.setEntity(entity);
		logEvent.setResult(entity.getId() + " bilgilerine bakiliyor");
		log(logEvent);
	}

	@Override
	protected void executeAfterController() {
		((MainWindow) UI.getCurrent()).executeAction(this);
	}
}
