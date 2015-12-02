package tr.org.unicase.web.view.action.api;

import java.util.List;

import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.web.controller.api.Controller;

public interface Action {

	String getId();

	Action getOwnerAction();

	List<Action> getChildren();

	String getIcon();

	String getText();

	String getClazz();
	
	Field getField();

	void addAction(Action action);

	void execute() throws Exception;

	String getStatusMessage();

	Long getEntityTypeId();
	
	void setEntityTypeId(Long id);

	Long getOwnerEntityId();

	Controller<?> getController();

	void setOwnerAction(Action ownerAction);

}
