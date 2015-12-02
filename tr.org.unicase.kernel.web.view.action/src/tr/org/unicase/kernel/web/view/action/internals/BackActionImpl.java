package tr.org.unicase.kernel.web.view.action.internals;

import com.vaadin.ui.UI;

import tr.org.unicase.kernel.web.view.MainWindow;
import tr.org.unicase.log.api.LogEvent;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.action.api.AbstractAction;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.api.View;

public class BackActionImpl<E> extends AbstractAction<E> {

	public BackActionImpl(String id, Action ownerAction, String icon, String text, String clazz, Controller<?> controller, View<?> view) {
		super(id, ownerAction, icon, text, clazz, controller, view, null);
	}

	@Override
	public void execute() throws Exception {
		LogEvent logEvent = prepareLogEvent();
		logEvent.setResult(" Ekran uzerinden geri tusuna basildi. ");
		log(logEvent);
		executeAfterController();
	}

	@Override
	protected void executeAfterController() {
		((MainWindow) UI.getCurrent()).back();
	}

}
