package tr.org.unicase.kernel.web.view;

import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.api.IEventListener;

public interface MainWindow extends IEventListener {

	void executeAction(Action action);
	
	void back();
	
}
