package tr.org.unicase.reference.web.view.action;

import tr.org.unicase.kernel.web.view.MainWindow;
import tr.org.unicase.log.api.LogEvent;
import tr.org.unicase.reference.model.ReferenceEntity;
import tr.org.unicase.reference.web.controller.ReferenceControllerImp;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.action.api.AbstractAction;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.api.View;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

public class OpenParentActionImpl extends AbstractAction<ReferenceEntity> {

	public OpenParentActionImpl(String id, Action ownerAction, String icon, String text, String clazz, Controller<?> controller, View<?> view) {
		super(id, ownerAction, icon, text, clazz, controller, view, controller.getEntityTypeId(), false, false, null);
	}

	public void operate(String theName) throws Exception {
		ReferenceEntity entity = (ReferenceEntity) controller.getEntity();
		ReferenceEntity owner = (entity != null ? entity.getCk_owner() : null);
		if (owner != null) {
			((ReferenceControllerImp) controller).setEntity(owner);
			this.setEntityTypeId(owner.getEntityTypeId().getId());
			executeAfterController();
			log();
		} else {
			Notification.show("Sectiginiz bilgilerin atasi bulunamadi.", Type.ERROR_MESSAGE);
		}

	}

	@Override
	@SuppressWarnings("unchecked")
	protected void log() {
		ReferenceEntity entity = ((Controller<ReferenceEntity>) view.getController()).getEntity();
		LogEvent logEvent = prepareLogEvent();
		logEvent.setEntity(entity);
		logEvent.setResult(entity.getId() +  " nin baba bilgilerine bakiliyor");
	}

	@Override
	protected void executeAfterController() {
		((MainWindow) UI.getCurrent()).executeAction(this);
	}
}