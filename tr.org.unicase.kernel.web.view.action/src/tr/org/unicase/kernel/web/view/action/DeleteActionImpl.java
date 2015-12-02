package tr.org.unicase.kernel.web.view.action;

import tr.org.unicase.kernel.model.IEntity;
import tr.org.unicase.log.api.LogEvent;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.action.api.AbstractAction;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.api.View;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class DeleteActionImpl<E> extends AbstractAction<E> {

	public DeleteActionImpl(String id, Action ownerAction, String icon, String text, String clazz, Controller<?> controller, View<?> view) {
		super(id, ownerAction, icon, text, clazz, controller, view, controller.getEntityTypeId(), true, true, null);

	}

	@Override
	@SuppressWarnings("unchecked")
	protected void log() {
		LogEvent logEvent = prepareLogEvent();
		IEntity entity = (IEntity) ((Controller<E>) view.getController()).getEntity();
		logEvent.setEntity(entity);
		logEvent.setResult(entity.getId() + " silindi");
		log(logEvent);
	}

	@Override
	protected void executeAfterController() {
		Notification.show("Silme İşlemi Başarı İle Gerçekleştirildi.", Type.HUMANIZED_MESSAGE);
		view.update();
		view.refresh();
	}

}
