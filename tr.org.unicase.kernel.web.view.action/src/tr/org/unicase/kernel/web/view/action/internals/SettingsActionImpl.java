package tr.org.unicase.kernel.web.view.action.internals;

import com.vaadin.ui.UI;

import tr.org.unicase.kernel.web.controller.FieldControllerImp;
import tr.org.unicase.kernel.web.view.FieldListViewImp;
import tr.org.unicase.kernel.web.view.MainWindow;
import tr.org.unicase.log.api.LogEvent;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.action.api.AbstractAction;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.api.View;

public class SettingsActionImpl<E> extends AbstractAction<E> {

	public SettingsActionImpl(String id, Action ownerAction, String icon, String text, String clazz, Controller<?> controller, View<?> view) {
		super(id, ownerAction, icon, text, FieldListViewImp.class.getName(), (Controller<?>) new FieldControllerImp(controller.getEntityTypeId(), null, null), view, controller.getEntityTypeId(), false, false, null);

		this.name = view.getName() + " Ekran Ayarları";
	}

	@Override
	public void execute() throws Exception {
		executeAfterController();
		log();
	}
	
	@Override
	protected void log() {
		LogEvent logEvent = prepareLogEvent();
		logEvent.setResult("Ekran Ayarlari acildi");
		log(logEvent);
	}

	@Override
	protected void executeAfterController() {
		((MainWindow) UI.getCurrent()).executeAction(this);
	}
}