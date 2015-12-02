package tr.org.unicase.kernel.web.view.action.internals;

import tr.org.unicase.log.api.LogEvent;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.action.api.AbstractAction;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.api.View;

public class RefreshActionImpl<E> extends AbstractAction<E> {

	public RefreshActionImpl(String id, Action ownerAction, String icon, String text, String clazz, Controller<?> controller, View<?> view) {
		super(id, ownerAction, icon, text, clazz, controller, view, controller.getEntityTypeId());

	}

	@Override
	public void execute() throws Exception {
		executeAfterController();
	}

	@Override
	protected void log() {
		LogEvent logEvent = prepareLogEvent();
		logEvent.setResult(view.getName() + " ekrani guncellendi");
		log(logEvent);
	}

	@Override
	protected void executeAfterController() {
		view.refresh();
	}

}
