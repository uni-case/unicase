package tr.org.unicase.web.view.api;

import java.util.List;

import tr.org.unicase.web.view.action.api.Action;

public interface Module {

	public String getName();

	public String getIcon();

	public List<Action> getActions();

}
