package tr.org.unicase.web.app.internals;

import tr.org.unicase.web.app.MainWindowImpl;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class SimpleUIProvider extends UIProvider {

	@Override
	public Class<? extends UI> getUIClass(UIClassSelectionEvent uiClassSelectionEvent) {
		return MainWindowImpl.class;
	}

	@Override
	public UI createInstance(UICreateEvent event) {
		UI ui = new MainWindowImpl();
		return ui;
	}

}
