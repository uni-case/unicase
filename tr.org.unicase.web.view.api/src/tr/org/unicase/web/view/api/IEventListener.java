package tr.org.unicase.web.view.api;

import tr.org.unicase.log.api.LogEvent;
import tr.org.unicase.web.view.action.api.Action;

public interface IEventListener {
	
	void fireEvent(LogEvent event);

	void fireEvent(LogEvent.Types type, Action action);

}
