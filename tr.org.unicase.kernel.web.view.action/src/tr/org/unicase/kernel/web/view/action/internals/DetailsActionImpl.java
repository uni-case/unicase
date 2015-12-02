package tr.org.unicase.kernel.web.view.action.internals;

import com.vaadin.ui.UI;

import tr.org.unicase.kernel.model.Field;
import tr.org.unicase.kernel.model.IEntity;
import tr.org.unicase.kernel.web.view.MainWindow;
import tr.org.unicase.log.api.LogEvent;
import tr.org.unicase.web.controller.api.Controller;
import tr.org.unicase.web.view.action.api.AbstractAction;
import tr.org.unicase.web.view.action.api.Action;
import tr.org.unicase.web.view.api.View;

public class DetailsActionImpl<E> extends AbstractAction<E> {

	private Field fields;

	public DetailsActionImpl(String id, Action ownerAction, String icon, String text, String clazz, Controller<?> controller, View<?> view, Field fields, Long ownerId) {
		super(id, ownerAction, icon, text, clazz, controller, view, controller.getEntityTypeId(), Boolean.FALSE, Boolean.FALSE, ownerId);
		this.setField(fields);
		if (fields != null && fields.getType() != null && fields.getType().equals("NTO1"))
			setOwnerEntityId(((IEntity) getController().getEntity()).getCk_owner().getId());
	}

	public DetailsActionImpl(String id, Action ownerAction, String icon, String text, String clazz, Controller<?> controller, View<?> view, Field fields) {
		this(id, ownerAction, icon, text, clazz, controller, view, fields, null);
	}

	@Override
	public String getClazz() {
		String theResult = super.getClazz();
		Field entityTypeRef = this.getField();
		String theType = entityTypeRef.getType();
		if (theType != null && theType.equals("1TON"))
			theResult = "tr.org.unicase.reference.web.view.ReferenceDetailViewImp";
		else if (theType != null && theType.equals("1TO1"))
			theResult = "tr.org.unicase.reference.web.view.ReferenceViewImp";
		else if (theType != null && theType.equals("NTO1"))
			theResult = "tr.org.unicase.reference.web.view.ReferenceParentViewImp";

		return theResult;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void log() {
		LogEvent logEvent = prepareLogEvent();
		IEntity entity = (IEntity) ((Controller<E>) view.getController()).getEntity();
		logEvent.setEntity(entity);
		logEvent.setResult(entity.getId() + " detaylarina bakiliyor");
		log(logEvent);
	}

	@Override
	protected void executeAfterController() {
		((MainWindow) UI.getCurrent()).executeAction(this);
	}

	public Field getField() {
		return this.fields;
	}

	public void setField(Field fields) {
		this.fields = fields;
	}

}
