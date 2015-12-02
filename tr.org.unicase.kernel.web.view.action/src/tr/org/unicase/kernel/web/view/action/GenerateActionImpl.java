package tr.org.unicase.kernel.web.view.action;

import tr.org.unicase.log.api.LogEvent;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.action.api.AbstractAction;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.api.View;

public class GenerateActionImpl<E> extends AbstractAction<E> {

	public GenerateActionImpl(String id, Action ownerAction, String icon, String text, String clazz, Controller<?> controller, View<?> view) {
		super(id, ownerAction, icon, text, clazz, controller, view, controller.getEntityTypeId());
	}

	@Override
	public void execute() throws Exception {
		controller.getClass().getMethod(getName()).invoke(controller);
		view.update();
		log();
	}

	@Override
	protected void log() {
		LogEvent logEvent = prepareLogEvent();
		logEvent.setResult(controller.getEntityTypeId() + " icin yeni bir entity olusturuluyor");
		log(logEvent);
	}

	@Override
	protected void executeAfterController() {

	}

}
